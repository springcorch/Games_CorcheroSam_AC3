package com.example.videogame.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Screen MainMenu
@Composable
fun MainMenu(modifier: Modifier, onChangeScreen: (String) -> Unit){
    // Variable that counts the total points the player has
    val points = 0
    Column(modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center)
    {
        Text("PLAY & EARN",
            fontSize = 64.sp,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center)
        Text("Puntuation: $points",
            fontSize = 32.sp,
            modifier = Modifier.padding(16.dp))
        //On Click - New screen TickTackToe
        Button(onClick = { onChangeScreen("ticktacktoe") },
            modifier = Modifier.fillMaxWidth().size(128.dp)
                                .padding(top = 16.dp, bottom = 16.dp),
            shape = RectangleShape) {
            Text("START",
                fontSize = 52.sp)
        }
    }
}