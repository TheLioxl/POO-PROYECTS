package org.poo.vista.gestor;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.Icono;
import org.poo.controlador.SalidaControlador;

public class VistaCabecera extends HBox {

    private final int MENU_ANCHO = 160;
    private final int MENU_ALTO = 35;
    private final int ESPACIO_X = 15;

    private Pane miPanelCuerpo;
    private final Stage miEscenario;
    private final BorderPane miPanelPrincipal;

    public VistaCabecera(
            Stage escenario,
            BorderPane panelPrincipal,
            Pane panelCuerpo,
            double anchoPanel,
            double altoCabecera) {

        miEscenario = escenario;
        miPanelPrincipal = panelPrincipal;
        miPanelCuerpo = panelCuerpo;

        setAlignment(Pos.CENTER_LEFT);
        setSpacing(ESPACIO_X);
        setPadding(new Insets(0, 30, 0, 30));
        setPrefHeight(altoCabecera);
        setStyle(Configuracion.CABECERA_ESTILO_FONDO);

        crearOpciones();
    }

    private void crearOpciones() {
        // MENÚS DE GESTIÓN (9 persistencias)
        menuTerminal();
        menuEmpresa();
        menuBus();
        menuRuta();
        menuConductor();
        menuPasajero();
        menuViaje();
        menuTiquete();
        menuDestino();

        // Espacio flexible antes del botón salir
        Region espacio = new Region();
        HBox.setHgrow(espacio, Priority.ALWAYS);
        getChildren().add(espacio);

        // Botón salir al final
        btnSalir();
    }

    private void agregarMenu(MenuButton menu) {
        menu.setCursor(Cursor.HAND);
        menu.setPrefWidth(MENU_ANCHO);
        menu.setPrefHeight(MENU_ALTO);
        getChildren().add(menu);
    }
    
    // ====================== MENÚ TERMINAL ======================
    private void menuTerminal() {
        MenuItem opcion1 = new MenuItem("Crear Terminal");
        MenuItem opcion2 = new MenuItem("Listar Terminales");
        MenuItem opcion3 = new MenuItem("Administrar Terminales");

        opcion1.setOnAction(e -> {
            miPanelCuerpo = new org.poo.vista.terminal.VistaTerminalCrear(
                    miEscenario, 
                    Configuracion.ANCHO_APP, 
                    Configuracion.ALTO_CUERPO);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        opcion2.setOnAction(e -> {
            miPanelCuerpo = new org.poo.vista.terminal.VistaTerminalListar(
                    miEscenario,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        opcion3.setOnAction(e -> {
            System.out.println("Administrar Terminales - Por implementar");
        });

        MenuButton menuButton = new MenuButton("Terminales");
        try {
            menuButton.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_TERMINAL, 25));
        } catch (Exception ex) {
            System.out.println("No se pudo cargar ícono de Terminal");
        }
        menuButton.getItems().addAll(opcion1, opcion2, opcion3);
        agregarMenu(menuButton);
    }

    // ====================== MENÚ EMPRESA ======================
    private void menuEmpresa() {
        MenuItem opcion1 = new MenuItem("Crear Empresa");
        MenuItem opcion2 = new MenuItem("Listar Empresas");
        MenuItem opcion3 = new MenuItem("Administrar Empresas");

        opcion1.setOnAction(e -> {
            System.out.println("Abrir formulario Crear Empresa");
        });

        opcion2.setOnAction(e -> {
            System.out.println("Abrir formulario Listar Empresas");
        });

        opcion3.setOnAction(e -> {
            System.out.println("Abrir formulario Administrar Empresas");
        });

        MenuButton menuButton = new MenuButton("Empresas");
        try {
            menuButton.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_EMPRESA, 25));
        } catch (Exception ex) {
            System.out.println("No se pudo cargar ícono de Empresa");
        }
        menuButton.getItems().addAll(opcion1, opcion2, opcion3);
        agregarMenu(menuButton);
    }

    // ====================== MENÚ BUS ======================
    private void menuBus() {
        MenuItem opcion1 = new MenuItem("Crear Bus");
        MenuItem opcion2 = new MenuItem("Listar Buses");
        MenuItem opcion3 = new MenuItem("Administrar Buses");

        opcion1.setOnAction(e -> {
            System.out.println("Abrir formulario Crear Bus");
        });

        opcion2.setOnAction(e -> {
            System.out.println("Abrir formulario Listar Buses");
        });

        opcion3.setOnAction(e -> {
            System.out.println("Abrir formulario Administrar Buses");
        });

        MenuButton menuButton = new MenuButton("Buses");
        try {
            menuButton.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_BUS, 25));
        } catch (Exception ex) {
            System.out.println("No se pudo cargar ícono de Bus");
        }
        menuButton.getItems().addAll(opcion1, opcion2, opcion3);
        agregarMenu(menuButton);
    }

    // ====================== MENÚ RUTA ======================
    private void menuRuta() {
        MenuItem opcion1 = new MenuItem("Crear Ruta");
        MenuItem opcion2 = new MenuItem("Listar Rutas");
        MenuItem opcion3 = new MenuItem("Administrar Rutas");

        opcion1.setOnAction(e -> {
            System.out.println("Abrir formulario Crear Ruta");
        });

        opcion2.setOnAction(e -> {
            System.out.println("Abrir formulario Listar Rutas");
        });

        opcion3.setOnAction(e -> {
            System.out.println("Abrir formulario Administrar Rutas");
        });

        MenuButton menuButton = new MenuButton("Rutas");
        try {
            menuButton.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_RUTA, 25));
        } catch (Exception ex) {
            System.out.println("No se pudo cargar ícono de Ruta");
        }
        menuButton.getItems().addAll(opcion1, opcion2, opcion3);
        agregarMenu(menuButton);
    }

    // ====================== MENÚ CONDUCTOR ======================
    private void menuConductor() {
        MenuItem opcion1 = new MenuItem("Crear Conductor");
        MenuItem opcion2 = new MenuItem("Listar Conductores");
        MenuItem opcion3 = new MenuItem("Administrar Conductores");

        opcion1.setOnAction(e -> {
            System.out.println("Abrir formulario Crear Conductor");
        });

        opcion2.setOnAction(e -> {
            System.out.println("Abrir formulario Listar Conductores");
        });

        opcion3.setOnAction(e -> {
            System.out.println("Abrir formulario Administrar Conductores");
        });

        MenuButton menuButton = new MenuButton("Conductores");
        try {
            menuButton.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_CONDUCTOR, 25));
        } catch (Exception ex) {
            System.out.println("No se pudo cargar ícono de Conductor");
        }
        menuButton.getItems().addAll(opcion1, opcion2, opcion3);
        agregarMenu(menuButton);
    }

    // ====================== MENÚ PASAJERO ======================
    private void menuPasajero() {
        MenuItem opcion1 = new MenuItem("Registrar Pasajero");
        MenuItem opcion2 = new MenuItem("Listar Pasajeros");
        MenuItem opcion3 = new MenuItem("Administrar Pasajeros");

        opcion1.setOnAction(e -> {
            System.out.println("Abrir formulario Crear Pasajero");
        });

        opcion2.setOnAction(e -> {
            System.out.println("Abrir formulario Listar Pasajeros");
        });

        opcion3.setOnAction(e -> {
            System.out.println("Abrir formulario Administrar Pasajeros");
        });

        MenuButton menuButton = new MenuButton("Pasajeros");
        try {
            menuButton.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_PASAJERO, 25));
        } catch (Exception ex) {
            System.out.println("No se pudo cargar ícono de Pasajero");
        }
        menuButton.getItems().addAll(opcion1, opcion2, opcion3);
        agregarMenu(menuButton);
    }

    // ====================== MENÚ VIAJE ======================
    private void menuViaje() {
        MenuItem opcion1 = new MenuItem("Programar Viaje");
        MenuItem opcion2 = new MenuItem("Listar Viajes");
        MenuItem opcion3 = new MenuItem("Administrar Viajes");

        opcion1.setOnAction(e -> {
            System.out.println("Abrir formulario Crear Viaje");
        });

        opcion2.setOnAction(e -> {
            System.out.println("Abrir formulario Listar Viajes");
        });

        opcion3.setOnAction(e -> {
            System.out.println("Abrir formulario Administrar Viajes");
        });

        MenuButton menuButton = new MenuButton("Viajes");
        try {
            menuButton.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_VIAJE, 25));
        } catch (Exception ex) {
            System.out.println("No se pudo cargar ícono de Viaje");
        }
        menuButton.getItems().addAll(opcion1, opcion2, opcion3);
        agregarMenu(menuButton);
    }

    // ====================== MENÚ TIQUETE ======================
    private void menuTiquete() {
        MenuItem opcion1 = new MenuItem("Vender Tiquete");
        MenuItem opcion2 = new MenuItem("Listar Tiquetes");
        MenuItem opcion3 = new MenuItem("Administrar Tiquetes");

        opcion1.setOnAction(e -> {
            System.out.println("Abrir formulario Crear Tiquete");
        });

        opcion2.setOnAction(e -> {
            System.out.println("Abrir formulario Listar Tiquetes");
        });

        opcion3.setOnAction(e -> {
            System.out.println("Abrir formulario Administrar Tiquetes");
        });

        MenuButton menuButton = new MenuButton("Tiquetes");
        try {
            menuButton.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_TIQUETE, 25));
        } catch (Exception ex) {
            System.out.println("No se pudo cargar ícono de Tiquete");
        }
        menuButton.getItems().addAll(opcion1, opcion2, opcion3);
        agregarMenu(menuButton);
    }

    // ====================== MENÚ DESTINO ======================
    private void menuDestino() {
        MenuItem opcion1 = new MenuItem("Crear Destino");
        MenuItem opcion2 = new MenuItem("Listar Destinos");
        MenuItem opcion3 = new MenuItem("Administrar Destinos");

        opcion1.setOnAction(e -> {
            System.out.println("Abrir formulario Crear Destino");
        });

        opcion2.setOnAction(e -> {
            System.out.println("Abrir formulario Listar Destinos");
        });

        opcion3.setOnAction(e -> {
            System.out.println("Abrir formulario Administrar Destinos");
        });

        MenuButton menuButton = new MenuButton("Destinos");
        try {
            menuButton.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_DESTINO, 25));
        } catch (Exception ex) {
            System.out.println("No se pudo cargar ícono de Destino");
        }
        menuButton.getItems().addAll(opcion1, opcion2, opcion3);
        agregarMenu(menuButton);
    }

    // ====================== BOTÓN SALIR ======================
    private void btnSalir() {
        Button btnSalir = new Button("Salir");
        btnSalir.setCursor(Cursor.HAND);
        btnSalir.setPrefWidth(MENU_ANCHO);
        btnSalir.setPrefHeight(MENU_ALTO);
        btnSalir.setStyle("-fx-background-color: #EF4444; -fx-text-fill: white; -fx-font-weight: bold;");

        btnSalir.setOnAction(e -> {
            e.consume();
            SalidaControlador.verificar(miEscenario);
        });

        getChildren().add(btnSalir);
    }
}
