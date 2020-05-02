package com.polish.currentnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.polish.currentnews.ui.CurrentNewsViewModel
import com.polish.currentnews.utils.Result

class MainActivity : AppCompatActivity() {

    val TAG = "MAINACTIVITY"

    lateinit var viewModel:CurrentNewsViewModel
    lateinit var makeCall:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        makeCall = findViewById(R.id.callButtonId)
        viewModel = ViewModelProvider(this).get(CurrentNewsViewModel::class.java)

        makeCall.setOnClickListener {

           viewModel.viewMyCurrentNEwsList()

        }


        viewModel.myNewsList.observe(this, Observer {

            when(it){

                is Result.Success -> {
                    it.data?.let { myResponse ->
                        val outcome = myResponse.body()?.articles
                        Log.d(TAG, "these are my list: ${outcome}")
                    }
                }
                is Result.Error -> {
                    Toast.makeText(this, "${it.exception.message}", Toast.LENGTH_LONG).show()
                }

            }

        })


    }






}
