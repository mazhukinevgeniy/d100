package org.example.controller

import org.example.listmodels.*
import org.example.ui.Screen
import org.example.ui.ScreenManager
import java.awt.Container

open class ModelProvider {
    private val collectionModels: HashMap<Long, CollectionListModel> = HashMap()
    private val objectComponentModels: HashMap<Long, ObjectComponentsListModel> = HashMap()

    val objectListModel: ObjectListModel by lazy {
        return@lazy ObjectListModel()
    }
    val collectionsListModel: CollectionsListModel by lazy {
        return@lazy CollectionsListModel(this)
    }
    val objectGenerationHistoryListModel: HistoryListModel by lazy {
        return@lazy HistoryListModel()
    }

    fun getCollectionModel(collectionId: Long): CollectionListModel {
        if (collectionId !in collectionModels) {
            collectionModels[collectionId] = CollectionListModel(collectionId)
        }
        return collectionModels[collectionId]!!
    }

    fun getObjectComponentModel(objectId: Long): ObjectComponentsListModel {
        if (objectId !in objectComponentModels) {
            objectComponentModels[objectId] = ObjectComponentsListModel(objectId)
        }
        return objectComponentModels[objectId]!!
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
