package Controller

import Controller.Model.IModel
import javafx.event.EventHandler
import javafx.scene.control.TableCell
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent

class EditingCell : TableCell<IModel?, Number?>() {
    private var textField: TextField? = null
    override fun startEdit() {
        if (!isEmpty) {
            super.startEdit()
            createTextField()
            text = null
            graphic = textField
            textField!!.onKeyReleased = EventHandler { t: KeyEvent ->
                if (t.code == KeyCode.ENTER) {
                    try {
                        println(textField!!.text)
                        val res = Integer.getInteger(textField!!.text)
                        println("YES")
                        commitEdit(res)
                    } catch (e: Exception) {
                        println("No")
                        cancelEdit()
                    }
                    tableView.requestFocus()
                    tableView.selectionModel.selectBelowCell()
                } else if (t.code == KeyCode.ESCAPE) {
                    cancelEdit()
                }
            }
            textField!!.selectAll()
        }
    }

    override fun cancelEdit() {
        super.cancelEdit()
        text = item.toString()
        graphic = null
    }

    override fun updateItem(p0: Number?, empty: Boolean) {
        super.updateItem(item, empty)
        if (empty) {
            text = null
            graphic = null
        } else {
            if (isEditing) {
                if (textField != null) {
                    textField!!.text = string
                }
                text = null
                graphic = textField
            } else {
                text = string
                graphic = null
            }
        }
    }

    private fun createTextField() {
        textField = TextField(string)
        textField!!.minWidth = width - graphicTextGap * 2
        textField!!.focusedProperty().addListener { arg0, arg1, arg2 ->
            if (!arg2!!) {
                try {
                    val res = Integer.getInteger(textField!!.text)
                    commitEdit(res)
                } catch (e: Exception) {
                    cancelEdit()
                }
            }
        }
    }

    private val string: String
        private get() = if (item == null) "" else item.toString()
}
