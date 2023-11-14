package com.example.samurairoad.ui.activities

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samurairoad.adapters.models.Exercise
import com.example.samurairoad.adapters.models.FullExercise
import com.example.samurairoad.adapters.models.Workout
import com.example.samurairoad.repository.WorkoutRepository
import com.example.samurairoad.room.tables.ExerciseTableModel
import com.example.samurairoad.room.tables.WorkoutTableModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

//TODO null check fix
//TODO add view model lifecycle logs
//TODO write unit tests

class WorkoutsListViewModel : ViewModel() {

    // shadow fields
    private val _workouts = MutableLiveData<List<WorkoutTableModel>>()
    private val _exercises = MutableLiveData<List<ExerciseTableModel>>()
    private val _workoutAdapterList = MutableLiveData<List<Workout>>()
    private val _exercisesFullAdapterList = MutableLiveData<List<FullExercise>>()

    // fragment only listening, changing only in view model
    val workouts: LiveData<List<WorkoutTableModel>> = _workouts
    val exercises: LiveData<List<ExerciseTableModel>> = _exercises
    val workoutAdapterList: LiveData<List<Workout>> get() = _workoutAdapterList
    val exercisesFullAdapterList: LiveData<List<FullExercise>> get() = _exercisesFullAdapterList

    // TODO remake this method, hard code
    fun getAllWorkouts(context: Context){
        Log.d("MyTag", "get All Workouts")
        viewModelScope.launch {
            _workouts.value = WorkoutRepository.getAllWorkouts(context)
            Log.d("MyTag", "ViewModel 1 " + _workouts.value?.size.toString())
            val workoutAdapterList = mutableListOf<Workout>()

            for(workout in workouts.value!!){
                // Creating adapter model
                val workoutAdapter = getWorkoutAdapter(context, workout.Id!!)
                workoutAdapterList.add(workoutAdapter)
            }
            // update live data
            _workoutAdapterList.value = workoutAdapterList

            Log.d("MyTag", "ViewModel " + _workouts.value?.size.toString())
        }
    }

    fun getAllExercises (context: Context){
        viewModelScope.launch {
            _exercises.value = WorkoutRepository.getAllExercises(context)
            Log.d("MyTag", "ViewModel " + _exercises.value?.size.toString())
        }
    }

    private suspend fun getWorkoutByName(context: Context, title: String): WorkoutTableModel{
        return WorkoutRepository.getWorkoutByName(context, title)
    }

    //TODO
    suspend fun getExercisesIdFromWorkouts(context: Context, workoutID: Long): List<Long>{
        return WorkoutRepository.getExercisesIdFromWorkouts(context, workoutID)
    }

    fun insertWorkoutData(context: Context, workoutID: Long, exerciseID: Long){
        WorkoutRepository.insertWorkoutData(context, workoutID, exerciseID)
    }

    fun insertWorkout(context: Context, title: String, description: String, color: Int){
        Log.d("MyTag", "Inset new value from view model")
        viewModelScope.launch {
            WorkoutRepository.insertWorkout(context, title, description, color)

            // update adapter live data list
            getAllWorkouts(context)
        }
    }

    fun insertExercise(context: Context, title: String, description: String,
                       sets:Int, reps: Int, weight: Int, bitmapImg: Bitmap, workoutSelect: String){
        viewModelScope.launch {
            // TODO check if exist
            val id = WorkoutRepository.insertExercise(context, title, description, sets, reps, weight, bitmapImg)
            Log.d(WorkoutRepository.MyTag, id.toString())
            val selectedWorkout = getWorkoutByName(context, workoutSelect)
            Log.d(WorkoutRepository.MyTag, selectedWorkout.Title)
            WorkoutRepository.insertWorkoutData(context, selectedWorkout.Id!!, id)

        }
    }

    fun deleteWorkout(context: Context, workoutID: Long){

        viewModelScope.launch {
            val workout = WorkoutRepository.getWorkoutByID(context, workoutID)
            WorkoutRepository.deleteWorkout(context, workout)

            // update adapter live data list
            getAllWorkouts(context)
        }

    }

    private suspend fun getWorkoutAdapter(context: Context, workoutID: Long): Workout {

        //all exercises which attached to this workout
        val listExID = WorkoutRepository.getExercisesIdFromWorkouts(context, workoutID)

        // current workout
        val currentWorkout = WorkoutRepository.getWorkoutByID(context, workoutID)

        val listEx = mutableListOf<ExerciseTableModel>()
        val listExAdapter = mutableListOf<Exercise>()

        // get all exercise model from db
        for (exerciseID in listExID) {
            Log.d(WorkoutRepository.MyTag, "exerciseID $exerciseID")
            val exercise = WorkoutRepository.getExerciseByID(context, exerciseID)
            Log.d(WorkoutRepository.MyTag, "exercise title ${exercise.Id}")
            listEx.add(exercise)
        }

        for ((index, value) in listEx.withIndex()) {
            Log.d(WorkoutRepository.MyTag, value.Title)
            listExAdapter.add(Exercise(value.Title, index + 1))
        }

        return Workout(currentWorkout.Title, currentWorkout.Description, currentWorkout.Color, listExAdapter, workoutID)

    }

    private suspend fun getExercisesFromWorkout(context: Context, workoutID: Long){

        val listExID = WorkoutRepository.getExercisesIdFromWorkouts(context, workoutID)
        val listEx = mutableListOf<ExerciseTableModel>()
        val exercisesAdapterList = mutableListOf<FullExercise>()

        for (exerciseID in listExID) {
            Log.d(WorkoutRepository.MyTag, "exerciseID $exerciseID")
            val exercise = WorkoutRepository.getExerciseByID(context, exerciseID)
            Log.d(WorkoutRepository.MyTag, "exercise title ${exercise.Id}")
            listEx.add(exercise)
        }

        for ((index, value) in listEx.withIndex()) {
            Log.d(WorkoutRepository.MyTag, value.Title)
            exercisesAdapterList.add(FullExercise(
                value.Title, value.Description, value.SetsCount,
                value.RepsCount, value.weightValue, value.exercisePhoto))
        }
        _exercisesFullAdapterList.value = exercisesAdapterList
    }

    fun loadExercises(context: Context, workoutID: Long){
        viewModelScope.launch {
            getExercisesFromWorkout(context, workoutID)
        }
    }

    private fun imitationData(){
        val testlist = mutableListOf<WorkoutTableModel>()
        for(i in 1..5){
            if(i == 3){
                val workout = WorkoutTableModel("Test$i", "Testing.. Send data, checkpoint", 1)
                testlist.add(workout)
            }
            else {
                val workout = WorkoutTableModel("Test$i", "Testing..", 5)
                testlist.add(workout)
            }
        }
        _workouts.value = testlist
    }


    fun getWorkout(context: Context, id: Long) : Deferred<WorkoutTableModel> = viewModelScope.async {
        WorkoutRepository.getWorkoutByID(context, id)
    }

    fun getWorkoutName(context: Context, name: String) : Deferred<WorkoutTableModel> = viewModelScope.async {
        WorkoutRepository.getWorkoutByName(context, name)
    }



//    init {
//        loadData()
//    }
//
//    private fun loadData(){
//        WorkoutRepository.getAllWorkouts()
//    }
}