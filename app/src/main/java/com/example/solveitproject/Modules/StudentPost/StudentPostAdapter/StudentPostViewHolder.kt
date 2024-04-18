package com.example.solveitproject.Modules.StudentPost.StudentPostAdapter


import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.solveitproject.Model.StudentPost
import com.example.solveitproject.Modules.StudentPost.StudentPostRecyclerViewActivity
import com.example.solveitproject.R
import com.squareup.picasso.Picasso

class StudentPostViewHolder(val itemView: View,
                            val listener: StudentPostRecyclerViewActivity.OnItemClickListener?,
                            var studentposts: List<StudentPost>?): RecyclerView.ViewHolder(itemView) {

    var courseTextView: TextView? = null
    var topicTextView: TextView? = null
    var additionalTextView: TextView? = null
    var idTextView: TextView? = null
    var imageImageView: ImageView? = null
    var studentpost: StudentPost? = null

    init {
        courseTextView = itemView.findViewById(R.id.courseLabelTextViewstudent)
        topicTextView = itemView.findViewById(R.id.topicLabelTextViewstudent)
        additionalTextView = itemView.findViewById(R.id.additionalTextLabelTextViewstudent)
        imageImageView = itemView.findViewById(R.id.postImageViewstudent)
//        idTextView = itemView.findViewById(R.id.studentPostIdLabelTextViewstudent)


        itemView.setOnClickListener {
            Log.i("TAG", "StudentPostViewHolder: Position clicked $adapterPosition")

            listener?.onItemClick(adapterPosition)
            listener?.onStudentPostClicked(studentpost)
        }
    }

    fun bind(studentpost: StudentPost?) {
        this.studentpost = studentpost
        courseTextView?.text = studentpost?.course
        topicTextView?.text = studentpost?.topic
        additionalTextView?.text = studentpost?.additionalText
        idTextView?.text = studentpost?.postid


        val imageUrl = studentpost?.image
        if (!imageUrl.isNullOrEmpty()) {
            Picasso.get().load(imageUrl)
                .resize(400, 400)
                .centerCrop()
                .into(imageImageView)
        }

    }
}