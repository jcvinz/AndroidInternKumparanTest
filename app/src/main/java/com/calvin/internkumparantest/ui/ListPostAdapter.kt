package com.calvin.internkumparantest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.calvin.internkumparantest.data.PostResponseItem
import com.calvin.internkumparantest.data.UserResponseItem
import com.calvin.internkumparantest.databinding.PostItemLayoutBinding

class ListPostAdapter(
    private val listPost: List<PostResponseItem>,
    private val listUser: List<UserResponseItem>,
    private val callback: PostCallback
) : RecyclerView.Adapter<ListPostAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            PostItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var index = -1
        for (i in listUser.indices) {
            if (listPost[position].userId == listUser[i].id) {
                holder.binding.tvName.text = listUser[i].name
                holder.binding.tvCompany.text = listUser[i].company.name
                index = i
            }
        }

        holder.binding.tvTitle.text = listPost[position].title
        holder.binding.tvBody.text = listPost[position].body

        holder.binding.root.setOnClickListener {
            callback.onPostClick(
                listPost[position].id,
                listPost[position].title,
                listPost[position].body,
                listUser[index].name,
                listUser[index].id
            )
        }
    }

    override fun getItemCount(): Int {
        return listPost.size
    }

    class ListViewHolder(val binding: PostItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface PostCallback {
        fun onPostClick(id: Int, title: String, body: String, userName: String, userId: Int)
    }

}