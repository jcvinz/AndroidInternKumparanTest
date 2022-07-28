package com.calvin.internkumparantest.ui.userdetail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.calvin.internkumparantest.data.Resource
import com.calvin.internkumparantest.databinding.ActivityUserDetailBinding
import com.calvin.internkumparantest.ui.AlbumAdapter
import com.calvin.internkumparantest.ui.CustomLoadingDialog
import com.calvin.internkumparantest.ui.PhotoAdapter
import com.calvin.internkumparantest.ui.ViewModelFactory

class UserDetailActivity : AppCompatActivity(), AlbumAdapter.AlbumCallback {
    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var loading: CustomLoadingDialog
    private lateinit var userDetailViewModel: UserDetailViewModel
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        userDetailViewModel = obtainViewModel(this)
        loading = CustomLoadingDialog(this)

        userId = intent.getIntExtra(USER_ID, 0)

        userDetailViewModel.getAlbums(userId)

        val layoutManager = LinearLayoutManager(this)
        binding.rvAlbum.layoutManager = layoutManager
        binding.rvAlbum.setHasFixedSize(true)

        userDetailViewModel.albums.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    renderLoading(it.state)
                }
                is Resource.Success -> {
                    val adapter = AlbumAdapter(it.data, this)
                    binding.rvAlbum.adapter = adapter
                    renderLoading(false)
                }
                is Resource.Error -> {
                    renderToast(it.message, this)
                }
            }
        }

    }

    override fun getPhotos(albumId: Int, rv: RecyclerView) {
        val layoutManager = LinearLayoutManager(rv.context, LinearLayoutManager.HORIZONTAL, false)
        rv.layoutManager = layoutManager
        rv.setHasFixedSize(true)

        userDetailViewModel.getPhotos(albumId)

        userDetailViewModel.photos.observe(this) {
            when (it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    val adapter = PhotoAdapter(it.data)
                    rv.adapter = adapter
                }
                is Resource.Error -> {
                    renderToast(it.message, this)
                }
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): UserDetailViewModel {
        val factory = ViewModelFactory.getInstance()
        return ViewModelProvider(activity, factory)[UserDetailViewModel::class.java]
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

    companion object {
        const val USER_ID = "user_id"
    }
}