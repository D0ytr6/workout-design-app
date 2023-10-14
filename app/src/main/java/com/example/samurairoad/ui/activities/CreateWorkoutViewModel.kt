package com.example.samurairoad.ui.activities

import android.content.ClipDescription
import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.samurairoad.repository.WorkoutRepository

class CreateWorkoutViewModel : ViewModel() {

    fun insertWorkout(context: Context, title: String, description: String){
        WorkoutRepository.insertWorkout(context, title, description)
    }

}