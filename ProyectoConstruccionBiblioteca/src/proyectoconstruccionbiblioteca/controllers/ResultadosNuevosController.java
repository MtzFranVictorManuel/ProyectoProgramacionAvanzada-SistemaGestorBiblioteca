/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoconstruccionbiblioteca.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import proyectoconstruccionbiblioteca.dao.ConstantesRecursoDocumental;
import proyectoconstruccionbiblioteca.dao.RecursoDocumentalDAO;
import proyectoconstruccionbiblioteca.objetos.RecursoDocumental;


/**
 * FXML Controller class
 *
 * @author WIN 10
 */
public class ResultadosNuevosController implements Initializable {
 RecursoDocumentalDAO documentalDAO = new RecursoDocumentalDAO();
 ConstantesRecursoDocumental documentalConstants = new ConstantesRecursoDocumental();
    
    @FXML
    private TextField tfBuscar;
    @FXML
    private TableColumn tcTitulo;
    @FXML
    private TableColumn tcTema;
    @FXML
    private TableColumn tcCantidad;
    @FXML
    private ComboBox cbTipoDeCategoria;
    @FXML
    private TableView<RecursoDocumental> tableViewRecursoDocumental;
    @FXML
    private Pane panelProteccion;
    private ObservableList<RecursoDocumental> documentalList;

    private String SQLQuery = "";
    private String valueSearch = ""; 
    private String tipoMaterial = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SQLQuery = ConstantesRecursoDocumental.SQL_SELECT;
        setTableView();
        tableViewRecursoDocumental.setItems(documentalDAO.select(documentalList, SQLQuery));
        panelProteccion.setVisible(false);
        ObservableList<String> opciones = FXCollections.observableArrayList("Titulo","Tema");
        cbTipoDeCategoria.setItems(opciones);
    }    

    @FXML
    private void clicBuscar(ActionEvent event) {
        panelProteccion.setVisible(false);
        String categoria = cbTipoDeCategoria.getSelectionModel().getSelectedItem().toString();
        String barraBusqueda = tfBuscar.getText();
         setTableView();
        valueSearch = tfBuscar.getText();
        if(!barraBusqueda.isEmpty() & categoria.equals("Titulo")){
            SQLQuery = ConstantesRecursoDocumental.SQL_SELECT_TITULO;
            tableViewRecursoDocumental.setItems(documentalDAO.select(documentalList, barraBusqueda, SQLQuery));
        }
        if(!barraBusqueda.isEmpty() & categoria.equals("Tema")){
             SQLQuery = ConstantesRecursoDocumental.SQL_SELECT_TEMA;
             tableViewRecursoDocumental.setItems(documentalDAO.select(documentalList, barraBusqueda, SQLQuery));
        }
        
    }
    public void setTableView() {
        documentalList = FXCollections.observableArrayList();
        this.tcTitulo.setCellValueFactory(new PropertyValueFactory("titulo"));
        this.tcCantidad.setCellValueFactory(new PropertyValueFactory("numCopias"));
        this.tcTema.setCellValueFactory(new PropertyValueFactory("tema"));
    }
 
}

