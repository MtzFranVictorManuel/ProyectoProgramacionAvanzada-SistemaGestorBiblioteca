/*
Nombre de Archivo: Constantes.java
Descripción: Clase que contiene datos constantes y valores que pueden cambiar con el tiempo (variables).
             Util para el acceso global a datos de orden general.
Autor: Eduardo Antonio Castillo Garrido
Fecha de moficiación: 13/12/2021
*/

package proyectoconstruccionbiblioteca.util;

public class Constantes {
    
    //Errores
    public static final int CODIGO_ERROR_CONEXION_BD = 403;
    
    public static final int CODIGO_RECURSO_EXISTE = 200;
    public static final int CODIGO_RECURSO_NOEXISTE = 220;
    
    public static final int CODIGO_USUARIO_EXISTE = 300;
    public static final int CODIGO_USUARIO_NOEXISTE = 330;
    
    //Valores
    public static final int VALOR_MULTA_POR_ATRASO = 1;
    public static final int VALOR_RENOVACIONES_MAXIMAS = 2;
    public static final int VALOR_MONTO_LIMITE_MULTA = 200;
    public static final int VALOR_PRESTAMOS_MAXIMOS = 5;
    public static final int VALOR_ADEUDOS_MAXIMOS = 3;
}
