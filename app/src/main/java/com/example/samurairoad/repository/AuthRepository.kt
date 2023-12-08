package com.example.samurairoad.repository

import com.example.samurairoad.ui.auth.WorkoutApiService
import com.example.samurairoad.ui.auth.models.Auth
import com.example.samurairoad.utils.apiRequestFlow
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authWorkoutApi: WorkoutApiService
) {

    fun login(auth: Auth) = apiRequestFlow {
        authWorkoutApi.loginUser(auth)
    }
}