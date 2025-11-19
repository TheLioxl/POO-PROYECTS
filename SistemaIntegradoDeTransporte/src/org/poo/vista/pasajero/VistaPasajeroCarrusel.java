package org.poo.vista.pasajero;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


import org.poo.controlador.pasajero.PasajeroControladorEliminar;
import org.poo.controlador.pasajero.PasajeroControladorListar;
import org.poo.controlador.pasajero.PasajeroControladorUna;
import org.poo.controlador.pasajero.PasajeroControladorVentana;
import org.poo.dto.PasajeroDto;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.constante.Persistencia;
import org.poo.recurso.utilidad.Icono;
import org.poo.recurso.utilidad.Marco;
import org.poo.recurso.utilidad.Mensaje;

public class VistaPasajeroCarrusel extends BorderPane {

    private final Stage miEscenario;
    private final BorderPane panelPrincipal;
    private Pane panelCuerpo;
    private final VBox organizadorVertical;

    private static int indiceActualEstatico = 0;
    private int indiceActual;
    private int totalPasajeros;
    private PasajeroDto objCargado;

    private StringProperty pasajeroTitulo;
    private StringProperty pasajeroNombre;
    private StringProperty pasajeroDocumento;
    private StringProperty pasajeroTipoDocumento;
    private StringProperty pasajeroFechaNac;
    private StringProperty pasajeroTelefono;
    private BooleanProperty pasajeroEsMayor;
    private ObjectProperty<Image> pasajeroImagen;

    private final DateTimeFormatter FORMATO_FECHA
            = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public VistaPasajeroCarrusel(Stage ventanaPadre, BorderPane princ, Pane pane,
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

        totalPasajeros = PasajeroControladorListar.obtenerCantidadPasajeros();

        if (totalPasajeros == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING,
                    miEscenario, "Sin pasajeros",
                    "No hay pasajeros registrados para mostrar en el carrusel");
            return;
        }

        if (indiceActual >= totalPasajeros) {
            indiceActual = 0;
            indiceActualEstatico = 0;
        }

        objCargado = PasajeroControladorUna.obtenerPasajero(indiceActual);

        configurarOrganizadorVertical();
        crearTitulo();
        construirPanelIzquierdo(0.10);
        construirPanelDerecho(0.10);
        construirPanelCentro();
    }

    private void configurarOrganizadorVertical() {
        organizadorVertical.setSpacing(10);
        organizadorVertical.setAlignment(Pos.TOP_CENTER);
        organizadorVertical.prefWidthProperty().bind(miEscenario.widthProperty());
        organizadorVertical.prefHeightProperty().bind(miEscenario.heightProperty());
    }

    private void crearTitulo() {
        Region bloqueSeparador = new Region();
        bloqueSeparador.prefHeightProperty().bind(miEscenario.heightProperty().multiply(0.08));
        organizadorVertical.getChildren().add(0, bloqueSeparador);

        pasajeroTitulo = new SimpleStringProperty(
                "Detalle del Pasajero (" + (indiceActual + 1) + " / " + totalPasajeros + ")");

        Label lblTitulo = new Label();
        lblTitulo.textProperty().bind(pasajeroTitulo);
        lblTitulo.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        lblTitulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 26));
        organizadorVertical.getChildren().add(lblTitulo);
    }

    private void construirPanelIzquierdo(double porcentaje) {
        Button btnAnterior = new Button();
        btnAnterior.setGraphic(Icono.obtenerIcono("btnAtras.png", 60));
        btnAnterior.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        btnAnterior.setCursor(javafx.scene.Cursor.HAND);

        btnAnterior.setOnAction(e -> {
            indiceActual = obtenerIndice("Anterior", indiceActual, totalPasajeros);
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
        btnSiguiente.setCursor(javafx.scene.Cursor.HAND);

        btnSiguiente.setOnAction(e -> {
            indiceActual = obtenerIndice("Siguiente", indiceActual, totalPasajeros);
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
                Configuracion.DEGRADE_ARREGLO_PASAJERO, // crea este degrade en Configuracion
                Configuracion.DEGRADE_BORDE);
        centerPane.getChildren().addAll(miMarco, organizadorVertical);

        panelOpciones();
        mostrarDatos();

        setCenter(centerPane);
    }

    private void panelOpciones() {
        int anchoBoton = 40;
        int tamanioIcono = 18;

        // ELIMINAR
        Button btnEliminar = new Button();
        btnEliminar.setPrefWidth(anchoBoton);
        btnEliminar.setCursor(javafx.scene.Cursor.HAND);
        btnEliminar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_BORRAR, tamanioIcono));

        btnEliminar.setOnAction(e -> {
            String mensaje = "¿Seguro que desea eliminar este pasajero?\n\n"
                    + "Código: " + objCargado.getIdPasajero() + "\n"
                    + "Nombre: " + objCargado.getNombrePasajero() + "\n"
                    + "Documento: " + objCargado.getDocumentoPasajero() + "\n\n"
                    + "Esta acción es irreversible.";

            Alert msg = new Alert(Alert.AlertType.CONFIRMATION);
            msg.setTitle("Confirmar Eliminación");
            msg.setHeaderText(null);
            msg.setContentText(mensaje);
            msg.initOwner(miEscenario);

            if (msg.showAndWait().get() == ButtonType.OK) {
                if (PasajeroControladorEliminar.borrar(indiceActual)) {
                    totalPasajeros = PasajeroControladorListar.obtenerCantidadPasajeros();

                    if (totalPasajeros > 0) {
                        if (indiceActual >= totalPasajeros) {
                            indiceActual = totalPasajeros - 1;
                            indiceActualEstatico = indiceActual;
                        }
                        actualizarContenido();
                        Mensaje.mostrar(Alert.AlertType.INFORMATION,
                                miEscenario, "Éxito", "Pasajero eliminado correctamente");
                    } else {
                        Mensaje.mostrar(Alert.AlertType.INFORMATION,
                                miEscenario, "Sin pasajeros", "No quedan pasajeros registrados");
                        indiceActualEstatico = 0;
                        panelCuerpo = PasajeroControladorVentana.administrar(
                                miEscenario, panelPrincipal, panelCuerpo,
                                Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO);
                        panelPrincipal.setCenter(panelCuerpo);
                    }
                } else {
                    Mensaje.mostrar(Alert.AlertType.ERROR,
                            miEscenario, "Error", "No se pudo eliminar el pasajero");
                }
            }
        });

        // ACTUALIZAR
        Button btnActualizar = new Button();
        btnActualizar.setPrefWidth(anchoBoton);
        btnActualizar.setCursor(javafx.scene.Cursor.HAND);
        btnActualizar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_EDITAR, tamanioIcono));

        btnActualizar.setOnAction(e -> {
            panelCuerpo = PasajeroControladorVentana.editar(
                    miEscenario,
                    panelPrincipal,
                    panelCuerpo,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO,
                    objCargado,
                    indiceActual,
                    true); // viene del carrusel

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

        // NOMBRE
        pasajeroNombre = new SimpleStringProperty(objCargado.getNombrePasajero());
        Label lblNombre = new Label();
        lblNombre.textProperty().bind(pasajeroNombre);
        lblNombre.setFont(Font.font("Rockwell", FontWeight.BOLD, 24));
        lblNombre.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblNombre);

        // IMAGEN (miniatura – nombre PRIVADO del documento)
        pasajeroImagen = new SimpleObjectProperty<>();
        try {
            String rutaImagen = Persistencia.RUTA_IMAGENES + Persistencia.SEPARADOR_CARPETAS
                    + objCargado.getNombreImagenPrivadoPasajero();
            FileInputStream imgArchivo = new FileInputStream(rutaImagen);
            Image imgNueva = new Image(imgArchivo);
            pasajeroImagen.set(imgNueva);

            ImageView imgMostrar = new ImageView(imgNueva);
            imgMostrar.setFitHeight(200);
            imgMostrar.setSmooth(true);
            imgMostrar.setPreserveRatio(true);
            imgMostrar.imageProperty().bind(pasajeroImagen);

            organizadorVertical.getChildren().add(imgMostrar);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VistaPasajeroCarrusel.class.getName()).log(Level.SEVERE, null, ex);
            ImageView imgDefault = Icono.obtenerIcono(Configuracion.ICONO_NO_DISPONIBLE, 150);
            organizadorVertical.getChildren().add(imgDefault);
        }

        // DOCUMENTO
        pasajeroDocumento = new SimpleStringProperty(objCargado.getDocumentoPasajero());
        pasajeroTipoDocumento = new SimpleStringProperty(
                objCargado.getTipoDocumentoPasajero() != null
                ? objCargado.getTipoDocumentoPasajero()
                : "N/D");
        Label lblDocumento = new Label();
        lblDocumento.textProperty().bind(
                Bindings.concat("Documento (", pasajeroTipoDocumento, "): ", pasajeroDocumento));
        lblDocumento.setFont(Font.font("Rockwell", tamanioFuente));
        lblDocumento.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblDocumento);

        // FECHA NACIMIENTO
        String fechaNacTxt = (objCargado.getFechaNacimientoPasajero() != null)
                ? objCargado.getFechaNacimientoPasajero().format(FORMATO_FECHA)
                : "Sin registrar";
        pasajeroFechaNac = new SimpleStringProperty(fechaNacTxt);

        Label lblFechaNac = new Label();
        lblFechaNac.textProperty().bind(
                Bindings.concat("Fecha de nacimiento: ", pasajeroFechaNac));
        lblFechaNac.setFont(Font.font("Rockwell", tamanioFuente));
        lblFechaNac.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblFechaNac);

        // ES MAYOR / MENOR
        pasajeroEsMayor = new SimpleBooleanProperty(
                objCargado.getEsMayorPasajero() != null && objCargado.getEsMayorPasajero());

        Label lblTipoPasajero = new Label();
        lblTipoPasajero.textProperty().bind(
                Bindings.when(pasajeroEsMayor)
                        .then("Tipo de pasajero: Mayor de edad")
                        .otherwise("Tipo de pasajero: Menor de edad"));
        lblTipoPasajero.setFont(Font.font("Rockwell", tamanioFuente));
        lblTipoPasajero.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblTipoPasajero);

        // TELÉFONO
        pasajeroTelefono = new SimpleStringProperty(objCargado.getTelefonoPasajero());
        Label lblTelefono = new Label();
        lblTelefono.textProperty().bind(
                Bindings.concat("Teléfono: ", pasajeroTelefono));
        lblTelefono.setFont(Font.font("Rockwell", tamanioFuente));
        lblTelefono.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblTelefono);
    }

    private void actualizarContenido() {
        objCargado = PasajeroControladorUna.obtenerPasajero(indiceActual);

        pasajeroTitulo.set("Detalle del Pasajero (" + (indiceActual + 1) + " / " + totalPasajeros + ")");
        pasajeroNombre.set(objCargado.getNombrePasajero());
        pasajeroDocumento.set(objCargado.getDocumentoPasajero());
        pasajeroTipoDocumento.set(
                objCargado.getTipoDocumentoPasajero() != null
                ? objCargado.getTipoDocumentoPasajero()
                : "N/D");

        String fechaNacTxt = (objCargado.getFechaNacimientoPasajero() != null)
                ? objCargado.getFechaNacimientoPasajero().format(FORMATO_FECHA)
                : "Sin registrar";
        pasajeroFechaNac.set(fechaNacTxt);

        pasajeroTelefono.set(objCargado.getTelefonoPasajero());
        pasajeroEsMayor.set(objCargado.getEsMayorPasajero() != null
                && objCargado.getEsMayorPasajero());

        try {
            String rutaImagen = Persistencia.RUTA_IMAGENES + Persistencia.SEPARADOR_CARPETAS
                    + objCargado.getNombreImagenPrivadoPasajero();
            FileInputStream imgArchivo = new FileInputStream(rutaImagen);
            Image imgNueva = new Image(imgArchivo);
            pasajeroImagen.set(imgNueva);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VistaPasajeroCarrusel.class.getName()).log(Level.SEVERE, null, ex);
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
