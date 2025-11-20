package org.poo.vista.destino;

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
import org.poo.controlador.destino.DestinoControladorEliminar;
import org.poo.controlador.destino.DestinoControladorListar;
import org.poo.controlador.destino.DestinoControladorVentana;
import org.poo.dto.DestinoDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.Icono;
import org.poo.recurso.utilidad.Marco;
import org.poo.recurso.utilidad.Mensaje;

public class VistaDestinoAdministrar extends StackPane {

    private final Rectangle marco;
    private final Stage miEscenario;
    private final VBox cajaVertical;
    private final TableView<DestinoDto> miTabla;

    private static final String ESTILO_CENTRAR = "-fx-alignment: CENTER;";
    private static final String ESTILO_IZQUIERDA = "-fx-alignment: CENTER-LEFT;";
    private static final String ESTILO_ROJO = "-fx-text-fill: " + Configuracion.ROJO_ERROR + ";" + ESTILO_CENTRAR;
    private static final String ESTILO_VERDE = "-fx-text-fill: " + Configuracion.VERDE_EXITO + ";" + ESTILO_CENTRAR;

    private Text titulo;
    private HBox cajaBotones;
    private final ObservableList<DestinoDto> datosTabla = FXCollections.observableArrayList();

    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;

    public VistaDestinoAdministrar(Stage ventanaPadre, BorderPane princ, Pane pane,
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

        int cant = DestinoControladorListar.obtenerCantidadDestinos();
        this.titulo = new Text("ADMINISTRAR DESTINOS (" + cant + ")");
        this.titulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        this.titulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 28));

        cajaVertical.getChildren().addAll(bloqueSeparador, this.titulo);
    }

    private TableColumn<DestinoDto, Integer> crearColumnaCodigo() {
        TableColumn<DestinoDto, Integer> columna = new TableColumn<>("Código");
        columna.setCellValueFactory(new PropertyValueFactory<>("idDestino"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.08));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<DestinoDto, String> crearColumnaNombre() {
        TableColumn<DestinoDto, String> columna = new TableColumn<>("Nombre Destino");
        columna.setCellValueFactory(new PropertyValueFactory<>("nombreDestino"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }

    private TableColumn<DestinoDto, String> crearColumnaDepartamento() {
        TableColumn<DestinoDto, String> columna = new TableColumn<>("Departamento");
        columna.setCellValueFactory(new PropertyValueFactory<>("departamentoDestino"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.12));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<DestinoDto, String> crearColumnaDescripcion() {
        TableColumn<DestinoDto, String> columna = new TableColumn<>("Descripción");
        columna.setCellValueFactory(new PropertyValueFactory<>("descripcionDestino"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }
    
    private TableColumn<DestinoDto, Integer> crearColumnaAltitud() {
        TableColumn<DestinoDto, Integer> columna = new TableColumn<>("Altitud (msnm)");
        columna.setCellValueFactory(new PropertyValueFactory<>("altitudMetros"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.10));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<DestinoDto, Double> crearColumnaTemperatura() {
        TableColumn<DestinoDto, Double> columna = new TableColumn<>("Temp. °C");
        columna.setCellValueFactory(new PropertyValueFactory<>("temperaturaPromedio"));
        columna.setCellFactory(col -> new TableCell<DestinoDto, Double>() {
            @Override
            protected void updateItem(Double temp, boolean empty) {
                super.updateItem(temp, empty);
                if (empty || temp == null) {
                    setText(null);
                } else {
                    setText(String.format("%.1f°C", temp));
                }
            }
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.08));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<DestinoDto, String> crearColumnaTipo() {
        TableColumn<DestinoDto, String> columna = new TableColumn<>("Tipo");
        columna.setCellValueFactory(obj -> {
            Boolean esPlayero = obj.getValue().getEsPlayero();
            String tipo = (esPlayero != null && esPlayero) ? "Playero" : "Montañoso";
            return new SimpleStringProperty(tipo);
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.10));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<DestinoDto, String> crearColumnaEstado() {
        TableColumn<DestinoDto, String> columna = new TableColumn<>("Estado");
        columna.setCellValueFactory(obj -> {
            String estado = obj.getValue().getEstadoDestino() ? "Activo" : "Inactivo";
            return new SimpleStringProperty(estado);
        });

        columna.setCellFactory(col -> new TableCell<>() {
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

        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.10));
        return columna;
    }

    private TableColumn<DestinoDto, String> crearColumnaImagen() {
        TableColumn<DestinoDto, String> columna = new TableColumn<>("Imagen");
        columna.setCellValueFactory(new PropertyValueFactory<>("nombreImagenPrivadoDestino"));
        columna.setCellFactory(column -> new TableCell<DestinoDto, String>() {
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
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.12));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private void configurarColumnas() {
        miTabla.getColumns().add(crearColumnaCodigo());
        miTabla.getColumns().add(crearColumnaNombre());
        miTabla.getColumns().add(crearColumnaDepartamento());
        miTabla.getColumns().add(crearColumnaDescripcion());
        miTabla.getColumns().add(crearColumnaAltitud());
        miTabla.getColumns().add(crearColumnaTemperatura());
        miTabla.getColumns().add(crearColumnaTipo());
        miTabla.getColumns().add(crearColumnaEstado());
        miTabla.getColumns().add(crearColumnaImagen());
    }

    private void crearTabla() {
        configurarColumnas();

        List<DestinoDto> arrDestinos = DestinoControladorListar.obtenerDestinos();
        datosTabla.setAll(arrDestinos);

        miTabla.setItems(datosTabla);
        miTabla.setPlaceholder(new Text("No hay destinos registrados"));
        miTabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        miTabla.maxWidthProperty().bind(miEscenario.widthProperty().multiply(0.70));
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
        
        // Botón eliminar
        Button btnEliminar = new Button();
        btnEliminar.setPrefWidth(ancho);
        btnEliminar.setCursor(Cursor.HAND);
        btnEliminar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_BORRAR, tamanioIconito));
        
        btnEliminar.setOnAction((e) -> {
            if (miTabla.getSelectionModel().getSelectedItem() == null) {
                Mensaje.mostrar(Alert.AlertType.WARNING,
                        miEscenario, "Advertencia", "Debe seleccionar un destino");
            } else {
                DestinoDto objDestino = miTabla.getSelectionModel().getSelectedItem();
                
                String mensaje = "¿Está seguro de eliminar este destino?\n\n"
                        + "Código: " + objDestino.getIdDestino() + "\n"
                        + "Destino: " + objDestino.getNombreDestino() + "\n"
                        + "Departamento: " + objDestino.getDepartamentoDestino() + "\n\n"
                        + "Esta acción no se puede deshacer.";

                Alert msg = new Alert(Alert.AlertType.CONFIRMATION);
                msg.setTitle("Confirmar Eliminación");
                msg.setHeaderText(null);
                msg.setContentText(mensaje);
                msg.initOwner(miEscenario);
                
                if (msg.showAndWait().get() == ButtonType.OK) {
                    int posi = miTabla.getSelectionModel().getSelectedIndex();
                    if (DestinoControladorEliminar.borrar(posi)) {
                        int canti = DestinoControladorListar.obtenerCantidadDestinos();
                        titulo.setText("ADMINISTRAR DESTINOS (" + canti + ")");

                        datosTabla.setAll(DestinoControladorListar.obtenerDestinos());
                        miTabla.refresh();

                        Mensaje.mostrar(Alert.AlertType.INFORMATION,
                                miEscenario, "Éxito", "Destino eliminado correctamente");
                    } else {
                        Mensaje.mostrar(Alert.AlertType.ERROR,
                                miEscenario, "Error", "No se pudo eliminar el destino");
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
                        "Debe seleccionar un destino para editar");
            } else {
                DestinoDto objDestino = miTabla.getSelectionModel().getSelectedItem();
                int posi = miTabla.getSelectionModel().getSelectedIndex();

                panelCuerpo = DestinoControladorVentana.editar(
                        miEscenario,
                        panelPrincipal,
                        panelCuerpo,
                        Configuracion.ANCHO_APP,
                        Configuracion.ALTO_CUERPO,
                        objDestino,
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
