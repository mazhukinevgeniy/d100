package org.example.listmodels

import org.example.tables.DbAccessor
import org.example.tables.SelectWithTitles
import javax.swing.AbstractListModel

class ObjectComponentsListModel(
    private val objectId: Long
) : AbstractListModel<String>(), ExtendedListModel<Long> {
    private val queries = DbAccessor.database.objectcomponentsQueries
    private var data = ArrayList<SelectWithTitles>(
        queries.selectWithTitles(objectId).executeAsList()
    )

    override fun add(value: Long) {
        queries.insert(objectId, value)
        val oldSize = data.size
        data = ArrayList(queries.selectWithTitles(objectId).executeAsList())
        if (oldSize > 0) {
            fireContentsChanged(this, 0, oldSize - 1)
        }
        fireIntervalAdded(this, oldSize, data.size - 1)
    }

    override fun itemId(index: Int): Long {
        require(index in data.indices)
        return data[index].collectionID
    }

    override fun getSize(): Int {
        return data.size
    }

    override fun getElementAt(index: Int): String {
        require(index in data.indices)
        return data[index].name
    }
}
