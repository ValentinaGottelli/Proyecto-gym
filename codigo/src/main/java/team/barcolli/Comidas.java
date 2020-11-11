package team.barcolli;

public class Comidas {

    public String id;
    public String name;
    public String des;
    public String comida;

    public Comidas(String id, String name, String des, String comida) {
        this.id = id;
        this.name = name;
        this.des = des;
        this.comida = comida;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getComida() {
        return comida;
    }

    public void setComida(String comida) {
        this.comida = comida;
    }
}
