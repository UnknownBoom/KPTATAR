package Controller.Model

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class Student_Exam(override var PkId: Int? = 0,var PkIdExamId:Int? = 0):IModel {
    val student_idProperty = SimpleIntegerProperty()
    var student_id by student_idProperty
    val exam_idProperty = SimpleIntegerProperty()
    var exam_id by exam_idProperty
    val dateProperty = SimpleStringProperty()
    var date by dateProperty
    val markProperty = SimpleIntegerProperty()
    var mark by markProperty

}