package com.alvarengadev.movieschallenge.utils.exceptions

import com.alvarengadev.movieschallenge.R
import com.blankj.utilcode.util.StringUtils
import java.net.HttpURLConnection

class ResponseErrorHttpException(
    private val code: Int? = null
) : Exception() {
    override val message: String
        get() = when (code) {
            HttpURLConnection.HTTP_INTERNAL_ERROR -> StringUtils.getString(R.string.error_server_internal_message)
            else -> StringUtils.getString(R.string.error_generic_message)
        }
}