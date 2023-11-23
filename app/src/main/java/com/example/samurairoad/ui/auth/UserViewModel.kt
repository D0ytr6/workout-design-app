package com.example.samurairoad.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.samurairoad.repository.WorkoutRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO create auth view model
class UserViewModel : ViewModel() {

    val isAuth = MutableLiveData(false)

    fun getAllUsers(api: WorkoutApi){
        CoroutineScope(Dispatchers.IO).launch {
            val users = WorkoutRepository.getUsersTest(api)
            Log.d("Retrofit", users.toString())
        }
    }

    fun createUser(api: WorkoutApi, userModel: RegisterUserModel){
        CoroutineScope(Dispatchers.IO).launch {
            WorkoutRepository.createUser(api, userModel)
        }
    }
}