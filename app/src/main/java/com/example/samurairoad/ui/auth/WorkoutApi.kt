package com.example.samurairoad.ui.auth

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface WorkoutApi {

    @GET("/users")
    suspend fun getUsers(@Query("skip") skip: Int, @Query("limit") limit: Int): List<FastApiUser>

    @POST("/users")
    suspend fun registerUser(@Body userModel: RegisterUserModel)
}