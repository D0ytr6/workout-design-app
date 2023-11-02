package com.example.samurairoad.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.samurairoad.room.tables.ExerciseTableModel
import com.example.samurairoad.room.tables.WorkoutDataTableModel
import com.example.samurairoad.room.tables.WorkoutTableModel

@Dao
interface DAOAccess {

    // TODO make unique workout name
    // TODO unique exercises only inside a workout4

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWorkout(workout: WorkoutTableModel)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExercise(exercise: ExerciseTableModel): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWorkoutData(workoutData: WorkoutDataTableModel)

    @Query("SELECT * FROM workout")
    suspend fun getAllWorkouts(): List<WorkoutTableModel>

    @Query("SELECT * FROM exercise")
    suspend fun getAllExercises(): List<ExerciseTableModel>

    @Query("SELECT * FROM exercise WHERE id =:exerciseID")
    suspend fun getExercisesById(exerciseID: Long): ExerciseTableModel

    @Query("SELECT * FROM workout WHERE title LIKE :titleName LIMIT 1")
    suspend fun getWorkoutByName(titleName: String): WorkoutTableModel

    @Query("SELECT * FROM workout WHERE id LIKE :id LIMIT 1")
    suspend fun getWorkoutById(id: Long): WorkoutTableModel

    @Query("SELECT exerciseId FROM workout_data WHERE workoutId LIKE :workout_id")
    suspend fun getExercisesIdFromWorkouts(workout_id: Long): List<Long>

    @Delete
    suspend fun deleteWorkout(workout: WorkoutTableModel)

    @Delete
    suspend fun deleteExercise(exercise: ExerciseTableModel)

}