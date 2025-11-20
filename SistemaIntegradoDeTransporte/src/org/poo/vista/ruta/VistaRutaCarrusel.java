package org.poo.vista.ruta;

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
import org.poo.controlador.ruta.*;
import org.poo.dto.RutaDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.constante.Persistencia;
import org.poo.recurso.utilidad.*;

public class VistaRutaCarrusel extends BorderPane {

    private final Stage miEscenario;
    private final BorderPane panelPrincipal;
    private Pane panelCuerpo;
    private final VBox organizadorVertical;

    private static int indiceActualEstatico = 0;
    private int indiceActual;
    private int totalRutas;
    private RutaDto objCargado;

    private StringProperty rutaTitulo;
    private StringProperty rutaNombre;
    private StringProperty rutaOrigen;
    private StringProperty rutaDestino;
    private ObjectProperty<Image> rutaImagen;
    private BooleanProperty rutaEstado;
    private DoubleProperty rutaDistancia;
    private IntegerProperty rutaDuracion;

    public VistaRutaCarrusel(Stage ventanaPadre, BorderPane princ, Pane pane,
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

        totalRutas = RutaControladorListar.obtenerCantidadRutas();
        
        if (totalRutas == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, 
                    miEscenario, "Sin rutas", 
                    "No hay rutas registradas para mostrar en el carrusel");
            return;
        }
        
        if (indiceActual >= totalRutas) {
            indiceActual = 0;
            indiceActualEstatico = 0;
        }
        
        objCargado = RutaControladorUna.obtenerUnaRuta(indiceActual);

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

        rutaTitulo = new SimpleStringProperty(
                "DETALLES DE LA RUTA: (" + (indiceActual + 1) + " / " + totalRutas + ")");

        Label lblTitulo = new Label();
        lblTitulo.textProperty().bind(rutaTitulo);
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
            indiceActual = obtenerIndice("Anterior", indiceActual, totalRutas);
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
            indiceActual = obtenerIndice("Siguiente", indiceActual, totalRutas);
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
            String mensaje = "¿Seguro que desea eliminar esta ruta?\n\n"
                    + "Código: " + objCargado.getIdRuta() + "\n"
                    + "Nombre: " + objCargado.getNombreRuta() + "\n\n"
                    + "Esta acción es irreversible.";

            Alert msg = new Alert(Alert.AlertType.CONFIRMATION);
            msg.setTitle("Confirmar Eliminación");
            msg.setHeaderText(null);
            msg.setContentText(mensaje);
            msg.initOwner(miEscenario);

            if (msg.showAndWait().get() == ButtonType.OK) {
                // Liberar la imagen antes de eliminar
                rutaImagen.set(null);
                System.gc(); // Sugerir recolección de basura
                
                if (RutaControladorEliminar.borrar(indiceActual)) {
                    totalRutas = RutaControladorListar.obtenerCantidadRutas();
                    
                    if (totalRutas > 0) {
                        if (indiceActual >= totalRutas) {
                            indiceActual = totalRutas - 1;
                            indiceActualEstatico = indiceActual;
                        }
                        actualizarContenido();
                        Mensaje.mostrar(Alert.AlertType.INFORMATION,
                                miEscenario, "Éxito", "Ruta eliminada correctamente");
                    } else {
                        Mensaje.mostrar(Alert.AlertType.INFORMATION,
                                miEscenario, "Sin rutas", "No quedan rutas registradas");
                        indiceActualEstatico = 0;
                        panelCuerpo = RutaControladorVentana.administrar(
                                miEscenario, panelPrincipal, panelCuerpo,
                                Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO);
                        panelPrincipal.setCenter(panelCuerpo);
                    }
                } else {
                    Mensaje.mostrar(Alert.AlertType.ERROR,
                            miEscenario, "Error", "No se pudo eliminar la ruta");
                }
            }
        });

        Button btnActualizar = new Button();
        btnActualizar.setPrefWidth(anchoBoton);
        btnActualizar.setCursor(Cursor.HAND);
        btnActualizar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_EDITAR, tamanioIcono));

        btnActualizar.setOnAction(e -> {
            panelCuerpo = RutaControladorVentana.editar(
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

        rutaNombre = new SimpleStringProperty(objCargado.getNombreRuta());
        Label lblNombre = new Label();
        lblNombre.textProperty().bind(rutaNombre);
        lblNombre.setFont(Font.font("Rockwell", FontWeight.BOLD, 24));
        lblNombre.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblNombre);

        rutaImagen = new SimpleObjectProperty<>();
        try {
            String rutaImagen = Persistencia.RUTA_IMAGENES + Persistencia.SEPARADOR_CARPETAS
                    + objCargado.getNombreImagenPrivadoRuta();
            FileInputStream imgArchivo = new FileInputStream(rutaImagen);
            Image imgNueva = new Image(imgArchivo);
            this.rutaImagen.set(imgNueva);

            ImageView imgMostrar = new ImageView(imgNueva);
            imgMostrar.setFitHeight(200);
            imgMostrar.setSmooth(true);
            imgMostrar.setPreserveRatio(true);
            imgMostrar.imageProperty().bind(this.rutaImagen);

            organizadorVertical.getChildren().add(imgMostrar);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VistaRutaCarrusel.class.getName()).log(Level.SEVERE, null, ex);
            ImageView imgDefault = Icono.obtenerIcono(Configuracion.ICONO_NO_DISPONIBLE, 200);
            organizadorVertical.getChildren().add(imgDefault);
        }

        rutaOrigen = new SimpleStringProperty(objCargado.getCiudadOrigenRuta());
        Label lblOrigen = new Label();
        lblOrigen.textProperty().bind(Bindings.concat("Origen: ", rutaOrigen));
        lblOrigen.setFont(Font.font("Rockwell", tamanioFuente));
        lblOrigen.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblOrigen);

        rutaDestino = new SimpleStringProperty(objCargado.getCiudadDestinoRuta());
        Label lblDestino = new Label();
        lblDestino.textProperty().bind(Bindings.concat("Destino: ", rutaDestino));
        lblDestino.setFont(Font.font("Rockwell", tamanioFuente));
        lblDestino.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblDestino);

        rutaDistancia = new SimpleDoubleProperty(objCargado.getDistanciaKmRuta());
        Label lblDistancia = new Label();
        lblDistancia.textProperty().bind(Bindings.concat("Distancia: ", rutaDistancia.asString(), " Km"));
        lblDistancia.setFont(Font.font("Rockwell", tamanioFuente));
        lblDistancia.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblDistancia);

        rutaDuracion = new SimpleIntegerProperty(objCargado.getDuracionHorasRuta());
        Label lblDuracion = new Label();
        lblDuracion.textProperty().bind(Bindings.concat("Duración: ", rutaDuracion.asString(), " horas"));
        lblDuracion.setFont(Font.font("Rockwell", tamanioFuente));
        lblDuracion.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblDuracion);

        rutaEstado = new SimpleBooleanProperty(objCargado.getEstadoRuta());
        Label lblEstado = new Label();
        lblEstado.textProperty().bind(Bindings.when(rutaEstado).then("Activa").otherwise("Inactiva"));
        lblEstado.setFont(Font.font("Rockwell", FontWeight.BOLD, 20));
        lblEstado.textFillProperty().bind(
                rutaEstado.map(dato -> dato ? Color.web(Configuracion.VERDE_EXITO) 
                        : Color.web(Configuracion.ROJO_ERROR))
        );
        organizadorVertical.getChildren().add(lblEstado);
    }

    private void actualizarContenido() {
        objCargado = RutaControladorUna.obtenerUnaRuta(indiceActual);

        rutaTitulo.set("DETALLES DE LA RUTA: (" + (indiceActual + 1) + " / " + totalRutas + ")");
        rutaNombre.set(objCargado.getNombreRuta());
        rutaOrigen.set(objCargado.getCiudadOrigenRuta());
        rutaDestino.set(objCargado.getCiudadDestinoRuta());
        rutaDistancia.set(objCargado.getDistanciaKmRuta());
        rutaDuracion.set(objCargado.getDuracionHorasRuta());
        rutaEstado.set(objCargado.getEstadoRuta());

        try {
            String rutaImagenPath = Persistencia.RUTA_IMAGENES + Persistencia.SEPARADOR_CARPETAS
                    + objCargado.getNombreImagenPrivadoRuta();
            FileInputStream imgArchivo = new FileInputStream(rutaImagenPath);
            Image imgNueva = new Image(imgArchivo);
            rutaImagen.set(imgNueva);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VistaRutaCarrusel.class.getName()).log(Level.SEVERE, null, ex);
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
