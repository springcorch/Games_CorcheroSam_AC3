package com.example.videogame.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun Header(p: Int, t: Int, onChangeScreen: (String) -> Unit){
    Row(modifier = Modifier
        .fillMaxWidth()
        .size(96.dp)
        .background(MaterialTheme.colorScheme.onPrimaryContainer),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
        )
    {
        // Se cambia la pantalla a mostrar por la de TTT
        Button(onClick = { onChangeScreen("ticktacktoe") },
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Text("TTT")
        }
        // Se cambia la pantalla a mostrar por la de CrashX
        Button(onClick = { onChangeScreen("crashx") },
            modifier = Modifier.padding(horizontal = 8.dp)) {
            Text("ChX")
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Timer:",
                modifier = Modifier.padding(horizontal = 16.dp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary)
            Text(text = "$t",
                modifier = Modifier.padding(horizontal = 16.dp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Puntuation:",
                modifier = Modifier.padding(horizontal = 16.dp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary)
            Text(text = "$p",
                modifier = Modifier.padding(horizontal = 16.dp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}