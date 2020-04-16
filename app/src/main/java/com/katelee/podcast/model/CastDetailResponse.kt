package com.katelee.podcast.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Kate on 2020-04-16.
 */

class CastDetailResponse {
    @SerializedName("data")
    val data: Data ?= null
    class Data {
        @SerializedName("collection")
        val collection: CastDetail ?= null
    }
}