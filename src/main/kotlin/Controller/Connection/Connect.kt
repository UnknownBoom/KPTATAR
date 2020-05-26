package Controller.Connection

import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement


object Connect : IConnection{
    private var connection: Connection? = null
    private var statement: Statement? = null

    override fun initConnect(db:String,login: String, password: String) {
        val db = "jdbc:mysql://127.0.0.1:3306/$db?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=GMT";
        connection = DriverManager.getConnection(db, login, password)
        connection!!.autoCommit = false

    }

    override fun getStatement(): Statement? {
            return statement?:connection?.createStatement()
    }

    override fun getConnection(): Connection? = connection

}