package com.example.guia_pocket.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.guia_pocket.databinding.ActivityDetalheMusicaBinding
import com.example.guia_pocket.model.Musica

class DetalheMusicaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetalheMusicaBinding
    private lateinit var musica: Musica

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalheMusicaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadData()
        setupViews()
        setupListeners()
    }

    private fun loadData() {
        musica = intent.getSerializableExtra("musica") as Musica
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
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(musica.linkSpotify))
            startActivity(intent)
        }

        binding.btnShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "🎵 ${musica.nome} - ${musica.artista}\n${musica.linkSpotify}")
            startActivity(Intent.createChooser(shareIntent, "Compartilhar música"))
        }

        binding.btnVoltar.setOnClickListener { finish() }
    }
}