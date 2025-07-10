package com.silverkey.domain.utils

// domain/src/main/java/com/silverkey/domain/utils/Result.kt
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    data object Loading : Result<Nothing>()
    data object Empty : Result<Nothing>()
}