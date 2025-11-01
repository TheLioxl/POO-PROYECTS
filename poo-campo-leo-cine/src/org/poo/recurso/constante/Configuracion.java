package org.poo.recurso.constante;

import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.stage.Screen;

public class Configuracion {

    public static final int ALTO_APP = (int) (Screen.getPrimary().getBounds().getHeight() * 0.7);
    public static final int ANCHO_APP = (int) (Screen.getPrimary().getBounds().getWidth() * 0.6);

    private static final double CABECERA_ALTO_PORCENTAJE = 0.1;
    public static final double ALTO_CABECERA = CABECERA_ALTO_PORCENTAJE * ALTO_APP;

    public static final String MORADO_OSCURO = "#5B21B6";          // morado profundo para texto o cabecera
    public static final String MORADO_MEDIO = "#8B5CF6";           // tono intermedio vibrante pero elegante
    public static final String MORADO_CLARO = "#C4B5FD";           // morado pastel medio
    public static final String MORADO_SUPER_CLARO = "#E9D5FF";     // lavanda claro para fondos suaves
    public static final String MORADO_PURPURA = "#A78BFA";         // violeta cálido intermedio
    public static final String MORADO_PURPURA_CLARO = "#DDD6FE";   // tono lavanda neutro claro

    public static final String CABECERA_ESTILO_FONDO = String.format(
            "-fx-background-color: linear-gradient(to bottom, %s, %s, %s);",
            MORADO_MEDIO, MORADO_PURPURA, MORADO_CLARO);

    public static final double MARCO_ALTO_PORCENTAJE = 0.75;
    public static final double MARCO_ANCHO_PORCENTAJE = 0.80; 

    public static final double GRILLA_ANCHO_PORCENTAJE = 0.7; 

    public static final String DEGRADE_BORDE = "#8B5CF6";

    public static final Stop[] DEGRADE_ARREGLO_GENERO = new Stop[]{
        new Stop(0.0, Color.web(MORADO_SUPER_CLARO, 1.0)),
        new Stop(0.3, Color.web(MORADO_CLARO, 0.9)),
        new Stop(0.6, Color.web(MORADO_PURPURA_CLARO, 0.8)),
        new Stop(1.0, Color.web(MORADO_SUPER_CLARO, 1.0))
    };

    public static final Stop[] DEGRADE_ARREGLO_PELICULA = new Stop[]{
        new Stop(0.0, Color.web(MORADO_PURPURA_CLARO, 1.0)),
        new Stop(0.3, Color.web(MORADO_PURPURA, 0.9)),
        new Stop(0.6, Color.web(MORADO_MEDIO, 0.8)),
        new Stop(1.0, Color.web(MORADO_CLARO, 0.9))
    };

    public static final String ICONO_BORRAR = "iconoBorrar.png";
    public static final String ICONO_EDITAR = "iconoEditar.png";
    public static final String ICONO_CANCELAR = "iconoCancelar.png";
    public static final String ICONO_NO_DISPONIBLE = "imgNoDisponible.png";
}
