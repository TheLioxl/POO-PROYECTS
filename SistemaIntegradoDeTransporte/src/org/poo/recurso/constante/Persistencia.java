package org.poo.recurso.constante;

import java.io.File;

public class Persistencia {

    public static final String RUTA_PROYECTO = System.getProperty("user.dir");
    
    public static final String NOMBRE_BASE_DATOS_TERMINAL = "Terminal.txt";
    public static final String NOMBRE_BASE_DATOS_EMPRESA = "Empresa.txt";
    public static final String NOMBRE_BASE_DATOS_BUS = "Bus.txt";
    public static final String NOMBRE_BASE_DATOS_RUTA = "Ruta.txt";
    public static final String NOMBRE_BASE_DATOS_CONDUCTOR = "Conductor.txt";
    public static final String NOMBRE_BASE_DATOS_PASAJERO = "Pasajero.txt";
    public static final String NOMBRE_BASE_DATOS_VIAJE = "Viaje.txt";
    public static final String NOMBRE_BASE_DATOS_TIQUETE = "Tiquete.txt";
    public static final String NOMBRE_BASE_DATOS_DESTINO = "Destino.txt";
    
    public static final String NOMBRE_CARPETA = "miBaseDeDatos";
    public static final String NOMBRE_CARPETA_IMAGENES_EXTERNAS = "LasImagenes";
    public static final String NOMBRE_CARPETA_IMAGENES_INTERNAS = "/org/poo/recurso/imagenes/";
    
    public static final String SEPARADOR_COLUMNAS = ";";
    public static final String SEPARADOR_CARPETAS = File.separator;

    public static final String RUTA_IMAGENES = RUTA_PROYECTO + SEPARADOR_CARPETAS + NOMBRE_CARPETA_IMAGENES_EXTERNAS;
    public final static String RUTA_ESTILO_BTN_ACERCA = "/org/poo/recurso/estilo/BtnAcerca.css";
    
    public static final String NOMBRE_TERMINAL = RUTA_PROYECTO + SEPARADOR_CARPETAS + NOMBRE_CARPETA + SEPARADOR_CARPETAS + NOMBRE_BASE_DATOS_TERMINAL;
    public static final String NOMBRE_EMPRESA = RUTA_PROYECTO + SEPARADOR_CARPETAS + NOMBRE_CARPETA + SEPARADOR_CARPETAS + NOMBRE_BASE_DATOS_EMPRESA;
    public static final String NOMBRE_BUS = RUTA_PROYECTO + SEPARADOR_CARPETAS + NOMBRE_CARPETA + SEPARADOR_CARPETAS + NOMBRE_BASE_DATOS_BUS;
    public static final String NOMBRE_RUTA = RUTA_PROYECTO + SEPARADOR_CARPETAS + NOMBRE_CARPETA + SEPARADOR_CARPETAS + NOMBRE_BASE_DATOS_RUTA;
    public static final String NOMBRE_CONDUCTOR = RUTA_PROYECTO + SEPARADOR_CARPETAS + NOMBRE_CARPETA + SEPARADOR_CARPETAS + NOMBRE_BASE_DATOS_CONDUCTOR;
    public static final String NOMBRE_PASAJERO = RUTA_PROYECTO + SEPARADOR_CARPETAS + NOMBRE_CARPETA + SEPARADOR_CARPETAS + NOMBRE_BASE_DATOS_PASAJERO;
    public static final String NOMBRE_VIAJE = RUTA_PROYECTO + SEPARADOR_CARPETAS + NOMBRE_CARPETA + SEPARADOR_CARPETAS + NOMBRE_BASE_DATOS_VIAJE;
    public static final String NOMBRE_TIQUETE = RUTA_PROYECTO + SEPARADOR_CARPETAS + NOMBRE_CARPETA + SEPARADOR_CARPETAS + NOMBRE_BASE_DATOS_TIQUETE;
    public static final String NOMBRE_DESTINO = RUTA_PROYECTO + SEPARADOR_CARPETAS + NOMBRE_CARPETA + SEPARADOR_CARPETAS + NOMBRE_BASE_DATOS_DESTINO;
}