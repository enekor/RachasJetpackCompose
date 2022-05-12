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


    @Composable
    fun showDialog(){

        var openDialog by remember { mutableStateOf(true)}
        var nombre by remember { mutableStateOf("")}
        var imagen by remember { mutableStateOf("https://upload.wikimedia.org/wikipedia/commons/thumb/7/7e/Vector_search_icon.svg/1200px-Vector_search_icon.svg.png") }
        var clicked by remember { mutableStateOf(false)}
        var buscarNombre by remember { mutableStateOf("")}
        var previewing by remember { mutableStateOf(false) }

        var lista = listOf<String>()
        
        if(openDialog){
            AlertDialog(
                onDismissRequest = { openDialog = false },
                text = {
                    Column {
                        OutlinedTextField(
                            value = nombre,
                            onValueChange = {nombre = it},
                            placeholder = { Text(text = "Sample text")},
                            label = { Text(text = "Nombre del contador: ") }
                        )
                        Image(
                            painter = rememberAsyncImagePainter(model = imagen),
                            contentDescription = "imagen",
                            modifier = Modifier.clickable { clicked = !clicked }
                        )
                        AnimatedVisibility(visible = clicked) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "Buscar")
                                Row() {
                                    OutlinedTextField(
                                        value = buscarNombre,
                                        onValueChange = {buscarNombre = it},
                                        placeholder = { Text(text = "ditto")},
                                        label = { Text(text = "Nombre del pokemon: ") }
                                    )
                                    Button(
                                        onClick = { lista = buscarImagen() }
                                    ) {
                                        Text(text = "Preview")
                                    }
                                }
                                AnimatedVisibility(
                                    visible = previewing) {
                                    Row() {
                                        Image(
                                            painter = rememberAsyncImagePainter(model = lista[0]),
                                            contentDescription = "Forma original")
                                        Image(
                                            painter = rememberAsyncImagePainter(model = lista[1]),
                                            contentDescription = "Forma shiny"
                                        )
                                    }
                                }
                            }
                        }
                    }
                },
                buttons = {
                    Row (
                        horizontalArrangement = Arrangement.Center
                    ){
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { openDialog = false }
                        ) {
                            Text(text = "Guardar")
                        }
                    }
                }
            )
        }
    }

    private fun buscarImagen():List<String>{
        
    }
}