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

public class profileController implements Initializable {

    @FXML
    Label name;
    @FXML
    Button logout;
    @FXML
    Button mydiet;
    @FXML
    Button fav;

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
    public void favourites(ActionEvent event) throws IOException {
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

    public static final String getUser = "select idusers from users";

    public String getUser(int id) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        try {
            PreparedStatement stmt = connectDb.prepareStatement(getUser);
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

        try {
            PreparedStatement stmt = connectDb.prepareStatement(getUser);
            ResultSet rs = stmt.executeQuery(getUser);

            while (rs.next()) {
                name.setText(rs.getString("username"));

            }


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();

        }
    }
}
