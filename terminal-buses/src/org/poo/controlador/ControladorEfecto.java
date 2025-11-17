package org.poo.controlador;

import javafx.scene.layout.Pane;
import org.poo.recurso.utilidad.Aleatorio;
import org.poo.recurso.utilidad.Efecto;

public class ControladorEfecto {

    public static void aplicarEfecto(Pane contenedor, double anchoFrm, double altoFrm) {
        int opcion = Aleatorio.entero(1, 7);
        
        switch (opcion) {
            case 1 -> {
                contenedor.setTranslateX(anchoFrm - (anchoFrm * 0.2));
                Efecto.transicionX(contenedor, 1.5);
            }
            case 2 -> {
                contenedor.setTranslateY(altoFrm - (altoFrm * 0.2));
                Efecto.transicionY(contenedor, 1.5);
            }
            case 3 -> {
                Efecto.crecer(contenedor, 1.2);
            }
            case 4 -> {
                Efecto.rotar(contenedor, 1.5);
            }
            case 5 -> {
                Efecto.desvanecer(contenedor, 1.8);
            }
            case 6 -> {
                Efecto.rebote(contenedor, 1.5);
            }
            case 7 -> {
                Efecto.latido(contenedor, 1.0);
            }
        }
    }
}