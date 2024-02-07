package com.wack.musicplayer.service

import com.wack.musicplayer.model.MusicDto
import retrofit2.Call
import retrofit2.http.GET

interface MusicService {

    @GET("/v3/fc4b0907-7687-4ecb-af4d-55fc162346f9")
    fun listMusic() : Call<MusicDto>
}