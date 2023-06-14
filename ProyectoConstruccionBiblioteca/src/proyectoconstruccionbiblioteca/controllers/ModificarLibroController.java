package proyectoconstruccionbiblioteca.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import proyectoconstruccionbiblioteca.dao.LibroDAO;
import proyectoconstruccionbiblioteca.dao.RecursoDocumentalDAO;
import proyectoconstruccionbiblioteca.objetos.Libro;
import proyectoconstruccionbiblioteca.objetos.RecursoDocumental;

/**
 * FXML Controller class
 *
 * @author Martinez Franzoni Victor Manuel
 */
public class ModificarLibroController implements Initializable {

    LibroDAO librodao = new LibroDAO();
    RecursoDocumentalDAO documentaldao = new RecursoDocumentalDAO();
    RecursoDocumental documental = new RecursoDocumental();
    RecursoDocumental recursoDocumentalInf =
            documentaldao.selectModificarRecursoDocumental(RecursoDocumental.getIdRecursoDocumentalGuarda());
    Libro libroInf = librodao.seleccionarLibro(RecursoDocumental.getIdRecursoDocumentalGuarda());
    
    
    @FXML
    private TextField textFieldTitulo;
    
    @FXML
    private TextField textFieldAutor;
    
    @FXML
    private TextField textFieldEditor;
    
    @FXML
    private TextField textFieldTemas;
    
    @FXML
    private TextArea textFieldDescripcion;
    
    @FXML
    private TextField textFieldClasificacionLC;
    
    @FXML
    private TextField textFieldCodigoBarras;
    
    @FXML
    private TextField textFieldIsbn;
    
    @FXML
    private TextField textFieldEdicion;
    
    @FXML
    private TextField textFieldVolumen;
    
    @FXML
    private TextField textFieldIdioma;
    
    @FXML
    private TextField textFieldSerie;
    
    @FXML
    private TextField textFieldTipoObra;
    
    @FXML
    private TextField textFielNumeroCopias;
    
    @FXML
    private DatePicker datePickerFechaPublicacion;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        formatoNumerico();
        textFieldAutor.setText(recursoDocumentalInf.getAutor());
        textFieldClasificacionLC.setText(recursoDocumentalInf.getClasificacionLC());
        textFieldCodigoBarras.setText(recursoDocumentalInf.getCodigoBarras());
        textFieldDescripcion.setText(recursoDocumentalInf.getDescripcion());
        textFieldEditor.setText(recursoDocumentalInf.getEditor());
        textFieldTemas.setText(recursoDocumentalInf.getTema());
        textFieldTitulo.setText(recursoDocumentalInf.getTitulo());
        textFielNumeroCopias.setText(String.valueOf(recursoDocumentalInf.getNumCopias()));
        if(libroInf == null){
            textFieldIsbn.setText("");
            textFieldEdicion.setText("");
            textFieldVolumen.setText("");
            textFieldIdioma.setText("");
            textFieldSerie.setText("");
            textFieldTipoObra.setText("");
        }else{           
            String volumenLibro = Integer.toString(libroInf.getVolumen());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
            String fecha = dateFormat.format(libroInf.getFechaPublicacion());       
            textFieldIsbn.setText(libroInf.getIsbn());
            textFieldEdicion.setText(libroInf.getEdicion());
            textFieldVolumen.setText(volumenLibro);
            textFieldIdioma.setText(libroInf.getIdioma());
            textFieldSerie.setText(libroInf.getSerie());
            textFieldTipoObra.setText(libroInf.getTipoObraLiteraria());
            datePickerFechaPublicacion.setValue(LOCAL_DATE(fecha));
        }
    }    
    
    public void clicSalir(ActionEvent actionEvent){
         navigationScreen("fxml/ModificarEliminarRecursoDocumental.fxml");
    }
     
    public void clicGuardarCambios(ActionEvent actionEvent){
        try{
            String codigoBarra = textFieldCodigoBarras.getText();
            String tituloLibro = textFieldTitulo.getText();
            String tipoMaterial = "libro";
            int idRecursoDocumental;
            datosCorrectos(true);
            if(textFieldAutor.getText().isEmpty() || textFieldClasificacionLC.getText().isEmpty() || textFieldCodigoBarras.getText().isEmpty()
                    || textFieldDescripcion.getText().isEmpty() || textFieldEdicion.getText().isEmpty() || textFieldEditor.getText().isEmpty()
                    || textFieldIdioma.getText().isEmpty() || textFieldIsbn.getText().isEmpty() || textFieldSerie.getText().isEmpty()
                    || textFieldTemas.getText().isEmpty() || textFieldTipoObra.getText().isEmpty() || textFieldTitulo.getText().isEmpty()
                    || textFieldVolumen.getText().isEmpty()){
                datosCorrectos(true);
                datosErroneso(true);
                Alert alertInfo = new Alert(Alert.AlertType.ERROR);
                alertInfo.setTitle("Datos vacíos");
                alertInfo.setContentText("Los datos ingresados son vacions, por favor de validar que los datos sean correctos.");
                alertInfo.showAndWait();               
            }else{
                if(Integer.valueOf(textFielNumeroCopias.getText()) <= 0){
                    Alert alertInfo = new Alert(Alert.AlertType.WARNING);
                    alertInfo.setTitle("Numero invalido");
                    alertInfo.setHeaderText("Numero de copias invalido");
                    alertInfo.setContentText("El numero de copias no puede ser 0 o menor a 0, por favor de escribir un numero mayor a 0");
                    alertInfo.showAndWait();
                }else{
                    Date fechaPublicacion = Date.valueOf(datePickerFechaPublicacion.getValue()); 
                    datosCorrectos(true);
                    int volumenLibro = Integer.parseInt(textFieldVolumen.getText());
                    Libro libroNuevo = new Libro();
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
                        documentaldao.update(documental, idRecursoDocumental);
                        if(idRecursoDocumental == 0){
                            Alert alertInfo = new Alert(Alert.AlertType.WARNING);
                            alertInfo.setTitle("Error");
                            alertInfo.setHeaderText("No se guardo el recurso documental");
                            alertInfo.setContentText("El recurso documental ingresado no fue guardado correctamente");
                            alertInfo.showAndWait();
                        }else{
                            if(libroInf == null){
                                libroNuevo.setEdicion(textFieldEdicion.getText());
                                libroNuevo.setIsbn(textFieldIsbn.getText());
                                libroNuevo.setFechaPublicacion(fechaPublicacion);
                                libroNuevo.setIdioma(textFieldIdioma.getText());
                                libroNuevo.setSerie(textFieldSerie.getText());
                                libroNuevo.setVolumen(volumenLibro);
                                libroNuevo.setTipoObraLiteraria(textFieldTipoObra.getText());
                                librodao.insertar(libroNuevo, idRecursoDocumental);
                                if(alertaConfirmarActualizacion(tituloLibro)){
                                   navigationScreen("fxml/ModificarEliminarRecursoDocumental.fxml");  
                                }      
                            }else{                         
                                libroNuevo.setEdicion(textFieldEdicion.getText());
                                libroNuevo.setIsbn(textFieldIsbn.getText());
                                libroNuevo.setFechaPublicacion(fechaPublicacion);
                                libroNuevo.setIdioma(textFieldIdioma.getText());
                                libroNuevo.setSerie(textFieldSerie.getText());
                                libroNuevo.setVolumen(volumenLibro);
                                libroNuevo.setTipoObraLiteraria(textFieldTipoObra.getText());
                                librodao.update(libroNuevo, idRecursoDocumental);
                                if(alertaConfirmarActualizacion(tituloLibro)){
                                   navigationScreen("fxml/ModificarEliminarRecursoDocumental.fxml");  
                                }
                            }                       
                        }            
                    }
                }
            }
        }catch(NullPointerException ex){
            datosCorrectos(true);
                    datosErroneso(true);
                    Alert alertInfo = new Alert(Alert.AlertType.ERROR);
                    alertInfo.setTitle("Datos vacíos");
                    alertInfo.setContentText("Los datos ingresados son vacions, por favor de validar que los datos sean correctos.");
                    alertInfo.showAndWait();
        }catch(NumberFormatException exNum){
            datosCorrectos(true);
                    datosErroneso(true);
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
    
    public static final LocalDate LOCAL_DATE (String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }
    
    public void formatoNumerico(){
        textFieldCodigoBarras.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                 if(!newValue.matches("\\d*")){
                     textFieldCodigoBarras.setText(newValue.replaceAll("[^\\d]", ""));
                 }               
            }               
        });
        textFieldVolumen.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                 if(!newValue.matches("\\d*")){
                     textFieldVolumen.setText(newValue.replaceAll("[^\\d]", ""));
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
    
    public boolean datosErroneso(boolean validar){
        while(validar == true){
            if(textFieldAutor.getText().isEmpty()){
                textFieldAutor.setStyle("-fx-border-color: red;");
            }if(textFieldClasificacionLC.getText().isEmpty()){
                textFieldClasificacionLC.setStyle("-fx-border-color: red;");
            }if(textFieldCodigoBarras.getText().isEmpty()){
                textFieldCodigoBarras.setStyle("-fx-border-color: red;");
            }if(textFieldDescripcion.getText().isEmpty()){
                textFieldDescripcion.setStyle("-fx-border-color: red;");
            }if(textFieldEdicion.getText().isEmpty()){
                textFieldEdicion.setStyle("-fx-border-color: red;");
            }if(textFieldEditor.getText().isEmpty()){
                textFieldEditor.setStyle("-fx-border-color: red;");
            }if(textFieldIdioma.getText().isEmpty()){
                textFieldIdioma.setStyle("-fx-border-color: red;");
            }if(textFieldIsbn.getText().isEmpty()){
                textFieldIsbn.setStyle("-fx-border-color: red;");
            }if(textFieldSerie.getText().isEmpty()){
                textFieldSerie.setStyle("-fx-border-color: red;");
            }if(textFieldTemas.getText().isEmpty()){
                textFieldTemas.setStyle("-fx-border-color: red;");
            }if(textFieldTipoObra.getText().isEmpty()){
                textFieldTipoObra.setStyle("-fx-border-color: red;");
            }if(textFieldTitulo.getText().isEmpty()){
                textFieldTitulo.setStyle("-fx-border-color: red;");
            }if(textFieldVolumen.getText().isEmpty()){
                textFieldVolumen.setStyle("-fx-border-color: red;");
            }if(datePickerFechaPublicacion.getValue() == null){
                datePickerFechaPublicacion.setStyle("-fx-border-color: red;");
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
            }if(!textFieldEdicion.getText().isEmpty()){
                textFieldEdicion.setStyle("-fx-border-color: null;");
            }if(!textFieldEditor.getText().isEmpty()){
                textFieldEditor.setStyle("-fx-border-color: null;");
            }if(!textFieldIdioma.getText().isEmpty()){
                textFieldIdioma.setStyle("-fx-border-color: null;");
            }if(!textFieldIsbn.getText().isEmpty()){
                textFieldIsbn.setStyle("-fx-border-color: null;");
            }if(!textFieldSerie.getText().isEmpty()){
                textFieldSerie.setStyle("-fx-border-color: null;");
            }if(!textFieldTemas.getText().isEmpty()){
                textFieldTemas.setStyle("-fx-border-color: null;");
            }if(!textFieldTipoObra.getText().isEmpty()){
                textFieldTipoObra.setStyle("-fx-border-color: null;");
            }if(!textFieldTitulo.getText().isEmpty()){
                textFieldTitulo.setStyle("-fx-border-color: null;");
            }if(!textFieldVolumen.getText().isEmpty()){
                textFieldVolumen.setStyle("-fx-border-color: null;");
            }if(datePickerFechaPublicacion.getValue() != null){
                datePickerFechaPublicacion.setStyle("-fx-border-color: null;");
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
        textFieldEdicion.setText("");
        textFieldEditor.setText("");
        textFieldIdioma.setText("");
        textFieldIsbn.setText("");
        textFieldSerie.setText("");
        textFieldTemas.setText("");
        textFieldTipoObra.setText("");
        textFieldTitulo.setText("");
        textFieldVolumen.setText("");
        textFielNumeroCopias.setText("0");
        datePickerFechaPublicacion.setValue(null);
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
    
    public boolean alertaConfirmarActualizacion(String tituloLibro){
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
        alertInfo.setTitle("Cambios realizados");
        alertInfo.setHeaderText("Cambios realizados correctamente");
        alertInfo.setContentText("Los cambios implementados en " + tituloLibro + " se realizaron de manera correcta.");
        alertInfo.showAndWait();
        return true;
    }
}
