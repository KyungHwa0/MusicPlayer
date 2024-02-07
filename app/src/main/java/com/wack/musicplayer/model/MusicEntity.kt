package com.wack.musicplayer.model

import com.google.gson.annotations.SerializedName

data class MusicEntity (
    @SerializedName("track")
    val track: String,
    @SerializedName("streamUrl")
    val streamUrl: String,
    @SerializedName("singer")
    val singer: String,
    @SerializedName("coverUrl")
    val coverUrl: String
)