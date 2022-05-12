package com.example.rachascompose.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.rachascompose.model.Counter
import com.example.rachascompose.ui.theme.RachasComposeTheme

var contadores = mutableListOf<Counter>()

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contadores.add(Counter("Nombre","https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/132.png",78))
        contadores.add(Counter("Nombre2","https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/132.png",7))
        contadores.add(Counter("Nombre3","https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/132.png",478))

        setContent {
            RachasComposeTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(){

    var openDialog by remember { mutableStateOf(false)}
    var nombre by remember { mutableStateOf("")}
    var imagen by remember { mutableStateOf("https://upload.wikimedia.org/wikipedia/commons/thumb/7/7e/Vector_search_icon.svg/1200px-Vector_search_icon.svg.png") }
    var clicked by remember { mutableStateOf(false)}
    var buscarNombre by remember { mutableStateOf("")}
    var previewing by remember { mutableStateOf(false) }

    Scaffold(
        Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { openDialog = true },
                modifier = Modifier.size(50.dp),
                backgroundColor = Color.Magenta,
            ){
                Icon(imageVector = Icons.Default.Add, contentDescription = "Nuevo")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ){
        CardList()
        AnimatedVisibility(visible = openDialog) {

            var lista = listOf<String>()

            AlertDialog(
                modifier = Modifier
                    .padding(horizontal = 10.dp,vertical = 20.dp)
                    .fillMaxSize()
                    .background(Color.Transparent),
                onDismissRequest = { openDialog = false },
                text = {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            value = nombre,
                            onValueChange = {nombre = it},
                            placeholder = { Text(text = "Sample text")},
                            label = { Text(text = "Nombre del contador: ") }
                        )
                        Image(
                            painter = rememberAsyncImagePainter(model = imagen),
                            contentDescription = "imagen",
                            modifier = Modifier.clickable {
                                clicked = !clicked
                                previewing = false
                            }.size(200.dp).padding(horizontal = 12.dp)
                        )
                        AnimatedVisibility(visible = clicked) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = "Buscar")
                                OutlinedTextField(
                                    value = buscarNombre,
                                    onValueChange = {buscarNombre = it},
                                    placeholder = { Text(text = "ditto")},
                                    label = { Text(text = "Nombre del pokemon: ") }
                                )
                                Button(
                                    onClick = {
                                        lista = AddNewCounterItem.buscarImagen()
                                        previewing = !previewing
                                    }
                                ) {
                                    Text(text = "Preview")
                                }
                                AnimatedVisibility(
                                    visible = previewing) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Image(
                                            modifier = Modifier
                                                .size(120.dp)
                                                .padding(horizontal = 5.dp)
                                                .clickable {
                                                    imagen = lista[0]
                                                    previewing = false
                                                    },
                                            painter = rememberAsyncImagePainter(model = lista[0]),
                                            contentDescription = "Forma original")

                                        Image(
                                            modifier = Modifier
                                                .size(120.dp)
                                                .padding(horizontal = 5.dp)
                                                .clickable {
                                                    imagen = lista[1]
                                                    previewing = false
                                                    },
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
}

@Composable
fun CardList(){
    Surface(
        color = Color.LightGray,
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn(){
            items(contadores){
                contador ->
                CardLayout.CounterCard(contador)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RachasComposeTheme {
        CardList()
    }
}