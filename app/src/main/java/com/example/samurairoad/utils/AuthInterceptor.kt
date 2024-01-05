package com.example.samurairoad.utils

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

// add auth token to each api request send
class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        // get access token
        val token = runBlocking {
            tokenManager.getToken().first() // get first emitted value
        }
//        update request
        val request = chain.request().newBuilder()
        request.addHeader("Authorization", "Bearer $token")

        return chain.proceed(request.build())
    }
}