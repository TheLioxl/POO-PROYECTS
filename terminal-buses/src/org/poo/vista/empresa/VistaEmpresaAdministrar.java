package org.poo.vista.empresa;

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
import org.poo.controlador.empresa.EmpresaControladorEliminar;
import org.poo.controlador.empresa.EmpresaControladorListar;
import org.poo.controlador.empresa.EmpresaControladorVentana;
import org.poo.dto.EmpresaDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.Icono;
import org.poo.recurso.utilidad.Marco;
import org.poo.recurso.utilidad.Mensaje;

public class VistaEmpresaAdministrar extends StackPane {

    private final Rectangle marco;
    private final Stage miEscenario;
    private final VBox cajaVertical;
    private final TableView<EmpresaDto> miTabla;

    private static final String ESTILO_CENTRAR = "-fx-alignment: CENTER;";
    private static final String ESTILO_IZQUIERDA = "-fx-alignment: CENTER-LEFT;";
    private static final String ESTILO_ROJO = "-fx-text-fill: " + Configuracion.ROJO_ERROR + ";" + ESTILO_CENTRAR;
    private static final String ESTILO_VERDE = "-fx-text-fill: " + Configuracion.VERDE_EXITO + ";" + ESTILO_CENTRAR;

    private Text titulo;
    private HBox cajaBotones;
    private final ObservableList<EmpresaDto> datosTabla = FXCollections.observableArrayList();

    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;

    public VistaEmpresaAdministrar(Stage ventanaPadre, BorderPane princ, Pane pane,
            double ancho, double alto) {
        setAlignment(Pos.CENTER);
        
        miEscenario = ventanaPadre;
        panelPrincipal = princ;
        panelCuerpo = pane;
        
        marco = Marco.crear(
                miEscenario,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_EMPRESA,
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

        int cant = EmpresaControladorListar.obtenerCantidadEmpresas();
        titulo = new Text("Administrar Empresas (" + cant + ")");
        titulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));

        cajaVertical.getChildren().addAll(bloqueSeparador, titulo);
    }

    private void crearTabla() {
        // Código
        TableColumn<EmpresaDto, Integer> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("idEmpresa"));
        colCodigo.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.08));
        colCodigo.setStyle(ESTILO_CENTRAR);

        // Nombre
        TableColumn<EmpresaDto, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreEmpresa"));
        colNombre.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.20));
        colNombre.setStyle(ESTILO_IZQUIERDA);

        // NIT
        TableColumn<EmpresaDto, String> colNit = new TableColumn<>("NIT");
        colNit.setCellValueFactory(new PropertyValueFactory<>("nitEmpresa"));
        colNit.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.12));
        colNit.setStyle(ESTILO_CENTRAR);

        // Terminal
        TableColumn<EmpresaDto, String> colTerminal = new TableColumn<>("Terminal");
        colTerminal.setCellValueFactory(obj -> 
            new SimpleStringProperty(obj.getValue().getTerminalEmpresa().getNombreTerminal()));
        colTerminal.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        colTerminal.setStyle(ESTILO_IZQUIERDA);

        // Estado
        TableColumn<EmpresaDto, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(obj -> {
            String estado = obj.getValue().getEstadoEmpresa() ? "Activo" : "Inactivo";
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
        colEstado.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.10));

        // Cantidad Buses
        TableColumn<EmpresaDto, Short> colBuses = new TableColumn<>("Buses");
        colBuses.setCellValueFactory(new PropertyValueFactory<>("cantidadBusesEmpresa"));
        colBuses.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.08));
        colBuses.setStyle(ESTILO_CENTRAR);

        // Imagen
        TableColumn<EmpresaDto, String> colImagen = new TableColumn<>("Logo");
        colImagen.setCellValueFactory(new PropertyValueFactory<>("nombreImagenPrivadoEmpresa"));
        colImagen.setCellFactory(column -> new TableCell<EmpresaDto, String>() {
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

        miTabla.getColumns().addAll(List.of(
                colCodigo, colNombre, colNit, colTerminal, colEstado, colBuses, colImagen
        ));

        List<EmpresaDto> arrEmpresas = EmpresaControladorListar.obtenerEmpresas();
        datosTabla.setAll(arrEmpresas);

        miTabla.setItems(datosTabla);
        miTabla.setPlaceholder(new Text("No hay empresas registradas"));
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
                        miEscenario, "Advertencia", "Debe seleccionar una empresa");
            } else {
                EmpresaDto objEmpresa = miTabla.getSelectionModel().getSelectedItem();
                
                if (objEmpresa.getCantidadBusesEmpresa() == 0) {
                    String mensaje = "¿Está seguro de eliminar esta empresa?\n\n"
                            + "Código: " + objEmpresa.getIdEmpresa() + "\n"
                            + "Empresa: " + objEmpresa.getNombreEmpresa() + "\n"
                            + "NIT: " + objEmpresa.getNitEmpresa() + "\n\n"
                            + "Esta acción no se puede deshacer.";

                    Alert msg = new Alert(Alert.AlertType.CONFIRMATION);
                    msg.setTitle("Confirmar Eliminación");
                    msg.setHeaderText(null);
                    msg.setContentText(mensaje);
                    msg.initOwner(miEscenario);
                    
                    if (msg.showAndWait().get() == ButtonType.OK) {
                        int posi = miTabla.getSelectionModel().getSelectedIndex();
                        if (EmpresaControladorEliminar.borrar(posi)) {
                            int canti = EmpresaControladorListar.obtenerCantidadEmpresas();
                            titulo.setText("Administrar Empresas (" + canti + ")");

                            datosTabla.setAll(EmpresaControladorListar.obtenerEmpresas());
                            miTabla.refresh();

                            Mensaje.mostrar(Alert.AlertType.INFORMATION,
                                    miEscenario, "Éxito", "Empresa eliminada correctamente");
                        } else {
                            Mensaje.mostrar(Alert.AlertType.ERROR,
                                    miEscenario, "Error", "No se pudo eliminar la empresa");
                        }
                    } else {
                        miTabla.getSelectionModel().clearSelection();
                    }
                } else {
                    Mensaje.mostrar(Alert.AlertType.ERROR,
                            miEscenario, "Error", 
                            "No se puede eliminar una empresa con buses asociados");
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
                        "Debe seleccionar una empresa para editar");
            } else {
                EmpresaDto objEmpresa = miTabla.getSelectionModel().getSelectedItem();
                int posi = miTabla.getSelectionModel().getSelectedIndex();

                panelCuerpo = EmpresaControladorVentana.editar(
                        miEscenario,
                        panelPrincipal,
                        panelCuerpo,
                        Configuracion.ANCHO_APP,
                        Configuracion.ALTO_CUERPO,
                        objEmpresa,
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
