package team.barcolli;

class Profesional extends Usuario {

    public String nombre;
    public String apellido;
    public String especialidad;
    public int matricula;


    public Profesional(String nombre, String apellido, String especialidad, int matricula) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.matricula = matricula;
    }
}
