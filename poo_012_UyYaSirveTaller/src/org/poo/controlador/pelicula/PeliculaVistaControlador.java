package org.poo.controlador.pelicula;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.poo.vista.pelicula.VistaPeliculaAdministrar;
import org.poo.vista.pelicula.VistaPeliculaCrear;
import org.poo.vista.pelicula.VistaPeliculaListar;

public class PeliculaVistaControlador {

    public static StackPane crearPeli(Stage esce, double anchito, double altito) {
        return new VistaPeliculaCrear(esce, anchito, altito);
    }

    public static StackPane listarPeli(Stage esce, double ancho, double alto) {
        return new VistaPeliculaListar(esce, ancho, alto);
    }

    public static StackPane administrarPeli(Stage esce, double ancho, double alto) {
        return new VistaPeliculaAdministrar(esce, ancho, alto);
    }
}
