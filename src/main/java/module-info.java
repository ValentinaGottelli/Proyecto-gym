module team.barcolli {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive java.sql;
    requires transitive javafx.base;
    requires transitive javafx.graphics;

    opens team.barcolli to javafx.fxml;
    exports team.barcolli;
}