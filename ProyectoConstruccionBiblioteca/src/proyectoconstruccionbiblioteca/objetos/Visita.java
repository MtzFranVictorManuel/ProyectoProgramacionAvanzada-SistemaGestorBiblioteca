
package modelo.POJO;
import java.sql.Date;

public class Visita {
    private int idVisita;
    private Date FechaHoraEntrada;
    private int idEstudiante;

    public Visita() {
    }

    public Visita(int idVisita, Date FechaHoraEntrada, int idEstudiante) {
        this.idVisita = idVisita;
        this.FechaHoraEntrada = FechaHoraEntrada;
        this.idEstudiante = idEstudiante;
    }

    public int getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(int idVisita) {
        this.idVisita = idVisita;
    }

    public Date getFechaHoraEntrada() {
        return FechaHoraEntrada;
    }

    public void setFechaHoraEntrada(Date FechaHoraEntrada) {
        this.FechaHoraEntrada = FechaHoraEntrada;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }
    
    
}


