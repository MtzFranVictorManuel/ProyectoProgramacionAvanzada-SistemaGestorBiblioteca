
package modelo.POJO;

public class PersonalAcademico {
    private int idPersonalAcademico;
    private String numeroPersonal;
    private int idUsuario;
    private String contrasena;
    public String tipoPrivilegio;

    public PersonalAcademico() {
    }

    public PersonalAcademico(int idPersonalAcademico, String numeroPersonal, int idUsuario, String contrasena, String tipoPrivilegio) {
        this.idPersonalAcademico = idPersonalAcademico;
        this.numeroPersonal = numeroPersonal;
        this.idUsuario = idUsuario;
        this.contrasena=contrasena;
        this.tipoPrivilegio=tipoPrivilegio;
    }

    public int getIdPersonalAcademico() {
        return idPersonalAcademico;
    }

    public void setIdPersonalAcademico(int idPersonalAcademico) {
        this.idPersonalAcademico = idPersonalAcademico;
    }

    public String getNumeroPersonal() {
        return numeroPersonal;
    }

    public void setNumeroPersonal(String numeroPersonal) {
        this.numeroPersonal = numeroPersonal;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getContrasena (){
        return contrasena;
    }
    
    public void setContrasena (String contrasena){
        this.contrasena=contrasena;
    }
    
    public String getTipoPrivilegio(){
        return tipoPrivilegio;
    }
    
    public void setTipoPrivilegio(String tipoPrivilegio){
        this.tipoPrivilegio=tipoPrivilegio;
    }
    
    
}
