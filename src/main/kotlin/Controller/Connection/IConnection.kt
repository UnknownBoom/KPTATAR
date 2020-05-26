package Controller.Connection


import java.sql.Connection;
import java.sql.Statement;

interface IConnection {
    fun initConnect(db:String,login:String, password:String)
    fun getStatement() : Statement?
    fun getConnection():Connection?
}