package org.example.listmodels

import org.example.tables.DbAccessor
import org.example.tables.Items
import kotlin.random.Random

class CollectionListModel(private val collectionId: Long) : InMemoryModelBase<Items>(
    object : QueryRunner<Items> {
        private val queries = DbAccessor.database.itemQueries

        override fun insert(value: String) {
            queries.insert(collectionId, value)
        }

        override fun selectAll(): List<Items> {
            return queries.selectByCollectionId(collectionId).executeAsList()
        }

        override fun selectSince(id: Long): List<Items> {
            return queries.selectByCollectionIdSinceItemId(collectionId, id).executeAsList()
        }
    }
) {
    override fun toString(item: Items): String {
        return item.name
    }

    override fun toId(item: Items): Long {
        return item.itemID
    }

    fun pickRandom(random: Random): Items? {
        if (data.isEmpty()) {
            return null
        }
        return data[random.nextInt(0, data.size)]
    }
}
