package proyectoconstruccionbiblioteca.dao;

import java.sql.Time;
import proyectoconstruccionbiblioteca.objetos.Multimedia;

/**
 *
 * @author Martinez franzoni victor manuel
 */
public interface IMultimediaDAO {
    public int insert(String tipoMultimedia, Time duracion, String formato, int fkRecursoDocumental);
    
    public int update (Multimedia multimedia, int idRecursoDocumental);
    
    public Multimedia seleccionarMultimedia (int idRecursoDocumental);
    
}
