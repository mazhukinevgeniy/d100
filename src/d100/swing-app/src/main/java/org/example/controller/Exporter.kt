package org.example.controller

import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

class Exporter(private val modelProvider: ModelProvider) {

    fun sendTableToClipboard(collectionId: Long) {
        val model = modelProvider.getCollectionModel(collectionId)
        val output = ArrayList<String>()
        for (i in 0 until model.size) {
            output.add(model.getElementAt(i))
        }
        sendToClipboard(output.joinToString(","))
    }

    private fun sendToClipboard(anyString: String) {
        Toolkit.getDefaultToolkit()
            .systemClipboard
            .setContents(
                StringSelection(anyString),
                null
            )
    }
}
