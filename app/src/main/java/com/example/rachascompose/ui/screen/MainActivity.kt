package com.example.rachascompose.ui.screen

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import coil.compose.rememberAsyncImagePainter
import com.example.rachascompose.baseDeDatos.AccesoBaseDeDatos
import com.example.rachascompose.baseDeDatos.BaseDeDatos
import com.example.rachascompose.model.Counter
import com.example.rachascompose.ui.theme.RachasComposeTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RachasComposeTheme {
                MainScreen()
            }
        }
    }

    @OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
    @Composable
    fun MainScreen(){
        val scrollState = rememberScrollState()

        // Smooth scroll to specified pixels on first composition
        LaunchedEffect(Unit) { scrollState.animateScrollTo(10000) }
        val buscarImagenDefecto = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7e/Vector_search_icon.svg/1200px-Vector_search_icon.svg.png"
        val contexto = LocalContext.current

        var openDialog by remember { mutableStateOf(false) }
        var nombre by remember { mutableStateOf("") }
        var imagen by remember { mutableStateOf(buscarImagenDefecto) }
        var clicked by remember { mutableStateOf(false) }
        var buscarNombre by remember { mutableStateOf("") }
        var previewing by remember { mutableStateOf(false) }
        val listaCounter by remember { loadListFromDataBase(contexto) }.collectAsState(initial = emptyList())

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
            //Creating the CardList who contains the counter items
            Surface(
                modifier = Modifier.fillMaxWidth()
            ) {
                LazyColumn(){
                    items(listaCounter){
                            contador ->
                        CardLayout.CounterCard(contador = contador)
                        /*run {
                            val state = rememberDismissState(
                                confirmStateChange = {
                                    if (it == DismissValue.DismissedToStart) {
                                        deleteCounter(contador, contexto)
                                    }
                                    true
                                }
                            )

                            SwipeToDismiss(
                                state = state,
                                background = {
                                    swipeBackground(state)
                                },
                                dismissContent = {
                                    CardLayout.CounterCard(contador)
                                },
                                directions = setOf(DismissDirection.EndToStart)
                            )
                        }*/
                    }
                }
            }

            //Dialog that allows the user to create a new counter item
            AnimatedVisibility(visible = openDialog) {

                var listaImagenes = listOf<String>()

                AlertDialog(
                    properties = DialogProperties(usePlatformDefaultWidth = false),
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 20.dp)
                        .wrapContentHeight()
                        .background(Color.Transparent),
                    onDismissRequest = { openDialog = false },
                    text = {
                        //Text field whith a custom image
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.wrapContentHeight().verticalScroll(scrollState)
                        ) {
                            OutlinedTextField(
                                value = nombre,
                                onValueChange = {nombre = it},
                                placeholder = { Text(text = "Sample text")},
                                label = { Text(text = "Nombre del contador: ") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Image(
                                painter = rememberAsyncImagePainter(model = imagen),
                                contentDescription = "imagen",
                                modifier = Modifier
                                    .clickable {
                                        clicked = !clicked
                                        previewing = false
                                    }
                                    .size(200.dp)
                                    .padding(horizontal = 12.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                            //image clicked ans showing the selecction menu
                            AnimatedVisibility(visible = clicked) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier.wrapContentHeight()
                                ) {
                                    Text(text = "Buscar")
                                    OutlinedTextField(
                                        value = buscarNombre,
                                        onValueChange = {buscarNombre = it},
                                        placeholder = { Text(text = "ditto")},
                                        label = { Text(text = "Nombre del pokemon: ") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    Button(
                                        onClick = {
                                            if(buscarNombre == ""){
                                                toaster("Introduzca algo en el cuadro de texto antes de buscar",contexto)
                                            }else{
                                                Log.i("api","Buscando")
                                                toaster("Buscando...",contexto)
                                                listaImagenes = AddNewCounterItem.buscarImagen(buscarNombre,contexto)

                                                if(!listaImagenes.isEmpty()) {
                                                    previewing = !previewing
                                                    Log.i("api", "avemus resultado")
                                                    toaster("Listo!", contexto)
                                                }else{
                                                    Log.i("api","No se encontro resultado")
                                                    toaster("No se han encontrado resultados", contexto)
                                                }
                                            }
                                        },
                                        modifier = Modifier.align(Alignment.CenterHorizontally)
                                    ) {
                                        Text(text = "Preview")
                                    }
                                    //the searched name is in the api, showing both images, shiny and normal one and waiting to one being chosen
                                    AnimatedVisibility(
                                        visible = previewing) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center,
                                            modifier = Modifier.wrapContentHeight().fillMaxWidth(),
                                        ) {
                                            Image(
                                                modifier = Modifier
                                                    .size(120.dp)
                                                    .padding(horizontal = 5.dp)
                                                    .clickable {
                                                        imagen = listaImagenes[0]
                                                        previewing = false
                                                    },
                                                painter = rememberAsyncImagePainter(model = listaImagenes[0]),
                                                contentDescription = "Forma original")

                                            Image(
                                                modifier = Modifier
                                                    .size(120.dp)
                                                    .padding(horizontal = 5.dp)
                                                    .clickable {
                                                        imagen = listaImagenes[1]
                                                        previewing = false
                                                    },
                                                painter = rememberAsyncImagePainter(model = listaImagenes[1]),
                                                contentDescription = "Forma shiny"
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    },
                    buttons = {
                        //Button that allows user to save he image
                        Row (
                            horizontalArrangement = Arrangement.Center
                        ){
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    if(imagen != buscarImagenDefecto && nombre != ""){
                                        createNewCounter(Counter(nombre,imagen,0),contexto)
                                        openDialog = false
                                    }else{
                                        toaster("No se pueden dejar campos en blanco",contexto)
                                    }
                                }
                            ) {
                                Text(text = "Guardar")
                            }
                        }
                    }
                )
            }
        }
    }

    /*@OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun swipeBackground(state:DismissState){
        var color = when (state.dismissDirection) {
            DismissDirection.EndToStart -> Color.Red
            null -> Color.Transparent
            else -> Color.Transparent
        }
        Box(
            modifier = Modifier
                .padding(12.dp)
                .background(color = color)
                .fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Borrar",
                tint = Color.Gray,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }*/

    private fun toaster(texto:String, contexto:Context){
        Toast.makeText(contexto,texto,Toast.LENGTH_SHORT)
    }

    private fun createNewCounter(counter:Counter, contexto: Context){
        val db = BaseDeDatos.getDatabase(contexto)
        AccesoBaseDeDatos.createNewCounter(counter,db)
    }

    private fun loadListFromDataBase(contexto:Context):Flow<List<Counter>>{
        val db = BaseDeDatos.getDatabase(contexto)
        lateinit var listado: Flow<List<Counter>>

        listado = AccesoBaseDeDatos.loadListFromDataBase(db)
        return listado
    }

    private fun deleteCounter(contador:Counter, contexto: Context){
        val db = BaseDeDatos.getDatabase(contexto)
        AccesoBaseDeDatos.deleteCounter(contador,db)
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        RachasComposeTheme {

        }
    }
}

