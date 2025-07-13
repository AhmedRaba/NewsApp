package com.silverkey.newsapp.ui.saved

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silverkey.domain.model.Article
import com.silverkey.domain.usecase.DeleteArticleUseCase
import com.silverkey.domain.usecase.GetSavedArticlesUseCase
import com.silverkey.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedNewsViewModel @Inject constructor(
    private val getSavedArticlesUseCase: GetSavedArticlesUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase,
) : ViewModel() {

    private val _savedArticlesState = MutableLiveData<Result<List<Article>>>()
    val savedArticlesState: LiveData<Result<List<Article>>> get() = _savedArticlesState

    init {
        getSavedArticles()
    }

   fun getSavedArticles() {
        viewModelScope.launch {
            val result = getSavedArticlesUseCase()
            _savedArticlesState.value = result
        }
    }

    fun deleteArticle(article: Article) {
        viewModelScope.launch {
            deleteArticleUseCase(article)
            getSavedArticles()
        }
    }
}
