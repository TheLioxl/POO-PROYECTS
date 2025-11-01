package org.poo.vista.pelicula;

import java.text.DecimalFormat;
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
import javafx.scene.layout.HBox;
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
import org.poo.controlador.pelicula.PeliculaControladorEliminar;
import org.poo.controlador.pelicula.PeliculaControladorListar;
import org.poo.dto.GeneroDto;
import org.poo.dto.PeliculaDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.Icono;
import org.poo.recurso.utilidad.Marco;
import org.poo.recurso.utilidad.Mensaje;


public class VistaPeliculaAdministrar extends StackPane {

    private final Rectangle marco;
    private final Stage miEscenario;
    private final VBox cajaVertical;
    private final TableView<PeliculaDto> miTabla;

    private static final String ESTILO_CENTRAR = "-fx-alignment: CENTER;";
    private static final String ESTILO_DERECHA = "-fx-alignment: CENTER-RIGHT;";
    private static final String ESTILO_IZQUIERDA = "-fx-alignment: CENTER-LEFT;";
    private static final String ESTILO_ROJO_IZQUIERDA = "-fx-text-fill: red;" + ESTILO_IZQUIERDA;
    private static final String ESTILO_VERDE_IZQUIERDA = "-fx-text-fill: green;" + ESTILO_IZQUIERDA;
    private static final String ESTILO_ROJO_CENTRADO = "-fx-text-fill: red;" + ESTILO_CENTRAR;
    private static final String ESTILO_VERDE_CENTRADO = "-fx-text-fill: green;" + ESTILO_CENTRAR;

    private Text titulo;
    private HBox cajaBotones;

    public VistaPeliculaAdministrar(Stage ventanaPadre, double ancho, double alto) {
        setAlignment(Pos.CENTER);
        miEscenario = ventanaPadre;
        marco = Marco.crear(miEscenario,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_PELICULA,
                Configuracion.DEGRADE_BORDE);

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

        int cant = PeliculaControladorListar.obtenerCantidadPeliculas();
        titulo = new Text("ADMINISTRAR PELÍCULAS (" + cant + ")");
        titulo.setFill(Color.web(Configuracion.MORADO_OSCURO));
        titulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 28));

        cajaVertical.getChildren().addAll(bloqueSeparador, titulo);
    }

    private TableColumn<PeliculaDto, Integer> crearColumnaCodigo() {
        TableColumn<PeliculaDto, Integer> columna = new TableColumn<>("Código");
        columna.setCellValueFactory(new PropertyValueFactory<>("idPelicula"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.10));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<PeliculaDto, String> crearColumnaNombre() {
        TableColumn<PeliculaDto, String> columna = new TableColumn<>("Nombre Película");
        columna.setCellValueFactory(new PropertyValueFactory<>("nombrePelicula"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.25));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }

    private TableColumn<PeliculaDto, String> crearColumnaProtagonista() {
        TableColumn<PeliculaDto, String> columna = new TableColumn<>("Protagonista");
        columna.setCellValueFactory(new PropertyValueFactory<>("protagonistaPelicula"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.20));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }

    private TableColumn<PeliculaDto, String> crearColumnaGenero() {
        TableColumn<PeliculaDto, String> columna = new TableColumn<>("Género (Estado)");

        columna.setCellValueFactory(cellData -> {
            GeneroDto genero = cellData.getValue().getGeneroPelicula();
            if (genero != null) {
                String nombre = genero.getNombreGenero();
                Boolean estado = genero.getEstadoGenero();
                String textoEstado = (estado != null && estado) ? "Activo" : "Inactivo";
                return new SimpleStringProperty(nombre + " (" + textoEstado + ")");
            } else {
                return new SimpleStringProperty("-");
            }
        });

        columna.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if (item.contains("Activo")) {
                        setStyle(ESTILO_VERDE_IZQUIERDA);
                    } else if (item.contains("Inactivo")) {
                        setStyle(ESTILO_ROJO_IZQUIERDA);
                    } else {
                        setStyle(ESTILO_IZQUIERDA);
                    }
                }
            }
        });

        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.20));
        return columna;
    }

    private TableColumn<PeliculaDto, Double> crearColumnaPresupuesto() {
        TableColumn<PeliculaDto, Double> columna = new TableColumn<>("Presupuesto");
        columna.setCellValueFactory(new PropertyValueFactory<>("presupuestoPelicula"));

        columna.setCellFactory(col -> new TableCell<PeliculaDto, Double>() {
            private final DecimalFormat formato = new DecimalFormat("#,##0.00");

            @Override
            protected void updateItem(Double precio, boolean empty) {
                super.updateItem(precio, empty);
                if (empty || precio == null) {
                    setText(null);
                } else {
                    setText("$" + formato.format(precio));
                }
            }
        });

        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        columna.setStyle(ESTILO_DERECHA);
        return columna;
    }

    private TableColumn<PeliculaDto, Boolean> crearColumnaEstado() {
        TableColumn<PeliculaDto, Boolean> columna = new TableColumn<>("Restricción");
        columna.setCellValueFactory(new PropertyValueFactory<>("estadoPelicula"));

        columna.setCellFactory(col -> new TableCell<PeliculaDto, Boolean>() {
            @Override
            protected void updateItem(Boolean valor, boolean empty) {
                super.updateItem(valor, empty);
                if (empty || valor == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(valor ? "+18" : "Infantil");
                    setStyle(valor ? ESTILO_ROJO_CENTRADO : ESTILO_VERDE_CENTRADO);
                }
            }
        });

        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.10));
        return columna;
    }

    private void configurarColumnas() {
        miTabla.getColumns().addAll(List.of(
                crearColumnaCodigo(),
                crearColumnaNombre(),
                crearColumnaProtagonista(),
                crearColumnaGenero(),
                crearColumnaPresupuesto(),
                crearColumnaEstado()
        ));
    }

    private void crearTabla() {
    configurarColumnas();
    List<PeliculaDto> arrPeliculas = PeliculaControladorListar.obtenerPeliculas();
    ObservableList<PeliculaDto> datosTabla = FXCollections.observableArrayList(arrPeliculas);

    miTabla.setItems(datosTabla);
    miTabla.setPlaceholder(new Text("No hay Películas Registradas."));

    miTabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

    miTabla.maxWidthProperty().bind(miEscenario.widthProperty().multiply(0.70));
    miTabla.maxHeightProperty().bind(miEscenario.heightProperty().multiply(0.50));

    
     miEscenario.heightProperty().addListener((o, oldVal, newVal)
             -> miTabla.setPrefHeight(newVal.doubleValue()));
    VBox.setVgrow(miTabla, Priority.ALWAYS);

    cajaVertical.getChildren().add(miTabla);
    getChildren().add(cajaVertical);
}

    private void losIconosAdmin() {
        int anchoIcono = 40;
        int sizeIcono = 16;

        // Botón Eliminar
        Button btnEliminar = new Button();
        btnEliminar.setPrefWidth(anchoIcono);
        btnEliminar.setCursor(Cursor.HAND);
        btnEliminar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_BORRAR, sizeIcono));
        btnEliminar.setOnAction((e) -> {
            if (miTabla.getSelectionModel().getSelectedItem() == null) {
                Mensaje.mostrar(Alert.AlertType.WARNING, miEscenario,
                        "Atención", "Por favor selecciona una película");
            } else {
                PeliculaDto objPeliculita = miTabla.getSelectionModel().getSelectedItem();
                
                String mensaje = "¿Estás seguro?\n\n"
                        + "Código: " + objPeliculita.getIdPelicula() + "\n"
                        + "Película: " + objPeliculita.getNombrePelicula() + "\n"
                        + "Protagonista: " + objPeliculita.getProtagonistaPelicula() + "\n"
                        + "Presupuesto: $" + String.format("%.2f", objPeliculita.getPresupuestoPelicula()) + "\n\n"
                        + "Esta acción no se puede deshacer";

                Alert msg = new Alert(Alert.AlertType.CONFIRMATION);
                msg.setTitle("Confirmar Eliminación");
                msg.setHeaderText(null);
                msg.setContentText(mensaje);
                msg.initOwner(miEscenario);

                if (msg.showAndWait().get() == ButtonType.OK) {
                    int posi = miTabla.getSelectionModel().getSelectedIndex();
                    if (PeliculaControladorEliminar.borrar(posi)) {
                        int canti = PeliculaControladorListar.obtenerCantidadPeliculas();
                        titulo.setText("ADMINISTRAR PELÍCULAS (" + canti + ")");

                        List<PeliculaDto> arrPeliculas = PeliculaControladorListar.obtenerPeliculas();
                        ObservableList<PeliculaDto> datosTablo = FXCollections.observableArrayList(arrPeliculas);

                        miTabla.setItems(datosTablo);
                        miTabla.refresh();
                        Mensaje.mostrar(Alert.AlertType.INFORMATION, miEscenario,
                                "Éxito", "Película eliminada correctamente");
                    } else {
                        Mensaje.mostrar(Alert.AlertType.ERROR, miEscenario,
                                "Error", "No se pudo eliminar la película");
                    }
                } else {
                    miTabla.getSelectionModel().clearSelection();
                }
            }
        });

        // Botón Actualizar
        Button btnActualizar = new Button();
        btnActualizar.setPrefWidth(anchoIcono);
        btnActualizar.setCursor(Cursor.HAND);
        btnActualizar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_EDITAR, sizeIcono));
        btnActualizar.setOnAction((e) -> {
            System.out.println("Actualizar Película");
        });

        // Botón Cancelar
        Button btnCancelar = new Button();
        btnCancelar.setPrefWidth(anchoIcono);
        btnCancelar.setCursor(Cursor.HAND);
        btnCancelar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_CANCELAR, sizeIcono));
        btnCancelar.setOnAction((e) -> {
            miTabla.getSelectionModel().clearSelection();
        });

        cajaBotones = new HBox(5);
        cajaBotones.setAlignment(Pos.CENTER);
        cajaBotones.getChildren().addAll(btnActualizar, btnCancelar, btnEliminar);
        cajaVertical.getChildren().add(cajaBotones);
    }
}
