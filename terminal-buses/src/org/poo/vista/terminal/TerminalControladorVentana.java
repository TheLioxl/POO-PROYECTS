package org.poo.controlador.terminal;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.poo.controlador.ControladorEfecto;
import org.poo.vista.terminal.VistaTerminalAdministrar;
import org.poo.vista.terminal.VistaTerminalCrear;
import org.poo.vista.terminal.VistaTerminalListar;

/**
 * Controlador para gestionar las diferentes vistas de Terminal
 */
public class TerminalControladorVentana {

    /**
     * Crea la vista de creación de terminales
     */
    public static StackPane crear(Stage miEscenario, double anchoFrm, double altoFrm) {
        VistaTerminalCrear vista = new VistaTerminalCrear(miEscenario, anchoFrm, altoFrm);
        StackPane contenedor = vista;
        
        // Aplicar efecto al abrir
        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);
        
        return contenedor;
    }

    /**
     * Crea la vista de listado de terminales
     */
    public static StackPane listar(Stage miEscenario, double anchoFrm, double altoFrm) {
        VistaTerminalListar vista = new VistaTerminalListar(miEscenario, anchoFrm, altoFrm);
        StackPane contenedor = vista;
        
        // Aplicar efecto al abrir
        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);
        
        return contenedor;
    }

    /**
     * Crea la vista de administración de terminales
     */
    public static StackPane administrar(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm) {
        
        VistaTerminalAdministrar vista = new VistaTerminalAdministrar(
                miEscenario, anchoFrm, altoFrm);
        StackPane contenedor = vista;
        
        // Aplicar efecto al abrir
        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);
        
        return contenedor;
    }

    /**
     * Crea la vista de carrusel de terminales
     */
    public static BorderPane carrusel(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm, int indice) {
        
        // TODO: Implementar VistaTerminalCarrusel
        System.out.println("Carrusel de terminales - Por implementar");
        
        // Por ahora retornamos un BorderPane vacío
        BorderPane contenedor = new BorderPane();
        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);
        
        return contenedor;
    }
}