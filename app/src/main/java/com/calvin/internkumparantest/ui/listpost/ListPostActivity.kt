package com.calvin.internkumparantest.ui.listpost

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.calvin.internkumparantest.R
import com.calvin.internkumparantest.data.PostResponseItem
import com.calvin.internkumparantest.data.Resource
import com.calvin.internkumparantest.data.UserResponseItem
import com.calvin.internkumparantest.databinding.ActivityListPostBinding
import com.calvin.internkumparantest.ui.CustomLoadingDialog
import com.calvin.internkumparantest.ui.ListPostAdapter
import com.calvin.internkumparantest.ui.ViewModelFactory
import com.calvin.internkumparantest.ui.detailpost.DetailPostActivity

class ListPostActivity : AppCompatActivity(), ListPostAdapter.PostCallback {
    private lateinit var binding: ActivityListPostBinding
    private lateinit var listPostViewModel: ListPostViewModel
    private lateinit var loading: CustomLoadingDialog

    private var tempListPost: List<PostResponseItem>? = null
    private var tempListUser: List<UserResponseItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.customActionBar.tvTitleAction.text = getString(R.string.home)

        listPostViewModel = obtainViewModel(this)
        loading = CustomLoadingDialog(this)

        listPostViewModel.getAllPost()

        val layoutManager = LinearLayoutManager(this)
        binding.rvPost.layoutManager = layoutManager
        binding.rvPost.setHasFixedSize(true)

        listPostViewModel.users.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    renderLoading(it.state)
                }
                is Resource.Success -> {
                    tempListUser = it.data
                    if (tempListPost != null && tempListUser != null) {
                        val adapter = ListPostAdapter(tempListPost!!, tempListUser!!, this)
                        binding.rvPost.adapter = adapter
                    }
                    renderLoading(false)
                }
                is Resource.Error -> {
                    renderToast(it.message, this)
                }
            }
        }

        listPostViewModel.posts.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    renderLoading(it.state)
                }
                is Resource.Success -> {
                    listPostViewModel.getAllUser()
                    tempListPost = it.data
                }
                is Resource.Error -> {
                    renderToast(it.message, this)
                }
            }
        }

    }

    override fun onPostClick(id: Int, title: String, body: String, userName: String, userId: Int) {
        val postDetailIntent = Intent(this, DetailPostActivity::class.java)
        postDetailIntent.apply {
            putExtra(DetailPostActivity.ID_POST, id)
            putExtra(DetailPostActivity.TITLE_POST, title)
            putExtra(DetailPostActivity.BODY_POST, body)
            putExtra(DetailPostActivity.USER_NAME, userName)
            putExtra(DetailPostActivity.USER_ID, userId)
        }
        startActivity(postDetailIntent)
    }

    private fun obtainViewModel(activity: AppCompatActivity): ListPostViewModel {
        val factory = ViewModelFactory.getInstance()
        return ViewModelProvider(activity, factory)[ListPostViewModel::class.java]
    }

    private fun renderLoading(state: Boolean) {
        if (state) {
            loading.show()
            loading.setCancelable(false)
        } else {
            loading.dismiss()
        }
    }

    private fun renderToast(message: String, context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}