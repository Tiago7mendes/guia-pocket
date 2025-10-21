package com.example.guia_pocket.model

import java.io.Serializable

data class Musica(
    val capa: Int,
    val nome: String,
    val artista: String,
    val genero: String,
    val descricao: String,
    val linkSpotify: String,
    val audioResId: Int
) : Serializable
