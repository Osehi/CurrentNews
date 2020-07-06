package com.polish.currentnews.ui

import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polish.currentnews.data.network.NetworkRepository
import com.polish.currentnews.model.CurrentNews
import com.polish.currentnews.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response

enum class CurrentNewsStatus { LOADING, ERROR, DONE}

class CurrentNewsViewModel: ViewModel() {

    private val repository = NetworkRepository()
    private val job = Job()
    private val viewModelScope = CoroutineScope(job + Dispatchers.Main)

    private var page = 1

    // to observe the loading status of news article
    private val _loadingNewsArticle = MutableLiveData<Boolean>()
    val loadingNewsArticle:LiveData<Boolean>
        get() = _loadingNewsArticle

    private val _myNewsList = MutableLiveData<Result<Response<CurrentNews>>>()
    val myNewsList: LiveData<Result<Response<CurrentNews>>>
    get() = _myNewsList

    // livedata to track the status
    private val _status = MutableLiveData<CurrentNewsStatus>()
    val status:LiveData<CurrentNewsStatus>
    get() = _status




    fun viewMyCurrentNEwsList(query:String):LiveData<Result<Response<CurrentNews>>>{

        viewModelScope.launch {



            _myNewsList.value = repository.getAllNews(query, page)
        }
        return _myNewsList

    }

    fun getMoreCurrentNewsLists(query: String){
        page++

        viewModelScope.launch {
            try {
                _loadingNewsArticle.value = true
                _myNewsList.value = repository.getAllNews(query, page)
            } catch (e:Exception){
                Log.i("CURRENTNEWSVIEWMODEL", "${e.message}")
                _loadingNewsArticle.value = false
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }


}