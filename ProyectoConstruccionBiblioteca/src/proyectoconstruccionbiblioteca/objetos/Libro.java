package proyectoconstruccionbiblioteca.objetos;

import java.sql.Date;

/**
 *
 * @author Martinez Franzoni Victor Manuel
 */
public class Libro {
        private int idLibro;
    private String edicion;
    private String isbn;
    private Date fechaPublicacion;
    private String idioma;
    private String serie;
    private int volumen;
    private String tipoObraLiteraria;
    private int fkIdRecursoDocumental;

    public Libro() {
    }
    
    public Libro(String edicion, String isbn, Date fechaPublicacion, String idioma, String serie, int volumen, String tipoObraLiteraria) {
        this.edicion = edicion;
        this.isbn = isbn;
        this.fechaPublicacion = fechaPublicacion;
        this.idioma = idioma;
        this.serie = serie;
        this.volumen = volumen;
        this.tipoObraLiteraria = tipoObraLiteraria;
    }

    public Libro(int idLibro, String edicion, String isbn, Date fechaPublicacion, String idioma, String serie, int volumen, String tipoObraLiteraria) {
        this.idLibro = idLibro;
        this.edicion = edicion;
        this.isbn = isbn;
        this.fechaPublicacion = fechaPublicacion;
        this.idioma = idioma;
        this.serie = serie;
        this.volumen = volumen;
        this.tipoObraLiteraria = tipoObraLiteraria;
    }

    public Libro(int idLibro, String edicion, String isbn, Date fechaPublicacion, String idioma, String serie, int volumen, String tipoObraLiteraria, int fkIdRecursoDocumental) {
        this.idLibro = idLibro;
        this.edicion = edicion;
        this.isbn = isbn;
        this.fechaPublicacion = fechaPublicacion;
        this.idioma = idioma;
        this.serie = serie;
        this.volumen = volumen;
        this.tipoObraLiteraria = tipoObraLiteraria;
        this.fkIdRecursoDocumental = fkIdRecursoDocumental;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public String getEdicion() {
        return edicion;
    }

    public String getIsbn() {
        return isbn;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public String getIdioma() {
        return idioma;
    }

    public String getSerie() {
        return serie;
    }

    public int getVolumen() {
        return volumen;
    }

    public String getTipoObraLiteraria() {
        return tipoObraLiteraria;
    }

    public int getFkIdRecursoDocumental() {
        return fkIdRecursoDocumental;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public void setVolumen(int volumen) {
        this.volumen = volumen;
    }

    public void setTipoObraLiteraria(String tipoObraLiteraria) {
        this.tipoObraLiteraria = tipoObraLiteraria;
    }

    public void setFkIdRecursoDocumental(int fkIdRecursoDocumental) {
        this.fkIdRecursoDocumental = fkIdRecursoDocumental;
    }
}
