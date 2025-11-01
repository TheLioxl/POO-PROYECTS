package org.poo.controlador.libro;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.poo.vista.libro.VistaLibroAdministrar;
import org.poo.vista.libro.VistaLibroCrear;
import org.poo.vista.libro.VistaLibroListar;

public class LibroVistasControlador {

    public static StackPane crearLibro(Stage esce, double anchito, double altito) {
        return new VistaLibroCrear(esce, anchito, altito);
    }

    public static StackPane listarLibro(Stage esce, double ancho, double alto) {
        return new VistaLibroListar(esce, ancho, alto);
    }
    
    public static StackPane administrarLibro(Stage esce, double ancho, double alto) {
        return new VistaLibroAdministrar(esce, ancho, alto);
    }
}