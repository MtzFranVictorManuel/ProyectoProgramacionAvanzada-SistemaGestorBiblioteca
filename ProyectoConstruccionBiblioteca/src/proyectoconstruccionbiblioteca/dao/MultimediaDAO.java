package proyectoconstruccionbiblioteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import proyectoconstruccionbiblioteca.conexion.ConexionBD;
import proyectoconstruccionbiblioteca.objetos.Multimedia;

/**
 *
 * @author Martinez Franzoni Victor Manuel
 */
public class MultimediaDAO implements IMultimediaDAO{
        private Connection connection;
    Connection connect = null;
    PreparedStatement preStatement = null;
    
    private static final String INSERT_SQL_RECURSO = "insert into multimedia "
            + "(tipoMultimedia, duracion, formato, idRecursoDocumental) values (?, ?, ?, ?);";
    private static final String UPDATE_SQL = "update multimedia set tipoMultimedia = ?, duracion = ?, formato = ? where idRecursoDocumental = ?;";
    private static final String SQL_SELECT_MULTIMEDIA = "select * from multimedia where idRecursoDocumental = ?;";

    public MultimediaDAO() {
    }
    
    
        @Override
    public int insert(String tipoMultimedia, Time duracion, String formato, int fkRecursoDocumental){
        connect = ConexionBD.getConnection();
        int rows = 0;
        try{
            preStatement = connect.prepareStatement(INSERT_SQL_RECURSO);
            preStatement.setString(1, tipoMultimedia);
            preStatement.setTime(2, duracion);
            preStatement.setString(3, formato);
            preStatement.setInt(4, fkRecursoDocumental);
            rows = preStatement.executeUpdate(); 
        }catch(SQLException excepcionSQL){
            System.out.println("error" + excepcionSQL);
        }finally{
            ConexionBD.close(preStatement);
            if(this.connection == null){
                ConexionBD.close(connect);
            }
        }
        return rows;
    }
    
        @Override
    public int update (Multimedia multimedia, int idRecursoDocumental){
        connect = ConexionBD.getConnection();
        int rows = 0;
        if(connect != null){
            try{
                preStatement = connect.prepareStatement(UPDATE_SQL);
                preStatement.setString(1, multimedia.getTipoMultimedia());
                preStatement.setTime(2, multimedia.getDuracion());
                preStatement.setString(3, multimedia.getFormato());
                preStatement.setInt(4, idRecursoDocumental);
                rows = preStatement.executeUpdate();
            }catch(SQLException excepcionSQL){
                System.out.println("error" + excepcionSQL);
            }finally{
                ConexionBD.close(preStatement);
                if(this.connection == null){
                    ConexionBD.close(connect);
                }
            }
        }
        return rows;
    }
    
        @Override
    public Multimedia seleccionarMultimedia (int idRecursoDocumental){
        connect = ConexionBD.getConnection();
        Multimedia multimedia = null;
        if(connect != null){
            try{
                preStatement = connect.prepareStatement(SQL_SELECT_MULTIMEDIA);
                preStatement.setInt(1, idRecursoDocumental);
                ResultSet rSet = preStatement.executeQuery();
                if(rSet.next()){
                    multimedia = new Multimedia();
                    multimedia.setTipoMultimedia(rSet.getString("tipoMultimedia"));
                    multimedia.setDuracion(rSet.getTime("duracion"));
                    multimedia.setFormato(rSet.getString("formato"));          
                    return multimedia;
                }
            }catch(SQLException excepcion){
                System.out.println(excepcion.getMessage());
            }finally { 
                ConexionBD.close(preStatement);
                if (this.connection == null) {
                    ConexionBD.close(connect);
                }
            }
        }
        return multimedia;
    }
}
