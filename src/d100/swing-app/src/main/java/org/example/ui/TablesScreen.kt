package org.example.ui

import org.example.controller.AppController
import org.example.controller.ModelProvider
import org.example.listmodels.ExtendedListModel
import org.example.ui.components.ContentColumn
import java.awt.CardLayout
import java.awt.Component
import java.awt.Container
import java.awt.Dimension
import javax.swing.JMenuItem
import javax.swing.JPanel
import javax.swing.JPopupMenu
import javax.swing.JSplitPane


class TablesScreen(
    contentPane: Container, modelProvider: ModelProvider
) : ScreenBase(contentPane, Screen.Tables) {
    private val collectionModels: HashMap<Long, ExtendedListModel<String>> = HashMap()

    private val container: Container by lazy {
        val itemCardPane = JPanel(CardLayout()).also {
            it.minimumSize = Dimension(300, 400)
        }

        JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            ContentColumn(modelProvider.collectionsListModel, "table").also {
                it.minimumSize = Dimension(100, 400)
                it.subscribe(object : ContentColumn.Subscriber {
                    override fun handleItemSelection(id: Long) {
                        if (id !in collectionModels) {
                            collectionModels[id] = modelProvider.getCollectionModel(id).also { newModel ->
                                itemCardPane.add(ContentColumn(newModel, "item"), id.toString())
                            }
                        }
                        (itemCardPane.layout as CardLayout).show(itemCardPane, id.toString())
                    }

                    override fun handlePopupRequest(id: Long, x: Int, y: Int) {
                        val menu = JPopupMenu()
                        menu.add(JMenuItem("Copy").also { menuItem ->
                            menuItem.addActionListener {
                                AppController.exporter.sendTableToClipboard(id)
                            }
                        })
                        menu.show(it, x, y)
                    }
                })
            },
            itemCardPane
        )
    }

    override fun getView(): Component {
        return container
    }
}
