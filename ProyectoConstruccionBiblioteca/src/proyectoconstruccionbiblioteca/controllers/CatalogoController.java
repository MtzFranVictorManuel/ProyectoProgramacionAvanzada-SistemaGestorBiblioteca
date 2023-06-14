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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyectoconstruccionbiblioteca.dao.RecursoDocumentalDAO;

public class CatalogoController implements Initializable {

    @FXML
    private TextField tfBusqueda;
    @FXML
    private ComboBox cbTipoDeRecurso;
    @FXML
    private Label lbErrorBusqueda;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void btBuscar(ActionEvent event) {
        validarDatos();
    }

    @FXML
    private void btRegresar(ActionEvent event) {
        
    }
    
    public void validarDatos(){
        String busqueda = tfBusqueda.getText();
        String tipoRecurso = (String) cbTipoDeRecurso.getValue();
        lbErrorBusqueda.setText(" ");
        if(busqueda.isEmpty()){            
            lbErrorBusqueda.setText("Este campo es obligatorio");
        }else{      
            /*if(RecursoDocumentalDAO.recuperarRecurso(busqueda, tipoRecurso).equals(tfBusqueda.getText())){ //verificar la consulta
              irPantallaResultados();
            }*/
        }        
    }   
                
    
    
    public void irPantallaResultados(){
         try{
            Parent cargaFXML = FXMLLoader.load(getClass().getResource("Resultados.fxml"));
            Scene sceneRegEntrada = new Scene(cargaFXML);
            Stage stage = new Stage();
            stage.setScene(sceneRegEntrada);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }catch(IOException e){
            mostrarAlerta("Error de aplicación ","Lo sentimos, no se puede cargar la pantalla Resultados",
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
     
}

