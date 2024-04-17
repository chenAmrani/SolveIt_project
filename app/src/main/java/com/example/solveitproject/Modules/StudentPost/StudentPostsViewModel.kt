package com.example.solveitproject.Modules.StudentPost

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.solveitproject.Model.StudentPost

class StudentPostsViewModel : ViewModel() {

    var studentposts: LiveData<MutableList<StudentPost>>? = null

    fun addAllStudentPosts(newPosts: List<StudentPost>){
        studentposts?.value?.addAll(newPosts)
    }
}

