package com.example.videogame.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.videogame.ui.theme.components.Header

@Composable
fun CrashX(){
    //LOGICA
    var gainedPoints by remember { mutableStateOf(0) }
    var timer by remember { mutableStateOf(60) }

    //Visuales
    Column(modifier = Modifier.fillMaxHeight()
        .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        //Aquí se presenta el componente Header que llevará consigo
        // los puntos acomulados - gainedPoints y temporizador - timer
        Header(gainedPoints, timer)
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Spacer(Modifier.size(100.dp))
            Text(
                "CRASH GAME",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.size(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CrashXPreview() {
    CrashX()
}