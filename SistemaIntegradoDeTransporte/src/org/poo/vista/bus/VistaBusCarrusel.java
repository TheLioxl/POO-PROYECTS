package org.poo.vista.bus;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.poo.controlador.bus.*;
import org.poo.dto.BusDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.constante.Persistencia;
import org.poo.recurso.utilidad.*;

public class VistaBusCarrusel extends BorderPane {

    private final Stage miEscenario;
    private final BorderPane panelPrincipal;
    private Pane panelCuerpo;
    private final VBox organizadorVertical;

    private static int indiceActualEstatico = 0;
    private int indiceActual;
    private int totalBuses;
    private BusDto objCargado;

    private StringProperty busTitulo;
    private StringProperty busPlaca;
    private StringProperty busModelo;
    private StringProperty busEmpresa;
    private ObjectProperty<Image> busImagen;
    private BooleanProperty busEstado;
    private IntegerProperty busCapacidad;
    private StringProperty busTipo;

    public VistaBusCarrusel(Stage ventanaPadre, BorderPane princ, Pane pane,
            double anchoPanel, double altoPanel, int indice) {

        miEscenario = ventanaPadre;
        panelPrincipal = princ;
        panelCuerpo = pane;
        
        if (indice == 0 && indiceActualEstatico > 0) {
            indiceActual = indiceActualEstatico;
        } else {
            indiceActual = indice;
            indiceActualEstatico = indice;
        }

        organizadorVertical = new VBox();

        totalBuses = BusControladorListar.obtenerCantidadBuses();
        
        if (totalBuses == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, 
                    miEscenario, "Sin buses", 
                    "No hay buses registrados para mostrar en el carrusel");
            return;
        }
        
        if (indiceActual >= totalBuses) {
            indiceActual = 0;
            indiceActualEstatico = 0;
        }
        
        objCargado = BusControladorUna.obtenerBus(indiceActual);

        configurarOrganizadorVertical();
        crearTitulo();
        construirPanelIzquierdo(0.08);
        construirPanelDerecho(0.08);
        construirPanelCentro();
    }

    private void configurarOrganizadorVertical() {
        organizadorVertical.setSpacing(15);
        organizadorVertical.setAlignment(Pos.TOP_CENTER);
        organizadorVertical.prefWidthProperty().bind(miEscenario.widthProperty());
        organizadorVertical.prefHeightProperty().bind(miEscenario.heightProperty());
    }

    private void crearTitulo() {
        Region bloqueSeparador = new Region();
        bloqueSeparador.prefHeightProperty().bind(miEscenario.heightProperty().multiply(0.08));
        organizadorVertical.getChildren().add(0, bloqueSeparador);

        busTitulo = new SimpleStringProperty(
                "DETALLES DEL BUS: (" + (indiceActual + 1) + " / " + totalBuses + ")");

        Label lblTitulo = new Label();
        lblTitulo.textProperty().bind(busTitulo);
        lblTitulo.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        lblTitulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 24));
        organizadorVertical.getChildren().add(lblTitulo);
    }

    private void construirPanelIzquierdo(double porcentaje) {
        Button btnAnterior = new Button();
        btnAnterior.setGraphic(Icono.obtenerIcono("btnAtras.png", 60));
        btnAnterior.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        btnAnterior.setCursor(Cursor.HAND);

        btnAnterior.setOnAction(e -> {
            indiceActual = obtenerIndice("Anterior", indiceActual, totalBuses);
            indiceActualEstatico = indiceActual;
            actualizarContenido();
        });

        StackPane panelIzquierdo = new StackPane();
        panelIzquierdo.prefWidthProperty().bind(miEscenario.widthProperty().multiply(porcentaje));
        panelIzquierdo.getChildren().add(btnAnterior);
        setLeft(panelIzquierdo);
    }

    private void construirPanelDerecho(double porcentaje) {
        Button btnSiguiente = new Button();
        btnSiguiente.setGraphic(Icono.obtenerIcono("btnSiguiente.png", 60));
        btnSiguiente.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        btnSiguiente.setCursor(Cursor.HAND);

        btnSiguiente.setOnAction(e -> {
            indiceActual = obtenerIndice("Siguiente", indiceActual, totalBuses);
            indiceActualEstatico = indiceActual;
            actualizarContenido();
        });

        StackPane panelDerecho = new StackPane();
        panelDerecho.prefWidthProperty().bind(miEscenario.widthProperty().multiply(porcentaje));
        panelDerecho.getChildren().add(btnSiguiente);
        setRight(panelDerecho);
    }

    private void construirPanelCentro() {
        StackPane centerPane = new StackPane();

        Rectangle miMarco = Marco.crear(miEscenario, 0.75, 0.65,
                Configuracion.DEGRADE_ARREGLO_TERMINAL,
                Configuracion.DEGRADE_BORDE);
        centerPane.getChildren().addAll(miMarco, organizadorVertical);

        panelOpciones();
        mostrarDatos();

        setCenter(centerPane);
    }

    private void panelOpciones() {
        int anchoBoton = 40;
        int tamanioIcono = 18;

        Button btnEliminar = new Button();
        btnEliminar.setPrefWidth(anchoBoton);
        btnEliminar.setCursor(Cursor.HAND);
        btnEliminar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_BORRAR, tamanioIcono));

        btnEliminar.setOnAction(e -> {
            String mensaje = "¿Seguro que desea eliminar este bus?\n\n"
                    + "Código: " + objCargado.getIdBus() + "\n"
                    + "Placa: " + objCargado.getPlacaBus() + "\n\n"
                    + "Esta acción es irreversible.";

            Alert msg = new Alert(Alert.AlertType.CONFIRMATION);
            msg.setTitle("Confirmar Eliminación");
            msg.setHeaderText(null);
            msg.setContentText(mensaje);
            msg.initOwner(miEscenario);

            if (msg.showAndWait().get() == ButtonType.OK) {
                // Liberar la imagen antes de eliminar
                busImagen.set(null);
                System.gc(); // Sugerir recolección de basura
                
                if (BusControladorEliminar.borrar(indiceActual)) {
                    totalBuses = BusControladorListar.obtenerCantidadBuses();
                    
                    if (totalBuses > 0) {
                        if (indiceActual >= totalBuses) {
                            indiceActual = totalBuses - 1;
                            indiceActualEstatico = indiceActual;
                        }
                        actualizarContenido();
                        Mensaje.mostrar(Alert.AlertType.INFORMATION,
                                miEscenario, "Éxito", "Bus eliminado correctamente");
                    } else {
                        Mensaje.mostrar(Alert.AlertType.INFORMATION,
                                miEscenario, "Sin buses", "No quedan buses registrados");
                        indiceActualEstatico = 0;
                        panelCuerpo = BusControladorVentana.administrar(
                                miEscenario, panelPrincipal, panelCuerpo,
                                Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO);
                        panelPrincipal.setCenter(panelCuerpo);
                    }
                } else {
                    Mensaje.mostrar(Alert.AlertType.ERROR,
                            miEscenario, "Error", "No se pudo eliminar el bus");
                }
            }
        });

        Button btnActualizar = new Button();
        btnActualizar.setPrefWidth(anchoBoton);
        btnActualizar.setCursor(Cursor.HAND);
        btnActualizar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_EDITAR, tamanioIcono));

        btnActualizar.setOnAction(e -> {
            panelCuerpo = BusControladorVentana.editar(
                    miEscenario,
                    panelPrincipal,
                    panelCuerpo,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO,
                    objCargado,
                    indiceActual,
                    true);
                    
            panelPrincipal.setCenter(null);
            panelPrincipal.setCenter(panelCuerpo);
        });

        HBox panelBotones = new HBox(5);
        panelBotones.setAlignment(Pos.CENTER);
        panelBotones.getChildren().addAll(btnEliminar, btnActualizar);
        
        organizadorVertical.getChildren().add(panelBotones);
    }
    
    private void mostrarDatos() {
        int tamanioFuente = 20;

        busPlaca = new SimpleStringProperty(objCargado.getPlacaBus());
        Label lblPlaca = new Label();
        lblPlaca.textProperty().bind(busPlaca);
        lblPlaca.setFont(Font.font("Rockwell", FontWeight.BOLD, 24));
        lblPlaca.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblPlaca);

        busImagen = new SimpleObjectProperty<>();
        try {
            String rutaImagen = Persistencia.RUTA_IMAGENES + Persistencia.SEPARADOR_CARPETAS
                    + objCargado.getNombreImagenPrivadoBus();
            FileInputStream imgArchivo = new FileInputStream(rutaImagen);
            Image imgNueva = new Image(imgArchivo);
            busImagen.set(imgNueva);

            ImageView imgMostrar = new ImageView(imgNueva);
            imgMostrar.setFitHeight(200);
            imgMostrar.setSmooth(true);
            imgMostrar.setPreserveRatio(true);
            imgMostrar.imageProperty().bind(busImagen);

            organizadorVertical.getChildren().add(imgMostrar);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VistaBusCarrusel.class.getName()).log(Level.SEVERE, null, ex);
            ImageView imgDefault = Icono.obtenerIcono(Configuracion.ICONO_NO_DISPONIBLE, 200);
            organizadorVertical.getChildren().add(imgDefault);
        }

        busModelo = new SimpleStringProperty(objCargado.getModeloBus());
        Label lblModelo = new Label();
        lblModelo.textProperty().bind(Bindings.concat("Modelo: ", busModelo));
        lblModelo.setFont(Font.font("Rockwell", tamanioFuente));
        lblModelo.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblModelo);

        busCapacidad = new SimpleIntegerProperty(objCargado.getCapacidadBus());
        Label lblCapacidad = new Label();
        lblCapacidad.textProperty().bind(Bindings.concat("Capacidad: ", busCapacidad.asString(), " pasajeros"));
        lblCapacidad.setFont(Font.font("Rockwell", tamanioFuente));
        lblCapacidad.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblCapacidad);

        busEmpresa = new SimpleStringProperty(
            objCargado.getEmpresaBus().getNombreEmpresa());
        Label lblEmpresa = new Label();
        lblEmpresa.textProperty().bind(Bindings.concat("Empresa: ", busEmpresa));
        lblEmpresa.setFont(Font.font("Rockwell", tamanioFuente));
        lblEmpresa.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblEmpresa);

        busTipo = new SimpleStringProperty(objCargado.getTipoBus());
        Label lblTipo = new Label();
        lblTipo.textProperty().bind(Bindings.concat("Tipo: ", busTipo));
        lblTipo.setFont(Font.font("Rockwell", tamanioFuente));
        lblTipo.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblTipo);

        busEstado = new SimpleBooleanProperty(objCargado.getEstadoBus());
        Label lblEstado = new Label();
        lblEstado.textProperty().bind(Bindings.when(busEstado).then("Activo").otherwise("Inactivo"));
        lblEstado.setFont(Font.font("Rockwell", FontWeight.BOLD, 20));
        lblEstado.textFillProperty().bind(
                busEstado.map(dato -> dato ? Color.web(Configuracion.VERDE_EXITO) 
                        : Color.web(Configuracion.ROJO_ERROR))
        );
        organizadorVertical.getChildren().add(lblEstado);
    }

    private void actualizarContenido() {
        objCargado = BusControladorUna.obtenerBus(indiceActual);

        busTitulo.set("DETALLES DEL BUS: (" + (indiceActual + 1) + " / " + totalBuses + ")");
        busPlaca.set(objCargado.getPlacaBus());
        busModelo.set(objCargado.getModeloBus());
        busCapacidad.set(objCargado.getCapacidadBus());
        busEmpresa.set(objCargado.getEmpresaBus().getNombreEmpresa());
        busTipo.set(objCargado.getTipoBus());
        busEstado.set(objCargado.getEstadoBus());

        try {
            String rutaImagen = Persistencia.RUTA_IMAGENES + Persistencia.SEPARADOR_CARPETAS
                    + objCargado.getNombreImagenPrivadoBus();
            FileInputStream imgArchivo = new FileInputStream(rutaImagen);
            Image imgNueva = new Image(imgArchivo);
            busImagen.set(imgNueva);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VistaBusCarrusel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static Integer obtenerIndice(String opcion, int indice, int total) {
        Integer nuevoIndice = indice;
        Integer limite = total - 1;

        switch (opcion.toLowerCase()) {
            case "anterior" -> {
                if (indice == 0) {
                    nuevoIndice = limite;
                } else {
                    nuevoIndice = indice - 1;
                }
            }
            case "siguiente" -> {
                if (indice == limite) {
                    nuevoIndice = 0;
                } else {
                    nuevoIndice = indice + 1;
                }
            }
        }
        return nuevoIndice;
    }
    
    public static void resetearPosicion() {
        indiceActualEstatico = 0;
    }
}
