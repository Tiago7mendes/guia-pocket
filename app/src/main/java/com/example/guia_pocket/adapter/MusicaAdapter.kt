package com.example.guia_pocket.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.guia_pocket.databinding.ItemMusicaBinding
import com.example.guia_pocket.model.Musica
import androidx.core.net.toUri

class MusicaAdapter(
    private var musicas: List<Musica>,
    private val onClick: (Musica) -> Unit
) : RecyclerView.Adapter<MusicaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMusicaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = musicas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(musicas[position])
    }

    fun updateLista(novasMusicas: List<Musica>) {
        musicas = novasMusicas
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        private val binding: ItemMusicaBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(musica: Musica) {
            binding.imgCapa.setImageURI(musica.capaUri.toUri())

            binding.tvNome.text = musica.nome
            binding.tvArtista.text = musica.artista

            binding.root.setOnClickListener {
                onClick(musica)
            }
        }
    }

}
