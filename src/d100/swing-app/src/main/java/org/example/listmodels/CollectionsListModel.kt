package org.example.listmodels

import org.example.controller.ModelProvider
import org.example.tables.Collections
import org.example.tables.DbAccessor
import javax.swing.AbstractListModel

class CollectionPickerView(val source: Collections) {
    override fun toString(): String {
        return source.name
    }
}

class CollectionsListModel(
    private val modelProvider: ModelProvider
) : InMemoryModelBase<Collections>(
    object : QueryRunner<Collections> {
        private val queries = DbAccessor.database.collectionQueries

        override fun insert(value: String) {
            queries.insert(value)
        }

        override fun selectAll(): List<Collections> {
            return queries.selectAll().executeAsList()
        }

        override fun selectSince(id: Long): List<Collections> {
            return queries.selectSince(id).executeAsList()
        }
    }
) {
    override fun toId(item: Collections): Long {
        return item.collectionID
    }

    override fun toString(item: Collections): String {
        return item.name
    }

    override fun onItemAdded(newItem: Collections) {
        val objectModel = modelProvider.objectListModel
        objectModel.add(newItem.name)
        modelProvider.getObjectComponentModel(
            objectModel.getUnderlyingItemAt(objectModel.size - 1).objectID
        ).add(newItem.collectionID)
    }

    fun simplePickerModel(): Array<CollectionPickerView> {
        return data.map { CollectionPickerView(it) }.toTypedArray()
    }
}
