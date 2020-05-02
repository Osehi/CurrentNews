package com.polish.currentnews.ui

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

class CurrentNewsViewModel: ViewModel() {

    private val repository = NetworkRepository()
    private val job = Job()
    private val viewModelScope = CoroutineScope(job + Dispatchers.Main)

    private val _myNewsList = MutableLiveData<Result<Response<CurrentNews>>>()
    val myNewsList: LiveData<Result<Response<CurrentNews>>>
    get() = _myNewsList


    fun viewMyCurrentNEwsList():LiveData<Result<Response<CurrentNews>>>{

        viewModelScope.launch {
            _myNewsList.value = repository.getAllNews()
        }
        return _myNewsList

    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }


}