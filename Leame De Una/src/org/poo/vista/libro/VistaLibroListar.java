package org.poo.vista.libro;

import java.text.DecimalFormat;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
import org.poo.controlador.libro.LibroControladorListar;
import org.poo.dto.AutorDto;
import org.poo.dto.EditorialDto;
import org.poo.dto.LibroDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.Marco;

public class VistaLibroListar extends StackPane {

    private final Rectangle marco;
    private final Stage miEscenario;
    private final VBox cajaVertical;
    private final TableView<LibroDto> miTabla;

    private static final String ESTILO_CENTRAR = "-fx-alignment: CENTER;";
    private static final String ESTILO_DERECHA = "-fx-alignment: CENTER-RIGHT;";
    private static final String ESTILO_IZQUIERDA = "-fx-alignment: CENTER-LEFT;";

    public VistaLibroListar(Stage ventanaPadre, double ancho, double alto) {
        setAlignment(Pos.CENTER);
        miEscenario = ventanaPadre;
        marco = Marco.crear(miEscenario,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_LIBRO,
                Configuracion.DEGRADE_BORDE);

        miTabla = new TableView<>();
        cajaVertical = new VBox(20);
        getChildren().add(marco);

        configurarCajaVertical();
        crearTitulo();
        crearTabla();
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

        int cant = LibroControladorListar.obtenerCantidadLibros();
        Text titulo = new Text("LISTA DE LIBROS (" + cant + ")");
        titulo.setFill(Color.web(Configuracion.MORADO_OSCURO));
        titulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 28));

        cajaVertical.getChildren().addAll(bloqueSeparador, titulo);
    }

    private TableColumn<LibroDto, Integer> crearColumnaCodigo() {
        TableColumn<LibroDto, Integer> columna = new TableColumn<>("Código");
        columna.setCellValueFactory(new PropertyValueFactory<>("idLibro"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.08));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<LibroDto, String> crearColumnaNombre() {
        TableColumn<LibroDto, String> columna = new TableColumn<>("Nombre Libro");
        columna.setCellValueFactory(new PropertyValueFactory<>("nombreLibro"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.25));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }

    private TableColumn<LibroDto, Double> crearColumnaPrecio() {
        TableColumn<LibroDto, Double> columna = new TableColumn<>("Precio");
        columna.setCellValueFactory(new PropertyValueFactory<>("precioLibro"));

        columna.setCellFactory(col -> new TableCell<LibroDto, Double>() {
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

        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.12));
        columna.setStyle(ESTILO_DERECHA);
        return columna;
    }

    private TableColumn<LibroDto, Short> crearColumnaAnio() {
        TableColumn<LibroDto, Short> columna = new TableColumn<>("Año");
        columna.setCellValueFactory(new PropertyValueFactory<>("anioLibro"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.08));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<LibroDto, String> crearColumnaEditorial() {
        TableColumn<LibroDto, String> columna = new TableColumn<>("Editorial (País)");
        columna.setCellValueFactory(cellData -> {
            EditorialDto editorial = cellData.getValue().getIdEditorialLibro();
            if (editorial != null) {
                String nombre = editorial.getNombreEditorial();
                String pais = editorial.getPaisEditorial();
                return new SimpleStringProperty(nombre + " (" + pais + ")");
            } else {
                return new SimpleStringProperty("-");
            }
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.23));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }

    private TableColumn<LibroDto, String> crearColumnaAutor() {
        TableColumn<LibroDto, String> columna = new TableColumn<>("Autor (Género)");
        columna.setCellValueFactory(cellData -> {
            AutorDto autor = cellData.getValue().getIdAutorLibro();
            if (autor != null) {
                String nombre = autor.getNombreAutor();
                Boolean genero = autor.getGeneroAutor();
                String textoGenero = (genero != null && genero) ? "Masculino" : "Femenino";
                return new SimpleStringProperty(nombre + " (" + textoGenero + ")");
            } else {
                return new SimpleStringProperty("-");
            }
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.24));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }

    private void configurarColumnas() {
        miTabla.getColumns().addAll(List.of(
                crearColumnaCodigo(),
                crearColumnaNombre(),
                crearColumnaPrecio(),
                crearColumnaAnio(),
                crearColumnaEditorial(),
                crearColumnaAutor()
        ));
    }

    private void crearTabla() {
        configurarColumnas();
        List<LibroDto> arrLibros = LibroControladorListar.obtenerLibros();
        ObservableList<LibroDto> datosTabla = FXCollections.observableArrayList(arrLibros);

        miTabla.setItems(datosTabla);
        miTabla.setPlaceholder(new Text("No hay Libros Registrados."));

        miTabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        miTabla.maxWidthProperty().bind(miEscenario.widthProperty().multiply(0.70));
        miTabla.maxHeightProperty().bind(miEscenario.heightProperty().multiply(0.60));

        miEscenario.heightProperty().addListener((o, oldVal, newVal)
                -> miTabla.setPrefHeight(newVal.doubleValue()));
        VBox.setVgrow(miTabla, Priority.ALWAYS);

        cajaVertical.getChildren().add(miTabla);
        getChildren().add(cajaVertical);
    }
}