package com.example.samurairoad.repository

import android.content.ClipDescription
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.samurairoad.room.WorkoutDatabase
import com.example.samurairoad.room.tables.ExerciseTableModel
import com.example.samurairoad.room.tables.WorkoutDataTableModel
import com.example.samurairoad.room.tables.WorkoutTableModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class WorkoutRepository {

    companion object{

        var workoutDatabase: WorkoutDatabase? = null

        private fun initDB(context: Context): WorkoutDatabase{
            return WorkoutDatabase.getWorkoutDb(context)
        }

        suspend fun getAllWorkouts(context: Context) : List<WorkoutTableModel>?{

            workoutDatabase = initDB(context)
            var workouts: List<WorkoutTableModel>? = null

                workouts = workoutDatabase!!.getDao().getAllWorkouts()
                Log.d("MyTag", "WorkoutRepository " + workouts!!.size.toString())
                logCurrentThread()

            return workouts

        }

        suspend fun insertWorkout(context: Context, title: String, description: String){

            workoutDatabase = initDB(context)

            val workout: WorkoutTableModel = WorkoutTableModel(title, description)
            workoutDatabase!!.getDao().insertWorkout(workout)

        }

        suspend fun insertExercise(context: Context, title: String, description: String, sets: Int, reps: Int, weight: Int){

            workoutDatabase = initDB(context)

            val exercise: ExerciseTableModel = ExerciseTableModel(title, description, sets, reps, weight)
            workoutDatabase!!.getDao().insertExercise(exercise)

        }

        fun insertWorkoutData(context: Context, workout_name: WorkoutTableModel, exercise: ExerciseTableModel){

            workoutDatabase = initDB(context)

            CoroutineScope(IO).launch {
                val workoutData: WorkoutDataTableModel = WorkoutDataTableModel(workout_name.Id!!, exercise.Id!!)
                workoutDatabase!!.getDao().insertWorkoutData(workoutData)
            }
        }

        private fun logCurrentThread(){
            Log.d("MyTag", "Current Thread " + Thread.currentThread().name)
        }

    }

}