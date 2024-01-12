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
    val accessTokenLiveData = MutableLiveData<String?>()
    val refreshTokenLiveData = MutableLiveData<String?>()

    val splashScreenStatus = MutableStateFlow(SplashScreenStatus.LOADING)

    init {
        viewModelScope.launch(Dispatchers.IO) {
//            collect token value from flow, update livedata
            refreshTokenManager.getToken().collect {token ->
                withContext(Dispatchers.Main) {
                    refreshTokenLiveData.value = token
                    // token exist
                    if(token != null){
                        val accessToken = getAccessTokenByRefresh(token)
                        if (accessToken != null){
                            accessTokenSession.setAccessToken(accessToken)
                            splashScreenStatus.value = SplashScreenStatus.HOME_SCREEN
                        }
                    }
                    else{
                        Log.d("STATE", "Change to SplashScreenStatus.LOGIN_SCREEN")
                        splashScreenStatus.value = SplashScreenStatus.LOGIN_SCREEN
                    }
                }
            }
        }
    }

    fun saveToken(token: String){
        Log.d("Token", token)
        viewModelScope.launch(Dispatchers.IO) {
            refreshTokenManager.saveToken(token)
        }
    }

    fun deleteToken(){
        viewModelScope.launch(Dispatchers.IO) {
            refreshTokenManager.deleteToken()
        }
    }

//    TODO fix clean code
    suspend fun getAccessTokenByRefresh(token: String): String? {
        val response = authRepository.getAccessToken(token)
        if (!response.isSuccessful){
            return null
        }
        else{
            return response.body()?.accessToken
        }
    }

    suspend fun loadToken(): String?{

        var token: String? = null

        refreshTokenManager.getToken().collect{
            token = it
        }

        return token
    }

}