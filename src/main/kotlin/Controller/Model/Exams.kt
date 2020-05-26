package Controller.Model

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*


class Exams(override var PkId: Int? = 0) :IModel {
    val idProperty = SimpleIntegerProperty()
    var id by idProperty
    val subjectProperty = SimpleStringProperty()
    var subject by subjectProperty
    val semesterProperty = SimpleIntegerProperty()
    var semester by semesterProperty
    val teacher_idProperty = SimpleIntegerProperty()
    var teacher_id by teacher_idProperty
    val courseProperty = SimpleIntegerProperty()
    var course by courseProperty
}

