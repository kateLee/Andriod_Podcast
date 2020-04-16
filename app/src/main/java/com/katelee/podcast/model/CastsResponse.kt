package com.katelee.podcast.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Kate on 2020-04-15.
 */

class CastsResponse {
    @SerializedName("data")
    val data: Data ?= null
    class Data {
        @SerializedName("podcast")
        val podcast: ArrayList<Cast> ?= null
    }
}