package com.example.videogame.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.videogame.ui.theme.components.Header
import kotlinx.coroutines.delay

// Esto es para que el TextField funcione aunque esté deprecado
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrashX(onChangeScreen: (String) -> Unit, score: Int, onUpdateScore: (Int) -> Unit){
    //LOGICA
    //Para el Header
    var gainedPoints by remember { mutableStateOf(score) }
    // Actualizamos el score global con las gainedPoints
    fun updateScore(points: Int) {
        gainedPoints = points
        onUpdateScore(points)
    }
    // Temporizador
    var timer by remember { mutableStateOf(60) }
    // Para el gameplay
    // Multiplicador de nuestro dinero al gamblear
    var multiplier by remember { mutableStateOf(0.98) }
    // Cantidad que nosotros pongamos que queremos apostar - TextField
    var betAmount by remember { mutableStateOf(TextFieldValue("")) }
    // La cantidad añadida que se irá multiplicando y sumando al apostar
    var baseBet by remember { mutableStateOf(0) }
    // Estado en que se encuentra la partida (un gameover con otro nombre)
    var isGambling by remember { mutableStateOf(false) }
    // Mensaje que actualiza al jugador qué está pasando
    var message by remember { mutableStateOf("") }

    // Courutina de Kotlin + delay de 1000 milisegundos (1 seg), esta courutina no parará
    // hasta que isGambling sea false
    LaunchedEffect(key1 = isGambling) {
        // Mientras haya gambling este estará funcionando, mete presión
        if (isGambling) {
            while (timer > 0) {
                delay(1000L)
                timer--
            }
            // Se puede perder el dinero si el tiempo llega a 0 - if
            if (timer == 0) {
                message = "Time's up! You've lost all money!"
                isGambling = false
            }
        }
    }

    //Visuales
    Column(modifier = Modifier.fillMaxHeight()
        .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        //Aquí se presenta el componente Header que llevará consigo
        // los puntos acomulados - gainedPoints y temporizador - timer
        Header(gainedPoints, timer, onChangeScreen = onChangeScreen)
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
            Text(
                "Multiplier: x$multiplier%",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.size(16.dp))

            // Si no estamos en racha mostramos input, esto es para que el jugador
            // no pueda tocar su apuesta hasta que saque el dinero o lo pierda
            if (!isGambling) {
                // Dice que esta deprecado y que puede ser que lo quiten en el futuro,
                // pero no me sé más métodos para hacerlo por ahora así que lo usaré
                TextField(
                    value = betAmount,
                    onValueChange = { betAmount = it },
                    label = { Text("Enter amount to gamble",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground)
                            },
                    singleLine = true,
                    // Intento de cambiar los colores
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = MaterialTheme.colorScheme.onBackground,
                        cursorColor = MaterialTheme.colorScheme.secondary,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.primary
                    )
                )
                Spacer(Modifier.size(16.dp))
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    // No se puede hacer gambling si no tienes dinero
                    enabled = !isGambling || baseBet > 0,
                    onClick = {
                        if (!isGambling) {
                            // Coge el texto y lo transforma a Int o Null
                            val bet = betAmount.text.toIntOrNull() ?: 0
                            // Si los puntos apostados son 0 o menor saldrá inválido y se devolverá
                            // el botón para poderse volverse a usar y usar otro amount of bet
                            if (bet <= 0) {
                                message = "Enter a valid bet!"
                                return@Button
                            }
                            // Si los puntos apostados son mayores de tus puntos totales pasará
                            // lo mismo
                            if (bet > gainedPoints) {
                                message = "You can't bet more than your points!"
                                return@Button
                            }
                            //Si el input fue correcto entonces la apuesta dará comienzo
                            else{
                                baseBet = bet
                                isGambling = true
                                multiplier = 0.98
                                // Se quitan los puntos en juego
                                updateScore(gainedPoints - bet)
                                message = "You are gambling with $bet points!"
                            }

                        } else {
                            // Se hará un random de 50/50 cada que se clique el botón - random
                            val win = listOf(true, false).random()
                            // Si ha ganado la apuesta - true
                            if (win) {
                                multiplier *= 2
                                message = "You won this round! Multiplier increased."
                            // Si ha perdido la apuesta, pierde el dinero y termina el juego - false
                            } else {
                                timer = 60
                                baseBet = 0
                                multiplier = 0.98
                                isGambling = false
                                message = "You lost the bet!"
                            }
                        }
                    }
                ) {
                    // El texto cambia del botón cuando esta gambling o no, esto es para evitar
                    // hacer otro botón, me pareció curioso hacerlo así aunque no sé si esto da
                    // ventaja o no - if else
                    Text(if (!isGambling) "Start Gamble" else "Gamble",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary)
                }
                // Botón para retirar el dinero a tiempo antes de perder la apuesta, se reinicia
                // y acaba el juego, llevándote los puntos ganados
                Button(
                    enabled = isGambling && baseBet > 0,
                    onClick = {
                        // Variable que multiplica el multiplicador por lo apostado
                        val winnings = (baseBet * multiplier).toInt()
                        timer = 60
                        // Se añade lo multiplicado al score total
                        updateScore(gainedPoints + winnings)
                        message = "You cashed out $winnings points!"
                        baseBet = 0
                        multiplier = 0.98
                        isGambling = false
                    }
                ) {
                    Text("Cash Out",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary)
                }
            }

            Spacer(Modifier.size(16.dp))
            // Mensaje de ayuda para saber el proceso de la partida
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        }
    }
}