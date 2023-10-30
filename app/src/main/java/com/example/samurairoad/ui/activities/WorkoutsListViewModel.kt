package com.example.samurairoad.ui.activities

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samurairoad.model.WorkoutState
import com.example.samurairoad.repository.WorkoutRepository
import com.example.samurairoad.room.tables.WorkoutTableModel
import kotlinx.coroutines.launch

class WorkoutsListViewModel : ViewModel() {

    private val _workouts = MutableLiveData<List<WorkoutTableModel>>()
    // fragment only listening, changing only in view model
    val workouts: LiveData<List<WorkoutTableModel>> = _workouts

    // TODO remake this method, hard code
    fun getWorkouts(context: Context){
        viewModelScope.launch {
            _workouts.value = WorkoutRepository.getAllWorkouts(context)
            Log.d("MyTag", "ViewModel " + _workouts.value?.size.toString())
        }
    }

    fun insertWorkout(context: Context, title: String, description: String){
        viewModelScope.launch {
            WorkoutRepository.insertWorkout(context, title, description)
            getWorkouts(context)
        }
    }

    fun insertExercise(context: Context, title: String, description: String,
                       sets:Int, reps: Int, weight: Int){
        viewModelScope.launch {
            WorkoutRepository.insertExercise(context, title, description, sets, reps, weight)
        }
    }

    private fun imitationData(){
        val testlist = mutableListOf<WorkoutTableModel>()
        for(i in 1..5){
            if(i == 3){
                val workout = WorkoutTableModel("Test$i", "Testing.. Send data, checkpoint")
                testlist.add(workout)
            }
            else {
                val workout = WorkoutTableModel("Test$i", "Testing..")
                testlist.add(workout)
            }
        }
        _workouts.value = testlist
    }

//    init {
//        loadData()
//    }
//
//    private fun loadData(){
//        WorkoutRepository.getAllWorkouts()
//    }
}