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
            Musica(R.drawable.coldplay, "Viva La Vida", "Coldplay", "Pop Rock", "Um dos maiores sucessos do Coldplay, lançado em 2008 no álbum Viva la Vida or Death and All His Friends."),
            Musica(R.drawable.weeknd, "Blinding Lights", "The Weeknd", "Synthwave", "Single de sucesso mundial que marcou o retorno do estilo oitentista à música pop."),
            Musica(R.drawable.harrystyles, "As It Was", "Harry Styles", "Pop", "Um hit melódico e introspectivo sobre mudança e crescimento pessoal."),
            Musica(R.drawable.edsheeran, "Shape of You", "Ed Sheeran", "Pop", "Faixa mais popular de Ed Sheeran, misturando pop e elementos de dancehall."),
            Musica(R.drawable.billie, "Bad Guy", "Billie Eilish", "Alternative", "Um som minimalista e provocante que redefiniu o pop alternativo."),
            Musica(R.drawable.dualipa, "Levitating", "Dua Lipa", "Dance Pop", "Uma faixa vibrante e retrô do álbum Future Nostalgia, perfeita para dançar.")
        ).sortedBy { it.nome }
    }

    private fun setupViews() {
        val adapter = MusicaAdapter(this, musicas)
        binding.listViewMusicas.adapter = adapter
    }

    private fun setupListeners() {
        binding.listViewMusicas.setOnItemClickListener { _, _, position, _ ->
            val musica = musicas[position]
            Toast.makeText(this, "🎶 Tocando: ${musica.nome} - ${musica.artista}", Toast.LENGTH_SHORT).show()
        }
    }
}