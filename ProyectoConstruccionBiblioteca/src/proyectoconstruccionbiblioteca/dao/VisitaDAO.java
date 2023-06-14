/*
Autor: Josué
 */
package proyectoconstruccionbiblioteca.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import javax.swing.JOptionPane;
import modelo.POJO.Visita;
import proyectoconstruccionbiblioteca.conexion.ConexionBD;

/**
 *
 * @author WIN 10
 */


public class VisitaDAO {
    
    public static PreparedStatement preStatement = null;
    public static Connection conn = null;
    private static final String SQL= "INSERT INTO visita (fechaHoraEntrada, matricula, areaVisita) VALUES (?,?,?);";
    
    
    public static boolean registrarVisita(String matricula, String areaVisita, Date fechaHora, Time hora){
        
        Visita visita= new Visita();
        
        
        conn=ConexionBD.getConnection();
        
        if(conn!=null){
            try{
                preStatement = conn.prepareStatement(SQL);
                preStatement.setDate(1, fechaHora);
                preStatement.setString(2, matricula);
                preStatement.setString(3, areaVisita);
                preStatement.executeUpdate();  
                conn.close();
                return true;
            }catch(SQLException e){
                  JOptionPane.showMessageDialog(null, "Error de conexión: " +e.getMessage());
            }
        }
        return false;
    }
}

