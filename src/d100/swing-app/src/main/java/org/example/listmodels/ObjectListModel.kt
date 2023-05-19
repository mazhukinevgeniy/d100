package org.example.listmodels

import org.example.tables.DbAccessor
import org.example.tables.Objects

class ObjectListModel : InMemoryModelBase<Objects>(
    object : QueryRunner<Objects> {
        val queries = DbAccessor.database.objectQueries

        override fun insert(value: String) {
            queries.insert(value)
        }

        override fun selectAll(): List<Objects> {
            return queries.selectAll().executeAsList()
        }

        override fun selectSince(id: Long): List<Objects> {
            return queries.selectSince(id).executeAsList()
        }
    }
) {
    override fun toString(item: Objects): String {
        return item.name
    }

    override fun toId(item: Objects): Long {
        return item.objectID
    }
}
