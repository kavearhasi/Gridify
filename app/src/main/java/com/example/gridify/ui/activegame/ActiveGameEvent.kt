package com.example.gridify.ui.activegame

sealed class ActiveGameEvent {
    data class onInput(val input: Int) : ActiveGameEvent()
    data class onTileFocused(val x: Int, val y: Int) : ActiveGameEvent()
    object onNewGameClick : ActiveGameEvent()
    object onStart : ActiveGameEvent()
    object onStop : ActiveGameEvent()
}