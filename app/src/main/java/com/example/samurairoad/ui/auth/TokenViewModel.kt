package com.example.samurairoad.ui.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samurairoad.ui.auth.models.SplashScreenStatus
import com.example.samurairoad.utils.TokenManager
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
    private val tokenManager: TokenManager
): ViewModel(){

//    TODO add shadow field
    val accessTokenLiveData = MutableLiveData<String?>()
    val refreshTokenLiveData = MutableLiveData<String?>()

    val splashScreenStatus = MutableStateFlow(SplashScreenStatus.LOADING)

    init {
        viewModelScope.launch(Dispatchers.IO) {
//            collect token value from flow, update livedata
            tokenManager.getToken().collect {
                withContext(Dispatchers.Main) {
                    refreshTokenLiveData.value = it
                    // token exist
                    if(it != null){
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

    fun saveToken(token: String){
        Log.d("Token", token)
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.saveToken(token)
        }
    }

    fun deleteToken(){
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.deleteToken()
        }
    }

    suspend fun loadToken(): String?{

        var token: String? = null

        tokenManager.getToken().collect{
            token = it
        }

        return token
    }

}