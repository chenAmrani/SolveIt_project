package com.example.solveitproject.Modules.StudentPost

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.solveitproject.Model.StudentPost
import com.example.solveitproject.Modules.StudentPost.StudentPostAdapter.StudentPostRecyclerAdapter
import com.example.solveitproject.databinding.ActivityStudentPostRecyclerViewBinding


class StudentPostRecyclerViewActivity : AppCompatActivity() {

        var studentPostsRecyclerView: RecyclerView? = null
        var studentposts: List<StudentPost>? = null
        var adapter: StudentPostRecyclerAdapter? = null


        lateinit var binding: ActivityStudentPostRecyclerViewBinding


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityStudentPostRecyclerViewBinding.inflate(layoutInflater)
            setContentView(binding.root)



            studentPostsRecyclerView = binding.rvStudentPostRecyclerList
            studentPostsRecyclerView?.setHasFixedSize(true)
            studentPostsRecyclerView?.layoutManager = LinearLayoutManager(this)

            adapter = StudentPostRecyclerAdapter(studentposts)
            adapter?.listener = object : OnItemClickListener {

                override fun onItemClick(position: Int) {
                    Log.i("TAG", "StudentPostsRecyclerAdapter: Position clicked $position")
                }

                override fun onStudentPostClicked(studentpost: StudentPost?) {
                    Log.i("TAG", "STUDENT POST $studentposts")
                }
            }

            studentPostsRecyclerView?.adapter = adapter
        }


            interface OnItemClickListener {
                fun onItemClick(position: Int) // General Post
                fun onStudentPostClicked(studentpost: StudentPost?)
            }

            override fun onResume() {
                super.onResume()

            }
        }
