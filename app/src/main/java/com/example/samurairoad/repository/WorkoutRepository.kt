package com.example.samurairoad.repository

import android.content.ClipDescription
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import com.example.samurairoad.repository.WorkoutRepository.Companion.workoutDatabase
import com.example.samurairoad.room.WorkoutDatabase
import com.example.samurairoad.room.tables.ExerciseTableModel
import com.example.samurairoad.room.tables.WorkoutDataTableModel
import com.example.samurairoad.room.tables.WorkoutTableModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.FileDescriptor
import java.io.IOException

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

        suspend fun getAllExercises(context: Context): List<ExerciseTableModel>{

            workoutDatabase = initDB(context)

            var exercises: List<ExerciseTableModel>? = null

            exercises = workoutDatabase!!.getDao().getAllExercises()
            Log.d("MyTag", "WorkoutRepository " + exercises!!.size.toString())
            logCurrentThread()

            return exercises
        }

        suspend fun getExerciseByID(context: Context, id: Long): ExerciseTableModel{
            workoutDatabase = initDB(context)
            return workoutDatabase!!.getDao().getExercisesById(id)
        }

        suspend fun getWorkoutByID(context: Context, id: Long): WorkoutTableModel{
            workoutDatabase = initDB(context)
            return workoutDatabase!!.getDao().getWorkoutById(id)
        }


        // TODO return value can be a null
        suspend fun getWorkoutByName(context: Context, title: String): WorkoutTableModel {
            workoutDatabase = initDB(context)
            return workoutDatabase!!.getDao().getWorkoutByName(title)
        }

        suspend fun getExercisesIdFromWorkouts(context: Context, workout_id: Long): List<Long>{
            workoutDatabase = initDB(context)
            val list = workoutDatabase!!.getDao().getExercisesIdFromWorkouts(workout_id)
            return list

        }

        suspend fun insertWorkout(context: Context, title: String, description: String, color: Int){

            workoutDatabase = initDB(context)

            val workout: WorkoutTableModel = WorkoutTableModel(title, description, color)
            workoutDatabase!!.getDao().insertWorkout(workout)

        }

        suspend fun insertExercise(context: Context, title: String, description: String, sets: Int, reps: Int, weight: Int, bitmapImg: Bitmap): Long{

            workoutDatabase = initDB(context)

            val exercise: ExerciseTableModel = ExerciseTableModel(title, description, sets, reps, weight, bitmapImg)
            val id = workoutDatabase!!.getDao().insertExercise(exercise)
            return id

        }

//        fun insertWorkoutData(context: Context, workoutTitle: String, workoutDescription: String,
//                              exerciseTitle: String, exerciseDescription: String, sets: Int, reps: Int, weight: Int, bitmapImg: Bitmap)

        fun insertWorkoutData(context: Context, workoutTableModelID: Long, exerciseTableModelID: Long){

            workoutDatabase = initDB(context)

            CoroutineScope(IO).launch {
                val workoutData: WorkoutDataTableModel = WorkoutDataTableModel(workoutTableModelID, exerciseTableModelID)
                workoutDatabase!!.getDao().insertWorkoutData(workoutData)
            }
        }


        private fun logCurrentThread(){
            Log.d("MyTag", "Current Thread " + Thread.currentThread().name)
        }

        // TODO change location of method to clean arch
        fun uriToBitmap(context: Context, selectedFileUri: Uri): Bitmap? {
            try {
                val parcelFileDescriptor = context.contentResolver.openFileDescriptor(selectedFileUri, "r")
                val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
                val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
                parcelFileDescriptor.close()
                return image
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }

        const val MyTag = "MyTag"
        const val LifecycleTag = "LifeCycleTag"

    }

}