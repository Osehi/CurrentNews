package com.polish.currentnews.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.polish.currentnews.R
import com.polish.currentnews.databinding.ActivityDetailBinding
import com.polish.currentnews.model.Article
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    val TAG = "Detailed Activity"

    private lateinit var binding: ActivityDetailBinding

    companion object {

        const val NEWS_ARTICLE = "newsArticle"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)




        val newsItem = intent.getParcelableExtra(NEWS_ARTICLE) as? Article

        Picasso.get()
            .load(newsItem?.urlToImage)
            .into(binding.detailedPictureMessage)

        Log.d(TAG, "inside article:${newsItem}")

        binding.article = newsItem






    }
}
