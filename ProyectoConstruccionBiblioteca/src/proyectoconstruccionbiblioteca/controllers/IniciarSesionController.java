/*
Pantalla: IniciarSesion
 *Josue
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import proyectoconstruccionbiblioteca.dao.PersonalAcademicoDAO;


public class IniciarSesionController implements Initializable {

    
    @FXML
    private Label lbErrorContraseña;
    @FXML
    private Label lbErrorNoPersonal;
    @FXML
    private PasswordField pfContrasena;
    @FXML
    private TextField tfNumeroPersonal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   

    @FXML
    private void clicEntrar(ActionEvent event) {
       validarUsuario();

    }
    public void validarUsuario(){
        String numP = tfNumeroPersonal.getText();
        String pass = pfContrasena.getText();
        lbErrorNoPersonal.setText(" ");
        lbErrorContraseña.setText(" ");
        
        
        if(numP.isEmpty()){
            lbErrorNoPersonal.setText("Este campo es obligatorio");
        }
        if(pass.isEmpty()){
            lbErrorContraseña.setText("Este campo es obligatorio");  
            if(numP.length()!=8){ //checar validación
                lbErrorNoPersonal.setText("El número de personal no es valido");
            }
        }
 
        if(!numP.isEmpty() & !pass.isEmpty()){
            
        if(numP.length() ==8){  
      
                    if(PersonalAcademicoDAO.iniciarSesion(numP, pass).equals("dir")){
                        navigationScreen("fxml/InicioRegistroModificar.fxml");
                        
                    }else if(PersonalAcademicoDAO.iniciarSesion(numP, pass).equals("sec")){
                        navigationScreen("fxml/ModuloDeCirculacion.fxml");
                     
                     }else if(PersonalAcademicoDAO.iniciarSesion(numP, pass).equals("bib")){
                        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
                        alertInfo.setTitle("");
                        alertInfo.setHeaderText("No Disponible por el momento");
                        alertInfo.setContentText("Ventana No Disponible");
                        alertInfo.showAndWait();
                       
                     }
            }else{
            lbErrorNoPersonal.setText("El número de personal no es valido");
            lbErrorContraseña.setText(" ");
            }
        }
        
    }
        public void mostrarPantallaDir(){  //FALTA AGREGAR BOTÓN DE CERRAR SESION A TODAS LAS PANTALLAS
          
            try{
            Parent cargaFXML = FXMLLoader.load(getClass().getResource("fxml/InicioRegistroModificar.fxml")); 
            Scene sceneDirector = new Scene(cargaFXML);
            Stage stage = new Stage();
            stage.setScene(sceneDirector);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "Error al mostrar pantalla de director: " +e.getMessage());
        }
        }
        
        public void mostrarPantallaSec(){
          
            try{
            Parent cargaFXML = FXMLLoader.load(getClass().getResource("fxml/ModuloDeCirculacion.fxml"));
            Scene sceneSecretaria = new Scene(cargaFXML);
            Stage stage = new Stage();
            stage.setScene(sceneSecretaria);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "Error al mostrar pantalla de secreataria: " +e.getMessage());
        }
        }
        
        
        public void mostrarPantallaBib(){
          
            try{
            Parent cargaFXML = FXMLLoader.load(getClass().getResource("Catalogo.fxml"));
            Scene sceneBibliotecario = new Scene(cargaFXML);
            Stage stage = new Stage();
            stage.setScene(sceneBibliotecario);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "Error al mostrar pantalla de bibliotecario: " +e.getMessage());
        }
    }
        
    public void navigationScreen(String url) {
        try {
            Stage stage = (Stage) tfNumeroPersonal.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(url)));
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}