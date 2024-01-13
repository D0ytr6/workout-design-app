package com.example.samurairoad.service.mainApi

import com.example.samurairoad.ui.auth.FastApiUser
import com.example.samurairoad.ui.auth.RegisterUserModel
import com.example.samurairoad.ui.auth.models.LoginResponse
import retrofit2.Response
import retrofit2.http.*


interface WorkoutApiService {

    @GET("/users")
    suspend fun getUsers(@Query("skip") skip: Int, @Query("limit") limit: Int): List<FastApiUser>

    suspend fun getFreeWorkouts()

}