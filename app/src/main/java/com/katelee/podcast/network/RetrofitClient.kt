package com.katelee.podcast.network

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit



/**
 * Created by Kate on 2020-04-15.
 */

object RetrofitClient {
    private const val BASE_URL = "http://demo4491005.mockable.io/"

    val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}