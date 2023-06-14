/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoconstruccionbiblioteca.dao;

/**
 *
 * @author azul_
 */
public class ConstantesRecursoDocumental {
    public static final String SQL_SELECT = "SELECT * FROM recursodocumental;";
    public static final String SQL_SELECT_TITULO = "SELECT * FROM recursodocumental WHERE titulo = ?;";
    public static final String SQL_SELECT_AUTOR = "SELECT * FROM recursodocumental WHERE autor = ?;";
    public static final String SQL_SELECT_EDITOR = "SELECT * FROM recursodocumental WHERE editor = ?;";
    public static final String SQL_SELECT_TEMA = "SELECT * FROM recursodocumental WHERE tema = ?;";
    public static final String SQL_SELECT_CODIGOBARRAS = "SELECT * FROM recursodocumental WHERE codigobarras = ?;";
}
