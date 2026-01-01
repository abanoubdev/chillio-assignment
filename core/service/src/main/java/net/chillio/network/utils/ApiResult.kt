package net.chillio.network.utils

sealed interface ApiResult<out T> {
    data object Loading : ApiResult<Nothing>

    data object Empty : ApiResult<Nothing>

    data class Success<T>(val data: T) : ApiResult<T>

    data class Error(val throwable: Throwable) : ApiResult<Nothing>
}
