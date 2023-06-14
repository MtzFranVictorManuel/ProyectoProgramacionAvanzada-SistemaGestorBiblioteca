/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoconstruccionbiblioteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import proyectoconstruccionbiblioteca.conexion.ConexionBD;
import proyectoconstruccionbiblioteca.objetos.RecursoDocumental;
import proyectoconstruccionbiblioteca.util.Constantes;

/**
 *
 * @author azul_
 */
public class RecursoDocumentalDAO implements IRecursoDocumentalDAO{
    private Connection connection;
    Connection connect = null;
    PreparedStatement preStatement = null;
    private static final String SQL_UPDATE = "UPDATE recursodocumental SET codigoBarras = ?, autor = ?,"
        + "titulo = ?, clasificacionLC = ?, descripcion = ?, editor = ?, tema = ?, tipoMaterial = ?, numCopias = ? WHERE idRecursoDocumental = ?;";
    private static final String SQL_DELETE = "DELETE recursodocumental WHERE titulo = ?;";
    private static final String SQL_INSERT = "insert into recursodocumental (codigoBarras, autor, titulo, clasificacionLC, "
            + "descripcion, editor, tema, tipoMaterial, numCopias) values (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_SELECT_BUSCARCOPIA = "select * from recursodocumental where codigoBarras = ? and titulo = ? and tipoMaterial = ?;";
    private static final String SQL_UPDATE_GUARDARCOPIA = "update recursodocumental set numCopias = numCopias + 1 where idRecursoDocumental = ?;";
    private static final String SQL_SELECT_IDRECURSODOCUMENTAL = "select idRecursoDocumental from recursoDocumental where titulo = ? and codigoBarras = ? and autor = ?;";
    private static final String SQL_SELECT_RECURSODOCUMENTAL = "select * from recursodocumental where idrecursodocumental = ?;";
    private static final String CONSULTA_RECURSO_DOCUMENTAL = "SELECT idRecursoDocumental, titulo, autor, codigoBarras, editor, tema, descripcion, "
                        + "clasificacionLC, tipoMaterial, numCopias FROM recursodocumental WHERE codigoBarras = ?";
    
    
    public RecursoDocumentalDAO() {
        
    }

    public RecursoDocumentalDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ObservableList<RecursoDocumental> select(ObservableList<RecursoDocumental> tableInfo, String SQLQuery) {
        connect = ConexionBD.getConnection();
        RecursoDocumental documental = null;
        if (connect != null) {
            try {
                preStatement = connect.prepareStatement(SQLQuery);
                ResultSet rSet = preStatement.executeQuery();
                while (rSet.next()) {
                    documental = new RecursoDocumental();
                    documental.setIdRecursoDocumental(rSet.getInt("idRecursoDocumental"));
                    documental.setCodigoBarras(rSet.getString("codigoBarras"));
                    documental.setAutor(rSet.getString("autor"));
                    documental.setTitulo(rSet.getString("titulo"));
                    documental.setClasificacionLC(rSet.getString("clasificacionLC"));
                    documental.setDescripcion(rSet.getString("descripcion"));
                    documental.setEditor(rSet.getString("editor"));
                    documental.setTema(rSet.getString("tema"));
                    documental.setTipoMaterial(rSet.getString("tipoMaterial"));
                    documental.setNumCopias(rSet.getInt("numCopias"));
                    tableInfo.add(documental);
                }
                ConexionBD.close(rSet);
                return tableInfo;
            } catch (SQLException excepcion) {
                Logger.getLogger(RecursoDocumentalDAO.class.getName()).log(Level.SEVERE, null, excepcion);
            } finally { 
                ConexionBD.close(preStatement);
                if (this.connection == null) {
                    ConexionBD.close(connect);
                }
            }
        }
        return tableInfo;
    }

    @Override
    public ObservableList<RecursoDocumental> select(ObservableList<RecursoDocumental> tableInfo, String value, String SQLQuery) {
        connect = ConexionBD.getConnection();
        RecursoDocumental documental = null;
        if (connect != null) {
            try {
                preStatement = connect.prepareStatement(SQLQuery);
                preStatement.setString(1, value);
                ResultSet rSet = preStatement.executeQuery();
                while (rSet.next()) {
                    documental = new RecursoDocumental();
                    documental.setIdRecursoDocumental(rSet.getInt("idRecursoDocumental"));
                    documental.setCodigoBarras(rSet.getString("codigoBarras"));
                    documental.setAutor(rSet.getString("autor"));
                    documental.setTitulo(rSet.getString("titulo"));
                    documental.setClasificacionLC(rSet.getString("clasificacionLC"));
                    documental.setDescripcion(rSet.getString("descripcion"));
                    documental.setEditor(rSet.getString("editor"));
                    documental.setTema(rSet.getString("tema"));
                    documental.setTipoMaterial(rSet.getString("tipoMaterial"));
                    documental.setNumCopias(rSet.getInt("numCopias"));
                    tableInfo.add(documental);
                }
                ConexionBD.close(rSet);
                return tableInfo;
            } catch (SQLException excepcion) {
                Logger.getLogger(RecursoDocumentalDAO.class.getName()).log(Level.SEVERE, null, excepcion);
            } finally { 
                ConexionBD.close(preStatement);
                if (this.connection == null) {
                    ConexionBD.close(connect);
                }
            }
        }
        return tableInfo;
    }

    @Override
    public boolean update(RecursoDocumental documental, int idRecursoDocumental) {
        connect = ConexionBD.getConnection();
        boolean confirmar = false;
        if(connect != null){
            try{
                preStatement = connect.prepareStatement(SQL_UPDATE);
                preStatement.setString(1, documental.getCodigoBarras());
                preStatement.setString(2, documental.getAutor());
                preStatement.setString(3, documental.getTitulo());
                preStatement.setString(4, documental.getClasificacionLC());
                preStatement.setString(5, documental.getDescripcion());
                preStatement.setString(6, documental.getEditor());
                preStatement.setString(7, documental.getTema());
                preStatement.setString(8, documental.getTipoMaterial());
                preStatement.setInt(9, documental.getNumCopias());
                preStatement.setInt(10, idRecursoDocumental);
                preStatement.executeUpdate();
                confirmar = true;
            }catch(SQLException excepcion){
               Logger.getLogger(RecursoDocumentalDAO.class.getName()).log(Level.SEVERE, null, excepcion);
            }finally { 
                ConexionBD.close(preStatement);
                if (this.connection == null) {
                    ConexionBD.close(connect);
                }
            }
        }
        return confirmar;
    }
    
    @Override
    public boolean updateCopia(int idRecursoDocumental){
        connect = ConexionBD.getConnection();
        boolean validar = false;
        if(connect != null){
            try{
                preStatement = connect.prepareStatement(SQL_UPDATE_GUARDARCOPIA );
                preStatement.setInt(1, idRecursoDocumental);
                preStatement.executeUpdate();
                validar = true;
            }catch(SQLException excepcion){
                Logger.getLogger(RecursoDocumentalDAO.class.getName()).log(Level.SEVERE, null, excepcion);
            }finally { 
                ConexionBD.close(preStatement);
                if (this.connection == null) {
                    ConexionBD.close(connect);
                }
            }
        }
        return false;   
    }

    @Override
    public boolean selectCopiaExistes(String codigoBarras, String titulo, String tipoMaterial){
        connect = ConexionBD.getConnection();
        RecursoDocumental documental = null;
        if(connect != null){
            try{
                preStatement = connect.prepareStatement(SQL_SELECT_BUSCARCOPIA);
                preStatement.setString(1, codigoBarras);
                preStatement.setString(2, titulo);
                preStatement.setString(3, tipoMaterial);
                ResultSet rSet = preStatement.executeQuery();
                if(rSet.next()){
                    documental = new RecursoDocumental();
                    documental.setIdRecursoDocumental(rSet.getInt("idRecursoDocumental"));
                    documental.setCodigoBarras(rSet.getString("codigoBarras"));
                    documental.setAutor(rSet.getString("autor"));
                    documental.setTitulo(rSet.getString("titulo"));
                    documental.setClasificacionLC(rSet.getString("clasificacionLC"));
                    documental.setDescripcion(rSet.getString("descripcion"));
                    documental.setEditor(rSet.getString("editor"));
                    documental.setTema(rSet.getString("tema"));
                    documental.setTipoMaterial(rSet.getString("tipoMaterial"));
                    documental.setNumCopias(rSet.getInt("numCopias"));
                    return true;
                }                
            }catch(SQLException excepcion){
                Logger.getLogger(RecursoDocumentalDAO.class.getName()).log(Level.SEVERE, null, excepcion);
            }finally { 
                ConexionBD.close(preStatement);
                if (this.connection == null) {
                    ConexionBD.close(connect);
                }
            }
        }
        return false;
    }    
    
    @Override
    public int insert(String codigoBarras, String autor, String titulo, String clasificacionLC, 
            String descripcion, String editor, String tema, String tipoMaterial, int numCopias){
        connect = ConexionBD.getConnection();
        int rows = 0;
        try{
            preStatement = connect.prepareStatement(SQL_INSERT);
            preStatement.setString(1, codigoBarras);
            preStatement.setString(2, autor);
            preStatement.setString(3, titulo);
            preStatement.setString(4, clasificacionLC);
            preStatement.setString(5, descripcion);
            preStatement.setString(6, editor);
            preStatement.setString(7, tema);
            preStatement.setString(8, tipoMaterial);
            preStatement.setInt(9, numCopias);
            rows = preStatement.executeUpdate(); 
        }catch(SQLException excepcion){
            Logger.getLogger(RecursoDocumentalDAO.class.getName()).log(Level.SEVERE, null, excepcion);
        }finally { 
            ConexionBD.close(preStatement);
            if (this.connection == null) {
                ConexionBD.close(connect);
            }
        }
        return rows;
    }
        
    @Override
    public boolean delete(RecursoDocumental documental) {
        return true;
    }
    
    @Override
    public int selectIdRecursoDocumental(String titulo, String codigoBarras, String autor){
        connect = ConexionBD.getConnection();
        if(connect != null){
            try{
                preStatement = connect.prepareStatement(SQL_SELECT_IDRECURSODOCUMENTAL);
                preStatement.setString(1, titulo);
                preStatement.setString(2, codigoBarras);
                preStatement.setString(3, autor);
                ResultSet rSet = preStatement.executeQuery();
                if(rSet.next()){
                    int idRecursoDocumental;
                    idRecursoDocumental = rSet.getInt("idRecursoDocumental");
                    return idRecursoDocumental;
                }
            }catch(SQLException excepcion){
                Logger.getLogger(RecursoDocumentalDAO.class.getName()).log(Level.SEVERE, null, excepcion);
            }finally { 
                ConexionBD.close(preStatement);
                if (this.connection == null) {
                    ConexionBD.close(connect);
                }
            }
        }
        return 0;
    }
    
    @Override
    public RecursoDocumental selectModificarRecursoDocumental(int idDocumental){
        connect = ConexionBD.getConnection();
        RecursoDocumental documental = null;
        if(connect != null){
            try{
                preStatement = connect.prepareStatement(SQL_SELECT_RECURSODOCUMENTAL);
                preStatement.setInt(1, idDocumental);
                ResultSet rSet = preStatement.executeQuery();
                if(rSet.next()){
                    documental = new RecursoDocumental();
                    documental.setIdRecursoDocumental(rSet.getInt("idRecursoDocumental"));
                    documental.setCodigoBarras(rSet.getString("codigoBarras"));
                    documental.setAutor(rSet.getString("autor"));
                    documental.setTitulo(rSet.getString("titulo"));
                    documental.setClasificacionLC(rSet.getString("clasificacionLC"));
                    documental.setDescripcion(rSet.getString("descripcion"));
                    documental.setEditor(rSet.getString("editor"));
                    documental.setTema(rSet.getString("tema"));
                    documental.setTipoMaterial(rSet.getString("tipoMaterial"));
                    documental.setNumCopias(rSet.getInt("numCopias"));
                    return documental;
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
        return documental;
    }
    
    public static RecursoDocumental obtenerRecursoDocumental(String codigoBarras){
        RecursoDocumental recurso = new RecursoDocumental();
        Connection conn = ConexionBD.getConnection();
        if(conn != null){
            try{
                PreparedStatement ps = conn.prepareStatement(CONSULTA_RECURSO_DOCUMENTAL);
                ps.setString(1, codigoBarras);
                ResultSet resultado = ps.executeQuery();
                
                if(resultado.next()){    
                    recurso.setCodigo(Constantes.CODIGO_RECURSO_EXISTE);
                    recurso.setIdRecursoDocumental(resultado.getInt("idRecursoDocumental"));
                    recurso.setTitulo(resultado.getString("titulo"));
                    recurso.setAutor(resultado.getString("autor"));
                    recurso.setCodigoBarras(resultado.getString("codigoBarras"));
                    recurso.setEditor(resultado.getString("editor"));
                    recurso.setTema(resultado.getString("tema"));
                    recurso.setDescripcion(resultado.getString("descripcion"));
                    recurso.setClasificacionLC(resultado.getString("clasificacionLC"));
                    recurso.setTipoMaterial(resultado.getString("tipoMaterial"));
                    recurso.setNumCopias(resultado.getInt("numCopias"));
                }else{
                    recurso.setCodigo(Constantes.CODIGO_RECURSO_NOEXISTE);
                }
                conn.close();
            }catch(SQLException ex){
                Logger.getLogger(RecursoDocumentalDAO.class.getName()).log(Level.SEVERE, null, ex);
                recurso.setCodigo(Constantes.CODIGO_ERROR_CONEXION_BD);
            }
        }else{
            recurso.setCodigo(Constantes.CODIGO_ERROR_CONEXION_BD);
        }
        return recurso;
    }
}
