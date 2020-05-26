package Controller.Model

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*


class  Teachers(override var PkId: Int? = 0):IModel {

    val idProperty = SimpleIntegerProperty()
    var id by idProperty
    val F_NameProperty = SimpleStringProperty()
    var F_Name by F_NameProperty
    val L_NameProperty = SimpleStringProperty()
    var L_Name by L_NameProperty
    val patronymicProperty = SimpleStringProperty()
    var patronymic by patronymicProperty
}

