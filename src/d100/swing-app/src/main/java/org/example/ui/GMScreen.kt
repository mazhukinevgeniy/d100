package org.example.ui

import org.example.controller.ModelProvider
import java.awt.Component
import java.awt.Container

class GMScreen(
    contentPane: Container, modelProvider: ModelProvider
) : ScreenBase(contentPane, Screen.Game) {

    private val container: Container by lazy {
        Container()
    }

    override fun getView(): Component {
        return container
    }
}
