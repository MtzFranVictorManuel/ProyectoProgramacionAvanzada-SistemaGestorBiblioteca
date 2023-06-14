/*
Nombre de Archivo: PrestamosADomicilioController.java
Descripción: El controlador logico de la pantalla "Préstamos a Domicilio" (PrestamosADomicilio.fxml)
Autor: Eduardo Antonio Castillo Garrido
Fecha de moficiación: 12/12/2021
*/

package proyectoconstruccionbiblioteca.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyectoconstruccionbiblioteca.dao.PrestamoDAO;
import proyectoconstruccionbiblioteca.dao.RecursoDocumentalDAO;
import proyectoconstruccionbiblioteca.dao.UsuarioDAO;
import proyectoconstruccionbiblioteca.objetos.Prestamo;
import proyectoconstruccionbiblioteca.objetos.RecursoDocumental;
import proyectoconstruccionbiblioteca.objetos.Usuario;
import proyectoconstruccionbiblioteca.util.Constantes;


public class PrestamosADomicilioController implements Initializable {
    
    @FXML
    private Label lbFechaDelSistema;
    @FXML
    private TextField tfBuscarUsuario;
    @FXML
    private Button btnBuscarUsuario;
    @FXML
    private Label lbNombreUsuario;
    @FXML
    private Label lbDireccionUsuario;
    @FXML
    private Label lbEmailUsuario;
    @FXML
    private Label lbNumAdeudos;
    @FXML
    private Label lbMontoTotal;
    @FXML
    private TextField tfBuscarRD;
    
    @FXML
    private TableView<Prestamo> tbPrestamosNuevos;
    @FXML
    private TableColumn colTitulo;
    @FXML
    private TableColumn colAutor;
    @FXML
    private TableColumn colFechaPrestamo;
    @FXML
    private TableColumn colFechaVencimiento;
    
    @FXML
    private TableView<Prestamo> tbPrestamosUsuario;
    @FXML
    private TableColumn colTitulo_TbPrestamos;
    @FXML
    private TableColumn colAutor_TbPrestamos;
    @FXML
    private TableColumn colFechaPrestamo_TbPrestamos;
    @FXML
    private TableColumn colFechaVencimiento_TbPrestamos;
    @FXML
    private TableColumn colEstado_TbPrestamos;
    
    private ObservableList<Prestamo> prestamosUsuario;
    private ObservableList<Prestamo> prestamosNuevos;
    
    private Optional<ButtonType> botonSeleccionado;
    private Usuario usuarioAPrestar; 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {     
        inicializarComponentes();
    }
    
    public void inicializarComponentes(){
        lbFechaDelSistema.setText("Fecha: "+LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        buscarRecursoDocumental();
        limitarTamañoTFBuscarUsuario();
        limitarTamañoTFCodigoBarras();
        configurarElementosTablaPrestamosNuevos();
        configurarElementosTablaPrestamosUsuario();
    }
    
    private void configurarElementosTablaPrestamosNuevos(){
        prestamosNuevos = FXCollections.observableArrayList();
        this.colTitulo.setCellValueFactory(new PropertyValueFactory("titulo"));
        this.colAutor.setCellValueFactory(new PropertyValueFactory("autor"));
        this.colFechaPrestamo.setCellValueFactory(new PropertyValueFactory("fechaPrestamo"));
        this.colFechaVencimiento.setCellValueFactory(new PropertyValueFactory("fechaVencimiento"));
    }
    
    private void configurarElementosTablaPrestamosUsuario(){
        prestamosUsuario = FXCollections.observableArrayList();
        this.colTitulo_TbPrestamos.setCellValueFactory(new PropertyValueFactory("titulo"));
        this.colAutor_TbPrestamos.setCellValueFactory(new PropertyValueFactory("autor"));
        this.colFechaPrestamo_TbPrestamos.setCellValueFactory(new PropertyValueFactory("fechaPrestamo"));
        this.colFechaVencimiento_TbPrestamos.setCellValueFactory(new PropertyValueFactory("fechaVencimiento"));
        this.colEstado_TbPrestamos.setCellValueFactory(new PropertyValueFactory("estado"));
    }
    
    public void limitarTamañoTFBuscarUsuario(){
        tfBuscarUsuario.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {             
                if(tfBuscarUsuario.getText().length()>20){
                    String buscarUsuario = tfBuscarUsuario.getText().substring(0, 20);
                    tfBuscarUsuario.setText(buscarUsuario);
                    mostrarAlerta("Buscar Usuario", "Limite de caracteres excedido", Alert.AlertType.ERROR);
                    tfBuscarUsuario.setText("");
                }
            }               
        }); 
    }
    
    public void limitarTamañoTFCodigoBarras(){
       tfBuscarRD.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {             
                if(tfBuscarRD.getText().length()>12){
                    String buscarRD = tfBuscarRD.getText().substring(0, 12);
                    tfBuscarRD.setText(buscarRD);
                    mostrarAlerta("Buscar Recurso Documental", "Limite de caracteres excedido", Alert.AlertType.ERROR);
                    tfBuscarRD.setText("");
                }
            }               
        });  
    }
    
    public void buscarRecursoDocumental(){
        tfBuscarRD.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String codigoBarras = tfBuscarRD.getText();
                if(!codigoBarras.isEmpty()){
                    recuperarYAgregarPrestamoNuevo(codigoBarras);
                }else{
                    mostrarAlerta("Buscar Recurso Documental", "Escanee o ingrese el código de barras del recurso documental", Alert.AlertType.INFORMATION);
                }
                tfBuscarRD.setText("");
            }
        });
    }
    
    private void recuperarYAgregarPrestamoNuevo(String codigoBarras){
        RecursoDocumental recursoDocumental = RecursoDocumentalDAO.obtenerRecursoDocumental(codigoBarras);
        int codigo = recursoDocumental.getCodigo();
        if(codigo == Constantes.CODIGO_RECURSO_NOEXISTE){
            mostrarAlerta("Buscar Recurso Documental", "No se encontró el Recurso Documental, verifique el código de barras", Alert.AlertType.ERROR);
        }else if(codigo == Constantes.CODIGO_RECURSO_EXISTE){
            Prestamo prestamoNuevo = convertirAPrestamo(recursoDocumental);
            boolean existe = verificarSiExiste(prestamoNuevo);
            if(!existe){
                prestamosNuevos.add(prestamoNuevo);
            }else{
                System.out.println("No se agregó el prestamo por que ya fue prestado o ya esta en la lista");
            }    
            System.out.println("Numero de elementos en la lista: "+ prestamosNuevos.size());
            actualizarTablaPrestamosNuevos();
        }
    }

    private Prestamo convertirAPrestamo(RecursoDocumental recursoDocumental) {
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaVence = fechaActual.plusDays(4);

        Date fechaPrestamo = Date.valueOf(fechaActual);
        Date fechaVencimiento = Date.valueOf(fechaVence);

        Prestamo prestamoNuevo = new Prestamo();
        prestamoNuevo.setTitulo(recursoDocumental.getTitulo());
        prestamoNuevo.setAutor(recursoDocumental.getAutor());
        prestamoNuevo.setFechaPrestamo(fechaPrestamo);
        prestamoNuevo.setFechaVencimiento(fechaVencimiento);
        prestamoNuevo.setEstado("En Curso");
        prestamoNuevo.setRenovaRest(Constantes.VALOR_RENOVACIONES_MAXIMAS);
        prestamoNuevo.setCargoMulta(Constantes.VALOR_MULTA_POR_ATRASO);
        prestamoNuevo.setIdRecursoDocumental(recursoDocumental.getIdRecursoDocumental());
        prestamoNuevo.setIdUsuario(usuarioAPrestar.getIdUsuario());

        return prestamoNuevo;   
    }
    
    private boolean verificarSiExiste(Prestamo prestamoNuevo){
        boolean existe = false;
        for(int i = 0; i < prestamosUsuario.size(); i++){
            if(prestamoNuevo.getTitulo().equals(prestamosUsuario.get(i).getTitulo())){
                mostrarAlerta("Prestamos", "El ítem ya fue prestado al usuario ¿Desea renovarlo?", Alert.AlertType.CONFIRMATION);
                if(botonSeleccionado.get().equals(ButtonType.OK)){
                    irPantallaDevoluciones();
                }
                existe = true;
            }
        }
        for(int i = 0; i < prestamosNuevos.size(); i++){
            if(prestamoNuevo.getTitulo().equals(prestamosNuevos.get(i).getTitulo())){
                mostrarAlerta("Prestamos", "El ítem ya esta en la lista", Alert.AlertType.ERROR);
                existe = true;
            }
        }
        return existe;
    }
    
    private void actualizarTablaPrestamosNuevos() {
        if(!prestamosNuevos.isEmpty()){
            tbPrestamosNuevos.setItems(prestamosNuevos);
        }else if(prestamosNuevos.isEmpty()){
            System.out.println("No hay prestamos nuevos");
        }
    }
    
    @FXML
    private void clicBuscarUsuario(ActionEvent event) {
        if(tfBuscarUsuario.getText().isEmpty()){
             mostrarAlerta("Buscar Usuario", "Ingrese primero el identificador del usuario (matricula o  número de personal)", Alert.AlertType.INFORMATION);
        }else{
            String identificador = tfBuscarUsuario.getText();
                     
            if(identificador.contains("S")){
                System.out.println("El identificador es de un estudiante");
                usuarioAPrestar = UsuarioDAO.recuperarEstudiante(identificador);
            }else{
                System.out.println("El identificador es de un personal academico");
                usuarioAPrestar = UsuarioDAO.recuperarPersonalAcademico(identificador);
            }

            int codigoUsuario = usuarioAPrestar.getCodigo();
            switch (codigoUsuario) {
                case Constantes.CODIGO_USUARIO_EXISTE:
                    boolean vigente = validarVigenciaUsuario();
                    if(vigente){
                        btnBuscarUsuario.setDisable(true);
                        tfBuscarRD.setDisable(false);
                        colocarDatosUsuario();
                        cargaPrestamosUsuario();
                    }
                    break;
                case Constantes.CODIGO_USUARIO_NOEXISTE:
                    mostrarAlerta("Buscar Usuario", "No se encontró el usuario, verifique el dato ingresado", Alert.AlertType.ERROR);
                    break;
                case Constantes.CODIGO_ERROR_CONEXION_BD:
                    mostrarAlerta("Error de conexion", "Se perdió la conexión con la base de datos, no se recuperó el usuario", Alert.AlertType.ERROR);
                    break;
                default:
                    break;
            }
        }
    }
    
    private boolean validarVigenciaUsuario(){
        boolean vigente = false;
        if("Vigente".equals(usuarioAPrestar.getEstado())){
            System.out.println("Usuario Vigente");
            vigente = true;
        }else if("No Vigente".equals(usuarioAPrestar.getEstado())){
            mostrarAlerta("No se puede préstar", "El usuario no tiene vigencia, se bloquearón los préstamos", Alert.AlertType.ERROR);
            tfBuscarRD.setDisable(true);
        }
        return vigente;
    }
    
    private void colocarDatosUsuario(){
        lbNombreUsuario.setText(usuarioAPrestar.getNombre()+" "+usuarioAPrestar.getApellidos());
        lbDireccionUsuario.setText(usuarioAPrestar.getDireccion());
        lbEmailUsuario.setText(usuarioAPrestar.getEmail());
        lbNumAdeudos.setText(String.valueOf(usuarioAPrestar.getNumAdeudos()));
        lbMontoTotal.setText(String.valueOf(usuarioAPrestar.getMontoTotal()));
        
    }

    private void cargaPrestamosUsuario(){
        ArrayList<Prestamo> prestamosUsuarioBD = PrestamoDAO.obtenerInfoPrestamos(usuarioAPrestar.getIdUsuario());
        if(prestamosUsuarioBD != null){
            prestamosUsuario.addAll(prestamosUsuarioBD);
            tbPrestamosUsuario.setItems(prestamosUsuario);
            
            if(prestamosUsuarioBD.size() >= Constantes.VALOR_PRESTAMOS_MAXIMOS){
                tfBuscarRD.setDisable(true);
                btnBuscarUsuario.setDisable(true);
                mostrarAlertaConfirmacion("No se puede prestar", "El usuario llegó al maximo de préstamos permitidos ¿Desea Devolver?", Alert.AlertType.CONFIRMATION);
                if(botonSeleccionado.get() == ButtonType.OK){
                    irPantallaDevoluciones();
                }
            }
        }else{
            mostrarAlerta("Error", "No hay préstamos asociados al usuario", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void clicPrestar(ActionEvent event) {
        if(tbPrestamosNuevos.getItems().isEmpty()){
            mostrarAlerta("Prestar", "Agregue al menos un recurso documental a prestar", Alert.AlertType.ERROR);
        }else{
            if(prestamosUsuario.size() + prestamosNuevos.size() > Constantes.VALOR_PRESTAMOS_MAXIMOS){
                mostrarAlerta("Prestar", "Solo se permiten 5 prestamos por usuario\nPrestamos restantes para el usuario: "+ (Constantes.VALOR_PRESTAMOS_MAXIMOS - prestamosUsuario.size()), Alert.AlertType.ERROR);
            }else{
                mostrarAlertaConfirmacion("Prestar", "Se préstarán los ítems de la tabla al usuario: "+ usuarioAPrestar.getNombre()+" "+usuarioAPrestar.getApellidos()+" ¿Continuar?", Alert.AlertType.CONFIRMATION);
                if(botonSeleccionado.get() == ButtonType.OK){
                    ArrayList<Prestamo> prestamosAInsertar = new ArrayList<>();
                    prestamosAInsertar.addAll(prestamosNuevos);
                    
                    boolean respuesta = PrestamoDAO.insertarPrestamo(prestamosAInsertar);
                    if(respuesta){
                        actualizarNumAdeudos();
                        mostrarAlerta("Prestar", "Se guardarón los prestamos con exito", Alert.AlertType.INFORMATION);
                        tbPrestamosNuevos.getItems().clear();
                        actualizarTablaPrestamosUsuario();
                    }else{
                        mostrarAlerta("Prestar", "No se guardarón los prestamos", Alert.AlertType.ERROR);
                    }
                }else{
                    System.out.println("Boton Cancelar Seleccionado");
                }
            }
        }  
    }
    
    private void actualizarNumAdeudos(){
        int numAdeudosNuevo = prestamosUsuario.size() + prestamosNuevos.size();
        boolean respuesta = UsuarioDAO.actualizarUsuarioNumAdeudos(numAdeudosNuevo, usuarioAPrestar.getIdUsuario());
        if(respuesta){
            //lbNumAdeudos.setText(usuarioAPrestar.getNumAdeudos());
            System.out.println("Se actualizó el numero de adeudos");
        }else{
            System.out.println("No se actualizó el número de adeudos");
        }
    }
    
    private void actualizarTablaPrestamosUsuario(){
        tbPrestamosUsuario.getItems().clear();
        cargaPrestamosUsuario();
    }

    @FXML
    private void clicLimpiarLista(ActionEvent event) {
        mostrarAlertaConfirmacion("Limpiar Lista", "Se eliminarán los ítems de la lista ¿Continuar?", Alert.AlertType.WARNING);
        if(botonSeleccionado.get() == ButtonType.OK){limpiarLista();}
    }

    @FXML
    private void clicLimpiarTodo(ActionEvent event) {
        mostrarAlertaConfirmacion("Limpiar Todo", "Se limpiarán todos los datos del usuario y los préstamos a realizar ¿Continuar?", Alert.AlertType.WARNING);
        if(botonSeleccionado.get() == ButtonType.OK){
            tfBuscarUsuario.setText("");
            tfBuscarRD.setText("");
            btnBuscarUsuario.setDisable(false);
            tfBuscarRD.setDisable(true);
            limpiarLista();
            limpiarDatosUsuario();
            
            tbPrestamosUsuario.getItems().clear();
            actualizarTablaPrestamosNuevos();
        }
    }
    
    private void limpiarLista(){
        System.out.println("Limpiando Lista");
        if(tbPrestamosNuevos.getItems().isEmpty()){
            System.out.println("No hay ítems en la tabla");
        }else{
            tbPrestamosNuevos.getItems().clear();
            prestamosNuevos.clear();
        }
    }
    
    private void limpiarDatosUsuario(){
        lbNombreUsuario.setText("Nombre del usuario");
        lbDireccionUsuario.setText("Dirección del usuario");
        lbEmailUsuario.setText("Email del usuario");
        lbNumAdeudos.setText("-");
        lbMontoTotal.setText("-");
    }
    
    @FXML
    private void clicQuitarItem(ActionEvent event) {
        int prestamoAEliminar = tbPrestamosNuevos.getSelectionModel().getSelectedIndex();
        if(prestamoAEliminar >= 0){
            prestamosNuevos.remove(prestamoAEliminar);
            actualizarTablaPrestamosNuevos();
        }else{
            mostrarAlerta("Quitar ítem", "Para quitar un ítem debe seleccionarlo primero", Alert.AlertType.INFORMATION);
        }       
    }
    
    @FXML
    private void clicSalir(ActionEvent event) {
       if(!tbPrestamosNuevos.getItems().isEmpty()){
            mostrarAlertaConfirmacion("Salir", "Hay ítems aun no préstados ¿Desea Salir?", Alert.AlertType.NONE);
            if(botonSeleccionado.get() == ButtonType.OK){
                cerrarVentana();
            }else{
                System.out.println("Boton Cancelar Seleccionado");
            }
       }else{ cerrarVentana(); }
    }
    
    
    private void irPantallaDevoluciones(){
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
        }
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo){
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }
    
    private void mostrarAlertaConfirmacion(String titulo, String mensaje, Alert.AlertType tipo){
        Alert dialogoConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        dialogoConfirmacion.setTitle(titulo);
        dialogoConfirmacion.setHeaderText(null);
        dialogoConfirmacion.setContentText(mensaje);
        botonSeleccionado = dialogoConfirmacion.showAndWait();
    }

    private void cerrarVentana() {
        Stage escenarioActual = (Stage) lbFechaDelSistema.getScene().getWindow();
        escenarioActual.close();
    }

}
