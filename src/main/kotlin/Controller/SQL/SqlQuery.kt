package Controller.SQL

import Controller.Connection.Connect

class SqlQuery {
    fun selectAllFromStudents() = Connect.getStatement()?.executeQuery("select * from Students")
    fun selectAllFromStudentsGroups() = Connect.getStatement()?.executeQuery("SELECT * FROM studentsgroups")
    fun selectAllFromExams() = Connect.getStatement()?.executeQuery("select * from exams")
    fun selectAllFromTeachers() = Connect.getStatement()?.executeQuery("select * from teachers")
    fun selectAllFromStudentExam() = Connect.getStatement()?.executeQuery("select * from student_exam")
}