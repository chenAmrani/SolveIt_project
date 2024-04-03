package com.example.solveitproject.dao

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.solveitproject.Model.Student
import com.example.solveitproject.base.MyApplication


@Database(entities = [Student::class], version = 1)
  abstract class AppLocalDbRepository : RoomDatabase() {
      abstract fun PostDao(): PostDao
  }

object AppLocalDataBasePost {
val db: AppLocalDbRepository by lazy {

    val context = MyApplication.Globals.appContext
        ?:throw IllegalStateException("Application context not available")

    Room.databaseBuilder(
        context,
        AppLocalDbRepository::class.java,
        "dbFileName.db"
    )
        .fallbackToDestructiveMigration()
        .build()
    }
}