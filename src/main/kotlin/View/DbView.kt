package View

import Controller.Connection.Connect
import Controller.Model.*
import javafx.collections.FXCollections
import javafx.scene.Parent
import javafx.scene.control.*
import javafx.scene.image.ImageView
import javafx.util.StringConverter
import tornadofx.*


class DbView : View("My View") {
    override val root: Parent by fxml("/FXML/Db.fxml")


   public val TabPanel:TabPane by fxid()

   public val Roll:ImageView by fxid()

   public val BClose:ImageView by fxid()

   public val GroupsTab:Tab by fxid()

   public val GroupsTable:TableView<StudentsGroups> by fxid()


   public val StudentsTab:Tab by fxid()

   public val StudentsTable:TableView<Students> by fxid()



   public val TeachersTab:Tab by fxid()

   public val Teacherstable:TableView<Teachers> by fxid()


   public val ExamsTab:Tab by fxid()

   public val ExamsTable:TableView<Exams> by fxid()


   public val StudentExamTab:Tab by fxid()

   public val StudentExamTable:TableView<Student_Exam> by fxid("StudentExamTable")
    
    public val  reportStudents:Tab by fxid()

    public val  reportStudentsTeable:TableView<Spec_tt_course> by fxid()

    public val  reportStudent_ExamTab:Tab by fxid("reportStudent_Exam")

    public val reportStudent_ExamTable:TableView<reportStudent_Exam> by fxid()


    val addContextItem = MenuItem("add")
    val removeContextItem = MenuItem("delete")
    val applyContextItem = MenuItem("apply")
    val refreshContextItem = MenuItem("refresh")
    val contextMenu = ContextMenu(addContextItem,removeContextItem,applyContextItem,refreshContextItem)


     init{
         binding()
         createColumns()
    }
    fun binding(){
        try{
            StudentsTable.items = Unity.studentsObservableList
            Teacherstable.items = Unity.teacherssObservableList
            GroupsTable.items = Unity.studentsGroupsObservableList
            StudentExamTable.items = Unity.student_examObservableList
            ExamsTable.items = Unity.examsObservableList
            reportStudentsTeable.items = Unity.reportStudentsTeableOL
            reportStudent_ExamTable.items = Unity.reportStudent_ExamTableOl
        }catch (e:Exception ){
            println("DbView binding\n${e.message}")
        }
    }

    fun createColumns(){
        try{

            GroupsTable.apply{
                isEditable = true
                column("Id",StudentsGroups::idProperty).makeEditable(IntConverter)
                column("Speciality",StudentsGroups::specialityProperty).makeEditable().useComboBox(FXCollections.observableArrayList("IT","GEO","ARCH","BUILD"))
                column("Tuition_type",StudentsGroups::tuition_typeProperty).makeEditable().useComboBox(FXCollections.observableArrayList("Full_Time","Distance"))
                column("Cours",StudentsGroups::coursProperty).makeEditable().useComboBox(FXCollections.observableArrayList<Number>(1,2,3,4))
                onEditCommit {
                    if(Unity.querylist.containsKey(it)){
                        Unity.querylist[it]?.setInt(1,it.id)
                        Unity.querylist[it]?.setString(2,it.speciality)
                        Unity.querylist[it]?.setString(3,it.tuition_type)
                        Unity.querylist[it]?.setInt(4,it.cours)
                    }
                    else if (!Unity.querylist.containsKey(it)){
                        val p = Connect.getConnection()!!.prepareStatement("UPDATE studentsgroups SET `id` =?, `Speciality` = ?, `Tuition_type` = ?, `cours` = ? WHERE (`id` = ?);")
                        p.setInt(5,it.PkId?:0)
                        p.setInt(1,it.id?:0)
                        p.setString(2,it.speciality?:"")
                        p.setString(3,it.tuition_type?:"")
                        p.setInt(4,it.cours?:0)
                        Unity.querylist[it] = p
                    }
                }

            }
            StudentsTable.apply {
                isEditable = true
                column("Id",Students::idProperty).makeEditable(IntConverter)
                column("F_Name",Students::F_NameProperty).makeEditable()
                column("L_Name",Students::L_NameProperty).makeEditable()
                column("patronymic",Students::patronymicProperty).makeEditable()
                column("group_id",Students::group_idProperty).makeEditable(IntConverter)
                onEditCommit {
                    if(Unity.querylist.containsKey(it)){
                        Unity.querylist[it]?.setInt(1,it.id?:0)
                        Unity.querylist[it]?.setString(2,it.F_Name?:"")
                        Unity.querylist[it]?.setString(3,it.L_Name?:"")
                        Unity.querylist[it]?.setString(4,it.patronymic?:"")
                        Unity.querylist[it]?.setInt(5,it.group_id?:0)
                    }
                    else if (!Unity.querylist.containsKey(it)){
                        
                        val p = Connect.getConnection()!!.prepareStatement("UPDATE `students` SET `id` = ?, `F_Name` = ?, `L_Name` = ?, `patronymic` = ?, `group_id` = ? WHERE (`id` = ?);")
                        p?.setInt(6,it.PkId?:0)
                        p?.setInt(1,it.id?:0)
                        p?.setString(2,it.F_Name?:"")
                        p?.setString(3,it.L_Name?:"")
                        p?.setString(4,it.patronymic?:"")
                        p?.setInt(5,it.group_id?:0)
                        Unity.querylist[it] = p
                    }
                }

            }
            Teacherstable.apply{
                isEditable = true
                column("Id", Teachers::idProperty).makeEditable(IntConverter)
                column("F_Name",Teachers::F_NameProperty).makeEditable()
                column("L_Name",Teachers::L_NameProperty).makeEditable()
                column("patronymic",Teachers::patronymicProperty).makeEditable()
                onEditCommit {
                    if(Unity.querylist.containsKey(it)){
                        Unity.querylist[it]?.setInt(1,it.id?:0)
                        Unity.querylist[it]?.setString(2,it.F_Name?:"")
                        Unity.querylist[it]?.setString(3,it.L_Name?:"")
                        Unity.querylist[it]?.setString(4,it.patronymic?:"")
                    }
                    else if (!Unity.querylist.containsKey(it)){
                        
                        val p = Connect.getConnection()!!.prepareStatement("UPDATE teachers SET `id` = ?, `F_Name` = ?, `L_Name` = ?, `patronymic` = ? WHERE (`id` = ?);")
                        p?.setInt(5,it.PkId?:0)
                        p?.setInt(1,it.id?:0)
                        p?.setString(2,it.F_Name?:"")
                        p?.setString(3,it.L_Name?:"")
                        p?.setString(4,it.patronymic?:"")
                        Unity.querylist[it] = p
                    }
                }

            }
            ExamsTable.apply{
                isEditable = true
                column("Id", Exams::idProperty).makeEditable(IntConverter)
                column("Subject", Exams::subjectProperty).makeEditable().useComboBox(FXCollections.observableArrayList("MATH","RUS","BIO","GEO"))
                column("Teacher_id", Exams::teacher_idProperty).makeEditable(IntConverter)
                column("Course", Exams::courseProperty).makeEditable().useComboBox(FXCollections.observableArrayList<Number>(1,2,3,4))
                column("Semester", Exams::semesterProperty).makeEditable().useComboBox(FXCollections.observableArrayList<Number>(1,2))
                onEditCommit {
                    if(Unity.querylist.containsKey(it)){
                        Unity.querylist[it]?.setInt(1,it.id?:0)
                        Unity.querylist[it]?.setString(2,it.subject?:"")
                        Unity.querylist[it]?.setInt(3,it.teacher_id?:0)
                        Unity.querylist[it]?.setInt(4,it.semester?:0)
                        Unity.querylist[it]?.setInt(5,it.course?:0)
                    }
                    else if (!Unity.querylist.containsKey(it)){
                        
                        val p = Connect.getConnection()!!.prepareStatement("UPDATE exams SET `id` = ?, `subject` = ?, `teacher_id` = ?, `semester` = ?, `course` = ? WHERE (`id` = ?);")
                        p?.setInt(6,it.PkId?:0)
                        p?.setInt(1,it.id?:0)
                        p?.setString(2,it.subject?:"")
                        p?.setInt(3,it.teacher_id?:0)
                        p?.setInt(4,it.semester?:0)
                        p?.setInt(5,it.course?:0)
                        Unity.querylist[it] = p
                    }
                }

            }
            StudentExamTable.apply{
                isEditable = true
                column("Student_id", Student_Exam::student_idProperty).makeEditable(IntConverter)
                column("Exam_id", Student_Exam::exam_idProperty).makeEditable(IntConverter)
                column("Date", Student_Exam::dateProperty).makeEditable()
                column("Mark", Student_Exam::markProperty).makeEditable().useComboBox(FXCollections.observableArrayList<Number>(1,2,3,4,5,0))
                onEditCommit {
                    if(Unity.querylist.containsKey(it)){
                        Unity.querylist[it]?.setInt(1,it.student_id?:0)
                        Unity.querylist[it]?.setInt(2,it.exam_id?:0)
                        Unity.querylist[it]?.setString(3,it.date?:"")
                        Unity.querylist[it]?.setInt(4,it.mark?:0)
                    }
                    else if (!Unity.querylist.containsKey(it)){
                        
                        val p = Connect.getConnection()!!.prepareStatement("UPDATE student_exam SET `student_id` = ?, `exam_id` = ?, `date` = ?, `mark` = ? WHERE (`student_id` = ?) and (`exam_id` = ?);")
                        p?.setInt(5,it.PkId?:0)
                        p?.setInt(6,it.PkIdExamId?:0)
                        p?.setInt(1,it.student_id?:0)
                        p?.setInt(2,it.exam_id?:0)
                        p?.setString(3,it.date?:"")
                        p?.setInt(4,it.mark?:0)
                        Unity.querylist[it] = p
                    }
                }
            }
            reportStudentsTeable.apply{
                column("Speciality", Spec_tt_course ::spec)
                column("Tuition_type", Spec_tt_course ::tt)
                column("cours", Spec_tt_course ::course)
                column("F_Name", Spec_tt_course ::F_Name)
                column("L_Name", Spec_tt_course ::L_Name)
            }
            reportStudent_ExamTable.apply{
                column("Speciality", reportStudent_Exam ::spec)
                column("Tuition_type", reportStudent_Exam ::tt)
                column("cours", reportStudent_Exam ::course)
                column("F_Name", reportStudent_Exam ::F_Name)
                column("L_Name", reportStudent_Exam ::L_Name)
                column("subject", reportStudent_Exam ::subj)
                column("date", reportStudent_Exam ::date)
                column("mark", reportStudent_Exam ::mark)
                column("Teacher f_name", reportStudent_Exam ::tfname)
                column("Teacher L_name", reportStudent_Exam ::tlname)
            }
        }catch(e:Exception){
            println("DbView createColumns\n${e.message}")
        }
    }

}
object IntConverter : StringConverter<Number>() {
    override fun toString(amount: Number?) = try{
        amount.toString()
    }catch (e:Exception){
        null
    }
    override fun fromString(string: String?) =try{
        if (string == null) 0 else string.toInt()
    }catch (e:Exception){
        0
    }

}

