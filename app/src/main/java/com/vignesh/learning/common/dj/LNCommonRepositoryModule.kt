package com.vignesh.learning.common.dj

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.vignesh.learning.common.LNConstant
import com.vignesh.learning.common.repository.local.LNSharePreference
import com.vignesh.learning.common.repository.local.LNSharePreferenceImpl
import com.vignesh.learning.common.repository.local.db.LNDatabase
import com.vignesh.learning.common.repository.network.LNApiManager
import com.vignesh.learning.common.repository.network.LNApiManagerImpl
import com.vignesh.learning.common.repository.network.LNAuthorizationInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LNCommonRepositoryModule {
    @Provides
    @Singleton
    fun getDataBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, LNDatabase::class.java, LNConstant.DATABASE_NAME).build()

    @Provides
    @Singleton
    fun getPreference(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(
            LNConstant.SHARE_PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )

    @Qualifier
    annotation class AuthorisedApiClient

    @Qualifier
    annotation class UnAuthorisedApiClient

    @Qualifier
    annotation class RefreshTokenClient


    @Provides
    @Singleton
    @AuthorisedApiClient
    fun getRefreshTokenClient(
        preferences: LNSharePreferenceImpl,
    ): Class<LNApiManagerImpl> =
        LNApiManager.buildRetrofitClient(
            LNApiManager.generateOkHttpClient(
                isRefreshTokenNeed = true,
                preference = preferences,
            ),
            LNApiManagerImpl::class.java
        )

    @Provides
    @Singleton
    @AuthorisedApiClient
    fun getAuthorizationClient(
        preferences: LNSharePreferenceImpl,
        authInterceptor: LNAuthorizationInterceptor
    ): Class<LNApiManagerImpl> =
        LNApiManager.buildRetrofitClient(
            LNApiManager.generateOkHttpClient(
                isAuthTokenNeed = true,
                preference = preferences,
                authInterceptor = authInterceptor
            ),
            LNApiManagerImpl::class.java
        )

    @Provides
    @Singleton
    @UnAuthorisedApiClient
    fun getUnAuthorizationClient(preferences: LNSharePreferenceImpl): Class<LNApiManagerImpl> =
        LNApiManager.buildRetrofitClient(
            LNApiManager.generateOkHttpClient(
                preference = preferences
            ),
            LNApiManagerImpl::class.java
        )
}

@Module
@InstallIn(SingletonComponent::class)
abstract class LNCommonRepositoryImpl {
    @Binds
    @Singleton
    abstract fun getPreferenceImpl(preferences: LNSharePreference): LNSharePreferenceImpl
}