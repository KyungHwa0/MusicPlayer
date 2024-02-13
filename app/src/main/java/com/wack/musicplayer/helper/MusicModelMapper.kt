package com.wack.musicplayer.helper

import com.wack.musicplayer.controller.PlayerController
import com.wack.musicplayer.model.MusicDto
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

fun MusicDto.mapper(): PlayerController =
    PlayerController(
        playMusicList = music.mapIndexed { index, musicEntity ->
            musicEntity.mapper(index.toLong())
        }
    )