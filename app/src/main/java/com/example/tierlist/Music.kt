package com.example.tierlist

data class Music(
    val trackName: String,
    val artistName: String,
    val albumName: String,
    val artworkUrl: String,
    var tier: String = "?"
)