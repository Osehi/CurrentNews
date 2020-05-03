package com.polish.currentnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.polish.currentnews.adapter.ArticleAdapter
import com.polish.currentnews.model.Article
import com.polish.currentnews.ui.CurrentNewsViewModel
import com.polish.currentnews.utils.Result
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val TAG = "MAINACTIVITY"

    lateinit var searchButton: Button
    lateinit var adapter:ArticleAdapter
    lateinit var inputKeyword:EditText

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

        // initialize the search button
        searchButton = findViewById(R.id.searchButtonId)

        searchButton.setOnClickListener {
            viewModel.viewMyCurrentNEwsList()
        }

        // initialize the recyclerview
        val recyclerView:RecyclerView = findViewById(R.id.myRecyclerViewId)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = ArticleAdapter()
        recyclerView.adapter = adapter


        viewModel.myNewsList.observe(this, Observer {

            when(it){

                is Result.Success -> {
                    progressBarId.visibility = View.GONE
                    it.data?.let { myResponse ->
                        val outcome = myResponse.body()?.articles
                        adapter.submitList(outcome)
                        Log.d(TAG, "these are my list: ${outcome}")
                    }
                    myRecyclerViewId.visibility = View.VISIBLE
                }
                is Result.Error -> {
                    progressBarId.visibility = View.GONE
                    networkErrorMsgId.visibility = View.VISIBLE
                    Toast.makeText(this, "${it.exception.message}", Toast.LENGTH_LONG).show()
                }

            }

        })


    }

//
//    private fun renderList(article: Article){
//
//        adapter.submitList(article)
//
//    }






}
