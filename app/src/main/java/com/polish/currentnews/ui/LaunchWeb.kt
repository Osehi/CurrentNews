package com.polish.currentnews.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.polish.currentnews.R
import com.polish.currentnews.databinding.ActivityLaunchWebBinding
import com.polish.currentnews.model.Article

class LaunchWeb : AppCompatActivity() {

    val TAG = "LaunchWeb Activity"
    private lateinit var binding: ActivityLaunchWebBinding
    lateinit var myWebView:WebView

    companion object {

        const val GET_URL_ITEM = "articleUrl"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_web)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_launch_web)

        val urlItem = intent.getParcelableExtra(GET_URL_ITEM) as? Article

        myWebView = binding.displayMyUrl

        myWebView.webViewClient = object : WebViewClient(){

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                url: String?
            ): Boolean {

               view?.loadUrl(url)
                return true

            }

        }

        myWebView.loadUrl(urlItem?.url)
            myWebView.settings.javaScriptEnabled = true
            myWebView.settings.allowContentAccess = true
            myWebView.settings.domStorageEnabled = true
            myWebView.settings.useWideViewPort = true
            myWebView.settings.setAppCacheEnabled(true)


    }
}
