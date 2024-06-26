package com.projetosrafaelfemina.cinemaoflix.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.projetosrafaelfemina.cinemaoflix.R
import com.projetosrafaelfemina.cinemaoflix.adapter.AdapterCategoria
import com.projetosrafaelfemina.cinemaoflix.api.Api
import com.projetosrafaelfemina.cinemaoflix.databinding.ActivityTelaPrincipalBinding
import com.projetosrafaelfemina.cinemaoflix.model.Categoria
import com.projetosrafaelfemina.cinemaoflix.model.Categorias
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class TelaPrincipal : AppCompatActivity() {

    private lateinit var binding: ActivityTelaPrincipalBinding
    private lateinit var adapterCategoria: AdapterCategoria
    private val listaCategorias: MutableList<Categoria> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTelaPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerViewFilmes = binding.recyclerViewFilmes
        recyclerViewFilmes.layoutManager = LinearLayoutManager(this)
        recyclerViewFilmes.setHasFixedSize(true)
        adapterCategoria = AdapterCategoria(this, listaCategorias)
        recyclerViewFilmes.adapter = adapterCategoria

        binding.txtSair.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, FormLogin::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(this, "Usuário deslogado", Toast.LENGTH_SHORT).show()
        }
        //Configurar Retrofit
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://stackmobile.com.br/").build()
            .create(Api::class.java)

        retrofit.listaCategorias().enqueue(object : Callback<Categorias> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<Categorias>, response: Response<Categorias>) {
                if (response.code() == 200) {
                    response.body()?.let {
                        adapterCategoria.listaCategorias.addAll(it.categorias)
                        adapterCategoria.notifyDataSetChanged()
                        binding.containerProgressBar.visibility = View.GONE
                        binding.progressBar.visibility = View.GONE
                        binding.txtCarregando.visibility = View.GONE
                    }
                }
            }

            override fun onFailure(p0: Call<Categorias>, p1: Throwable) {
                Toast.makeText(
                    applicationContext,
                    "Erro ao buscar todos os filmes!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }
}