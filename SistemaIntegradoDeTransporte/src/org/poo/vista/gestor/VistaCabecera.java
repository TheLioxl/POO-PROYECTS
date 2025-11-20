package org.poo.vista.gestor;

import javafx.event.ActionEvent;
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
import org.poo.recurso.constante.Persistencia;
import org.poo.recurso.dominio.Contenedor;

public class VistaCabecera extends HBox {

    private final int menuAncho = 160;
    private final int menuAlto = 35;
    private final int menuEspacio = 15;

    private Pane miPanelCuerpo;
    private final Stage miEscenario;
    private final HBox miPanelCabecera;
    private final BorderPane miPanelPrincipal;

    public VistaCabecera(
            Stage escenario,
            BorderPane panelPrincipal,
            Pane panelCuerpo,
            double anchoPanel,
            double altoCabecera) {

        super();

        miPanelCabecera = this;
        miPanelCabecera.setAlignment(Pos.CENTER_LEFT);

        miPanelPrincipal = panelPrincipal;
        miPanelCuerpo = panelCuerpo;
        miEscenario = escenario;

        miPanelCabecera.setSpacing(menuEspacio);
        miPanelCabecera.setPadding(new Insets(0, 30, 0, 30));
        miPanelCabecera.setPrefHeight(Contenedor.ALTO_CABECERA.getValor());
        miPanelCabecera.setStyle(Configuracion.CABECERA_ESTILO_FONDO);

        crearOpciones();

    }
    
    public HBox getMiPanelCabecera() {
        return miPanelCabecera;
    }

    private void crearOpciones() {
        menuTerminal();
        menuEmpresa();
        menuBus();
        menuRuta();
        menuConductor();
        menuPasajero();
        menuViaje();
        menuTiquete();
        menuDestino();

        Region espacio = new Region();
        HBox.setHgrow(espacio, Priority.ALWAYS);
        getChildren().add(espacio);

        btnSalir();
        btnAcerca(400, 450);
    }
    
    private void btnAcerca(double anchoFlotante, double altoFlotante) {

        Button botonAyuda = new Button("?");
        botonAyuda.setOnAction((ActionEvent event) -> {
            VistaAcerca.mostrar(miEscenario, anchoFlotante, altoFlotante);
        });

        botonAyuda.setPrefWidth(30);
        botonAyuda.setId("btn-ayuda");
        botonAyuda.setCursor(Cursor.HAND);
        botonAyuda.getStylesheets().
                add(getClass().getResource(Persistencia.RUTA_ESTILO_BTN_ACERCA).toExternalForm());

        Region espacio = new Region();
        HBox.setHgrow(espacio, Priority.ALWAYS);

        miPanelCabecera.getChildren().add(espacio);
        miPanelCabecera.getChildren().add(botonAyuda);
    }

    private void agregarMenu(MenuButton menu) {
        menu.setCursor(Cursor.HAND);
        menu.setPrefWidth(menuAncho);
        menu.setPrefHeight(menuAlto);
        miPanelCabecera.getChildren().add(menu);
    }
    
    // ====================== MENÚ TERMINAL ======================
    private void menuTerminal() {
        MenuItem opcion1 = new MenuItem("Crear Terminal");
        MenuItem opcion2 = new MenuItem("Listar Terminales");
        MenuItem opcion3 = new MenuItem("Administrar Terminales");
        MenuItem opcion4 = new MenuItem("Carrusel");

        opcion1.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.terminal.TerminalControladorVentana.crear(
                    miEscenario,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        opcion2.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.terminal.TerminalControladorVentana.listar(
                    miEscenario,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        opcion3.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.terminal.TerminalControladorVentana.administrar(
                    miEscenario,
                    miPanelPrincipal,
                    miPanelCuerpo,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        opcion4.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.terminal.TerminalControladorVentana.carrusel(
                miEscenario,
                miPanelPrincipal,
                miPanelCuerpo,
                Configuracion.ANCHO_APP,
                Configuracion.ALTO_CUERPO,
                0);
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        MenuButton menuButton = new MenuButton("Terminales");
        try {
            menuButton.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_TERMINAL, 25));
        } catch (Exception ex) {
            System.out.println("No se pudo cargar ícono de Terminal");
        }
        menuButton.getItems().addAll(opcion1, opcion2, opcion3, opcion4);
        agregarMenu(menuButton);
    }

    // ====================== MENÚ EMPRESA ======================
    private void menuEmpresa() {
        MenuItem opcion1 = new MenuItem("Crear Empresa");
        MenuItem opcion2 = new MenuItem("Listar Empresas");
        MenuItem opcion3 = new MenuItem("Administrar Empresas");
        MenuItem opcion4 = new MenuItem("Carrusel");

        opcion1.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.empresa.EmpresaControladorVentana.crear(
                    miEscenario,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        opcion2.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.empresa.EmpresaControladorVentana.listar(
                    miEscenario,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        opcion3.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.empresa.EmpresaControladorVentana.administrar(
                    miEscenario,
                    miPanelPrincipal,
                    miPanelCuerpo,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        opcion4.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.empresa.EmpresaControladorVentana.carrusel(
                    miEscenario,
                    miPanelPrincipal,
                    miPanelCuerpo,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO,
                    0);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        MenuButton menuButton = new MenuButton("Empresas");
        try {
            menuButton.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_EMPRESA, 25));
        } catch (Exception ex) {
            System.out.println("No se pudo cargar ícono de Empresa");
        }
        menuButton.getItems().addAll(opcion1, opcion2, opcion3, opcion4);
        agregarMenu(menuButton);
    }

    // ====================== MENÚ BUS ======================
    private void menuBus() {
        MenuItem opcion1 = new MenuItem("Crear Bus");
        MenuItem opcion2 = new MenuItem("Listar Buses");
        MenuItem opcion3 = new MenuItem("Administrar Buses");
        MenuItem opcion4 = new MenuItem("Carrusel");

        opcion1.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.bus.BusControladorVentana.crear(
                    miEscenario,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        opcion2.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.bus.BusControladorVentana.listar(
                    miEscenario,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        opcion3.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.bus.BusControladorVentana.administrar(
                    miEscenario,
                    miPanelPrincipal,
                    miPanelCuerpo,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        opcion4.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.bus.BusControladorVentana.carrusel(
                    miEscenario,
                    miPanelPrincipal,
                    miPanelCuerpo,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO,
                    0);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        MenuButton menuButton = new MenuButton("Buses");
        try {
            menuButton.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_BUS, 25));
        } catch (Exception ex) {
            System.out.println("No se pudo cargar ícono de Bus");
        }
        menuButton.getItems().addAll(opcion1, opcion2, opcion3, opcion4);
        agregarMenu(menuButton);
    }


    // ====================== MENÚ RUTA ======================
    private void menuRuta() {
        MenuItem opcion1 = new MenuItem("Crear Ruta");
        MenuItem opcion2 = new MenuItem("Listar Rutas");
        MenuItem opcion3 = new MenuItem("Administrar Rutas");
        MenuItem opcion4 = new MenuItem("Carrusel");

        opcion1.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.ruta.RutaControladorVentana.crear(
                    miEscenario,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        opcion2.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.ruta.RutaControladorVentana.listar(
                    miEscenario,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        opcion3.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.ruta.RutaControladorVentana.administrar(
                    miEscenario,
                    miPanelPrincipal,
                    miPanelCuerpo,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        opcion4.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.ruta.RutaControladorVentana.carrusel(
                    miEscenario,
                    miPanelPrincipal,
                    miPanelCuerpo,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO,
                    0);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        MenuButton menuButton = new MenuButton("Rutas");
        try {
            menuButton.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_RUTA, 25));
        } catch (Exception ex) {
            System.out.println("No se pudo cargar ícono de Ruta");
        }
        menuButton.getItems().addAll(opcion1, opcion2, opcion3, opcion4);
        agregarMenu(menuButton);
    }

    // ====================== MENÚ CONDUCTOR ======================
    private void menuConductor() {

        MenuItem opcion1 = new MenuItem("Crear Conductor");
        MenuItem opcion2 = new MenuItem("Listar Conductores");
        MenuItem opcion3 = new MenuItem("Administrar Conductores");
        MenuItem opcion4 = new MenuItem("Carrusel");

        // Crear Conductor
        opcion1.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.conductor.ConductorControladorVentana.crear(
                    miEscenario,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        // Listar Conductores
        opcion2.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.conductor.ConductorControladorVentana.listar(
                    miEscenario,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        // Administrar Conductores
        opcion3.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.conductor.ConductorControladorVentana.administrar(
                    miEscenario,
                    miPanelPrincipal,
                    miPanelCuerpo,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        // Carrusel de Conductores
        opcion4.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.conductor.ConductorControladorVentana.carrusel(
                    miEscenario,
                    miPanelPrincipal,
                    miPanelCuerpo,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO,
                    0);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        MenuButton menuConductor = new MenuButton("Conductores");
        try {
            // Usa el icono que tengas configurado para conductor
            menuConductor.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_CONDUCTOR, 25));
        } catch (Exception ex) {
            System.out.println("No se pudo cargar ícono de Conductor");
        }

        menuConductor.getItems().addAll(opcion1, opcion2, opcion3, opcion4);
        agregarMenu(menuConductor);
    }

    // ====================== MENÚ PASAJERO ======================
    private void menuPasajero() {
        MenuItem opcion1 = new MenuItem("Crear Pasajero");
        MenuItem opcion2 = new MenuItem("Listar Pasajeros");
        MenuItem opcion3 = new MenuItem("Administrar Pasajeros");
        MenuItem opcion4 = new MenuItem("Carrusel");

        opcion1.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.pasajero.PasajeroControladorVentana.crear(
                    miEscenario,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        opcion2.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.pasajero.PasajeroControladorVentana.listar(
                    miEscenario,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        opcion3.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.pasajero.PasajeroControladorVentana.administrar(
                    miEscenario,
                    miPanelPrincipal,
                    miPanelCuerpo,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        opcion4.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.pasajero.PasajeroControladorVentana.carrusel(
                    miEscenario,
                    miPanelPrincipal,
                    miPanelCuerpo,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO,
                    0);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        MenuButton menuButton = new MenuButton("Pasajeros");
        try {
            menuButton.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_PASAJERO, 25));
        } catch (Exception ex) {
            System.out.println("No se pudo cargar ícono de Pasajero");
        }
        menuButton.getItems().addAll(opcion1, opcion2, opcion3, opcion4);
        agregarMenu(menuButton);
    }

    // ====================== MENÚ VIAJE (COMPLETO Y FUNCIONAL) ======================
    private void menuViaje() {
        MenuItem opcion1 = new MenuItem("Crear Viaje");
        MenuItem opcion2 = new MenuItem("Listar Viajes");
        MenuItem opcion3 = new MenuItem("Administrar Viajes");
        MenuItem opcion4 = new MenuItem("Carrusel");

        opcion1.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.viaje.ViajeControladorVentana.crear(
                    miEscenario,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        opcion2.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.viaje.ViajeControladorVentana.listar(
                    miEscenario,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        opcion3.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.viaje.ViajeControladorVentana.administrar(
                    miEscenario,
                    miPanelPrincipal,
                    miPanelCuerpo,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        opcion4.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.viaje.ViajeControladorVentana.carrusel(
                    miEscenario,
                    miPanelPrincipal,
                    miPanelCuerpo,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO,
                    0);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        MenuButton menuButton = new MenuButton("Viajes");
        try {
            menuButton.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_VIAJE, 25));
        } catch (Exception ex) {
            System.out.println("No se pudo cargar ícono de Viaje");
        }
        menuButton.getItems().addAll(opcion1, opcion2, opcion3, opcion4);
        agregarMenu(menuButton);
    }

    // ====================== MENÚ TIQUETE ======================
    private void menuTiquete() {
        MenuItem opcion1 = new MenuItem("Crear Tiquete");
        MenuItem opcion2 = new MenuItem("Listar Tiquetes");
        MenuItem opcion3 = new MenuItem("Administrar Tiquetes");
        MenuItem opcion4 = new MenuItem("Carrusel");

        opcion1.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.tiquete.TiqueteControladorVentana.crear(
                    miEscenario,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        opcion2.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.tiquete.TiqueteControladorVentana.listar(
                    miEscenario,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        opcion3.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.tiquete.TiqueteControladorVentana.administrar(
                    miEscenario,
                    miPanelPrincipal,
                    miPanelCuerpo,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        opcion4.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.tiquete.TiqueteControladorVentana.carrusel(
                    miEscenario,
                    miPanelPrincipal,
                    miPanelCuerpo,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO,
                    0);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        MenuButton menuButton = new MenuButton("Tiquetes");
        try {
            menuButton.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_TIQUETE, 25));
        } catch (Exception ex) {
            System.out.println("No se pudo cargar ícono de Tiquete");
        }
        menuButton.getItems().addAll(opcion1, opcion2, opcion3, opcion4);
        agregarMenu(menuButton);
    }

    // ====================== MENÚ DESTINO ======================
    private void menuDestino() {
        MenuItem opcion1 = new MenuItem("Crear Destino");
        MenuItem opcion2 = new MenuItem("Listar Destinos");
        MenuItem opcion3 = new MenuItem("Administrar Destinos");
        MenuItem opcion4 = new MenuItem("Carrusel");

        opcion1.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.destino.DestinoControladorVentana.crear(
                    miEscenario,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        opcion2.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.destino.DestinoControladorVentana.listar(
                    miEscenario,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        opcion3.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.destino.DestinoControladorVentana.administrar(
                    miEscenario,
                    miPanelPrincipal,
                    miPanelCuerpo,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        opcion4.setOnAction(e -> {
            miPanelCuerpo = org.poo.controlador.destino.DestinoControladorVentana.carrusel(
                    miEscenario,
                    miPanelPrincipal,
                    miPanelCuerpo,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO,
                    0);
            miPanelPrincipal.setCenter(null);
            miPanelPrincipal.setCenter(miPanelCuerpo);
        });

        MenuButton menuButton = new MenuButton("Destinos");
        try {
            menuButton.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_DESTINO, 25));
        } catch (Exception ex) {
            System.out.println("No se pudo cargar ícono de Destino");
        }
        menuButton.getItems().addAll(opcion1, opcion2, opcion3, opcion4);
        agregarMenu(menuButton);
    }

    // ====================== BOTÓN SALIR ======================
    private void btnSalir() {
        Button btnSalir = new Button("Salir");
        btnSalir.setCursor(Cursor.HAND);
        btnSalir.setPrefWidth(menuAncho);
        btnSalir.setPrefHeight(menuAlto);
        btnSalir.setStyle("-fx-background-color: #EF4444; -fx-text-fill: white; -fx-font-weight: bold;");

        btnSalir.setOnAction(e -> {
            e.consume();
            SalidaControlador.verificar(miEscenario);
        });

        getChildren().add(btnSalir);
    }
    
}
