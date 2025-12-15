package com.example.guia_pocket.ui

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
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

        loadData()
        setupViews()
        setupListeners()
    }

    // 🔹 Igual ao professor
    private fun loadData() {
        musica = intent.getSerializableExtra("musica", Musica::class.java) as Musica
    }

    // 🔹 Igual ao professor
    private fun setupViews() {
        binding.tvNome.text = musica.nome
        binding.tvArtista.text = musica.artista
        binding.tvGenero.text = musica.genero
        binding.tvDescricao.text = musica.descricao

        binding.tvMiniNome.text = musica.nome
        binding.tvMiniArtista.text = musica.artista

        // CAPA via URI (igual foto do prato)
        binding.imgCapa.setImageURI(musica.capaUri.toUri())
        binding.imgMiniCapa.setImageURI(musica.capaUri.toUri())
    }

    // 🔹 Igual ao professor
    private fun setupListeners() {

        // Spotify
        binding.btnSpotify.setOnClickListener {
            if (musica.linkSpotify.isNotBlank()) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = musica.linkSpotify.toUri()
                startActivity(intent)
            }
        }

        // Compartilhar
        binding.btnShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "🎵 ${musica.nome} - ${musica.artista}\n${musica.linkSpotify}"
            )
            startActivity(Intent.createChooser(intent, "Compartilhar música"))
        }

        // Voltar
        binding.btnVoltar.setOnClickListener {
            liberarPlayer()
            finish()
        }

        // ▶️ Mini Player — PLAY / PAUSE
        binding.btnMiniPlay.setOnClickListener {
            if (!tocando) {
                if (player == null) {
                    player = MediaPlayer().apply {
                        setDataSource(this@DetalheMusicaActivity, musica.audioUri.toUri())
                        prepare()
                        setOnCompletionListener {
                            tocando = false
                            binding.btnMiniPlay.text = "▶️"
                            binding.progressoMusica.progress = 0
                        }
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

        // ⏹ STOP
        binding.btnMiniStop.setOnClickListener {
            liberarPlayer()
            binding.progressoMusica.progress = 0
            binding.btnMiniPlay.text = "▶️"
        }
    }

    private fun atualizarProgresso() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                player?.let {
                    val progresso = (it.currentPosition * 100) / it.duration
                    binding.progressoMusica.progress = progresso
                    if (tocando) {
                        handler.postDelayed(this, 500)
                    }
                }
            }
        }, 500)
    }

    private fun liberarPlayer() {
        tocando = false
        handler.removeCallbacksAndMessages(null)
        player?.release()
        player = null
    }

    override fun onDestroy() {
        super.onDestroy()
        liberarPlayer()
    }
}
