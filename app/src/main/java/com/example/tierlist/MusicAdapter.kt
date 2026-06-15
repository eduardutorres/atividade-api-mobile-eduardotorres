package com.example.tierlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MusicAdapter(
    private val context: Context,
    private val musicList: List<Music>,
    private val onAddClick: (Music) -> Unit
) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    inner class MusicViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivArtwork: ImageView = view.findViewById(R.id.ivArtwork)
        val tvTrackName: TextView = view.findViewById(R.id.tvTrackName)
        val tvAlbum: TextView = view.findViewById(R.id.tvAlbum)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_music, parent, false)
        return MusicViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val music = musicList[position]
        holder.tvTrackName.text = music.trackName
        holder.tvAlbum.text = "📀 ${music.albumName}"

        val artworkUrl = music.artworkUrl.replace("100x100", "300x300")
        Glide.with(context)
            .load(artworkUrl)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(holder.ivArtwork)
    }

    override fun getItemCount() = musicList.size
}