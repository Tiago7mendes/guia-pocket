package com.example.guia_pocket.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.guia_pocket.data.db.AgendaDatabase
import com.example.guia_pocket.databinding.ActivityCadastroMusicaBinding
import com.example.guia_pocket.model.Musica
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CadastroMusicaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroMusicaBinding

    private lateinit var launcherImagem: ActivityResultLauncher<Array<String>>
    private lateinit var launcherAudio: ActivityResultLauncher<Array<String>>

    private lateinit var capaUri: String
    private lateinit var audioUri: String

    private lateinit var db: AgendaDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroMusicaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AgendaDatabase.getInstance(this)

        setupLaunchers()
        setupListeners()
    }

    private fun setupLaunchers() {

        // Selecionar imagem (capa)
        launcherImagem = registerForActivityResult(
            ActivityResultContracts.OpenDocument()
        ) { uri ->
            if (uri != null) {
                contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                capaUri = uri.toString()
                binding.imgCapaCadastro.setImageURI(uri)
            }
        }

        // Selecionar áudio
        launcherAudio = registerForActivityResult(
            ActivityResultContracts.OpenDocument()
        ) { uri ->
            if (uri != null) {
                contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                audioUri = uri.toString()
                Toast.makeText(this, "Áudio selecionado com sucesso", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupListeners() {

        // Clicar na imagem para escolher capa
        binding.imgCapaCadastro.setOnClickListener {
            launcherImagem.launch(arrayOf("image/*"))
        }

        // Botão para selecionar áudio
        binding.btnSelecionarAudio.setOnClickListener {
            launcherAudio.launch(arrayOf("audio/*"))
        }

        // Salvar música
        binding.btnSalvarMusica.setOnClickListener {

            val nome = binding.edtNomeMusica.text.toString()
            val artista = binding.edtArtista.text.toString()
            val genero = binding.edtGenero.text.toString()
            val descricao = binding.edtDescricao.text.toString()
            val linkSpotify = ""

            if (::capaUri.isInitialized &&
                ::audioUri.isInitialized &&
                nome.isNotBlank() &&
                artista.isNotBlank() &&
                genero.isNotBlank() &&
                descricao.isNotBlank()
            ) {

                val musica = Musica(
                    capaUri = capaUri,
                    nome = nome,
                    artista = artista,
                    genero = genero,
                    descricao = descricao,
                    audioUri = audioUri,
                    linkSpotify = linkSpotify
                )

                salvarNoBanco(musica)

            } else {
                Toast.makeText(
                    this,
                    "Preencha todos os campos e selecione capa e áudio",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Voltar
        binding.btnVoltarCadastro.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun salvarNoBanco(musica: Musica) {
        lifecycleScope.launch(Dispatchers.IO) {
            db.MusicaDao().inserir(musica)
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}
