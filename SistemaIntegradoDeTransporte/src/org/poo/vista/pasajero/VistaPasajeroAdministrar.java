package org.poo.vista.pasajero;

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
import org.poo.controlador.pasajero.PasajeroControladorEliminar;
import org.poo.controlador.pasajero.PasajeroControladorListar;
import org.poo.controlador.pasajero.PasajeroControladorVentana;
import org.poo.dto.PasajeroDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.Icono;
import org.poo.recurso.utilidad.Marco;
import org.poo.recurso.utilidad.Mensaje;

public class VistaPasajeroAdministrar extends StackPane{

    private final Rectangle marco;
    private final Stage miEscenario;
    private final VBox cajaVertical;
    private final TableView<PasajeroDto> miTabla;

    private static final String ESTILO_CENTRAR = "-fx-alignment: CENTER;";
    private static final String ESTILO_IZQUIERDA = "-fx-alignment: CENTER-LEFT;";
    private static final String ESTILO_DERECHA = "-fx-alignment: CENTER-RIGHT;";
    private static final String ESTILO_ROJO = "-fx-text-fill: " + Configuracion.ROJO_ERROR + ";" + ESTILO_CENTRAR;
    private static final String ESTILO_VERDE = "-fx-text-fill: " + Configuracion.VERDE_EXITO + ";" + ESTILO_CENTRAR;

    private Text titulo;
    private HBox cajaBotones;
    private final ObservableList<PasajeroDto> datosTabla = FXCollections.observableArrayList();

    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;

    public VistaPasajeroAdministrar(Stage ventanaPadre, BorderPane princ, Pane pane,
                                    double ancho, double alto) {

        setAlignment(Pos.CENTER);

        miEscenario = ventanaPadre;
        panelPrincipal = princ;
        panelCuerpo = pane;

        marco = Marco.crear(
                miEscenario,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_PASAJERO,   // usa el degrade de pasajero
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

        int cant = PasajeroControladorListar.obtenerCantidadPasajeros();
        this.titulo = new Text("ADMINISTRAR PASAJEROS (" + cant + ")");
        this.titulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        this.titulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 28));

        cajaVertical.getChildren().addAll(bloqueSeparador, this.titulo);
    }

    // ================== COLUMNAS ==================

    private TableColumn<PasajeroDto, Integer> crearColumnaCodigo() {
        TableColumn<PasajeroDto, Integer> columna = new TableColumn<>("Código");
        columna.setCellValueFactory(new PropertyValueFactory<>("idPasajero"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.06));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<PasajeroDto, String> crearColumnaNombre() {
        TableColumn<PasajeroDto, String> columna = new TableColumn<>("Nombre Pasajero");
        columna.setCellValueFactory(new PropertyValueFactory<>("nombrePasajero"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.20));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }

    private TableColumn<PasajeroDto, String> crearColumnaDocumento() {
        TableColumn<PasajeroDto, String> columna = new TableColumn<>("Documento");
        columna.setCellValueFactory(new PropertyValueFactory<>("documentoPasajero"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.14));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<PasajeroDto, String> crearColumnaTipoDocumento() {
        TableColumn<PasajeroDto, String> columna = new TableColumn<>("Tipo Doc.");
        columna.setCellValueFactory(new PropertyValueFactory<>("tipoDocumentoPasajero"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.10));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<PasajeroDto, String> crearColumnaMayor() {
        TableColumn<PasajeroDto, String> columna = new TableColumn<>("Mayor de edad");
        columna.setCellValueFactory(obj -> {
            Boolean mayor = obj.getValue().getEsMayorPasajero();
            String texto = (mayor != null && mayor) ? "Sí" : "No";
            return new SimpleStringProperty(texto);
        });

        columna.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String txt, boolean empty) {
                super.updateItem(txt, empty);
                if (empty || txt == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(txt);
                    setStyle("Sí".equals(txt) ? ESTILO_VERDE : ESTILO_ROJO);
                }
            }
        });

        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.10));
        return columna;
    }

    private TableColumn<PasajeroDto, String> crearColumnaTelefono() {
        TableColumn<PasajeroDto, String> columna = new TableColumn<>("Teléfono");
        columna.setCellValueFactory(new PropertyValueFactory<>("telefonoPasajero"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.12));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<PasajeroDto, String> crearColumnaEmail() {
        TableColumn<PasajeroDto, String> columna = new TableColumn<>("Email");
        columna.setCellValueFactory(new PropertyValueFactory<>("emailPasajero"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.18));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }

    private TableColumn<PasajeroDto, String> crearColumnaImagen() {
        TableColumn<PasajeroDto, String> columna = new TableColumn<>("Imagen");
        // ⚠️ En ADMIN miniatura ⇒ usamos nombre PRIVADO
        columna.setCellValueFactory(new PropertyValueFactory<>("nombreImagenPrivadoPasajero"));

        columna.setCellFactory(column -> new TableCell<PasajeroDto, String>() {
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

        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.10));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private void configurarColumnas() {
        miTabla.getColumns().add(crearColumnaCodigo());
        miTabla.getColumns().add(crearColumnaNombre());
        miTabla.getColumns().add(crearColumnaDocumento());
        miTabla.getColumns().add(crearColumnaTipoDocumento());
        miTabla.getColumns().add(crearColumnaMayor());
        miTabla.getColumns().add(crearColumnaTelefono());
        miTabla.getColumns().add(crearColumnaEmail());
        miTabla.getColumns().add(crearColumnaImagen());
    }

    // ================== TABLA ==================

    private void crearTabla() {
        configurarColumnas();

        List<PasajeroDto> arrPasajeros = PasajeroControladorListar.obtenerPasajeros();
        datosTabla.setAll(arrPasajeros);

        miTabla.setItems(datosTabla);
        miTabla.setPlaceholder(new Text("No hay pasajeros registrados"));
        miTabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        miTabla.maxWidthProperty().bind(miEscenario.widthProperty().multiply(0.80));
        miTabla.maxHeightProperty().bind(miEscenario.heightProperty().multiply(0.60));

        miEscenario.heightProperty().addListener((o, oldVal, newVal)
                -> miTabla.setPrefHeight(newVal.doubleValue()));

        VBox.setVgrow(miTabla, Priority.ALWAYS);

        cajaVertical.getChildren().add(miTabla);
        getChildren().add(cajaVertical);
    }

    // ================== BOTONES ADMIN ==================

    private void losIconosAdmin() {
        int ancho = 40;
        int tamanioIconito = 16;

        // ELIMINAR
        Button btnEliminar = new Button();
        btnEliminar.setPrefWidth(ancho);
        btnEliminar.setCursor(Cursor.HAND);
        btnEliminar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_BORRAR, tamanioIconito));

        btnEliminar.setOnAction((e) -> {
            if (miTabla.getSelectionModel().getSelectedItem() == null) {
                Mensaje.mostrar(Alert.AlertType.WARNING,
                        miEscenario, "Advertencia", "Debe seleccionar un pasajero");
            } else {
                PasajeroDto objPasajero = miTabla.getSelectionModel().getSelectedItem();

                String mensaje = "¿Está seguro de eliminar este pasajero?\n\n"
                        + "Código: " + objPasajero.getIdPasajero() + "\n"
                        + "Nombre: " + objPasajero.getNombrePasajero() + "\n"
                        + "Documento: " + objPasajero.getDocumentoPasajero() + "\n\n"
                        + "Esta acción no se puede deshacer.";

                Alert msg = new Alert(Alert.AlertType.CONFIRMATION);
                msg.setTitle("Confirmar Eliminación");
                msg.setHeaderText(null);
                msg.setContentText(mensaje);
                msg.initOwner(miEscenario);

                if (msg.showAndWait().get() == ButtonType.OK) {
                    int posi = miTabla.getSelectionModel().getSelectedIndex();
                    if (PasajeroControladorEliminar.borrar(posi)) {
                        int canti = PasajeroControladorListar.obtenerCantidadPasajeros();
                        titulo.setText("ADMINISTRAR PASAJEROS (" + canti + ")");

                        datosTabla.setAll(PasajeroControladorListar.obtenerPasajeros());
                        miTabla.refresh();

                        Mensaje.mostrar(Alert.AlertType.INFORMATION,
                                miEscenario, "Éxito", "Pasajero eliminado correctamente");
                    } else {
                        Mensaje.mostrar(Alert.AlertType.ERROR,
                                miEscenario, "Error", "No se pudo eliminar el pasajero");
                    }
                } else {
                    miTabla.getSelectionModel().clearSelection();
                }
            }
        });

        // EDITAR
        Button btnActualizar = new Button();
        btnActualizar.setPrefWidth(ancho);
        btnActualizar.setCursor(Cursor.HAND);
        btnActualizar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_EDITAR, tamanioIconito));

        btnActualizar.setOnAction((e) -> {
            if (miTabla.getSelectionModel().getSelectedItem() == null) {
                Mensaje.mostrar(Alert.AlertType.WARNING,
                        miEscenario, "Advertencia",
                        "Debe seleccionar un pasajero para editar");
            } else {
                PasajeroDto objPasajero = miTabla.getSelectionModel().getSelectedItem();
                int posi = miTabla.getSelectionModel().getSelectedIndex();

                panelCuerpo = PasajeroControladorVentana.editar(
                        miEscenario,
                        panelPrincipal,
                        panelCuerpo,
                        Configuracion.ANCHO_APP,
                        Configuracion.ALTO_CUERPO,
                        objPasajero,
                        posi
                );

                panelPrincipal.setCenter(null);
                panelPrincipal.setCenter(panelCuerpo);
            }
        });

        // CANCELAR SELECCIÓN
        Button btnCancelar = new Button();
        btnCancelar.setPrefWidth(ancho);
        btnCancelar.setCursor(Cursor.HAND);
        btnCancelar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_CANCELAR, tamanioIconito));
        btnCancelar.setOnAction((e) -> miTabla.getSelectionModel().clearSelection());

        cajaBotones = new HBox(5);
        cajaBotones.setAlignment(Pos.CENTER);
        cajaBotones.getChildren().addAll(btnEliminar, btnActualizar, btnCancelar);
        cajaVertical.getChildren().add(cajaBotones);
    }
}
