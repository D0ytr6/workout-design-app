package com.example.samurairoad.service.authApi

import com.example.samurairoad.ui.auth.FastApiUser
import com.example.samurairoad.ui.auth.RegisterUserModel
import com.example.samurairoad.ui.auth.models.LoginResponse
import com.example.samurairoad.utils.RefreshResponce
import retrofit2.Response
import retrofit2.http.*

interface AuthApiWorkoutService {

    @POST("/auth/login")
    suspend fun registerUser(@Body userModel: RegisterUserModel): FastApiUser

    @FormUrlEncoded
    @POST("/login/token")
    suspend fun loginUser(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    @POST("/login/token/refresh")
    suspend fun refreshToken(
        @Header("Authorization") token: String,
    ): Response<RefreshResponce>

}