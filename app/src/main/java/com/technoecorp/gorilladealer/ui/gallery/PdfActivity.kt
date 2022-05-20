package com.technoecorp.gorilladealer.ui.gallery

import android.app.DownloadManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.technoecorp.gorilladealer.R
import com.technoecorp.gorilladealer.databinding.ActivityPdfBinding

class PdfActivity : AppCompatActivity() {
    private var _binding: ActivityPdfBinding? = null
    private val binding: ActivityPdfBinding get() = _binding!!
    var url: String? = null
    private lateinit var manager: DownloadManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPdfBinding.inflate(layoutInflater)
        setContentView(binding.root)
        url = intent.getStringExtra("url")
        if (url == null) {
            finish()
        }
        manager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager

        binding.toolbar.btnExport.setOnClickListener {
            val uri = Uri.parse(url)
            val fileName = url!!.substring(url!!.lastIndexOf('/') + 1, url!!.length)
            val request = DownloadManager.Request(uri)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
            manager.enqueue(request)
        }
        binding.toolbar.btnExport.text = getString(R.string.download)

        binding.toolbar.linearBack.setOnClickListener { finish() }
        binding.webView.apply {
            webViewClient = WebViewClient()
            settings.setSupportZoom(true)
            settings.javaScriptEnabled = true
            loadUrl("https://docs.google.com/gview?embedded=true&url=$url")
        }
    }
}