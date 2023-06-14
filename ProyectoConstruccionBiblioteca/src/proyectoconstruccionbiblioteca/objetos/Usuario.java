package proyectoconstruccionbiblioteca.objetos;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String telefono;
    private String email;
    private String estado;
    private int numAdeudos;
    
    private int montoTotal; 
    
    private String carrera;
    private String matricula;
    private String numPersonal;

    private int codigo;
    
    public Usuario() {
    }

    public Usuario(int idUsuario, String nombre, String apellidos, String direccion, String telefono, String email, String estado, int numAdeudos, int montoTotal, String carrera, String matricula, String numPersonal, int codigo) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.estado = estado;
        this.numAdeudos = numAdeudos;
        this.montoTotal = montoTotal;
        this.carrera = carrera;
        this.matricula = matricula;
        this.numPersonal = numPersonal;
        this.codigo = codigo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getNumAdeudos() {
        return numAdeudos;
    }

    public void setNumAdeudos(int numAdeudos) {
        this.numAdeudos = numAdeudos;
    }

    public int getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(int montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNumPersonal() {
        return numPersonal;
    }

    public void setNumPersonal(String numPersonal) {
        this.numPersonal = numPersonal;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

}
