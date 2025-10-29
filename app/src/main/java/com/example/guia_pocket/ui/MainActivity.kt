package com.example.guia_pocket.ui

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.guia_pocket.R
import com.example.guia_pocket.adapter.MusicaAdapter
import com.example.guia_pocket.databinding.ActivityMainBinding
import com.example.guia_pocket.model.Musica
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var musicas: List<Musica>

    // chaves para SharedPreferences
    private val PREFS_NAME = "app_prefs"
    private val KEY_THEME = "pref_theme"    // "dark" ou "light"
    private val KEY_LANG = "pref_lang"      // "pt" ou "en"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applySavedTheme()
        applySavedLocale()
        loadData()
        setupList()
        initButtonsText()

        // 4) Listeners
        binding.btnTema.setOnClickListener {
            toggleTheme()
        }

        binding.btnIdioma.setOnClickListener {
            toggleLanguage()
        }
    }
    private fun applySavedTheme() {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val theme = prefs.getString(KEY_THEME, "light") ?: "light"
        if (theme == "dark") {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun toggleTheme() {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val current = prefs.getString(KEY_THEME, "light") ?: "light"
        val new = if (current == "dark") "light" else "dark"

        prefs.edit().putString(KEY_THEME, new).apply()

        if (new == "dark") {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        updateThemeButtonText(new)
    }

    private fun updateThemeButtonText(themeValue: String) {
        // usa strings definidas nos resources
        if (themeValue == "dark") {
            binding.btnTema.text = getString(R.string.btn_tema_claro) // texto sugerido quando está em dark: "Tema Claro"
        } else {
            binding.btnTema.text = getString(R.string.btn_tema_escuro) // texto sugerido quando está em light: "Tema Escuro"
        }
    }

    private fun initButtonsText() {
        // inicializa texto do botão de tema
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val theme = prefs.getString(KEY_THEME, "light") ?: "light"
        updateThemeButtonText(theme)

        // inicializa texto do botão de idioma
        val lang = prefs.getString(KEY_LANG, "pt") ?: "pt"
        binding.btnIdioma.text = if (lang == "pt") getString(R.string.btn_idioma) else getString(R.string.btn_idioma)
    }
    private fun applySavedLocale() {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val lang = prefs.getString(KEY_LANG, "pt") ?: "pt"
        applyLocale(lang, save = false) // aplicar sem salvar de novo
    }

    private fun toggleLanguage() {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val current = prefs.getString(KEY_LANG, "pt") ?: "pt"
        val newLang = if (current == "pt") "en" else "pt"
        prefs.edit().putString(KEY_LANG, newLang).apply()
        applyLocale(newLang, save = true)
    }

    private fun applyLocale(lang: String, save: Boolean) {
        try {
            val localeList = LocaleListCompat.forLanguageTags(lang)
            AppCompatDelegate.setApplicationLocales(localeList) // aplica globalmente
            return
        } catch (e: Exception) {
        }
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        recreate()
    }
    private fun loadData() {
        musicas = listOf(
            Musica(
                R.drawable.edsheeran,
                "Perfect",
                "Ed Sheeran",
                getString(R.string.genre_romantic_pop),
                getString(R.string.desc_perfect),
                "https://open.spotify.com/track/0tgVpDi06FyKpA1z0VMD4v",
                R.raw.perfect
            ),
            Musica(
                R.drawable.billie,
                "Wildflower",
                "Billie Eilish",
                getString(R.string.genre_alternative_pop),
                getString(R.string.desc_wildflower),
                "https://open.spotify.com/intl-pt/track/3QaPy1KgI7nu9FJEQUgn6h",
                R.raw.wildflower
            ),
            Musica(
                R.drawable.coldplay,
                "Yellow",
                "Coldplay",
                getString(R.string.genre_soft_rock),
                getString(R.string.desc_yellow),
                "https://open.spotify.com/track/3AJwUDP919kvQ9QcozQPxg",
                R.raw.yellow
            ),
            Musica(
                R.drawable.dualipa,
                "Levitating",
                "Dua Lipa",
                getString(R.string.genre_dance_pop),
                getString(R.string.desc_levitating),
                "https://open.spotify.com/track/463CkQjx2Zk1yXoBuierM9",
                R.raw.levitating
            ),
            Musica(
                R.drawable.lewis,
                "Before You Go",
                "Lewis Capaldi",
                getString(R.string.genre_pop_soul),
                getString(R.string.desc_before_you_go),
                "https://open.spotify.com/track/7qEHsqek33rTcFNT9PFqLf",
                R.raw.before_you_go
            ),
            Musica(
                R.drawable.arctic,
                "Do I Wanna Know?",
                "Arctic Monkeys",
                getString(R.string.genre_indie_rock),
                getString(R.string.desc_do_i_wanna_know),
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