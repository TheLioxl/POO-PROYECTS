package org.poo.controlador.terminal;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.poo.vista.terminal.VistaTerminalCrear;
import org.poo.vista.terminal.VistaTerminalListar;
import org.poo.recurso.utilidad.Efecto;

public class TerminalControladorVentana {

    public static Pane crear(Stage miEscenario, double anchoFrm, double altoFrm) {
        VistaTerminalCrear vistaCrear = new VistaTerminalCrear(
                miEscenario, anchoFrm, altoFrm);
        
        StackPane contenedor = vistaCrear;
        
        // Aplicar efecto al abrir
        Efecto.desvanecer(contenedor, 1.5);
        
        return contenedor;
    }

    public static Pane listar(Stage miEscenario, double anchoFrm, double altoFrm) {
        VistaTerminalListar vistaListar = new VistaTerminalListar(
                miEscenario, anchoFrm, altoFrm);
        
        StackPane contenedor = vistaListar;
        
        // Aplicar efecto al abrir
        Efecto.crecer(contenedor, 1.2);
        
        return contenedor;
    }
}
