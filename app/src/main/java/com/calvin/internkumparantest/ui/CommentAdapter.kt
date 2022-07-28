package com.calvin.internkumparantest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.calvin.internkumparantest.data.CommentResponseItem
import com.calvin.internkumparantest.databinding.CommentItemLayoutBinding

class CommentAdapter(private val listComment: List<CommentResponseItem>) :
    RecyclerView.Adapter<CommentAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            CommentItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.binding.apply {
            tvName.text = listComment[position].name
            tvComment.text = listComment[position].body
        }
    }

    class ListViewHolder(val binding: CommentItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return listComment.size
    }
}