package com.example.rachascompose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rachascompose.model.Counter

object CardLayout {
    @Composable
    fun CounterCard(contador:Counter){
        
        var expanded by remember { mutableStateOf(false) }
        
        Card(
            Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .clickable { expanded = !expanded },
            elevation = 10.dp
        ) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = contador.nombre,
                    style = MaterialTheme.typography.h1)
                AnimatedVisibility(visible = expanded) {
                    Text(
                        text = contador.count.toString(),
                        style = MaterialTheme.typography.h3
                    )
                }
            }
        }
    }
}