package com.example.solveitproject.Modules.StudentPost.StudentPostAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.solveitproject.Model.StudentPost
import com.example.solveitproject.Modules.StudentPost.StudentPostRecyclerViewActivity
import com.example.solveitproject.R
class StudentPostRecyclerAdapter(var studentposts: List<StudentPost>?): RecyclerView.Adapter<StudentPostViewHolder>() {

        var listener: StudentPostRecyclerViewActivity.OnItemClickListener? = null

        override fun getItemCount(): Int = studentposts?.size ?: 0

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentPostViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.student_post_lyout_row, parent, false)
            return StudentPostViewHolder(itemView, listener, studentposts)
        }

        override fun onBindViewHolder(holder: StudentPostViewHolder, position: Int) {
            val studentpost = studentposts?.get(position)
            holder.bind(studentpost)
        }
    }


