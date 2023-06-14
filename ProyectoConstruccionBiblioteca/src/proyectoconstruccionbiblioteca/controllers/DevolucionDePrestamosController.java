/*
Nombre de Archivo: DevolucionDePrestamosController.java
Descripción: El controlador logico de la pantalla "Devolución de Préstamos" (DevolucionDePrestamos.fxml)
Autor: Eduardo Antonio Castillo Garrido
Fecha de moficiación: 15/12/2021
*/
package proyectoconstruccionbiblioteca.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import proyectoconstruccionbiblioteca.dao.PrestamoDAO;
import proyectoconstruccionbiblioteca.dao.UsuarioDAO;
import proyectoconstruccionbiblioteca.objetos.Prestamo;
import proyectoconstruccionbiblioteca.objetos.Usuario;
import proyectoconstruccionbiblioteca.util.Constantes;

public class DevolucionDePrestamosController implements Initializable {
    
    @FXML
    private Label lbNombreUsuario;
    @FXML
    private Label txtAviso;
    @FXML
    private TextField tfBuscarPrestamo;
    @FXML
    private TableView<Prestamo> tbPrestamosUsuario;
    @FXML
    private TextField tfBuscarUsuario;
    @FXML
    private TableColumn colTitulo;
    @FXML
    private TableColumn colAutor;
    @FXML
    private TableColumn colFechaPrestamo;
    @FXML
    private TableColumn colFechaVencimiento;
    @FXML
    private TableColumn colEstado;
    @FXML
    private TableColumn colRenovaRest;
    
    private ObservableList<Prestamo> prestamosUsuario;
    private Optional<ButtonType> botonSeleccionado;
    private Usuario usuario;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarComponentes();
    }    
    
    private void inicializarComponentes(){
        configurarTablaPrestamos();
        buscarPrestamo();
        limitarTamañoTFBuscarUsuario();
        limitarTamañoTFBuscarPrestamo();
    }
    
    private void configurarTablaPrestamos(){
        prestamosUsuario = FXCollections.observableArrayList();
        this.colTitulo.setCellValueFactory(new PropertyValueFactory("titulo"));
        this.colAutor.setCellValueFactory(new PropertyValueFactory("autor"));
        this.colFechaPrestamo.setCellValueFactory(new PropertyValueFactory("fechaPrestamo"));
        this.colFechaVencimiento.setCellValueFactory(new PropertyValueFactory("fechaVencimiento"));
        this.colEstado.setCellValueFactory(new PropertyValueFactory("estado"));
        this.colRenovaRest.setCellValueFactory(new PropertyValueFactory("renovaRest"));
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
    
    public void limitarTamañoTFBuscarPrestamo(){
        tfBuscarPrestamo.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {             
                if(tfBuscarPrestamo.getText().length()>12){
                    String buscarPrestamo = tfBuscarPrestamo.getText().substring(0, 12);
                    tfBuscarPrestamo.setText(buscarPrestamo);
                    mostrarAlerta("Recuperar Préstamo", "Limite de caracteres excedido", Alert.AlertType.ERROR);
                    tfBuscarPrestamo.setText("");
                }
            }               
        }); 
    }

    @FXML
    private void clicBuscarUsuario(ActionEvent event) {
        if(tfBuscarUsuario.getText().isEmpty()){
             mostrarAlerta("Buscar Usuario", "Ingrese primero el identificador del usuario (matricula o  número de personal)", Alert.AlertType.INFORMATION);
        }else{
            String identificador = tfBuscarUsuario.getText();
                     
            if(identificador.contains("S")){
                System.out.println("El identificador es de un estudiante");
                usuario = UsuarioDAO.recuperarEstudiante(identificador);
            }else{
                System.out.println("El identificador es de un personal academico");
                usuario = UsuarioDAO.recuperarPersonalAcademico(identificador);
            }

            int codigoUsuario = usuario.getCodigo();
            switch (codigoUsuario) {
                case Constantes.CODIGO_USUARIO_EXISTE:
                    colocarDatosUsuario();
                    tfBuscarUsuario.setDisable(true);
                    tfBuscarPrestamo.setDisable(false);
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
    
    public void buscarPrestamo(){
        tfBuscarPrestamo.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String codigoBarras = tfBuscarPrestamo.getText();
                if(!codigoBarras.isEmpty()){
                    recuperarYAgregarPrestamo(codigoBarras);
                }else{
                    mostrarAlerta("Buscar Prestamo", "Escanee o ingrese el código de barras del recurso documental préstado", Alert.AlertType.INFORMATION);
                }
                tfBuscarPrestamo.setText("");
            }
        });
    }
    
    private void colocarDatosUsuario(){
        if(tfBuscarUsuario.getText().contains("S")){
            lbNombreUsuario.setText(usuario.getNombre()+" "+usuario.getApellidos()+" ("+usuario.getMatricula()+")");
        }else{
            lbNombreUsuario.setText(usuario.getNombre()+" "+usuario.getApellidos()+" ("+usuario.getNumPersonal()+")");
        }
    }
    
    private void recuperarYAgregarPrestamo(String codigoBarras){
        Prestamo prestamo = PrestamoDAO.recuperarPrestamoUsuario(usuario.getIdUsuario(), codigoBarras);
        if(prestamo != null){
            boolean existe = verificarSiExiste(prestamo);
            if(existe){
                mostrarAlerta("Prestamos", "El ítem ya se agregó a la lista", Alert.AlertType.INFORMATION);
            }else{
                prestamosUsuario.add(prestamo);
            }
            System.out.println("Num préstamos: "+prestamosUsuario.size());
            actualizarTablaPrestamos();
        }
    }
    
    private boolean verificarSiExiste(Prestamo prestamo){
        boolean existe = false;
        for(int i = 0; i < prestamosUsuario.size(); i++){
            if(prestamo.getTitulo().equals(prestamosUsuario.get(i).getTitulo())){
                existe = true;
            }
        }
        return existe;
    }
    
    private void actualizarTablaPrestamos(){
        if(!prestamosUsuario.isEmpty()){
            tbPrestamosUsuario.setItems(prestamosUsuario);
        }else if(prestamosUsuario.isEmpty()){
            System.out.println("No hay prestamos nuevos");
        }
    }
    
    @FXML
    private void clicLimpiarTodo(ActionEvent event) {
        tbPrestamosUsuario.getItems().clear();
        lbNombreUsuario.setText("Ingrese el identificador del usuario");
        tfBuscarUsuario.setText("");
        tfBuscarPrestamo.setText("");
        tfBuscarUsuario.setDisable(false);
        tfBuscarPrestamo.setDisable(true);
    }
    
    @FXML
    private void clicDevolver(ActionEvent event) {
        if(tbPrestamosUsuario.getItems().isEmpty()){
            mostrarAlerta("Devolver", "Debe ingresar al menos un ítem a devolver", Alert.AlertType.ERROR);
        }else{
            mostrarAlertaConfirmacion("Devolver", "Se devolverán los préstamos de la tabla ¿Continuar?", Alert.AlertType.INFORMATION);
            if(botonSeleccionado.get() == ButtonType.OK){
                ArrayList<Prestamo> prestamosADevolver = new ArrayList<>();
                prestamosADevolver.addAll(prestamosUsuario);
                boolean respuesta = PrestamoDAO.actualizarPrestamosDevolver(prestamosADevolver);
                if(respuesta){
                    actualizarNumAdeudos();
                    mostrarAlerta("Devolución", "Se devolvierón los préstamos con exito", Alert.AlertType.INFORMATION);
                    tbPrestamosUsuario.getItems().clear();
                    actualizarTablaPrestamos();
                }else{
                    mostrarAlerta("Devolución", "No se devolvierón los préstamos", Alert.AlertType.ERROR);
                }
            }
        }
    }
    
    private void actualizarNumAdeudos(){
        int numAdeudosNuevo = usuario.getNumAdeudos() - prestamosUsuario.size();
        boolean respuesta = UsuarioDAO.actualizarUsuarioNumAdeudos(numAdeudosNuevo, usuario.getIdUsuario());
        if(respuesta){
            System.out.println("Se actualizó el numero de adeudos");
        }else{
            System.out.println("No se actualizó el número de adeudos");
        }
    }
    
    @FXML
    private void clicRenovar(ActionEvent event) {
        if(tbPrestamosUsuario.getItems().isEmpty()){
            mostrarAlerta("Devolver", "Debe ingresar al menos un ítem para renovar", Alert.AlertType.ERROR);
        }else{
            mostrarAlerta("Renovar", "Abriendo pantalla de renovación de préstamos", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void clicSalir(ActionEvent event) {
        if(!tbPrestamosUsuario.getItems().isEmpty()){
            mostrarAlertaConfirmacion("Salir", "¿Desea cancelar las devoluciones?", Alert.AlertType.CONFIRMATION);
            if(botonSeleccionado.get() == ButtonType.OK){
                cerrarVentana();
            }
        } else { cerrarVentana(); }
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
        Stage escenarioActual = (Stage) tfBuscarPrestamo.getScene().getWindow();
        escenarioActual.close();
    }
}
