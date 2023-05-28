package org.example.ui.components

import org.example.listmodels.ExtendedListModel
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.JList
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.ListSelectionModel
import javax.swing.border.EmptyBorder
import javax.swing.event.ListDataEvent
import javax.swing.event.ListDataListener

class ContentColumn<Model : ExtendedListModel<String>>(
    private val model: Model, itemName: String
) : JPanel(GridBagLayout()) {
    public interface Subscriber {
        fun handleItemSelection(id: Long)
    }

    private val subscribers: ArrayList<Subscriber> = ArrayList()
    private val list = JList(model)

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

        add(list.also {
            it.addListSelectionListener { _ ->
                onSelection()
            }
            it.border = EmptyBorder(2, 2, 2, 2)
            it.selectionMode = ListSelectionModel.SINGLE_SELECTION
            it.layoutOrientation = JList.HORIZONTAL_WRAP
            it.visibleRowCount = -1
            if (model.size > 0) {
                it.selectedIndex = 0
            } else {
                model.addListDataListener(object : ListDataListener {
                    override fun intervalAdded(e: ListDataEvent?) {
                        if (e?.index0 == 0) {
                            it.selectedIndex = 0
                        }
                    }

                    override fun intervalRemoved(e: ListDataEvent?) {
                    }

                    override fun contentsChanged(e: ListDataEvent?) {
                    }
                })
            }
            JScrollPane(it)
        }, constraints)
    }

    private fun onSelection(singleSubscriber: Subscriber? = null) {
        if (list.selectedIndex in 0 until model.size) {
            val id = model.itemId(list.selectedIndex)
            if (singleSubscriber != null) {
                singleSubscriber.handleItemSelection(id)
            } else {
                for (subscriber in subscribers) {
                    subscriber.handleItemSelection(id)
                }
            }
        }
    }

    fun subscribe(subscriber: Subscriber) {
        subscribers.add(subscriber)
        onSelection(subscriber)
    }
}
