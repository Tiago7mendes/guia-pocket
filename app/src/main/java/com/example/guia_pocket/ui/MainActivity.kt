package com.example.guia_pocket.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.guia_pocket.R
import com.example.guia_pocket.adapter.MusicaAdapter
import com.example.guia_pocket.databinding.ActivityMainBinding
import com.example.guia_pocket.model.Musica

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var musicas: List<Musica>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadData()
        setupViews()
        setupListeners()
    }

    private fun loadData() {
        musicas = listOf(
            Musica(R.drawable.coldplay, "Viva La Vida", "Coldplay", "Pop"),
            Musica(R.drawable.weeknd, "Blinding Lights", "The Weeknd", "Synthwave"),
            Musica(R.drawable.harrystyles, "As It Was", "Harry Styles", "Pop Rock"),
            Musica(R.drawable.edsheeran, "Shape of You", "Ed Sheeran", "Pop"),
            Musica(R.drawable.billie, "Bad Guy", "Billie Eilish", "Alternative"),
            Musica(R.drawable.dualipa, "Levitating", "Dua Lipa", "Dance Pop")
        ).sortedBy { it.nome }
    }

    private fun setupViews() {
        val adapter = MusicaAdapter(this, musicas)
        binding.listViewMusicas.adapter = adapter
    }

    private fun setupListeners() {
        binding.listViewMusicas.setOnItemClickListener { _, _, position, _ ->
            val musica = musicas[position]
            Toast.makeText(this, "🎵 Tocando: ${musica.nome}", Toast.LENGTH_SHORT).show()
        }
    }
}