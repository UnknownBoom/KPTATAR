package Controller.Model

import javafx.collections.FXCollections
import java.sql.PreparedStatement

object Unity {
    val querylist = LinkedHashMap<Any?,PreparedStatement>()
    val studentsObservableList = FXCollections.observableArrayList<Students>()
    val teacherssObservableList = FXCollections.observableArrayList<Teachers>()
    val studentsGroupsObservableList = FXCollections.observableArrayList<StudentsGroups>()
    val examsObservableList = FXCollections.observableArrayList<Exams>()
    val student_examObservableList = FXCollections.observableArrayList<Student_Exam>()
    val reportStudentsTeableOL = FXCollections.observableArrayList<Spec_tt_course>()
    val reportStudent_ExamTableOl = FXCollections.observableArrayList<reportStudent_Exam>()

}