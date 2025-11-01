package org.poo.vista.pelicula;

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
import org.poo.controlador.pelicula.PeliculaControladorListar;
import org.poo.dto.PeliculaDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.Marco;

public class VistaPeliculaListar extends StackPane {

    private final Rectangle marco;
    private final Stage miEscenario;
    private final VBox cajaVertical;
    private final TableView<PeliculaDto> miTabla;

    private static final String ESTILO_CENTRAR = "-fx-alignment: CENTER;";
    private static final String ESTILO_DERECHA = "-fx-alignment: CENTER-RIGHT;";
    private static final String ESTILO_IZQUIERDA = "-fx-alignment: CENTER-LEFT;";

    public VistaPeliculaListar(Stage ventanaPadre, double ancho, double alto) {
        setAlignment(Pos.CENTER);
        miEscenario = ventanaPadre;
        marco = Marco.crear(miEscenario, Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.MARCO_ANCHO_PORCENTAJE, Configuracion.DEGRADE_ARREGLO_PELICULA,
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
        bloqueSeparador.prefHeightProperty().bind(miEscenario.heightProperty().multiply(0.05));
        int cant = PeliculaControladorListar.obtenerCantidadPeliculas();
        Text titulo = new Text("LISTA DE PELICULAS (" + cant + ")");
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
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.25));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }

    private TableColumn<PeliculaDto, String> crearColumnaGenero() {
        TableColumn<PeliculaDto, String> columna = new TableColumn<>("Género");
        columna.setCellValueFactory(obj -> {
            if (obj.getValue().getGeneroPelicula() != null) {
                return new SimpleStringProperty(obj.getValue().getGeneroPelicula().getNombreGenero());
            }
            return new SimpleStringProperty("");
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.20));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<PeliculaDto, String> crearColumnaPresupuesto() {
        TableColumn<PeliculaDto, String> columna = new TableColumn<>("Presupuesto");
        columna.setCellValueFactory(obj -> {
            Double presupuesto = obj.getValue().getPresupuestoPelicula();
            String presupuestoFormateado = String.format("%.2f", presupuesto);
            return new SimpleStringProperty(presupuestoFormateado);
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        columna.setStyle(ESTILO_DERECHA);
        return columna;
    }

    private TableColumn<PeliculaDto, String> crearColumnaEstado() {
        TableColumn<PeliculaDto, String> columna = new TableColumn<>("Estado");
        columna.setCellValueFactory(obj -> {
            Boolean estado = obj.getValue().getEstadoPelicula();
            String estadoTexto = (estado != null && estado) ? "+18" : "Infantil";
            return new SimpleStringProperty(estadoTexto);
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.10));
        columna.setStyle(ESTILO_CENTRAR);
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

        miEscenario.heightProperty().addListener((o, oldVal, newVal) -> miTabla.setPrefHeight(newVal.doubleValue()));
        VBox.setVgrow(miTabla, Priority.ALWAYS);

        cajaVertical.getChildren().add(miTabla);
        getChildren().add(cajaVertical);
    }
}