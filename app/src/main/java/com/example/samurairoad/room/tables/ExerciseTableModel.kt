package com.example.samurairoad.room.tables

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise")
data class ExerciseTableModel(

    @ColumnInfo(name = "title")
    var Title: String,

    @ColumnInfo(name = "description")
    var Description: String,

    @ColumnInfo(name = "sets")
    var SetsCount: Int,

    @ColumnInfo(name = "reps")
    var RepsCount: Int,

    @ColumnInfo(name = "weight")
    var weightValue: Int,

    @ColumnInfo(name = "exercise_photo")
    var exercisePhoto: Bitmap
    // Todo image blob

){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Long? = null
}
