package org.example.listmodels

import org.example.controller.ModelProvider
import org.example.tables.DbAccessor
import org.example.tables.SelectAllIds
import org.example.tables.SelectComponents
import java.security.CodeSource
import javax.swing.AbstractListModel

class HistoryItem(
    val objectName: String,
    val note: String,
    val components: List<SelectComponents>,
    private val modelProvider: ModelProvider
) {
    fun reroll(source: SelectComponents): SelectComponents {
        val next = modelProvider.getCollectionModel(source.collectionID).pickRandom(modelProvider.random)
        if (next != null) {
            DbAccessor.database.generationQueries.updateGenerationLink(next.itemID, source.genLinkID)
            return source.copy(generatedValue = next.name)
        }
        println("can't reroll, perhaps collection was cleared")
        return source
    }
}

class HistoryListModel(private val modelProvider: ModelProvider) : AbstractListModel<SelectAllIds>() {
    private val queries = DbAccessor.database.generationQueries
    private val data = ArrayList<SelectAllIds>(queries.selectAllIds().executeAsList())

    override fun getSize(): Int {
        return data.size
    }

    override fun getElementAt(index: Int): SelectAllIds {
        require(index in data.indices)
        return data[index]
    }

    fun generateObject(objectId: Long, note: String) {
        queries.insertGeneration(objectId, note)
        val selected = if (data.isEmpty()) {
            queries.selectAllIds().executeAsList()
        } else {
            queries.selectGenerationIdsSince(data.last().generationID).executeAsList().map {
                SelectAllIds(it.generationID, it.objectID, it.notes, it.objectName)
            }
        }
        data.addAll(selected)
        check(selected.size == 1)

        val objectModel = modelProvider.getObjectComponentModel(objectId)
        for (i in 0 until objectModel.size) {
            val componentId = objectModel.itemId(i)
            val generated = modelProvider.getCollectionModel(componentId).pickRandom(modelProvider.random)
            if (generated != null) {
                queries.insertComponent(selected[0].generationID, generated.itemID)
            }
        }

        fireIntervalAdded(this,data.size - 1, data.size - 1)
    }

    fun getDetailedObject(index: Int): HistoryItem {
        require(index in data.indices)
        val source = data[index]
        return HistoryItem(
            source.objectName,
            source.notes,
            modelProvider.getGeneratedComponentModel(source.generationID),
            modelProvider
        )
    }
}
