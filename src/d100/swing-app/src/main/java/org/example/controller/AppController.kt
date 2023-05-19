package org.example.controller

import org.example.listmodels.*
import org.example.ui.Screen
import org.example.ui.ScreenManager
import java.awt.Container

open class ModelProvider {
    val collectionsListModel: CollectionsListModel by lazy {
        return@lazy CollectionsListModel()
    }
    val objectListModel: ObjectListModel by lazy {
        return@lazy ObjectListModel()
    }

    fun createItemModel(collectionId: Long): ItemListModel {
        return ItemListModel(collectionId)
    }

    fun createObjectComponentModel(objectId: Long): ObjectComponentsListModel {
        return ObjectComponentsListModel(objectId)
    }
}

object AppController : ModelProvider() {

    var screenManager: ScreenManager? = null

    fun onInitUi(contentPane: Container) {
        screenManager = ScreenManager(contentPane, this as ModelProvider).also {
            it.activateScreen(Screen.Tables)
        }
    }
}
