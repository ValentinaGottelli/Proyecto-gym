package team.barcolli;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MyDietController implements Initializable {
    @FXML
    Button fav;
    @FXML
    Button profile;
    @FXML
    Label name;
    @FXML
    Label Composition;
    @FXML
    Button next;
    @FXML
    Button back;
    @FXML
    Label desac;
    @FXML
    Label desdm;
    @FXML
    ImageView image;



    public void profile(ActionEvent event) throws IOException {
        Parent abmview = FXMLLoader.load(getClass().getResource("profile.fxml"));
        Scene abmscene = new Scene(abmview);
        profile.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(abmscene);
                window.show();
            }
        });
    }

    public void fav(ActionEvent event) throws IOException {
        Parent abmview = FXMLLoader.load(getClass().getResource("favourites.fxml"));
        Scene abmscene = new Scene(abmview);
        fav.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(abmscene);
                window.show();
            }
        });
    }


    public static final String GETPLAN = "select planesalimenticios.nombre, planesalimenticios.descripciondm, planesalimenticios.descripcionac, planesalimenticios.composicionac, planesalimenticios.imagen, clientes.users_idusers from clientes join planesalimenticios on clientes.planesalimenticios_idplanes=planesalimenticios.idplan where clientes.users_idusers = ?";
    public Comidas obtenerComida() {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        try {
            PreparedStatement stmt = connectDb.prepareStatement(GETPLAN);
            stmt.setInt(1, App.userId);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return new Comidas(resultSet.getString(1), resultSet.getString(2), resultSet.getString(4), resultSet.getString(3), resultSet.getString(5));
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        return null;
    }


    public void updateComida() {
        Comidas comida= obtenerComida();

        name.setText(comida.getName());
        desac.setText(comida.getDesac());
        desdm.setText(comida.getDes());
        Composition.setText(comida.getComida());
        image.setImage(new Image(comida.image));
    }


    public static final String GETIDS = "select planesalimenticios_idplanes from clientes";
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        updateComida();
    }
}
