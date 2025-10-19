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
import androidx.compose.ui.unit.dp
import com.example.videogame.ui.theme.components.Header
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.delay

@Composable
fun TickTackToe(onChangeScreen: (String) -> Unit, score: Int, onUpdateScore: (Int) -> Unit){
    // LOGICA
    // Tablero entero 3x3 - primeramente vacío
    var board by remember { mutableStateOf(Array(9) { "" }) }
    // Turno del jugador, si es true, es nuestro turno, si es false, el del PC
    var playerTurn by remember { mutableStateOf(true) }
    // Mensaje que actualizará al jugador de en qué estado está la partida
    var message by remember { mutableStateOf("Make your move!") }
    // Booleana que decide si ha acabado o no la partida
    var gameOver by remember { mutableStateOf(false) }
    // Para el header
    // Puntuación que irá ganando la variable global score mediante la partida
    var gainedPoints by remember { mutableStateOf(score) }
    // Actualiza el score global con las locales de esta pantalla
    fun updateScore(points: Int) {
        gainedPoints = points
        onUpdateScore(points)
    }
    // Tiempo entre jugada y jugada
    var timer by remember { mutableStateOf(60) }

    // Courutina de Kotlin + delay de 1000 milisegundos (1 seg), esta courutina no parará
    // hasta que GameOver sea true
    LaunchedEffect(key1 = gameOver) {
        // Mientras el juego transcurra el tiempo irá bajando con un delay
        if (!gameOver) {
            while (timer > 0) {
                delay(1000L)
                timer--
            }
            // Se puede perder si el tiempo llega a 0 - if
            if (timer == 0) {
                message = "Time's up!"
                gameOver = true
            }
        }
    }

    fun checkWinner(): String? {
        // Formas de ganar hardcodeadas (hor, vert, diagonal)
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
        // Si el juego termina se salta esta función - if
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
                updateScore(gainedPoints + 10)
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
        Header(gainedPoints, timer, onChangeScreen = onChangeScreen)
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
                        // Index que indica cada espacio del tablero
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
                                    updateScore(gainedPoints + 100)
                                    message = "Congratulations, you win!"
                                    gameOver = true
                                }
                                // Si está el tablero lleno de fichas - if
                                else if (board.all { it != "" }) {
                                    updateScore(gainedPoints + 10)
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
                            // Las fichas visualmente mostradas "" "O" "X"
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
                timer = 60
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