package org.poo.vista.terminal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.poo.controlador.terminal.TerminalControladorEliminar;
import org.poo.controlador.terminal.TerminalControladorListar;
import org.poo.controlador.terminal.TerminalControladorUna;
import org.poo.controlador.terminal.TerminalControladorVentana;
import org.poo.dto.TerminalDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.constante.Persistencia;
import org.poo.recurso.utilidad.Fondo;
import org.poo.recurso.utilidad.Icono;
import org.poo.recurso.utilidad.Marco;
import org.poo.recurso.utilidad.Mensaje;

/**
 * Vista de carrusel para navegar entre terminales
 */
public class VistaTerminalCarrusel extends BorderPane {

    private final Stage miEscenario;
    private final BorderPane panelPrincipal;
    private Pane panelCuerpo;
    private final VBox organizadorVertical;

    private int indiceActual;
    private int totalTerminales;
    private TerminalDto objCargado;

    private StringProperty terminalTitulo;
    private StringProperty terminalNombre;
    private StringProperty terminalCiudad;
    private StringProperty terminalDireccion;
    private ObjectProperty<Image> terminalImagen;
    private BooleanProperty terminalEstado;
    private IntegerProperty terminalCantEmpresas;
    private IntegerProperty terminalPlataformas;
    private StringProperty terminalServicios;

    public VistaTerminalCarrusel(Stage ventanaPadre, BorderPane princ, Pane pane,
            double anchoPanel, double altoPanel, int indice) {

        miEscenario = ventanaPadre;
        panelPrincipal = princ;
        panelCuerpo = pane;
        indiceActual = indice;

        totalTerminales = TerminalControladorListar.obtenerCantidadTerminales();
        
        // Validar que haya terminales
        if (totalTerminales == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, 
                    miEscenario, "Sin terminales", 
                    "No hay terminales registradas para mostrar en el carrusel");
            return;
        }
        
        objCargado = TerminalControladorUna.obtenerTerminal(indiceActual);

        organizadorVertical = new VBox();
        configurarOrganizadorVertical();

        crearTitulo();
        construirPanelIzquierdo(0.14);
        construirPanelDerecho(0.14);
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

        terminalTitulo = new SimpleStringProperty(
                "Detalle de la Terminal (" + (indiceActual + 1) + " / " + totalTerminales + ")");

        Label lblTitulo = new Label();
        lblTitulo.textProperty().bind(terminalTitulo);
        lblTitulo.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        organizadorVertical.getChildren().add(lblTitulo);
    }

    private void construirPanelIzquierdo(double porcentaje) {
        Button btnAnterior = new Button();
        btnAnterior.setGraphic(Icono.obtenerIcono("btnAtras.png", 80));
        btnAnterior.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        btnAnterior.setCursor(Cursor.HAND);

        btnAnterior.setOnAction(e -> {
            indiceActual = obtenerIndice("Anterior", indiceActual, totalTerminales);
            actualizarContenido();
        });

        StackPane panelIzquierdo = new StackPane();
        panelIzquierdo.prefWidthProperty().bind(miEscenario.widthProperty().multiply(porcentaje));
        panelIzquierdo.getChildren().add(btnAnterior);
        setLeft(panelIzquierdo);
    }

    private void construirPanelDerecho(double porcentaje) {
        Button btnSiguiente = new Button();
        btnSiguiente.setGraphic(Icono.obtenerIcono("btnSiguiente.png", 80));
        btnSiguiente.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        btnSiguiente.setCursor(Cursor.HAND);

        btnSiguiente.setOnAction(e -> {
            indiceActual = obtenerIndice("Siguiente", indiceActual, totalTerminales);
            actualizarContenido();
        });

        StackPane panelDerecho = new StackPane();
        panelDerecho.prefWidthProperty().bind(miEscenario.widthProperty().multiply(porcentaje));
        panelDerecho.getChildren().add(btnSiguiente);
        setRight(panelDerecho);
    }

    private void construirPanelCentro() {
        StackPane centerPane = new StackPane();

        // Marco
        Rectangle miMarco = Marco.crear(miEscenario, 0.70, 0.80,
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
            if (objCargado.getCantidadEmpresasTerminal() == 0) {
                String mensaje = "¿Seguro que desea eliminar esta terminal?\n\n"
                        + "Código: " + objCargado.getIdTerminal() + "\n"
                        + "Terminal: " + objCargado.getNombreTerminal() + "\n\n"
                        + "Esta acción es irreversible.";

                Alert msg = new Alert(Alert.AlertType.CONFIRMATION);
                msg.setTitle("Confirmar Eliminación");
                msg.setHeaderText(null);
                msg.setContentText(mensaje);
                msg.initOwner(miEscenario);

                if (msg.showAndWait().get() == ButtonType.OK) {
                    if (TerminalControladorEliminar.borrar(indiceActual)) {
                        totalTerminales = TerminalControladorListar.obtenerCantidadTerminales();
                        
                        if (totalTerminales > 0) {
                            if (indiceActual >= totalTerminales) {
                                indiceActual = totalTerminales - 1;
                            }
                            actualizarContenido();
                            Mensaje.mostrar(Alert.AlertType.INFORMATION,
                                    miEscenario, "Éxito", "Terminal eliminada correctamente");
                        } else {
                            Mensaje.mostrar(Alert.AlertType.INFORMATION,
                                    miEscenario, "Sin terminales", "No quedan terminales registradas");
                            // Volver a la vista de administrar
                            panelCuerpo = TerminalControladorVentana.administrar(
                                    miEscenario, panelPrincipal, panelCuerpo,
                                    Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO);
                            panelPrincipal.setCenter(panelCuerpo);
                        }
                    } else {
                        Mensaje.mostrar(Alert.AlertType.ERROR,
                                miEscenario, "Error", "No se pudo eliminar la terminal");
                    }
                }
            } else {
                Mensaje.mostrar(Alert.AlertType.ERROR,
                        miEscenario, "Error",
                        "No se puede eliminar una terminal con empresas asociadas");
            }
        });

        // Botón Actualizar
        Button btnActualizar = new Button();
        btnActualizar.setPrefWidth(anchoBoton);
        btnActualizar.setCursor(Cursor.HAND);
        btnActualizar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_EDITAR, tamanioIcono));

        btnActualizar.setOnAction(e -> {
            panelCuerpo = TerminalControladorVentana.editar(
                    miEscenario,
                    panelPrincipal,
                    panelCuerpo,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO,
                    objCargado,
                    indiceActual);
                    
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

        // Nombre
        terminalNombre = new SimpleStringProperty(objCargado.getNombreTerminal());
        Label lblNombre = new Label();
        lblNombre.textProperty().bind(terminalNombre);
        lblNombre.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        lblNombre.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblNombre);

        // Imagen
        terminalImagen = new SimpleObjectProperty<>();
        try {
            String rutaImagen = Persistencia.RUTA_IMAGENES + Persistencia.SEPARADOR_CARPETAS
                    + objCargado.getNombreImagenPrivadoTerminal();
            FileInputStream imgArchivo = new FileInputStream(rutaImagen);
            Image imgNueva = new Image(imgArchivo);
            terminalImagen.set(imgNueva);

            ImageView imgMostrar = new ImageView(imgNueva);
            imgMostrar.setFitHeight(200);
            imgMostrar.setSmooth(true);
            imgMostrar.setPreserveRatio(true);
            imgMostrar.imageProperty().bind(terminalImagen);

            organizadorVertical.getChildren().add(imgMostrar);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VistaTerminalCarrusel.class.getName()).log(Level.SEVERE, null, ex);
            // Mostrar imagen por defecto
            ImageView imgDefault = Icono.obtenerIcono(Configuracion.ICONO_NO_DISPONIBLE, 200);
            organizadorVertical.getChildren().add(imgDefault);
        }

        // Ciudad
        terminalCiudad = new SimpleStringProperty(objCargado.getCiudadTerminal());
        Label lblCiudad = new Label();
        lblCiudad.textProperty().bind(Bindings.concat("📍 Ciudad: ", terminalCiudad));
        lblCiudad.setFont(Font.font("Arial", tamanioFuente));
        lblCiudad.setTextFill(Color.web(Configuracion.AZUL_MEDIO));
        organizadorVertical.getChildren().add(lblCiudad);

        // Dirección
        terminalDireccion = new SimpleStringProperty(objCargado.getDireccionTerminal());
        Label lblDireccion = new Label();
        lblDireccion.textProperty().bind(Bindings.concat("📌 Dirección: ", terminalDireccion));
        lblDireccion.setFont(Font.font("Arial", tamanioFuente));
        lblDireccion.setTextFill(Color.web(Configuracion.AZUL_MEDIO));
        organizadorVertical.getChildren().add(lblDireccion);

        // Plataformas
        terminalPlataformas = new SimpleIntegerProperty(
            objCargado.getNumeroPlataformas() != null ? objCargado.getNumeroPlataformas() : 0);
        Label lblPlataformas = new Label();
        lblPlataformas.textProperty().bind(Bindings.concat("🚌 Plataformas: ", terminalPlataformas.asString()));
        lblPlataformas.setFont(Font.font("Arial", tamanioFuente));
        lblPlataformas.setTextFill(Color.web(Configuracion.AZUL_MEDIO));
        organizadorVertical.getChildren().add(lblPlataformas);

        // Servicios
        terminalServicios = new SimpleStringProperty(construirTextoServicios());
        Label lblServicios = new Label();
        lblServicios.textProperty().bind(Bindings.concat("✨ Servicios: ", terminalServicios));
        lblServicios.setFont(Font.font("Arial", tamanioFuente));
        lblServicios.setTextFill(Color.web(Configuracion.AZUL_MEDIO));
        organizadorVertical.getChildren().add(lblServicios);

        // Estado
        terminalEstado = new SimpleBooleanProperty(objCargado.getEstadoTerminal());
        Label lblEstado = new Label();
        lblEstado.textProperty().bind(Bindings.when(terminalEstado).then("✅ ACTIVO").otherwise("❌ INACTIVO"));
        lblEstado.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        lblEstado.textFillProperty().bind(
                terminalEstado.map(dato -> dato ? Color.web(Configuracion.VERDE_EXITO) 
                        : Color.web(Configuracion.ROJO_ERROR))
        );
        organizadorVertical.getChildren().add(lblEstado);

        // Cantidad de empresas
        terminalCantEmpresas = new SimpleIntegerProperty(objCargado.getCantidadEmpresasTerminal());
        Label lblCantEmpresas = new Label();
        lblCantEmpresas.textProperty().bind(Bindings.concat("🏢 Empresas asociadas: ", terminalCantEmpresas.asString()));
        lblCantEmpresas.setFont(Font.font("Arial", tamanioFuente));
        lblCantEmpresas.setTextFill(Color.web(Configuracion.AZUL_MEDIO));
        organizadorVertical.getChildren().add(lblCantEmpresas);
    }

    private String construirTextoServicios() {
        StringBuilder servicios = new StringBuilder();
        
        if (objCargado.getTieneWifi() != null && objCargado.getTieneWifi()) {
            servicios.append("WiFi");
        }
        
        if (objCargado.getTieneCafeteria() != null && objCargado.getTieneCafeteria()) {
            if (servicios.length() > 0) servicios.append(", ");
            servicios.append("Cafetería");
        }
        
        if (objCargado.getTieneBanos() != null && objCargado.getTieneBanos()) {
            if (servicios.length() > 0) servicios.append(", ");
            servicios.append("Baños");
        }
        
        return servicios.length() > 0 ? servicios.toString() : "Ninguno";
    }

    private void actualizarContenido() {
        objCargado = TerminalControladorUna.obtenerTerminal(indiceActual);

        terminalTitulo.set("Detalle de la Terminal (" + (indiceActual + 1) + " / " + totalTerminales + ")");
        terminalNombre.set(objCargado.getNombreTerminal());
        terminalCiudad.set(objCargado.getCiudadTerminal());
        terminalDireccion.set(objCargado.getDireccionTerminal());
        terminalEstado.set(objCargado.getEstadoTerminal());
        terminalCantEmpresas.set(objCargado.getCantidadEmpresasTerminal());
        terminalPlataformas.set(objCargado.getNumeroPlataformas() != null ? objCargado.getNumeroPlataformas() : 0);
        terminalServicios.set(construirTextoServicios());

        // Actualizar imagen
        try {
            String rutaImagen = Persistencia.RUTA_IMAGENES + Persistencia.SEPARADOR_CARPETAS
                    + objCargado.getNombreImagenPrivadoTerminal();
            FileInputStream imgArchivo = new FileInputStream(rutaImagen);
            Image imgNueva = new Image(imgArchivo);
            terminalImagen.set(imgNueva);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VistaTerminalCarrusel.class.getName()).log(Level.SEVERE, null, ex);
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
}
