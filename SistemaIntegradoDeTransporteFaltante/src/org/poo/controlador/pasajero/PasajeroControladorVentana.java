package org.poo.controlador.pasajero;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.poo.controlador.ControladorEfecto;
import org.poo.dto.PasajeroDto;
import org.poo.vista.pasajero.VistaPasajeroAdministrar;
import org.poo.vista.pasajero.VistaPasajeroCarrusel;
import org.poo.vista.pasajero.VistaPasajeroCrear;
import org.poo.vista.pasajero.VistaPasajeroEditar;
import org.poo.vista.pasajero.VistaPasajeroListar;

public class PasajeroControladorVentana {

    // ================== CREAR ==================
    public static StackPane crear(Stage miEscenario, double anchoFrm, double altoFrm) {
        VistaPasajeroCrear vista = new VistaPasajeroCrear(miEscenario, anchoFrm, altoFrm);
        StackPane contenedor = vista;

        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);

        return contenedor;
    }

    // ================== LISTAR ==================
    public static StackPane listar(Stage miEscenario, double anchoFrm, double altoFrm) {
        VistaPasajeroListar vista = new VistaPasajeroListar(miEscenario, anchoFrm, altoFrm);
        StackPane contenedor = vista;

        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);

        return contenedor;
    }

    // ================== ADMINISTRAR ==================
    public static StackPane administrar(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm) {

        VistaPasajeroAdministrar vista = new VistaPasajeroAdministrar(
                miEscenario, princ, pane, anchoFrm, altoFrm);
        
        StackPane contenedor = vista;

        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);

        return contenedor;
    }

    // ================== CARRUSEL ==================
    public static BorderPane carrusel(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm, int indice) {

        VistaPasajeroCarrusel vista = new VistaPasajeroCarrusel(
                miEscenario, princ, pane, anchoFrm, altoFrm, indice);

        ControladorEfecto.aplicarEfecto(vista, anchoFrm, altoFrm);

        return vista;
    }

    // ================== EDITAR (con bandera vieneDeCarrusel) ==================
    public static StackPane editar(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm,
            PasajeroDto objPasajero, int posicion,
            boolean desdeCarrusel) {

        VistaPasajeroEditar vista = new VistaPasajeroEditar(
                miEscenario, princ, pane, anchoFrm, altoFrm,
                objPasajero, posicion, desdeCarrusel);

        StackPane contenedor = vista.getMiFormulario();

        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);

        return contenedor;
    }

    // ================== EDITAR (sobrecarga simple) ==================
    public static StackPane editar(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm,
            PasajeroDto objPasajero, int posicion) {
        return editar(miEscenario, princ, pane, anchoFrm, altoFrm,
                objPasajero, posicion, false);
    }
}
