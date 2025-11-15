package org.poo.recurso.utilidad;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Efecto {

    public static void transicionX(Pane contenedor, double segundos) {
        TranslateTransition cambio = new TranslateTransition();
        cambio.setDuration(Duration.seconds(segundos));
        cambio.setNode(contenedor);
        cambio.setToX(0);
        cambio.play();
    }

    public static void transicionY(Pane contenedor, double segundos) {
        TranslateTransition cambio = new TranslateTransition();
        cambio.setDuration(Duration.seconds(segundos));
        cambio.setNode(contenedor);
        cambio.setToY(0);
        cambio.play();
    }

    public static void crecer(Pane contenedor, double segundos) {
        ScaleTransition cambio = new ScaleTransition();
        cambio.setDuration(Duration.seconds(segundos));
        cambio.setNode(contenedor);
        cambio.setFromX(0.1);
        cambio.setFromY(0.1);
        cambio.setToX(1);
        cambio.setToY(1);
        cambio.play();
    }

    public static void desvanecer(Pane contenedor, double segundos) {
        FadeTransition cambio = new FadeTransition();
        cambio.setDuration(Duration.seconds(segundos));
        cambio.setNode(contenedor);
        cambio.setFromValue(0);
        cambio.setToValue(1);
        cambio.play();
    }
}
