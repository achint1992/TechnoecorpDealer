package com.technoecorp.gorilladealer.ui.gallery

import android.app.DownloadManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.technoecorp.gorilladealer.R
import com.technoecorp.gorilladealer.databinding.ActivityImageFullBinding

class ImageFullActivity : AppCompatActivity() {
    private var _binding: ActivityImageFullBinding? = null
    private val binding: ActivityImageFullBinding get() = _binding!!
    private var url: String? = null
    private lateinit var manager: DownloadManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityImageFullBinding.inflate(layoutInflater)
        setContentView(binding.root)
        url = intent.getStringExtra("url")
        if (url == null) {
            finish()
        }
        manager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager

        Glide.with(this).load(url).into(binding.imageView)
        binding.toolbar.linearBack.setOnClickListener { finish() }
        binding.toolbar.btnExport.setOnClickListener {
            val uri = Uri.parse(url)
            val fileName = url!!.substring(url!!.lastIndexOf('/') + 1, url!!.length)
            val request = DownloadManager.Request(uri)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
            manager.enqueue(request)
        }
        binding.toolbar.btnExport.text = getString(R.string.download)
    }
}