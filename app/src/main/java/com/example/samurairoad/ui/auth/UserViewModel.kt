package com.example.samurairoad.ui.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.samurairoad.repository.WorkoutRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//// TODO create auth view model
//class UserViewModel : ViewModel() {
//
//    val isAuth = MutableLiveData(false)
//    val token = MutableLiveData<String>()
//
//    fun getAllUsers(api: WorkoutApiService){
//        CoroutineScope(Dispatchers.IO).launch {
//            val users = WorkoutRepository.getUsersTest(api)
//            Log.d("Retrofit", users.toString())
//        }
//    }
//
//// Todo check if not error
//    fun createUser(api: WorkoutApiService, userModel: RegisterUserModel){
//        CoroutineScope(Dispatchers.IO).launch {
////          TODO will return a token auth
//            val user = WorkoutRepository.createUser(api, userModel)
//            token.postValue(user.token)
//            Log.d("Retrofit", user.toString())
//        }
//    }
//}