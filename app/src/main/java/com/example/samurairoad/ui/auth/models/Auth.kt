package com.example.samurairoad.ui.auth.models

import retrofit2.http.Field

data class Auth(
    val username: String,
    val password: String
)
