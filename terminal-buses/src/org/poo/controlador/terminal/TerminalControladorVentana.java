package org.poo.controlador.terminal;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.poo.controlador.ControladorEfecto;
import org.poo.dto.TerminalDto;
import org.poo.vista.terminal.VistaTerminalAdministrar;
import org.poo.vista.terminal.VistaTerminalCarrusel;
import org.poo.vista.terminal.VistaTerminalCrear;
import org.poo.vista.terminal.VistaTerminalEditar;
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
                miEscenario, princ, pane, anchoFrm, altoFrm);
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
        
        VistaTerminalCarrusel vista = new VistaTerminalCarrusel(
                miEscenario, princ, pane, anchoFrm, altoFrm, indice);
        
        // VistaTerminalCarrusel extiende de BorderPane, así que podemos aplicar efectos directamente
        ControladorEfecto.aplicarEfecto(vista, anchoFrm, altoFrm);
        
        return vista;
    }
    
    /**
     * Crea la vista de edición de terminales
     * @param desdeCarrusel indica si la llamada viene desde el carrusel (true) o desde administrar (false)
     */
    public static StackPane editar(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm, TerminalDto objTerminal, int posicion, boolean desdeCarrusel) {
        
        VistaTerminalEditar vista = new VistaTerminalEditar(
                miEscenario, princ, pane, anchoFrm, altoFrm, objTerminal, posicion, desdeCarrusel);
        StackPane contenedor = vista.getMiFormulario();
        
        // Aplicar efecto al abrir
        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);
        
        return contenedor;
    }
    
    /**
     * Sobrecarga del método editar para mantener compatibilidad con código existente
     * Por defecto asume que NO viene del carrusel
     */
    public static StackPane editar(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm, TerminalDto objTerminal, int posicion) {
        return editar(miEscenario, princ, pane, anchoFrm, altoFrm, objTerminal, posicion, false);
    }
}
