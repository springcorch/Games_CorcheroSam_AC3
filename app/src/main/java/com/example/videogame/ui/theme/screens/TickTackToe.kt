package com.example.videogame.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.videogame.ui.theme.components.Header
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight

@Composable
fun TickTackToe(){
    // LOGICA
    var board by remember { mutableStateOf(Array(9) { "" }) }
    var playerTurn by remember { mutableStateOf(true) }
    var message by remember { mutableStateOf("Make your move!") }
    var gameOver by remember { mutableStateOf(false) }
    var gainedPoints by remember { mutableStateOf(0) }

    fun checkWinner(): String? {
        // Formas de ganar hardcodeadas
        val lines = listOf(
            listOf(0, 1, 2),
            listOf(3, 4, 5),
            listOf(6, 7, 8),
            listOf(0, 3, 6),
            listOf(1, 4, 7),
            listOf(2, 5, 8),
            listOf(0, 4, 8),
            listOf(2, 4, 6)
        )
        // Se añaden las listas representadas en el tablero
        for (line in lines) {
            val (a, b, c) = line
            if (board[a] != "" && board[a] == board[b] && board[a] == board[c]) {
                return board[a]
            }
        }
        return null
    }

    //Turno del PC:
    fun pcMove() {
        if (gameOver) return
        //Busca los espacios vacios del tablero en un filtro - filterNotNull()
        val emptySpots = board.mapIndexed { i, v -> if (v == "") i else null }.filterNotNull()
        // Si hay espacios vacios coloca su pieza en un lugar randomizado - if
        if (emptySpots.isNotEmpty()) {
            // De los espacios vacios escoge uno de ellos al azar - random
            val move = emptySpots.random()
            board = board.copyOf().also { it[move] = "O" }
            // Posibles mensajes a mostrar después de su turno
            val winner = checkWinner()
            //Si hay un ganador - como es el turno del PC siempre será el ganador - if
            if (winner != null) {
                message = "Uh oh... You lose!"
                gameOver = true
            // Si está el tablero lleno de fichas - if
            } else if (board.all { it != "" }) {
                gainedPoints += 10
                message = "Tie!"
                gameOver = true
            // Si no se presenta ninguna de las dos posibilidades se continua la partida - else
            } else {
                message = "Make your move!"
                playerTurn = true
            }
        }
    }

    //VISUALES
    Column(modifier = Modifier.fillMaxHeight()
        .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        //Aquí se presenta el componente Header que llevará consigo
        // los puntos acomulados - gainedPoints
        Header(gainedPoints, 60f)
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Spacer(Modifier.size(100.dp))
            Text("TICK TACK TOE",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground)
            Spacer(Modifier.size(16.dp))
            Text(text = message,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground)
            Spacer(Modifier.size(8.dp))
            //Indica las rows que habrá por cada columna - for
            for (i in 0..2)
            {
                Row (horizontalArrangement = Arrangement.spacedBy(8.dp))
                {
                    //Indica los boxs que van en cada fila - for
                    for (j in 0..2)
                    {
                        val index = i * 3 + j
                        // Estas cajas serán clickables, y al hacer dicho click
                        // se pondrá nuestra pieza "X"
                        Box(modifier = Modifier.size(80.dp)
                                .background(MaterialTheme.colorScheme.tertiary)
                                .clickable(enabled = playerTurn && !gameOver && board[index] == "")
                        {
                            // Posibles mensajes a mostrar después clicar uno de los Boxs
                            if (!gameOver && board[index] == "") {
                                board = board.copyOf().also { it[index] = "X" }
                                val winner = checkWinner()
                                //Si hay un ganador - como es tu turno siempre serás el ganador - if
                                if (winner != null) {
                                    gainedPoints += 100
                                    message = "Congratulations, you win!"
                                    gameOver = true
                                }
                                // Si está el tablero lleno de fichas - if
                                else if (board.all { it != "" }) {
                                    gainedPoints += 10
                                    message = "Tie!"
                                    gameOver = true
                                }
                                // Si no se presenta ninguna de las dos posibilidades
                                // se continua la partida - else
                                else {
                                    message = "PC is making a move"
                                    playerTurn = false
                                    pcMove()
                                }
                            }
                        }, contentAlignment = Alignment.Center)
                        {
                            Text(text = board[index],
                                color = MaterialTheme.colorScheme.onTertiary,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.size(16.dp))
            // Al reiniciar partida se reinician las variables
            Button(onClick = {
                board = Array(9) { "" }
                playerTurn = true
                message = "Make your move!"
                gameOver = false
                             },
                Modifier.fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("RETRY",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TickTackToePreview() {
    TickTackToe()
}