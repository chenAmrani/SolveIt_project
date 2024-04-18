package com.example.solveitproject.Modules.Posts

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.solveitproject.Model.StudentPost
import com.example.solveitproject.Modules.Posts.PostAdapter.PostsRecyclerAdapter
import com.example.solveitproject.R
import com.example.solveitproject.databinding.ActivityPostRecyclerViewBinding


class PostsRecyclerViewActivity : AppCompatActivity() {



    var PostsRecyclerView: RecyclerView? = null
    var posts: List<StudentPost>? = null
    var adapter: PostsRecyclerAdapter? = null


    private lateinit var binding: ActivityPostRecyclerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPostRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)



        PostsRecyclerView = binding.rvPostRecyclerList
        PostsRecyclerView?.setHasFixedSize(true)
        PostsRecyclerView?.layoutManager = LinearLayoutManager(this)
        val recyclerView: RecyclerView = findViewById(R.id.rvPostRecyclerList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = PostsRecyclerAdapter(posts)
        adapter?.listener = object : OnItemClickListener {

            override fun onItemClick(position: Int) {
                Log.i("TAG", "PostsRecyclerAdapter: Position clicked $position")
                recyclerView.smoothScrollToPosition(position)
            }

            override fun onPostClicked(studentpost: StudentPost?) {
                // Implement the logic here
                Log.i("TAG", "General Post Clicked: $studentpost")
            }
        }

        PostsRecyclerView?.adapter = adapter
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onPostClicked(studentpost: StudentPost?)
    }

    override fun onResume() {
        super.onResume()

    }
}