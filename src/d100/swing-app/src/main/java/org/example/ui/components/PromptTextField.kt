package org.example.ui.components

import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import javax.swing.JTextField

class PromptTextField(var prompt: String) : JTextField(prompt) {
    init {
        isEditable = true

        addFocusListener(object : FocusListener {
            override fun focusGained(e: FocusEvent?) {
                if (this@PromptTextField.text == this@PromptTextField.prompt) {
                    this@PromptTextField.text = ""
                }
            }
            override fun focusLost(e: FocusEvent?) {
                if (this@PromptTextField.text.isEmpty()) {
                    this@PromptTextField.text = this@PromptTextField.prompt
                }
            }
        })
    }

    constructor() : this("")
}
