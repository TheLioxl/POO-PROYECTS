package org.poo.recurso.constante;

import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.stage.Screen;

public class Configuracion {

    // Tamaño de la aplicación
    public static final int ALTO_APP = (int) (Screen.getPrimary().getBounds().getHeight() * 0.7);
    public static final int ANCHO_APP = (int) (Screen.getPrimary().getBounds().getWidth() * 0.6);

    // Cabecera
    private static final double CABECERA_ALTO_PORCENTAJE = 0.1;
    public static final double ALTO_CABECERA = CABECERA_ALTO_PORCENTAJE * ALTO_APP;

    // Paleta de colores
    public static final String MORADO_OSCURO = "#5B21B6";
    public static final String MORADO_MEDIO = "#8B5CF6";
    public static final String MORADO_CLARO = "#C4B5FD";
    public static final String MORADO_SUPER_CLARO = "#E9D5FF";
    public static final String MORADO_PURPURA = "#A78BFA";
    public static final String MORADO_PURPURA_CLARO = "#DDD6FE";

    // Gradiente de la cabecera
    public static final String CABECERA_ESTILO_FONDO = String.format(
            "-fx-background-color: linear-gradient(to bottom, %s, %s, %s);",
            MORADO_MEDIO, MORADO_PURPURA, MORADO_CLARO);

    // Porcentajes de marco
    public static final double MARCO_ALTO_PORCENTAJE = 0.75;
    public static final double MARCO_ANCHO_PORCENTAJE = 0.80;

    // Ancho de grilla
    public static final double GRILLA_ANCHO_PORCENTAJE = 0.5;

    // Borde exterior
    public static final String DEGRADE_BORDE = "#8B5CF6";

    // Degradado para formularios de libros
    public static final Stop[] DEGRADE_ARREGLO_LIBRO = new Stop[]{
        new Stop(0.0, Color.web(MORADO_SUPER_CLARO, 1.0)),
        new Stop(0.3, Color.web(MORADO_CLARO, 0.9)),
        new Stop(0.6, Color.web(MORADO_PURPURA_CLARO, 0.8)),
        new Stop(1.0, Color.web(MORADO_SUPER_CLARO, 1.0))
    };

    // Degradado para tablas de editoriales
    public static final Stop[] DEGRADE_ARREGLO_EDITORIAL = new Stop[]{
        new Stop(0.0, Color.web(MORADO_PURPURA_CLARO, 1.0)),
        new Stop(0.3, Color.web(MORADO_PURPURA, 0.9)),
        new Stop(0.6, Color.web(MORADO_MEDIO, 0.8)),
        new Stop(1.0, Color.web(MORADO_CLARO, 0.9))
    };

    // Degradado para tablas de autores
    public static final Stop[] DEGRADE_ARREGLO_AUTOR = new Stop[]{
        new Stop(0.0, Color.web("#E9D5FF", 1.0)),
        new Stop(0.5, Color.web("#C4B5FD", 0.9)),
        new Stop(1.0, Color.web("#A78BFA", 0.85))
    };

    // Iconos
    public static final String ICONO_BORRAR = "iconoBorrar.png";
    public static final String ICONO_EDITAR = "iconoEditar.png";
    public static final String ICONO_CANCELAR = "iconoCancelar.png";
    public static final String ICONO_NO_DISPONIBLE = "imgNoDisponible.png";
}
