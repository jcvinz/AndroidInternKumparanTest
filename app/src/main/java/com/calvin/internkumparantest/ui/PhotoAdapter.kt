package com.calvin.internkumparantest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.calvin.internkumparantest.R
import com.calvin.internkumparantest.data.PhotoResponseItem
import com.calvin.internkumparantest.databinding.PhotoItemLayoutBinding

class PhotoAdapter(private val listPhoto: List<PhotoResponseItem>) :
    RecyclerView.Adapter<PhotoAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            PhotoItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(R.drawable.dummy_album_thumbnail)
            .into(holder.binding.ivThumbnail)
    }

    override fun getItemCount(): Int {
        return listPhoto.size
    }
    class ListViewHolder(val binding: PhotoItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}