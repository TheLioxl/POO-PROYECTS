package org.poo.vista.tiquete;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.poo.controlador.tiquete.TiqueteControladorEliminar;
import org.poo.controlador.tiquete.TiqueteControladorListar;
import org.poo.controlador.tiquete.TiqueteControladorUna;
import org.poo.controlador.tiquete.TiqueteControladorVentana;
import org.poo.dto.TiqueteDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.constante.Persistencia;
import org.poo.recurso.utilidad.Icono;
import org.poo.recurso.utilidad.Marco;
import org.poo.recurso.utilidad.Mensaje;

public class VistaTiqueteCarrusel extends BorderPane {

    private final Stage miEscenario;
    private final BorderPane panelPrincipal;
    private Pane panelCuerpo;
    private final VBox organizadorVertical;

    private static int indiceActualEstatico = 0;
    private int indiceActual;
    private int totalTiquetes;
    private TiqueteDto objCargado;

    private StringProperty tiqueteTitulo;
    private StringProperty tiqueteViaje;
    private StringProperty tiquetePasajero;
    private ObjectProperty<Image> tiqueteImagen;
    private IntegerProperty tiqueteAsiento;
    private DoubleProperty tiquetePrecio;
    private StringProperty tiqueteFecha;
    private StringProperty tiqueteMetodoPago;
    private BooleanProperty tiqueteEquipajeExtra;
    private StringProperty tiqueteReferencia;

    public VistaTiqueteCarrusel(Stage ventanaPadre, BorderPane princ, Pane pane,
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

        totalTiquetes = TiqueteControladorListar.obtenerCantidadTiquetes();
        
        if (totalTiquetes == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, 
                    miEscenario, "Sin tiquetes", 
                    "No hay tiquetes registrados para mostrar en el carrusel");
            return;
        }
        
        if (indiceActual >= totalTiquetes) {
            indiceActual = 0;
            indiceActualEstatico = 0;
        }
        
        objCargado = TiqueteControladorUna.obtenerTiquete(indiceActual);

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

        tiqueteTitulo = new SimpleStringProperty(
                "DETALLES DEL TIQUETE: (" + (indiceActual + 1) + " / " + totalTiquetes + ")");

        Label lblTitulo = new Label();
        lblTitulo.textProperty().bind(tiqueteTitulo);
        lblTitulo.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        lblTitulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 26));
        organizadorVertical.getChildren().add(lblTitulo);
    }

    private void construirPanelIzquierdo(double porcentaje) {
        Button btnAnterior = new Button();
        btnAnterior.setGraphic(Icono.obtenerIcono("btnAtras.png", 60));
        btnAnterior.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        btnAnterior.setCursor(Cursor.HAND);

        btnAnterior.setOnAction(e -> {
            indiceActual = obtenerIndice("Anterior", indiceActual, totalTiquetes);
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
            indiceActual = obtenerIndice("Siguiente", indiceActual, totalTiquetes);
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

        Rectangle miMarco = Marco.crear(miEscenario, 0.80, 0.70,
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

        // Botón Eliminar
        Button btnEliminar = new Button();
        btnEliminar.setPrefWidth(anchoBoton);
        btnEliminar.setCursor(Cursor.HAND);
        btnEliminar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_BORRAR, tamanioIcono));

        btnEliminar.setOnAction(e -> {
            String mensaje = "¿Seguro que desea eliminar este tiquete?\n\n"
                    + "Código: " + objCargado.getIdTiquete() + "\n"
                    + "Pasajero: " + objCargado.getPasajeroTiquete().getNombrePasajero() + 
                      " - " + objCargado.getPasajeroTiquete().getDocumentoPasajero()+ "\n"
                    + "Asiento: " + objCargado.getNumeroAsientoTiquete() + "\n\n"
                    + "Esta acción es irreversible.";

            Alert msg = new Alert(Alert.AlertType.CONFIRMATION);
            msg.setTitle("Confirmar Eliminación");
            msg.setHeaderText(null);
            msg.setContentText(mensaje);
            msg.initOwner(miEscenario);

            if (msg.showAndWait().get() == ButtonType.OK) {
                // Liberar la imagen antes de eliminar
                tiqueteImagen.set(null);
                System.gc(); // Sugerir recolección de basura
                
                if (TiqueteControladorEliminar.borrar(indiceActual)) {
                    totalTiquetes = TiqueteControladorListar.obtenerCantidadTiquetes();
                    
                    if (totalTiquetes > 0) {
                        if (indiceActual >= totalTiquetes) {
                            indiceActual = totalTiquetes - 1;
                            indiceActualEstatico = indiceActual;
                        }
                        actualizarContenido();
                        Mensaje.mostrar(Alert.AlertType.INFORMATION,
                                miEscenario, "Éxito", "Tiquete eliminado correctamente");
                    } else {
                        Mensaje.mostrar(Alert.AlertType.INFORMATION,
                                miEscenario, "Sin tiquetes", "No quedan tiquetes registrados");
                        indiceActualEstatico = 0;
                        panelCuerpo = TiqueteControladorVentana.administrar(
                                miEscenario, panelPrincipal, panelCuerpo,
                                Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO);
                        panelPrincipal.setCenter(panelCuerpo);
                    }
                } else {
                    Mensaje.mostrar(Alert.AlertType.ERROR,
                            miEscenario, "Error", "No se pudo eliminar el tiquete");
                }
            }
        });

        // Botón Actualizar
        Button btnActualizar = new Button();
        btnActualizar.setPrefWidth(anchoBoton);
        btnActualizar.setCursor(Cursor.HAND);
        btnActualizar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_EDITAR, tamanioIcono));

        btnActualizar.setOnAction(e -> {
            panelCuerpo = TiqueteControladorVentana.editar(
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
        int tamanioFuente = 16;

        // Código del tiquete
        Label lblCodigo = new Label("Tiquete #" + objCargado.getIdTiquete());
        lblCodigo.setFont(Font.font("Rockwell", FontWeight.BOLD, 24));
        lblCodigo.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblCodigo);

        // Imagen
        tiqueteImagen = new SimpleObjectProperty<>();
        try {
            String rutaImagen = Persistencia.RUTA_IMAGENES + Persistencia.SEPARADOR_CARPETAS
                    + objCargado.getNombreImagenPrivadoTiquete();
            FileInputStream imgArchivo = new FileInputStream(rutaImagen);
            Image imgNueva = new Image(imgArchivo);
            tiqueteImagen.set(imgNueva);

            ImageView imgMostrar = new ImageView(imgNueva);
            imgMostrar.setFitHeight(200);
            imgMostrar.setSmooth(true);
            imgMostrar.setPreserveRatio(true);
            imgMostrar.imageProperty().bind(tiqueteImagen);

            organizadorVertical.getChildren().add(imgMostrar);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VistaTiqueteCarrusel.class.getName()).log(Level.SEVERE, null, ex);
            ImageView imgDefault = Icono.obtenerIcono(Configuracion.ICONO_NO_DISPONIBLE, 150);
            organizadorVertical.getChildren().add(imgDefault);
        }

        // Viaje
        tiqueteViaje = new SimpleStringProperty("Viaje #" + objCargado.getViajeTiquete().getIdViaje() + 
                " - " + objCargado.getViajeTiquete().getRutaViaje().getNombreRuta());
        Label lblViaje = new Label();
        lblViaje.textProperty().bind(Bindings.concat("Viaje: ", tiqueteViaje));
        lblViaje.setFont(Font.font("Rockwell", tamanioFuente));
        lblViaje.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblViaje);

        // Pasajero
        tiquetePasajero = new SimpleStringProperty(objCargado.getPasajeroTiquete().getNombrePasajero() + 
                " - " + objCargado.getPasajeroTiquete().getDocumentoPasajero());
        Label lblPasajero = new Label();
        lblPasajero.textProperty().bind(Bindings.concat("Pasajero: ", tiquetePasajero));
        lblPasajero.setFont(Font.font("Rockwell", tamanioFuente));
        lblPasajero.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblPasajero);

        // Asiento
        tiqueteAsiento = new SimpleIntegerProperty(objCargado.getNumeroAsientoTiquete());
        Label lblAsiento = new Label();
        lblAsiento.textProperty().bind(Bindings.concat("Asiento: ", tiqueteAsiento.asString()));
        lblAsiento.setFont(Font.font("Rockwell", tamanioFuente));
        lblAsiento.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblAsiento);

        // Precio
        tiquetePrecio = new SimpleDoubleProperty(objCargado.getPrecioTiquete());
        Label lblPrecio = new Label();
        lblPrecio.textProperty().bind(Bindings.format("Precio: $%.2f", tiquetePrecio));
        lblPrecio.setFont(Font.font("Rockwell", tamanioFuente));
        lblPrecio.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblPrecio);

        // Fecha de Compra
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        tiqueteFecha = new SimpleStringProperty(objCargado.getFechaCompraTiquete().format(formatter));
        Label lblFecha = new Label();
        lblFecha.textProperty().bind(Bindings.concat("Fecha: ", tiqueteFecha));
        lblFecha.setFont(Font.font("Rockwell", tamanioFuente));
        lblFecha.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblFecha);

        // Método de Pago
        tiqueteMetodoPago = new SimpleStringProperty(
            objCargado.getMetodoPagoTiquete() != null ? objCargado.getMetodoPagoTiquete() : "N/A");
        Label lblMetodoPago = new Label();
        lblMetodoPago.textProperty().bind(Bindings.concat("Método de Pago: ", tiqueteMetodoPago));
        lblMetodoPago.setFont(Font.font("Rockwell", tamanioFuente));
        lblMetodoPago.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblMetodoPago);

        // Equipaje Extra
        tiqueteEquipajeExtra = new SimpleBooleanProperty(
            objCargado.getEquipajeExtraTiquete() != null ? objCargado.getEquipajeExtraTiquete() : false);
        Label lblEquipaje = new Label();
        lblEquipaje.textProperty().bind(
            Bindings.when(tiqueteEquipajeExtra).then("Con Equipaje Extra").otherwise("Sin Equipaje Extra"));
        lblEquipaje.setFont(Font.font("Rockwell", FontWeight.BOLD, tamanioFuente));
        lblEquipaje.textFillProperty().bind(
            tiqueteEquipajeExtra.map(dato -> dato ? Color.web(Configuracion.VERDE_EXITO) 
                    : Color.web(Configuracion.AZUL_OSCURO))
        );
        organizadorVertical.getChildren().add(lblEquipaje);

    }

    private void actualizarContenido() {
        objCargado = TiqueteControladorUna.obtenerTiquete(indiceActual);

        tiqueteTitulo.set("DETALLES DEL TIQUETE: (" + (indiceActual + 1) + " / " + totalTiquetes + ")");
        
        tiqueteViaje.set("Viaje #" + objCargado.getViajeTiquete().getIdViaje() + 
                " - " + objCargado.getViajeTiquete().getRutaViaje().getNombreRuta());
        tiquetePasajero.set(objCargado.getPasajeroTiquete().getNombrePasajero() + 
                " - " + objCargado.getPasajeroTiquete().getDocumentoPasajero());
        tiqueteAsiento.set(objCargado.getNumeroAsientoTiquete());
        tiquetePrecio.set(objCargado.getPrecioTiquete());
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        tiqueteFecha.set(objCargado.getFechaCompraTiquete().format(formatter));
        tiqueteMetodoPago.set(objCargado.getMetodoPagoTiquete() != null ? 
                objCargado.getMetodoPagoTiquete() : "N/A");
        tiqueteEquipajeExtra.set(objCargado.getEquipajeExtraTiquete() != null ? 
                objCargado.getEquipajeExtraTiquete() : false);

        // Actualizar imagen
        try {
            String rutaImagen = Persistencia.RUTA_IMAGENES + Persistencia.SEPARADOR_CARPETAS
                    + objCargado.getNombreImagenPrivadoTiquete();
            FileInputStream imgArchivo = new FileInputStream(rutaImagen);
            Image imgNueva = new Image(imgArchivo);
            tiqueteImagen.set(imgNueva);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VistaTiqueteCarrusel.class.getName()).log(Level.SEVERE, null, ex);
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
