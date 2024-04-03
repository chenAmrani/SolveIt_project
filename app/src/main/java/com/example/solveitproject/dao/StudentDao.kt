package com.example.solveitproject.dao


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.solveitproject.Model.Student

@Dao
interface StudentDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insert(vararg student: Student)

        @Delete
        fun delete(student: Student)

        @Query("SELECT * FROM Student WHERE id =:id")
        fun getStudentById(id: String): LiveData<Student>

        @Query("SELECT * FROM Student WHERE email =:email")
        fun getStudentByEmail(email: String): LiveData<Student>

        @Update
        fun update(person: Student)
    }
