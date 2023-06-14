package proyectoconstruccionbiblioteca.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import proyectoconstruccionbiblioteca.dao.MultimediaDAO;
import proyectoconstruccionbiblioteca.dao.RecursoDocumentalDAO;
import proyectoconstruccionbiblioteca.objetos.Multimedia;
import proyectoconstruccionbiblioteca.objetos.RecursoDocumental;

/**
 * FXML Controller class
 *
 * @author Martinez Franzoni Victor Manuel
 */
public class RegistrarMultimediaController implements Initializable {
    MultimediaDAO multimedidao = new MultimediaDAO();
    RecursoDocumentalDAO documentaldao = new RecursoDocumentalDAO();
    Multimedia multimedia = new Multimedia();
    RecursoDocumental documento = new RecursoDocumental();
    
    @FXML
    private TextField textFieldTitulo;
    
    @FXML
    private TextField textFieldAutor;
    
    @FXML
    private TextField textFieldEditor;
    
    @FXML
    private TextField textFieldTemas;
    
    @FXML
    private TextField textFieldClasificacionLC;
    
    @FXML
    private TextField textFieldDuraionHora;
    
    @FXML
    private TextField textFieldDuraionMinutos;
    
    @FXML
    private TextField textFieldDuraionSegundos;
    
    @FXML
    private TextField textFieldFormato;
    
    @FXML
    private TextField textFieldTipoDocumento;
                
    @FXML
    private TextField textFieldCodigoBarras;
            
    @FXML
    private TextArea textFieldDescripcion;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        formatoNumerico();
        textFieldDuraionSegundos.setText("00");
        textFieldDuraionMinutos.setText("00");
        textFieldDuraionHora.setText("00");
        
    }    
    
    public void clicSalir(ActionEvent actionEvent){
        navigationScreen("fxml/InicioRegistroModificar.fxml");       
    }
    
    public void clicRegistrar(ActionEvent actionEvent){
        try{
            String codigoBarra = textFieldCodigoBarras.getText();
            String tituloMultimedia = textFieldTitulo.getText();
            String tipoMaterial = "multimedia";
            String duracion = textFieldDuraionHora.getText() + ":" + 
                    textFieldDuraionMinutos.getText() + ":" + textFieldDuraionSegundos.getText();
            int idRecursoDocumental;
            datosCorrectos(true);
            if(textFieldDuraionSegundos.getText().equals("00") || textFieldDuraionSegundos.getText().isEmpty() || textFieldDuraionMinutos.getText().isEmpty()
                    || textFieldDuraionHora.getText().isEmpty() || textFieldAutor.getText().isEmpty() || textFieldClasificacionLC.getText().isEmpty()
                    || textFieldCodigoBarras.getText().isEmpty() || textFieldDescripcion.getText().isEmpty() || textFieldEditor.getText().isEmpty()
                    || textFieldFormato.getText().isEmpty() || textFieldTemas.getText().isEmpty() || textFieldTipoDocumento.getText().isEmpty()
                    || textFieldTitulo.getText().isEmpty()){
                datosCorrectos(true);
                datosErroenes(true);
                Alert alertInfo = new Alert(Alert.AlertType.ERROR);
                alertInfo.setTitle("Datos vacíos");
                alertInfo.setContentText("Los datos ingresados son vacions, por favor de validar que los datos sean correctos.");
                alertInfo.showAndWait();
            }else{
                datosCorrectos(true);
                if(documentaldao.selectCopiaExistes(codigoBarra, tituloMultimedia, tipoMaterial) == false){
                    documentaldao.insert(textFieldCodigoBarras.getText(), textFieldAutor.getText(), 
                            textFieldTitulo.getText(), textFieldClasificacionLC.getText(), 
                            textFieldDescripcion.getText(), textFieldEditor.getText(), textFieldTemas.getText(), tipoMaterial, 1);
                    idRecursoDocumental = documentaldao.selectIdRecursoDocumental(textFieldTitulo.getText(), textFieldCodigoBarras.getText(), textFieldAutor.getText());
                    if(idRecursoDocumental == 0){
                        Alert alertInfo = new Alert(Alert.AlertType.WARNING);
                        alertInfo.setTitle("Error");
                        alertInfo.setHeaderText("No se guardo el recurso documental");
                        alertInfo.setContentText("El recurso documental ingresado no fue guardado correctamente");
                        alertInfo.showAndWait();
                    }
                    multimedidao.insert(textFieldTipoDocumento.getText(), Time.valueOf(duracion), textFieldFormato.getText(), idRecursoDocumental);         
                    Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
                    alertInfo.setTitle("Datos ingresados correctamente");
                    alertInfo.setHeaderText("Los datos se guardaron correctamente");
                    alertInfo.setContentText("Los datos ingresados se guardaron de la manera correcta");
                    alertInfo.showAndWait();
                    limpiarCampos();
                }else{
                    if(alertaConfirmacion("Copia", "Recurso documetal existente", 
                            "El recurso documental ingresado ya existe en el sistema, ¿Desea guardarlo como copia?")== true){
                        idRecursoDocumental = documentaldao.selectIdRecursoDocumental(textFieldTitulo.getText(), textFieldCodigoBarras.getText(), textFieldAutor.getText());
                        documentaldao.updateCopia(idRecursoDocumental);
                        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
                        alertInfo.setTitle("Datos ingresados correctamente");
                        alertInfo.setHeaderText("Los datos se guardaron correctamente");
                        alertInfo.setContentText("Los datos ingresados se guardaron de la manera correcta");
                        alertInfo.showAndWait();
                        limpiarCampos();
                    }
                } 
            }
        }catch(NullPointerException ex){
            datosCorrectos(true);
            datosErroenes(true);
            Alert alertInfo = new Alert(Alert.AlertType.ERROR);
            alertInfo.setTitle("Datos vacíos");
            alertInfo.setContentText("Los datos ingresados son vacions, por favor de validar que los datos sean correctos.");
            alertInfo.showAndWait();
        }catch(NumberFormatException exNum){
            datosCorrectos(true);
            datosErroenes(true);
            Alert alertInfo = new Alert(Alert.AlertType.ERROR);
            alertInfo.setTitle("Datos vacíos");
            alertInfo.setContentText("Los datos ingresados son vacions, por favor de validar que los datos sean correctos.");
            alertInfo.showAndWait();
        }
    }
   
    public boolean alertaConfirmacion(String title, String headerText, String contentText){
        Alert alertEmptyInfo = new Alert(Alert.AlertType.CONFIRMATION);
        alertEmptyInfo.setTitle(title);
        alertEmptyInfo.setHeaderText(headerText);
        alertEmptyInfo.setContentText(contentText);
        Optional<ButtonType> result = alertEmptyInfo.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            return true;
        }
        return false;
    }
    
    public void formatoNumerico(){
        textFieldDuraionHora.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                 if(!newValue.matches("\\d*")){
                     textFieldDuraionHora.setText(newValue.replaceAll("[^\\d]", ""));
                 }               
                 if(textFieldDuraionHora.getText().length()>2){
                     String hora = textFieldDuraionHora.getText().substring(0, 2);
                     textFieldDuraionHora.setText(hora);
                 }
            }               
        }); 
        textFieldDuraionMinutos.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                 if(!newValue.matches("\\d*")){
                     textFieldDuraionMinutos.setText(newValue.replaceAll("[^\\d]", ""));
                 }               
                 if(textFieldDuraionMinutos.getText().length()>2){
                     String hora = textFieldDuraionMinutos.getText().substring(0, 2);
                     textFieldDuraionMinutos.setText(hora);
                 }
            }               
        }); 
        textFieldDuraionSegundos.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                 if(!newValue.matches("\\d*")){
                     textFieldDuraionSegundos.setText(newValue.replaceAll("[^\\d]", ""));
                 }               
                 if(textFieldDuraionSegundos.getText().length()>2){
                     String hora = textFieldDuraionSegundos.getText().substring(0, 2);
                     textFieldDuraionSegundos.setText(hora);
                 }
            }               
        }); 
        textFieldCodigoBarras.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                 if(!newValue.matches("\\d*")){
                     textFieldCodigoBarras.setText(newValue.replaceAll("[^\\d]", ""));
                 }               
            }               
        }); 
    }
    
    @FXML
    private void maximoTiempo(KeyEvent event){
        try{
            int horas = Integer.parseInt(textFieldDuraionHora.getText());
            int segundos = Integer.parseInt(textFieldDuraionSegundos.getText());
            int minutos = Integer.parseInt(textFieldDuraionMinutos.getText());
            if(segundos >= 60){
                minutos = minutos + 1;
                segundos = segundos - 60;
                String minutosNuevos = String.valueOf(minutos);
                String segundosNuevos = String.valueOf(segundos);
                textFieldDuraionSegundos.setText(segundosNuevos);
                textFieldDuraionMinutos.setText(minutosNuevos);
            }else if(minutos >= 60){
                horas = horas + 1;
                minutos = minutos - 60;
                String horasNuevo = String.valueOf(horas);
                String minutosNuevo = String.valueOf(minutos);
                textFieldDuraionHora.setText(horasNuevo);
                textFieldDuraionMinutos.setText(minutosNuevo);
            }else if(horas >= 24){
                Alert alertInfo = new Alert(Alert.AlertType.WARNING);
                alertInfo.setTitle("Error");
                alertInfo.setHeaderText("Numero de horas mayor");
                alertInfo.setContentText("El numero de horas que se ingresa son mayores a 24 horas, verifique que el tiempo sea correcto.");
                alertInfo.showAndWait();
            }
        }catch(NumberFormatException numerosExc){
            Logger.getLogger(RegistrarMultimediaController.class.getName()).log(Level.SEVERE, null, numerosExc);
        }   
    }
    
    public boolean datosErroenes(boolean validar){
        while(validar == true){
            if(textFieldAutor.getText().isEmpty()){
                textFieldAutor.setStyle("-fx-border-color: red;");
            }if(textFieldClasificacionLC.getText().isEmpty()){
                textFieldClasificacionLC.setStyle("-fx-border-color: red;");
            }if(textFieldCodigoBarras.getText().isEmpty()){
                textFieldCodigoBarras.setStyle("-fx-border-color: red;");
            }if(textFieldDescripcion.getText().isEmpty()){
                textFieldDescripcion.setStyle("-fx-border-color: red;");
            }if(textFieldDuraionHora.getText().isEmpty()){
                textFieldDuraionHora.setStyle("-fx-border-color: red;");
            }if(textFieldDuraionMinutos.getText().equals("00") || textFieldDuraionMinutos.getText().isEmpty()){
                textFieldDuraionMinutos.setStyle("-fx-border-color: red;");
            }if(textFieldDuraionSegundos.getText().equals("00") || textFieldDuraionSegundos.getText().isEmpty()){
                textFieldDuraionSegundos.setStyle("-fx-border-color: red;");
            }if(textFieldEditor.getText().isEmpty()){
                textFieldEditor.setStyle("-fx-border-color: red;");
            }if(textFieldFormato.getText().isEmpty()){
                textFieldFormato.setStyle("-fx-border-color: red;");
            }if(textFieldTemas.getText().isEmpty()){
                textFieldTemas.setStyle("-fx-border-color: red;");
            }if(textFieldTipoDocumento.getText().isEmpty()){
                textFieldTipoDocumento.setStyle("-fx-border-color: red;");
            }if(textFieldTitulo.getText().isEmpty()){
                textFieldTitulo.setStyle("-fx-border-color: red;");
            }
            validar = false;
        }
        return validar;
    }
    
    public boolean datosCorrectos(boolean validar){
        while(validar == true){
            if(!textFieldAutor.getText().isEmpty()){
                textFieldAutor.setStyle("-fx-border-color: null;");
            }if(!textFieldClasificacionLC.getText().isEmpty()){
                textFieldClasificacionLC.setStyle("-fx-border-color: null;");
            }if(!textFieldCodigoBarras.getText().isEmpty()){
                textFieldCodigoBarras.setStyle("-fx-border-color: null;");
            }if(!textFieldDescripcion.getText().isEmpty()){
                textFieldDescripcion.setStyle("-fx-border-color: null;");
            }if(!textFieldDuraionHora.getText().equals("00") || textFieldDuraionHora.getText().isEmpty()){
                textFieldDuraionHora.setStyle("-fx-border-color: null;");
            }if(!textFieldDuraionMinutos.getText().equals("00") || textFieldDuraionMinutos.getText().isEmpty()){
                textFieldDuraionMinutos.setStyle("-fx-border-color: null;");
            }if(!textFieldDuraionSegundos.getText().equals("00") || textFieldDuraionSegundos.getText().isEmpty()){
                textFieldDuraionSegundos.setStyle("-fx-border-color: null;");
            }if(!textFieldEditor.getText().isEmpty()){
                textFieldEditor.setStyle("-fx-border-color: null;");
            }if(!textFieldFormato.getText().isEmpty()){
                textFieldFormato.setStyle("-fx-border-color: null;");
            }if(!textFieldTemas.getText().isEmpty()){
                textFieldTemas.setStyle("-fx-border-color: null;");
            }if(!textFieldTipoDocumento.getText().isEmpty()){
                textFieldTipoDocumento.setStyle("-fx-border-color: null;");
            }if(!textFieldTitulo.getText().isEmpty()){
                textFieldTitulo.setStyle("-fx-border-color: null;");
            }
            validar = false;
        }
        return validar;
    }
    
    public void limpiarCampos(){
        textFieldAutor.setText("");
        textFieldClasificacionLC.setText("");
        textFieldCodigoBarras.setText("");
        textFieldDescripcion.setText("");
        textFieldDuraionHora.setText("00");
        textFieldDuraionMinutos.setText("00");
        textFieldDuraionSegundos.setText("00");
        textFieldEditor.setText("");
        textFieldFormato.setText("");
        textFieldTemas.setText("");
        textFieldTipoDocumento.setText("");
        textFieldTitulo.setText("");
    }
    
    public void navigationScreen(String url) {
        try {
            Stage stage = (Stage) textFieldAutor.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(url)));
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
