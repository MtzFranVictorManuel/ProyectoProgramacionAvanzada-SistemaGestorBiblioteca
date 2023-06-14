/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoconstruccionbiblioteca.dao;

import javafx.collections.ObservableList;
import proyectoconstruccionbiblioteca.objetos.RecursoDocumental;

/**
 *
 * @author azul_
 */
public interface IRecursoDocumentalDAO {
    public ObservableList<RecursoDocumental> select(ObservableList<RecursoDocumental> tableInfo, String SQLQuery);
    
    public ObservableList<RecursoDocumental> select(ObservableList<RecursoDocumental> tableInfo, String value, String SQLQuery);
    
    public boolean update(RecursoDocumental documental, int idRecursoDocumental);
    
    public boolean updateCopia(int idRecursoDocumental);
    
    public boolean selectCopiaExistes(String codigoBarras, String titulo, String tipoMaterial);
    
    public int insert(String codigoBarras, String autor, String titulo, String clasificacionLC, String descripcion, String editor, String tema, String tipoMaterial, int numCopias);
    
    public boolean delete(RecursoDocumental documental);
    
    public int selectIdRecursoDocumental(String titulo, String codigoBarras, String autor);
    
    public RecursoDocumental selectModificarRecursoDocumental(int idDocumental);
}
