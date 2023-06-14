/*
Nombre de Archivo: UsuarioDAO.java
Descripción: Objeto de acceso a datos para de la clase Usuario.java
Autor: Eduardo Antonio Castillo Garrido
Fecha de moficiación: 12/12/2021
*/

package proyectoconstruccionbiblioteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import proyectoconstruccionbiblioteca.conexion.ConexionBD;
import proyectoconstruccionbiblioteca.objetos.Usuario;
import proyectoconstruccionbiblioteca.util.Constantes;
        
public class UsuarioDAO {
    
    private static final String CONSULTA_USUARIO_ESTUDIANTE = "SELECT matricula, carrera, estudiante.idUsuario, nombre, apellidos, direccion, telefono, email, estado, numAdeudos, montoTotal " +
                    "FROM estudiante " +
                    "INNER JOIN usuario " +
                    "ON estudiante.idUsuario = usuario.idUsuario " +
                    "INNER JOIN multa " +
                    "ON usuario.idUsuario = multa.idMulta " +
                    "WHERE estudiante.matricula = ?";
    
    private static final String CONSULTA_USUARIO_PERSONALACADEMICO = "SELECT numPersonal, personalacademico.idUsuario, nombre, apellidos, direccion, telefono, email, estado, numAdeudos, montoTotal " +
                    "FROM personalacademico " +
                    "INNER JOIN usuario " +
                    "ON personalacademico.idUsuario = usuario.idUsuario " +
                    "INNER JOIN multa " +
                    "ON usuario.idUsuario = multa.idMulta " +
                    "WHERE personalacademico.numPersonal = ?";
    
    private static final String SENTENCIA_ACTUALIZAR_USUARIO_NUMADEUDOS = "UPDATE usuario SET numAdeudos = ? WHERE idUsuario = ?";
    
    public static Usuario recuperarEstudiante(String identificador){
        Usuario estudiante = new Usuario();
                    
        Connection conn = ConexionBD.getConnection();
        if(conn != null){
            try{
                PreparedStatement ps = conn.prepareStatement(CONSULTA_USUARIO_ESTUDIANTE);
                ps.setString(1, identificador);
                ResultSet resultado = ps.executeQuery();
                if(resultado.next()){
                    estudiante.setCodigo(Constantes.CODIGO_USUARIO_EXISTE);
                    estudiante.setIdUsuario(resultado.getInt("idUsuario"));
                    estudiante.setNombre(resultado.getString("nombre"));
                    estudiante.setApellidos(resultado.getString("apellidos"));
                    estudiante.setDireccion(resultado.getString("direccion"));
                    estudiante.setTelefono(resultado.getString("telefono"));
                    estudiante.setEmail(resultado.getString("email"));
                    estudiante.setEstado(resultado.getString("estado"));
                    estudiante.setNumAdeudos(resultado.getInt("numAdeudos"));
                    estudiante.setMontoTotal(resultado.getInt("montoTotal"));
                    estudiante.setMatricula(resultado.getString("matricula"));
                    estudiante.setCarrera(resultado.getString("carrera"));
                }else{
                    System.out.println("No se recuperó el estudiante");
                    estudiante.setCodigo(Constantes.CODIGO_USUARIO_NOEXISTE);
                }
                conn.close();
            }catch(SQLException ex){
                System.out.println("Error conexion");
                estudiante.setCodigo(Constantes.CODIGO_ERROR_CONEXION_BD);
                ex.printStackTrace();
            }
        }else{
            System.out.println("Error de conexión");
            estudiante.setCodigo(Constantes.CODIGO_ERROR_CONEXION_BD);
        }
        return estudiante;
    }

    public static Usuario recuperarPersonalAcademico(String identificador) {
        Usuario personalAcademico = new Usuario();
        
        Connection conn = ConexionBD.getConnection();
        if(conn != null){
            try{
                PreparedStatement ps = conn.prepareStatement(CONSULTA_USUARIO_PERSONALACADEMICO);
                ps.setString(1, identificador);
                ResultSet resultado = ps.executeQuery();
                if(resultado.next()){
                    personalAcademico.setCodigo(Constantes.CODIGO_USUARIO_EXISTE);
                    personalAcademico.setIdUsuario(resultado.getInt("idUsuario"));
                    personalAcademico.setNombre(resultado.getString("nombre"));
                    personalAcademico.setApellidos(resultado.getString("apellidos"));
                    personalAcademico.setDireccion(resultado.getString("direccion"));
                    personalAcademico.setTelefono(resultado.getString("telefono"));
                    personalAcademico.setEmail(resultado.getString("email"));
                    personalAcademico.setEstado(resultado.getString("estado"));
                    personalAcademico.setNumAdeudos(resultado.getInt("numAdeudos"));
                    personalAcademico.setMontoTotal(resultado.getInt("montoTotal"));
                    personalAcademico.setNumPersonal(resultado.getString("numPersonal"));
                }else{
                    personalAcademico.setCodigo(Constantes.CODIGO_USUARIO_NOEXISTE);
                }
                
                conn.close(); 
            }catch(SQLException ex){
                personalAcademico.setCodigo(Constantes.CODIGO_ERROR_CONEXION_BD);
                ex.printStackTrace();
            }
        }else{
            personalAcademico.setCodigo(Constantes.CODIGO_ERROR_CONEXION_BD);
        }
        return personalAcademico;
    }

    public static boolean actualizarUsuarioNumAdeudos(int numAdeudos, int idUsuario){
        boolean respuesta = false;
        Connection conn = ConexionBD.getConnection();
        if(conn != null){
            try{
                PreparedStatement ps = conn.prepareStatement(SENTENCIA_ACTUALIZAR_USUARIO_NUMADEUDOS);
                ps.setInt(1, numAdeudos);
                ps.setInt(2, idUsuario);
                int resultado = ps.executeUpdate();
                respuesta = (resultado > 0);
            }catch(SQLException e){
                mostrarAlerta("Error de conexión", "Se perdió la conexion con la base de datos: No se actualizó número de adeudos", Alert.AlertType.ERROR);
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
