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
        setupList()
    }

    private fun loadData() {
        musicas = listOf(
            Musica(
                R.drawable.edsheeran, "Perfect", "Ed Sheeran", "Romantic Pop",
                "Uma balada emocionante do álbum Divide (2017).",
                "https://open.spotify.com/track/0tgVpDi06FyKpA1z0VMD4v",
                R.raw.perfect
            ),
            Musica(
                R.drawable.billie, "Wildflower", "Billie Eilish", "Alternative Pop",
                "Uma faixa suave e atmosférica, com vocais marcantes e melodia envolvente.",
                "https://open.spotify.com/track/xyz", // substitua pelo link real se quiser
                R.raw.wildflower
            ),
            Musica(
                R.drawable.coldplay, "Yellow", "Coldplay", "Soft Rock",
                "Um dos maiores sucessos do Coldplay, símbolo da sonoridade emocional da banda.",
                "https://open.spotify.com/track/3AJwUDP919kvQ9QcozQPxg",
                R.raw.yellow
            ),
            Musica(
                R.drawable.dualipa, "Levitating", "Dua Lipa", "Dance Pop",
                "Uma faixa vibrante e retrô do álbum Future Nostalgia (2020).",
                "https://open.spotify.com/track/463CkQjx2Zk1yXoBuierM9",
                R.raw.levitating
            ),
            Musica(
                R.drawable.lewis, "Someone You Loved", "Lewis Capaldi", "Pop Soul",
                "Um hino melancólico sobre amor e perda, lançado em 2019.",
                "https://open.spotify.com/track/7qEHsqek33rTcFNT9PFqLf",
                R.raw.someone_you_loved
            ),
            Musica(
                R.drawable.arctic, "Do I Wanna Know?", "Arctic Monkeys", "Indie Rock",
                "Uma faixa icônica com ritmo marcante e guitarra envolvente.",
                "https://open.spotify.com/track/5FVd6KXrgO9B3JPmC8OPst",
                R.raw.do_i_wanna_know
            )
        ).sortedBy { it.nome }
    }

    private fun setupList() {
        val adapter = MusicaAdapter(this, musicas)
        binding.listViewMusicas.adapter = adapter

        binding.listViewMusicas.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, DetalheMusicaActivity::class.java)
            intent.putExtra("musica", musicas[position])
            startActivity(intent)
        }
    }
}