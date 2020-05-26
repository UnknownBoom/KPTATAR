package Controller
//--module-path "E:\Athers\JavaFxSDK\javafx-sdk-14.0.1\lib" --add-modules=javafx.controls,javafx.fxml
import Controller.Connection.Connect
import Controller.Model.*
import Controller.SQL.SqlQuery
import View.DbView
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.control.Alert
import javafx.scene.input.ContextMenuEvent
import javafx.scene.input.MouseEvent
import javafx.stage.Stage
import tornadofx.*
import java.sql.SQLException

class DbController : Controller(){
    private var xOffset:Double? = null
    private var yOffset:Double? = null
    private var infoAlert = Alert(Alert.AlertType.ERROR)



    val DbView: DbView by inject()
    fun init(){
        try{
        DbView.root.setOnMousePressed {
            xOffset = FX.primaryStage.x - it.screenX
            yOffset = FX.primaryStage.y - it.screenY
        }
        DbView.root.setOnMouseDragged {
            FX.primaryStage.x = it.screenX + xOffset!!
            FX.primaryStage.y = it.screenY + yOffset!!
        }
        DbView.BClose.setOnMousePressed {super.workspace.close()}
        var dialogPane = infoAlert.getDialogPane()
        dialogPane.getStylesheets().add("style.css")
        dialogPane.getStyleClass().add("alertMessage")

        DbView.addContextItem.onAction = EventHandler {
            try{
                when(DbView.TabPanel.selectionModel.selectedIndex){
                    0->{DbView.GroupsTable.items.add(StudentsGroups())
                        val p = Connect.getConnection()!!.prepareStatement("INSERT INTO studentsgroups(`id`, `Speciality`, `Tuition_type`, `cours`) VALUES (?,?,?,?)")
                        p.setInt(1,0)
                        p.setString(2, "")
                        p.setString(3, "")
                        p.setInt(4,0)
                        Unity.querylist[DbView.GroupsTable.items.last()] = p
                        
                    }
                    1->{DbView.StudentsTable.items.add(Students())
                        val p = Connect.getConnection()!!.prepareStatement("INSERT INTO `students` (`id`, `F_Name`, `L_Name`, `patronymic`, `group_id`) VALUES (?, ?, ?, ?, ?)")
                        p.setInt(1,0)
                        p.setString(2, "")
                        p.setString(3, "")
                        p.setString(4, "")
                        p.setInt(5,0)
                        Unity.querylist[DbView.StudentsTable.items.last()] = p
                        
                    }
                    2->{DbView.Teacherstable.items.add(Teachers())
                        val p = Connect.getConnection()!!.prepareStatement("INSERT INTO `teachers` (`id`, `F_Name`, `L_Name`, `patronymic`) VALUES (?, ?, ?, ?)")
                        p.setInt(1,0)
                        p.setString(2, "")
                        p.setString(3, "")
                        p.setString(4, "")
                        Unity.querylist[DbView.Teacherstable.items.last()] = p
                        
                    }
                    3->{DbView.ExamsTable.items.add(Exams())
                        val p = Connect.getConnection()!!.prepareStatement("INSERT INTO exams (`id`, `subject`, `teacher_id`, `semester`, `course`) VALUES (?, ?, ?, ?, ?)")
                        p.setInt(1,0)
                        p.setString(2, "")
                        p.setInt(3,0)
                        p.setInt(4,0)
                        p.setInt(5,0)
                        Unity.querylist[DbView.ExamsTable.items.last()] =p
                        
                    }
                    4->{DbView.StudentExamTable.items.add(Student_Exam())
                        val p = Connect.getConnection()!!.prepareStatement("INSERT INTO `student_exam` (`student_id`, `exam_id`, `date`, `mark`) VALUES (?, ?, ?, ?)")
                        p.setInt(1,0)
                        p.setInt(2,0)
                        p.setString(3, "")
                        p.setInt(4,0)
                        Unity.querylist[DbView.StudentExamTable.items.last()] = p
                    }
                }
            }catch (e:Exception){
                println("DbController DbView.addContextItem.onAction\n${e.message}")
            }

        }
            DbView.refreshContextItem.onAction = EventHandler{
                RefreshAll()
            }
        DbView.removeContextItem.onAction = EventHandler {
            try {
                when(DbView.TabPanel.tabs[DbView.TabPanel.selectionModel.selectedIndex].id){
                    "GroupsTab"->{if(DbView.GroupsTable.selectedItem!=null)
                    {

                        if(Unity.querylist.containsKey(DbView.GroupsTable.selectedItem))
                            if(Regex(pattern = ": .*? ").find(Unity.querylist[DbView.GroupsTable.selectedItem].toString())!!.value.toString().equals(": UPDATE ")){
                                val ps = Connect.getConnection()!!.prepareStatement("DELETE FROM `studentsgroups` WHERE (`id` = ?);")
                                DbView.GroupsTable.selectedItem!!.PkId?.let { it1 -> ps.setInt(1, it1) }
                                Unity.querylist[DbView.GroupsTable.selectedItem] = ps
                            }
                        else Unity.querylist.remove(DbView.GroupsTable.selectedItem)
                        else {
                            val ps = Connect.getConnection()!!.prepareStatement("DELETE FROM `studentsgroups` WHERE (`id` = ?);")
                            DbView.GroupsTable.selectedItem!!.PkId?.let { it1 -> ps.setInt(1, it1) }
                            Unity.querylist[DbView.GroupsTable.selectedItem] = ps
                        }
                        Unity.studentsGroupsObservableList.remove(DbView.GroupsTable.selectedItem)
                    }}
                    "StudentsTab"->{if(DbView.StudentsTable.selectedItem!=null){

                        if(Unity.querylist.containsKey(DbView.StudentsTable.selectedItem))
                            if(Regex(pattern = ": .*? ").find(Unity.querylist[DbView.StudentsTable.selectedItem].toString())!!.value.toString().equals(": UPDATE ")){
                                val ps = Connect.getConnection()!!.prepareStatement("DELETE FROM `students` WHERE (`id` = ?);")
                                DbView.StudentsTable.selectedItem!!.PkId?.let { it1 -> ps.setInt(1, it1) }
                                Unity.querylist[DbView.StudentsTable.selectedItem] = ps
                            }
                            else Unity.querylist.remove(DbView.StudentsTable.selectedItem)
                        else {
                            val ps = Connect.getConnection()!!.prepareStatement("DELETE FROM `students` WHERE (`id` = ?);")
                            DbView.StudentsTable.selectedItem!!.PkId?.let { it1 -> ps.setInt(1, it1) }
                            Unity.querylist[DbView.StudentsTable.selectedItem] = ps
                        }
                        Unity.studentsObservableList.remove(DbView.StudentsTable.selectedItem)
                    } }
                    "TeachersTab"->{if(DbView.Teacherstable.selectedItem!=null){

                        if(Unity.querylist.containsKey(DbView.Teacherstable.selectedItem))
                            if(Regex(pattern = ": .*? ").find(Unity.querylist[DbView.Teacherstable.selectedItem].toString())!!.value.toString().equals(": UPDATE ")){
                                val ps = Connect.getConnection()!!.prepareStatement("DELETE FROM `teachers` WHERE (`id` = ?);")
                                DbView.Teacherstable.selectedItem!!.PkId?.let { it1 -> ps.setInt(1, it1) }
                                Unity.querylist[DbView.Teacherstable.selectedItem] = ps
                            }
                            else Unity.querylist.remove(DbView.Teacherstable.selectedItem)
                        else {
                            val ps = Connect.getConnection()!!.prepareStatement("DELETE FROM `teachers` WHERE (`id` = ?);")
                            DbView.Teacherstable.selectedItem!!.PkId?.let { it1 -> ps.setInt(1, it1) }
                            Unity.querylist[DbView.Teacherstable.selectedItem] = ps
                        }
                        Unity.teacherssObservableList.remove(DbView.Teacherstable.selectedItem )
                    } }
                    "ExamsTab"->{if(DbView.ExamsTable.selectedItem!=null){

                        if(Unity.querylist.containsKey(DbView.ExamsTable.selectedItem))
                            if(Regex(pattern = ": .*? ").find(Unity.querylist[DbView.ExamsTable.selectedItem].toString())!!.value.toString().equals(": UPDATE ")){
                                val ps = Connect.getConnection()!!.prepareStatement("DELETE FROM `exams` WHERE (`id` = ?)")
                                DbView.ExamsTable.selectedItem!!.PkId?.let { it1 -> ps.setInt(1, it1) }
                                Unity.querylist[DbView.ExamsTable.selectedItem] = ps
                            }
                            else Unity.querylist.remove(DbView.ExamsTable.selectedItem)
                        else {
                            val ps = Connect.getConnection()!!.prepareStatement("DELETE FROM `exams` WHERE (`id` = ?)")
                            DbView.ExamsTable.selectedItem!!.PkId?.let { it1 -> ps.setInt(1, it1) }
                            Unity.querylist[DbView.ExamsTable.selectedItem] = ps
                        }
                        Unity.examsObservableList.remove(DbView.ExamsTable.selectedItem as Exams)
                    } }
                    "StudentExamTab"->{if(DbView.StudentExamTable.selectedItem!=null){

                        if(Unity.querylist.containsKey(DbView.StudentExamTable.selectedItem))
                            if(Regex(pattern = ": .*? ").find(Unity.querylist[DbView.StudentExamTable.selectedItem].toString())!!.value.toString().equals(": UPDATE ")){
                                val ps = Connect.getConnection()!!.prepareStatement("DELETE FROM `student_exam` WHERE (`student_id` = ?) and (`exam_id` = ?);")
                                DbView.StudentExamTable.selectedItem!!.PkId?.let { it1 -> ps.setInt(1, it1) }
                                DbView.StudentExamTable.selectedItem!!.PkIdExamId?.let { it1 -> ps.setInt(2, it1) }
                                Unity.querylist[DbView.StudentExamTable.selectedItem] = ps
                            }
                            else Unity.querylist.remove(DbView.StudentExamTable.selectedItem)
                        else {
                            var ps = Connect.getConnection()!!.prepareStatement("DELETE FROM `student_exam` WHERE (`student_id` = ?) and (`exam_id` = ?);")
                            DbView.StudentExamTable.selectedItem!!.PkId?.let { it1 -> ps.setInt(1, it1) }
                            DbView.StudentExamTable.selectedItem!!.PkIdExamId?.let { it1 -> ps.setInt(2, it1) }
                            Unity.querylist[DbView.StudentExamTable.selectedItem] = ps
                        }
                        Unity.student_examObservableList.remove(DbView.StudentExamTable.selectedItem)
                    } }
                    else->{println(DbView.TabPanel.tabs[DbView.TabPanel.selectionModel.selectedIndex].id)}
                }

            }catch (e:Exception){
                println(Regex(pattern = ": .*? ").find(Unity.querylist[DbView.GroupsTable.selectedItem].toString())!!.value.toString())
                println("DbController DbView.removeContextItem.onAction\n${e.message}")
            }
        }
        DbView.applyContextItem.onAction = EventHandler{
            try{
                Connect.getConnection()?.autoCommit = false
                Unity.querylist.iterator().forEach {it.value.execute()
                }
                Connect.getConnection()?.autoCommit = true
                println("--------------------------------------------------")
                RefreshAll()
            }catch (e:SQLException){
                Connect.getConnection()?.autoCommit = true
                infoAlert.contentText = e.message
                infoAlert.showAndWait()
            }catch (e:Exception){
                Connect.getConnection()?.autoCommit = true
                println("DbController DbView.applyContextItem.onAction\n${e.message}")
            }
        }
            DbView.Roll.setOnMousePressed { rollApp(it) }
            contextEventBind()
            fillAll()

    }catch(e:Exception){
            println("DbController Init\n${e.message}")
        }
    }
     fun rollApp(e: MouseEvent) {
        val node: Node = e.getSource() as Node
        val stage: Stage = node.getScene().getWindow() as Stage
        stage.setIconified(true)
    }
    public fun InitDb(){
        dropAllTables()
        createTables()
        InsertAll()
    }
    fun InsertAll(){
        insertTeachers()
        insertStudentsGroups()
        insertStudents()
        insertExams()
        insertStudent_Exam()
    }
    fun fillAll(){
        fillAllStudents()
        fillAllStudentsGroups()
        fillAllTeachers()
        fillAllExams()
        fillAllStudentExam()
        fillreportstudents()
        fullreportStudent_Exam()
    }
    fun dropAllTables(){
        Connect.getStatement()?.execute("drop table if exists Student_Exam")
        Connect.getStatement()?.execute("drop table if exists Exams")
    Connect.getStatement()?.execute("drop table if exists Students")
    Connect.getStatement()?.execute("drop table if exists StudentsGroups")
        Connect.getStatement()?.execute("drop table if exists Teachers")




    }
    fun createTableTeachers(){
        Connect.getStatement()?.execute("CREATE TABLE IF NOT EXISTS  Teachers(" +
                "id int PRIMARY KEY AUTO_INCREMENT," +
                    "F_Name varchar(50) not null ," +
                    "L_Name varchar(50) not null ," +
                    "patronymic varchar(50) ) ")
    }
    fun createTableStudentsGroups(){

        Connect.getStatement()?.execute("CREATE TABLE IF NOT EXISTS  StudentsGroups(" +
                "id int PRIMARY KEY AUTO_INCREMENT," +
                "Speciality ${Speciality.IT.toString()} not null ," +
                "Tuition_type ${Tuition_type.Distance.toString()}," +
                "cours int," +
                "CHECK (cours<=4 and cours>0))")
    }
    fun createTableStudents(){

        Connect.getStatement()?.execute("CREATE TABLE IF NOT EXISTS  Students(" +
                "id int PRIMARY KEY AUTO_INCREMENT,"+
                "F_Name varchar(50) not null," +
                "L_Name varchar(50) not null," +
                "patronymic varchar(50)," +
                "group_id int not null," +
                "FOREIGN KEY (group_id)  REFERENCES StudentsGroups (id) ON DELETE CASCADE)")
    }
    fun createTableExams(){

        Connect.getStatement()?.execute("CREATE TABLE IF NOT EXISTS  Exams(" +
                "id int PRIMARY KEY AUTO_INCREMENT," +
                "subject ${Subject.BIO.toString()} not null," +
                "teacher_id int not null," +
                "semester int," +
                "course int," +
                "FOREIGN KEY (teacher_id)  REFERENCES Teachers (id)  ON DELETE CASCADE," +
                "CHECK (semester<=4 and semester>0)," +
                "CHECK (course<=4 and course>0))")
    }
    fun createtalbeStudent_Exam(){
        Connect.getStatement()?.execute("CREATE TABLE IF NOT EXISTS Student_Exam(" +
                "student_id int not null," +
                "exam_id int not null," +
                "date date default (curdate())," +
                "mark int ," +
                "FOREIGN KEY (exam_id)  REFERENCES Exams (id)  ON DELETE CASCADE," +
                "FOREIGN KEY (student_id)  REFERENCES Students (id)  ON DELETE CASCADE," +
                "PRIMARY KEY (student_id, exam_id))")

    }

    fun fillAllStudentsGroups(){
        try{
            val res = SqlQuery().selectAllFromStudentsGroups()
            while(res!!.next()){
                val stgroup =  StudentsGroups()
                stgroup.id = res.getInt(1)
                stgroup.PkId = stgroup.id
                stgroup.speciality = res.getString(2)
                stgroup.tuition_type = res.getString(3)
                stgroup.cours = res.getInt(4)
                Unity.studentsGroupsObservableList.add(stgroup)
            }
        }catch(e:Exception){
            println("DbController fillAllStudentsGroups\n${e.message}")
        }
    }
    fun fillAllTeachers(){
        try{
            val res = SqlQuery().selectAllFromTeachers()
            while(res!!.next()){
                val teach =  Teachers()
                teach.id = res.getInt("id")
                teach.PkId = teach.id
                teach.F_Name = res.getString("F_Name")
                teach.L_Name = res.getString("L_Name")
                teach.patronymic = res.getString("patronymic")
                Unity.teacherssObservableList.add(teach)
            }
        }catch(e:Exception){
            println("DbController fillAllTeachers\n${e.message}")
        }
    }
    fun fillAllExams(){
        try{
            val res = SqlQuery().selectAllFromExams()
            while(res!!.next()){
                val exam =  Exams()
                exam.id = res.getInt("id")
                exam.PkId = exam.id
                exam.subject = res.getString("subject")
                exam.semester = res.getInt("semester")
                exam.teacher_id = res.getInt("teacher_id")
                exam.course = res.getInt("course")
                Unity.examsObservableList.add(exam)
            }
        }catch(e:Exception){
            println("DbController fillAllExams\n${e.message}")
        }
    }
    fun fillAllStudentExam(){
        try{
            val res = SqlQuery().selectAllFromStudentExam()
            while(res!!.next()){
                val stex =  Student_Exam()
                stex.student_id = res.getInt("student_id")
                stex.PkId = stex.student_id
                stex.exam_id = res.getInt("exam_id")
                stex.PkIdExamId = stex.exam_id
                stex.date = res.getDate("date").toString()
                stex.mark = res.getInt("mark")
                Unity.student_examObservableList.add(stex)
            }
        }catch(e:Exception){
            println("DbController fillAllStudentExam\n${e.message}")
        }
    }
    fun fillAllStudents(){
        try{
            val res = SqlQuery().selectAllFromStudents()
            while(res!!.next()){
                val st =  Students()
                st.id = res.getInt("id")
                st.PkId = st.id
                st.F_Name = res.getString("F_Name")
                st.L_Name = res.getString("L_Name")
                st.patronymic = res.getString("patronymic")
                st.group_id = res.getInt("group_id")
                Unity.studentsObservableList.add(st)
            }
        }catch(e:Exception){
            println("DbController fillAllStudents\n${e.message}")
        }
    }
    fun fillreportstudents(){
        val res = Connect?.getStatement()?.executeQuery("SELECT t1.Speciality,t1.Tuition_type,t1.cours,t2.F_Name,t2.L_Name from studentsgroups as t1 join students as t2 on t1.id = t2.group_id order by t1.Speciality,t1.Tuition_type,t1.cours")
        while(res!!.next()){
            val st =  Spec_tt_course()
            st.spec = res.getString("t1.Speciality")
            st.tt = res.getString("t1.Tuition_type")
            st.course = res.getInt("t1.cours")
            st.F_Name = res.getString("t2.F_Name")
            st.L_Name = res.getString("t2.L_Name")
            Unity.reportStudentsTeableOL.add(st)
        }
    }
    fun fullreportStudent_Exam(){
        val res = Connect?.getStatement()?.executeQuery("select t4.Speciality,t4.Tuition_type, t4.cours,t3.F_Name,t3.L_Name,t2.subject,t1.date,t1.mark,t5.F_Name as \"Teacher f_name\",t5.L_Name as \"Teacher L_name\" from student_exam as t1 join exams as t2 join students as t3 join studentsgroups as t4 join teachers as t5 on t1.exam_id = t2.id and t3.id = t1.student_id and t4.id = t3.group_id and t2.teacher_id = t5.id")
        while(res!!.next()){
            val st =  reportStudent_Exam()
            st.spec = res.getString("t4.Speciality")
            st.tt = res.getString("t4.Tuition_type")
            st.course = res.getInt("t4.cours")
            st.F_Name = res.getString("t3.F_Name")
            st.L_Name = res.getString("t3.L_Name")
            st.subj = res.getString("t2.subject")
            st.date = res.getString("t1.date")
            st.mark = res.getInt("t1.mark")
            st.tfname = res.getString("Teacher f_name")
            st.tlname = res.getString("Teacher L_name")
            Unity.reportStudent_ExamTableOl.add(st)
        }
    }
    fun  contextEvent() = EventHandler<ContextMenuEvent> {DbView.contextMenu.show(DbView.currentWindow,it.screenX,it.screenY)}
    fun contextEventBind(){
        DbView.GroupsTable.onContextMenuRequested = contextEvent()
        DbView.StudentExamTable.onContextMenuRequested = contextEvent()
        DbView.StudentsTable.onContextMenuRequested = contextEvent()
        DbView.ExamsTable.onContextMenuRequested = contextEvent()
        DbView.Teacherstable.onContextMenuRequested = contextEvent()
    }
    fun RefreshAll(){
        Unity.querylist.clear()
        Unity.teacherssObservableList.clear()
        Unity.student_examObservableList.clear()
        Unity.examsObservableList.clear()
        Unity.studentsGroupsObservableList.clear()
        Unity.studentsObservableList.clear()
        fillAll()
    }
    fun createTables(){
        createTableTeachers()
        createTableStudentsGroups()
        createTableStudents()
        createTableExams()
        createtalbeStudent_Exam()
    }

    fun insertStudentsGroups(){
        try{
            val spec = listOf("IT","GEO","ARCH","BUILD")
            val t_t = listOf("Full_Time","Distance")
            val ps = Connect.getConnection()?.prepareStatement("INSERT IGNORE INTO `studentsgroups` (`id`, `Speciality`, `Tuition_type`, `cours`) VALUES (?, ?, ?, ?);")
            var i=1
                for(y in 0..3){
                    ps?.setString(2,spec[y])
                    for (z in 0..1){
                        ps?.setString(3,t_t[z])
                        for(g in 1..4){
                            ps?.setInt(4,g)
                            ps?.setInt(1,i)
                            ps?.execute()
                            i++
                        }
                    }
                }

        }catch(e:Exception){
            println("DbController insertStudentsGroups\n${e.message}")
        }
    }
    fun insertTeachers(){
        try{
            Connect?.getStatement()?.execute("INSERT INTO `teachers` (`id`,`F_Name`,`L_Name`) VALUES (1,\"Perry\",\"Mcdaniel\"),(2,\"Elton\",\"Lee\"),(3,\"Ciara\",\"Gould\"),(4,\"Charissa\",\"Reilly\"),(5,\"Myles\",\"Mcdonald\"),(6,\"Keane\",\"Williamson\"),(7,\"Regan\",\"Whitaker\"),(8,\"Barclay\",\"Gibson\"),(9,\"Dante\",\"Pate\"),(10,\"September\",\"Peterson\");")
            Connect?.getStatement()?.execute("INSERT INTO `teachers` (`id`,`F_Name`,`L_Name`) VALUES (11,\"Valentine\",\"Tucker\"),(12,\"Kevin\",\"Ellison\"),(13,\"Hiroko\",\"Roth\"),(14,\"Yvonne\",\"Oliver\"),(15,\"Anthony\",\"Garrison\"),(16,\"Alika\",\"Padilla\"),(17,\"Igor\",\"Richmond\"),(18,\"Heather\",\"Bush\"),(19,\"Brent\",\"Chambers\"),(20,\"Sonia\",\"Dickerson\");")
            Connect?.getStatement()?.execute("INSERT INTO `teachers` (`id`,`F_Name`,`L_Name`) VALUES (21,\"Daniel\",\"Mclean\"),(22,\"Jescie\",\"Gray\"),(23,\"Lavinia\",\"Larson\"),(24,\"Blaze\",\"Kerr\"),(25,\"Ray\",\"Atkins\"),(26,\"Mallory\",\"Harding\"),(27,\"Brenna\",\"Guy\"),(28,\"Melanie\",\"Kirk\"),(29,\"Amos\",\"Anthony\"),(30,\"Reed\",\"Reyes\");")
            Connect?.getStatement()?.execute("INSERT INTO `teachers` (`id`,`F_Name`,`L_Name`) VALUES (31,\"Hayes\",\"Hines\"),(32,\"Elizabeth\",\"Valdez\"),(33,\"Cherokee\",\"Garrett\"),(34,\"Hayden\",\"Allen\"),(35,\"Holmes\",\"Fuller\"),(36,\"Vielka\",\"Murray\"),(37,\"Yuri\",\"Fuller\"),(38,\"Solomon\",\"Riddle\"),(39,\"Harrison\",\"Roberts\"),(40,\"Karina\",\"Bruce\");")
            Connect?.getStatement()?.execute("INSERT INTO `teachers` (`id`,`F_Name`,`L_Name`) VALUES (41,\"Oprah\",\"Duffy\"),(42,\"Shelly\",\"Reese\"),(43,\"Quynn\",\"Gomez\"),(44,\"Graiden\",\"Vasquez\"),(45,\"Zelenia\",\"Clark\"),(46,\"Phelan\",\"Fowler\"),(47,\"Barry\",\"Hahn\"),(48,\"Daniel\",\"Brewer\"),(49,\"Gavin\",\"Sherman\"),(50,\"Zorita\",\"Cobb\");")
            Connect?.getStatement()?.execute("INSERT INTO `teachers` (`id`,`F_Name`,`L_Name`) VALUES (51,\"Juliet\",\"Russell\"),(52,\"Justina\",\"Dickson\"),(53,\"Owen\",\"Deleon\"),(54,\"Geraldine\",\"Mercado\"),(55,\"Hilel\",\"Ramsey\"),(56,\"Lila\",\"Freeman\"),(57,\"Brianna\",\"Lang\"),(58,\"Sigourney\",\"Cardenas\"),(59,\"Shelly\",\"Patel\"),(60,\"Edward\",\"Mcintosh\");")
            Connect?.getStatement()?.execute("INSERT INTO `teachers` (`id`,`F_Name`,`L_Name`) VALUES (61,\"Channing\",\"Myers\"),(62,\"Murphy\",\"Rowland\"),(63,\"Thaddeus\",\"Vazquez\"),(64,\"Aquila\",\"Bass\"),(65,\"Vincent\",\"Riley\"),(66,\"Knox\",\"Gregory\"),(67,\"Shaeleigh\",\"Gilmore\"),(68,\"Cora\",\"Fleming\"),(69,\"Rowan\",\"Lawson\"),(70,\"Victor\",\"Sparks\");")
            Connect?.getStatement()?.execute("INSERT INTO `teachers` (`id`,`F_Name`,`L_Name`) VALUES (71,\"Jena\",\"Meadows\"),(72,\"Allen\",\"Leon\"),(73,\"Cora\",\"Ashley\"),(74,\"Gay\",\"Chang\"),(75,\"Alika\",\"Trujillo\"),(76,\"Marny\",\"Glenn\"),(77,\"Zia\",\"Mccray\"),(78,\"Kibo\",\"Woodward\"),(79,\"Keith\",\"Russell\"),(80,\"Wynne\",\"Simon\");")
            Connect?.getStatement()?.execute("INSERT INTO `teachers` (`id`,`F_Name`,`L_Name`) VALUES (81,\"Alexandra\",\"Maxwell\"),(82,\"Alexandra\",\"Frank\"),(83,\"Ferris\",\"Collins\"),(84,\"Maisie\",\"Riddle\"),(85,\"Patricia\",\"Mcfadden\"),(86,\"Kirk\",\"Mendez\"),(87,\"Charlotte\",\"Silva\"),(88,\"Michael\",\"Ashley\"),(89,\"Barrett\",\"Lester\"),(90,\"Lucian\",\"Brooks\");")
            Connect?.getStatement()?.execute("INSERT INTO `teachers` (`id`,`F_Name`,`L_Name`) VALUES (91,\"Burke\",\"Adams\"),(92,\"Nolan\",\"Morin\"),(93,\"Oliver\",\"Mcfadden\"),(94,\"Cadman\",\"Hurst\"),(95,\"Lois\",\"Burt\"),(96,\"Rafael\",\"Mcmillan\"),(97,\"Lillian\",\"Lott\"),(98,\"Lyle\",\"Burris\"),(99,\"Carson\",\"Vinson\"),(100,\"Carter\",\"Buckley\");")

        }catch(e:Exception){
            println("DbController insertTeachers\n${e.message}")
        }
    }
    fun insertExams(){
        try{
            val ps = Connect.getConnection()?.prepareStatement("INSERT INTO `exams` (`id`, `subject`, `teacher_id`, `semester`, `course`) VALUES (?, ?, ?, ?, ?);")
            val subj = listOf("MATH","RUS","BIO","GEO")
            for(i in 1..100){
                ps?.setInt(1,i)
                ps?.setString(2,subj[i%4])
                ps?.setInt(3,i%101)
                ps?.setInt(4,i%2+1)
                ps?.setInt(5,i%4+1)
                ps?.execute()
            }
        }catch(e:Exception){
            println("DbController insertExams\n${e.message}")
        }

    }
    fun insertStudents(){
        try{
            Connect?.getStatement()?.execute("INSERT INTO students (`id`,`F_Name`,`L_Name`,`group_id`) VALUES (1,\"Camille\",\"Horne\",12),(2,\"Hoyt\",\"Clements\",14),(3,\"Salvador\",\"Woodard\",1),(4,\"Bertha\",\"Fitzgerald\",18),(5,\"Kato\",\"Hahn\",1),(6,\"Kelly\",\"Velazquez\",22),(7,\"Gail\",\"Golden\",14),(8,\"Gloria\",\"Haynes\",4),(9,\"Burton\",\"Pitts\",2),(10,\"Fredericka\",\"Mays\",5),(11,\"Paki\",\"Torres\",29),(12,\"Alexis\",\"Hampton\",10),(13,\"Risa\",\"Salas\",11),(14,\"Eagan\",\"Lynn\",3),(15,\"Josiah\",\"Summers\",20),(16,\"Yael\",\"Shaffer\",23),(17,\"Blair\",\"Caldwell\",32),(18,\"Hiram\",\"Pickett\",13),(19,\"Edan\",\"Padilla\",10),(20,\"Leah\",\"Phillips\",4),(21,\"Harrison\",\"Blevins\",25),(22,\"Kamal\",\"Weiss\",7),(23,\"Olympia\",\"Baxter\",27),(24,\"Xavier\",\"Pugh\",8),(25,\"Germane\",\"Palmer\",10),(26,\"Ivor\",\"Christian\",9),(27,\"Chase\",\"Collins\",25),(28,\"Kendall\",\"Castaneda\",4),(29,\"Tara\",\"Cummings\",1),(30,\"Darryl\",\"Meyers\",15),(31,\"Thaddeus\",\"Landry\",3),(32,\"Hiroko\",\"Bauer\",18),(33,\"Brandon\",\"Sanders\",16),(34,\"Sophia\",\"Holder\",22),(35,\"Oprah\",\"Maynard\",31),(36,\"Selma\",\"Matthews\",19),(37,\"Unity\",\"Obrien\",23),(38,\"Sharon\",\"Monroe\",16),(39,\"Asher\",\"Patton\",17),(40,\"Echo\",\"Patel\",13),(41,\"Axel\",\"Howard\",15),(42,\"Cheryl\",\"Farrell\",19),(43,\"Allegra\",\"Byrd\",22),(44,\"Quon\",\"Frye\",5),(45,\"Xander\",\"Rocha\",28),(46,\"Reed\",\"Howard\",18),(47,\"Quail\",\"Thomas\",28),(48,\"Wendy\",\"Ramos\",3),(49,\"Kay\",\"Holland\",10),(50,\"Kirk\",\"Joyce\",23),(51,\"Venus\",\"Clark\",8),(52,\"Azalia\",\"Mullen\",5),(53,\"Vladimir\",\"Abbott\",3),(54,\"Sebastian\",\"Pena\",3),(55,\"Quinn\",\"Calderon\",26),(56,\"Vera\",\"Velez\",8),(57,\"Alana\",\"Mcdaniel\",10),(58,\"Barry\",\"Copeland\",31),(59,\"Robert\",\"Kerr\",6),(60,\"Tate\",\"Griffith\",17),(61,\"Brandon\",\"Austin\",18),(62,\"Wayne\",\"Adkins\",25),(63,\"Fritz\",\"Hampton\",29),(64,\"Micah\",\"Guy\",21),(65,\"Steven\",\"Key\",12),(66,\"Eleanor\",\"Nolan\",27),(67,\"Octavius\",\"Puckett\",11),(68,\"Lester\",\"Osborn\",3),(69,\"Fredericka\",\"Franks\",26),(70,\"Brenna\",\"Chase\",10),(71,\"Lael\",\"Tyler\",32),(72,\"Daniel\",\"Ellis\",1),(73,\"Deirdre\",\"Watson\",19),(74,\"Ishmael\",\"Chapman\",4),(75,\"Janna\",\"Snider\",28),(76,\"Jenette\",\"Gross\",3),(77,\"Plato\",\"Blackwell\",23),(78,\"Arthur\",\"Davenport\",7),(79,\"Quamar\",\"Sweet\",8),(80,\"Mara\",\"Hebert\",2),(81,\"Rudyard\",\"Randall\",2),(82,\"Cynthia\",\"Rodgers\",12),(83,\"Keelie\",\"Juarez\",16),(84,\"Guy\",\"Weiss\",26),(85,\"Shelby\",\"Holman\",7),(86,\"Vivien\",\"Hopkins\",11),(87,\"Nita\",\"Mccarthy\",8),(88,\"Cheyenne\",\"Matthews\",30),(89,\"Callie\",\"Fisher\",7),(90,\"Olympia\",\"Watson\",11),(91,\"Brenda\",\"Mcguire\",3),(92,\"Lewis\",\"Bentley\",4),(93,\"Tyrone\",\"Lopez\",24),(94,\"Fuller\",\"Crosby\",7),(95,\"Chester\",\"Carroll\",9),(96,\"Eugenia\",\"Morales\",18),(97,\"Omar\",\"Haney\",10),(98,\"Megan\",\"Black\",24),(99,\"Ava\",\"Gonzalez\",8),(100,\"Courtney\",\"White\",9);")
        }catch(e:Exception){
            println("DbController insertStudents\n${e.message}")
        }

    }
    fun insertStudent_Exam(){
        try{
            Connect?.getStatement()?.execute("INSERT IGNORE INTO `student_exam` (`student_id`,`exam_id`,`date`,`mark`) VALUES (51,23,\"2019-09-27\",3),(22,92,\"2020-03-21\",1),(32,49,\"2021-01-18\",4),(60,53,\"2020-12-17\",1),(33,23,\"2019-09-07\",2),(44,81,\"2020-05-16\",1),(36,10,\"2021-02-01\",4),(93,83,\"2019-10-30\",4),(62,40,\"2019-11-16\",2),(35,97,\"2019-11-25\",2);")
            Connect?.getStatement()?.execute("INSERT IGNORE INTO `student_exam` (`student_id`,`exam_id`,`date`,`mark`) VALUES (91,79,\"2019-06-24\",5),(56,18,\"2021-01-10\",4),(69,53,\"2020-09-16\",1),(19,16,\"2021-03-26\",4),(78,25,\"2020-03-12\",2),(19,70,\"2020-04-15\",3),(1,41,\"2019-05-31\",1),(60,91,\"2021-01-31\",5),(78,4,\"2020-05-16\",3),(23,59,\"2020-05-13\",4);")
            Connect?.getStatement()?.execute("INSERT IGNORE INTO `student_exam` (`student_id`,`exam_id`,`date`,`mark`) VALUES (26,83,\"2021-03-27\",5),(96,56,\"2020-11-26\",1),(4,68,\"2020-08-05\",3),(50,85,\"2020-09-22\",4),(13,32,\"2020-04-24\",3),(47,7,\"2019-08-31\",3),(17,58,\"2020-10-23\",1),(67,83,\"2020-06-29\",4),(67,79,\"2019-08-13\",1),(24,99,\"2020-08-20\",2);")
            Connect?.getStatement()?.execute("INSERT IGNORE INTO `student_exam` (`student_id`,`exam_id`,`date`,`mark`) VALUES (77,82,\"2019-10-08\",4),(29,48,\"2021-04-26\",1),(23,65,\"2020-01-04\",2),(4,34,\"2020-11-06\",4),(45,100,\"2019-08-02\",3),(99,26,\"2020-09-11\",3),(97,99,\"2019-08-10\",4),(24,30,\"2020-12-28\",4),(55,33,\"2019-12-02\",4),(82,79,\"2020-08-18\",2);")
            Connect?.getStatement()?.execute("INSERT IGNORE INTO `student_exam` (`student_id`,`exam_id`,`date`,`mark`) VALUES (66,16,\"2019-09-03\",4),(28,41,\"2020-09-04\",3),(10,63,\"2020-08-12\",5),(45,75,\"2020-09-19\",3),(27,89,\"2020-05-13\",1),(98,55,\"2020-07-20\",5),(47,19,\"2020-08-27\",2),(63,17,\"2020-02-10\",1),(96,8,\"2020-01-28\",2),(37,58,\"2021-01-10\",2);")
            Connect?.getStatement()?.execute("INSERT IGNORE INTO `student_exam` (`student_id`,`exam_id`,`date`,`mark`) VALUES (100,29,\"2019-11-26\",5),(24,74,\"2021-03-12\",2),(29,75,\"2020-01-15\",2),(81,9,\"2020-02-16\",1),(59,14,\"2019-10-29\",5),(86,75,\"2020-01-29\",3),(48,94,\"2020-07-03\",4),(40,43,\"2019-06-13\",5),(15,62,\"2019-12-26\",3),(78,7,\"2021-03-23\",4);")
            Connect?.getStatement()?.execute("INSERT IGNORE INTO `student_exam` (`student_id`,`exam_id`,`date`,`mark`) VALUES (71,25,\"2019-09-10\",4),(62,35,\"2019-09-13\",5),(94,96,\"2020-08-09\",5),(54,78,\"2020-04-04\",3),(44,5,\"2019-08-22\",4),(81,50,\"2020-09-03\",3),(21,50,\"2019-07-14\",3),(73,59,\"2019-07-24\",2),(83,9,\"2021-01-16\",5),(55,34,\"2021-01-07\",5);")
            Connect?.getStatement()?.execute("INSERT IGNORE INTO `student_exam` (`student_id`,`exam_id`,`date`,`mark`) VALUES (29,80,\"2021-03-25\",3),(50,59,\"2020-02-24\",3),(24,98,\"2020-08-17\",2),(84,3,\"2019-08-18\",1),(83,80,\"2021-03-21\",5),(100,64,\"2021-02-05\",4),(96,93,\"2019-09-27\",1),(48,31,\"2021-03-30\",5),(73,98,\"2021-05-09\",4),(92,1,\"2020-03-20\",3);")
            Connect?.getStatement()?.execute("INSERT IGNORE INTO `student_exam` (`student_id`,`exam_id`,`date`,`mark`) VALUES (70,90,\"2020-02-21\",5),(60,68,\"2021-04-19\",1),(72,5,\"2019-10-10\",3),(16,100,\"2020-05-28\",4),(65,21,\"2020-12-16\",2),(65,99,\"2020-07-12\",3),(8,4,\"2020-11-07\",3),(30,70,\"2019-09-14\",4),(26,11,\"2020-11-15\",1),(11,88,\"2020-11-20\",4);")
            Connect?.getStatement()?.execute("INSERT IGNORE INTO `student_exam` (`student_id`,`exam_id`,`date`,`mark`) VALUES (96,16,\"2020-09-23\",1),(5,75,\"2020-11-13\",2),(83,91,\"2021-03-04\",5),(96,67,\"2020-12-21\",1),(5,29,\"2021-01-16\",2),(32,79,\"2020-09-23\",5),(5,31,\"2019-07-05\",2),(39,39,\"2020-02-22\",2),(86,26,\"2021-05-22\",3),(87,22,\"2019-07-23\",2);")
            Connect?.getStatement()?.execute("INSERT IGNORE INTO `student_exam` (`student_id`,`exam_id`,`date`,`mark`) VALUES (2,23,\"2020-08-13\",4),(57,90,\"2019-07-21\",3),(98,23,\"2020-07-14\",2),(22,34,\"2020-01-01\",1),(38,40,\"2019-08-13\",2),(47,87,\"2020-04-06\",5),(37,56,\"2021-04-14\",2),(24,19,\"2020-02-17\",4),(38,86,\"2020-04-17\",2),(8,47,\"2020-07-16\",4);")
            Connect?.getStatement()?.execute("INSERT IGNORE INTO `student_exam` (`student_id`,`exam_id`,`date`,`mark`) VALUES (15,93,\"2020-05-02\",4),(29,95,\"2019-09-15\",4),(71,32,\"2020-10-25\",5),(59,91,\"2020-01-20\",3),(4,42,\"2020-10-04\",3),(50,80,\"2020-11-30\",1),(1,37,\"2021-05-20\",4),(91,10,\"2021-01-10\",2),(19,24,\"2020-03-01\",3),(8,62,\"2020-03-05\",5);")
            Connect?.getStatement()?.execute("INSERT IGNORE INTO `student_exam` (`student_id`,`exam_id`,`date`,`mark`) VALUES (83,4,\"2020-08-22\",3),(63,95,\"2019-10-08\",2),(61,11,\"2020-12-19\",2),(34,100,\"2020-11-19\",3),(32,78,\"2020-08-24\",1),(25,27,\"2020-03-05\",5),(86,85,\"2019-11-18\",1),(41,96,\"2020-04-15\",2),(65,83,\"2020-12-13\",4),(49,80,\"2019-09-29\",1);")
            Connect?.getStatement()?.execute("INSERT IGNORE INTO `student_exam` (`student_id`,`exam_id`,`date`,`mark`) VALUES (3,7,\"2021-03-31\",3),(61,6,\"2020-11-21\",5),(42,92,\"2021-02-13\",2),(40,7,\"2019-09-19\",2),(83,66,\"2020-04-19\",3),(41,59,\"2020-01-17\",2),(14,14,\"2021-03-31\",1),(75,43,\"2020-10-12\",2),(3,58,\"2020-08-19\",2),(79,70,\"2021-04-06\",5);")
            Connect?.getStatement()?.execute("INSERT IGNORE INTO `student_exam` (`student_id`,`exam_id`,`date`,`mark`) VALUES (29,87,\"2020-12-19\",1),(50,72,\"2020-08-13\",5),(80,78,\"2020-11-27\",2),(24,49,\"2019-05-29\",4),(10,72,\"2019-07-15\",2),(85,7,\"2020-10-16\",5),(90,70,\"2020-12-11\",3),(4,49,\"2020-12-26\",5),(83,32,\"2019-07-18\",4),(16,5,\"2020-09-25\",4);")
            Connect?.getStatement()?.execute("INSERT IGNORE INTO `student_exam` (`student_id`,`exam_id`,`date`,`mark`) VALUES (54,56,\"2019-07-26\",1),(38,49,\"2020-06-23\",3),(45,49,\"2020-02-24\",5),(51,29,\"2020-11-04\",1),(51,24,\"2020-06-18\",2),(85,5,\"2021-02-28\",4),(75,63,\"2019-07-13\",5),(22,66,\"2019-06-26\",5),(71,25,\"2020-10-19\",2),(93,64,\"2019-07-17\",2);")
            Connect?.getStatement()?.execute("INSERT IGNORE INTO `student_exam` (`student_id`,`exam_id`,`date`,`mark`) VALUES (40,31,\"2019-10-17\",5),(44,21,\"2021-02-28\",4),(94,71,\"2021-04-04\",1),(32,7,\"2021-02-16\",2),(97,30,\"2019-06-24\",5),(5,93,\"2020-09-03\",4),(33,11,\"2020-02-05\",4),(65,49,\"2019-11-22\",3),(29,80,\"2019-12-10\",3),(9,29,\"2020-09-12\",5);")
            Connect?.getStatement()?.execute("INSERT IGNORE INTO `student_exam` (`student_id`,`exam_id`,`date`,`mark`) VALUES (57,23,\"2019-07-16\",3),(54,37,\"2020-11-26\",5),(90,94,\"2021-02-19\",4),(28,88,\"2020-04-16\",4),(38,14,\"2019-08-15\",5),(25,5,\"2019-08-15\",3),(28,42,\"2021-04-05\",2),(46,64,\"2020-03-25\",1),(67,37,\"2020-01-09\",3),(8,26,\"2019-09-04\",1);")
            Connect?.getStatement()?.execute("INSERT IGNORE INTO `student_exam` (`student_id`,`exam_id`,`date`,`mark`) VALUES (24,76,\"2019-09-16\",2),(100,74,\"2021-04-28\",4),(98,72,\"2020-05-06\",5),(26,47,\"2020-10-26\",1),(67,22,\"2020-05-18\",3),(45,86,\"2020-10-15\",5),(3,15,\"2020-06-05\",5),(14,36,\"2020-06-22\",2),(98,97,\"2020-06-23\",4),(49,40,\"2019-11-04\",2);")
            Connect?.getStatement()?.execute("INSERT IGNORE INTO `student_exam` (`student_id`,`exam_id`,`date`,`mark`) VALUES (21,44,\"2021-05-08\",2),(52,78,\"2019-05-28\",2),(15,78,\"2020-10-09\",4),(8,63,\"2020-04-20\",2),(3,43,\"2020-04-11\",3),(50,74,\"2020-07-19\",1),(98,47,\"2019-12-10\",1),(16,68,\"2021-01-28\",3),(64,17,\"2020-04-25\",2),(79,24,\"2020-10-17\",1);")
        }catch(e:Exception){
            println("DbController insertStudent_Exam\n${e.message}")
        }

    }



}



