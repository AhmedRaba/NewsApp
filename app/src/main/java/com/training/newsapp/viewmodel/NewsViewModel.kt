package com.training.newsapp.viewmodel

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.newsapp.isInternetAvailable
import com.training.newsapp.model.articles.ArticlesResponse
import com.training.newsapp.model.sources.SourcesResponse
import com.training.newsapp.repos.NewsRepository
import com.training.newsapp.repos.NewsRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel : ViewModel() {

    private val repository: NewsRepository = NewsRepositoryImpl()


    private val _articles = MutableLiveData<ArticlesResponse>()
    val articles: LiveData<ArticlesResponse> get() = _articles

    private val _sources = MutableLiveData<SourcesResponse>()
    val sources: LiveData<SourcesResponse> get() = _sources

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error


    fun fetchSources() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getSources()
                if (response.isSuccessful) {
                    _sources.postValue(response.body())
                } else {
                    _error.postValue(response.message())
                }
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }
    }

    fun fetchArticles(context: Context) {
        if (isInternetAvailable(context)) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response: Response<ArticlesResponse> = repository.getArticles()
                    if (response.isSuccessful) {
                        _articles.postValue(response.body())
                    } else {
                        _error.postValue(response.message())
                    }
                } catch (e: Exception) {
                    _error.postValue(e.message)
                }
            }
        } else {
            _error.postValue("Check Internet Connection")
        }
    }
}