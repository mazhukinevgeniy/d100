package org.example.listmodels

import org.example.tables.Collections
import org.example.tables.DbAccessor
import javax.swing.AbstractListModel

class CollectionsListModel : AbstractListModel<String>() {

    private val queries = DbAccessor.database.collectionQueries

    private val data = ArrayList<Collections>(
        queries.selectAll().executeAsList()
    )

    override fun getSize(): Int {
        return data.size
    }

    override fun getElementAt(index: Int): String {
        require(index in data.indices) {
            "invalid access to CollectionsListModel: $index not in ${data.indices}"
        }
        return data[index].name
    }

    fun add(name: String) {
        queries.insert(name)

        if (data.isEmpty()) {
            data.addAll(queries.selectAll().executeAsList())
            if (data.isNotEmpty()) {
                fireIntervalAdded(this, 0, data.size - 1)
            }
        } else {
            val lastSize = data.size
            data.addAll(queries.selectSince(data.last().collectionID).executeAsList())
            if (data.size > lastSize) {
                fireIntervalAdded(this, lastSize, data.size - 1)
            }
        }
    }

    fun remove(id: Long) {
        TODO("impl")
    }
}
