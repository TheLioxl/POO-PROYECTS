package org.poo.vista.autor;

import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
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
import org.poo.controlador.autor.AutorControladorListar;
import org.poo.dto.AutorDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.Marco;

public class VistaAutorListar extends StackPane {

    private final Rectangle marco;
    private final Stage miEscenario;
    private final VBox cajaVertical;
    private final TableView<AutorDto> miTabla;

    private static final String ESTILO_CENTRAR = "-fx-alignment: CENTER;";
    private static final String ESTILO_IZQUIERDA = "-fx-alignment: CENTER-LEFT;";

    public VistaAutorListar(Stage ventanaPadre, double ancho, double alto) {
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
        Text titulo = new Text("LISTA DE AUTORES (" + cant + ")");
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
        TableColumn<AutorDto, Short> columna = new TableColumn<>("Cantidad Libros");
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
        miTabla.maxHeightProperty().bind(miEscenario.heightProperty().multiply(0.60));

        miEscenario.heightProperty().addListener((o, oldVal, newVal)
                -> miTabla.setPrefHeight(newVal.doubleValue()));
        VBox.setVgrow(miTabla, Priority.ALWAYS);

        cajaVertical.getChildren().add(miTabla);
        getChildren().add(cajaVertical);
    }
}