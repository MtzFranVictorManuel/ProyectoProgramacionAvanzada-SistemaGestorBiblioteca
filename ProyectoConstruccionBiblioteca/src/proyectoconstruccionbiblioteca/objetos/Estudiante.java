package modelo.POJO;


public class Estudiante {
    private int idEstudiante ;
    private String matricula;
    private String carrera;
   private int idUsuario;

    public Estudiante() {
    }

   
   
    public Estudiante(int idEstudiante, String matricula, String carrera, int idUsuario) {
        this.idEstudiante = idEstudiante;
        this.matricula = matricula;
        this.carrera = carrera;
        this.idUsuario = idUsuario;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
   
   
    
}
