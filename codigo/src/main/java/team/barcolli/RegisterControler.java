package team.barcolli;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
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
    @FXML
    private Button usersABM;
    @FXML
    private Button logout;
    @FXML
    Button usr;
    @FXML
    Button plan;


    public void registerButtonOnAction(ActionEvent event){
        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (!username.getText().isBlank() && !password.getText().isBlank() && !nombre.getText().isBlank()
                        && !apellido.getText().isBlank() && !edad.getText().isBlank() && !sexo.getText().isBlank()
                        && !disciplina.getText().isBlank() && !dni.getText().isBlank()) {
                    registerUser();
                } else if(!username.getText().isBlank() || !password.getText().isBlank() || !nombre.getText().isBlank()
                        && !apellido.getText().isBlank() || !edad.getText().isBlank() || !sexo.getText().isBlank()
                        && !disciplina.getText().isBlank() || !dni.getText().isBlank()) {
                    userRegisterLabel.setText("Porfavor complete los campos");
                }
                else{
                    userRegisterLabel.setText("Porfavor complete los campos");
                }

            }
        });
    }
    public void LogOut(ActionEvent event) throws IOException {
        Parent abmview = FXMLLoader.load(getClass().getResource("loginLindo.fxml"));
        Scene abmscene = new Scene(abmview);
        logout.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(abmscene);
                window.show();
            }
        });
    }

    public void ABMPLANES(ActionEvent event) throws IOException {
        Parent abmview = FXMLLoader.load(getClass().getResource("batidosABM.fxml"));
        Scene abmscene = new Scene(abmview);
        plan.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(abmscene);
                window.show();
            }
        });
    }

    public void abmusers(ActionEvent event) throws IOException {
        Parent abmview = FXMLLoader.load(getClass().getResource("abm.fxml"));
        Scene abmscene = new Scene(abmview);
        usr.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(abmscene);
                window.show();
            }
        });
    }

    public void changeScene(ActionEvent event) throws IOException {
        Parent abmview = FXMLLoader.load(getClass().getResource("abm.fxml"));
        Scene abmscene= new Scene(abmview);
        usersABM.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

                window.setScene(abmscene);
                window.show();
            }
        });



    }

    public static final String getUser = "select idusers from users where username = ?";
    public boolean userExists(String username) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();

        try {
            PreparedStatement stmt = connectDb.prepareStatement(getUser);
            stmt.setString(1, username);
            return stmt.execute();
        } catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
        return false;
    }

    public int getUserId(String username) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        try {
            PreparedStatement stmt = connectDb.prepareStatement(getUser);
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return -1;
    }

    private static final String INSERTUSER = "insert into users values (null, ?, ?, 0)";
    private static final String INSERTCLIENTE = "insert into clientes values (null, ?, ?, ?, ?, ?, ?, ?, null)";
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

        if(getUserId(user) != -1) {
            userRegisterLabel.setText("Ese usuario ya existe");
            return;
        }

        try{
            PreparedStatement userStmt = connectDb.prepareStatement(INSERTUSER);
            userStmt.setString(1, user);
            userStmt.setString(2, pass);
            userStmt.executeUpdate();
            PreparedStatement clientStmt = connectDb.prepareStatement(INSERTCLIENTE);
            clientStmt.setString(1,firstname);
            clientStmt.setString(2,lastname);
            clientStmt.setString(3,age);
            clientStmt.setString(4,sex);
            clientStmt.setString(5,disc);
            clientStmt.setString(6,dn);
            clientStmt.setInt(7, getUserId(user));
            clientStmt.executeUpdate();
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
