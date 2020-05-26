package View

import javafx.scene.Parent
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import tornadofx.*



open class loginView: View()  {

    override val root: Parent by fxml("/FXML/LogIn.fxml")

    val CloseLogIn:ImageView by fxid()
    
     val MainPane: AnchorPane  by fxid()

    val DbNameField: TextField by fxid()

     val LoginLabel: Label  by fxid()

     val LoginField: TextField  by fxid()
    
     val PasswordField: PasswordField  by fxid()

     val LoginButton: Button  by fxid()

     val ErrorLabel: Label  by fxid()
    init{
        LoginField.text = "root"
        PasswordField.text = "root"
        DbNameField.text = "mydb"

    }

}
