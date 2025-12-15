package com.example.guia_pocket.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.guia_pocket.R
import com.example.guia_pocket.adapter.MusicaAdapter
import com.example.guia_pocket.data.db.AgendaDatabase
import com.example.guia_pocket.databinding.ActivityMainBinding
import com.example.guia_pocket.model.Musica
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var musicas: MutableList<Musica>
    private lateinit var adapter: MusicaAdapter
    private lateinit var launcherCadastro: ActivityResultLauncher<Intent>
    private lateinit var db: AgendaDatabase

    // SharedPreferences
    private val PREFS_NAME = "app_prefs"
    private val KEY_THEME = "pref_theme"
    private val KEY_LANG = "pref_lang"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        applySavedTheme()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadData()
        setupRecyclerView()
        setupLauncherCadastro()
        setupListeners()
        initButtonsText()
    }

    private fun loadData() {
        musicas = mutableListOf()
        db = AgendaDatabase.getInstance(this)

        lifecycleScope.launch(Dispatchers.IO) {
            musicas = db.MusicaDao().filtrarPorNome().toMutableList()
            withContext(Dispatchers.Main) {
                adapter.updateLista(musicas)
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = MusicaAdapter(musicas) { musica ->
            val intent = Intent(this, DetalheMusicaActivity::class.java)
            intent.putExtra("musica", musica)
            startActivity(intent)
        }

        binding.listViewMusicas.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            )
        }
    }

    private fun setupLauncherCadastro() {
        launcherCadastro = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                loadData()
            }
        }
    }

    private fun setupListeners() {

        binding.btnAdicionar.setOnClickListener {
            val intent = Intent(this, CadastroMusicaActivity::class.java)
            launcherCadastro.launch(intent)
        }

        binding.edtFiltro.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val filtro = s.toString().lowercase()
                val filtradas = musicas.filter {
                    it.nome.lowercase().contains(filtro) ||
                            it.artista.lowercase().contains(filtro)
                }
                adapter.updateLista(filtradas)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnTema.setOnClickListener {
            toggleTheme()
        }

        binding.btnIdioma.setOnClickListener {
            toggleLanguage()
        }
    }

    private fun toggleTheme() {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val current = prefs.getString(KEY_THEME, "light") ?: "light"
        val newTheme = if (current == "dark") "light" else "dark"

        prefs.edit().putString(KEY_THEME, newTheme).apply()
        applySavedTheme()
        initButtonsText()
    }

    private fun applySavedTheme() {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        when (prefs.getString(KEY_THEME, "light")) {
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun toggleLanguage() {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val current = prefs.getString(KEY_LANG, "pt") ?: "pt"
        val newLang = if (current == "en") "pt" else "en"

        prefs.edit().putString(KEY_LANG, newLang).apply()
        recreate()
        applySavedLang()
        initButtonsText()
    }

    private fun applySavedLang() {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        when (prefs.getString(KEY_LANG, "pt")) {
            "en" -> AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags("en")
            )
            else -> AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags("pt")
            )
        }
    }

    private fun initButtonsText() {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        val theme = prefs.getString(KEY_THEME, "light")
        binding.btnTema.text =
            if (theme == "dark") getString(R.string.btn_tema_claro)
            else getString(R.string.btn_tema_escuro)

        binding.btnIdioma.text = getString(R.string.btn_idioma)
    }
}
