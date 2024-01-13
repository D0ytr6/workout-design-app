package com.example.samurairoad.utils

import com.example.samurairoad.ui.auth.AccessTokenSession
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

// add auth token to each api request send
class AuthInterceptor @Inject constructor(
    private val refreshTokenManager: RefreshTokenManager,
    private val accessTokenSession: AccessTokenSession,
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        // get access token
//        val token = runBlocking {
//            tokenManager.getToken().first() // get first emitted value
//        }
//        TODO null check
        val token = accessTokenSession.getAccessToken()

//        update request
        val request = chain.request().newBuilder()
        request.addHeader("Authorization", "Bearer $token")

        return chain.proceed(request.build())
    }
}