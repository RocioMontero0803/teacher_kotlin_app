package com.example.teacher_kotlin_app.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.teacher_kotlin_app.R
import androidx.recyclerview.widget.ListAdapter



class AppPicturesAdapter : ListAdapter<Int, AppPicturesAdapter.AppPictureViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppPictureViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_app_picture, parent, false)
        return AppPictureViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppPictureViewHolder, position: Int) {
        val appPictureResId = getItem(position)
        holder.bind(appPictureResId)
    }

    class AppPictureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val appImageView: ImageView = itemView.findViewById(R.id.appImageView)

        fun bind(appPictureResId: Int) {
            appImageView.setImageResource(appPictureResId)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }
    }
}
