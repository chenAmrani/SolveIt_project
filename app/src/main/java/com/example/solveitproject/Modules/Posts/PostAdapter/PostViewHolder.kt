package com.example.solveitproject.Modules.Posts.PostAdapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.solveitproject.Model.StudentPost
import com.example.solveitproject.Modules.Posts.PostsRecyclerViewActivity
import com.example.solveitproject.R
import com.squareup.picasso.Picasso

class PostViewHolder(val itemView: View,
                         val listener: PostsRecyclerViewActivity.OnItemClickListener?,
                         var posts: List<StudentPost>?): RecyclerView.ViewHolder(itemView) {

        var course: TextView? = null
        var topic: TextView? = null
        var additonalText: TextView? = null
//        var idTextView: TextView? = null
        var imageImageView: ImageView? = null
        var post: StudentPost? = null

        init {
            course = itemView.findViewById(R.id.CoureNameLabelTextView)
            topic = itemView.findViewById(R.id.TopicLabelTextView)
            additonalText = itemView.findViewById(R.id.AdditonalTextLabelTextView)
            imageImageView = itemView.findViewById(R.id.postImageView)
//            idTextView = itemView.findViewById(R.id.generalPostIdTextView)


            itemView.setOnClickListener {
                Log.i("TAG", "PostViewHolder: Position clicked $adapterPosition")

                listener?.onItemClick(adapterPosition)
                listener?.onPostClicked(post)
            }
        }

        fun bind(post: StudentPost?) {
            this.post = post
//            idTextView?.text = post?.postid
            course?.text = post?.course
            topic?.text = post?.topic
            additonalText?.text = post?.additionalText
            val imageUrl = post?.image
            if (!imageUrl.isNullOrEmpty()) {
                Picasso.get().load(imageUrl)
                    .resize(400, 400)
                    .centerCrop()
                    .into(imageImageView)
            }
        }
    }


