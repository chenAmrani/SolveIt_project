package com.example.solveitproject.Model

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class FirebaseStudentPostModel {


    companion object {
        const val STUDENTPOST_COLLECTION_PATH = "studentPosts"
        const val POST_COLLECTION_PATH = "posts"
    }
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    init {
        val settings: FirebaseFirestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()

        db.firestoreSettings = settings
    }


    fun getAllStudentPosts(since: Long,publisher: String, callback: (List<StudentPost>) -> Unit){
        Log.i("getAllStudentPosts", "getAllStudentPosts -publisher ${publisher}")
        db.collection(STUDENTPOST_COLLECTION_PATH)
            .whereGreaterThanOrEqualTo(StudentPost.LAST_UPDATED, Timestamp(since, 0))
            .get().addOnCompleteListener {
                when (it.isSuccessful) {
                    true -> {
                        val studentPosts: MutableList<StudentPost> = mutableListOf()
                        for (json in it.result) {
                            val studentPost = StudentPost.fromJSON(json.data)
                            if (studentPost != null) {
                                studentPosts.add(studentPost)
                            }
                        }
                        callback(studentPosts)
                    }
                    false -> callback(listOf())
                }
            }
        }

    fun addStudentPost(student_post: StudentPost, callback: () -> Unit) {
        db.collection(STUDENTPOST_COLLECTION_PATH).document(student_post.postid).set(student_post.json).addOnSuccessListener {
            callback()
        }
    }


    fun getStudentPostById(postid: String, callback: (StudentPost?) -> Unit) {
        db.collection(STUDENTPOST_COLLECTION_PATH).document(postid).get().addOnSuccessListener { documentSnapshot ->
            val data = documentSnapshot.data
            if (data != null) {
                val studentPost = StudentPost.fromJSON(data as Map<String, Any>)
                callback(studentPost)
            } else {
                callback(null)
            }
        }
    }

    fun updateStudentPost(studentpost: StudentPost, callback: () -> Unit) {
        db.collection(STUDENTPOST_COLLECTION_PATH).document(studentpost.postid).update(studentpost.json).addOnSuccessListener {
            callback()
        }
    }


    fun deleteStudentPost(id: String, callback: () -> Unit) {
        db.collection(STUDENTPOST_COLLECTION_PATH).document(id).delete().addOnSuccessListener {
            callback()
        }
    }
}
