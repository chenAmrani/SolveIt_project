package com.example.solveitproject.Modules.Posts

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.solveitproject.Model.StudentPost
import com.example.solveitproject.Modules.Posts.PostAdapter.PostsRecyclerAdapter


class PostsRecyclerViewActivity : AppCompatActivity() {



        var PostsRcyclerView: RecyclerView? = null
        var posts: List<StudentPost>? = null
        var adapter: PostsRecyclerAdapter? = null


//        private lateinit var binding: ActivityPostRcyclerViewBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

//            binding = ActivityPostRcyclerViewBinding.inflate(layoutInflater)
//            setContentView(binding.root)



//            PostsRcyclerView = binding.rvGeneralPostRecyclerList
            PostsRcyclerView?.setHasFixedSize(true)
            PostsRcyclerView?.layoutManager = LinearLayoutManager(this)

            adapter = PostsRecyclerAdapter(posts)
            adapter?.listener = object : OnItemClickListener {

                override fun onItemClick(position: Int) {
                    Log.i("TAG", "PostsRecyclerAdapter: Position clicked $position")
                }

                override fun onPostClicked(studentpost: StudentPost?) {
                    // Implement the logic here
                    Log.i("TAG", "General Post Clicked: $studentpost")
                }
            }

            PostsRcyclerView?.adapter = adapter
        }

        interface OnItemClickListener {
            fun onItemClick(position: Int) // General Post
            fun onPostClicked(studentpost: StudentPost?)
        }

        override fun onResume() {
            super.onResume()

        }
    }


