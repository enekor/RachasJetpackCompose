package com.example.rachascompose.ui.screen

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.room.Room
import coil.compose.rememberAsyncImagePainter
import com.example.rachascompose.R
import com.example.rachascompose.baseDeDatos.BaseDeDatos
import com.example.rachascompose.model.Counter

object CardLayout {
    @Composable
    fun CounterCard(contador: Counter){

        val contexto = LocalContext.current
        
        var expanded by remember { mutableStateOf(false) }
        var contadorNum by remember { mutableStateOf( contador.contador) }
        
        Card(
            Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .clickable { expanded = !expanded },
            elevation = 10.dp,
            shape = RoundedCornerShape(20.dp)

        ) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = contador.imagen),
                        contentDescription = "imagen del contador",
                        modifier = Modifier.size(120.dp)
                    )
                    Text(
                        text = contador.nombre,
                        style = MaterialTheme.typography.h3)
                }
                AnimatedVisibility(visible = expanded) {
                    Column(
                        Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = contadorNum.toString(),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h2
                        )
                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ){
                            Image(
                                painter = painterResource(id = R.drawable.add),
                                contentDescription = "sumar",
                                modifier = Modifier
                                    .clickable {
                                        contadorNum++
                                        updateCounter(contadorNum, contador.id,contexto)
                                    }
                                    .size(100.dp)
                                    .padding(end = 20.dp)
                            )

                            Image(
                                painter = painterResource(id = R.drawable.remove),
                                contentDescription = "restar",
                                modifier = Modifier
                                    .clickable {
                                        contadorNum--
                                        updateCounter(contadorNum, contador.id,contexto)
                                    }
                                    .size(100.dp)
                                    .padding(start = 20.dp)
                            )
                        }
                    }

                }
            }
        }
    }

    private fun updateCounter(contador:Int, id:Int, contexto: Context){
        val baseDeDatos = Room.databaseBuilder(contexto, BaseDeDatos::class.java,"contadores").allowMainThreadQueries().build()
        baseDeDatos.itemDao().updateCounter(contador,id)
    }
}