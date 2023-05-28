package org.example.ui

import org.example.controller.AppController
import java.awt.BorderLayout
import java.awt.CardLayout
import java.awt.Dimension
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel

class MainWindowBuilder {

    private fun buildTopPanel(frame: JFrame) {
        val panel = JPanel()

        val tableManagerBtn = JButton("Tables").also {
            it.addActionListener {
                AppController.screenManager?.activateScreen(Screen.Tables)
            }
        }
        val objectManagerBtn = JButton("Objects").also {
            it.addActionListener {
                AppController.screenManager?.activateScreen(Screen.Objects)
            }
        }
        val gameMasterBtn = JButton("GM Mode").also {
            it.addActionListener {
                AppController.screenManager?.activateScreen(Screen.Game)
            }
        }
        panel.add(tableManagerBtn)
        panel.add(objectManagerBtn)
        panel.add(gameMasterBtn)

        frame.contentPane.add(panel, BorderLayout.NORTH)
    }

    init {
        val frame = JFrame("d100")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        frame.iconImages = null

        buildTopPanel(frame)

        frame.minimumSize = Dimension(800, 600)

        val cardPane = JPanel(CardLayout())
        frame.contentPane.add(cardPane, BorderLayout.CENTER)

        AppController.onInitUi(cardPane)

        frame.pack()
        frame.isVisible = true
    }
}
