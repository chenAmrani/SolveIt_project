package com.example.solveitproject.Modules.Posts


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.solveitproject.Model.Post
import com.example.solveitproject.R

class PostsListActivity: AppCompatActivity() {

        var PostListView: ListView? = null
        var Posts: List<Post>? = null
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_post_list)


            PostListView = findViewById(R.id.lvPostList)
            PostListView?.adapter = PostsListAdapter(Posts)

            PostListView?.setOnItemClickListener { parent, view, position, id ->
                Log.i("TAG", "Row was clicked at: $position")
            }
        }

        class PostsListAdapter(val posts: List<Post>?): BaseAdapter() {

            override fun getCount(): Int = posts?.size ?: 0

            override fun getItem(position: Int): Any {
                TODO("Not yet implemented")
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                val post = posts?.get(position)
                var view: View? = null
                if (convertView == null) {
                    view = LayoutInflater.from(parent?.context).inflate(R.layout.post_layout_row, parent, false)

                }

                view = view ?: convertView

//                val publisherTextView: TextView? = view?.findViewById(R.id.publisherTextView)
//                val offerTextView: TextView? = view?.findViewById(R.id.offerTextView)
//                val contactTextView: CheckBox? = view?.findViewById(R.id.contactTextView)
//                val generalPostIdTextView: CheckBox? = view?.findViewById(R.id.generalPostIdTextView)

//                publisherTextView?.text = post?.publisher
//                offerTextView?.text = post?.offer
//                contactTextView?.text= post?.contact
//                generalPostIdTextView?.text= post?.postid

                return view!!
            }

            override fun getItemId(position: Int): Long = 0
        }

    }



