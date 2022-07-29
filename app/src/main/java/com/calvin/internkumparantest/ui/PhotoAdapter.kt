package com.calvin.internkumparantest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.calvin.internkumparantest.R
import com.calvin.internkumparantest.data.PhotoResponseItem
import com.calvin.internkumparantest.databinding.PhotoItemLayoutBinding

class PhotoAdapter(
    private val listPhoto: List<PhotoResponseItem>,
    private val callback: PhotoCallback
) :
    RecyclerView.Adapter<PhotoAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            PhotoItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        /* Code untuk menampilkan thumbnail foto-foto dari album menggunakan link yang didapat dari API */
//        Glide.with(holder.itemView.context)
//            .load(listPhoto[position].thumbnailUrl)
//            .into(holder.binding.ivThumbnail)

        /* Code untuk menampilkan thumbnail foto-foto dari album menggunakan gambar dummy untuk ilustrasi
        * dikarenakan data link url gambar yang didapat dari API tidak menampilkan gambar apapun */

        Glide.with(holder.itemView.context)
            .load(R.drawable.dummy_album_thumbnail)
            .into(holder.binding.ivThumbnail)

        holder.binding.root.setOnClickListener {
            callback.onPhotoClick(listPhoto[position])
        }
    }

    override fun getItemCount(): Int {
        return listPhoto.size
    }

    class ListViewHolder(val binding: PhotoItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface PhotoCallback {
        fun onPhotoClick(photoItem: PhotoResponseItem)
    }
}