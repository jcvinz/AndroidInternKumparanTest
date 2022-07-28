package com.calvin.internkumparantest.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.calvin.internkumparantest.databinding.ActivityMainBinding
import com.calvin.internkumparantest.ui.listpost.ListPostActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val intent = Intent(this, ListPostActivity::class.java)

        Handler(mainLooper).postDelayed({
            startActivity(intent)
            finish()
        }, DELAY_TIME)

    }

    companion object {
        const val DELAY_TIME = 3000L
    }
}