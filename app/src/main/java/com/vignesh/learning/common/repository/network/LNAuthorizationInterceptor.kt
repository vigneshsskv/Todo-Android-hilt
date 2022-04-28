package com.vignesh.learning.common.repository.network

import com.vignesh.learning.common.dj.LNCommonRepositoryModule
import com.vignesh.learning.common.repository.local.LNSharePreferenceImpl
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class LNAuthorizationInterceptor @Inject constructor(
    val sharePreference: LNSharePreferenceImpl,
    @LNCommonRepositoryModule.RefreshTokenClient val refreshTokenClient: Class<LNApiManagerImpl>
) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}