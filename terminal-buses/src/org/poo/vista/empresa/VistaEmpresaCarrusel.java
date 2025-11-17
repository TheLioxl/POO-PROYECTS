package org.poo.vista.empresa;

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
import org.poo.controlador.empresa.*;
import org.poo.dto.EmpresaDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.constante.Persistencia;
import org.poo.recurso.utilidad.*;

public class VistaEmpresaCarrusel extends BorderPane {

    private final Stage miEscenario;
    private final BorderPane panelPrincipal;
    private Pane panelCuerpo;
    private final VBox organizadorVertical;

    private static int indiceActualEstatico = 0;
    private int indiceActual;
    private int totalEmpresas;
    private EmpresaDto objCargado;

    private StringProperty empresaTitulo;
    private StringProperty empresaNombre;
    private StringProperty empresaNit;
    private StringProperty empresaTerminal;
    private ObjectProperty<Image> empresaImagen;
    private BooleanProperty empresaEstado;
    private IntegerProperty empresaCantBuses;

    public VistaEmpresaCarrusel(Stage ventanaPadre, BorderPane princ, Pane pane,
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

        totalEmpresas = EmpresaControladorListar.obtenerCantidadEmpresas();
        
        if (totalEmpresas == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, 
                    miEscenario, "Sin empresas", 
                    "No hay empresas registradas para mostrar en el carrusel");
            return;
        }
        
        if (indiceActual >= totalEmpresas) {
            indiceActual = 0;
            indiceActualEstatico = 0;
        }
        
        objCargado = EmpresaControladorUna.obtenerEmpresa(indiceActual);

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

        empresaTitulo = new SimpleStringProperty(
                "Detalle de la Empresa (" + (indiceActual + 1) + " / " + totalEmpresas + ")");

        Label lblTitulo = new Label();
        lblTitulo.textProperty().bind(empresaTitulo);
        lblTitulo.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        organizadorVertical.getChildren().add(lblTitulo);
    }

    private void construirPanelIzquierdo(double porcentaje) {
        Button btnAnterior = new Button();
        btnAnterior.setGraphic(Icono.obtenerIcono("btnAtras.png", 60));
        btnAnterior.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        btnAnterior.setCursor(Cursor.HAND);

        btnAnterior.setOnAction(e -> {
            indiceActual = obtenerIndice("Anterior", indiceActual, totalEmpresas);
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
            indiceActual = obtenerIndice("Siguiente", indiceActual, totalEmpresas);
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

        Rectangle miMarco = Marco.crear(miEscenario, 0.70, 0.80,
                Configuracion.DEGRADE_ARREGLO_EMPRESA,
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
            if (objCargado.getCantidadBusesEmpresa() == 0) {
                String mensaje = "¿Seguro que desea eliminar esta empresa?\n\n"
                        + "Código: " + objCargado.getIdEmpresa() + "\n"
                        + "Empresa: " + objCargado.getNombreEmpresa() + "\n\n"
                        + "Esta acción es irreversible.";

                Alert msg = new Alert(Alert.AlertType.CONFIRMATION);
                msg.setTitle("Confirmar Eliminación");
                msg.setHeaderText(null);
                msg.setContentText(mensaje);
                msg.initOwner(miEscenario);

                if (msg.showAndWait().get() == ButtonType.OK) {
                    if (EmpresaControladorEliminar.borrar(indiceActual)) {
                        totalEmpresas = EmpresaControladorListar.obtenerCantidadEmpresas();
                        
                        if (totalEmpresas > 0) {
                            if (indiceActual >= totalEmpresas) {
                                indiceActual = totalEmpresas - 1;
                                indiceActualEstatico = indiceActual;
                            }
                            actualizarContenido();
                            Mensaje.mostrar(Alert.AlertType.INFORMATION,
                                    miEscenario, "Éxito", "Empresa eliminada correctamente");
                        } else {
                            Mensaje.mostrar(Alert.AlertType.INFORMATION,
                                    miEscenario, "Sin empresas", "No quedan empresas registradas");
                            indiceActualEstatico = 0;
                            panelCuerpo = EmpresaControladorVentana.administrar(
                                    miEscenario, panelPrincipal, panelCuerpo,
                                    Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO);
                            panelPrincipal.setCenter(panelCuerpo);
                        }
                    } else {
                        Mensaje.mostrar(Alert.AlertType.ERROR,
                                miEscenario, "Error", "No se pudo eliminar la empresa");
                    }
                }
            } else {
                Mensaje.mostrar(Alert.AlertType.ERROR,
                        miEscenario, "Error",
                        "No se puede eliminar una empresa con buses asociados");
            }
        });

        Button btnActualizar = new Button();
        btnActualizar.setPrefWidth(anchoBoton);
        btnActualizar.setCursor(Cursor.HAND);
        btnActualizar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_EDITAR, tamanioIcono));

        btnActualizar.setOnAction(e -> {
            panelCuerpo = EmpresaControladorVentana.editar(
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

        // Nombre
        empresaNombre = new SimpleStringProperty(objCargado.getNombreEmpresa());
        Label lblNombre = new Label();
        lblNombre.textProperty().bind(empresaNombre);
        lblNombre.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        lblNombre.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblNombre);

        // Imagen
        empresaImagen = new SimpleObjectProperty<>();
        try {
            String rutaImagen = Persistencia.RUTA_IMAGENES + Persistencia.SEPARADOR_CARPETAS
                    + objCargado.getNombreImagenPrivadoEmpresa();
            FileInputStream imgArchivo = new FileInputStream(rutaImagen);
            Image imgNueva = new Image(imgArchivo);
            empresaImagen.set(imgNueva);

            ImageView imgMostrar = new ImageView(imgNueva);
            imgMostrar.setFitHeight(200);
            imgMostrar.setSmooth(true);
            imgMostrar.setPreserveRatio(true);
            imgMostrar.imageProperty().bind(empresaImagen);

            organizadorVertical.getChildren().add(imgMostrar);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VistaEmpresaCarrusel.class.getName()).log(Level.SEVERE, null, ex);
            ImageView imgDefault = Icono.obtenerIcono(Configuracion.ICONO_NO_DISPONIBLE, 200);
            organizadorVertical.getChildren().add(imgDefault);
        }

        // NIT
        empresaNit = new SimpleStringProperty(objCargado.getNitEmpresa());
        Label lblNit = new Label();
        lblNit.textProperty().bind(Bindings.concat("🆔 NIT: ", empresaNit));
        lblNit.setFont(Font.font("Arial", tamanioFuente));
        lblNit.setTextFill(Color.web(Configuracion.AZUL_MEDIO));
        organizadorVertical.getChildren().add(lblNit);

        // Terminal
        empresaTerminal = new SimpleStringProperty(
            objCargado.getTerminalEmpresa().getNombreTerminal());
        Label lblTerminal = new Label();
        lblTerminal.textProperty().bind(Bindings.concat("🏢 Terminal: ", empresaTerminal));
        lblTerminal.setFont(Font.font("Arial", tamanioFuente));
        lblTerminal.setTextFill(Color.web(Configuracion.AZUL_MEDIO));
        organizadorVertical.getChildren().add(lblTerminal);

        // Estado
        empresaEstado = new SimpleBooleanProperty(objCargado.getEstadoEmpresa());
        Label lblEstado = new Label();
        lblEstado.textProperty().bind(Bindings.when(empresaEstado).then("✅ ACTIVO").otherwise("❌ INACTIVO"));
        lblEstado.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        lblEstado.textFillProperty().bind(
                empresaEstado.map(dato -> dato ? Color.web(Configuracion.VERDE_EXITO) 
                        : Color.web(Configuracion.ROJO_ERROR))
        );
        organizadorVertical.getChildren().add(lblEstado);

        // Cantidad de buses
        empresaCantBuses = new SimpleIntegerProperty(objCargado.getCantidadBusesEmpresa());
        Label lblCantBuses = new Label();
        lblCantBuses.textProperty().bind(Bindings.concat("🚌 Buses en flota: ", empresaCantBuses.asString()));
        lblCantBuses.setFont(Font.font("Arial", tamanioFuente));
        lblCantBuses.setTextFill(Color.web(Configuracion.AZUL_MEDIO));
        organizadorVertical.getChildren().add(lblCantBuses);
    }

    private void actualizarContenido() {
        objCargado = EmpresaControladorUna.obtenerEmpresa(indiceActual);

        empresaTitulo.set("Carrusel de Empresas (" + (indiceActual + 1) + " / " + totalEmpresas + ")");
        empresaNombre.set(objCargado.getNombreEmpresa());
        empresaNit.set(objCargado.getNitEmpresa());
        empresaTerminal.set(objCargado.getTerminalEmpresa().getNombreTerminal());
        empresaEstado.set(objCargado.getEstadoEmpresa());
        empresaCantBuses.set(objCargado.getCantidadBusesEmpresa());

        // Actualizar imagen
        try {
            String rutaImagen = Persistencia.RUTA_IMAGENES + Persistencia.SEPARADOR_CARPETAS
                    + objCargado.getNombreImagenPrivadoEmpresa();
            FileInputStream imgArchivo = new FileInputStream(rutaImagen);
            Image imgNueva = new Image(imgArchivo);
            empresaImagen.set(imgNueva);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VistaEmpresaCarrusel.class.getName()).log(Level.SEVERE, null, ex);
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
