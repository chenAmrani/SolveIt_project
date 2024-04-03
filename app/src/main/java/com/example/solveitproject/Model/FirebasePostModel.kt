package com.example.solveitproject.Model
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
class FirebasePostModel {

    companion object {
        const val POST_COLLECTION_PATH = "Posts"
    }

    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    init {
        val settings: FirebaseFirestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()

        db.firestoreSettings = settings
    }


    fun getAllPosts(since: Long, callback: (List<Post>) -> Unit) {

        db.collection(POST_COLLECTION_PATH)
            .whereGreaterThanOrEqualTo(Post.LAST_UPDATED, Timestamp(since, 0))
            .get().addOnCompleteListener {
                when (it.isSuccessful) {
                    true -> {
                        val Posts: MutableList<Post> = mutableListOf()
                        Log.i("TAG", "result: ${it.result.size()}")
                        for (json in it.result) {
                            val Post = Post.fromJSON(json.data)
                            Posts.add(Post)
                        }
                        Log.i("TAG", "Posts size: ${Posts. size}")
                        callback(Posts)
                    }
                    false -> callback(listOf())
                }
            }
    }

    fun addPost(Post: Post, callback: () -> Unit) {
        db.collection(POST_COLLECTION_PATH).document(Post.postid).set(Post.Json).addOnSuccessListener {
            callback()
        }
    }



    fun getPostById(postid: String, callback: (Post?) -> Unit) {
        db.collection(POST_COLLECTION_PATH).document(postid).get().addOnSuccessListener { documentSnapshot ->
            val data = documentSnapshot.data
            if (data != null) {
                val Post = Post.fromJSON(data as Map<String, Any>)
                callback(Post)
            } else {
                callback(null)
            }
        }
    }


    fun updatePost(Post: Post, callback: () -> Unit) {
        db.collection(POST_COLLECTION_PATH).document(Post.postid).update(Post.Json).addOnSuccessListener {
            callback()
        }

    }

    fun deletePost(id: String, callback: () -> Unit) {
        db.collection(POST_COLLECTION_PATH).document(id).delete().addOnSuccessListener {
            callback()
        }
    }


}


