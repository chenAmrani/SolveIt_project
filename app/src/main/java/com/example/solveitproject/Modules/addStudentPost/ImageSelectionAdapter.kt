package com.example.solveitproject.Modules.addStudentPost

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.solveitproject.R
import com.squareup.picasso.Picasso

class ImageSelectionAdapter (

        private val imageUrls: List<String>,
        private val onItemClick: (String) -> Unit
    ) : RecyclerView.Adapter<ImageSelectionAdapter.ImageViewHolder>() {



        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
            return ImageViewHolder(view)
        }

        override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
            val imageUrl = imageUrls[position]
            Picasso.get().load(imageUrl).into(holder.imageView)
            holder.itemView.setOnClickListener { onItemClick(imageUrl) }
        }

        override fun getItemCount(): Int {
            return imageUrls.size
        }

        inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageView: ImageView = itemView.findViewById(R.id.imageView)


        }
    }
