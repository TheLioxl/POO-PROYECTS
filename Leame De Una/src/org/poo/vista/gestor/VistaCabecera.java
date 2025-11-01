package org.poo.vista.gestor;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.poo.controlador.autor.AutorVistasControlador;
import org.poo.controlador.editorial.EditorialVistasControlador;
import org.poo.controlador.libro.LibroVistasControlador;
import org.poo.recurso.constante.Configuracion;

public class VistaCabecera extends HBox {

    private final int menuAncho = 150;
    private final int menuAlto = 35;
    private final int menuEspacio = 10;

    private final Stage miEscenario;
    private final BorderPane miPanelPrincipal;

    public VistaCabecera(Stage esce, BorderPane bpan, double altoCabecera) {
        miEscenario = esce;
        miPanelPrincipal = bpan;
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(menuEspacio);
        setPadding(new Insets(0, 20, 0, 20));
        setPrefHeight(altoCabecera);
        setStyle(Configuracion.CABECERA_ESTILO_FONDO);

        crearMenus();
    }

    private void agregarBotonMenu(MenuButton miMenu) {
        miMenu.setCursor(Cursor.HAND);
        miMenu.setPrefWidth(menuAncho);
        miMenu.setPrefHeight(menuAlto);
        getChildren().add(miMenu);
    }

    private void crearMenus() {
        menuLibro();
        menuEditorial();
        menuAutor();
    }

    private void menuLibro() {
        MenuItem opcion1 = new MenuItem("Crear Libro");
        MenuItem opcion2 = new MenuItem("Listar Libros");
        MenuItem opcion3 = new MenuItem("Administrar Libros");

        opcion1.setOnAction((e) -> {
            miPanelPrincipal.setCenter(LibroVistasControlador.crearLibro(
                    miEscenario, Configuracion.ANCHO_APP, Configuracion.ALTO_APP));
        });

        opcion2.setOnAction((e) -> {
            miPanelPrincipal.setCenter(LibroVistasControlador.listarLibro(
                    miEscenario, Configuracion.ANCHO_APP, Configuracion.ALTO_APP));
        });

        opcion3.setOnAction((e) -> {
            miPanelPrincipal.setCenter(LibroVistasControlador.administrarLibro(
                    miEscenario, Configuracion.ANCHO_APP, Configuracion.ALTO_APP));
        });

        MenuButton miBoton = new MenuButton("Libros");
        miBoton.getItems().addAll(opcion1, opcion2, opcion3);
        agregarBotonMenu(miBoton);
    }

    private void menuEditorial() {
        MenuItem opcion1 = new MenuItem("Crear Editorial");
        MenuItem opcion2 = new MenuItem("Listar Editoriales");
        MenuItem opcion3 = new MenuItem("Administrar Editoriales");

        opcion1.setOnAction((e) -> {
            miPanelPrincipal.setCenter(EditorialVistasControlador.crearEditorial(
                    miEscenario, Configuracion.ANCHO_APP, Configuracion.ALTO_APP));
        });

        opcion2.setOnAction((e) -> {
            miPanelPrincipal.setCenter(EditorialVistasControlador.listarEditorial(
                    miEscenario, Configuracion.ANCHO_APP, Configuracion.ALTO_APP));
        });

        opcion3.setOnAction((e) -> {
            miPanelPrincipal.setCenter(EditorialVistasControlador.administrarEditorial(
                    miEscenario, Configuracion.ANCHO_APP, Configuracion.ALTO_APP));
        });

        MenuButton miBoton = new MenuButton("Editoriales");
        miBoton.getItems().addAll(opcion1, opcion2, opcion3);
        agregarBotonMenu(miBoton);
    }

    private void menuAutor() {
        MenuItem opcion1 = new MenuItem("Crear Autor");
        MenuItem opcion2 = new MenuItem("Listar Autores");
        MenuItem opcion3 = new MenuItem("Administrar Autores");

        opcion1.setOnAction((e) -> {
            miPanelPrincipal.setCenter(AutorVistasControlador.crearAutor(
                    miEscenario, Configuracion.ANCHO_APP, Configuracion.ALTO_APP));
        });

        opcion2.setOnAction((e) -> {
            miPanelPrincipal.setCenter(AutorVistasControlador.listarAutor(
                    miEscenario, Configuracion.ANCHO_APP, Configuracion.ALTO_APP));
        });

        opcion3.setOnAction((e) -> {
            miPanelPrincipal.setCenter(AutorVistasControlador.administrarAutor(
                    miEscenario, Configuracion.ANCHO_APP, Configuracion.ALTO_APP));
        });

        MenuButton miBoton = new MenuButton("Autores");
        miBoton.getItems().addAll(opcion1, opcion2, opcion3);
        agregarBotonMenu(miBoton);
    }
}