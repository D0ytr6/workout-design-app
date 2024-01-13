package com.example.samurairoad.utils

import com.example.samurairoad.service.authApi.AuthApiWorkoutService
import com.example.samurairoad.ui.auth.AccessTokenSession
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

// Update Access
class AuthAuthenticator @Inject constructor(
    private val refreshTokenManager: RefreshTokenManager,
    private val accessTokenSession: AccessTokenSession,
): Authenticator  {
    // Gets called when a request returns 401 (Unauthorized Exception )
    override fun authenticate(route: Route?, response: Response): Request? {
        val token = runBlocking {
            refreshTokenManager.getToken().first()
        }
        return runBlocking {
            val newAccessToken = getNewToken(token)

            if (!newAccessToken.isSuccessful || newAccessToken.body() == null) { //Couldn't refresh the token, so restart the login process
                refreshTokenManager.deleteToken()
            }

            newAccessToken.body()?.let {
                accessTokenSession.setAccessToken(it.accessToken)
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${it.accessToken}")
                    .build()
            }
        }
    }

    private suspend fun getNewToken(refreshToken: String?): retrofit2.Response<RefreshResponce> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
//                TODO url
//                TODO remove build, add api inject
            .baseUrl("https://jwt-test-api.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val service = retrofit.create(AuthApiWorkoutService::class.java)
        return service.refreshToken("Bearer $refreshToken")
    }
}