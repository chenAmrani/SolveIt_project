package com.example.solveitproject.dao

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.solveitproject.Model.Student
import com.example.solveitproject.base.MyApplication

    @Database(entities = [Student::class], version = 5)
    abstract class AppLocalDbStudentRepository : RoomDatabase() {
        abstract fun StudentDao(): StudentDao
    }

    object AppLocalDataBaseStudent {
        val db: AppLocalDbStudentRepository by lazy {

            val context = MyApplication.Globals.appContext
                ?: throw IllegalStateException("Application context not available")

            Room.databaseBuilder(
                context,
                AppLocalDbStudentRepository::class.java,
                "StudentFileName.db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }


