package com.vignesh.todo.common.repository.network

import com.vignesh.todo.common.dj.LNCommonRepositoryModule
import com.vignesh.todo.common.repository.local.LNSharePreferenceImpl
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TDAuthorizationInterceptor @Inject constructor(
    val sharePreference: LNSharePreferenceImpl,
    @LNCommonRepositoryModule.RefreshTokenClient val refreshTokenClient: Class<LNApiManagerImpl>
) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}