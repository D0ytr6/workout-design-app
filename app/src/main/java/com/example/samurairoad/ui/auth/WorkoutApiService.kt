package com.example.samurairoad.ui.auth

import com.example.samurairoad.ui.auth.models.Auth
import com.example.samurairoad.ui.auth.models.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface WorkoutApiService {

    @GET("/users")
    suspend fun getUsers(@Query("skip") skip: Int, @Query("limit") limit: Int): List<FastApiUser>

//    TODO register user, register page, login after
    @POST("/auth/login")
    suspend fun registerUser(@Body userModel: RegisterUserModel): FastApiUser

    @POST("/login/token")
    suspend fun loginUser(@Body auth: Auth, ): Response<LoginResponse>

}