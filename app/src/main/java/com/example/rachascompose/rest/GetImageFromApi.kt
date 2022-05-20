package com.example.rachascompose.rest

import android.util.Log
import com.example.rachascompose.rest.config.Api
import com.example.rachascompose.rest.config.ApiConfig
import com.example.rachascompose.rest.model.Pokemon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GetImageFromApi {

    val api = ApiConfig.getClient().create(Api::class.java)
    var lista = listOf<String>()

    fun getPokemon(nombre:String):List<String>{
        Log.d("apilistado","dentro")
        val call = api.getPokemon(nombre)

        call.enqueue(object : Callback<Pokemon> {
            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                Log.d("apilistado","fallo en la api")
            }

            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                lista = response.body()?.let { getImageList(it) } ?: listOf<String>()
            }
        })

        return lista
    }

    private fun getImageList(pokemon:Pokemon):List<String>{
        var lista = mutableListOf<String>()
        val sprites = pokemon.sprites.other?.home

        sprites?.front_default?.let { lista.add(it) }
        sprites?.front_shiny?.let { lista.add(it) }

        return lista.toList()
    }
}