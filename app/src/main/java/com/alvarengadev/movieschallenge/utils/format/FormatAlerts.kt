package com.alvarengadev.movieschallenge.utils.format

import com.alvarengadev.movieschallenge.R
import com.blankj.utilcode.util.StringUtils
import java.net.HttpURLConnection

fun codeResponseToString(
    code: Int? = null
): String {
    return when (code) {
        HttpURLConnection.HTTP_INTERNAL_ERROR -> StringUtils.getString(R.string.error_server_internal_message)
        else -> StringUtils.getString(R.string.error_generic_message)
    }
}

fun errorMoviesFavoritesToString(
    isEmpty: Boolean? = null
): String {
    return if (isEmpty == true)
        StringUtils.getString(R.string.error_list_favorite_is_empty)
    else
        StringUtils.getString(R.string.error_get_list_favorite)
}