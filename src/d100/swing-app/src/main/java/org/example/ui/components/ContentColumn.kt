package org.example.ui.components

import org.example.listmodels.ExtendedListModel
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.*

class ContentColumn<Model : ExtendedListModel>(
    private val model: Model, itemName: String
) : JPanel(GridBagLayout()) {
    public interface Subscriber {
        fun handleItemSelection(id: Long)
    }

    private val subscribers: ArrayList<Subscriber> = ArrayList()

    init {
        val constraints = GridBagConstraints().also {
            it.fill = GridBagConstraints.HORIZONTAL
            it.weightx = 1.0
            it.gridx = 0
            it.gridy = 0
        }

        add(PromptTextField("New $itemName...").also {
            val textfield = it

            it.addActionListener {
                if (textfield.text.isNotEmpty()) {
                    model.add(textfield.text)
                    textfield.text = ""
                }
            }
            it.minimumSize = Dimension(100, 20)
        }, constraints)

        constraints.fill = GridBagConstraints.BOTH
        constraints.gridy = 1
        constraints.gridheight = GridBagConstraints.REMAINDER
        constraints.weighty = 1.0

        fun onSelection(list: JList<String>) {
            if (list.selectedIndex in 0 until model.size) {
                val id = model.itemId(list.selectedIndex)
                for (subscriber in subscribers) {
                    subscriber.handleItemSelection(id)
                }
            }
        }

        add(JList(model).also {
            it.addListSelectionListener { _ ->
                onSelection(it)
                // TODO: why does it not work on first click?
            }
            it.selectionMode = ListSelectionModel.SINGLE_SELECTION
            it.layoutOrientation = JList.HORIZONTAL_WRAP
            it.visibleRowCount = -1
            JScrollPane(it)
        }, constraints)
    }

    fun subscribe(subscriber: Subscriber) {
        subscribers.add(subscriber)
    }
}
