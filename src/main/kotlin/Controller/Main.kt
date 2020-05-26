package Controller

import Controller.Connection.Connect
import View.loginView
import javafx.stage.Stage
import javafx.stage.StageStyle
import tornadofx.*


class Main : App(loginView::class) {
    val LoginController: LoginController by inject()

    override fun start(stage: Stage) {
        stage.initStyle(StageStyle.UNDECORATED)
        super.start(stage)
        LoginController.init()
    }

    override fun stop() {
        try{
            try{
                Connect.getConnection()?.commit()
            }catch(e:Exception)
            {}
            Connect.getConnection()?.close()
        }finally {
            println("Stop")
            super.stop()
        }
    }
}
fun main(args: Array<String>) {
    launch<Main>(args)
}
