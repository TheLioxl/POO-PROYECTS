package org.poo.controlador.autor;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.poo.vista.autor.VistaAutorAdministrar;
import org.poo.vista.autor.VistaAutorCrear;
import org.poo.vista.autor.VistaAutorListar;

public class AutorVistasControlador {
    
    public static StackPane crearAutor(Stage esce, double ancho, double alto) {
        return new VistaAutorCrear(esce, ancho, alto);
    }
    
    public static StackPane listarAutor(Stage esce, double ancho, double alto) {
        return new VistaAutorListar(esce, ancho, alto);
    }

    public static StackPane administrarAutor(Stage esce, double ancho, double alto) {
        return new VistaAutorAdministrar(esce, ancho, alto);
    }
}