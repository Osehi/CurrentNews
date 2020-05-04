package com.polish.currentnews.data.network

import android.util.Log
import com.polish.currentnews.data.api.EverythingNewsAPI
import com.polish.currentnews.model.Article
import com.polish.currentnews.model.CurrentNews
import com.polish.currentnews.utils.BaseRepository
import com.polish.currentnews.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class NetworkRepository(): BaseRepository() {

    val everythingNewsAPI = EverythingNewsAPI()

    val TAG = "NETWORK REPOSITORY"

    suspend fun getAllNews(query:String):Result<Response<CurrentNews>> {

        return withContext(Dispatchers.IO){

            try {
                val output = everythingNewsAPI.fetchCurrentNewsAsync(query, "publishedAt", "en")
                Log.d(TAG, "show me the response:${output}")
                Result.Success(output)


            } catch (t:Throwable){
                Log.d(TAG, t.message.toString())
                Result.Error(t as Exception)
            }

        }

    }

}