package org.poo.controlador.ruta;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.poo.dto.RutaDto;
import org.poo.vista.ruta.VistaRutaAdministrar;
import org.poo.vista.ruta.VistaRutaCarrusel;
import org.poo.vista.ruta.VistaRutaCrear;
import org.poo.vista.ruta.VistaRutaEditar;
import org.poo.vista.ruta.VistaRutaListar;

public class RutaControladorVentana {

    public static Pane crear(Stage escenario, double ancho, double alto) {
        return new VistaRutaCrear(escenario, ancho, alto);
    }

    public static Pane listar(Stage escenario, double ancho, double alto) {
        return new VistaRutaListar(escenario, ancho, alto);
    }

    public static Pane administrar(Stage escenario, BorderPane panelPrinc,
            Pane panelCuerpo, double ancho, double alto) {
        return new VistaRutaAdministrar(escenario, panelPrinc, panelCuerpo, ancho, alto);
    }

    public static Pane carrusel(Stage escenario, BorderPane panelPrinc,
            Pane panelCuerpo, double ancho, double alto, int indice) {
        return new VistaRutaCarrusel(escenario, panelPrinc, panelCuerpo, ancho, alto, indice);
    }

    public static Pane editar(Stage escenario, BorderPane panelPrinc,
            Pane panelCuerpo, double ancho, double alto, RutaDto rutaDto,
            int indice, boolean desdeCarrusel) {
        return new VistaRutaEditar(escenario, panelPrinc, panelCuerpo, ancho, alto,
                rutaDto, indice, desdeCarrusel);
    }
}
