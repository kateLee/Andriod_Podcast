package com.katelee.podcast.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Kate on 2020-04-16.
 */

class CastDetail {
    @SerializedName("artistName")
    val artistName: String = ""
    @SerializedName("artworkUrl100")
    val artworkUrlThumbnail: String = ""
    @SerializedName("artworkUrl600")
    val artworkUrl: String = ""
    @SerializedName("collectionId")
    val id: String = ""
    @SerializedName("collectionName")
    val name: String = ""
    @SerializedName("contentFeed")
    val contentFeed: ArrayList<ContentFeed> ?= null
    class ContentFeed {
        @SerializedName("contentUrl")
        val url: String = ""
        @SerializedName("title")
        val title: String = ""
    }
}
