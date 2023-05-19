package org.example.listmodels

import javax.swing.ListModel

interface ExtendedListModel : ListModel<String> {

    fun add(value: String)

    fun itemId(index: Int): Long
}
