package org.poo.vista.terminal;

import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
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
import org.poo.controlador.terminal.TerminalControladorVentana;
import org.poo.dto.TerminalDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.Icono;
import org.poo.recurso.utilidad.Marco;
import org.poo.recurso.utilidad.Mensaje;

/**
 * Vista para administrar terminales (listar, editar, eliminar)
 */
public class VistaTerminalAdministrar extends StackPane {

    private final Rectangle marco;
    private final Stage miEscenario;
    private final VBox cajaVertical;
    private final TableView<TerminalDto> miTabla;

    private static final String ESTILO_CENTRAR = "-fx-alignment: CENTER;";
    private static final String ESTILO_IZQUIERDA = "-fx-alignment: CENTER-LEFT;";
    private static final String ESTILO_DERECHA = "-fx-alignment: CENTER-RIGHT;";
    private static final String ESTILO_ROJO = "-fx-text-fill: " + Configuracion.ROJO_ERROR + ";" + ESTILO_CENTRAR;
    private static final String ESTILO_VERDE = "-fx-text-fill: " + Configuracion.VERDE_EXITO + ";" + ESTILO_CENTRAR;

    private Text titulo;
    private HBox cajaBotones;
    private final ObservableList<TerminalDto> datosTabla = FXCollections.observableArrayList();

    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;

    public VistaTerminalAdministrar(Stage ventanaPadre, BorderPane princ, Pane pane,
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

        int cant = TerminalControladorListar.obtenerCantidadTerminales();
        this.titulo = new Text("ADMINISTRAR TERMINALES (" + cant + ")");
        this.titulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        this.titulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 28));

        cajaVertical.getChildren().addAll(bloqueSeparador, this.titulo);
    }

    private TableColumn<TerminalDto, Integer> crearColumnaCodigo() {
        TableColumn<TerminalDto, Integer> columna = new TableColumn<>("Código");
        columna.setCellValueFactory(new PropertyValueFactory<>("idTerminal"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.08));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<TerminalDto, String> crearColumnaNombre() {
        TableColumn<TerminalDto, String> columna = new TableColumn<>("Nombre Terminal");
        columna.setCellValueFactory(new PropertyValueFactory<>("nombreTerminal"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.22));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }

    private TableColumn<TerminalDto, String> crearColumnaCiudad() {
        TableColumn<TerminalDto, String> columna = new TableColumn<>("Ciudad");
        columna.setCellValueFactory(new PropertyValueFactory<>("ciudadTerminal"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.12));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<TerminalDto, String> crearColumnaDireccion() {
        TableColumn<TerminalDto, String> columna = new TableColumn<>("Dirección");
        columna.setCellValueFactory(new PropertyValueFactory<>("direccionTerminal"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.20));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }

    private TableColumn<TerminalDto, String> crearColumnaEstado() {
        TableColumn<TerminalDto, String> columna = new TableColumn<>("Estado");
        columna.setCellValueFactory(obj -> {
            String estado = obj.getValue().getEstadoTerminal() ? "Activo" : "Inactivo";
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

    private TableColumn<TerminalDto, Short> crearColumnaCantidadEmpresas() {
        TableColumn<TerminalDto, Short> columna = new TableColumn<>("Empresas");
        columna.setCellValueFactory(new PropertyValueFactory<>("cantidadEmpresasTerminal"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.08));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<TerminalDto, String> crearColumnaImagen() {
        TableColumn<TerminalDto, String> columna = new TableColumn<>("Imagen");
        columna.setCellValueFactory(new PropertyValueFactory<>("nombreImagenPrivadoTerminal"));
        columna.setCellFactory(column -> new TableCell<TerminalDto, String>() {
            @Override
            protected void updateItem(String nombreImagen, boolean bandera) {
                super.updateItem(nombreImagen, bandera);
                if (bandera || nombreImagen == null || nombreImagen.isEmpty()) {
                    setGraphic(null);
                } else {
                    try {
                        setGraphic(Icono.obtenerFotosExternas(nombreImagen, 50));
                    } catch (Exception e) {
                        // Si falla, mostrar imagen por defecto
                        setGraphic(Icono.obtenerIcono(Configuracion.ICONO_NO_DISPONIBLE, 50));
                    }
                }
            }
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.20));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private void configurarColumnas() {
        miTabla.getColumns().add(crearColumnaCodigo());
        miTabla.getColumns().add(crearColumnaNombre());
        miTabla.getColumns().add(crearColumnaCiudad());
        miTabla.getColumns().add(crearColumnaDireccion());
        miTabla.getColumns().add(crearColumnaEstado());
        miTabla.getColumns().add(crearColumnaCantidadEmpresas());
        miTabla.getColumns().add(crearColumnaImagen());
    }

    private void crearTabla() {
        configurarColumnas();

        List<TerminalDto> arrTerminales = TerminalControladorListar.obtenerTerminales();
        datosTabla.setAll(arrTerminales);

        miTabla.setItems(datosTabla);
        miTabla.setPlaceholder(new Text("No hay terminales registradas"));
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
                        miEscenario, "Advertencia", "Debe seleccionar una terminal");
            } else {
                TerminalDto objTerminal = miTabla.getSelectionModel().getSelectedItem();
                
                if (objTerminal.getCantidadEmpresasTerminal() == 0) {
                    String mensaje = "¿Está seguro de eliminar esta terminal?\n\n"
                            + "Código: " + objTerminal.getIdTerminal() + "\n"
                            + "Terminal: " + objTerminal.getNombreTerminal() + "\n"
                            + "Ciudad: " + objTerminal.getCiudadTerminal() + "\n\n"
                            + "Esta acción no se puede deshacer.";

                    Alert msg = new Alert(Alert.AlertType.CONFIRMATION);
                    msg.setTitle("Confirmar Eliminación");
                    msg.setHeaderText(null);
                    msg.setContentText(mensaje);
                    msg.initOwner(miEscenario);
                    
                    if (msg.showAndWait().get() == ButtonType.OK) {
                        int posi = miTabla.getSelectionModel().getSelectedIndex();
                        if (TerminalControladorEliminar.borrar(posi)) {
                            int canti = TerminalControladorListar.obtenerCantidadTerminales();
                            titulo.setText("ADMINISTRAR TERMINALES (" + canti + ")");

                            datosTabla.setAll(TerminalControladorListar.obtenerTerminales());
                            miTabla.refresh();

                            Mensaje.mostrar(Alert.AlertType.INFORMATION,
                                    miEscenario, "Éxito", "Terminal eliminada correctamente");
                        } else {
                            Mensaje.mostrar(Alert.AlertType.ERROR,
                                    miEscenario, "Error", "No se pudo eliminar la terminal");
                        }
                    } else {
                        miTabla.getSelectionModel().clearSelection();
                    }
                } else {
                    Mensaje.mostrar(Alert.AlertType.ERROR,
                            miEscenario, "Error", 
                            "No se puede eliminar una terminal con empresas asociadas");
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
                        "Debe seleccionar una terminal para editar");
            } else {
                TerminalDto objTerminal = miTabla.getSelectionModel().getSelectedItem();
                int posi = miTabla.getSelectionModel().getSelectedIndex();

                panelCuerpo = TerminalControladorVentana.editar(
                        miEscenario,
                        panelPrincipal,
                        panelCuerpo,
                        Configuracion.ANCHO_APP,
                        Configuracion.ALTO_CUERPO,
                        objTerminal,
                        posi);
                        
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
