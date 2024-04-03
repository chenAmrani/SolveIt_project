package com.example.solveitproject.Model


import android.util.Log
import com.example.solveitproject.Model.Student
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
class FirebaseStudentModel {

    private val firestore = FirebaseFirestore.getInstance()
    private val collection = firestore.collection("users") // Adjust "users" to your Firestore collection name

    companion object {
        const val STUDENTS_COLLECTION_PATH = "students"
    }


    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    init {
        val settings: FirebaseFirestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()

        db.firestoreSettings = settings
    }


    fun getStudent(id: String, callback: (Student?) -> Unit) {
        db.collection(STUDENTS_COLLECTION_PATH).document(id).get().addOnCompleteListener {
            when (it.isSuccessful) {
                true -> {
                    val student = it.result.toObject(Student::class.java)
                    callback(student)
                }
                false -> callback(null)
            }
        }
    }

    fun getStudentById(Id: String, callback: (Student?) -> Unit) {
        db.collection(STUDENTS_COLLECTION_PATH).document(Id).get().addOnCompleteListener {
            when (it.isSuccessful) {
                true -> {
                    val student = it.result.toObject(Student::class.java)
                    callback(student)
                }
                false -> callback(null)
            }
        }
    }

    fun getStudentByEmail(email: String, callback: (Student?) -> Unit) {
        db.collection(STUDENTS_COLLECTION_PATH).whereEqualTo("email", email)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val documents = task.result
                    if (documents != null && !documents.isEmpty) {
                        val document = documents.documents[0]
                        val studnet = document.toObject(Student::class.java)
                        Log.i("TAG", "Student id: ${studnet?.id}")
                        callback(studnet)
                    } else {
                        callback(null)
                        Log.i("TAG", "Student id: null2")
                    }
                } else {
                    // Handle failure
                    callback(null)
                }
            }
    }



    fun updateStudent(id:String ,student: Student, callback: () -> Unit) {
        db.collection(STUDENTS_COLLECTION_PATH).document(id).update(student.json).addOnSuccessListener {
            callback()
            Log.i("FirebaseStudentModel", "updateStudent: ${student.id}")
        }.addOnFailureListener { e ->
            // Handle failure, log error, or perform additional actions if needed
            Log.e("FirebaseStudentModel", "Error updating student: ${e.message}", e)
        }
    }

}