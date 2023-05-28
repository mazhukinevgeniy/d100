package org.example.ui.components

import org.example.listmodels.HistoryItem
import org.example.listmodels.HistoryListModel
import java.awt.Font
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.font.TextAttribute
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.event.ListDataEvent
import javax.swing.event.ListDataListener
import javax.swing.text.AttributeSet.FontAttribute

private class ObjectView(historyItem: HistoryItem) : JPanel(GridBagLayout()) {
    init {
        val constraints = GridBagConstraints()

        constraints.gridx = 1
        constraints.gridy = 0
        constraints.gridwidth = 2
        add(JLabel(historyItem.objectName), constraints)

        constraints.gridwidth = 1
        for (row in historyItem.components) {
            constraints.gridy++

            constraints.gridx = 0
            add(JButton("\uD83D\uDD04").also {
                it.font = Font.getFont(Font.SERIF)
            }, constraints)

            constraints.gridx = 1
            add(JLabel("${row.tableName}: "), constraints)

            constraints.gridx = 2
            add(JLabel(row.generatedValue).also {
                it.font = it.font.deriveFont(mapOf(
                    TextAttribute.WEIGHT to TextAttribute.WEIGHT_SEMIBOLD
                ))
            }, constraints)
        }
        constraints.gridy++

        constraints.gridx = 0
        add(JLabel("Note: "), constraints)

        constraints.gridx = 1
        add(JLabel(historyItem.note).also {
            it.font = it.font.deriveFont(mapOf(
                TextAttribute.WEIGHT to TextAttribute.WEIGHT_SEMIBOLD
            ))
        }, constraints)
    }
}

class GeneratedObjectList(val model: HistoryListModel) : JPanel() {
    init {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)

        model.addListDataListener(object : ListDataListener {
            val root = this@GeneratedObjectList

            override fun intervalAdded(e: ListDataEvent?) {
                if (e == null) {
                    return
                }
                for (i in e.index0..e.index1) {
                    root.add(ObjectView(model.getDetailedObject(i)), 0)
                }
                root.revalidate()
            }

            override fun intervalRemoved(e: ListDataEvent?) {
                TODO("Not yet implemented")
            }

            override fun contentsChanged(e: ListDataEvent?) {
                TODO("Not yet implemented")
            }
        })

        for (i in 0 until model.size) {
            add(ObjectView(model.getDetailedObject(i)), 0)
        }
    }
}
