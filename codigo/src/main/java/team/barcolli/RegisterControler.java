package team.barcolli;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class RegisterControler implements Initializable {

    @FXML
    private Label userRegisterLabel;
    @FXML
    private PasswordField password;
    @FXML
    private TextField username;
    @FXML
    private TextField nombre;
    @FXML
    private TextField apellido;
    @FXML
    private TextField edad;
    @FXML
    private TextField sexo;
    @FXML
    private TextField disciplina;
    @FXML
    private TextField dni;
    @FXML
    private Button registerButton;



    public void registerButtonOnAction(ActionEvent event){
        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (!username.getText().isBlank() && !password.getText().isBlank() && !nombre.getText().isBlank()
                        && !apellido.getText().isBlank() && !edad.getText().isBlank() && !sexo.getText().isBlank()
                        && !disciplina.getText().isBlank() && !dni.getText().isBlank()) {
                    registerUser();
                } else {
                    userRegisterLabel.setText("Porfavor complete los campos");
                }

            }
        });
    }






    public void registerUser(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();

        String firstname=nombre.getText();
        String lastname=apellido.getText();
        String user=username.getText();
        String pass=password.getText();
        String sex=sexo.getText();
        String age=edad.getText();
        String disc=disciplina.getText();
        String dn=dni.getText();

        String insertFields= "INSERT INTO user_account(firstname,lastname,user,pass,sex,age,disc,dn) VALUES ('";
        String insertValues= firstname + "','" + lastname +"','" + user +"','" + pass +"','" + sex +"','" + age +"','" + disc +"','" + dn + "')" ;
        String insertToRegister= insertFields + insertValues;

        try{
            Statement statement = connectDb.createStatement();
            statement.executeUpdate(insertToRegister);
            userRegisterLabel.setText("Usuario registrado con exito");
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }




    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
