package org.example.ui

import org.example.controller.ModelProvider
import org.example.listmodels.CollectionsListModel
import org.example.listmodels.ExtendedListModel
import org.example.listmodels.ItemListModel
import org.example.ui.components.ContentColumn
import java.awt.*
import javax.swing.*


class TablesScreen(
    contentPane: Container, modelProvider: ModelProvider
) : ScreenBase(contentPane, Screen.Tables) {
    private val itemModels: HashMap<Long, ExtendedListModel<String>> = HashMap()

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
                        if (id !in itemModels) {
                            itemModels[id] = modelProvider.createItemModel(id).also { newModel ->
                                itemCardPane.add(ContentColumn(newModel, "item"), id.toString())
                            }
                        }
                        (itemCardPane.layout as CardLayout).show(itemCardPane, id.toString())
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
