package team.barcolli;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class abmUsuariosAdmin implements Initializable{

    @FXML
    Button registerUser;
    @FXML
    Button logout;
    @FXML
    TableView<Usuario> table;
    @FXML
    TableColumn<Usuario, String> idtable;
    @FXML
    TableColumn<Usuario, String> nametable;
    @FXML
    TableColumn<Usuario, String> roletable;
    @FXML
    TextField idUser;
    @FXML
    CheckBox professionalToggle;
    @FXML
    Button abmplanes;
    @FXML
    Label total;

    private ObservableList<Usuario> list = FXCollections.observableArrayList();

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void changeScene(ActionEvent event) throws IOException {
        Parent abmview = FXMLLoader.load(getClass().getResource("register.fxml"));
        Scene abmscene = new Scene(abmview);
        registerUser.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(abmscene);
                window.show();
            }
        });
    }

    public void abmplanes(ActionEvent event) throws IOException {
        Parent abmview = FXMLLoader.load(getClass().getResource("batidosABM.fxml"));
        Scene abmscene = new Scene(abmview);
        abmplanes.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(abmscene);
                window.show();
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

    static private final String ELIMINATECLIENTE = "delete from clientes where users_idusers = ?";
    static private final String ELIMINATEUSER = "delete from users where idusers = ?";
    public void onDeleteUser(ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();

        if(!isNumeric(idUser.getText())) return;
        int iduser = Integer.parseInt(idUser.getText());
        if(iduser == 1) return;

        try {
            PreparedStatement clientStmt = connectDb.prepareStatement(ELIMINATECLIENTE);
            clientStmt.setInt(1, iduser);
            clientStmt.executeUpdate();
            PreparedStatement userStmt = connectDb.prepareStatement(ELIMINATEUSER);
            userStmt.setInt(1, iduser);
            if(userStmt.executeUpdate() > 0) {
                list.removeIf(user -> Integer.parseInt(user.id) == iduser);
            }
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    static private final String ISPROFESIONAL = "select profesional from users where idusers = ?";
    public void onIdKeyInput(ActionEvent event) {
        if(isNumeric(idUser.getText())) {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDb = connectNow.getConnection();

            int iduser = Integer.parseInt(idUser.getText());
            try {
                PreparedStatement userStmt = connectDb.prepareStatement(UPDATEUSER);
                userStmt.setInt(1, iduser);
                ResultSet set = userStmt.executeQuery();
                if(set.next()) {
                    professionalToggle.setSelected(set.getBoolean(1));
                }
            }catch(Exception e){
                e.printStackTrace();
                e.getCause();
            }
        }
    }

    static private final String UPDATEUSER = "update users set profesional = ? where idusers = ?";
    public void hacerProfesional(ActionEvent event){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();

        if(!isNumeric(idUser.getText())) return;
        int iduser = Integer.parseInt(idUser.getText());

        try {
            PreparedStatement userStmt = connectDb.prepareStatement(UPDATEUSER);
            userStmt.setBoolean(1, professionalToggle.isSelected());
            userStmt.setInt(2, iduser);
            if(userStmt.executeUpdate() > 0) {
                list.stream().filter(user -> Integer.parseInt(user.id) == iduser).findAny().orElse(null).role = professionalToggle.isSelected() ? "Profesional" : "Cliente";
            }
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    static private final String GETCANTUSERS = "select count(*) from users";
    static private final String GETUSERS = "select idusers,username,profesional from users";
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        try {
            Statement stmt = connectDb.createStatement();
            ResultSet set = stmt.executeQuery(GETUSERS);
            Statement cant = connectDb.createStatement();
            ResultSet cantu = cant.executeQuery(GETCANTUSERS);
            if (cantu.next())
                total.setText(String.valueOf(cantu.getInt(1)));
            while(set.next()) {
                String role = set.getBoolean(3) ? "Profesional" : "Cliente";
                if(set.getInt(1) == 1) role = "Admin";
                list.add(new Usuario(String.valueOf(set.getInt(1)), set.getString(2), role));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }


        idtable.setCellValueFactory(new PropertyValueFactory<>("id"));
        nametable.setCellValueFactory(new PropertyValueFactory<>("user"));
        roletable.setCellValueFactory(new PropertyValueFactory<>("role"));

        table.setItems(list);
    }


}
