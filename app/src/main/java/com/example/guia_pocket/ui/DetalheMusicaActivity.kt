package com.example.guia_pocket.ui

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.guia_pocket.databinding.ActivityDetalheMusicaBinding
import com.example.guia_pocket.model.Musica

class DetalheMusicaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalheMusicaBinding
    private lateinit var musica: Musica
    private var player: MediaPlayer? = null
    private var tocando = false
    private val handler = Handler(Looper.getMainLooper())

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
        binding.imgMiniCapa.setImageResource(musica.capa)
        binding.tvNome.text = musica.nome
        binding.tvArtista.text = musica.artista
        binding.tvMiniNome.text = musica.nome
        binding.tvMiniArtista.text = musica.artista
        binding.tvGenero.text = musica.genero
        binding.tvDescricao.text = musica.descricao
    }

    private fun setupListeners() {
        // Botão Spotify
        binding.btnSpotify.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(musica.linkSpotify)))
        }

        // Botão compartilhar
        binding.btnShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "🎵 ${musica.nome} - ${musica.artista}\n${musica.linkSpotify}")
            startActivity(Intent.createChooser(shareIntent, getString(com.example.guia_pocket.R.string.msg_share_title)))
        }

        // Botão voltar
        binding.btnVoltar.setOnClickListener {
            player?.release()
            finish()
        }

        // 🎵 Mini player
        binding.btnMiniPlay.setOnClickListener {
            if (!tocando) {
                if (player == null) {
                    player = MediaPlayer.create(this, musica.audioResId)
                    player?.setOnCompletionListener {
                        tocando = false
                        binding.btnMiniPlay.text = "▶️"
                        binding.progressoMusica.progress = 0
                    }
                }
                player?.start()
                tocando = true
                binding.btnMiniPlay.text = "⏸️"
                atualizarProgresso()
            } else {
                player?.pause()
                tocando = false
                binding.btnMiniPlay.text = "▶️"
            }
        }

        binding.btnMiniStop.setOnClickListener {
            player?.stop()
            player?.release()
            player = null
            tocando = false
            binding.btnMiniPlay.text = "▶️"
            binding.progressoMusica.progress = 0
        }
    }

    private fun atualizarProgresso() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                player?.let {
                    val progresso = (it.currentPosition * 100) / it.duration
                    binding.progressoMusica.progress = progresso
                    if (tocando) handler.postDelayed(this, 500)
                }
            }
        }, 500)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
        player?.release()
        player = null
    }
}