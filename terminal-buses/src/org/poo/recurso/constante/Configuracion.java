package org.poo.recurso.constante;

import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.stage.Screen;

public class Configuracion {

    public static final int ALTO_APP = (int) (Screen.getPrimary().getBounds().getHeight() * 0.7);
    public static final int ANCHO_APP = (int) (Screen.getPrimary().getBounds().getWidth() * 0.6);

    private static final double CABECERA_ALTO_PORCENTAJE = 0.1;
    public static final double ALTO_CABECERA = CABECERA_ALTO_PORCENTAJE * ALTO_APP;

    // Colores tema Terminal de Buses (azules y grises)
    public static final String AZUL_OSCURO = "#1E3A8A";      // Azul profundo
    public static final String AZUL_MEDIO = "#3B82F6";       // Azul intermedio
    public static final String AZUL_CLARO = "#93C5FD";       // Azul claro
    public static final String AZUL_SUPER_CLARO = "#DBEAFE"; // Azul muy claro
    public static final String GRIS_OSCURO = "#374151";      // Gris oscuro
    public static final String GRIS_CLARO = "#D1D5DB";       // Gris claro

    public static final String CABECERA_ESTILO_FONDO = String.format(
            "-fx-background-color: linear-gradient(to bottom, %s, %s, %s);",
            AZUL_OSCURO, AZUL_MEDIO, AZUL_CLARO);

    public static final double MARCO_ALTO_PORCENTAJE = 0.75;
    public static final double MARCO_ANCHO_PORCENTAJE = 0.80; 

    public static final double GRILLA_ANCHO_PORCENTAJE = 0.7; 

    public static final String DEGRADE_BORDE = "#3B82F6";

    public static final Stop[] DEGRADE_ARREGLO_TERMINAL = new Stop[]{
        new Stop(0.0, Color.web(AZUL_SUPER_CLARO, 1.0)),
        new Stop(0.3, Color.web(AZUL_CLARO, 0.9)),
        new Stop(0.6, Color.web(AZUL_MEDIO, 0.8)),
        new Stop(1.0, Color.web(AZUL_SUPER_CLARO, 1.0))
    };

    public static final Stop[] DEGRADE_ARREGLO_EMPRESA = new Stop[]{
        new Stop(0.0, Color.web(GRIS_CLARO, 1.0)),
        new Stop(0.3, Color.web(AZUL_CLARO, 0.9)),
        new Stop(0.6, Color.web(AZUL_MEDIO, 0.8)),
        new Stop(1.0, Color.web(GRIS_CLARO, 0.9))
    };
    
    public static final Stop[] DEGRADE_ARREGLO_BUS = new Stop[]{
        new Stop(0.0, Color.web(AZUL_CLARO, 1.0)),
        new Stop(0.5, Color.web(AZUL_MEDIO, 0.9)),
        new Stop(1.0, Color.web(AZUL_OSCURO, 0.8))
    };

    public static final String ICONO_BORRAR = "iconoBorrar.png";
    public static final String ICONO_EDITAR = "iconoEditar.png";
    public static final String ICONO_CANCELAR = "iconoCancelar.png";
    public static final String ICONO_NO_DISPONIBLE = "imgNoDisponible.png";
}