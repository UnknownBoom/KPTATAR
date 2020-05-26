package Controller

import Controller.Connection.Connect
import View.DbView
import View.loginView

import javafx.event.Event
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import javafx.stage.Stage
import tornadofx.*
import java.util.*


open class LoginController : Controller(){
    private var xOffset:Double? = null
    private var yOffset:Double? = null
    private val loginView: loginView by inject()
    private val dbView: DbView by inject()
    val DbController:DbController by inject()
     fun init(){
        loginView.CloseLogIn.setOnMousePressed {super.workspace.close()}
         loginView.LoginButton.setOnAction{e->loginHandler(e)}
         loginView.MainPane.setOnMousePressed {
                 xOffset = FX.primaryStage.x - it.screenX
                 yOffset = FX.primaryStage.y - it.screenY
         }
         loginView.MainPane.setOnMouseDragged {
             FX.primaryStage.x = it.screenX + xOffset!!
             FX.primaryStage.y = it.screenY + yOffset!!
         }
         loginView.MainPane.setOnKeyPressed { if(it.code == KeyCode.ENTER) loginHandler(it) }
     }

    private fun loginHandler(e: Event) {
        if(loginView.LoginField.text !="" && loginView.PasswordField.text!="" && loginView.DbNameField.text !=""){
            try{
                    loginView.ErrorLabel.textFill = Color.WHITESMOKE
                    loginView.ErrorLabel.text = "Connecting.."
                    Connect.initConnect(loginView.DbNameField.text.trim(), loginView.LoginField.text.trim(), loginView.PasswordField.text.trim())
                    showConfAlert(e)
            }catch (e:Exception){
                println("${e.message}\nError in LoginController loginHandler")
                loginView.ErrorLabel.textFill = Color.TOMATO
                loginView.ErrorLabel.text = "Error  Connect"
            }
        }
        else {
            loginView.ErrorLabel.textFill = Color.TOMATO
            loginView.ErrorLabel.text = "Fill all fields"
        }

    }
    private fun login(user: String, password: String, db: String):Boolean=
        try{
            Connect.initConnect(db.trim(),user.trim(),password.trim())
            true
        }catch (e:Exception){
            println("${e.message}\nError in LoginController login")
            false
        }

    private fun opendb(e: Event) {
        val node: Node = e.source as Node
        val stage:Stage = node.scene.window as Stage
        val scene: Scene = Scene(dbView.root)
        stage.scene = scene
        stage.show()
        DbController.init()
    }
    fun showConfAlert(e: Event){
        val node: Node = e.source as Node
        val stage:Stage = node.scene.window as Stage
        stage.close()
        val alert = Alert(Alert.AlertType.CONFIRMATION)
        alert.contentText = "Do you wont to create tables"
        val option:Optional<ButtonType> = alert.showAndWait();
        if (option.get() == null) {
            opendb(e)
        } else if (option.get() == ButtonType.OK) {
            DbController.InitDb()
            opendb(e)
        } else if (option.get() == ButtonType.CANCEL) {
            opendb(e)
        } else {
            opendb(e)
        }
    }

}


