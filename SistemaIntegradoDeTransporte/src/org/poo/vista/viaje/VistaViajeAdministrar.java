package org.poo.vista.viaje;

import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.poo.controlador.viaje.ViajeControladorEliminar;
import org.poo.controlador.viaje.ViajeControladorListar;
import org.poo.controlador.viaje.ViajeControladorVentana;
import org.poo.dto.ViajeDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.Icono;
import org.poo.recurso.utilidad.Marco;
import org.poo.recurso.utilidad.Mensaje;

public class VistaViajeAdministrar extends StackPane {

    private final Rectangle marco;
    private final Stage miEscenario;
    private final VBox cajaVertical;
    private final TableView<ViajeDto> miTabla;

    private static final String ESTILO_CENTRAR = "-fx-alignment: CENTER;";
    private static final String ESTILO_IZQUIERDA = "-fx-alignment: CENTER-LEFT;";
    private static final String ESTILO_ROJO = "-fx-text-fill: " + Configuracion.ROJO_ERROR + ";" + ESTILO_CENTRAR;
    private static final String ESTILO_VERDE = "-fx-text-fill: " + Configuracion.VERDE_EXITO + ";" + ESTILO_CENTRAR;

    private Text titulo;
    private HBox cajaBotones;
    private final ObservableList<ViajeDto> datosTabla = FXCollections.observableArrayList();

    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;

    public VistaViajeAdministrar(Stage ventanaPadre, BorderPane princ, Pane pane,
            double ancho, double alto) {
        setAlignment(Pos.CENTER);
        
        miEscenario = ventanaPadre;
        panelPrincipal = princ;
        panelCuerpo = pane;
        
        marco = Marco.crear(
                miEscenario,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_VIAJE,
                Configuracion.DEGRADE_BORDE
        );

        miTabla = new TableView<>();
        cajaVertical = new VBox(20);
        getChildren().add(marco);

        configurarCajaVertical();
        crearTitulo();
        crearTabla();
        losIconosAdmin();
    }

    private void configurarCajaVertical() {
        cajaVertical.setAlignment(Pos.TOP_CENTER);
        cajaVertical.prefWidthProperty().bind(miEscenario.widthProperty());
        cajaVertical.prefHeightProperty().bind(miEscenario.heightProperty());
    }

    private void crearTitulo() {
        Region bloqueSeparador = new Region();
        bloqueSeparador.prefHeightProperty().bind(
                miEscenario.heightProperty().multiply(0.05));

        int cant = ViajeControladorListar.obtenerCantidadViajes();
        this.titulo = new Text("ADMINISTRAR VIAJES (" + cant + ")");
        this.titulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        this.titulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 28));

        cajaVertical.getChildren().addAll(bloqueSeparador, this.titulo);
    }

    private void crearTabla() {
        // Código
        TableColumn<ViajeDto, Integer> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("idViaje"));
        colCodigo.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.06));
        colCodigo.setStyle(ESTILO_CENTRAR);

        // Fecha
        TableColumn<ViajeDto, String> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(obj -> 
            new SimpleStringProperty(obj.getValue().getFechaViaje().toString()));
        colFecha.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.10));
        colFecha.setStyle(ESTILO_CENTRAR);

        // Ruta
        TableColumn<ViajeDto, String> colRuta = new TableColumn<>("Ruta");
        colRuta.setCellValueFactory(obj -> 
            new SimpleStringProperty(obj.getValue().getRutaViaje().toString()));
        colRuta.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        colRuta.setStyle(ESTILO_IZQUIERDA);

        // Bus
        TableColumn<ViajeDto, String> colBus = new TableColumn<>("Bus");
        colBus.setCellValueFactory(obj -> 
            new SimpleStringProperty(obj.getValue().getBusViaje().getPlacaBus()));
        colBus.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.10));
        colBus.setStyle(ESTILO_CENTRAR);

        // Hora Salida
        TableColumn<ViajeDto, String> colHoraSalida = new TableColumn<>("Salida");
        colHoraSalida.setCellValueFactory(obj -> 
            new SimpleStringProperty(obj.getValue().getHoraSalidaViaje().toString()));
        colHoraSalida.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.08));
        colHoraSalida.setStyle(ESTILO_CENTRAR);

        // Precio
        TableColumn<ViajeDto, String> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(obj -> 
            new SimpleStringProperty("$" + String.format("%.2f", obj.getValue().getPrecioViaje())));
        colPrecio.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.10));
        colPrecio.setStyle(ESTILO_CENTRAR);

        // Asientos
        TableColumn<ViajeDto, Integer> colAsientos = new TableColumn<>("Asientos");
        colAsientos.setCellValueFactory(new PropertyValueFactory<>("asientosDisponiblesViaje"));
        colAsientos.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.08));
        colAsientos.setStyle(ESTILO_CENTRAR);

        // Estado
        TableColumn<ViajeDto, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(obj -> {
            String estado = obj.getValue().getEstadoViaje() ? "Activo" : "Inactivo";
            return new SimpleStringProperty(estado);
        });
        colEstado.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String estadoTXT, boolean empty) {
                super.updateItem(estadoTXT, empty);
                if (empty || estadoTXT == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(estadoTXT);
                    setStyle("Activo".equals(estadoTXT) ? ESTILO_VERDE : ESTILO_ROJO);
                }
            }
        });
        colEstado.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.08));

        // Imagen
        TableColumn<ViajeDto, String> colImagen = new TableColumn<>("Imagen");
        colImagen.setCellValueFactory(new PropertyValueFactory<>("nombreImagenPrivadoViaje"));
        colImagen.setCellFactory(column -> new TableCell<ViajeDto, String>() {
            @Override
            protected void updateItem(String nombreImagen, boolean bandera) {
                super.updateItem(nombreImagen, bandera);
                if (bandera || nombreImagen == null || nombreImagen.isEmpty()) {
                    setGraphic(null);
                } else {
                    try {
                        setGraphic(Icono.obtenerFotosExternas(nombreImagen, 50));
                    } catch (Exception e) {
                        setGraphic(Icono.obtenerIcono(Configuracion.ICONO_NO_DISPONIBLE, 50));
                    }
                }
            }
        });
        colImagen.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        colImagen.setStyle(ESTILO_CENTRAR);
        
        miTabla.getColumns().add(colCodigo);
        miTabla.getColumns().add(colFecha);
        miTabla.getColumns().add(colRuta);
        miTabla.getColumns().add(colBus);
        miTabla.getColumns().add(colHoraSalida);
        miTabla.getColumns().add(colPrecio);
        miTabla.getColumns().add(colAsientos);
        miTabla.getColumns().add(colEstado);
        miTabla.getColumns().add(colImagen);

        List<ViajeDto> arrViajes = ViajeControladorListar.obtenerViajes();
        datosTabla.setAll(arrViajes);

        miTabla.setItems(datosTabla);
        miTabla.setPlaceholder(new Text("No hay viajes registrados"));
        miTabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        miTabla.maxWidthProperty().bind(miEscenario.widthProperty().multiply(0.70));
        miTabla.maxHeightProperty().bind(miEscenario.heightProperty().multiply(0.50));

        miEscenario.heightProperty().addListener((o, oldVal, newVal) -> 
                miTabla.setPrefHeight(newVal.doubleValue()));
        
        VBox.setVgrow(miTabla, Priority.ALWAYS);

        cajaVertical.getChildren().add(miTabla);
        getChildren().add(cajaVertical);
    }

    private void losIconosAdmin() {
        int ancho = 40;
        int tamanioIconito = 16;
        
        // Botón eliminar
        Button btnEliminar = new Button();
        btnEliminar.setPrefWidth(ancho);
        btnEliminar.setCursor(Cursor.HAND);
        btnEliminar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_BORRAR, tamanioIconito));
        
        btnEliminar.setOnAction((e) -> {
            if (miTabla.getSelectionModel().getSelectedItem() == null) {
                Mensaje.mostrar(Alert.AlertType.WARNING,
                        miEscenario, "Advertencia", "Debe seleccionar un viaje");
            } else {
                ViajeDto objViaje = miTabla.getSelectionModel().getSelectedItem();
                
                String mensaje = "¿Está seguro de eliminar este viaje?\n\n"
                        + "Código: " + objViaje.getIdViaje() + "\n"
                        + "Fecha: " + objViaje.getFechaViaje() + "\n"
                        + "Ruta: " + objViaje.getRutaViaje() + "\n\n"
                        + "Esta acción no se puede deshacer.";

                Alert msg = new Alert(Alert.AlertType.CONFIRMATION);
                msg.setTitle("Confirmar Eliminación");
                msg.setHeaderText(null);
                msg.setContentText(mensaje);
                msg.initOwner(miEscenario);
                
                if (msg.showAndWait().get() == ButtonType.OK) {
                    int posi = miTabla.getSelectionModel().getSelectedIndex();
                    if (ViajeControladorEliminar.borrar(posi)) {
                        int canti = ViajeControladorListar.obtenerCantidadViajes();
                        titulo.setText("ADMINISTRAR VIAJES (" + canti + ")");

                        datosTabla.setAll(ViajeControladorListar.obtenerViajes());
                        miTabla.refresh();

                        Mensaje.mostrar(Alert.AlertType.INFORMATION,
                                miEscenario, "Éxito", "Viaje eliminado correctamente");
                    } else {
                        Mensaje.mostrar(Alert.AlertType.ERROR,
                                miEscenario, "Error", "No se pudo eliminar el viaje");
                    }
                } else {
                    miTabla.getSelectionModel().clearSelection();
                }
            }
        });
        
        // Botón actualizar/editar
        Button btnActualizar = new Button();
        btnActualizar.setPrefWidth(ancho);
        btnActualizar.setCursor(Cursor.HAND);
        btnActualizar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_EDITAR, tamanioIconito));
        
        btnActualizar.setOnAction((e) -> {
            if (miTabla.getSelectionModel().getSelectedItem() == null) {
                Mensaje.mostrar(Alert.AlertType.WARNING,
                        miEscenario, "Advertencia", 
                        "Debe seleccionar un viaje para editar");
            } else {
                ViajeDto objViaje = miTabla.getSelectionModel().getSelectedItem();
                int posi = miTabla.getSelectionModel().getSelectedIndex();

                panelCuerpo = ViajeControladorVentana.editar(
                        miEscenario,
                        panelPrincipal,
                        panelCuerpo,
                        Configuracion.ANCHO_APP,
                        Configuracion.ALTO_CUERPO,
                        objViaje,
                        posi,
                        false);
                        
                panelPrincipal.setCenter(null);
                panelPrincipal.setCenter(panelCuerpo);
            }
        });
        
        // Botón cancelar
        Button btnCancelar = new Button();
        btnCancelar.setPrefWidth(ancho);
        btnCancelar.setCursor(Cursor.HAND);
        btnCancelar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_CANCELAR, tamanioIconito));
        btnCancelar.setOnAction((e) -> {
            miTabla.getSelectionModel().clearSelection();
        });

        cajaBotones = new HBox(5);
        cajaBotones.setAlignment(Pos.CENTER);
        cajaBotones.getChildren().addAll(btnEliminar, btnActualizar, btnCancelar);
        cajaVertical.getChildren().add(cajaBotones);
    }
}