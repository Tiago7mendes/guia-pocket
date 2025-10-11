package com.example.guia_pocket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class DetalheActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe)

        val nome = intent.getStringExtra("nome")
        val categoria = intent.getStringExtra("categoria")
        val descricao = intent.getStringExtra("descricao")
        val imagem = intent.getIntExtra("imagem", 0)

        findViewById<TextView>(R.id.txtNomeDetalhe).text = nome
        findViewById<TextView>(R.id.txtCategoriaDetalhe).text = categoria
        findViewById<TextView>(R.id.txtDescricaoDetalhe).text = descricao
        findViewById<ImageView>(R.id.imgDetalhe).setImageResource(imagem)
    }
}
