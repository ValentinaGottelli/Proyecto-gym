module team.barcolli {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens team.barcolli to javafx.fxml;
    exports team.barcolli;
}