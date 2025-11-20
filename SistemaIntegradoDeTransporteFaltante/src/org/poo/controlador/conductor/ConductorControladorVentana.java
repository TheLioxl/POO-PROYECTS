package org.poo.controlador.conductor;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.poo.controlador.ControladorEfecto;
import org.poo.dto.ConductorDto;
import org.poo.vista.conductor.VistaConductorAdministrar;
import org.poo.vista.conductor.VistaConductorCarrusel;
import org.poo.vista.conductor.VistaConductorCrear;
import org.poo.vista.conductor.VistaConductorEditar;
import org.poo.vista.conductor.VistaConductorListar;

public class ConductorControladorVentana {

    public static StackPane crear(Stage miEscenario, double anchoFrm, double altoFrm) {
        VistaConductorCrear vista = new VistaConductorCrear(miEscenario, anchoFrm, altoFrm);
        StackPane contenedor = vista;

        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);

        return contenedor;
    }

    public static StackPane listar(Stage miEscenario, double anchoFrm, double altoFrm) {
        VistaConductorListar vista = new VistaConductorListar(miEscenario, anchoFrm, altoFrm);
        StackPane contenedor = vista;

        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);

        return contenedor;
    }

    public static StackPane administrar(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm) {

        VistaConductorAdministrar vista = new VistaConductorAdministrar(
                miEscenario, princ, pane, anchoFrm, altoFrm);
        StackPane contenedor = vista;

        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);

        return contenedor;
    }

    public static BorderPane carrusel(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm, int indice) {

        VistaConductorCarrusel vista = new VistaConductorCarrusel(
                miEscenario, princ, pane, anchoFrm, altoFrm, indice);

        ControladorEfecto.aplicarEfecto(vista, anchoFrm, altoFrm);

        return vista;
    }

    public static StackPane editar(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm, ConductorDto objConductor,
            int posicion, boolean desdeCarrusel) {

        VistaConductorEditar vista = new VistaConductorEditar(
                miEscenario, princ, pane, anchoFrm, altoFrm, objConductor, posicion, desdeCarrusel);
        StackPane contenedor = vista.getMiFormulario();

        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);

        return contenedor;
    }

    public static StackPane editar(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm, ConductorDto objConductor, int posicion) {
        return editar(miEscenario, princ, pane, anchoFrm, altoFrm, objConductor, posicion, false);
    }
}
