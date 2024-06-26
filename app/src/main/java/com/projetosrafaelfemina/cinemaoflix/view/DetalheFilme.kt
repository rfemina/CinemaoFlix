package com.projetosrafaelfemina.cinemaoflix.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.projetosrafaelfemina.cinemaoflix.R
import com.projetosrafaelfemina.cinemaoflix.databinding.ActivityDetalheFilmeBinding

class DetalheFilme : AppCompatActivity() {

    private lateinit var binding: ActivityDetalheFilmeBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetalheFilmeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val capa = intent.extras?.getString("capa")
        val nome = intent.extras?.getString("nome")
        val descricao = intent.extras?.getString("descricao")
        val elenco = intent.extras?.getString("elenco")

        Glide.with(this).load(capa).centerCrop().into(binding.capaFilme)
        binding.txtNome.setText(nome)
        binding.txtDescricao.setText("Descrição: $descricao")
        binding.txtElenco.setText("Elenco: $elenco")
    }
}