package com.example.guia_pocket.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "musica")
data class Musica(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val capaUri: String,
    val nome: String,
    val artista: String,
    val genero: String,
    val descricao: String,
    val audioUri: String,
    val linkSpotify: String

) : Serializable
