package com.example.solveitproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class RegisterStudentActivity : AppCompatActivity() {

    var nameTextField: EditText? = null
    var idTextField: EditText? = null
    var emailTextField: EditText? = null
    var passwordTextField: EditText? = null
    var saveButton: Button? = null
    var cancelButton: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_student)

//        setUpUI()
    }


    private fun setUpUI(view: View) {
        nameTextField = view.findViewById(R.id.nameTextField)
        idTextField = view.findViewById(R.id.idTextField)
        emailTextField = view.findViewById(R.id.emailTextField)
        passwordTextField = view.findViewById(R.id.passwordTextField)
        saveButton = view.findViewById(R.id.saveButton)
        cancelButton = view.findViewById(R.id.cancelButton)

    }
}