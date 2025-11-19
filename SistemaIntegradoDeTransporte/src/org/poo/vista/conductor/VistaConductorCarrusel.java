package org.poo.vista.conductor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
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
import javafx.stage.Stage;
import org.poo.controlador.conductor.ConductorControladorEliminar;
import org.poo.controlador.conductor.ConductorControladorListar;
import org.poo.controlador.conductor.ConductorControladorUna;
import org.poo.controlador.conductor.ConductorControladorVentana;
import org.poo.dto.ConductorDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.constante.Persistencia;
import org.poo.recurso.utilidad.Icono;
import org.poo.recurso.utilidad.Marco;
import org.poo.recurso.utilidad.Mensaje;
import java.util.logging.Logger;
import java.util.logging.Level;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import org.poo.dto.EmpresaDto;

public class VistaConductorCarrusel extends  BorderPane{
    
    private final Stage miEscenario;
    private final BorderPane panelPrincipal;
    private Pane panelCuerpo;
    private final VBox organizadorVertical;

    private static int indiceActualEstatico = 0; // Mantener posición
    private int indiceActual;
    private int totalConductores;
    private ConductorDto objCargado;

    // Propiedades para binding
    private StringProperty conductorTitulo;
    private StringProperty conductorNombre;
    private StringProperty conductorCedula;
    private StringProperty conductorTelefono;
    private StringProperty conductorEmpresa;
    private StringProperty conductorLicencia;
    private StringProperty conductorFechaNac;
    private StringProperty conductorFechaVenc;
    private ObjectProperty<Image> conductorImagen;
    private BooleanProperty conductorEstado;

    public VistaConductorCarrusel(Stage ventanaPadre, BorderPane princ, Pane pane,
                                  double anchoPanel, double altoPanel, int indice) {

        miEscenario = ventanaPadre;
        panelPrincipal = princ;
        panelCuerpo = pane;

        // Usar índice estático si viene 0, si no usar el proporcionado
        if (indice == 0 && indiceActualEstatico > 0) {
            indiceActual = indiceActualEstatico;
        } else {
            indiceActual = indice;
            indiceActualEstatico = indice;
        }

        organizadorVertical = new VBox();

        totalConductores = ConductorControladorListar.obtenerCantidadConductores();

        // Validar existencia
        if (totalConductores == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING,
                    miEscenario, "Sin conductores",
                    "No hay conductores registrados para mostrar en el carrusel");
            return;
        }

        // Validar rango
        if (indiceActual >= totalConductores) {
            indiceActual = 0;
            indiceActualEstatico = 0;
        }

        objCargado = ConductorControladorUna.obtenerConductor(indiceActual);

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

        conductorTitulo = new SimpleStringProperty(
                "Detalle del Conductor (" + (indiceActual + 1) + " / " + totalConductores + ")");

        Label lblTitulo = new Label();
        lblTitulo.textProperty().bind(conductorTitulo);
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
            indiceActual = obtenerIndice("Anterior", indiceActual, totalConductores);
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
            indiceActual = obtenerIndice("Siguiente", indiceActual, totalConductores);
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
                Configuracion.DEGRADE_ARREGLO_CONDUCTOR,
                Configuracion.DEGRADE_BORDE);
        centerPane.getChildren().addAll(miMarco, organizadorVertical);

        panelOpciones();
        mostrarDatos();

        setCenter(centerPane);
    }

    private void panelOpciones() {
        int anchoBoton = 40;
        int tamanioIcono = 18;

        // Eliminar
        Button btnEliminar = new Button();
        btnEliminar.setPrefWidth(anchoBoton);
        btnEliminar.setCursor(Cursor.HAND);
        btnEliminar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_BORRAR, tamanioIcono));

        btnEliminar.setOnAction(e -> {
            String mensaje = "¿Seguro que desea eliminar este conductor?\n\n"
                    + "Código: " + objCargado.getIdConductor() + "\n"
                    + "Conductor: " + objCargado.getNombreConductor() + "\n"
                    + "Cédula: " + objCargado.getCedulaConductor() + "\n\n"
                    + "Esta acción es irreversible.";

            Alert msg = new Alert(Alert.AlertType.CONFIRMATION);
            msg.setTitle("Confirmar Eliminación");
            msg.setHeaderText(null);
            msg.setContentText(mensaje);
            msg.initOwner(miEscenario);

            if (msg.showAndWait().get() == ButtonType.OK) {
                if (ConductorControladorEliminar.borrar(indiceActual)) {
                    totalConductores = ConductorControladorListar.obtenerCantidadConductores();

                    if (totalConductores > 0) {
                        if (indiceActual >= totalConductores) {
                            indiceActual = totalConductores - 1;
                            indiceActualEstatico = indiceActual;
                        }
                        actualizarContenido();
                        Mensaje.mostrar(Alert.AlertType.INFORMATION,
                                miEscenario, "Éxito", "Conductor eliminado correctamente");
                    } else {
                        Mensaje.mostrar(Alert.AlertType.INFORMATION,
                                miEscenario, "Sin conductores", "No quedan conductores registrados");
                        indiceActualEstatico = 0;
                        panelCuerpo = ConductorControladorVentana.administrar(
                                miEscenario, panelPrincipal, panelCuerpo,
                                Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO);
                        panelPrincipal.setCenter(panelCuerpo);
                    }
                } else {
                    Mensaje.mostrar(Alert.AlertType.ERROR,
                            miEscenario, "Error", "No se pudo eliminar el conductor");
                }
            }
        });

        // Actualizar
        Button btnActualizar = new Button();
        btnActualizar.setPrefWidth(anchoBoton);
        btnActualizar.setCursor(Cursor.HAND);
        btnActualizar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_EDITAR, tamanioIcono));

        btnActualizar.setOnAction(e -> {
            panelCuerpo = ConductorControladorVentana.editar(
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

        // Nombre
        conductorNombre = new SimpleStringProperty(objCargado.getNombreConductor());
        Label lblNombre = new Label();
        lblNombre.textProperty().bind(conductorNombre);
        lblNombre.setFont(Font.font("Rockwell", FontWeight.BOLD, 24));
        lblNombre.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblNombre);

        // Imagen
        conductorImagen = new SimpleObjectProperty<>();
        try {
            String rutaImagen = Persistencia.RUTA_IMAGENES + Persistencia.SEPARADOR_CARPETAS
                    + objCargado.getNombreImagenPrivadoConductor();
            FileInputStream imgArchivo = new FileInputStream(rutaImagen);
            Image imgNueva = new Image(imgArchivo);
            conductorImagen.set(imgNueva);

            ImageView imgMostrar = new ImageView(imgNueva);
            imgMostrar.setFitHeight(200);
            imgMostrar.setSmooth(true);
            imgMostrar.setPreserveRatio(true);
            imgMostrar.imageProperty().bind(conductorImagen);

            organizadorVertical.getChildren().add(imgMostrar);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VistaConductorCarrusel.class.getName()).log(Level.SEVERE, null, ex);
            ImageView imgDefault = Icono.obtenerIcono(Configuracion.ICONO_NO_DISPONIBLE, 150);
            organizadorVertical.getChildren().add(imgDefault);
        }

        // Cédula
        conductorCedula = new SimpleStringProperty(objCargado.getCedulaConductor());
        Label lblCedula = new Label();
        lblCedula.textProperty().bind(Bindings.concat("Cédula: ", conductorCedula));
        lblCedula.setFont(Font.font("Rockwell", tamanioFuente));
        lblCedula.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblCedula);

        // Teléfono
        conductorTelefono = new SimpleStringProperty(objCargado.getTelefonoConductor());
        Label lblTelefono = new Label();
        lblTelefono.textProperty().bind(Bindings.concat("Teléfono: ", conductorTelefono));
        lblTelefono.setFont(Font.font("Rockwell", tamanioFuente));
        lblTelefono.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblTelefono);

        // Empresa
        EmpresaDto emp = objCargado.getEmpresaConductor();
        String nombreEmp = (emp != null) ? emp.getNombreEmpresa() : "Sin empresa";
        conductorEmpresa = new SimpleStringProperty(nombreEmp);
        Label lblEmpresa = new Label();
        lblEmpresa.textProperty().bind(Bindings.concat("Empresa: ", conductorEmpresa));
        lblEmpresa.setFont(Font.font("Rockwell", tamanioFuente));
        lblEmpresa.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblEmpresa);

        // Licencia
        conductorLicencia = new SimpleStringProperty(objCargado.getLicenciaConductor());
        Label lblLicencia = new Label();
        lblLicencia.textProperty().bind(Bindings.concat("Licencia: ", conductorLicencia));
        lblLicencia.setFont(Font.font("Rockwell", tamanioFuente));
        lblLicencia.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblLicencia);

        // Fecha nacimiento
        String fnacStr = objCargado.getFechaNacimientoConductor() != null
                ? objCargado.getFechaNacimientoConductor().toString()
                : "No registrada";
        conductorFechaNac = new SimpleStringProperty(fnacStr);
        Label lblFechaNac = new Label();
        lblFechaNac.textProperty().bind(Bindings.concat("Fecha de nacimiento: ", conductorFechaNac));
        lblFechaNac.setFont(Font.font("Rockwell", tamanioFuente));
        lblFechaNac.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblFechaNac);

        // Fecha venc. licencia
        String fvStr = objCargado.getFechaVencimientoLicencia() != null
                ? objCargado.getFechaVencimientoLicencia().toString()
                : "No registrada";
        conductorFechaVenc = new SimpleStringProperty(fvStr);
        Label lblFechaVenc = new Label();
        lblFechaVenc.textProperty().bind(Bindings.concat("Vence licencia: ", conductorFechaVenc));
        lblFechaVenc.setFont(Font.font("Rockwell", tamanioFuente));
        lblFechaVenc.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblFechaVenc);

        // Estado
        conductorEstado = new SimpleBooleanProperty(objCargado.getEstadoConductor());
        Label lblEstado = new Label();
        lblEstado.textProperty().bind(Bindings.when(conductorEstado).then("Activo").otherwise("Inactivo"));
        lblEstado.setFont(Font.font("Rockwell", FontWeight.BOLD, 20));
        lblEstado.textFillProperty().bind(
                conductorEstado.map(dato -> dato ? Color.web(Configuracion.VERDE_EXITO)
                        : Color.web(Configuracion.ROJO_ERROR))
        );
        organizadorVertical.getChildren().add(lblEstado);
    }

    private void actualizarContenido() {
        objCargado = ConductorControladorUna.obtenerConductor(indiceActual);

        conductorTitulo.set("Detalle del Conductor (" + (indiceActual + 1) + " / " + totalConductores + ")");
        conductorNombre.set(objCargado.getNombreConductor());
        conductorCedula.set(objCargado.getCedulaConductor());
        conductorTelefono.set(objCargado.getTelefonoConductor());

        EmpresaDto emp = objCargado.getEmpresaConductor();
        String nombreEmp = (emp != null) ? emp.getNombreEmpresa() : "Sin empresa";
        conductorEmpresa.set(nombreEmp);

        conductorLicencia.set(objCargado.getLicenciaConductor());

        String fnacStr = objCargado.getFechaNacimientoConductor() != null
                ? objCargado.getFechaNacimientoConductor().toString()
                : "No registrada";
        conductorFechaNac.set(fnacStr);

        String fvStr = objCargado.getFechaVencimientoLicencia() != null
                ? objCargado.getFechaVencimientoLicencia().toString()
                : "No registrada";
        conductorFechaVenc.set(fvStr);

        conductorEstado.set(objCargado.getEstadoConductor());

        // Actualizar imagen
        try {
            String rutaImagen = Persistencia.RUTA_IMAGENES + Persistencia.SEPARADOR_CARPETAS
                    + objCargado.getNombreImagenPrivadoConductor();
            FileInputStream imgArchivo = new FileInputStream(rutaImagen);
            Image imgNueva = new Image(imgArchivo);
            conductorImagen.set(imgNueva);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VistaConductorCarrusel.class.getName()).log(Level.SEVERE, null, ex);
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
