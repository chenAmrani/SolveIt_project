package com.example.solveitproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val registerStudentButton = findViewById<Button>(R.id.BtnRegisterStudent)
        registerStudentButton.setOnClickListener(::onRegisterStudentButton)

    }

    fun onRegisterStudentButton(view: View){
        val intent = Intent(this, RegisterStudentActivity::class.java)
        startActivity(intent)
    }

}