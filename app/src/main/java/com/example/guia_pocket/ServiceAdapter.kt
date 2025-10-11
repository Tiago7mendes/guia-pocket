package com.example.guia_pocket

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class ServiceAdapter(private val context: Context, private val servicos: List<Service>) : BaseAdapter() {
    override fun getCount(): Int = servicos.size
    override fun getItem(position: Int): Any = servicos[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_servico, parent, false)

        val servico = servicos[position]

        val imgServico = view.findViewById<ImageView>(R.id.imgServico)
        val txtNome = view.findViewById<TextView>(R.id.txtNome)
        val txtCategoria = view.findViewById<TextView>(R.id.txtCategoria)

        imgServico.setImageResource(servico.imagem)
        txtNome.text = servico.nome
        txtCategoria.text = servico.categoria

        return view
    }
}
