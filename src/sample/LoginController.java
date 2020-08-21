package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;


import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField UsernameField;
    @FXML
    private TextField PasswordField;


    public void loginButtonAction(ActionEvent event){

        if (UsernameField.getText().isBlank()==false && PasswordField.getText().isBlank()==false) {
            validateLogin();
            loginMessageLabel.setText("Iniciando sesion");
        }
        else{
            loginMessageLabel.setText("Porfavor complete los campos");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void validateLogin(){
        DatabaseConnection connectNow= new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();

        String verifyLogin = "" + UsernameField.getText() + "" + PasswordField.getText() +"'";

        try{

            Statement statement= connectDb.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()){
                if(queryResult.getInt(1)==1){
                    loginMessageLabel.setText("VAMOOOOOOSS QUE FUNCIONA!!!");
                } else {
                    loginMessageLabel.setText("Login invalido,volve a intentarlo");
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }
}
