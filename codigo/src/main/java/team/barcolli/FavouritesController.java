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

    public static final String SUSCRIBIRSE= "update clientes set planesalimenticios_idplanes = ? where idcliente = ? ";
    public void Suscribirse(ActionEvent event) {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();

        try {
            PreparedStatement userStmt = connectDb.prepareStatement(SUSCRIBIRSE);
            userStmt.setInt(1,ids.get(id));
            userStmt.setInt(2, App.userId);
            userStmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public static final String GETPLAN = "select nombre,descripciondm,descripcionac,composicionac,imagen from planesalimenticios where idplan = ?";
    public Comidas obtenerComida(int id) {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        try {
            PreparedStatement stmt = connectDb.prepareStatement(GETPLAN);
            stmt.setInt(1, id);
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

    public int id=0;

    public void NEXT(ActionEvent event){
        if(id + 1 >= ids.size()) {
            id = 0;
        } else {
            id++;
        }
        updateComida();
    }

    public void BACK(ActionEvent event){
        if(id - 1 < 0) {
            id = ids.get(ids.size() - 1);
        } else {
            id--;
        }
        updateComida();
    }

    public void updateComida() {
        Comidas comida= obtenerComida(ids.get(id));

        nombre.setText(comida.getName());
        descripcionAC.setText(comida.getDesac());
        descripcionDM.setText(comida.getDes());
        composition.setText(comida.getComida());
        image.setImage(new Image(comida.image));
    }

    ArrayList<Integer> ids = new ArrayList<>();

    public static final String GETIDS = "select idplan from planesalimenticios";
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        try {
            Statement stmt = connectDb.createStatement();
            ResultSet set = stmt.executeQuery(GETIDS);
            while (set.next()) {
                ids.add(set.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        updateComida();
    }
}
