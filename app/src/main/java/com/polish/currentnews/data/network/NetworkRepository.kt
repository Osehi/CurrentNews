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

    suspend fun getAllNews():Result<Response<CurrentNews>> {

        return withContext(Dispatchers.IO){

            try {

                Result.Success(everythingNewsAPI.fetchCurrentNewsAsync("nigeria", "publishedAt", "en"))

            } catch (t:Throwable){
                Log.d(TAG, t.message.toString())
                Result.Error(t as Exception)
            }

        }

    }

}