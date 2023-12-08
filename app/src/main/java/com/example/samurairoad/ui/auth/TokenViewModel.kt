package com.example.samurairoad.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samurairoad.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    val tokenLiveData = MutableLiveData<String?>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
//            collect token value from flow, update livedata
            tokenManager.getToken().collect {
//                TODO ?? withContext
                withContext(Dispatchers.Main) {
                    tokenLiveData.value = it
                }
            }
        }
    }

    fun saveToken(token: String){
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.saveToken(token)
        }
    }

    fun deleteToken(){
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.deleteToken()
        }
    }

}