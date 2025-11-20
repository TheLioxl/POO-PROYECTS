package org.poo.recurso.utilidad;

import javafx.animation.*;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.poo.recurso.constante.Persistencia;

public class Efecto {

    public static void transicionX(Pane contenedor, double segundos) {
        TranslateTransition cambio = new TranslateTransition();
        cambio.setDuration(Duration.seconds(segundos));
        cambio.setNode(contenedor);
        cambio.setToX(0);
        cambio.play();
        System.out.println("TransicionX aplicada");
    }

    public static void transicionY(Pane contenedor, double segundos) {
        TranslateTransition cambio = new TranslateTransition();
        cambio.setDuration(Duration.seconds(segundos));
        cambio.setNode(contenedor);
        cambio.setToY(0);
        cambio.play();
        System.out.println("TransicionY aplicada");
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
        System.out.println("Efecto Crecer aplicado");
    }

    public static void rotar(Pane contenedor, double segundos) {
        RotateTransition cambio = new RotateTransition();
        cambio.setDuration(Duration.seconds(segundos));
        cambio.setNode(contenedor);
        cambio.setFromAngle(0);
        cambio.setToAngle(360);
        cambio.setCycleCount(1);
        cambio.play();
        System.out.println("Efecto Rotar aplicado");
    }

    public static void desvanecer(Pane contenedor, double segundos) {
        FadeTransition cambio = new FadeTransition();
        cambio.setDuration(Duration.seconds(segundos));
        cambio.setNode(contenedor);
        cambio.setFromValue(0);
        cambio.setToValue(1);
        cambio.play();
        System.out.println("Efecto Desvanecer aplicado");
    }

    public static void rebote(Pane contenedor, double segundos) {
        TranslateTransition cambio = new TranslateTransition();
        cambio.setDuration(Duration.seconds(segundos));
        cambio.setNode(contenedor);
        cambio.setByY(-150);
        cambio.setCycleCount(4);
        cambio.setAutoReverse(true);
        cambio.play();
        System.out.println("Efecto Rebote aplicado");
    }

    public static void latido(Pane contenedor, double segundos) {
        ScaleTransition latido = new ScaleTransition(Duration.seconds(segundos), contenedor);
        latido.setFromX(1.0);
        latido.setFromY(1.0);
        latido.setToX(1.2);
        latido.setToY(1.2);
        latido.setCycleCount(2);
        latido.setAutoReverse(true);
        latido.play();
        System.out.println("Efecto Latido aplicado");
    }

    public static void pantallaRota(Pane contenedor, double anchoFrm, double altoFrm, int segundos) {
        String rutaPantalla = Persistencia.NOMBRE_CARPETA_IMAGENES_INTERNAS + "pantallaRota.png";
        
        try {
            Image vidrioRoto = new Image(Efecto.class.getResourceAsStream(rutaPantalla));
            ImageView vidrioView = new ImageView(vidrioRoto);
            vidrioView.setFitWidth(anchoFrm);
            vidrioView.setFitHeight(altoFrm);

            contenedor.getChildren().add(vidrioView);
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(segundos),
                    e -> contenedor.getChildren().remove(vidrioView)));
            timeline.setCycleCount(1);
            timeline.play();
            System.out.println("Efecto Pantalla Rota aplicado");
        } catch (Exception e) {
            System.out.println("No se pudo cargar imagen de pantalla rota: " + e.getMessage());
        }
    }
}
