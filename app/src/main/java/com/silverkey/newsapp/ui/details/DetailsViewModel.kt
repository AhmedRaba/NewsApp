package com.silverkey.newsapp.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silverkey.domain.model.Article
import com.silverkey.domain.usecase.DeleteArticleUseCase
import com.silverkey.domain.usecase.IsArticleSavedUseCase
import com.silverkey.domain.usecase.SaveArticleUseCase
import com.silverkey.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val saveArticleUseCase: SaveArticleUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase,
    private val isArticleSavedUseCase: IsArticleSavedUseCase,
) : ViewModel() {

    private val _newsState = MutableLiveData<Result<List<Article>>>()
    val newsState: LiveData<Result<List<Article>>> get() = _newsState

    private val _savedStatuses = MutableLiveData<Map<String, Boolean>>()
    val savedStatuses: LiveData<Map<String, Boolean>> get() = _savedStatuses


    fun toggleArticleSaved(article: Article) {
        val isCurrentlySaved = savedStatuses.value?.get(article.url) == true
        val updatedMap = _savedStatuses.value.orEmpty().toMutableMap()
        updatedMap[article.url] = !isCurrentlySaved
        _savedStatuses.value = updatedMap


        viewModelScope.launch {
            try {
                if (isCurrentlySaved) {
                    deleteArticleUseCase(article)
                    Log.d("DetailsViewModel", "Deleted: ${article.title}")
                } else {
                    saveArticleUseCase(article)
                    Log.d("DetailsViewModel", "Saved: ${article.title}")
                }
            } catch (e: Exception) {
                updatedMap[article.url] = isCurrentlySaved
                _savedStatuses.postValue(updatedMap)
                Log.e("DetailsViewModel", "Toggle failed: ${e.message}")
            }
        }
    }

    fun checkIfArticleSaved(url: String) {
        viewModelScope.launch {
            val isSaved = isArticleSavedUseCase(url)
            val updatedMap = _savedStatuses.value.orEmpty().toMutableMap()
            updatedMap[url] = isSaved
            _savedStatuses.value = updatedMap
        }
    }


}