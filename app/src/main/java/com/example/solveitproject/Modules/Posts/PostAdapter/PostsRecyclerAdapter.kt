package com.example.solveitproject.Modules.Posts.PostAdapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.solveitproject.Model.Student
import com.example.solveitproject.Model.StudentPost
import com.example.solveitproject.Modules.Posts.PostsRecyclerViewActivity
import com.example.solveitproject.R

class PostsRecyclerAdapter {

    class PostsRecyclerAdapter(var posts: List<StudentPost>?): RecyclerView.Adapter<PostViewHolder>() {

        var listener: PostsRecyclerViewActivity.OnItemClickListener? = null

        override fun getItemCount(): Int = posts?.size ?: 0

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.post_layout_row, parent, false)
            return PostViewHolder(itemView, listener, posts)
        }

        override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
            val post = posts?.get(position)
            holder.bind(post)
        }
    }

}