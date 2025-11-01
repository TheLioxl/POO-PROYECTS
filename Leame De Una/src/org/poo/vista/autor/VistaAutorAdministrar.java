package org.poo.vista.autor;

import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import org.poo.controlador.autor.AutorControladorEliminar;
import org.poo.controlador.autor.AutorControladorListar;
import org.poo.dto.AutorDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.Icono;
import org.poo.recurso.utilidad.Marco;
import org.poo.recurso.utilidad.Mensaje;

public class VistaAutorAdministrar extends StackPane {

    private final Rectangle marco;
    private final Stage miEscenario;
    private final VBox cajaVertical;
    private final TableView<AutorDto> miTabla;

    private static final String ESTILO_CENTRAR = "-fx-alignment: CENTER;";
    private static final String ESTILO_IZQUIERDA = "-fx-alignment: CENTER-LEFT;";

    private Text titulo;
    private HBox cajaBotones;

    public VistaAutorAdministrar(Stage ventanaPadre, double ancho, double alto) {
        setAlignment(Pos.CENTER);
        miEscenario = ventanaPadre;
        marco = Marco.crear(miEscenario,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_AUTOR,
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

        int cant = AutorControladorListar.obtenerCantidadAutores();
        titulo = new Text("ADMINISTRAR AUTORES (" + cant + ")");
        titulo.setFill(Color.web(Configuracion.MORADO_OSCURO));
        titulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 28));

        cajaVertical.getChildren().addAll(bloqueSeparador, titulo);
    }

    private TableColumn<AutorDto, Integer> crearColumnaCodigo() {
        TableColumn<AutorDto, Integer> columna = new TableColumn<>("Código");
        columna.setCellValueFactory(new PropertyValueFactory<>("idAutor"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<AutorDto, String> crearColumnaNombre() {
        TableColumn<AutorDto, String> columna = new TableColumn<>("Nombre");
        columna.setCellValueFactory(new PropertyValueFactory<>("nombreAutor"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.45));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }

    private TableColumn<AutorDto, String> crearColumnaGenero() {
        TableColumn<AutorDto, String> columna = new TableColumn<>("Género");
        columna.setCellValueFactory(obj -> {
            Boolean genero = obj.getValue().getGeneroAutor();
            String textoGenero = (genero != null && genero) ? "Masculino" : "Femenino";
            return new SimpleStringProperty(textoGenero);
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.20));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<AutorDto, Short> crearColumnaCantidadLibros() {
        TableColumn<AutorDto, Short> columna = new TableColumn<>("Cantidad De Libros");
        columna.setCellValueFactory(new PropertyValueFactory<>("cantidadLibrosAutor"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.20));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private void configurarColumnas() {
        miTabla.getColumns().addAll(List.of(
                crearColumnaCodigo(),
                crearColumnaNombre(),
                crearColumnaGenero(),
                crearColumnaCantidadLibros()
        ));
    }

    private void crearTabla() {
        configurarColumnas();
        List<AutorDto> arrAutores = AutorControladorListar.obtenerAutores();
        ObservableList<AutorDto> datosTabla = FXCollections.observableArrayList(arrAutores);

        miTabla.setItems(datosTabla);
        miTabla.setPlaceholder(new Text("No hay Autores Registrados."));

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
                        "Atención", "Por favor selecciona un autor");
            } else {
                AutorDto objAutorcito = miTabla.getSelectionModel().getSelectedItem();
                if (objAutorcito.getCantidadLibrosAutor() == 0) {
                    String generoTexto = objAutorcito.getGeneroAutor() ? "Masculino" : "Femenino";
                    String mensaje = "¿Estás seguro?\n\n"
                            + "Código: " + objAutorcito.getIdAutor() + "\n"
                            + "Autor: " + objAutorcito.getNombreAutor() + "\n"
                            + "Género: " + generoTexto + "\n\n"
                            + "Esta acción no se puede deshacer";

                    Alert msg = new Alert(Alert.AlertType.CONFIRMATION);
                    msg.setTitle("Confirmar Eliminación");
                    msg.setHeaderText(null);
                    msg.setContentText(mensaje);
                    msg.initOwner(miEscenario);

                    if (msg.showAndWait().get() == ButtonType.OK) {
                        int posi = miTabla.getSelectionModel().getSelectedIndex();
                        if (AutorControladorEliminar.borrar(posi)) {
                            int canti = AutorControladorListar.obtenerCantidadAutores();
                            titulo.setText("ADMINISTRAR AUTORES (" + canti + ")");

                            List<AutorDto> arrAutores = AutorControladorListar.obtenerAutores();
                            ObservableList<AutorDto> datosTablo = FXCollections.observableArrayList(arrAutores);

                            miTabla.setItems(datosTablo);
                            miTabla.refresh();
                            Mensaje.mostrar(Alert.AlertType.INFORMATION, miEscenario,
                                    "Éxito", "Autor eliminado correctamente");
                        } else {
                            Mensaje.mostrar(Alert.AlertType.ERROR, miEscenario,
                                    "Error", "No se pudo eliminar el autor");
                        }
                    } else {
                        miTabla.getSelectionModel().clearSelection();
                    }
                } else {
                    Mensaje.mostrar(Alert.AlertType.ERROR, miEscenario,
                            "No se puede eliminar",
                            "Este autor tiene " + objAutorcito.getCantidadLibrosAutor() + " libro(s) asociado(s)");
                }
            }
        });

        // Botón Actualizar
        Button btnActualizar = new Button();
        btnActualizar.setPrefWidth(anchoIcono);
        btnActualizar.setCursor(Cursor.HAND);
        btnActualizar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_EDITAR, sizeIcono));
        btnActualizar.setOnAction((e) -> {
            System.out.println("Actualizar Autor");
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