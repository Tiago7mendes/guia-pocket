package com.example.guia_pocket.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.guia_pocket.databinding.ItemMusicaBinding
import com.example.guia_pocket.model.Musica

class MusicaAdapter(
    context: Context,
    private val lista: List<Musica>
) : ArrayAdapter<Musica>(context, 0, lista) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemMusicaBinding
        val itemView: View

        if (convertView == null) {
            binding = ItemMusicaBinding.inflate(LayoutInflater.from(context), parent, false)
            itemView = binding.root
            itemView.tag = binding
        } else {
            itemView = convertView
            binding = itemView.tag as ItemMusicaBinding
        }

        val musica = lista[position]
        binding.imgCapa.setImageResource(musica.capa)
        binding.tvNome.text = musica.nome
        binding.tvArtista.text = musica.artista
        binding.tvGenero.text = musica.genero

        return itemView
    }
}