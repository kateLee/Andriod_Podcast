package com.katelee.podcast.network

import com.katelee.podcast.model.CastDetailResponse
import com.katelee.podcast.model.CastsResponse
import retrofit2.http.GET

/**
 * Created by Kate on 2020-04-15.
 */

interface CastService {
    @GET("getcasts")
    suspend fun getCasts(): CastsResponse

    @GET("getcastdetail")
    suspend fun getCastDetail(): CastDetailResponse
}