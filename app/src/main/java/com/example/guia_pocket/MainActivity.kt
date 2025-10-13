package com.example.guia_pocket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.guia_pocket.DetalheActivity
import com.example.guia_pocket.R
import com.example.guia_pocket.Service
import com.example.guia_pocket.ServiceAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listServicos)

        val servicos: List<Service> = listOf(
            Service("Limpeza Residencial", "Casa", R.drawable.limpeza, "Limpeza completa para casas e apartamentos."),
            Service("Pintura", "Reforma", R.drawable.pintura, "Serviço de pintura profissional."),
            Service("Manutenção Elétrica", "Reparo", R.drawable.eletrica, "Verificação e troca de instalações elétricas."),
            Service("Jardinagem", "Natureza", R.drawable.jardinagem, "Cuidado e manutenção de jardins."),
            Service("Marcenaria", "Construção", R.drawable.marcenaria, "Montagem e criação de móveis sob medida."),
            Service("Encanamento", "Reparo", R.drawable.encanamento, "Reparo de vazamentos e manutenção hidráulica.")
        )

        listView.adapter = ServiceAdapter(this, servicos)

        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, DetalheActivity::class.java)
            intent.putExtra("nome", servicos[position].nome)
            intent.putExtra("categoria", servicos[position].categoria)
            intent.putExtra("descricao", servicos[position].descricao)
            intent.putExtra("imagem", servicos[position].imagem)
            startActivity(intent)
        }
    }
}
