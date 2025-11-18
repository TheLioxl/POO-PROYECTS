package org.poo.controlador.bus;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.poo.controlador.ControladorEfecto;
import org.poo.dto.BusDto;
import org.poo.vista.bus.VistaBusAdministrar;
import org.poo.vista.bus.VistaBusCarrusel;
import org.poo.vista.bus.VistaBusCrear;
import org.poo.vista.bus.VistaBusEditar;
import org.poo.vista.bus.VistaBusListar;

public class BusControladorVentana {

    public static StackPane crear(Stage miEscenario, double anchoFrm, double altoFrm) {
        VistaBusCrear vista = new VistaBusCrear(miEscenario, anchoFrm, altoFrm);
        StackPane contenedor = vista;

        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);

        return contenedor;
    }

    public static StackPane listar(Stage miEscenario, double anchoFrm, double altoFrm) {
        VistaBusListar vista = new VistaBusListar(miEscenario, anchoFrm, altoFrm);
        StackPane contenedor = vista;

        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);

        return contenedor;
    }

    public static StackPane administrar(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm) {

        VistaBusAdministrar vista = new VistaBusAdministrar(
                miEscenario, princ, pane, anchoFrm, altoFrm);
        StackPane contenedor = vista;

        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);

        return contenedor;
    }

    public static BorderPane carrusel(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm, int indice) {

        VistaBusCarrusel vista = new VistaBusCarrusel(
                miEscenario, princ, pane, anchoFrm, altoFrm, indice);

        ControladorEfecto.aplicarEfecto(vista, anchoFrm, altoFrm);

        return vista;
    }

    public static StackPane editar(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm, BusDto objBus, int posicion, boolean origenCarrusel) {

        VistaBusEditar vista = new VistaBusEditar(
                miEscenario, princ, pane, anchoFrm, altoFrm, objBus, posicion, origenCarrusel);
        StackPane contenedor = vista.getMiFormulario();

        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);

        return contenedor;
    }
}
