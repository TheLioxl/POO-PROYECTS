package org.poo.controlador.destino;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.poo.controlador.ControladorEfecto;
import org.poo.dto.DestinoDto;
import org.poo.vista.destino.VistaDestinoAdministrar;
import org.poo.vista.destino.VistaDestinoCarrusel;
import org.poo.vista.destino.VistaDestinoCrear;
import org.poo.vista.destino.VistaDestinoEditar;
import org.poo.vista.destino.VistaDestinoListar;

public class DestinoControladorVentana {
    
    public static StackPane crear(Stage ventana, double ancho, double alto) {
        VistaDestinoCrear vista = new VistaDestinoCrear(ventana, ancho, alto);
        StackPane contenedor = vista;
        ControladorEfecto.aplicarEfecto(contenedor, ancho, alto);
        return contenedor;
    }
    
    public static StackPane listar(Stage ventana, double ancho, double alto) {
        VistaDestinoListar vista = new VistaDestinoListar(ventana, ancho, alto);
        StackPane contenedor = vista;
        ControladorEfecto.aplicarEfecto(contenedor, ancho, alto);
        return contenedor;
    }
    
    public static StackPane administrar(Stage ventana, BorderPane panelPrincipal, 
            Pane panelCuerpo, double ancho, double alto) {
        VistaDestinoAdministrar vista = new VistaDestinoAdministrar(ventana, panelPrincipal, panelCuerpo, ancho, alto);
        StackPane contenedor = vista;
        ControladorEfecto.aplicarEfecto(contenedor, ancho, alto);
        return contenedor;
    }
    
    public static BorderPane carrusel(Stage ventana, BorderPane panelPrincipal, 
            Pane panelCuerpo, double ancho, double alto, int posicion) {
        VistaDestinoCarrusel vista = new VistaDestinoCarrusel(ventana, panelPrincipal, panelCuerpo, ancho, alto, posicion);
        ControladorEfecto.aplicarEfecto(vista, ancho, alto);
        return vista;
    }
    
    public static StackPane editar(Stage ventana, BorderPane panelPrincipal, 
            Pane panelCuerpo, double ancho, double alto, DestinoDto dto, 
            int posicion, boolean desdeCarrusel) {
        VistaDestinoEditar vista = new VistaDestinoEditar(ventana, panelPrincipal, panelCuerpo, 
                ancho, alto, dto, posicion, desdeCarrusel);
        StackPane contenedor = vista.getMiFormulario();
        ControladorEfecto.aplicarEfecto(contenedor, ancho, alto);
        return contenedor;
    }
}
