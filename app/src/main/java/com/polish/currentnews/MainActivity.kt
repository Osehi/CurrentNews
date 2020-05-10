package com.polish.currentnews

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.polish.currentnews.adapter.ArticleAdapter
import com.polish.currentnews.adapter.ArticleCardAdapter
import com.polish.currentnews.model.Article
import com.polish.currentnews.ui.CurrentNewsViewModel
import com.polish.currentnews.ui.DetailActivity
import com.polish.currentnews.ui.LaunchWeb
import com.polish.currentnews.ui.OnItemOpenWebListener
import com.polish.currentnews.utils.Result
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val TAG = "MAINACTIVITY"

    lateinit var searchButton: Button
    lateinit var adapter:ArticleAdapter
    lateinit var inputKeyword:EditText
    lateinit var mAdapter: ArticleCardAdapter

    lateinit var viewModel:CurrentNewsViewModel
//    lateinit var makeCall:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        makeCall = findViewById(R.id.callButtonId)
        viewModel = ViewModelProvider(this).get(CurrentNewsViewModel::class.java)

//        makeCall.setOnClickListener {
//
//           viewModel.viewMyCurrentNEwsList()
//
//        }

        // initialize the editText
        inputKeyword = findViewById(R.id.inputFromUserId)
//        val keyword = inputKeyword.text.toString()

        // initialize the search button
        searchButton = findViewById(R.id.searchButtonId)



        searchButton.setOnClickListener {

            var keyword:String = " "

            when {
                inputKeyword.text.trim().isEmpty() -> {
                   inputKeyword.error = "Keyword cannot be empty"
                }
                else -> {
                     keyword = inputKeyword.text.trim().toString()
                }
            }


            viewModel.viewMyCurrentNEwsList(keyword)
            inputKeyword.setText(" ")
            closeKeyboard()
        }

        // initialize the recyclerview
        val recyclerView:RecyclerView = findViewById(R.id.myRecyclerViewId)
        recyclerView.layoutManager = LinearLayoutManager(this)

//        adapter = ArticleAdapter(ArticleAdapter.OnClickListener {
//
//        }, object :OnItemOpenWebListener {
//            override fun onItemOpenWeb(article: Article) {
//                val webpage:Uri = Uri.parse(article.url)
//                val intent = Intent(Intent.ACTION_VIEW, webpage)
//                if(intent.resolveActivity(packageManager) != null){
//                    startActivity(intent)
//                }
//            }
//        }
//
//            )
//        recyclerView.adapter = adapter

        // new adapter
        mAdapter = ArticleCardAdapter(

            ArticleCardAdapter.OnClickListener { article ->

//                val intent = Intent(this, DetailActivity::class.java)
//                intent.putExtra(DetailActivity.NEWS_ARTICLE, article)
//                startActivity(intent)

                val intent = Intent(this,LaunchWeb::class.java)
                intent.putExtra(LaunchWeb.GET_URL_ITEM,article)
                startActivity(intent)

            },

            object :OnItemOpenWebListener {
                override fun onItemOpenWeb(article: Article) {
                    val webpage:Uri = Uri.parse(article.url)
                    val intent = Intent(Intent.ACTION_VIEW, webpage)
                    if (intent.resolveActivity(packageManager) != null){
                        startActivity(intent)
                    }
                }

            }
        )
        recyclerView.adapter = mAdapter


        viewModel.myNewsList.observe(this, Observer {

            when(it){

                is Result.Success -> {
                    progressBarId.visibility = View.GONE
                    it.data?.let { myResponse ->
                        val outcome = myResponse.body()?.articles
//                        adapter.submitList(outcome)
                        mAdapter.submitList(outcome)
                        Log.d(TAG, "these are my list: ${outcome}")
                    }
                    myRecyclerViewId.visibility = View.VISIBLE
                    if (networkErrorMsgId.isVisible){
                        networkErrorMsgId.visibility = View.INVISIBLE
                    }
                }
                is Result.Error -> {
                    progressBarId.visibility = View.GONE
                    if (myRecyclerViewId.isVisible){
                        myRecyclerViewId.visibility = View.GONE
                    }
                    networkErrorMsgId.visibility = View.VISIBLE
//                    Toast.makeText(this, "${it.exception.message}", Toast.LENGTH_LONG).show()
                    Toast.makeText(this, "No network connection", Toast.LENGTH_LONG).show()
                }

            }

        })


    }


   private fun closeKeyboard(){

       val view = this.currentFocus
       if(view != null){
            val hideMe = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
           hideMe.hideSoftInputFromWindow(view.windowToken, 0)

       }

    }



//
//    private fun renderList(article: Article){
//
//        adapter.submitList(article)
//
//    }






}
