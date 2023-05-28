package org.example.listmodels

import org.example.controller.ModelProvider
import org.example.tables.SelectAllIds
import org.example.tables.SelectComponents
import javax.swing.AbstractListModel

data class HistoryItem(
    val objectName: String,
    val note: String,
    val components: List<SelectComponents>
)

class HistoryListModel(private val modelProvider: ModelProvider) : AbstractListModel<SelectAllIds>() {

    private val data = ArrayList<SelectAllIds>()

    override fun getSize(): Int {
        return data.size
    }

    override fun getElementAt(index: Int): SelectAllIds {
        require(index in data.indices)
        return data[index]
    }

    fun generateObject(objectId: Long, note: String) {
        //todo generation
        //todo insertions

        data.add(SelectAllIds(0, objectId, note, "ObjectName"))
        fireIntervalAdded(this,data.size - 1, data.size - 1)
    }

    fun getDetailedObject(index: Int): HistoryItem {
        require(index in data.indices)
        val source = data[index]
        return HistoryItem(
            source.objectName,
            source.notes,
            modelProvider.getGeneratedComponentModel(source.generationID)
        )
    }
}
