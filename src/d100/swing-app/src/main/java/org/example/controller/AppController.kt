package org.example.controller

import org.example.listmodels.CollectionsListModel
import org.example.ui.Screen
import org.example.ui.ScreenManager
import java.awt.Container

open class ModelProvider {
    val collectionsListModel: CollectionsListModel by lazy {
        CollectionsListModel()
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
