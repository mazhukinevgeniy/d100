package org.example.listmodels

import javax.swing.ListModel

interface ExtendedListModel<InsertType> : ListModel<String> {

    fun add(value: InsertType)

    fun itemId(index: Int): Long
}
