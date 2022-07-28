package com.calvin.internkumparantest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.calvin.internkumparantest.data.AlbumResponseItem
import com.calvin.internkumparantest.databinding.AlbumItemLayoutBinding

class AlbumAdapter(
    private val listAlbum: List<AlbumResponseItem>,
    private val callback: AlbumCallback
) :
    RecyclerView.Adapter<AlbumAdapter.ListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            AlbumItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.binding.tvAlbumTitle.text = listAlbum[position].title
        callback.getPhotos(listAlbum[position].id, holder.binding.rvPhoto)
    }

    override fun getItemCount(): Int {
        return listAlbum.size
    }

    class ListViewHolder(val binding: AlbumItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface AlbumCallback {
        fun getPhotos(albumId: Int, rv: RecyclerView)
    }
}