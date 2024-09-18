package com.training.newsapp.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading


    fun fetchSources() {
        _isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response: Response<SourcesResponse> = repository.getSources()
                Log.e(TAG, "Sources response: ${response.body()}")

                if (response.isSuccessful) {
                    _sources.postValue(response.body())
                } else {
                    Log.e(TAG, "fetchSourcesVM: ${response.message()}")
                    _error.postValue(response.message())
                }
            } catch (e: Exception) {
                _error.postValue(e.message)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun fetchArticles() {
        _isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response: Response<ArticlesResponse> = repository.getArticles()
                if (response.isSuccessful) {
                    _articles.postValue(response.body())
                } else {
                    Log.e(TAG, "fetchArticles VM: ${response.message()}")
                    _error.postValue(response.message())
                }
            } catch (e: Exception) {
                _error.postValue(e.message)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun fetchArticlesBySource(source: String) {
        _isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response: Response<ArticlesResponse> = repository.getArticlesBySource(source)
                if (response.isSuccessful) {
                    _articles.postValue(response.body())
                } else {
                    Log.e(TAG, "fetchArticlesBySource VM: ${response.message()}")
                    _error.postValue(response.message())
                }
            } catch (e: Exception) {
                _error.postValue(e.message)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}
