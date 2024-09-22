package com.training.newsapp.ui.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.newsapp.data.api.model.ArticlesResponse
import com.training.newsapp.data.api.model.SourcesResponse
import com.training.newsapp.data.repos.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {


    private val _articles = MutableLiveData<ArticlesResponse>()
    val articles: LiveData<ArticlesResponse> get() = _articles

    private val _sources = MutableLiveData<SourcesResponse>()
    val sources: LiveData<SourcesResponse> get() = _sources

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading


    fun fetchSources(category: String) {
        _isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response: Response<SourcesResponse> = repository.getSources(category)
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


    fun fetchArticlesBySource(source: String, query: String) {
        _isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response: Response<ArticlesResponse> =
                    repository.getArticlesBySource(source, query = query)
                if (response.isSuccessful) {
                    _articles.postValue(response.body())
                    Log.e(TAG, "fetchArticlesBySource VM: ${response.message()}")
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
