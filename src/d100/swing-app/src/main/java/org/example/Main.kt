package org.example
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Window
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel


object Main {

    private fun buildTopPanel(frame: JFrame) {
        val panel = JPanel()

        val tableManagerBtn = JButton("Tables")
        val objectManagerBtn = JButton("Objects")
        val gameMasterBtn = JButton("GM Mode")
        panel.add(tableManagerBtn)
        panel.add(objectManagerBtn)
        panel.add(gameMasterBtn)

        frame.contentPane.add(panel, BorderLayout.NORTH)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val frame = JFrame("d100")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        buildTopPanel(frame)

        frame.minimumSize = Dimension(800, 600)

        frame.pack()
        frame.isVisible = true
    }
}
