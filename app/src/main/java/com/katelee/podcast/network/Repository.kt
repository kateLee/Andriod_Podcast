package com.katelee.podcast.network

/**
 * Created by Kate on 2020-04-15.
 */

class Repository {
    private val apiService = RetrofitClient.retrofit.create(CastService::class.java)
    suspend fun getCasts() = apiService.getCasts()
}