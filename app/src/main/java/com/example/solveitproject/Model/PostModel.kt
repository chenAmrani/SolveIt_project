package com.example.solveitproject.Models

import android.os.Looper
import android.util.Log
import androidx.core.os.HandlerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.solveitproject.Model.Post
import com.example.solveitproject.dao.AppLocalDataBasePost
import java.util.concurrent.Executors


class PostModel private constructor() {

    enum class LoadingState {
        LOADING,
        LOADED
    }

    private val database = AppLocalDataBasePost.db
    private var executor = Executors.newSingleThreadExecutor()
    private var executor2 = Executors.newSingleThreadExecutor()
    private var executor3 = Executors.newSingleThreadExecutor()
    private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val FirebasePostModel = FirebasePostModel()
    private val allPosts: LiveData<MutableList<Post>> =database.PostDao().getAll()
    val postsListLoadingState: MutableLiveData<LoadingState> = MutableLiveData(LoadingState.LOADED)
    companion object {
        val instance: PostModel = PostModel()
    }

    interface GetAllStudentListener {
        fun onComplete(Post: List<Post>)
    }

    fun getAllPosts(): LiveData<MutableList<Post>> {
        refreshAllPosts()
        return allPosts
    }

    fun refreshAllPosts() {

        postsListLoadingState.value = LoadingState.LOADING

        // 1. Get last updated time from local data
        val lastUpdated: Long = Post.lastUpdated

        // 2. Get all updated records from firestore since last update locally
        FirebasePostModel.getAllPosts(lastUpdated) { list ->
            Log.i("TAG", "Firebase returned ${list.size}, lastUpdated: $lastUpdated")
            // 3. Insert new record to ROOM
            executor.execute {
                var time = lastUpdated
                for (Post in list) {
                    database.PostDao().insert(Post)
                    Log.i("TAG", "PostDao(): ${database.PostDao().getAll().value.toString()}")

//                    Post.lastUpdated?.let {
//                        if (time < it)
//                            time = Post.lastUpdated ?: System.currentTimeMillis()
//                    }
                }

                // 4. Update local data
                Post.lastUpdated = time
                postsListLoadingState.postValue(LoadingState.LOADED)
            }
        }
    }

    fun addPost(post: Post, callback: () -> Unit) {
        FirebasePostModel.addPost(Post) {
//            FirebaseStudentPostModel()
            callback()
        }
    }
    fun getPostById(id: String, callback: (Post?) -> Unit) : LiveData<Post>{
        FirebasePostModel.getPostById(id) {
//            callback(it)
        }
        return database.PostDao().getPostById(id)
    }

    fun updatePost(Post: Post, callback: () -> Unit) {
        FirebasePostModel.updatePost(Post) {
            executor2.execute {
                database.PostDao().updatePost(Post)
            }
            callback()
        }
    }

    fun deletePost(post: Post, callback: () -> Unit) {
        FirebasePostModel.deletePost(post.postid) {
            executor3.execute {
                database.PostDao().delete(post)
            }
            callback()
        }
    }
}