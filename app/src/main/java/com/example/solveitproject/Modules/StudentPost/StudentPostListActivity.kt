package com.example.solveitproject.Modules.StudentPost

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.solveitproject.R
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ListView
import android.widget.TextView
import com.example.solveitproject.Model.StudentPost


class StudentPostListActivity : AppCompatActivity() {

        var StudentPostListView: ListView? = null
        var studentPosts: List<StudentPost>? = null
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_student_post_list)


            StudentPostListView = findViewById(R.id.lvstudentPostList)
            StudentPostListView?.adapter = StudentPostsListAdapter(studentPosts)

            StudentPostListView?.setOnItemClickListener { parent, view, position, id ->
                Log.i("TAG", "Row was clicked at: $position")
            }
        }

        class StudentPostsListAdapter(val studentposts: List<StudentPost>?) : BaseAdapter() {

            override fun getCount(): Int = studentposts?.size ?: 0

            override fun getItem(position: Int): Any {
                TODO("Not yet implemented")
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                val studentpost = studentposts?.get(position)
                var view: View? = null
                if (convertView == null) {
                    view = LayoutInflater.from(parent?.context)
                        .inflate(R.layout.student_post_lyout_row, parent, false)

                }

                view = view ?: convertView

                val courseTextView: TextView? = view?.findViewById(R.id.courseLabelTextViewstudent)
                val topicTextView: TextView? = view?.findViewById(R.id.topicLabelTextViewstudent)
                val additionalTextTextView: TextView? = view?.findViewById(R.id.additionalTextLabelTextViewstudent)
//                val studentPostIdTextView: TextView? = view?.findViewById(R.id.studentPostIdLabelTextViewstudent)

                courseTextView?.text = studentpost?.course
                topicTextView?.text = studentpost?.topic
                additionalTextTextView?.text = studentpost?.additionalText
//                studentPostIdTextView?.text = studentpost?.postid

                return view!!
            }

            override fun getItemId(position: Int): Long = 0
        }
    }
