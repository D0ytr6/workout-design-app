package com.example.samurairoad.ui.auth

import com.example.samurairoad.ui.auth.models.Auth
import com.example.samurairoad.ui.auth.models.LoginResponse
import retrofit2.Response
import retrofit2.http.*

interface WorkoutApiService {

    @GET("/users")
    suspend fun getUsers(@Query("skip") skip: Int, @Query("limit") limit: Int): List<FastApiUser>

//    TODO register user, register page, login after
    @POST("/auth/login")
    suspend fun registerUser(@Body userModel: RegisterUserModel): FastApiUser

//    @POST("/login/token")
//    suspend fun loginUser(@Body auth: Auth): Response<LoginResponse>

    @FormUrlEncoded
    @POST("/login/token")
    suspend fun loginUser(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    @GET("auth/refresh")
    suspend fun refreshToken(
        @Header("Authorization") token: String,
    ): Response<LoginResponse>

}