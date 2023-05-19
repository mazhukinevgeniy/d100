package org.example.ui

import java.awt.CardLayout
import java.awt.Component
import java.awt.Container

abstract class ScreenBase(
    private val contentPane: Container,
    private val screen: Screen
) {
    abstract fun getView(): Component

    private val ensureView: Component by lazy {
        getView().also {
            contentPane.add(it, screen.toString())
        }
    }

    fun activate() {
        ensureView
        (contentPane.layout as CardLayout).show(
            contentPane, Screen.Tables.toString()
        )
    }
}
