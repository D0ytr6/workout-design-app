package com.example.samurairoad.room.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise")
data class ExerciseTableModel(

    @ColumnInfo(name = "title")
    var Title: String,

    @ColumnInfo(name = "description")
    var Description: String,

    @ColumnInfo(name = "circle_count")
    var CircleCount: Int,

    @ColumnInfo(name = "repetitions_count")
    var RepetitionsCount: Int,

    // Todo image blob

){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int? = null
}
