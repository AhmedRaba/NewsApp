package com.silverkey.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silverkey.domain.model.Article
import com.silverkey.domain.usecase.FetchNewsUseCase
import com.silverkey.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchNewsUseCase: FetchNewsUseCase,
) : ViewModel() {

    private val _newsState = MutableLiveData<Result<List<Article>>>()
    val newsState: LiveData<Result<List<Article>>> get() = _newsState

    init {
        fetchNews()
    }

    fun fetchNews() {
        viewModelScope.launch {
            _newsState.value = Result.Loading
            try {
                _newsState.value = fetchNewsUseCase()
            } catch (e: Exception) {
                _newsState.value = Result.Error(e)
            }
        }
    }


}