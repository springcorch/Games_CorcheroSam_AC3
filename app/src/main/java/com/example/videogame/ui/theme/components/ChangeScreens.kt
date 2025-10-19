package com.example.videogame.ui.theme.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.videogame.ui.theme.screens.CrashX
import com.example.videogame.ui.theme.screens.MainMenu
import com.example.videogame.ui.theme.screens.TickTackToe

@Composable
fun ChangeScreens(){
    // Variable string que guardará los nombres de las pantallas para enseñar estas
    var shownScreen by remember { mutableStateOf("mainmenu") }

    // Variable que guarda una función, esta función recibe una string "shownScreen"
    // y actualiza la pantalla visible
    val onChangeScreen: (String) -> Unit = { newScreen ->
        shownScreen = newScreen
    }
    // Todos los posibles nombres y pantallas - when
    when (shownScreen) {
        "mainmenu" -> MainMenu(Modifier, onChangeScreen)
        "ticktacktoe" -> TickTackToe(onChangeScreen)
        "crashx" -> CrashX(onChangeScreen)
    }
}