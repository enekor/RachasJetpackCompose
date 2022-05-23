package com.example.rachascompose.ui.screen

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import coil.compose.rememberAsyncImagePainter
import com.example.rachascompose.R
import com.example.rachascompose.baseDeDatos.AccesoBaseDeDatos
import com.example.rachascompose.baseDeDatos.BaseDeDatos
import com.example.rachascompose.model.Counter

class CardLayout {

    var recienBorrado = MutableLiveData(false)

    private var abrirMenuBorrado = MutableLiveData(false)
    @Composable
    fun CounterCard(contador: Counter){

        val contexto = LocalContext.current

        val recienBorradoState by recienBorrado.observeAsState()
        var expanded by remember { mutableStateOf(false)}
        var contadorNum by remember { mutableStateOf( contador.contador) }
        val confirmarBorrado by abrirMenuBorrado.observeAsState()

        if(recienBorradoState == true){
            recienBorrado.value = false
            expanded = false
        }
        
        Card(
            Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .clickable {
                    expanded = !expanded
                },
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
                        style = MaterialTheme.typography.h3,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center)
                }
                AnimatedVisibility(visible = expanded) {
                    Column(
                        Modifier.fillMaxWidth().background(Color(159,146,156))
                    ) {
                        Text(
                            text = contadorNum.toString(),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h2
                        )
                        Row(
                            Modifier
                                .wrapContentWidth()
                                .align(Alignment.Start)
                                .clickable {
                                    abrirMenuBorrado.value = true
                                }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = Color.Red,
                                modifier = Modifier
                                    .size(25.dp)
                                    .align(Alignment.CenterVertically)
                            )
                            Text(
                                text = "Borrar",
                                fontSize = 25.sp,
                                modifier = Modifier.align(Alignment.CenterVertically),
                                color = Color.Red
                            )
                        }
                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ){
                            Image(
                                painter = painterResource(id = R.drawable.remove),
                                contentDescription = "restar",
                                modifier = Modifier
                                    .clickable {
                                        contadorNum--
                                        updateCounter(contadorNum, contador.id, contexto)
                                    }
                                    .size(100.dp)
                                    .padding(start = 20.dp)
                            )
                            
                            Image(
                                painter = painterResource(id = R.drawable.add),
                                contentDescription = "sumar",
                                modifier = Modifier
                                    .clickable {
                                        contadorNum++
                                        updateCounter(contadorNum, contador.id, contexto)
                                    }
                                    .size(100.dp)
                                    .padding(end = 20.dp)
                            )

                            AnimatedVisibility(visible = confirmarBorrado!!) {
                               confirmDelete(contador)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun confirmDelete(contador:Counter):Boolean{
        var borrar = false
        val contexto = LocalContext.current

        AlertDialog(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 20.dp),
            onDismissRequest = { abrirMenuBorrado.value = false },
            text = { Text(text = "Desea borrar el contador ${contador.nombre}")},
            buttons = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            borrar = false
                            abrirMenuBorrado.value = false
                                  },
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Text(text = "Cancelar")
                    }
                    Button(
                        onClick = {
                            borrar = true
                            deleteCounter(contador.id, contexto)
                            abrirMenuBorrado.value = false
                        },
                        modifier = Modifier.padding(start = 12.dp)
                    ) {
                        Text(text = "Borrar")
                    }
                }
            }
        )
        return borrar
    }

    private fun updateCounter(contador:Int, id:Int, contexto: Context){
        val db = BaseDeDatos.getDatabase(contexto)
        AccesoBaseDeDatos.updateCounter(contador,id,db)
    }

    private fun deleteCounter(counter: Int, contexto:Context){
        val db = BaseDeDatos.getDatabase(contexto)
        AccesoBaseDeDatos.deleteCounter(counter,db)
        recienBorrado.value = true
    }
}