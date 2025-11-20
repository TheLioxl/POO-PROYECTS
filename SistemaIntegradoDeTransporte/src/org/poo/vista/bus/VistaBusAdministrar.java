package org.poo.vista.bus;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.poo.controlador.bus.BusControladorEliminar;
import org.poo.controlador.bus.BusControladorListar;
import org.poo.controlador.bus.BusControladorUna;
import org.poo.controlador.bus.BusControladorVentana;
import org.poo.dto.BusDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.constante.Persistencia;
import org.poo.recurso.utilidad.Icono;
import org.poo.recurso.utilidad.Marco;
import org.poo.recurso.utilidad.Mensaje;

public class VistaBusAdministrar extends StackPane {

    private final Rectangle marco;
    private final Stage miEscenario;
    private final VBox cajaVertical;
    private final TableView<BusDto> miTabla;
    

    private static final String ESTILO_CENTRAR = "-fx-alignment: CENTER;";
    private static final String ESTILO_IZQUIERDA = "-fx-alignment: CENTER-LEFT;";
    private static final String ESTILO_ROJO = "-fx-text-fill: " + Configuracion.ROJO_ERROR + ";" + ESTILO_CENTRAR;
    private static final String ESTILO_VERDE = "-fx-text-fill: " + Configuracion.VERDE_EXITO + ";" + ESTILO_CENTRAR;

    private Text titulo;
    private HBox cajaBotones;
    private final ObservableList<BusDto> datosTabla = FXCollections.observableArrayList();
    private int totalBuses;
    private int indiceActual;
    private BusDto objCargado;
    private static int indiceActualEstatico = 0;
    
    private StringProperty busTitulo;
    private StringProperty busPlaca;
    private StringProperty busModelo;
    private StringProperty busEmpresa;
    private ObjectProperty<Image> busImagen;
    private BooleanProperty busEstado;
    private IntegerProperty busCapacidad;
    private StringProperty busTipo;

    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;

    public VistaBusAdministrar(Stage ventanaPadre, BorderPane princ, Pane pane,
            double ancho, double alto) {
        setAlignment(Pos.CENTER);
        
        miEscenario = ventanaPadre;
        panelPrincipal = princ;
        panelCuerpo = pane;
        
        marco = Marco.crear(
                miEscenario,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_TERMINAL,
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

        int cant = BusControladorListar.obtenerCantidadBuses();
        Text titulo = new Text("ADMINISTRAR BUSES (" + cant + ")");
        titulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        titulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 28));

        cajaVertical.getChildren().addAll(bloqueSeparador, titulo);
    }

    private void crearTabla() {
        
        TableColumn<BusDto, Integer> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("idBus"));
        colCodigo.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.10));
        colCodigo.setStyle(ESTILO_CENTRAR);

        TableColumn<BusDto, String> colPlaca = new TableColumn<>("Placa");
        colPlaca.setCellValueFactory(new PropertyValueFactory<>("placaBus"));
        colPlaca.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.20));
        colPlaca.setStyle(ESTILO_CENTRAR);

        TableColumn<BusDto, String> colModelo = new TableColumn<>("Modelo");
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modeloBus"));
        colModelo.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.20));
        colModelo.setStyle(ESTILO_IZQUIERDA);

        TableColumn<BusDto, Integer> colCapacidad = new TableColumn<>("Capacidad");
        colCapacidad.setCellValueFactory(new PropertyValueFactory<>("capacidadBus"));
        colCapacidad.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.12));
        colCapacidad.setStyle(ESTILO_CENTRAR);

        TableColumn<BusDto, String> colEmpresa = new TableColumn<>("Empresa");
        colEmpresa.setCellValueFactory(obj -> 
            new SimpleStringProperty(obj.getValue().getEmpresaBus().getNombreEmpresa()));
        colEmpresa.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.20));
        colEmpresa.setStyle(ESTILO_IZQUIERDA);

        TableColumn<BusDto, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoBus"));
        colTipo.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.20));
        colTipo.setStyle(ESTILO_CENTRAR);

        TableColumn<BusDto, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(obj -> {
            String estado = obj.getValue().getEstadoBus() ? "Activo" : "Inactivo";
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
        colEstado.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.20));

        TableColumn<BusDto, String> colImagen = new TableColumn<>("Imagen");
        colImagen.setCellValueFactory(new PropertyValueFactory<>("nombreImagenPrivadoBus"));
        colImagen.setCellFactory(column -> new TableCell<BusDto, String>() {
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
        colImagen.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.20));
        colImagen.setStyle(ESTILO_CENTRAR);

        miTabla.getColumns().add(colCodigo);
        miTabla.getColumns().add(colPlaca);
        miTabla.getColumns().add(colModelo);
        miTabla.getColumns().add(colCapacidad);
        miTabla.getColumns().add(colEmpresa);
        miTabla.getColumns().add(colTipo);
        miTabla.getColumns().add(colEstado);
        miTabla.getColumns().add(colImagen);
        
        List<BusDto> arrBuses = BusControladorListar.obtenerBuses();
        ObservableList<BusDto> datosTabla = FXCollections.observableArrayList(arrBuses);

        miTabla.setItems(datosTabla);
        miTabla.setPlaceholder(new Text("No hay buses registrados"));
        miTabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        miTabla.maxWidthProperty().bind(miEscenario.widthProperty().multiply(0.72));
        miTabla.maxHeightProperty().bind(miEscenario.heightProperty().multiply(0.60));

        miEscenario.heightProperty().addListener((o, oldVal, newVal)
                -> miTabla.setPrefHeight(newVal.doubleValue()));

        VBox.setVgrow(miTabla, Priority.ALWAYS);

        cajaVertical.getChildren().add(miTabla);
        getChildren().add(cajaVertical);
    }

    private void losIconosAdmin() {
        int ancho = 40;
        int tamanioIconito = 16;
        
        Button btnEliminar = new Button();
        btnEliminar.setPrefWidth(ancho);
        btnEliminar.setCursor(Cursor.HAND);
        btnEliminar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_BORRAR, tamanioIconito));
        
        btnEliminar.setOnAction((e) -> {
            if (miTabla.getSelectionModel().getSelectedItem() == null) {
                Mensaje.mostrar(Alert.AlertType.WARNING,
                        miEscenario, "Advertencia", "Debe seleccionar un bus");
            } else {
                BusDto objBus = miTabla.getSelectionModel().getSelectedItem();
                
                String mensaje = "¿Está seguro de eliminar este bus?\\n\\n"
                        + "Código: " + objBus.getIdBus() + "\\n"
                        + "Placa: " + objBus.getPlacaBus() + "\\n"
                        + "Modelo: " + objBus.getModeloBus() + "\\n\\n"
                        + "Esta acción no se puede deshacer.";

                Alert msg = new Alert(Alert.AlertType.CONFIRMATION);
                msg.setTitle("Confirmar Eliminación");
                msg.setHeaderText(null);
                msg.setContentText(mensaje);
                msg.initOwner(miEscenario);
                
                if (msg.showAndWait().get() == ButtonType.OK) {
                    int posi = miTabla.getSelectionModel().getSelectedIndex();
                    if (BusControladorEliminar.borrar(posi)) {
                        int canti = BusControladorListar.obtenerCantidadBuses();
                        titulo.setText("ADMINISTRAR BUSES (" + canti + ")");

                        datosTabla.setAll(BusControladorListar.obtenerBuses());
                        miTabla.refresh();

                        Mensaje.mostrar(Alert.AlertType.INFORMATION,
                                miEscenario, "Éxito", "Bus eliminado correctamente");
                    } else {
                        Mensaje.mostrar(Alert.AlertType.ERROR,
                                miEscenario, "Error", "No se pudo eliminar el bus");
                    }
                } else {
                    miTabla.getSelectionModel().clearSelection();
                }
            }
        });
        
        Button btnActualizar = new Button();
        btnActualizar.setPrefWidth(ancho);
        btnActualizar.setCursor(Cursor.HAND);
        btnActualizar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_EDITAR, tamanioIconito));
        
        btnEliminar.setOnAction(e -> {
            String mensaje = "¿Seguro que desea eliminar este bus?\\n\\n"
                    + "Código: " + objCargado.getIdBus() + "\\n"
                    + "Placa: " + objCargado.getPlacaBus() + "\\n\\n"
                    + "Esta acción es irreversible.";

            Alert msg = new Alert(Alert.AlertType.CONFIRMATION);
            msg.setTitle("Confirmar Eliminación");
            msg.setHeaderText(null);
            msg.setContentText(mensaje);
            msg.initOwner(miEscenario);

            if (msg.showAndWait().get() == ButtonType.OK) {
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
    
    private void actualizarContenido() {
        objCargado = BusControladorUna.obtenerBus(indiceActual);

        busTitulo.set("Carrusel de Buses (" + (indiceActual + 1) + " / " + totalBuses + ")");
        busPlaca.set(objCargado.getPlacaBus() + " - " + objCargado.getModeloBus());
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
}
