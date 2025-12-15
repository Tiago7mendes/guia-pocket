package com.example.guia_pocket.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.guia_pocket.data.dao.MusicaDao
import com.example.guia_pocket.model.Musica

@Database(entities = [Musica::class], version = 1)
abstract class AgendaDatabase : RoomDatabase() {
    abstract fun MusicaDao(): MusicaDao
    companion object {
        @Volatile
        private var INSTANCE: AgendaDatabase? = null
        fun getInstance(context: Context): AgendaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AgendaDatabase::class.java,
                    "agenda_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
