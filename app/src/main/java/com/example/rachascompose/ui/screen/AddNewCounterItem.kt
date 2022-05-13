package com.example.rachascompose.ui.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.rememberAsyncImagePainter
import com.example.rachascompose.rest.GetImageFromApi

object AddNewCounterItem {

    fun buscarImagen(nombre:String, contexto:Context):List<String>{
        var lista =  GetImageFromApi.getPokemon(nombre)

        if(lista.isEmpty()){
            Toast.makeText(contexto,"No se han encontrado resultados, asegurate de que el nombre esta bien escrito",Toast.LENGTH_SHORT)
        }

        return lista
    }
}