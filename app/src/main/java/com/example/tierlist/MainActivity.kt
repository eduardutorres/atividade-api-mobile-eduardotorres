package com.example.tierlist

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {

    private lateinit var etArtist: EditText
    private lateinit var btnSearch: Button
    private lateinit var tvStatus: TextView
    private lateinit var areaResultado: LinearLayout

    private lateinit var containerS: LinearLayout
    private lateinit var containerA: LinearLayout
    private lateinit var containerB: LinearLayout
    private lateinit var containerC: LinearLayout
    private lateinit var containerD: LinearLayout
    private lateinit var containerE: LinearLayout

    private val musicList = mutableListOf<Music>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etArtist = findViewById(R.id.etArtist)
        btnSearch = findViewById(R.id.btnSearch)
        tvStatus = findViewById(R.id.tvStatus)
        areaResultado = findViewById(R.id.areaResultado)

        containerS = findViewById(R.id.containerS)
        containerA = findViewById(R.id.containerA)
        containerB = findViewById(R.id.containerB)
        containerC = findViewById(R.id.containerC)
        containerD = findViewById(R.id.containerD)
        containerE = findViewById(R.id.containerE)

        btnSearch.setOnClickListener {
            val artist = etArtist.text.toString().trim()
            if (artist.isEmpty()) {
                Toast.makeText(this, "⚠️ Digite o nome de um artista!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            searchMusic(artist)
        }
    }

    private fun searchMusic(artist: String) {
        tvStatus.text = "🔍 Buscando..."
        btnSearch.isEnabled = false
        areaResultado.visibility = View.GONE

        val encoded = URLEncoder.encode(artist, "UTF-8")
        val url = "https://itunes.apple.com/search?term=$encoded&media=music&entity=song&limit=10&country=br"

        val queue = Volley.newRequestQueue(this)

        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    val results = response.getJSONArray("results")

                    if (results.length() == 0) {
                        tvStatus.text = "Nenhuma música encontrada para \"$artist\"."
                        btnSearch.isEnabled = true
                        return@JsonObjectRequest
                    }

                    musicList.clear()
                    areaResultado.removeAllViews()

                    for (i in 0 until results.length()) {
                        val item = results.getJSONObject(i)
                        val track = item.optString("trackName", "Sem título")
                        val artistName = item.optString("artistName", "Desconhecido")
                        val album = item.optString("collectionName", "Álbum desconhecido")
                        val artwork = item.optString("artworkUrl100", "")
                        musicList.add(Music(track, artistName, album, artwork))
                    }

                    areaResultado.visibility = View.VISIBLE

                    for (music in musicList) {
                        val itemView = LayoutInflater.from(this)
                            .inflate(R.layout.item_music, areaResultado, false)

                        val ivArtwork = itemView.findViewById<ImageView>(R.id.ivArtwork)
                        val tvTrackName = itemView.findViewById<TextView>(R.id.tvTrackName)
                        val tvAlbum = itemView.findViewById<TextView>(R.id.tvAlbum)
                        val btnAddToTier = itemView.findViewById<Button>(R.id.btnAddToTier)

                        tvTrackName.text = music.trackName
                        tvAlbum.text = "📀 ${music.albumName}"

                        val artworkUrl = music.artworkUrl.replace("100x100", "300x300")
                        Glide.with(this).load(artworkUrl)
                            .placeholder(android.R.drawable.ic_menu_gallery)
                            .into(ivArtwork)

                        btnAddToTier.setOnClickListener {
                            showTierDialog(music)
                        }

                        areaResultado.addView(itemView)
                    }

                    tvStatus.text = "${musicList.size} músicas encontradas. Adicione aos tiers!"

                } catch (e: Exception) {
                    tvStatus.text = "❌ Erro ao processar os dados."
                }

                btnSearch.isEnabled = true
            },
            { _ ->
                tvStatus.text = "❌ Erro na conexão. Verifique sua internet."
                btnSearch.isEnabled = true
            }
        )

        queue.add(request)
    }

    private fun showTierDialog(music: Music) {
        val tiers = arrayOf("S", "A", "B", "C", "D", "E")

        AlertDialog.Builder(this)
            .setTitle("Adicionar \"${music.trackName}\" ao tier:")
            .setItems(tiers) { _, which ->
                val selectedTier = tiers[which]
                addMusicToTier(music, selectedTier)
            }
            .show()
    }

    private fun addMusicToTier(music: Music, tier: String) {
        val container = when (tier) {
            "S" -> containerS
            "A" -> containerA
            "B" -> containerB
            "C" -> containerC
            "D" -> containerD
            "E" -> containerE
            else -> return
        }

        val imageView = ImageView(this).apply {
            layoutParams = LinearLayout.LayoutParams(65, 65).apply {
                setMargins(4, 4, 4, 4)
            }
            scaleType = ImageView.ScaleType.CENTER_CROP
        }

        val artworkUrl = music.artworkUrl.replace("100x100", "300x300")
        Glide.with(this).load(artworkUrl)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(imageView)

        imageView.setOnClickListener {
            Toast.makeText(this, "${music.trackName} — ${music.artistName}", Toast.LENGTH_SHORT).show()
        }

        container.addView(imageView)
        Toast.makeText(this, "\"${music.trackName}\" adicionada ao tier $tier!", Toast.LENGTH_SHORT).show()
    }
}