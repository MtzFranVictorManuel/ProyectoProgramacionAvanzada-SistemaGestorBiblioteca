package proyectoconstruccionbiblioteca.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author Martinez Franzoni Victor Manuel
 */
public class InicioRegistroModificarController implements Initializable {

    @FXML
    private ImageView imagenLogo;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        File file = new File("img/flor-azul.jpeg");
        Image image = new Image(file.toURI().toString());
        imagenLogo.setImage(image);
        
    }    
    
    public void clicRegistrarLibro(ActionEvent actionEvent){
        navigationScreen("fxml/RegistrarLibro.fxml");
    }
    
    public void clicRegistrarMultimedia(ActionEvent actionEvent){
        navigationScreen("fxml/RegistrarMultimedia.fxml");
    }
    
    public void clicSalir(ActionEvent actionEvent){
        navigationScreen("fxml/IniciarSesion.fxml");
    }
    
    public void clicModificarRecurso(ActionEvent actionEvent){
        navigationScreen("fxml/ModificarEliminarRecursoDocumental.fxml");
    }
    
    public void navigationScreen(String url) {
        try {
            Stage stage = (Stage) imagenLogo.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(url)));
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }  
}
