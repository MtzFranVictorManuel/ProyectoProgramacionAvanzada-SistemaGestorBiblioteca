
package proyectoconstruccionbiblioteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import proyectoconstruccionbiblioteca.conexion.ConexionBD;


public class PersonalAcademicoDAO {
    public static PreparedStatement preStatement = null;
    public static Connection conn = null;
    private static final String SQL = "SELECT privilegio FROM personalacademico WHERE numPersonal=? and contrasena =?;";
       
    public static String iniciarSesion(String nPersonal, String contrasena){
        String tipoPrivilegio = null; 
        conn=ConexionBD.getConnection();
         if(conn != null){
             try{
                preStatement = conn.prepareStatement(SQL);
                preStatement.setString(1,nPersonal);
                preStatement.setString(2,contrasena);                
                ResultSet rs=preStatement.executeQuery();               
                 if(rs.next()){
                     tipoPrivilegio = rs.getString("privilegio");
                     return tipoPrivilegio;                    
                 }else{
                     JOptionPane.showMessageDialog(null,"El usuario no existe");
                 }               
                conn.close();
             }catch(SQLException e){                
                 JOptionPane.showMessageDialog(null, "Error de conexión"); //CAMBIAR A UNA ALERTA
                 
             }            
        }
            return tipoPrivilegio;
    }
        
        
}


