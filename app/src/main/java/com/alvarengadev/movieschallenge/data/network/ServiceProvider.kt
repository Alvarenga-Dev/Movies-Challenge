package com.alvarengadev.movieschallenge.data.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val baseUrl = "https://api.themoviedb.org/3/"

object ServiceProvider {

    private val gson = GsonBuilder().create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun <API> create(apiClass: Class<API>): API = retrofit.create(apiClass)
}