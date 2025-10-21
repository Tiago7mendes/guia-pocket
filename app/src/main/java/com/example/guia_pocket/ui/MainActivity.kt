package com.example.guia_pocket.ui

import android.content.Intent
import android.os.Bundle
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
            Musica(
                R.drawable.coldplay, "Viva La Vida", "Coldplay", "Pop Rock",
                "Um dos maiores sucessos da banda britânica, lançado em 2008.",
                "https://open.spotify.com/track/1mea3bSkSGXuIRvnydlB5b"
            ),
            Musica(
                R.drawable.weeknd, "Blinding Lights", "The Weeknd", "Synthwave",
                "Hit mundial com forte influência dos anos 80.",
                "https://open.spotify.com/track/0VjIjW4GlUZAMYd2vXMi3b"
            ),
            Musica(
                R.drawable.billie, "Bad Guy", "Billie Eilish", "Alternative Pop",
                "Som minimalista e provocante que redefiniu o pop alternativo.",
                "https://open.spotify.com/track/2Fxmhks0bxGSBdJ92vM42m"
            )
        ).sortedBy { it.nome }
    }

    private fun setupViews() {
        val adapter = MusicaAdapter(this, musicas)
        binding.listViewMusicas.adapter = adapter
    }

    private fun setupListeners() {
        binding.listViewMusicas.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, DetalheMusicaActivity::class.java)
            intent.putExtra("musica", musicas[position])
            startActivity(intent)
        }
    }
}