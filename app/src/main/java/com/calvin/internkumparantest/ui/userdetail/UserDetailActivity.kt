package com.calvin.internkumparantest.ui.userdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.calvin.internkumparantest.R
import com.calvin.internkumparantest.data.PhotoResponseItem
import com.calvin.internkumparantest.data.Resource
import com.calvin.internkumparantest.databinding.ActivityUserDetailBinding
import com.calvin.internkumparantest.ui.AlbumAdapter
import com.calvin.internkumparantest.ui.CustomLoadingDialog
import com.calvin.internkumparantest.ui.PhotoAdapter
import com.calvin.internkumparantest.ui.ViewModelFactory
import com.calvin.internkumparantest.ui.photodetail.PhotoDetailActivity

class UserDetailActivity : AppCompatActivity(), AlbumAdapter.AlbumCallback,
    PhotoAdapter.PhotoCallback {
    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var loading: CustomLoadingDialog
    private lateinit var userDetailViewModel: UserDetailViewModel
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.customActionBar.tvTitleAction.text = getString(R.string.user_detail)

        userDetailViewModel = obtainViewModel(this)
        loading = CustomLoadingDialog(this)

        userId = intent.getIntExtra(USER_ID_DETAIL,0)

        userDetailViewModel.getUserDetail(userId)

        val layoutManager = LinearLayoutManager(this)
        binding.rvAlbum.layoutManager = layoutManager
        binding.rvAlbum.setHasFixedSize(true)

        userDetailViewModel.user.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    renderLoading(it.state)
                }
                is Resource.Success -> {
                    binding.apply {
                        tvName.text = it.data[0].name
                        tvEmail.text = it.data[0].email
                        tvAddress.text =
                            it.data[0].address.street.plus(" ${it.data[0].address.city}")
                        tvCompany.text = it.data[0].company.name
                    }
                    userDetailViewModel.getAlbums(userId)
                }
                is Resource.Error -> {
                    renderToast(it.message, this)
                    renderLoading(false)
                    finish()
                }
            }
        }

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
                    renderLoading(it.state)
                }
                is Resource.Success -> {
                    val adapter = PhotoAdapter(it.data, this)
                    rv.adapter = adapter
                    renderLoading(false)
                }
                is Resource.Error -> {
                    renderToast(it.message, this)
                }
            }
        }
    }

    override fun onPhotoClick(photoItem: PhotoResponseItem) {
        val photoDetailIntent = Intent(this, PhotoDetailActivity::class.java)
        photoDetailIntent.putExtra(PhotoDetailActivity.PHOTO_TITLE, photoItem.title)
        photoDetailIntent.putExtra(PhotoDetailActivity.PHOTO_URL, photoItem.url)
        startActivity(photoDetailIntent)
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
        const val USER_ID_DETAIL = "user_id_detail"
    }
}