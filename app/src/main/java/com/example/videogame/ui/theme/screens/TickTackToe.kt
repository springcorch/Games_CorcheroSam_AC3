package com.example.videogame.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.videogame.ui.theme.components.Header

@Composable
fun TickTackToe(){
    Column(modifier = Modifier.fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
    horizontalAlignment = Alignment.CenterHorizontally) {
        Header(0)
        Text("TICK TACK TOE",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground)

    }

}

@Preview(showBackground = true)
@Composable
fun TickTackToePreview() {
    TickTackToe()
}