package org.example.ui

import org.example.controller.ModelProvider
import java.awt.*
import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import javax.swing.*


class TablesScreen(
    contentPane: Container, modelProvider: ModelProvider
) : ScreenBase(contentPane, Screen.Tables) {
    private val tablesUi: Component by lazy {
        val panel = JPanel(GridBagLayout())

        val constraints = GridBagConstraints().also {
            it.fill = GridBagConstraints.HORIZONTAL
            it.weightx = 1.0
            it.gridx = 0
            it.gridy = 0
        }

        panel.add(JTextField().also {
            val prompt = "New table..."

            val textfield = it
            it.isEditable = true
            it.text = prompt

            it.addActionListener {
                if (textfield.text.isNotEmpty()) {
                    modelProvider.collectionsListModel.add(textfield.text)
                    textfield.text = ""
                }
            }
            it.addFocusListener(object : FocusListener {
                override fun focusGained(e: FocusEvent?) {
                    if (textfield.text == prompt) {
                        textfield.text = ""
                    }
                }
                override fun focusLost(e: FocusEvent?) {
                    if (textfield.text.isEmpty()) {
                        textfield.text = prompt
                    }
                }
            })
            it.minimumSize = Dimension(100, 20)
        }, constraints)

        constraints.fill = GridBagConstraints.BOTH
        constraints.gridy = 1
        constraints.gridheight = GridBagConstraints.REMAINDER
        constraints.weighty = 1.0

        panel.add(JList(modelProvider.collectionsListModel).also {
            it.addListSelectionListener {
                // TODO: on selection, display secondary view with table items / editing
            }
            it.selectionMode = ListSelectionModel.SINGLE_SELECTION
            it.layoutOrientation = JList.HORIZONTAL_WRAP
            it.visibleRowCount = -1
            JScrollPane(it)
        }, constraints)

        panel.minimumSize = Dimension(100, 400)

        panel
    }

    private val container: Container by lazy {
        JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            tablesUi,
            JTextField("todo: editing table contents!")
        )
    }

    override fun getView(): Component {
        return container
    }
}
