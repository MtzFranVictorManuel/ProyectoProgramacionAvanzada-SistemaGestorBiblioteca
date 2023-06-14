package proyectoconstruccionbiblioteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import proyectoconstruccionbiblioteca.conexion.ConexionBD;
import proyectoconstruccionbiblioteca.objetos.Libro;

/**
 *
 * @author Martinez Franzoni Victor Manuel
 * 
 */
public class LibroDAO implements ILibroDAO{
    private Connection connection;
    Connection connect = null;
    PreparedStatement preStatement = null;
    
    private static final String INSERT_SQL_LIBRO = "insert into libro (edicion, isbn, fechaPublicacion, idioma, serie, volumen, tipoObraLiteraria, idRecursoDocumental) " +
            "values (?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_SELECT_LIBRO = "select * from libro where idRecursoDocumental = ?;";
    private static final String SQL_UPDATE_LIBRO = "update libro set edicion = ?, isbn = ?, fechaPublicacion = ?, idioma = ?, serie = ?, volumen = ?, tipoObraLiteraria = ? where idRecursoDocumental = ?;";
    
    public LibroDAO(){
        
    }
    
    @Override
    public boolean insertar (Libro libro, int fkDocumentoID){
        connect = ConexionBD.getConnection();
        boolean registroExitoso = false;
        try{
            preStatement = connect.prepareStatement(INSERT_SQL_LIBRO);
            preStatement.setString(1, libro.getEdicion());
            preStatement.setString(2, libro.getIsbn());
            preStatement.setDate(3, libro.getFechaPublicacion());
            preStatement.setString(4, libro.getIdioma());
            preStatement.setString(5, libro.getSerie());
            preStatement.setInt(6, libro.getVolumen());
            preStatement.setString(7, libro.getTipoObraLiteraria());
            preStatement.setInt(8, fkDocumentoID);
            preStatement.executeUpdate();
            registroExitoso = true;
        }catch(SQLException excepcion){
            Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, excepcion);
        }finally{
            ConexionBD.close(preStatement);
            if(this.connection == null){
                ConexionBD.close(connect);
            }
        }
        return registroExitoso;
    }
    
    @Override
    public Libro seleccionarLibro (int idRecursoDocumental){
        connect = ConexionBD.getConnection();
         Libro libro = null;
        if(connect != null){
            try{
                preStatement = connect.prepareStatement(SQL_SELECT_LIBRO);
                preStatement.setInt(1, idRecursoDocumental);
                ResultSet rSet = preStatement.executeQuery();
                if(rSet.next()){
                    libro = new Libro();
                    libro.setIdLibro(rSet.getInt("idLibro"));
                    libro.setEdicion(rSet.getString("edicion"));
                    libro.setIsbn(rSet.getString("isbn"));
                    libro.setFechaPublicacion(rSet.getDate("fechaPublicacion"));
                    libro.setIdioma(rSet.getString("idioma"));
                    libro.setSerie(rSet.getString("serie"));
                    libro.setVolumen(rSet.getInt("volumen"));
                    libro.setTipoObraLiteraria(rSet.getString("tipoObraLiteraria"));
                    return libro;
                }
            }catch(SQLException excepcion){
                Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, excepcion);
            }finally { 
                ConexionBD.close(preStatement);
                if (this.connection == null) {
                    ConexionBD.close(connect);
                }
            }
        }
        return libro;
    }
    
    @Override
    public boolean update(Libro libro, int idRecursoDocumental) {
        connect = ConexionBD.getConnection();
        boolean confirmar = false;
        if(connect != null){
            try{
                preStatement = connect.prepareStatement(SQL_UPDATE_LIBRO);
                preStatement.setString(1, libro.getEdicion());
                preStatement.setString(2, libro.getIsbn());
                preStatement.setDate(3, libro.getFechaPublicacion());
                preStatement.setString(4, libro.getIdioma());
                preStatement.setString(5, libro.getSerie());
                preStatement.setInt(6, libro.getVolumen());
                preStatement.setString(7, libro.getTipoObraLiteraria());
                preStatement.setInt(8, idRecursoDocumental);
                preStatement.executeUpdate();
                confirmar = true;
            }catch(SQLException excepcion){
                Logger.getLogger(LibroDAO.class.getName()).log(Level.SEVERE, null, excepcion);
            }finally { 
                ConexionBD.close(preStatement);
                if (this.connection == null) {
                    ConexionBD.close(connect);
                }
            }
        }
        return confirmar;
    }
}
