package com.example.samurairoad.room.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

//TODO change cascade on ex
@Entity(tableName = "workout_data", foreignKeys = [
    ForeignKey(
        entity = WorkoutTableModel::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("workoutId"),
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = ExerciseTableModel::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("exerciseId"),
        onDelete = ForeignKey.CASCADE
    )
])
data class WorkoutDataTableModel(

    @ColumnInfo(name = "workoutId")
    var WorkoutID: Long,

    @ColumnInfo(name = "exerciseId")
    var ExerciseID: Long,

){

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Long? = null

}
