package com.training.newsapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.newsapp.Resource
import com.training.newsapp.data.api.model.ArticlesResponse
import com.training.newsapp.data.api.model.SourcesResponse
import com.training.newsapp.data.repos.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    private val _articles = MutableLiveData<Resource<ArticlesResponse>>()
    val articles: LiveData<Resource<ArticlesResponse>> get() = _articles

    private val _sources = MutableLiveData<Resource<SourcesResponse>>()
    val sources: LiveData<Resource<SourcesResponse>> get() = _sources


    fun fetchSources(category: String) {
        _sources.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getSources(category)
            _sources.postValue(result)
        }
    }


    fun fetchArticlesBySource(source: String, query: String) {
        _articles.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getArticlesBySource(source, query)
            _articles.postValue(result)
        }
    }

}
