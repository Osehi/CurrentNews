package com.polish.currentnews.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.polish.currentnews.data.api.EverythingNewsAPI
import com.polish.currentnews.model.Article
import com.polish.currentnews.model.CurrentNews
import com.polish.currentnews.utils.Result
import com.polish.currentnews.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CurrentNewsViewModelTest {


    @get:Rule
    val testInstantTaskExecutorRule:TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var everythingNewsAPI: EverythingNewsAPI

//    @Mock
//    private lateinit var apiCurrentNewsObserver: Observer<Result<Response<CurrentNews>>>


}