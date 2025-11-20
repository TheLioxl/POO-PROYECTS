package org.poo.vista.ruta;

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
import org.poo.controlador.ruta.RutaControladorEliminar;
import org.poo.controlador.ruta.RutaControladorListar;
import org.poo.controlador.ruta.RutaControladorVentana;
import org.poo.dto.RutaDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.Icono;
import org.poo.recurso.utilidad.Marco;
import org.poo.recurso.utilidad.Mensaje;

public class VistaRutaAdministrar extends StackPane {

    private final Rectangle marco;
    private final Stage miEscenario;
    private final VBox cajaVertical;
    private final TableView<RutaDto> miTabla;

    private static final String ESTILO_CENTRAR = "-fx-alignment: CENTER;";
    private static final String ESTILO_IZQUIERDA = "-fx-alignment: CENTER-LEFT;";
    private static final String ESTILO_ROJO = "-fx-text-fill: " + Configuracion.ROJO_ERROR + ";" + ESTILO_CENTRAR;
    private static final String ESTILO_VERDE = "-fx-text-fill: " + Configuracion.VERDE_EXITO + ";" + ESTILO_CENTRAR;

    private Text titulo;
    private HBox cajaBotones;
    private final ObservableList<RutaDto> datosTabla = FXCollections.observableArrayList();

    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;

    public VistaRutaAdministrar(Stage ventanaPadre, BorderPane princ, Pane pane,
            double ancho, double alto) {
        setAlignment(Pos.CENTER);
        
        miEscenario = ventanaPadre;
        panelPrincipal = princ;
        panelCuerpo = pane;
        
        marco = Marco.crear(
                miEscenario,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_RUTA,
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

        int cant = RutaControladorListar.obtenerCantidadRutas();
        this.titulo = new Text("ADMINISTRAR RUTAS (" + cant + ")");
        this.titulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        this.titulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 28));

        cajaVertical.getChildren().addAll(bloqueSeparador, this.titulo);
    }

    private void crearTabla() {
        TableColumn<RutaDto, Integer> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("idRuta"));
        colCodigo.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.07));
        colCodigo.setStyle(ESTILO_CENTRAR);

        TableColumn<RutaDto, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreRuta"));
        colNombre.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.20));
        colNombre.setStyle(ESTILO_IZQUIERDA);

        TableColumn<RutaDto, String> colOrigen = new TableColumn<>("Origen");
        colOrigen.setCellValueFactory(new PropertyValueFactory<>("ciudadOrigenRuta"));
        colOrigen.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.13));
        colOrigen.setStyle(ESTILO_CENTRAR);

        TableColumn<RutaDto, String> colDestino = new TableColumn<>("Destino");
        colDestino.setCellValueFactory(new PropertyValueFactory<>("ciudadDestinoRuta"));
        colDestino.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.13));
        colDestino.setStyle(ESTILO_CENTRAR);

        TableColumn<RutaDto, Double> colDistancia = new TableColumn<>("Distancia (Km)");
        colDistancia.setCellValueFactory(new PropertyValueFactory<>("distanciaKmRuta"));
        colDistancia.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.10));
        colDistancia.setStyle(ESTILO_CENTRAR);

        TableColumn<RutaDto, Integer> colDuracion = new TableColumn<>("Duración (Hrs)");
        colDuracion.setCellValueFactory(new PropertyValueFactory<>("duracionHorasRuta"));
        colDuracion.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.10));
        colDuracion.setStyle(ESTILO_CENTRAR);

        TableColumn<RutaDto, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(obj -> {
            String estado = obj.getValue().getEstadoRuta() ? "Activa" : "Inactiva";
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
                    setStyle("Activa".equals(estadoTXT) ? ESTILO_VERDE : ESTILO_ROJO);
                }
            }
        });
        colEstado.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.10));

        TableColumn<RutaDto, String> colImagen = new TableColumn<>("Imagen");
        colImagen.setCellValueFactory(new PropertyValueFactory<>("nombreImagenPrivadoRuta"));
        colImagen.setCellFactory(column -> new TableCell<RutaDto, String>() {
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
        colImagen.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.17));
        colImagen.setStyle(ESTILO_CENTRAR);

        miTabla.getColumns().add(colCodigo);
        miTabla.getColumns().add(colNombre);
        miTabla.getColumns().add(colOrigen);
        miTabla.getColumns().add(colDestino);
        miTabla.getColumns().add(colDistancia);
        miTabla.getColumns().add(colDuracion);
        miTabla.getColumns().add(colEstado);
        miTabla.getColumns().add(colImagen);

        List<RutaDto> arrRutas = RutaControladorListar.obtenerRutas();
        datosTabla.setAll(arrRutas);

        miTabla.setItems(datosTabla);
        miTabla.setPlaceholder(new Text("No hay rutas registradas"));
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
        
        Button btnEliminar = new Button();
        btnEliminar.setPrefWidth(ancho);
        btnEliminar.setCursor(Cursor.HAND);
        btnEliminar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_BORRAR, tamanioIconito));
        
        btnEliminar.setOnAction((e) -> {
            if (miTabla.getSelectionModel().getSelectedItem() == null) {
                Mensaje.mostrar(Alert.AlertType.WARNING,
                        miEscenario, "Advertencia", "Debe seleccionar una ruta");
            } else {
                RutaDto objRuta = miTabla.getSelectionModel().getSelectedItem();
                
                String mensaje = "¿Está seguro de eliminar esta ruta?\n\n"
                        + "Código: " + objRuta.getIdRuta() + "\n"
                        + "Ruta: " + objRuta.getCiudadOrigenRuta() + " → " + objRuta.getCiudadDestinoRuta() + "\n\n"
                        + "Esta acción no se puede deshacer.";

                Alert msg = new Alert(Alert.AlertType.CONFIRMATION);
                msg.setTitle("Confirmar Eliminación");
                msg.setHeaderText(null);
                msg.setContentText(mensaje);
                msg.initOwner(miEscenario);
                
                if (msg.showAndWait().get() == ButtonType.OK) {
                    int posi = miTabla.getSelectionModel().getSelectedIndex();
                    if (RutaControladorEliminar.borrar(posi)) {
                        int canti = RutaControladorListar.obtenerCantidadRutas();
                        titulo.setText("ADMINISTRAR RUTAS (" + canti + ")");

                        datosTabla.setAll(RutaControladorListar.obtenerRutas());
                        miTabla.refresh();

                        Mensaje.mostrar(Alert.AlertType.INFORMATION,
                                miEscenario, "Éxito", "Ruta eliminada correctamente");
                    } else {
                        Mensaje.mostrar(Alert.AlertType.ERROR,
                                miEscenario, "Error", "No se pudo eliminar la ruta");
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
        
        btnActualizar.setOnAction((e) -> {
            if (miTabla.getSelectionModel().getSelectedItem() == null) {
                Mensaje.mostrar(Alert.AlertType.WARNING,
                        miEscenario, "Advertencia", 
                        "Debe seleccionar una ruta para editar");
            } else {
                RutaDto objRuta = miTabla.getSelectionModel().getSelectedItem();
                int posi = miTabla.getSelectionModel().getSelectedIndex();

                panelCuerpo = RutaControladorVentana.editar(
                        miEscenario,
                        panelPrincipal,
                        panelCuerpo,
                        Configuracion.ANCHO_APP,
                        Configuracion.ALTO_CUERPO,
                        objRuta,
                        posi,
                        false);
                        
                panelPrincipal.setCenter(null);
                panelPrincipal.setCenter(panelCuerpo);
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
}
