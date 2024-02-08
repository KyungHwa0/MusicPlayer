package com.wack.musicplayer.helper

import com.wack.musicplayer.model.MusicEntity
import com.wack.musicplayer.model.MusicModel

fun MusicEntity.mapper(id: Long): MusicModel =
    MusicModel(
        id = id,
        streamUrl = streamUrl,
        coverUrl = coverUrl,
        track = track,
        singer = singer
    )