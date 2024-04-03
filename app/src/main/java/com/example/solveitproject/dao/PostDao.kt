package com.example.solveitproject.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.solveitproject.Model.Post

@Dao
interface PostDao {

    @Query("SELECT * FROM Post")
    fun getAll(): LiveData<MutableList<Post>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg post: Post)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg post: Post)

    @Delete
    fun delete(post: Post)

    @Query("SELECT * FROM Post WHERE postid =:id")
    infix fun getPostById(id: String):  LiveData<Post>

    @Update
    fun updatePost(post: Post)


}