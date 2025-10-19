package com.example.videogame.ui.theme.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.videogame.ui.theme.screens.CrashX
import com.example.videogame.ui.theme.screens.MainMenu
import com.example.videogame.ui.theme.screens.TickTackToe

@Composable
fun ChangeScreens(){
    // Variable string que guardará los nombres de las pantallas para enseñar estas
    var shownScreen by remember { mutableStateOf("mainmenu") }
    // Score global
    var score by remember { mutableStateOf(0) }

    // Variable que guarda una función, esta función recibe una string "shownScreen"
    // y actualiza la pantalla visible
    val onChangeScreen: (String) -> Unit = { newScreen ->
        shownScreen = newScreen
    }
    // Variable que guarda una función, esta función recibe un Int "score" y actualiza dicho
    val onUpdateScore: (Int) -> Unit = { newScore ->
        score = newScore
    }

    // Todos los posibles nombres y pantallas - when
    when (shownScreen) {
        "mainmenu" -> MainMenu(onChangeScreen, score)
        "ticktacktoe" -> TickTackToe(onChangeScreen, score, onUpdateScore)
        "crashx" -> CrashX(onChangeScreen, score, onUpdateScore)
    }
}