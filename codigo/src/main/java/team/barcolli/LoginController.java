package team.barcolli;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField UsernameField;
    @FXML
    private TextField PasswordField;
    @FXML
    private Button ok;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ok.setOnAction(new EventHandler<ActionEvent>() {
             public void handle(ActionEvent e) {
                 if (!UsernameField.getText().isBlank() && !PasswordField.getText().isBlank()) {
                     loginMessageLabel.setText("Iniciando sesion");
                     validateLogin();
                 } else {
                     loginMessageLabel.setText("Porfavor complete los campos");
                 }

            }
        });
    }

    public static final String getUser = "select * from users where username = ?";
    public void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();

        try {
            PreparedStatement stmt = connectDb.prepareStatement(getUser);
            stmt.setString(1, UsernameField.getText());
            ResultSet queryResult = stmt.executeQuery();

            if(queryResult.next() && queryResult.getString(2).equals(PasswordField.getText())) {
                loginMessageLabel.setText("VAMOOOOOOSS QUE FUNCIONA!!!");
            } else {
                loginMessageLabel.setText("Login invalido,volve a intentarlo");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }
}
