package com.polish.currentnews

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
const val SHARED_PREFERENCE = "sharePrefenece"
const val INPUT_KEYWORD = "keywordPassed"
class MainActivity : AppCompatActivity() {

//    private val lastVisibleItemPosition:Int
//        get() = linearLayoutManager.findLastVisibleItemPosition()

    val TAG = "MAINACTIVITY"

     var keyword:String = " "
        var enterQuery:String? = null

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

            progressBarId.visibility = View.VISIBLE

//             keyword = " "

            when {
                inputKeyword.text.trim().isEmpty() -> {
                   inputKeyword.error = "Keyword cannot be empty"
                }
                else -> {
                     keyword = inputKeyword.text.trim().toString()
                }
            }

            saveKeyword()


            viewModel.viewMyCurrentNEwsList(keyword)
//            enterQuery = keyword
            inputKeyword.setText(" ")
            closeKeyboard()
            Log.d(TAG, "at the search-button:$keyword")
//            Log.d(TAG, "at the sarch bar phase_2:$enterQuery")
        }
//        Log.d(TAG, "outside the search button:$keyword")
//        Log.d(TAG,"outside the search button:$enterQuery")

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

        setScrollListener()
        Log.d(TAG,"inside this keyword is:$keyword")


    }


   private fun closeKeyboard(){

       val view = this.currentFocus
       if(view != null){
            val hideMe = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
           hideMe.hideSoftInputFromWindow(view.windowToken, 0)

       }

    }


    private fun setScrollListener(){

        val insert = loadSavedData()
        Log.d("CHECKING_MY_SVD_PREF", "checking inside saved pref value:$insert")


        myRecyclerViewId.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE){
                    if (viewModel.loadingNewsArticle.value == null
                        || viewModel.loadingNewsArticle.value == false
                    ){
                        viewModel.getMoreCurrentNewsLists(insert!!)
                    }
                }
            }
        })


    }


    private fun saveKeyword(){
        val pref = getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(INPUT_KEYWORD, keyword)
        Log.d("MY_PREFERENCE_FUNCTION", "is there a value in keyword capture:${keyword}")
        editor.apply()
    }


    private fun loadSavedData():String?{
        val pref = getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE)
        enterQuery = pref.getString(INPUT_KEYWORD, null)
        return enterQuery
    }



//
//    private fun renderList(article: Article){
//
//        adapter.submitList(article)
//
//    }






}
