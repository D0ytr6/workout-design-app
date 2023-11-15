package com.example.samurairoad.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.samurairoad.model.Converters
import com.example.samurairoad.room.tables.ExerciseTableModel
import com.example.samurairoad.room.tables.WorkoutDataTableModel
import com.example.samurairoad.room.tables.WorkoutTableModel

@Database(entities = [WorkoutTableModel::class, ExerciseTableModel::class, WorkoutDataTableModel::class], version = 5, exportSchema = false)
@TypeConverters(Converters::class)
abstract class WorkoutDatabase : RoomDatabase() {

    abstract fun getDao(): DAOAccess

    companion object{

        // singleton object
        private var INSTANCE: WorkoutDatabase? = null

        fun getWorkoutDb(context: Context) : WorkoutDatabase{

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {
                INSTANCE = Room.databaseBuilder(context, WorkoutDatabase::class.java, "workouts.db").fallbackToDestructiveMigration().build()
                return INSTANCE!!
            }

        }

    }

}