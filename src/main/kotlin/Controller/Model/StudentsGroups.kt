package Controller.Model

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*


class StudentsGroups(override var PkId: Int? = 0) :IModel {

    val idProperty = SimpleIntegerProperty()
    var id by  idProperty
    val specialityProperty = SimpleStringProperty()
    var speciality by specialityProperty
    val tuition_typeProperty = SimpleStringProperty()
    var tuition_type by tuition_typeProperty
    val coursProperty = SimpleIntegerProperty()
    var cours by coursProperty

}
