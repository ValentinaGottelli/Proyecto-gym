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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class FavouritesController implements Initializable {

    @FXML
    Button mydiet;
    @FXML
    Button profile;
    @FXML
    Label composition;
    @FXML
    Label nombre;
    @FXML
    Label descripcionAC;
    @FXML
    Label descripcionDM;
    @FXML
    Button next;
    @FXML
    Button back;
    @FXML
    Button suscribe;

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

    public void Mydiet(ActionEvent event) throws IOException {
        Parent abmview = FXMLLoader.load(getClass().getResource("MyDiet.fxml"));
        Scene abmscene = new Scene(abmview);
        mydiet.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(abmscene);
                window.show();
            }
        });
    }

    public static final String GETNOMBREPLAN = "select nombre from planesalimenticios where idplan = ?";

    public String getNombrePLan(int id) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        try {
            PreparedStatement stmt = connectDb.prepareStatement(GETNOMBREPLAN);
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return "";
    }

    public static final String GETDESCRIPCION = "select descripcionac from planesalimenticios where idplan = ?";

    public String getDescripcion(int id) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        try {
            PreparedStatement stmt = connectDb.prepareStatement(GETDESCRIPCION);
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return "";
    }

    public static final String GETCOMPOSICION = "select composicionac from planesalimenticios where idplan = ?";

    public String getComposicion(int id) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        try {
            PreparedStatement stmt = connectDb.prepareStatement(GETCOMPOSICION);
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return "";
    }

    public static final String GETDESCRIPCIONDM = "select descripciondm from planesalimenticios where idplan = ?";

    public String getDescripcionDM(int id) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        try {
            PreparedStatement stmt = connectDb.prepareStatement(GETDESCRIPCIONDM);
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return "";
    }

    public void Suscribirse(ActionEvent event) throws IOException {
        Parent abmview = FXMLLoader.load(getClass().getResource("formularioPlan.fxml"));
        Scene abmscene = new Scene(abmview);
        suscribe.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(abmscene);
                window.show();
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        nombre.setText(getNombrePLan(1));
        descripcionAC.setText(getDescripcion(1));
        descripcionDM.setText(getDescripcionDM(1));
        composition.setText(getComposicion(1));

    }
}
