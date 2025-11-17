package org.poo.controlador.empresa;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.poo.controlador.ControladorEfecto;
import org.poo.dto.EmpresaDto;
import org.poo.vista.empresa.VistaEmpresaAdministrar;
import org.poo.vista.empresa.VistaEmpresaCarrusel;
import org.poo.vista.empresa.VistaEmpresaCrear;
import org.poo.vista.empresa.VistaEmpresaEditar;
import org.poo.vista.empresa.VistaEmpresaListar;

/**
 * Controlador para gestionar las diferentes vistas de Empresa
 */
public class EmpresaControladorVentana {

    /**
     * Crea la vista de creación de empresas
     */
    public static StackPane crear(Stage miEscenario, double anchoFrm, double altoFrm) {
        VistaEmpresaCrear vista = new VistaEmpresaCrear(miEscenario, anchoFrm, altoFrm);
        StackPane contenedor = vista;
        
        // Aplicar efecto al abrir
        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);
        
        return contenedor;
    }

    /**
     * Crea la vista de listado de empresas
     */
    public static StackPane listar(Stage miEscenario, double anchoFrm, double altoFrm) {
        VistaEmpresaListar vista = new VistaEmpresaListar(miEscenario, anchoFrm, altoFrm);
        StackPane contenedor = vista;
        
        // Aplicar efecto al abrir
        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);
        
        return contenedor;
    }

    /**
     * Crea la vista de administración de empresas
     */
    public static StackPane administrar(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm) {
        
        VistaEmpresaAdministrar vista = new VistaEmpresaAdministrar(
                miEscenario, princ, pane, anchoFrm, altoFrm);
        StackPane contenedor = vista;
        
        // Aplicar efecto al abrir
        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);
        
        return contenedor;
    }

    /**
     * Crea la vista de carrusel de empresas
     */
    public static BorderPane carrusel(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm, int indice) {
        
        VistaEmpresaCarrusel vista = new VistaEmpresaCarrusel(
                miEscenario, princ, pane, anchoFrm, altoFrm, indice);
        
        // VistaEmpresaCarrusel extiende de BorderPane
        ControladorEfecto.aplicarEfecto(vista, anchoFrm, altoFrm);
        
        return vista;
    }
    
    /**
     * Crea la vista de edición de empresas
     */
    public static StackPane editar(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm, EmpresaDto objEmpresa, int posicion) {
        
        VistaEmpresaEditar vista = new VistaEmpresaEditar(
                miEscenario, princ, pane, anchoFrm, altoFrm, objEmpresa, posicion);
        StackPane contenedor = vista.getMiFormulario();
        
        // Aplicar efecto al abrir
        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);
        
        return contenedor;
    }
}
