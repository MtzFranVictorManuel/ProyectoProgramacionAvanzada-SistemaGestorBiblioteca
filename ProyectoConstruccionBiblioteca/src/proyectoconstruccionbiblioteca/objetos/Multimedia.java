/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoconstruccionbiblioteca.objetos;

import java.sql.Time;

/**
 *
 * @author Martinez Franzoni Victor Manuel
 */
public class Multimedia {
    private static int idMultimedia;
    private String tipoMultimedia;
    private Time duracion;
    private String formato;
    private int fkIdRecursoDocumental;

    public Multimedia() {
    }
    
    public Multimedia(String tipoMultimedia, Time duracion, String formato) {
        this.tipoMultimedia = tipoMultimedia;
        this.duracion = duracion;
        this.formato = formato;
    }

    public Multimedia(int idMultimedia, String tipoMultimedia, Time duracion, String formato) {
        this.idMultimedia = idMultimedia;
        this.tipoMultimedia = tipoMultimedia;
        this.duracion = duracion;
        this.formato = formato;
    }

    public Multimedia(String tipoMultimedia, Time duracion, String formato, int fkIdRecursoDocumental) {
        this.tipoMultimedia = tipoMultimedia;
        this.duracion = duracion;
        this.formato = formato;
        this.fkIdRecursoDocumental = fkIdRecursoDocumental;
    }

    public int getIdMultimedia() {
        return idMultimedia;
    }

    public String getTipoMultimedia() {
        return tipoMultimedia;
    }

    public Time getDuracion() {
        return duracion;
    }

    public String getFormato() {
        return formato;
    }

    public int getFkIdRecursoDocumental() {
        return fkIdRecursoDocumental;
    }

    public void setIdMultimedia(int idMultimedia) {
        this.idMultimedia = idMultimedia;
    }

    public void setTipoMultimedia(String tipoMultimedia) {
        this.tipoMultimedia = tipoMultimedia;
    }

    public void setDuracion(Time duracion) {
        this.duracion = duracion;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public void setFkIdRecursoDocumental(int fkIdRecursoDocumental) {
        this.fkIdRecursoDocumental = fkIdRecursoDocumental;
    }
}
