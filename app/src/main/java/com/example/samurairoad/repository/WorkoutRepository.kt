package com.example.samurairoad.repository

import android.content.ClipDescription
import android.content.Context
import androidx.lifecycle.LiveData
import com.example.samurairoad.room.WorkoutDatabase
import com.example.samurairoad.room.tables.WorkoutTableModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class WorkoutRepository {

    companion object{

        var workoutDatabase: WorkoutDatabase? = null

        private fun initDB(context: Context): WorkoutDatabase{
            return WorkoutDatabase.getWorkoutDb(context)
        }

        fun getAllWorkouts(context: Context) : LiveData<List<WorkoutTableModel>>{

            workoutDatabase = initDB(context)
            return workoutDatabase!!.getDao().getAllWorkouts()

        }

        fun insertWorkout(context: Context, title: String, description: String){

            workoutDatabase = initDB(context)

            CoroutineScope(IO).launch {
                val workout: WorkoutTableModel = WorkoutTableModel(title, description)
                workoutDatabase!!.getDao().insertWorkout(workout)

            }

        }

    }

}