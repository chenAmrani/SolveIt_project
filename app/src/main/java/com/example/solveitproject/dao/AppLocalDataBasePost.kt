package com.example.solveitproject.dao

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.solveitproject.Model.Post
import com.example.solveitproject.Model.Student
import com.example.solveitproject.base.MyApplication


@Database(entities = [Post::class], version = 3)
  abstract class AppLocalDbPostRepository : RoomDatabase() {
      abstract fun PostDao(): PostDao
  }


object AppLocalDataBasePost {
val db: AppLocalDbPostRepository by lazy {

    val context = MyApplication.Globals.appContext
        ?:throw IllegalStateException("Application context not available")

    Room.databaseBuilder(
        context,
        AppLocalDbPostRepository::class.java,
        "PostFileName.db"
    )
        .fallbackToDestructiveMigration()
        .build()
    }
}