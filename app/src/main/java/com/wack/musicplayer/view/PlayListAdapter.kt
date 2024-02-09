package com.wack.musicplayer.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wack.musicplayer.R
import com.wack.musicplayer.model.MusicModel

class PlayListAdapter(private val callback: (MusicModel) -> Unit) : ListAdapter<MusicModel, PlayListAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private  val view: View): RecyclerView.ViewHolder(view) {
        fun bind(item: MusicModel) {
            val trackTitleTv = view.findViewById<TextView>(R.id.item_title_tv)
            val singerTv = view.findViewById<TextView>(R.id.item_singer_tv)
            val coverIv = view.findViewById<ImageView>(R.id.item_cover_iv)

            trackTitleTv.text = item.track
            singerTv.text = item.singer

            Glide.with(coverIv.context)
                .load(item.coverUrl)
                .into(coverIv)

            if (item.isPlaying) {
                itemView.setBackgroundColor(Color.GRAY)
            } else {
                itemView.setBackgroundColor(Color.TRANSPARENT)
            }

            itemView.setOnClickListener {
                callback(item)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_music, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        currentList[position].also { musicModel ->
            holder.bind(musicModel)
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MusicModel>() {
            override fun areItemsTheSame(oldItem: MusicModel, newItem: MusicModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MusicModel, newItem: MusicModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}