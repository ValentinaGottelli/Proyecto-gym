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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodTextRun;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class crearPlanController implements Initializable{

    @FXML
    private TextField nombrePlan;
    @FXML
    private TextField DM;
    @FXML
    private TextField DescAC;
    @FXML
    private TextField compAC;
    @FXML
    private TextField Imagen;
    @FXML
    private Label labelPlan;
    @FXML
    private Button registerButton;
    @FXML
    private Button logout;

    public void registerButtonOnAction(ActionEvent event){
        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (!nombrePlan.getText().isBlank() && !DM.getText().isBlank() && !DescAC.getText().isBlank()
                        && !compAC.getText().isBlank() && !Imagen.getText().isBlank()) {
                    registerPlan();
                } else if(!nombrePlan.getText().isBlank() || !DM.getText().isBlank() || !DescAC.getText().isBlank()
                        && !compAC.getText().isBlank() || !Imagen.getText().isBlank()) {
                    labelPlan.setText("Porfavor complete los campos");
                }
                else{
                    labelPlan.setText("Porfavor complete los campos");
                }

            }
        });
    }

    public static final String getPlan = "select idplan from planesalimenticios where nombre = ?";
    public boolean PlanExists(String nombre) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();

        try {
            PreparedStatement stmt = connectDb.prepareStatement(getPlan);
            stmt.setString(1, nombre);
            return stmt.execute();
        } catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
        return false;
    }

    public int getPlanId(String nombre) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        try {
            PreparedStatement stmt = connectDb.prepareStatement(getPlan);
            stmt.setString(1, nombre);
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

    private static final String INSERTPLAN = "insert into planesalimenticios values (null, ?, ?, ?, ?,?)";
    public void registerPlan(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();

        String nombre=nombrePlan.getText();
        String descripciondm=DM.getText();
        String descripcionac=DescAC.getText();
        String composicionac=compAC.getText();
        String imagen= Imagen.getText();

        if(getPlanId(nombre) != -1) {
            labelPlan.setText("Ese plan ya existe");
            return;
        }

        try{
            PreparedStatement planStmt = connectDb.prepareStatement(INSERTPLAN);
            planStmt.setString(1, nombre);
            planStmt.setString(2, descripciondm);
            planStmt.setString(3,descripcionac);
            planStmt.setString(4,composicionac);
            planStmt.setString(5,imagen);
            planStmt.executeUpdate();
            labelPlan.setText("Plan registrado con exito");
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }




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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
