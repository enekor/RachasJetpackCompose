package com.example.rachascompose.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.rachascompose.model.Counter
import com.example.rachascompose.ui.theme.RachasComposeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contadores.add(Counter("Nombre","https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/132.png",78))
        contadores.add(Counter("Nombre2","https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/132.png",7))
        contadores.add(Counter("Nombre3","https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/132.png",478))

        setContent {
            RachasComposeTheme {
                CardList()
            }
        }
    }
}

var contadores = mutableListOf<Counter>()

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