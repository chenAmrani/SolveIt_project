package com.example.solveitproject.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.solveitproject.Model.StudentPost

    @Dao
    interface StudentPostDao {

        @Query("SELECT * FROM StudentPost")
        fun getAll(): LiveData<MutableList<StudentPost>>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insert(vararg studentpost: StudentPost)

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertAll(vararg studentpost: StudentPost)

        @Delete
        fun delete(studentpost: StudentPost)

        @Query("SELECT * FROM StudentPost WHERE postid =:id")
        fun getStudentPostById(id: String): LiveData<StudentPost>

        @Update
        fun updateStudentPost(studentpost: StudentPost)

    }
