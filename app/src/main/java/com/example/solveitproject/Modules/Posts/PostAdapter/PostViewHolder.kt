package com.example.solveitproject.Modules.Posts.PostAdapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.solveitproject.Model.StudentPost
import com.example.solveitproject.Modules.Posts.PostsRecyclerViewActivity

    class PostViewHolder(val itemView: View,
                         val listener: PostsRecyclerViewActivity.OnItemClickListener?,
                         var posts: List<StudentPost>?): RecyclerView.ViewHolder(itemView) {

//        var requestTextView: TextView? = null
//        var offerTextView: TextView? = null
//        var contactTextView: TextView? = null
//        var idTextView: TextView? = null
        var imageImageView: ImageView? = null
        var post: StudentPost? = null

        init {
//            requestTextView = itemView.findViewById(R.id.requestTextView)
//            offerTextView = itemView.findViewById(R.id.offerTextView)
//            contactTextView = itemView.findViewById(R.id.contactTextView)
//            imageImageView = itemView.findViewById(R.id.postImageView)
//            idTextView = itemView.findViewById(R.id.generalPostIdTextView)


            itemView.setOnClickListener {
                Log.i("TAG", "GeneralPostViewHolder: Position clicked $adapterPosition")

                listener?.onItemClick(adapterPosition)
                listener?.onGeneralPostClicked(post)
            }
        }

        fun bind(post: StudentPost?) {
            this.post = post
//            requestTextView?.text = post?.request
//            offerTextView?.text = post?.offer
//            contactTextView?.text = post?.contact
//            idTextView?.text = post?.postid
            val imageUrl = post?.image
            if (!imageUrl.isNullOrEmpty()) {
                Picasso.get().load(imageUrl)
                    .resize(400, 400)
                    .centerCrop()
                    .into(imageImageView)
            }
        }
    }


}