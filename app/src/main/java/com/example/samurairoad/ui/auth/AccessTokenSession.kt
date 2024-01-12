package com.example.samurairoad.ui.auth

class AccessTokenSession{
    private var accessToken: String? = null
    private var accessTokenExpirationTime: Long? = null

    fun setAccessToken(token: String){
        accessToken = token
    }

    fun getAccessToken(): String? = accessToken
}