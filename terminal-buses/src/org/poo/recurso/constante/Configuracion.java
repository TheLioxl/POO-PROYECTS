package org.poo.recurso.constante;

import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.stage.Screen;

public class Configuracion {

    // DIMENSIONES DE LA APLICACIÓN
    public static final int ALTO_APP = (int) (Screen.getPrimary().getBounds().getHeight() * 0.8);
    public static final int ANCHO_APP = (int) (Screen.getPrimary().getBounds().getWidth() * 0.7);

    // CABECERA
    private static final double CABECERA_ALTO_PORCENTAJE = 0.1;
    public static final double ALTO_CABECERA = CABECERA_ALTO_PORCENTAJE * ALTO_APP;

    // COLORES TEMA TERMINAL DE BUSES (Azules y grises profesionales)
    public static final String AZUL_OSCURO = "#1E3A8A";        // Azul profundo
    public static final String AZUL_MEDIO = "#3B82F6";         // Azul intermedio
    public static final String AZUL_CLARO = "#93C5FD";         // Azul claro
    public static final String AZUL_SUPER_CLARO = "#DBEAFE";   // Azul muy claro
    public static final String GRIS_OSCURO = "#374151";        // Gris oscuro
    public static final String GRIS_CLARO = "#D1D5DB";         // Gris claro
    public static final String BLANCO = "#FFFFFF";
    public static final String VERDE_EXITO = "#10B981";        // Verde para estados activos
    public static final String ROJO_ERROR = "#EF4444";         // Rojo para estados inactivos

    // ESTILO CABECERA
    public static final String CABECERA_ESTILO_FONDO = String.format(
            "-fx-background-color: linear-gradient(to bottom, %s, %s, %s);",
            AZUL_OSCURO, AZUL_MEDIO, AZUL_CLARO);

    // MARCOS Y CONTENEDORES
    public static final double MARCO_ALTO_PORCENTAJE = 0.75;
    public static final double MARCO_ANCHO_PORCENTAJE = 0.80;
    public static final double GRILLA_ANCHO_PORCENTAJE = 0.7;

    // DEGRADADOS PARA DIFERENTES MÓDULOS
    public static final String DEGRADE_BORDE = "#3B82F6";

    // Degradado para Terminal
    public static final Stop[] DEGRADE_ARREGLO_TERMINAL = new Stop[]{
        new Stop(0.0, Color.web(AZUL_SUPER_CLARO, 1.0)),
        new Stop(0.3, Color.web(AZUL_CLARO, 0.9)),
        new Stop(0.6, Color.web(AZUL_MEDIO, 0.8)),
        new Stop(1.0, Color.web(AZUL_SUPER_CLARO, 1.0))
    };

    // Degradado para Empresa
    public static final Stop[] DEGRADE_ARREGLO_EMPRESA = new Stop[]{
        new Stop(0.0, Color.web(GRIS_CLARO, 1.0)),
        new Stop(0.3, Color.web(AZUL_CLARO, 0.9)),
        new Stop(0.6, Color.web(AZUL_MEDIO, 0.8)),
        new Stop(1.0, Color.web(GRIS_CLARO, 0.9))
    };

    // Degradado para Bus
    public static final Stop[] DEGRADE_ARREGLO_BUS = new Stop[]{
        new Stop(0.0, Color.web(AZUL_CLARO, 1.0)),
        new Stop(0.5, Color.web(AZUL_MEDIO, 0.9)),
        new Stop(1.0, Color.web(AZUL_OSCURO, 0.8))
    };

    // Degradado para Ruta
    public static final Stop[] DEGRADE_ARREGLO_RUTA = new Stop[]{
        new Stop(0.0, Color.web("#E0F2FE", 1.0)),
        new Stop(0.5, Color.web("#BAE6FD", 0.9)),
        new Stop(1.0, Color.web("#7DD3FC", 0.8))
    };

    // Degradado para Conductor
    public static final Stop[] DEGRADE_ARREGLO_CONDUCTOR = new Stop[]{
        new Stop(0.0, Color.web("#F0F9FF", 1.0)),
        new Stop(0.5, Color.web("#E0F2FE", 0.9)),
        new Stop(1.0, Color.web("#BAE6FD", 0.8))
    };

    // Degradado para Pasajero
    public static final Stop[] DEGRADE_ARREGLO_PASAJERO = new Stop[]{
        new Stop(0.0, Color.web("#DBEAFE", 1.0)),
        new Stop(0.5, Color.web("#BFDBFE", 0.9)),
        new Stop(1.0, Color.web("#93C5FD", 0.8))
    };

    // Degradado para Viaje
    public static final Stop[] DEGRADE_ARREGLO_VIAJE = new Stop[]{
        new Stop(0.0, Color.web("#DBEAFE", 1.0)),
        new Stop(0.3, Color.web("#93C5FD", 0.9)),
        new Stop(0.6, Color.web("#60A5FA", 0.8)),
        new Stop(1.0, Color.web("#DBEAFE", 1.0))
    };

    // Degradado para Tiquete
    public static final Stop[] DEGRADE_ARREGLO_TIQUETE = new Stop[]{
        new Stop(0.0, Color.web("#F0F9FF", 1.0)),
        new Stop(0.5, Color.web("#BAE6FD", 0.9)),
        new Stop(1.0, Color.web("#7DD3FC", 0.8))
    };

    // Degradado para Destino
    public static final Stop[] DEGRADE_ARREGLO_DESTINO = new Stop[]{
        new Stop(0.0, Color.web("#E0F2FE", 1.0)),
        new Stop(0.5, Color.web("#93C5FD", 0.9)),
        new Stop(1.0, Color.web("#3B82F6", 0.8))
    };

    // ICONOS
    public static final String ICONO_BORRAR = "iconoBorrar.png";
    public static final String ICONO_EDITAR = "iconoEditar.png";
    public static final String ICONO_CANCELAR = "iconoCancelar.png";
    public static final String ICONO_NO_DISPONIBLE = "imgNoDisponible.png";

    // ICONOS DE MENÚ
    public static final String ICONO_TERMINAL = "iconoTerminal.png";
    public static final String ICONO_EMPRESA = "iconoEmpresa.png";
    public static final String ICONO_BUS = "iconoBus.png";
    public static final String ICONO_RUTA = "iconoRuta.png";
    public static final String ICONO_CONDUCTOR = "iconoConductor.png";
    public static final String ICONO_PASAJERO = "iconoPasajero.png";
    public static final String ICONO_VIAJE = "iconoViaje.png";
    public static final String ICONO_TIQUETE = "iconoTiquete.png";
    public static final String ICONO_DESTINO = "iconoDestino.png";

    // ALTURA DEL CUERPO (calculada)
    public static final double ALTO_CUERPO = ALTO_APP - ALTO_CABECERA;
}
