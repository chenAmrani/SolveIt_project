package com.example.solveitproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.solveitproject.Modules.RegisterFragment

class MainActivity : AppCompatActivity() {

    private var emailTextField: EditText? = null
    private var passwordTextField: EditText? = null
    private var signInButton: Button? = null
    var registerButton: Button? = null
    var view: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val registerStudentButton = findViewById<Button>(R.id.BtnRegisterStudent)
        registerButton?.setOnClickListener {
            onRegisterButton(RegisterFragment())
        }
    }

    private fun onRegisterButton(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.BtnRegisterStudent, fragment)
            .addToBackStack(null)
            .commit()
    }





    private fun setUpUI(view : View) {
        emailTextField = view.findViewById(R.id.SignInTextEmailAddress)
        passwordTextField = view.findViewById(R.id.SignInTextPassword)
        signInButton = view.findViewById(R.id.BtnSignIn)


        signInButton?.setOnClickListener {
            val email = emailTextField?.text.toString().trim()
            val password  = passwordTextField?.text.toString().trim()

//            if(email.isEmpty() || password.isEmpty()){
//
//            }
        }


    }
}