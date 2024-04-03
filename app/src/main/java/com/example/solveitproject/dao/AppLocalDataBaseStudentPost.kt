package com.example.solveitproject.dao



import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.solveitproject.base.MyApplication
import com.example.solveitproject.Model.StudentPost

@Database(entities = [StudentPost::class], version = 5)
abstract class AppLocalDbPPRepository : RoomDatabase() {
    abstract fun StudentPostDao(): StudentPostDao
}
object AppLocalDataBaseStudentPost {

    val db: AppLocalDbPPRepository by lazy {

        val context = MyApplication.Globals.appContext
            ?: throw IllegalStateException("Application context not available")

        Room.databaseBuilder(
            context,
            AppLocalDbPPRepository::class.java,
            "StudentPostFileName.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}






