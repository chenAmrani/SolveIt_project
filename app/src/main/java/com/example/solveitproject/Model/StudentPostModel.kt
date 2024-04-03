package com.example.solveitproject.Models

import android.os.Looper
import android.util.Log
import androidx.core.os.HandlerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.solveitproject.Model.FirebaseStudentPostModel
import com.example.solveitproject.Model.StudentPost
import com.example.solveitproject.dao.AppLocalDataBaseStudentPost
import java.util.concurrent.Executors



class StudentPostModel private constructor() {

    enum class LoadingState {
        LOADING,
        LOADED
    }

    private val database = AppLocalDataBaseStudentPost.db
    private var executor = Executors.newSingleThreadExecutor()
    private var executor2 = Executors.newSingleThreadExecutor()
    private var executor3 = Executors.newSingleThreadExecutor()
    private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val FirebaseStudentPostModel = FirebaseStudentPostModel()
    private val studentPosts: LiveData<MutableList<StudentPost>> = database.StudentPostDao().getAll()
    val studentPostsListLoadingState: MutableLiveData<LoadingState> = MutableLiveData(LoadingState.LOADED)
    companion object {
        val instance: StudentPostModel = StudentPostModel()
    }

    interface GetAllStudentListener {
        fun onComplete(studentPosts: List<StudentPost>)
    }

    fun getAllstudentPosts(publisher: String): LiveData<MutableList<StudentPost>> {
        refreshAllstudentPosts(publisher)
        return studentPosts
    }


    fun refreshAllstudentPosts(publisher: String)  {

        studentPostsListLoadingState.value = LoadingState.LOADING

        // 1. Get last local update
        val lastUpdated: Long = StudentPost.lastUpdated

        // 2. Get all updated records from firestore since last update locally
        FirebaseStudentPostModel.getAllStudentPosts(lastUpdated, publisher) { list ->
            Log.i("TAG", "Firebase Student Post returned ${list.size}, lastUpdated: $lastUpdated")
            // 3. Insert new record to ROOM
            executor.execute {
                var time = lastUpdated
                for (studentPost in list) {
                    database.StudentPostDao().insert(studentPost)


                    Log.i("TAG", "database.StudentPostsDao() ${database.StudentPostDao().getAll().value?.size}")

                    studentPost.lastUpdated?.let {
                        if (time < it)
                            time = studentPost.lastUpdated ?: System.currentTimeMillis()
                    }
                }

                // 4. Update local data
                StudentPost.lastUpdated = time

                studentPostsListLoadingState.postValue(LoadingState.LOADED)
            }
        }
    }

    fun addStudentPost(publisher: String, studentPost: StudentPost, callback: () -> Unit) {
        FirebaseStudentPostModel.addStudentPost(studentPost) {
            refreshAllstudentPosts(publisher)
            callback()
        }
    }

    fun getStudentPostById(id: String, callback: (StudentPost?) -> Unit) : LiveData<StudentPost>{
        FirebaseStudentPostModel.getStudentPostById(id) {
            callback(it)
        }
        return database.StudentPostDao().getStudentPostById(id)
    }

    fun updateStudentPost(studentPost: StudentPost, callback: () -> Unit) {
        FirebaseStudentPostModel.updateStudentPost(studentPost) {
            executor2.execute {
                database.StudentPostDao().updateStudentPost(studentPost)
            }
            callback()
        }
    }

    fun deleteStudentPost(studentPost: StudentPost, callback: () -> Unit) {
        FirebaseStudentPostModel.deleteStudentPost(studentPost.postid) {
            executor3.execute {
                database.StudentPostDao().delete(studentPost)
            }
            callback()
        }
    }
}