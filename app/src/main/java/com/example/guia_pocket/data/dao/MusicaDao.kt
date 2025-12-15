package com.example.guia_pocket.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.guia_pocket.model.Musica

@Dao
interface MusicaDao {
    @Insert
    suspend fun inserir(musica: Musica)
    @Query(value = "SELECT * FROM musica ORDER BY nome ASC")
    suspend fun filtrarPorNome(): List<Musica>
    @Delete
    suspend fun deletar(musica: Musica)
    @Update
    suspend fun atualizar(musica: Musica)
}