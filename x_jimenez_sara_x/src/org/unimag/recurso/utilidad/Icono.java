package org.unimag.recurso.utilidad;

import java.io.FileInputStream;
import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.unimag.recurso.constante.Persistencia;

public class Icono {

    public static ImageView obtenerIcono(
            String nombreIcono, int alto
    ) {
        String rutaIcono = Persistencia.NOMBRE_CARPETA_IMAGENES_INTERNAS + nombreIcono;
        // System.out.println("----> " + rutaIcono);
        Image iconito = new Image(rutaIcono);
        ImageView vistaIconito = new ImageView(iconito);

        if (alto > 0) {
            vistaIconito.setFitHeight(alto);
        }
        vistaIconito.setPreserveRatio(true);
        vistaIconito.setSmooth(true);
        return vistaIconito;
    }

    public static ImageView previsualizar(String rutaImagen, int dimensionMaxima) {
        ImageView imgMostrar = null;

        try (FileInputStream archivo = new FileInputStream(rutaImagen)) {
            Image imgBasica = new Image(archivo);
            imgMostrar = new ImageView(imgBasica);

            double ancho = imgBasica.getWidth();
            double alto = imgBasica.getHeight();

            if (ancho > alto) {
                imgMostrar.setFitWidth(dimensionMaxima);
            } else {
                imgMostrar.setFitHeight(dimensionMaxima);
            }

            imgMostrar.setPreserveRatio(true);
            imgMostrar.setSmooth(true);

        } catch (IOException miError) {
            System.out.println("Error al cargar la foto externa: " + miError.getMessage());
        }

        return imgMostrar;
    }

}
