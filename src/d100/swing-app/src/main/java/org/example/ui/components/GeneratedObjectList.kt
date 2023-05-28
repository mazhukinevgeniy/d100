package org.example.ui.components

import org.example.listmodels.HistoryItem
import org.example.listmodels.HistoryListModel
import java.awt.*
import java.awt.event.ComponentEvent
import java.awt.event.ComponentListener
import java.awt.font.TextAttribute
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.event.ListDataEvent
import javax.swing.event.ListDataListener
import kotlin.math.max
import kotlin.math.min

private class ObjectView(historyItem: HistoryItem) : JPanel(GridBagLayout()) {
    init {
        val constraints = GridBagConstraints()

        constraints.gridwidth = 1
        constraints.gridy = 0
        for (row in historyItem.components) {
            constraints.gridx = 0
            val rerollButton = JButton("\uD83D\uDD04").also {
                it.font = Font.getFont(Font.SERIF)
            }
            add(rerollButton, constraints)

            constraints.gridx = 1
            add(JLabel("${row.tableName}: "), constraints)

            constraints.gridx = 2
            add(JLabel(row.generatedValue).also {
                it.font = it.font.deriveFont(mapOf(
                    TextAttribute.WEIGHT to TextAttribute.WEIGHT_SEMIBOLD
                ))
                rerollButton.addActionListener { _ ->
                    it.text = historyItem.reroll(row).generatedValue
                }
            }, constraints)

            constraints.gridy++
        }

        constraints.gridx = 0
        add(JLabel("Note: "), constraints)

        constraints.gridx = 1
        add(JLabel(historyItem.note).also {
            it.font = it.font.deriveFont(mapOf(
                TextAttribute.WEIGHT to TextAttribute.WEIGHT_SEMIBOLD
            ))
        }, constraints)

        border = BorderFactory.createTitledBorder(historyItem.objectName).also {
            it.titleFont = it.titleFont.deriveFont(
                mapOf(
                    TextAttribute.WEIGHT to TextAttribute.WEIGHT_BOLD
                )
            )
        }
    }
}

class GeneratedObjectList(val model: HistoryListModel) : JPanel() {
    init {
        layout = FlowLayout(FlowLayout.LEFT, 10, 10)
        componentOrientation = ComponentOrientation.LEFT_TO_RIGHT

        model.addListDataListener(object : ListDataListener {
            val root = this@GeneratedObjectList

            override fun intervalAdded(e: ListDataEvent?) {
                if (e == null) {
                    return
                }
                for (i in e.index0..e.index1) {
                    root.add(ObjectView(model.getDetailedObject(i)), 0)
                }
                updatePreferredSize()
            }

            override fun intervalRemoved(e: ListDataEvent?) {
            }

            override fun contentsChanged(e: ListDataEvent?) {
            }
        })

        for (i in 0 until model.size) {
            add(ObjectView(model.getDetailedObject(i)), 0)
        }

        updatePreferredSize()
        addComponentListener(object : ComponentListener {
            override fun componentResized(e: ComponentEvent?) {
                updatePreferredSize()
            }

            override fun componentMoved(e: ComponentEvent?) {
            }

            override fun componentShown(e: ComponentEvent?) {
            }

            override fun componentHidden(e: ComponentEvent?) {
            }
        })
    }

    private fun updatePreferredSize() {
        val dimension = Dimension(400, 400)
        for (item in components) {
            dimension.width = max(dimension.width, item.x + item.width)
            dimension.height = max(dimension.height, item.y + item.height)
        }
        if (parent != null) {
            dimension.width = min(dimension.width, parent.width)
        }
        if (preferredSize != dimension) {
            preferredSize = dimension
            revalidate()
        }
    }
}
