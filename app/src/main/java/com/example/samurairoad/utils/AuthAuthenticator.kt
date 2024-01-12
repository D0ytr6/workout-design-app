package com.example.samurairoad.utils

import com.example.samurairoad.service.authApi.AuthApiWorkoutService
import com.example.samurairoad.ui.auth.models.LoginResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

//class AuthAuthenticator @Inject constructor(
//    private val tokenManager: TokenManager,
//): Authenticator  {
//    // Gets called when a request returns 401 (Unauthorized Exception )
//    override fun authenticate(route: Route?, response: Response): Request? {
//        val token = runBlocking {
//            tokenManager.getToken().first()
//        }
//        return runBlocking {
//            val newToken = getNewToken(token)
//
//            if (!newToken.isSuccessful || newToken.body() == null) { //Couldn't refresh the token, so restart the login process
//                tokenManager.deleteToken()
//            }
//
//            newToken.body()?.let {
//                tokenManager.saveToken(it.accessToken)
//                response.request.newBuilder()
//                    .header("Authorization", "Bearer ${it.accessToken}")
//                    .build()
//            }
//        }
//    }
//
//    private suspend fun getNewToken(refreshToken: String?): retrofit2.Response<LoginResponse> {
//        val loggingInterceptor = HttpLoggingInterceptor()
//        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
//
//        val retrofit = Retrofit.Builder()
////                TODO url
//            .baseUrl("https://jwt-test-api.onrender.com/api/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)
//            .build()
//        val service = retrofit.create(AuthApiWorkoutService::class.java)
//        return service.refreshToken("Bearer $refreshToken")
//    }
//}