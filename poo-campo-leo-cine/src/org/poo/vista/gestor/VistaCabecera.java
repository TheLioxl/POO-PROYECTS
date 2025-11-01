package org.poo.vista.gestor;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.poo.controlador.genero.GeneroVistasControlador;
import org.poo.controlador.pelicula.PeliculaVistaControlador;
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
        menuGenero();
        menuPelicula();
        menuSalas();
        menuBoletas();
    }

    private void menuGenero() {
        MenuItem opcion1 = new MenuItem("Crear Género");
        MenuItem opcion2 = new MenuItem("Listar Géneros");
        MenuItem opcion3 = new MenuItem("Administrar Géneros");
        MenuItem opcion4 = new MenuItem("Carrusel");

        opcion1.setOnAction((e) -> {
            miPanelPrincipal.setCenter(GeneroVistasControlador.crearGen(
                    miEscenario, Configuracion.ANCHO_APP, Configuracion.ALTO_APP));
        });

        opcion2.setOnAction((e) -> {
            miPanelPrincipal.setCenter(GeneroVistasControlador.listarGen(
                    miEscenario, Configuracion.ANCHO_APP, Configuracion.ALTO_APP));
        });

        opcion3.setOnAction((e) -> {
            miPanelPrincipal.setCenter(GeneroVistasControlador.administrarGen(
                    miEscenario, Configuracion.ANCHO_APP, Configuracion.ALTO_APP));
        });

        opcion4.setOnAction((e) -> {
            System.out.println("Abrir formulario carrusel Género");
        });

        MenuButton miBoton = new MenuButton("Géneros");
        miBoton.getItems().addAll(opcion1, opcion2, opcion3, opcion4);
        agregarBotonMenu(miBoton);
    }

    private void menuPelicula() {
        MenuItem opcion1 = new MenuItem("Crear Película");
        MenuItem opcion2 = new MenuItem("Listar Películas");
        MenuItem opcion3 = new MenuItem("Administrar Películas");
        MenuItem opcion4 = new MenuItem("Carrusel");

        opcion1.setOnAction((e) -> {
            miPanelPrincipal.setCenter(PeliculaVistaControlador.crearPeli(
                    miEscenario, Configuracion.ANCHO_APP, Configuracion.ALTO_APP));
        });

        opcion2.setOnAction((e) -> {
            miPanelPrincipal.setCenter(PeliculaVistaControlador.listarPeli(
                    miEscenario, Configuracion.ANCHO_APP, Configuracion.ALTO_APP));
        });

        opcion3.setOnAction((e) -> {
            miPanelPrincipal.setCenter(PeliculaVistaControlador.administrarPeli(
                    miEscenario, Configuracion.ANCHO_APP, Configuracion.ALTO_APP));
        });

        opcion4.setOnAction((e) -> {
            System.out.println("Abrir formulario carrusel Película");
        });

        MenuButton miBoton = new MenuButton("Películas");
        miBoton.getItems().addAll(opcion1, opcion2, opcion3, opcion4);
        agregarBotonMenu(miBoton);
    }

    private void menuSalas() {
        MenuItem opcion1 = new MenuItem("Crear Sala");
        MenuItem opcion2 = new MenuItem("Listar Salas");
        MenuItem opcion3 = new MenuItem("Administrar Salas");
        MenuItem opcion4 = new MenuItem("Carrusel");

        opcion1.setOnAction((e) -> {
            System.out.println("Abrir formulario crear Sala");
        });

        opcion2.setOnAction((e) -> {
            System.out.println("Abrir formulario listar Sala");
        });

        opcion3.setOnAction((e) -> {
            System.out.println("Abrir administrar Sala");
        });

        opcion4.setOnAction((e) -> {
            System.out.println("Abrir formulario carrusel Sala");
        });

        MenuButton miBoton = new MenuButton("Salas");
        miBoton.getItems().addAll(opcion1, opcion2, opcion3, opcion4);
        agregarBotonMenu(miBoton);
    }

    private void menuBoletas() {
        MenuItem opcion1 = new MenuItem("Crear Boleta");
        MenuItem opcion2 = new MenuItem("Listar Boletas");
        MenuItem opcion3 = new MenuItem("Administrar Boletas");
        MenuItem opcion4 = new MenuItem("Carrusel");

        opcion1.setOnAction((e) -> {
            System.out.println("Abrir formulario crear Boleta");
        });

        opcion2.setOnAction((e) -> {
            System.out.println("Abrir formulario listar Boleta");
        });

        opcion3.setOnAction((e) -> {
            System.out.println("Abrir administrar Boleta");
        });

        opcion4.setOnAction((e) -> {
            System.out.println("Abrir formulario carrusel Boleta");
        });

        MenuButton miBoton = new MenuButton("Boletas");
        miBoton.getItems().addAll(opcion1, opcion2, opcion3, opcion4);
        agregarBotonMenu(miBoton);
    }
}
