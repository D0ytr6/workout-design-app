package com.example.samurairoad.room.tables
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout")
data class WorkoutTableModel(

    @ColumnInfo(name = "title")
    var Title: String,

    @ColumnInfo(name = "description")
    var Description: String,
){

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int? = null
}
