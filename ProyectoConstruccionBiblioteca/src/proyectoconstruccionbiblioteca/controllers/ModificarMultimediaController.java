package proyectoconstruccionbiblioteca.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
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
public class ModificarMultimediaController implements Initializable {
    MultimediaDAO multimedidao = new MultimediaDAO();
    RecursoDocumentalDAO documentaldao = new RecursoDocumentalDAO();
    Multimedia multimedia = new Multimedia();
    RecursoDocumental documental = new RecursoDocumental();
    Multimedia multimediaInfo = multimedidao.seleccionarMultimedia(RecursoDocumental.getIdRecursoDocumentalGuarda());
    RecursoDocumental documentoInfo = documentaldao.selectModificarRecursoDocumental(RecursoDocumental.getIdRecursoDocumentalGuarda());
    
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
    private TextField textFielNumeroCopias;
    
            
    @FXML
    private TextArea textFieldDescripcion;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        formatoNumerico();
        textFieldAutor.setText(documentoInfo.getAutor());
        textFieldClasificacionLC.setText(documentoInfo.getClasificacionLC());
        textFieldCodigoBarras.setText(documentoInfo.getCodigoBarras());
        textFieldDescripcion.setText(documentoInfo.getDescripcion());
        textFieldEditor.setText(documentoInfo.getEditor());
        textFieldTemas.setText(documentoInfo.getTema());
        textFieldTitulo.setText(documentoInfo.getTitulo());
        textFielNumeroCopias.setText(String.valueOf(documentoInfo.getNumCopias()));
        if(multimediaInfo != null){
           Time duracion = multimediaInfo.getDuracion();
           int segundos = duracion.getSeconds();
           int minutos = duracion.getMinutes();
           int horas = duracion.getHours();
           textFieldDuraionSegundos.setText(String.valueOf(segundos));
           textFieldDuraionMinutos.setText(String.valueOf(minutos));
           textFieldDuraionHora.setText(String.valueOf(horas));
           textFieldTipoDocumento.setText(multimediaInfo.getTipoMultimedia());
           textFieldFormato.setText(multimediaInfo.getFormato());
        }
    }    
    public void clicSalir(ActionEvent actionEvent){
        navigationScreen("fxml/ModificarEliminarRecursoDocumental.fxml");
    }
    
    public void clicGuardarCambios(ActionEvent actionEvent){
        try{
            String codigoBarra = textFieldCodigoBarras.getText();
            String tituloLibro = textFieldTitulo.getText();
            String tipoMaterial = "multimedia";
            int idRecursoDocumental;
            datosCorrectos(true);
            if(Integer.valueOf(textFielNumeroCopias.getText()) <= 0){
                Alert alertInfo = new Alert(Alert.AlertType.WARNING);
                alertInfo.setTitle("Numero invalido");
                alertInfo.setHeaderText("Numero de copias invalido");
                alertInfo.setContentText("El numero de copias no puede ser 0 o menor a 0, por favor de escribir un numero mayor a 0");
                alertInfo.showAndWait();
            }else if (textFieldDuraionSegundos.getText().equals("00") || textFieldDuraionSegundos.getText().isEmpty() || textFieldDuraionMinutos.getText().isEmpty()
                    || textFieldDuraionHora.getText().isEmpty() || textFieldAutor.getText().isEmpty() || textFieldClasificacionLC.getText().isEmpty()
                    || textFieldCodigoBarras.getText().isEmpty() || textFieldDescripcion.getText().isEmpty() || textFieldEditor.getText().isEmpty()
                    || textFieldFormato.getText().isEmpty() || textFieldTemas.getText().isEmpty() || textFieldTipoDocumento.getText().isEmpty()
                    || textFieldTitulo.getText().isEmpty()){
                datosErroenes(true);
                Alert alertInfo = new Alert(Alert.AlertType.ERROR);
                alertInfo.setTitle("Datos vacíos");
                alertInfo.setContentText("Los datos ingresados son vacions, por favor de validar que los datos sean correctos.");
                alertInfo.showAndWait();
            }else{
                if(datosCorrectos(true) == true){
                    idRecursoDocumental = RecursoDocumental.getIdRecursoDocumentalGuarda();
                    documental.setTitulo(tituloLibro);
                    documental.setAutor(textFieldAutor.getText());
                    documental.setEditor(textFieldEditor.getText());
                    documental.setTema(textFieldTemas.getText());
                    documental.setDescripcion(textFieldDescripcion.getText());
                    documental.setClasificacionLC(textFieldClasificacionLC.getText());
                    documental.setCodigoBarras(codigoBarra);
                    documental.setTipoMaterial(tipoMaterial);
                    documental.setNumCopias(Integer.parseInt(textFielNumeroCopias.getText()));
                    if(idRecursoDocumental == 0){
                        Alert alertInfo = new Alert(Alert.AlertType.WARNING);
                        alertInfo.setTitle("Error");
                        alertInfo.setHeaderText("No se guardo el recurso documental");
                        alertInfo.setContentText("Los datos ingresados son vacions o el tiempo es mayor a 24hh:59mm:59ss, por favor de validar que los datos sean correctos.");
                        alertInfo.showAndWait();
                    }else{
                        String duracion = textFieldDuraionHora.getText() + ":" + 
                            textFieldDuraionMinutos.getText() + ":" + textFieldDuraionSegundos.getText();
                        if(multimediaInfo == null){
                            multimedidao.insert(textFieldTipoDocumento.getText(), Time.valueOf(duracion), textFieldFormato.getText(), idRecursoDocumental);
                            documentaldao.update(documental, idRecursoDocumental);
                            limpiarCampos();
                            if(alertaConfirmarActualizacion(tituloLibro)){
                               navigationScreen("fxml/ModificarEliminarRecursoDocumental.fxml");  
                            }      
                        }else{                         
                            multimedia.setDuracion(Time.valueOf(duracion));
                            multimedia.setFormato(textFieldFormato.getText());
                            multimedia.setTipoMultimedia(textFieldTipoDocumento.getText());
                            multimedidao.update(multimedia, idRecursoDocumental);
                            documentaldao.update(documental, idRecursoDocumental);
                            if(alertaConfirmarActualizacion(tituloLibro)){
                               navigationScreen("fxml/ModificarEliminarRecursoDocumental.fxml");  
                            }
                        }                       
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
        }catch(java.lang.NumberFormatException exNum){
            datosCorrectos(true);
            datosErroenes(true);
            Alert alertInfo = new Alert(Alert.AlertType.ERROR);
            alertInfo.setTitle("Datos vacíos");
            alertInfo.setContentText("Los datos ingresados son vacions, por favor de validar que los datos sean correctos.");
            alertInfo.showAndWait();
        }
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
        textFielNumeroCopias.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                 if(!newValue.matches("\\d*")){
                     textFielNumeroCopias.setText(newValue.replaceAll("[^\\d]", ""));
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
            Logger.getLogger(ModificarMultimediaController.class.getName()).log(Level.SEVERE, null, numerosExc);
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
            }if(textFieldDuraionHora.getText().equals("00") || textFieldDuraionHora.getText().isEmpty()){
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
            }if(textFielNumeroCopias.getText().isEmpty()){
                textFielNumeroCopias.setStyle("-fx-border-color: red;");
            }
            return validar = true;
        }
        return false;
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
            }if(!textFielNumeroCopias.getText().isEmpty()){
                textFielNumeroCopias.setStyle("-fx-border-color: null;");
            }
            return validar = true;
        }
        return false;
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
    
    public boolean alertaConfirmarActualizacion(String tituloLibro){
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
        alertInfo.setTitle("Cambios realizados");
        alertInfo.setHeaderText("Cambios realizados correctamente");
        alertInfo.setContentText("Los cambios implementados en " + tituloLibro + " se realizaron de manera correcta.");
        alertInfo.showAndWait();
        return true;
    }  
    
}
