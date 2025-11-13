package edu.poo.controlador.categoria;

import edu.poo.vista.categoria.VistaCategoCrear;
import edu.poo.vista.categoria.VistaCategoListar;
import edu.poo.controlador.varios.ControladorEfecto;
import edu.poo.modelo.Categoria;
import edu.poo.vista.categoria.VistaCategoAdmin;
import edu.poo.vista.categoria.VistaCategoCarrusel;
import edu.poo.vista.categoria.VistaCategoEditar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ControladorCategoVentana {

    public static StackPane crear(Stage miEscenario, double anchoFrm, double altoFrm) {
        VistaCategoCrear carroCrearSubScena = new VistaCategoCrear(miEscenario, anchoFrm, altoFrm);
        StackPane contenedor = carroCrearSubScena.getMiFormulario();

        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);
        return contenedor;
    }

    public static StackPane listar(Stage miEscenario, double anchoFrm, double altoFrm) {
        VistaCategoListar ventana = new VistaCategoListar(miEscenario, anchoFrm, altoFrm);
        StackPane contenedor = ventana.getMiFormulario();

        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);
        return contenedor;
    }

    public static StackPane administrar(Stage miEscenario, BorderPane princ, Pane pane, double anchoFrm, double altoFrm) {
        VistaCategoAdmin ventana = new VistaCategoAdmin(miEscenario, princ, pane, anchoFrm, altoFrm);
        StackPane contenedor = ventana.getMiFormulario();

        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);
        return contenedor;
    }

    public static StackPane editar(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm, Categoria objCate, int posi) {

        VistaCategoEditar ventana = new VistaCategoEditar(miEscenario, princ, pane, anchoFrm, altoFrm, objCate, posi);
        StackPane contenedor = ventana.getMiFormulario();

        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);
        return contenedor;
    }

    public static BorderPane carrusel(Stage miEscenario, BorderPane princ, Pane pane,
            double anchoFrm, double altoFrm, int indice) {

        VistaCategoCarrusel ventana = new VistaCategoCarrusel(miEscenario, princ, pane, anchoFrm, altoFrm, indice);
        BorderPane contenedor = ventana.getMiBorderPane();

        ControladorEfecto.aplicarEfecto(contenedor, anchoFrm, altoFrm);
        return contenedor;
    }
}
