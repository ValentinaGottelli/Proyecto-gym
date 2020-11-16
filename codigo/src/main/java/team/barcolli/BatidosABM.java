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

public class BatidosABM implements Initializable{


    @FXML
    Button eliminar;
    @FXML
    Button userabm;
    @FXML
    TextField idComida;
    @FXML
    TableView<Comidas> table;
    @FXML
    TableColumn<Comidas, String> idtable;
    @FXML
    TableColumn<Comidas, String> nametable;
    @FXML
    TableColumn<Comidas, String> destable;
    @FXML
    TableColumn<Comidas, String> comidatable;
    @FXML
    Button registerUser;
    @FXML
    Button logout;

    private ObservableList<Comidas> list = FXCollections.observableArrayList();

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
    public void userabm(ActionEvent event) throws IOException {
        Parent abmview = FXMLLoader.load(getClass().getResource("abm.fxml"));
        Scene abmscene = new Scene(abmview);
        userabm.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(abmscene);
                window.show();
            }
        });
    }

    static private final String ELIMINATECOMIDA = "delete from planesalimenticios where idplan = ?";
    static private final String UPDATECLIENTES = "update clientes set planesalimenticios_idplanes = null where planesalimenticios_idplanes = ?";
    public void onDeletePlan(ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();

        if(!isNumeric(idComida.getText())) return;
        int idcomida = Integer.parseInt(idComida.getText());


        try {
            PreparedStatement clientStmt = connectDb.prepareStatement(UPDATECLIENTES);
            PreparedStatement comidaStmt= connectDb.prepareStatement(ELIMINATECOMIDA);
            clientStmt.setInt(1, idcomida);
            comidaStmt.setInt(1,idcomida);
            clientStmt.executeUpdate();
            comidaStmt.executeUpdate();
            if(clientStmt.executeUpdate() > 0) {
                list.removeIf(name -> Integer.parseInt(name.id) == idcomida);
            }
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }


    static private final String GETPLAN = "select idplan,nombre,descripciondm,composicionac from planesalimenticios";
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        try {
            Statement stmt = connectDb.createStatement();
            ResultSet set = stmt.executeQuery(GETPLAN);
            while(set.next()) {
                list.add(new Comidas(String.valueOf(set.getInt(1)), set.getString(2), set.getString(3),set.getString(4)));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }


        idtable.setCellValueFactory(new PropertyValueFactory<>("id"));
        nametable.setCellValueFactory(new PropertyValueFactory<>("name"));
        destable.setCellValueFactory(new PropertyValueFactory<>("des"));
        comidatable.setCellValueFactory(new PropertyValueFactory<>("comida"));

        table.setItems(list);
    }





}
