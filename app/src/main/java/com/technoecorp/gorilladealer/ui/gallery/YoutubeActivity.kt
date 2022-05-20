package com.technoecorp.gorilladealer.ui.gallery

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.webkit.WebChromeClient
import androidx.appcompat.app.AppCompatActivity
import com.technoecorp.gorilladealer.databinding.ActivityYoutubeBinding

class YoutubeActivity : AppCompatActivity() {
    private var _binding: ActivityYoutubeBinding? = null
    private val binding: ActivityYoutubeBinding = _binding!!
    private var urlLink: String? = null
    private var videoId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityYoutubeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        intent?.let {
            urlLink = intent.getStringExtra("urlLink")
            videoId = intent.getStringExtra("videoId")
        }
        if (urlLink == null && videoId == null) {
            finish()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun init() {
        with(binding.webView) {
            settings.javaScriptEnabled = true
            webChromeClient = WebChromeClient()
            settings.allowContentAccess = true
            settings.allowFileAccess = true
            loadData(
                "<html><body style=\"background-color: black\"><iframe width=\"100%\" height=\"90%\" src=\"$urlLink$videoId \" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe></body></html>",
                "text/html",
                "utf/8"
            )
        }

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            init()
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            init()
        }
    }


}