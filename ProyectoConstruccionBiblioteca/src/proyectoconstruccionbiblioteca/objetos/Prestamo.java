package proyectoconstruccionbiblioteca.objetos;

import java.sql.Date;

public class Prestamo {
    
    private int idPrestamo;
    private Date fechaPrestamo;
    private Date fechaVencimiento;
    private String estado;
    private int renovaRest;
    private int cargoMulta;
    private int idRecursoDocumental;
    private int idUsuario;
    
    private String titulo;
    private String autor;

    public Prestamo() {
    }

    public Prestamo(int idPrestamo, Date fechaPrestamo, Date fechaVencimiento, String estado, int renovaRest, int cargoMulta, int idRecursoDocumental, int idUsuario, String titulo, String autor) {
        this.idPrestamo = idPrestamo;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaVencimiento = fechaVencimiento;
        this.estado = estado;
        this.renovaRest = renovaRest;
        this.cargoMulta = cargoMulta;
        this.idRecursoDocumental = idRecursoDocumental;
        this.idUsuario = idUsuario;
        this.titulo = titulo;
        this.autor = autor;
    }

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getRenovaRest() {
        return renovaRest;
    }

    public void setRenovaRest(int renovaRest) {
        this.renovaRest = renovaRest;
    }

    public int getCargoMulta() {
        return cargoMulta;
    }

    public void setCargoMulta(int cargoMulta) {
        this.cargoMulta = cargoMulta;
    }

    public int getIdRecursoDocumental() {
        return idRecursoDocumental;
    }

    public void setIdRecursoDocumental(int idRecursoDocumental) {
        this.idRecursoDocumental = idRecursoDocumental;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
    
    @Override
    public String toString(){
        return titulo;
    }
}
