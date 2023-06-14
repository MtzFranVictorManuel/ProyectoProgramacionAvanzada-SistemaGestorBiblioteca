/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoconstruccionbiblioteca.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author azul_
 */
public class ConexionBD {
    
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    
    private static final String DATABASE = "sistemabibliotecario";
    
    private static final String HOSTNAME = "localhost";
    
    private static final String PORT = "3306";
    
    private static final String URL = "jdbc:mysql://"+HOSTNAME+":"+PORT+"/"+DATABASE+"?serverTimezone=UTC";
    
    private static final String USERNAME = "lalo9706";
            
    private static final String PASSWORD = "1234";
    
    
    public static Connection getConnection(){
        Connection conn = null;
        try{
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }catch(Exception e){
            System.out.println("Error al abrir conexión");
            e.printStackTrace();
        }
        return conn;
    }

    public static void close(ResultSet rSet){
        try{
            if(rSet != null){
                rSet.close();
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public static void close (PreparedStatement pStatement){
        try{
            if(pStatement != null){
                pStatement.close();
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public static void close(Connection connect){
        try{
            if(connect != null){
                connect.close();
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}
