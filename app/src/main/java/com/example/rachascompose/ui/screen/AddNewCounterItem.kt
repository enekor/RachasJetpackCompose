package com.example.rachascompose.ui.screen

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

object AddNewCounterItem {

    fun buscarImagen():List<String>{
        return listOf("https://bangbranding.com/blog/wp-content/uploads/2016/09/700x511_SliderInterior.jpg","https://biblioteca.acropolis.org/wp-content/uploads/2014/12/verde-1200x839.png")
    }
}