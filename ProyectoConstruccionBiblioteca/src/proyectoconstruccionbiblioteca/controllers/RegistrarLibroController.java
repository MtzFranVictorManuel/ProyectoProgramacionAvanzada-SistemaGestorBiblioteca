package proyectoconstruccionbiblioteca.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
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
public class RegistrarLibroController implements Initializable {
    LibroDAO librodao = new LibroDAO();
    RecursoDocumentalDAO documentaldao = new RecursoDocumentalDAO();
    RecursoDocumental documental = new RecursoDocumental();
    
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
    private DatePicker datePickerFechaPublicacion;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        formatoTextFields();
    }
        
    public void clicSalir(ActionEvent actionEvent){
        navigationScreen("fxml/InicioRegistroModificar.fxml");       
    }
    
    public void clicRegistrar(ActionEvent actionEvent){       
        try{
            String codigoBarra = textFieldCodigoBarras.getText();
            String tituloLibro = textFieldTitulo.getText();
            String tipoMaterial = "libro";
            int idRecursoDocumental;
            Date fechaPublicacion = Date.valueOf(datePickerFechaPublicacion.getValue()); 
            datosCorrectos(true);
            if(textFieldAutor.getText().isEmpty() || textFieldClasificacionLC.getText().isEmpty() || textFieldCodigoBarras.getText().isEmpty()
                    || textFieldDescripcion.getText().isEmpty() || textFieldEdicion.getText().isEmpty() || textFieldEditor.getText().isEmpty()
                    || textFieldIdioma.getText().isEmpty() || textFieldIsbn.getText().isEmpty() || textFieldSerie.getText().isEmpty()
                    || textFieldTemas.getText().isEmpty() || textFieldTipoObra.getText().isEmpty() || textFieldTitulo.getText().isEmpty()
                    || textFieldVolumen.getText().isEmpty()){
                datosErroneso(true);
                datosCorrectos(true);
                    Alert alertInfo = new Alert(Alert.AlertType.ERROR);
                    alertInfo.setTitle("Datos vacíos");
                    alertInfo.setContentText("Los datos ingresados son vacions, por favor de validar que los datos sean correctos.");
                    alertInfo.showAndWait();
            }else{
                datosCorrectos(true);
                if(documentaldao.selectCopiaExistes(codigoBarra, tituloLibro, tipoMaterial) == false){
                    documentaldao.insert(codigoBarra, textFieldAutor.getText(), tituloLibro, textFieldClasificacionLC.getText(), 
                            textFieldDescripcion.getText(), textFieldEditor.getText(), textFieldTemas.getText(), tipoMaterial, 1);
                    idRecursoDocumental = documentaldao.selectIdRecursoDocumental(tituloLibro, codigoBarra, textFieldAutor.getText());
                    if(idRecursoDocumental == 0){
                        Alert alertInfo = new Alert(Alert.AlertType.WARNING);
                        alertInfo.setTitle("Error");
                        alertInfo.setHeaderText("No se guardo el recurso documental");
                        alertInfo.setContentText("El recurso documental ingresado no fue guardado correctamente");
                        alertInfo.showAndWait();
                    }
                    int volumenLibro = Integer.parseInt(textFieldVolumen.getText());
                    Libro libroNuevo = new Libro();
                    libroNuevo.setEdicion(textFieldEdicion.getText());
                    libroNuevo.setIsbn(textFieldIsbn.getText());
                    libroNuevo.setFechaPublicacion(fechaPublicacion);
                    libroNuevo.setIdioma(textFieldIdioma.getText());
                    libroNuevo.setSerie(textFieldSerie.getText());
                    libroNuevo.setVolumen(volumenLibro);
                    libroNuevo.setTipoObraLiteraria(textFieldTipoObra.getText());
                    librodao.insertar(libroNuevo, idRecursoDocumental);
                    Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
                    alertInfo.setTitle("Datos ingresados correctamente");
                    alertInfo.setHeaderText("Los datos se guardaron correctamente");
                    alertInfo.setContentText("Los datos ingresados se guardaron de la manera correcta");
                    alertInfo.showAndWait();
                    limpiarCampos();
                }else{
                    if(alertaConfirmacion("Copia", "Recurso documental existente", 
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
    
    public void formatoTextFields(){
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
        textFieldIdioma.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                 if(!newValue.matches("\\sa-zA-Z*")){
                     textFieldVolumen.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
                 }               
            }               
        });
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
        datePickerFechaPublicacion.setValue(null);
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
