package org.poo.controlador.viaje;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.poo.controlador.ControladorEfecto;
import org.poo.dto.ViajeDto;
import org.poo.vista.viaje.VistaViajeAdministrar;
import org.poo.vista.viaje.VistaViajeCarrusel;
import org.poo.vista.viaje.VistaViajeCrear;
import org.poo.vista.viaje.VistaViajeEditar;
import org.poo.vista.viaje.VistaViajeListar;

public class ViajeControladorVentana {

    public static StackPane crear(Stage miEscenario, double anchoFrm, double altoFrm) {
        VistaViajeCrear vista = new VistaViajeCrear(miEscenario, anchoFrm, altoFrm);
        StackPane contenedor = vista;
        
        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);
        
        return contenedor;
    }

    public static StackPane listar(Stage miEscenario, double anchoFrm, double altoFrm) {
        VistaViajeListar vista = new VistaViajeListar(miEscenario, anchoFrm, altoFrm);
        StackPane contenedor = vista;
        
        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);
        
        return contenedor;
    }

    public static StackPane administrar(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm) {
        
        VistaViajeAdministrar vista = new VistaViajeAdministrar(
                miEscenario, princ, pane, anchoFrm, altoFrm);
        StackPane contenedor = vista;
        
        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);
        
        return contenedor;
    }

    public static BorderPane carrusel(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm, int indice) {
        
        VistaViajeCarrusel vista = new VistaViajeCarrusel(
                miEscenario, princ, pane, anchoFrm, altoFrm, indice);
        
        ControladorEfecto.aplicarEfecto(vista, anchoFrm, altoFrm);
        
        return vista;
    }

    public static StackPane editar(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm, ViajeDto objViaje, int posicion, boolean origenCarrusel) {
        
        VistaViajeEditar vista = new VistaViajeEditar(
                miEscenario, princ, pane, anchoFrm, altoFrm, objViaje, posicion, origenCarrusel);
        StackPane contenedor = vista.getMiFormulario();

        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);
        
        return contenedor;
    }
}