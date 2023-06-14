
package proyectoconstruccionbiblioteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import proyectoconstruccionbiblioteca.conexion.ConexionBD;

public class EstudianteDAO {
    public static PreparedStatement preStatement = null;
    public static Connection conn = null;
    private static final String SQL = "SELECT matricula FROM estudiante WHERE matricula=?;";
    
    
    public static String registrarEntrada(String matricula){
        String matriculaValida = null;
       
        conn=ConexionBD.getConnection();
        
        if(conn!=null){
            try{
                preStatement = conn.prepareStatement(SQL);
                preStatement.setString(1,matricula);
                ResultSet rs=preStatement.executeQuery();                
                if(rs.next()){
                    matriculaValida = rs.getString("matricula");
                    return matriculaValida;
                     
                 }               
                conn.close();          
            }catch(SQLException e){
                  JOptionPane.showMessageDialog(null, "Error en la conexión");
            }
        }
        return matriculaValida;
    }
}
      
