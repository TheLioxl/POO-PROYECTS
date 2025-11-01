package org.poo.controlador.editorial;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.poo.vista.editorial.VistaEditorialAdministrar;
import org.poo.vista.editorial.VistaEditorialCrear;
import org.poo.vista.editorial.VistaEditorialListar;

public class EditorialVistasControlador {
    
    public static StackPane crearEditorial(Stage esce, double ancho, double alto) {
        return new VistaEditorialCrear(esce, ancho, alto);
    }
    
    public static StackPane listarEditorial(Stage esce, double ancho, double alto) {
        return new VistaEditorialListar(esce, ancho, alto);
    }

    public static StackPane administrarEditorial(Stage esce, double ancho, double alto) {
        return new VistaEditorialAdministrar(esce, ancho, alto);
    }
}