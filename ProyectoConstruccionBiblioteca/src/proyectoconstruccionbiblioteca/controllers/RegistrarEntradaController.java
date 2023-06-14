/*
Autor: Josué
 */
package proyectoconstruccionbiblioteca.controllers;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import proyectoconstruccionbiblioteca.dao.EstudianteDAO;
import proyectoconstruccionbiblioteca.dao.VisitaDAO;



public class RegistrarEntradaController implements Initializable {

    @FXML
    private Label lbErrorDatos;
    @FXML
    private TextField tfMatricula;
    @FXML
    private ComboBox cbAreaVisita;
    @FXML
    private Label lbErrorAreaVisita;
    @FXML
    private ComboBox<String> cbAreaAvisitar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> opciones = FXCollections.observableArrayList("Area de computación","Area de lectura");
        cbAreaAvisitar.setItems(opciones);
    }  
    
    @FXML
    private void btEntrar(ActionEvent event) {
        validarUsuario();
    }
    
    public void validarUsuario(){ 
        String matricula=tfMatricula.getText();
        String areaVisita = cbAreaAvisitar.getValue();
        System.out.println(areaVisita);
        Date tiempo = Date.valueOf(LocalDate.now());
        Time hora = Time.valueOf(LocalTime.now());
        lbErrorDatos.setText(" ");
        lbErrorAreaVisita.setText(" ");
        
        try{
             if(matricula.isEmpty()){
            lbErrorDatos.setText("Este campo es obligatorio");
        }
        if(areaVisita == null){
             lbErrorAreaVisita.setText("Seleccione el área que visita");
        }
        if(!matricula.isEmpty()){  
            if(EstudianteDAO.registrarEntrada(matricula).equals(tfMatricula.getText())){
                System.out.println("Entrando a consulta"); 
                VisitaDAO.registrarVisita(matricula, areaVisita, tiempo ,hora);
            }else{
                  JOptionPane.showMessageDialog(null,"El usuario no existe");
            }                 
        } 
        }catch (NullPointerException e){
            System.out.println("Error al validar");
        }
       
        
             
    }
}