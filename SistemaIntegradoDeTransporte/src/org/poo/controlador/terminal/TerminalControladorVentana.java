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

public class TerminalControladorVentana {

    public static StackPane crear(Stage miEscenario, double anchoFrm, double altoFrm) {
        VistaTerminalCrear vista = new VistaTerminalCrear(miEscenario, anchoFrm, altoFrm);
        StackPane contenedor = vista;
        
        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);
        
        return contenedor;
    }

    public static StackPane listar(Stage miEscenario, double anchoFrm, double altoFrm) {
        VistaTerminalListar vista = new VistaTerminalListar(miEscenario, anchoFrm, altoFrm);
        StackPane contenedor = vista;
        
        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);
        
        return contenedor;
    }

    public static StackPane administrar(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm) {
        
        VistaTerminalAdministrar vista = new VistaTerminalAdministrar(
                miEscenario, princ, pane, anchoFrm, altoFrm);
        StackPane contenedor = vista;
        
        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);
        
        return contenedor;
    }

    public static BorderPane carrusel(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm, int indice) {
        
        VistaTerminalCarrusel vista = new VistaTerminalCarrusel(
                miEscenario, princ, pane, anchoFrm, altoFrm, indice);
        
        ControladorEfecto.aplicarEfecto(vista, anchoFrm, altoFrm);
        
        return vista;
    }
    
    public static StackPane editar(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm, TerminalDto objTerminal, int posicion, boolean desdeCarrusel) {
        
        VistaTerminalEditar vista = new VistaTerminalEditar(
                miEscenario, princ, pane, anchoFrm, altoFrm, objTerminal, posicion, desdeCarrusel);
        StackPane contenedor = vista.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);
        
        return contenedor;
    }
    
    public static StackPane editar(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm, TerminalDto objTerminal, int posicion) {
        return editar(miEscenario, princ, pane, anchoFrm, altoFrm, objTerminal, posicion, false);
    }
}
