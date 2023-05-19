package org.example.ui

import org.example.controller.ModelProvider
import java.awt.Container

enum class Screen {
    Tables,
    Objects,
    Game
}

class ScreenManager(contentPane: Container, modelProvider: ModelProvider) {
    private val screens = mapOf(
        Screen.Tables to TablesScreen(contentPane, modelProvider),
        Screen.Objects to ObjectsScreen(contentPane, modelProvider),
        Screen.Game to GMScreen(contentPane)
    )

    fun activateScreen(screen: Screen) {
        screens[screen]!!.activate()
    }
}
