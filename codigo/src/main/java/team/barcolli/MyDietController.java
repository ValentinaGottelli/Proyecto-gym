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

public class MyDietController implements Initializable {
    @FXML
    Button fav;
    @FXML
    Button profile;
    @FXML
    Label composition;
    @FXML
    Label name;
    @FXML
    Label Composition;
    @FXML
    Button next;
    @FXML
    Button back;
    @FXML
    Label currentMeal;



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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        name.setText(getNombrePLan(1));
        currentMeal.setText(getDescripcion(1));
        Composition.setText(getComposicion(1));

    }
}
