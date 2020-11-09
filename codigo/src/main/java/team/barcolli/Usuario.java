package team.barcolli;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Usuario {
    public String id;
    public String user;
    public String role;

    public Usuario() {
        this.id = "";
        this.user = "";
        this.role = "";
    }

    public Usuario(String id, String user, String role) {
        this.id = id;
        this.user = user;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getRole() {
        return role;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
