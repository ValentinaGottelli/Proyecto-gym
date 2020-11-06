package team.barcolli;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

public class BatidosABM {

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
        private Button registerButton;
        @FXML
        private Label labelPlan;


        public void registerButtonOnAction(ActionEvent event){
            registerButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    if (!nombrePlan.getText().isBlank() && !DM.getText().isBlank() && !DescAC.getText().isBlank()
                            && !compAC.getText().isBlank() && !Imagen.getText().isBlank()) {
                        registerPlan();
                    } else {
                        labelPlan.setText("Porfavor complete los campos");
                    }

                }
            });
        }

        public void registerPlan(){
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDb = connectNow.getConnection();

            String nombPl=nombrePlan.getText();
            String dm=DM.getText();
            String dscAc=DescAC.getText();
            String compac=compAC.getText();
            String imagen=Imagen.getText();

            String insertFields= "INSERT INTO plan_alimenticio(nombPL,dm,dscAc,compac,imagen) VALUES ('";
            String insertValues= nombPl + "','" + dm +"','" + dscAc +"','" + compac +"','" + imagen + "')" ;
            String insertToRegister= insertFields + insertValues;

            try{
                Statement statement = connectDb.createStatement();
                statement.executeUpdate(insertToRegister);
                labelPlan.setText("Usuario registrado con exito");
            }catch(Exception e){
                e.printStackTrace();
                e.getCause();
            }




        }
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

        }
    }

}
