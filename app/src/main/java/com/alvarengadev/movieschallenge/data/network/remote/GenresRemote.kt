package com.alvarengadev.movieschallenge.data.network.remote

import com.google.gson.annotations.SerializedName

data class GenresResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
)

fun List<GenresResponse>.asDomain(): List<String> {
    return map {
        it.name
    }
}