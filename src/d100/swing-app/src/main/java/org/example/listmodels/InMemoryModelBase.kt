package org.example.listmodels

import javax.swing.AbstractListModel

interface QueryRunner<ItemType> {

    fun insert(value: String)
    fun selectAll(): List<ItemType>

    fun selectSince(id: Long): List<ItemType>
}

abstract class InMemoryModelBase<ItemType>(
    private val queryRunner: QueryRunner<ItemType>
) : AbstractListModel<String>(), ExtendedListModel<String> {
    protected val data = ArrayList<ItemType>(queryRunner.selectAll())

    final override fun getSize(): Int {
        return data.size
    }

    final override fun getElementAt(index: Int): String {
        require(index in data.indices)
        return toString(data[index])
    }

    final override fun add(value: String) {
        queryRunner.insert(value)

        if (data.isEmpty()) {
            data.addAll(queryRunner.selectAll())
            if (data.isNotEmpty()) {
                fireIntervalAdded(this, 0, data.size - 1)
            }
        } else {
            val lastSize = data.size
            data.addAll(queryRunner.selectSince(toId(data.last())))
            if (data.size > lastSize) {
                fireIntervalAdded(this, lastSize, data.size - 1)
            }
        }
    }

    final override fun itemId(index: Int): Long {
        require(index in data.indices)
        return toId(data[index])
    }

    abstract fun toString(item: ItemType): String

    abstract fun toId(item: ItemType): Long
}
