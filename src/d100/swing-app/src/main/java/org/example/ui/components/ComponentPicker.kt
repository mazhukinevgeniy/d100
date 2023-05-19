package org.example.ui.components

import org.example.listmodels.CollectionPickerView
import org.example.listmodels.CollectionsListModel
import java.awt.Component
import javax.swing.JOptionPane

object ComponentPicker {
    fun pickComponent(frame: Component, model: CollectionsListModel): Long? {
        val pickerModel = model.simplePickerModel()

        if (pickerModel.isEmpty()) {
            JOptionPane.showMessageDialog(
                frame,
                "Create some generation tables first",
                "Error",
                JOptionPane.ERROR_MESSAGE
            )
            return null
        }

        val result: Any? = JOptionPane.showInputDialog(
            frame,
            "Pick additional component",
            "Component picker",
            JOptionPane.PLAIN_MESSAGE,
            null,
            pickerModel,
            pickerModel[0]
        )

        if (result != null && result is CollectionPickerView) {
            return result.source.collectionID
        }
        return null
    }
}
