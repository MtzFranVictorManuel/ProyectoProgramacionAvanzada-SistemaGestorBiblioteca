/*
Nombre de Archivo: ModuloDeCiculacionController.java
Descripción: El controlador logico de la pantalla "Devolución de Préstamos" (ModuloDeCirculacion.fxml)
             Esta pantalla es unicamente para pruebas
Autor: Eduardo Antonio Castillo Garrido
Fecha de moficiación: 10/12/2021
*/

package proyectoconstruccionbiblioteca.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModuloDeCirculacionController implements Initializable {

    @FXML
    private Label titulo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicPrestamosADomicilio(ActionEvent event) {
        irPantallaPrestamosADomicilio();
    }
    
    @FXML
    private void clicDevolucionDePrestamos(ActionEvent event) {
        irPantallaDevoluciones();
    }
    
    private void irPantallaPrestamosADomicilio(){
        try{
            Parent cargaFXML = FXMLLoader.load(getClass().getResource("fxml/PrestamosADomicilio.fxml"));
            Scene scenePrestamos = new Scene(cargaFXML);
            Stage stage = new Stage();
            stage.setScene(scenePrestamos);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }catch(IOException e){
            mostrarAlerta("Error de aplicación","Lo sentimos, no se puede cargar la pantalla Prestamos a Domicilio",
                    Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
    
    private void irPantallaDevoluciones() {
        try{
            Parent cargaFXML = FXMLLoader.load(getClass().getResource("fxml/DevolucionDePrestamos.fxml"));
            Scene sceneDevoluciones = new Scene(cargaFXML);
            Stage stage = new Stage();
            stage.setScene(sceneDevoluciones);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }catch(IOException e){
            mostrarAlerta("Error de aplicación","Lo sentimos, no se puede cargar la pantalla de Devoluciones",
                    Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo){
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }

    @FXML
    private void clicCerrarSesion(ActionEvent event) {
        navigationScreen("fxml/IniciarSesion.fxml");
    }
    
    public void navigationScreen(String url) {
        try {
            Stage stage = (Stage) titulo.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(url)));
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    } 
}
