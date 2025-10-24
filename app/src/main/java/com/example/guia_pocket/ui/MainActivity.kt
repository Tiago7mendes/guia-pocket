package com.example.guia_pocket.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.guia_pocket.R
import com.example.guia_pocket.adapter.MusicaAdapter
import com.example.guia_pocket.databinding.ActivityMainBinding
import com.example.guia_pocket.model.Musica
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var musicas: List<Musica>
    private var darkMode = false
    private var idiomaAtual = "pt"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🔹 Configura lista e dados
        loadData()
        setupList()

        // 🔹 Controle de tema (claro/escuro)
        var darkMode = false

        binding.btnTema.setOnClickListener {
            darkMode = !darkMode
            if (darkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.btnTema.text = getString(R.string.btn_tema_claro)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.btnTema.text = getString(R.string.btn_tema_escuro)
            }
        }

        var idiomaAtual = "pt"
        binding.btnIdioma.setOnClickListener {
            idiomaAtual = if (idiomaAtual == "pt") "en" else "pt"
            mudarIdioma(idiomaAtual)
        }
    }

    private fun mudarIdioma(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        recreate()
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
                "https://open.spotify.com/intl-pt/track/3QaPy1KgI7nu9FJEQUgn6h", // substitua pelo link real se quiser
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
                R.drawable.lewis, "Before You Go", "Lewis Capaldi", "Pop Soul",
                "Um hino melancólico sobre amor e perda, lançado em 2019.",
                "https://open.spotify.com/track/7qEHsqek33rTcFNT9PFqLf",
                R.raw.before_you_go
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