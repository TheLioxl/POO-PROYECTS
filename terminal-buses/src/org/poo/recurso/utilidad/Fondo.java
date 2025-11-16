package org.poo.recurso.utilidad;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import org.poo.recurso.constante.Persistencia;

public class Fondo {

    /**
     * Asigna un fondo específico
     */
    public static Background asignar(String nombreFondo) {
        String rutaFondo = Persistencia.NOMBRE_CARPETA_IMAGENES_INTERNAS + nombreFondo;
        Image miImagen = new Image(rutaFondo, true);

        BackgroundSize ajustarTamanio = new BackgroundSize(1, 1, true, true, false, false);
        BackgroundImage fondoListo = new BackgroundImage(
                miImagen, 
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, 
                BackgroundPosition.DEFAULT, 
                ajustarTamanio
        );
        return new Background(fondoListo);
    }

    /**
     * Asigna un fondo aleatorio de un array
     */
    public static Background asignarAleatorio(String fondos[]) {
        String nombreFondo = fondos[Aleatorio.entero(0, fondos.length - 1)];
        return asignar(nombreFondo);
    }
}
