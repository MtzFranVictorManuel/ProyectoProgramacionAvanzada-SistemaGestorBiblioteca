/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoconstruccionbiblioteca.dao;

import proyectoconstruccionbiblioteca.objetos.Libro;

/**
 *
 * @author azul_
 */
public interface ILibroDAO {
    public boolean insertar (Libro libro, int fkDocumentoID);
    
    public Libro seleccionarLibro (int idRecursoDocumental);
    
    public boolean update(Libro libro, int idRecursoDocumental);
}
