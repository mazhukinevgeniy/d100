package org.example.ui

import org.example.controller.ModelProvider
import org.example.ui.components.GeneratedObjectList
import org.example.ui.components.PromptTextField
import java.awt.Component
import java.awt.Container
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.*

class GMScreen(
    contentPane: Container, modelProvider: ModelProvider
) : ScreenBase(contentPane, Screen.Game) {

    private val container: Container by lazy {
        JPanel(GridBagLayout()).also { root ->
            val constraints = GridBagConstraints()

            constraints.gridx = 0
            constraints.gridy = 0
            constraints.gridwidth = 2
            root.add(JLabel("placeholder for picker"), constraints)

            constraints.gridx = 0
            constraints.gridy = 1
            constraints.gridwidth = 1
            root.add(JLabel("note editor"), constraints)

            constraints.gridx = 1
            constraints.gridy = 1
            val noteField = PromptTextField("Type note here...")
            root.add(noteField, constraints)

            constraints.gridx = 2
            constraints.gridy = 0
            constraints.gridheight = 2
            constraints.anchor = GridBagConstraints.CENTER
            root.add(JButton("Generate").also {
                it.addActionListener {
                    modelProvider.objectGenerationHistoryListModel.generateObject(
                        //TODO proper
                        0, noteField.text
                    )
                }
            }, constraints)

            constraints.gridx = 0
            constraints.gridy = 2
            constraints.gridheight = 1
            constraints.gridwidth = 3
            constraints.fill = GridBagConstraints.BOTH
            constraints.weightx = 1.0
            constraints.weighty = 1.0
            root.add(
                JScrollPane(GeneratedObjectList(modelProvider.objectGenerationHistoryListModel),
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
            ), constraints)
        }
    }

    override fun getView(): Component {
        return container
    }
}
