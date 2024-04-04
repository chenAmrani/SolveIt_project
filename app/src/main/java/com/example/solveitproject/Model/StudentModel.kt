package com.example.solveitproject.Model

import android.os.Looper
import androidx.core.os.HandlerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.solveitproject.dao.AppLocalDataBaseStudent
import java.util.concurrent.Executors


    class StudentModel private constructor() {
        enum class LoadingState {
            LOADING,
            LOADED
        }

        private val database = AppLocalDataBaseStudent.db
        private var executor = Executors.newSingleThreadExecutor()
        private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
        private val firebaseStudentModel = FirebaseStudentModel()
        private var studentLiveData: LiveData<Student>? = null
        val studentLoadingState: MutableLiveData<LoadingState> = MutableLiveData(LoadingState.LOADED)

        companion object {
            val instance: StudentModel = StudentModel()
        }

        fun getStudent(id: String, callback: (Student?) -> Unit) {
            firebaseStudentModel.getStudent(id) {
                callback(it)
            }
        }



        fun getStudentByEmail(email: String, callback: (Student?) -> Unit): LiveData<Student> {
            firebaseStudentModel.getStudentByEmail(email) {
                callback(it)
            }
            studentLiveData = database.StudentDao().getStudentByEmail(email)
            return studentLiveData!!
        }


        fun updateStudent(id:String,student: Student, callback: () -> Unit) {
            firebaseStudentModel.updateStudent(id, student) {
                executor.execute {
                    database.StudentDao().update(student)
//                    refreshStudent(student.id) {
                        callback()
                        //}
                    }
                }
            }
        }

