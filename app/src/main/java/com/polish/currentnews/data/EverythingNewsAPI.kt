package com.polish.currentnews.data

import com.polish.currentnews.constants.URLConstants
import com.polish.currentnews.model.CurrentNews
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface EverythingNewsAPI {

    @GET("v2/everything")
    suspend fun fetchCurrentNewsAsync(
        @Query("q") query:String,
        @Query("sortBy") sortBy:String
    ):Response<CurrentNews>

    companion object {

        operator fun invoke():EverythingNewsAPI {

             val interceptor = Interceptor { chain ->
                val url = chain.request().url().newBuilder().addQueryParameter("apiKey", URLConstants.API_KEY).build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                chain.proceed(request)
            }


             val apiClient = OkHttpClient().newBuilder().addInterceptor(interceptor).build()

            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URLConstants.BASE_URL)
                .client(apiClient)
                .build()
                .create(EverythingNewsAPI::class.java)


        }

    }

}