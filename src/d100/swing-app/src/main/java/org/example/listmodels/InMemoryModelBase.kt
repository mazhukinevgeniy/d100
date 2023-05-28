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

    fun getUnderlyingItemAt(index: Int): ItemType {
        require(index in data.indices)
        return data[index]
    }

    final override fun getElementAt(index: Int): String {
        return toString(getUnderlyingItemAt(index))
    }

    final override fun add(value: String) {
        queryRunner.insert(value)

        if (data.isEmpty()) {
            data.addAll(queryRunner.selectAll())
            if (data.isNotEmpty()) {
                fireIntervalAddedWithRawItem(0, data.size - 1)
            }
        } else {
            val lastSize = data.size
            data.addAll(queryRunner.selectSince(toId(data.last())))
            if (data.size > lastSize) {
                fireIntervalAddedWithRawItem(lastSize, data.size - 1)
            }
        }
    }

    private fun fireIntervalAddedWithRawItem(first: Int, last: Int) {
        fireIntervalAdded(this, first, last)
        for (i in first..last) {
            onItemAdded(data[i])
        }
    }

    final override fun itemId(index: Int): Long {
        require(index in data.indices)
        return toId(data[index])
    }

    abstract fun toString(item: ItemType): String

    abstract fun toId(item: ItemType): Long

    open fun onItemAdded(newItem: ItemType) {
        // do nothing by default
    }
}
