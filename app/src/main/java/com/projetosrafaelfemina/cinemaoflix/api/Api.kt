package com.projetosrafaelfemina.cinemaoflix.api

import com.projetosrafaelfemina.cinemaoflix.model.Categorias
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("/filmes")
    fun listaCategorias(): Call<Categorias>
}