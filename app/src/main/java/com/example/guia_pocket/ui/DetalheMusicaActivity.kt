package com.example.guia_pocket.ui

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.guia_pocket.databinding.ActivityDetalheMusicaBinding
import com.example.guia_pocket.model.Musica

class DetalheMusicaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalheMusicaBinding
    private lateinit var musica: Musica
    private var player: MediaPlayer? = null
    private var tocando = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalheMusicaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        musica = intent.getSerializableExtra("musica") as Musica

        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        binding.imgCapa.setImageResource(musica.capa)
        binding.tvNome.text = musica.nome
        binding.tvArtista.text = musica.artista
        binding.tvGenero.text = musica.genero
        binding.tvDescricao.text = musica.descricao
    }

    private fun setupListeners() {
        binding.btnSpotify.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(musica.linkSpotify)))
        }

        binding.btnPlay.setOnClickListener {
            if (!tocando) {
                player = MediaPlayer.create(this, musica.audioResId)
                player?.start()
                tocando = true
                binding.btnPlay.text = "⏸️ Pausar"
            } else {
                player?.pause()
                tocando = false
                binding.btnPlay.text = "▶️ Tocar"
            }
        }

        binding.btnShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "🎵 ${musica.nome} - ${musica.artista}\n${musica.linkSpotify}")
            startActivity(Intent.createChooser(shareIntent, "Compartilhar música"))
        }

        binding.btnVoltar.setOnClickListener {
            player?.release()
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
    }
}