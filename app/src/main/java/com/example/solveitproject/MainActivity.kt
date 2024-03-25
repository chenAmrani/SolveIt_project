package com.example.solveitproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("DEMO3" , "onCreate")
    }

    override fun onStart() {
        super.onStart()

        Log.d("DEMO3" , "onStart")

    }

    override fun onResume() {
        super.onResume()

        Log.d("DEMO3" , "onResume")
    }

    override fun onPause() {
        super.onPause()

        Log.d("DEMO3" , "onPause")
    }

    override fun onStop() {
        super.onStop()

        Log.d("DEMO3" , "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d("DEMO3" , "onDestroy")
    }
}