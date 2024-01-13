package com.example.samurairoad.repository

import com.example.samurairoad.service.authApi.AuthApiWorkoutService
import com.example.samurairoad.ui.auth.models.Auth
import com.example.samurairoad.utils.RefreshResponce
import com.example.samurairoad.utils.apiRequestFlow
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authWorkoutApi: AuthApiWorkoutService
) {

    fun login(auth: Auth) = apiRequestFlow {
        authWorkoutApi.loginUser(auth.username, auth.password)
    }

    suspend fun getAccessToken(refreshToken: String): Response<RefreshResponce>{
        return authWorkoutApi.refreshToken("Bearer $refreshToken")
    }

//    fun login2(auth: Auth): Flow<ApiResponse<LoginResponse>>{
//        val flow = apiRequestFlow{
//            authWorkoutApi.loginUser(auth)
//        }
//        return flow
//    }
}