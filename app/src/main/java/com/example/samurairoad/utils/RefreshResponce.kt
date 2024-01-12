package com.example.samurairoad.utils

import com.google.gson.annotations.SerializedName

data class RefreshResponce(
    @SerializedName("access_token")
    val accessToken: String,
)
