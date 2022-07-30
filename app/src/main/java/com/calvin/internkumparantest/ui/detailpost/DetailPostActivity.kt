package com.calvin.internkumparantest.ui.detailpost

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.calvin.internkumparantest.R
import com.calvin.internkumparantest.data.Resource
import com.calvin.internkumparantest.databinding.ActivityDetailPostBinding
import com.calvin.internkumparantest.ui.CommentAdapter
import com.calvin.internkumparantest.ui.ViewModelFactory
import com.calvin.internkumparantest.ui.userdetail.UserDetailActivity

class DetailPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPostBinding
    private lateinit var detailViewModel: DetailPostViewModel

    private var idPost: Int = 0
    private var titlePost: String = ""
    private var bodyPost: String = ""
    private var userName: String = ""
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        detailViewModel = obtainViewModel(this)

        binding.customActionBar.tvTitleAction.text = getString(R.string.comments)

        idPost = intent.getIntExtra(ID_POST, 0)
        titlePost = intent.getStringExtra(TITLE_POST).toString()
        bodyPost = intent.getStringExtra(BODY_POST).toString()
        userName = intent.getStringExtra(USER_NAME).toString()
        userId = intent.getIntExtra(ID_POST, 0)

        binding.apply {
            tvTitle.text = titlePost
            tvName.text = userName
            tvBody.text = bodyPost

            tvName.setOnClickListener {
                val userDetailIntent = Intent(it.context, UserDetailActivity::class.java)
                userDetailIntent.putExtra(UserDetailActivity.USER_ID, userId)
                startActivity(userDetailIntent)
            }
        }
        val layoutManager = LinearLayoutManager(this)
        binding.rvComments.layoutManager = layoutManager
        binding.rvComments.setHasFixedSize(true)

        detailViewModel.getComments(idPost)

        detailViewModel.comments.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    renderLoading(it.state)
                }
                is Resource.Success -> {
                    val adapter = CommentAdapter(it.data)
                    binding.rvComments.adapter = adapter
                    if (binding.warningLayout.root.visibility == View.VISIBLE && binding.rvComments.visibility == View.INVISIBLE) {
                        binding.warningLayout.root.visibility = View.GONE
                        binding.rvComments.visibility = View.VISIBLE
                    }
                    renderLoading(false)
                }
                is Resource.Error -> {
                    renderToast(it.message, this)
                    binding.warningLayout.root.visibility = View.VISIBLE
                    binding.rvComments.visibility = View.INVISIBLE
                    renderLoading(false)
                    binding.warningLayout.btnRetry.setOnClickListener {
                        detailViewModel.getComments(idPost)
                    }
                }
            }
        }

    }

    private fun renderLoading(state: Boolean) {
        if (state) {
            binding.loading.visibility = View.VISIBLE
        } else {
            binding.loading.visibility = View.INVISIBLE
        }
    }

    private fun renderToast(message: String, context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailPostViewModel {
        val factory = ViewModelFactory.getInstance()
        return ViewModelProvider(activity, factory)[DetailPostViewModel::class.java]
    }

    companion object {
        const val ID_POST = "id_post"
        const val TITLE_POST = "title_post"
        const val BODY_POST = "body_post"
        const val USER_NAME = "user_name"
        const val USER_ID = "user_id"
    }
}