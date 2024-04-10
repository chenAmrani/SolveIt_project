package com.example.solveitproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.solveitproject.Model.Student


class StudentViewModel : ViewModel(){
    var student: LiveData<Student>? = null
}