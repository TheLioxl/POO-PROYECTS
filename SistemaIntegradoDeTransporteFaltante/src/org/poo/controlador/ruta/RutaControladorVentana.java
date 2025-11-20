package org.poo.controlador.ruta;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.poo.controlador.ControladorEfecto;
import org.poo.dto.RutaDto;
import org.poo.vista.ruta.VistaRutaAdministrar;
import org.poo.vista.ruta.VistaRutaCarrusel;
import org.poo.vista.ruta.VistaRutaCrear;
import org.poo.vista.ruta.VistaRutaEditar;
import org.poo.vista.ruta.VistaRutaListar;

public class RutaControladorVentana {

    public static StackPane crear(Stage miEscenario, double anchoFrm, double altoFrm) {
        VistaRutaCrear vista = new VistaRutaCrear(miEscenario, anchoFrm, altoFrm);
        StackPane contenedor = vista;

        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);

        return contenedor;
    }

    public static StackPane listar(Stage miEscenario, double anchoFrm, double altoFrm) {
        VistaRutaListar vista = new VistaRutaListar(miEscenario, anchoFrm, altoFrm);
        StackPane contenedor = vista;

        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);

        return contenedor;
    }

    public static StackPane administrar(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm) {

        VistaRutaAdministrar vista = new VistaRutaAdministrar(
                miEscenario, princ, pane, anchoFrm, altoFrm);
        StackPane contenedor = vista;

        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);

        return contenedor;
    }

    public static BorderPane carrusel(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm, int indice) {

        VistaRutaCarrusel vista = new VistaRutaCarrusel(
                miEscenario, princ, pane, anchoFrm, altoFrm, indice);

        ControladorEfecto.aplicarEfecto(vista, anchoFrm, altoFrm);

        return vista;
    }

    public static StackPane editar(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm, RutaDto objRuta, int posicion, boolean origenCarrusel) {

        VistaRutaEditar vista = new VistaRutaEditar(
                miEscenario, princ, pane, anchoFrm, altoFrm, objRuta, posicion, origenCarrusel);
        StackPane contenedor = vista.getMiFormulario();

        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);

        return contenedor;
    }
}
