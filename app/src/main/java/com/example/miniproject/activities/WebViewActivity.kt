package com.example.miniproject.activities

import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.miniproject.model.ArticleModel
import com.example.miniproject.R

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        val webView = findViewById<WebView>(R.id.webview)
        val noInternetMessage = findViewById<TextView>(R.id.no_internet_available_message)
        val articleModel = intent.getSerializableExtra("Article_Model") as ArticleModel?
        if (articleModel != null && isNetworkAvailable) {
            webView.loadUrl(articleModel.url)
        } else {
            webView.visibility = View.GONE
            noInternetMessage.visibility = View.VISIBLE
        }
    }

    private val isNetworkAvailable: Boolean
        get() {
            val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
}