package org.poo.vista.destino;

import java.util.List;
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
import org.poo.controlador.destino.DestinoControladorListar;
import org.poo.dto.DestinoDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.Icono;
import org.poo.recurso.utilidad.Marco;

public class VistaDestinoListar extends StackPane {

    private final Rectangle marco;
    private final Stage miEscenario;
    private final VBox cajaVertical;
    private final TableView<DestinoDto> miTabla;

    private static final String ESTILO_CENTRAR = "-fx-alignment: CENTER;";
    private static final String ESTILO_IZQUIERDA = "-fx-alignment: CENTER-LEFT;";

    public VistaDestinoListar(Stage ventanaPadre, double ancho, double alto) {
        setAlignment(Pos.CENTER);
        miEscenario = ventanaPadre;
        marco = Marco.crear(
                miEscenario,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_DESTINO,
                Configuracion.DEGRADE_BORDE
        );

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

        int cant = DestinoControladorListar.obtenerCantidadDestinos();
        Text titulo = new Text("LISTA DE DESTINOS (" + cant + ")");
        titulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        titulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 28));

        cajaVertical.getChildren().addAll(bloqueSeparador, titulo);
    }

    private void configurarColumnas() {
        TableColumn<DestinoDto, Integer> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("idDestino"));
        colCodigo.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.08));
        colCodigo.setStyle(ESTILO_CENTRAR);

        TableColumn<DestinoDto, String> colNombre = new TableColumn<>("Nombre Destino");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreDestino"));
        colNombre.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.25));
        colNombre.setStyle(ESTILO_IZQUIERDA);

        TableColumn<DestinoDto, String> colDescripcion = new TableColumn<>("Descripción");
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcionDestino"));
        colDescripcion.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.35));
        colDescripcion.setStyle(ESTILO_IZQUIERDA);

        TableColumn<DestinoDto, String> colImagen = new TableColumn<>("Imagen");
        colImagen.setCellValueFactory(new PropertyValueFactory<>("nombreImagenPublicoDestino"));
        colImagen.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.32));
        colImagen.setStyle(ESTILO_IZQUIERDA);

        miTabla.getColumns().add(colCodigo);
        miTabla.getColumns().add(colNombre);
        miTabla.getColumns().add(colDescripcion);
        miTabla.getColumns().add(colImagen);
    }

    private void crearTabla() {
        configurarColumnas();

        List<DestinoDto> arrDestinos = DestinoControladorListar.obtenerDestinos();
        ObservableList<DestinoDto> datosTabla = FXCollections.observableArrayList(arrDestinos);

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
}
