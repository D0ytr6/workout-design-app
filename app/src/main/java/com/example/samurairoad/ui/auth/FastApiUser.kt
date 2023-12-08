package com.example.samurairoad.ui.auth

import androidx.lifecycle.LiveData
import com.google.gson.annotations.SerializedName

data class FastApiUser(
    val id: Int,
    val email: String,
    val firstName: String,
    val token: String,
) {

}