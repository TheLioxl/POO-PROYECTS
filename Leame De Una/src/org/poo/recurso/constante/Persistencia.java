package org.poo.recurso.constante;

import java.io.File;

public class Persistencia {

    public static final String RUTA_PROYECTO = System.getProperty("user.dir");
    public static final String NOMBRE_BASE_DATOS_EDITORIAL = "editoriales.txt";
    public static final String NOMBRE_BASE_DATOS_AUTOR = "autores.txt";
    public static final String NOMBRE_BASE_DATOS_LIBRO = "libros.txt";
    public static final String NOMBRE_CARPETA = "miBaseDeDatos";
    public static final String NOMBRE_CARPETA_IMAGENES_EXTERNAS = "LasImagenes";
    public static final String NOMBRE_CARPETA_IMAGENES_INTERNAS = "/org/poo/recurso/imagenes/";
    
    public static final String SEPARADOR_COLUMNAS = ";";
    public static final String SEPARADOR_CARPETAS = File.separator;

    public static final String RUTA_IMAGENES = RUTA_PROYECTO + Persistencia.SEPARADOR_CARPETAS + NOMBRE_CARPETA_IMAGENES_EXTERNAS;
    
    public static final String NOMBRE_EDITORIAL = RUTA_PROYECTO + SEPARADOR_CARPETAS + NOMBRE_CARPETA + SEPARADOR_CARPETAS
            + NOMBRE_BASE_DATOS_EDITORIAL;

    public static final String NOMBRE_AUTOR = RUTA_PROYECTO + SEPARADOR_CARPETAS + NOMBRE_CARPETA + SEPARADOR_CARPETAS
            + NOMBRE_BASE_DATOS_AUTOR;
            
    public static final String NOMBRE_LIBRO = RUTA_PROYECTO + SEPARADOR_CARPETAS + NOMBRE_CARPETA + SEPARADOR_CARPETAS
            + NOMBRE_BASE_DATOS_LIBRO;
}