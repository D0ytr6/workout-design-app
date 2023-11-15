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

    @ColumnInfo(name = "color")
    var Color: Int,

    @ColumnInfo(name = "expand")
    var Expand: Boolean,
){

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Long? = null
}
