package com.wack.musicplayer.model

data class MusicModel(
    val id: Long,
    val track: String,
    val streamUrl: String,
    val singer: String,
    val coverUrl: String,
    val isPlaying: Boolean = false
)
