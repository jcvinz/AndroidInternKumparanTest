package com.calvin.internkumparantest.ui.photodetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.calvin.internkumparantest.R
import com.calvin.internkumparantest.databinding.ActivityPhotoDetailBinding

class PhotoDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotoDetailBinding
    private var photoTitle: String = ""
    private var photoUrl: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.customActionBar.tvTitleAction.text = getString(R.string.photo_detail)

        photoTitle = intent.getStringExtra(PHOTO_TITLE).toString()
        photoUrl = intent.getStringExtra(PHOTO_URL).toString()

        binding.tvPhotoTitle.text = photoTitle

        //Code untuk menampilkan image view menggunakan data dummy dikarenakan link url photo yang tidak menampilkan gambar apapun
        Glide.with(this)
            .load(R.drawable.dummy_album_thumbnail)
            .into(binding.ivPhoto)

        //Code apabila ingin menggunakan url photo
//        Glide.with(this)
//            .load(photoUrl)
//            .into(binding.ivPhoto)
    }

    companion object {
        const val PHOTO_TITLE = "photo_title"
        const val PHOTO_URL = "photo_url"
    }
}