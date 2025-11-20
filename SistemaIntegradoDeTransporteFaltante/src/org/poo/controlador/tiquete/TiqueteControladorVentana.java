package org.poo.controlador.tiquete;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.poo.controlador.ControladorEfecto;
import org.poo.dto.TiqueteDto;
import org.poo.vista.tiquete.VistaTiqueteAdministrar;
import org.poo.vista.tiquete.VistaTiqueteCarrusel;
import org.poo.vista.tiquete.VistaTiqueteCrear;
import org.poo.vista.tiquete.VistaTiqueteEditar;
import org.poo.vista.tiquete.VistaTiqueteListar;

public class TiqueteControladorVentana {

    public static StackPane crear(Stage miEscenario, double anchoFrm, double altoFrm) {
        VistaTiqueteCrear vista = new VistaTiqueteCrear(miEscenario, anchoFrm, altoFrm);
        StackPane contenedor = vista;
        
        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);
        
        return contenedor;
    }

    public static StackPane listar(Stage miEscenario, double anchoFrm, double altoFrm) {
        VistaTiqueteListar vista = new VistaTiqueteListar(miEscenario, anchoFrm, altoFrm);
        StackPane contenedor = vista;
        
        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);
        
        return contenedor;
    }

    public static StackPane administrar(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm) {
        
        VistaTiqueteAdministrar vista = new VistaTiqueteAdministrar(
                miEscenario, princ, pane, anchoFrm, altoFrm);
        StackPane contenedor = vista;
        
        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);
        
        return contenedor;
    }

    public static BorderPane carrusel(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm, int indice) {
        
        VistaTiqueteCarrusel vista = new VistaTiqueteCarrusel(
                miEscenario, princ, pane, anchoFrm, altoFrm, indice);
        
        ControladorEfecto.aplicarEfecto(vista, anchoFrm, altoFrm);
        
        return vista;
    }
    
    public static StackPane editar(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm, TiqueteDto objTiquete, int posicion, boolean desdeCarrusel) {
        
        VistaTiqueteEditar vista = new VistaTiqueteEditar(
                miEscenario, princ, pane, anchoFrm, altoFrm, objTiquete, posicion, desdeCarrusel);
        StackPane contenedor = vista.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);
        
        return contenedor;
    }
    
    public static StackPane editar(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm, TiqueteDto objTiquete, int posicion) {
        return editar(miEscenario, princ, pane, anchoFrm, altoFrm, objTiquete, posicion, false);
    }
}
