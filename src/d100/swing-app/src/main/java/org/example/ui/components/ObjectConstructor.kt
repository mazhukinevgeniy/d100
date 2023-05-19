package org.example.ui.components

import org.example.listmodels.CollectionsListModel
import org.example.listmodels.ObjectComponentsListModel
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.*

class ObjectConstructor(
    private val objectComponentsListModel: ObjectComponentsListModel,
    private val collectionsListModel: CollectionsListModel
) : JPanel(GridBagLayout()) {

    init {
        val constraints = GridBagConstraints()
        constraints.fill = GridBagConstraints.HORIZONTAL
        constraints.anchor = GridBagConstraints.FIRST_LINE_START
        constraints.weightx = 1.0

        add(JList(objectComponentsListModel).also {
            it.selectionMode = ListSelectionModel.SINGLE_SELECTION
            it.layoutOrientation = JList.HORIZONTAL_WRAP
            it.visibleRowCount = -1
            JScrollPane(it)
        }, constraints)

        constraints.fill = GridBagConstraints.NONE
        constraints.gridy = 1000
        constraints.weighty = 1.0
        add(JButton("+").also {
            it.addActionListener {
                val answer = ComponentPicker.pickComponent(this@ObjectConstructor, collectionsListModel)
                if (answer != null) {
                    objectComponentsListModel.add(answer)
                }
            }
        }, constraints)
    }
}
