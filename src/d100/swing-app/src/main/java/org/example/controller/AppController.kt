package org.example.controller

import org.example.listmodels.*
import org.example.tables.DbAccessor
import org.example.tables.SelectComponents
import org.example.ui.Screen
import org.example.ui.ScreenManager
import java.awt.Container
import kotlin.random.Random

open class ModelProvider {
    val random = Random(System.currentTimeMillis())

    private val collectionModels: HashMap<Long, CollectionListModel> = HashMap()
    private val objectComponentModels: HashMap<Long, ObjectComponentsListModel> = HashMap()

    val objectListModel: ObjectListModel by lazy {
        return@lazy ObjectListModel()
    }
    val collectionsListModel: CollectionsListModel by lazy {
        return@lazy CollectionsListModel(this)
    }
    val objectGenerationHistoryListModel: HistoryListModel by lazy {
        return@lazy HistoryListModel(this)
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

    fun getGeneratedComponentModel(generationId: Long): List<SelectComponents> {
        return DbAccessor.database.generationQueries.selectComponents(generationId).executeAsList()
    }
}

object AppController : ModelProvider() {

    var screenManager: ScreenManager? = null
    var exporter = Exporter(this)

    fun onInitUi(contentPane: Container) {
        screenManager = ScreenManager(contentPane, this as ModelProvider).also {
            it.activateScreen(Screen.Tables)
        }
    }
}
