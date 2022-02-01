package com.alvarengadev.movieschallenge.utils.remote

data class ResultUtils<out T>(
    val status: Status,
    val data: T?,
    val message: String?
) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(
            data: T
        ): ResultUtils<T> {
            return ResultUtils(Status.SUCCESS, data, null)
        }

        fun <T> error(
            message: String?,
            data: T? = null
        ): ResultUtils<T> {
            return ResultUtils(Status.ERROR, data, message)
        }

        fun <T> loading(
            data: T? = null
        ): ResultUtils<T> {
            return ResultUtils(Status.LOADING, data, null)
        }
    }
}