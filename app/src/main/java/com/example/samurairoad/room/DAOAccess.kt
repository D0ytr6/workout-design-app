package com.example.samurairoad.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.samurairoad.room.tables.ExerciseTableModel
import com.example.samurairoad.room.tables.WorkoutDataTableModel
import com.example.samurairoad.room.tables.WorkoutTableModel

@Dao
interface DAOAccess {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWorkout(workout: WorkoutTableModel)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExercise(exercise: ExerciseTableModel)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWorkoutData(workoutData: WorkoutDataTableModel)

    @Query("SELECT * FROM workout")
    suspend fun getAllWorkouts(): List<WorkoutTableModel>

}