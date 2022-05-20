package com.technoecorp.gorilladealer.ui.webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.technoecorp.gorilladealer.databinding.ActivityPdfBinding

class WebViewActivity : AppCompatActivity() {
    private var _binding: ActivityPdfBinding? = null
    private val binding: ActivityPdfBinding get() =  _binding!!
    private var urlLink: String = ""
    private var title: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPdfBinding.inflate(layoutInflater)
        setContentView(binding.root)
        urlLink = intent.getStringExtra("url") ?: ""
        title = intent.getStringExtra("title")
        if (urlLink == "") {
            finish()
        }
        binding.toolbar.btnExport.visibility = View.INVISIBLE
        title?.let {
            binding.toolbar.headingText.text = it
        }
        binding.webView.apply {
            settings.loadsImagesAutomatically = true
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
          //  settings.allowUniversalAccessFromFileURLs = true
          //  settings.allowFileAccessFromFileURLs = true
            settings.loadsImagesAutomatically = true
            settings.setSupportMultipleWindows(true)
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            loadUrl(urlLink)
            webViewClient = WebViewClient()
            webViewClient = MyBrowser(binding.progressBar)
        }
        binding.toolbar.linearBack.setOnClickListener { finish() }
    }

    class MyBrowser(private val progressBar: ProgressBar) : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            progressBar.visibility = View.GONE
        }

        init {
            progressBar.visibility = View.VISIBLE
        }
    }

}