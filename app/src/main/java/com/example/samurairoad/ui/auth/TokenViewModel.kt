package com.example.samurairoad.ui.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samurairoad.repository.AuthRepository
import com.example.samurairoad.ui.auth.models.SplashScreenStatus
import com.example.samurairoad.utils.RefreshTokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

// token operation view model
// TODO remove Livedata
@HiltViewModel
class TokenViewModel @Inject constructor(
    private val refreshTokenManager: RefreshTokenManager,
    private val authRepository: AuthRepository,
    private val accessTokenSession: AccessTokenSession,
): ViewModel(){

//    TODO add shadow field
    val refreshTokenLiveData = MutableLiveData<String?>()

    val splashScreenStatus = MutableStateFlow(SplashScreenStatus.LOADING)

    init {
        viewModelScope.launch(Dispatchers.IO) {
//            collect token value from flow, update livedata
            refreshTokenManager.getToken().collect {token ->
                withContext(Dispatchers.Main) {
                    refreshTokenLiveData.value = token
                    val offlineMode = refreshTokenManager.getOfflineState()
                    // token exist
                    if(token != null && !isRefreshTokenExpired() && (offlineMode == false || offlineMode == null)){
                        val accessToken = getAccessTokenByRefresh(token)
                        if (accessToken != null){
                            accessTokenSession.setAccessToken(accessToken)
                            splashScreenStatus.value = SplashScreenStatus.HOME_SCREEN
                        }
                    }
                    else if (token != null && offlineMode == true){
                        splashScreenStatus.value = SplashScreenStatus.HOME_SCREEN
                    }

                    else{
                        Log.d("STATE", "Change to SplashScreenStatus.LOGIN_SCREEN")
                        splashScreenStatus.value = SplashScreenStatus.LOGIN_SCREEN
                    }
                }
            }
        }
    }

    fun saveRefreshToken(token: String){
        Log.d("Token", token)
        viewModelScope.launch(Dispatchers.IO) {
            refreshTokenManager.saveToken(token)
//            TODO make constant
            setExpirationRefreshToken(60)
        }
    }

    fun deleteRefreshToken(){
        viewModelScope.launch(Dispatchers.IO) {
            refreshTokenManager.deleteToken()
            refreshTokenManager.deleteExpirationTime()
            val offlineMode = refreshTokenManager.getOfflineState()
            if(offlineMode == true){
                refreshTokenManager.removeOfflineState()
            }
        }
    }

    private suspend fun getAccessTokenByRefresh(token: String): String? {
        val response = authRepository.getAccessToken(token)
        if (!response.isSuccessful || response.body() == null){
            return null
        }
        return response.body()?.accessToken
    }

    private suspend fun setExpirationRefreshToken(live_days: Long){
        val expSec = live_days * 24 * 60 * 60
        refreshTokenManager.setExpirationTime(expSec)
    }

    private suspend fun isRefreshTokenExpired(): Boolean {
        val currentTimeMillis = System.currentTimeMillis()
        var expTime = refreshTokenManager.getExpirationTime()
        if (expTime != null){
            expTime = System.currentTimeMillis() + (expTime * 1000)
            if (currentTimeMillis >= expTime){
                return true
            }

        }
        return false
    }

    fun setOfflineMode(){
        viewModelScope.launch {
            refreshTokenManager.setOfflineState()
        }
    }

}