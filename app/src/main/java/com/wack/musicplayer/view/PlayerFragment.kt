package com.wack.musicplayer.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wack.musicplayer.R
import com.wack.musicplayer.databinding.FragmentPlayerBinding
import com.wack.musicplayer.helper.mapper
import com.wack.musicplayer.model.MusicDto
import com.wack.musicplayer.model.MusicModel
import com.wack.musicplayer.service.MusicService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlayerFragment : Fragment(R.layout.fragment_player) {

    private var binding: FragmentPlayerBinding? = null
    private var isWatchingPlayListView = true
    private lateinit var playListAdapter: PlayListAdapter
    private var player: ExoPlayer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentPlayerBinding = FragmentPlayerBinding.bind(view)
        binding = fragmentPlayerBinding

        initPlayView(fragmentPlayerBinding)
        initPlayListBtn(fragmentPlayerBinding)
        initPlayControlButtons(fragmentPlayerBinding)
        initRecyclerView(fragmentPlayerBinding)
        getVideoListFromServer()
    }

    private fun initPlayControlButtons(fragmentPlayerBinding: FragmentPlayerBinding) {
        fragmentPlayerBinding.playerPlayIv.setOnClickListener {
            Log.d("PlayerFragment", "Play button clicked")
            val player = this.player ?: return@setOnClickListener

            if (player.isPlaying) {
                player.pause()
            } else {
                player.play()
            }
        }

        fragmentPlayerBinding.playerNextIv.setOnClickListener {
        }

        fragmentPlayerBinding.playerPrevIv.setOnClickListener {
        }
    }

    private fun initPlayView(fragmentPlayerBinding: FragmentPlayerBinding) {

        //초기화
        context?.let {
            player = ExoPlayer.Builder(it).build()
        }
        fragmentPlayerBinding.playerView.player = player

        binding?.let { binding ->
            player?.addListener(object: Player.Listener{
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)

                    if (isPlaying) {
                        binding.playerPlayIv.setImageResource(R.drawable.ic_player_pause_48)
                    } else {
                        binding.playerPlayIv.setImageResource(R.drawable.ic_player_play_48)
                    }
                }
            })
        }
    }

    private fun initRecyclerView(fragmentPlayerBinding: FragmentPlayerBinding) {
        playListAdapter = PlayListAdapter {
            //todo 음악 재생
        }

        fragmentPlayerBinding.playListRv.apply {
            adapter = playListAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initPlayListBtn(fragmentPlayerBinding: FragmentPlayerBinding) {
        fragmentPlayerBinding.playerListIv.setOnClickListener {
            //todo 만약에 서버에서 데이터가 다 불러오지 않은 상태 일 때
            fragmentPlayerBinding.playerViewGroup.isVisible = isWatchingPlayListView
            fragmentPlayerBinding.playListViewGroup.isVisible = isWatchingPlayListView.not()

            isWatchingPlayListView =! isWatchingPlayListView
        }
    }

    private fun getVideoListFromServer() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(MusicService::class.java)
            .also {
                it.listMusic()
                    .enqueue(object : Callback<MusicDto> {
                        override fun onResponse(call: Call<MusicDto>, response: Response<MusicDto>) {
                            Log.d("PlayerFragment", "${response.body()}")

                            response.body()?.let {
                                val modelList = it.music.mapIndexed { index, musicEntity ->
                                    musicEntity.mapper(index.toLong())
                                }

                                setMusicList(modelList)
                                playListAdapter.submitList(modelList)
                            }
                        }

                        override fun onFailure(call: Call<MusicDto>, t: Throwable) {

                        }
                    })
            }
    }

    private fun setMusicList(modelList: List<MusicModel>) {
        context?.let {
            player?.addMediaItems(modelList.map { musicModel ->
                MediaItem.Builder()
                    .setMediaId(musicModel.id.toString())
                    .setUri(musicModel.streamUrl)
                    .build()
            })
            player?.prepare()
            player?.play() // 재생 시작
        }
    }

    companion object {
        fun newInstance(): PlayerFragment {
            return PlayerFragment()
        }
    }
}