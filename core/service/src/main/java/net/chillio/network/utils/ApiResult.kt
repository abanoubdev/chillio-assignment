package net.chillio.network.utils

/**
 * A sealed interface representing the different states of an API call.
 * This is a generic class that can hold any type of data.
 *
 * @param T The type of data expected from a successful API call.
 */
sealed interface ApiResult<out T> {
    data object Loading : ApiResult<Nothing>

    data object Empty : ApiResult<Nothing>

    data class Success<T>(val data: T) : ApiResult<T>

    data class Error(val throwable: Throwable) : ApiResult<Nothing>
}
