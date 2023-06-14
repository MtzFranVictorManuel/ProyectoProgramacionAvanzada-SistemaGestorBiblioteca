/*
Nombre de Archivo: PrestamoDAO.java
Descripción: Objeto de acceso a datos para la clase Prestamo.java
Autor: Eduardo Antonio Castillo Garrido
Fecha de moficiación: 15/12/2021
*/

package proyectoconstruccionbiblioteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import proyectoconstruccionbiblioteca.conexion.ConexionBD;
import proyectoconstruccionbiblioteca.objetos.Prestamo;

public class PrestamoDAO {
    
    private static final String CONSULTA_PRESTAMOS_USUARIO = "SELECT idPrestamo, fechaPrestamo, fechaVencimiento, estado, renovaRest, cargoMulta, prestamo.idRecursoDocumental, idUsuario, " +
                        "recursodocumental.titulo AS titulo, recursodocumental.autor AS autor " +
                        "FROM prestamo " +
                        "INNER JOIN recursodocumental " +
                        "ON prestamo.idRecursoDocumental = recursodocumental.idRecursoDocumental " +
                        "WHERE idUsuario = ? AND estado = 'En Curso' OR estado = 'Atrasado'";
    
    private static final String CONSULTA_PRESTAMO_USUARIO = "SELECT idPrestamo, fechaPrestamo, fechaVencimiento, estado, renovaRest, cargoMulta, prestamo.idRecursoDocumental, idUsuario, " +
                        "recursodocumental.titulo AS titulo, recursodocumental.autor AS autor, recursodocumental.codigoBarras AS codigoBarras " +
                        "FROM prestamo " +
                        "INNER JOIN recursodocumental " +
                        "ON prestamo.idRecursoDocumental = recursodocumental.idRecursoDocumental " +
                        "WHERE idUsuario = ? AND recursodocumental.codigoBarras = ? AND estado = 'En Curso' OR recursodocumental.codigoBarras = ? AND estado = 'Atrasado'";
    
    private static final String SENTENCIA_INSERTAR_PRESTAMO = "INSERT INTO prestamo(fechaPrestamo, fechaVencimiento, estado, renovaRest, cargoMulta, idRecursoDocumental, idUsuario) VALUES(?,?,?,?,?,?,?)";
    
    private static final String SENTENCIA_ACTUALIZAR_PRESTAMO_DEVOLVER = "UPDATE prestamo SET estado = 'Devuelto' WHERE idPrestamo = ?";
    
    private static final String SENTENCIA_ACTUALIZAR_PRESTAMO_RENOVAR = "UPDATE prestamo SET estado = 'En Curso', fechaVencimiento = ? WHERE idPrestamo = ?";
    
    //Metodo que obtiene todos los prestamos con estado "En Curso" o "Atrasado" de un usuario
    public static ArrayList<Prestamo> obtenerInfoPrestamos(int idUsuario){
        ArrayList<Prestamo> prestamosBD = null;
        Connection conn = ConexionBD.getConnection();
        if(conn != null){
            try{
                PreparedStatement prepararConsulta = conn.prepareStatement(CONSULTA_PRESTAMOS_USUARIO);
                prepararConsulta.setInt(1, idUsuario);
                ResultSet resultadoConsulta = prepararConsulta.executeQuery();
                prestamosBD = new ArrayList<>();
                if(resultadoConsulta.next()){
                    do{
                        Prestamo prestamo = new Prestamo();
                        prestamo.setIdPrestamo(resultadoConsulta.getInt("idPrestamo"));
                        prestamo.setFechaPrestamo(resultadoConsulta.getDate("fechaPrestamo"));
                        prestamo.setFechaVencimiento(resultadoConsulta.getDate("fechaVencimiento"));
                        prestamo.setEstado(resultadoConsulta.getString("estado"));
                        prestamo.setRenovaRest(resultadoConsulta.getInt("renovaRest"));
                        prestamo.setCargoMulta(resultadoConsulta.getInt("cargoMulta"));
                        prestamo.setIdRecursoDocumental(resultadoConsulta.getInt("idRecursoDocumental"));
                        prestamo.setIdUsuario(resultadoConsulta.getInt("idUsuario"));
                        prestamo.setTitulo(resultadoConsulta.getString("titulo"));
                        prestamo.setAutor(resultadoConsulta.getString("autor"));

                        prestamosBD.add(prestamo);
                    }while(resultadoConsulta.next());
                }else{
                    System.out.println("Resultado consulta vacio");
                }
                conn.close();
            }catch(SQLException e){
                e.printStackTrace();
                mostrarAlerta("Error de conexión", "Se perdió la conexión con la base de datos", Alert.AlertType.ERROR);
            }
        }
        return prestamosBD;    
    }
    
    
    public static boolean insertarPrestamo(ArrayList<Prestamo> prestamosAGuardar){
        boolean respuesta = false;
        Connection conn = ConexionBD.getConnection();
        if(conn != null){
            try{
                for(int i = 0; i<prestamosAGuardar.size(); i++){
                    PreparedStatement prepararSentencia = conn.prepareStatement(SENTENCIA_INSERTAR_PRESTAMO);
                    prepararSentencia.setDate(1, prestamosAGuardar.get(i).getFechaPrestamo());
                    prepararSentencia.setDate(2, prestamosAGuardar.get(i).getFechaVencimiento());
                    prepararSentencia.setString(3, prestamosAGuardar.get(i).getEstado());
                    prepararSentencia.setInt(4, prestamosAGuardar.get(i).getRenovaRest());
                    prepararSentencia.setInt(5, prestamosAGuardar.get(i).getCargoMulta());
                    prepararSentencia.setInt(6, prestamosAGuardar.get(i).getIdRecursoDocumental());
                    prepararSentencia.setInt(7, prestamosAGuardar.get(i).getIdUsuario());
                    int resultado = prepararSentencia.executeUpdate();
                    respuesta = (resultado > 0);
                }
                conn.close();
            }catch(SQLException e){
                mostrarAlerta("Error de conexión", "Se perdió la conexión con la base de datos: No insertarón los préstamos", Alert.AlertType.ERROR);
            }
        }
        return respuesta;
        
    }
    
    //Metodo que obtiene de un usuario, el préstamo con un código de barras especifico.
    public static Prestamo recuperarPrestamoUsuario(int idUsuario, String codigoBarras){
        Prestamo prestamo = null;
        Connection conn = ConexionBD.getConnection();
        if(conn != null){
            try{
                PreparedStatement ps = conn.prepareStatement(CONSULTA_PRESTAMO_USUARIO);
                ps.setInt(1, idUsuario);
                ps.setString(2, codigoBarras);
                ps.setString(3, codigoBarras);
                ResultSet resultado = ps.executeQuery();
                if(resultado.next()){
                    prestamo = new Prestamo();
                    prestamo.setIdPrestamo(resultado.getInt("idPrestamo"));
                    prestamo.setFechaPrestamo(resultado.getDate("fechaPrestamo"));
                    prestamo.setFechaVencimiento(resultado.getDate("fechaVencimiento"));
                    prestamo.setEstado(resultado.getString("Estado"));
                    prestamo.setRenovaRest(resultado.getInt("renovaRest"));
                    prestamo.setCargoMulta(resultado.getInt("cargoMulta"));
                    prestamo.setIdRecursoDocumental(resultado.getInt("idRecursoDocumental"));
                    prestamo.setIdUsuario(resultado.getInt("idUsuario"));
                    prestamo.setTitulo(resultado.getString("titulo"));
                    prestamo.setAutor(resultado.getString("autor"));
                }else{
                    mostrarAlerta("Error", "No se encontró el préstamo", Alert.AlertType.ERROR);
                    System.out.println("No se encontró el préstamo");
                    prestamo = null;
                }
                conn.close();
            }catch(SQLException e){
                mostrarAlerta("Error de conexión", "Se perdió la conexión con la base de datos: No recuperarón los préstamos", Alert.AlertType.ERROR);
            }
        }
        return prestamo;
    }
    
    public static boolean actualizarPrestamosDevolver(ArrayList<Prestamo> prestamosADevolver){
        boolean respuesta = false;
        Connection conn = ConexionBD.getConnection();
        if(conn != null){
            try{
                for(int i = 0; i<prestamosADevolver.size(); i++){
                    PreparedStatement ps = conn.prepareStatement(SENTENCIA_ACTUALIZAR_PRESTAMO_DEVOLVER);
                    ps.setInt(1, prestamosADevolver.get(i).getIdPrestamo());
                    int resultado = ps.executeUpdate();
                    respuesta = (resultado > 0);
                }
                conn.close();
            }catch(SQLException e){
                mostrarAlerta("Error de conexión", "Se perdió la conexion con la base de datos", Alert.AlertType.ERROR);
            }
        }
        return respuesta;
    }
    
    public static boolean actualizarPrestamosRenovar(ArrayList<Prestamo> prestamosARenovar){
        boolean respuesta = false;
        Connection conn = ConexionBD.getConnection();
        if(conn != null){
            try{
                for(int i = 0 ; i<prestamosARenovar.size(); i++){
                    PreparedStatement ps = conn.prepareStatement(SENTENCIA_ACTUALIZAR_PRESTAMO_RENOVAR);
                    ps.setDate(1, prestamosARenovar.get(i).getFechaVencimiento());
                    ps.setInt(2, prestamosARenovar.get(i).getIdPrestamo());
                    int resultado = ps.executeUpdate();
                    respuesta = (resultado > 0);
                }
                conn.close();
            }catch(SQLException e){
                mostrarAlerta("Error de conexión", "Se perdió la conexion con la base de datos", Alert.AlertType.ERROR);
            }
        }
        return respuesta;
    }
    
    private static void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo){
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }
}
