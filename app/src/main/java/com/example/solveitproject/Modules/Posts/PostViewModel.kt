package com.example.solveitproject.Modules.Posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.solveitproject.Model.StudentPost


    class PostViewModel: ViewModel() {
        var posts: LiveData<MutableList<StudentPost>>? = null
    }


