package org.poo.vista.tiquete;

import java.time.format.DateTimeFormatter;
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
import org.poo.controlador.tiquete.TiqueteControladorListar;
import org.poo.dto.TiqueteDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.Icono;
import org.poo.recurso.utilidad.Marco;

public class VistaTiqueteListar extends StackPane {

    private final Rectangle marco;
    private final Stage miEscenario;
    private final VBox cajaVertical;
    private final TableView<TiqueteDto> miTabla;

    private static final String ESTILO_CENTRAR = "-fx-alignment: CENTER;";
    private static final String ESTILO_IZQUIERDA = "-fx-alignment: CENTER-LEFT;";
    private static final String ESTILO_ROJO = "-fx-text-fill: " + Configuracion.ROJO_ERROR + ";" + ESTILO_CENTRAR;
    private static final String ESTILO_VERDE = "-fx-text-fill: " + Configuracion.VERDE_EXITO + ";" + ESTILO_CENTRAR;

    public VistaTiqueteListar(Stage ventanaPadre, double ancho, double alto) {
        setAlignment(Pos.CENTER);
        miEscenario = ventanaPadre;
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

        int cant = TiqueteControladorListar.obtenerCantidadTiquetes();
        Text titulo = new Text("LISTA DE TIQUETES (" + cant + ")");
        titulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        titulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 28));

        cajaVertical.getChildren().addAll(bloqueSeparador, titulo);
    }

    private TableColumn<TiqueteDto, Integer> crearColumnaCodigo() {
        TableColumn<TiqueteDto, Integer> columna = new TableColumn<>("Código");
        columna.setCellValueFactory(new PropertyValueFactory<>("idTiquete"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.08));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<TiqueteDto, String> crearColumnaViaje() {
        TableColumn<TiqueteDto, String> columna = new TableColumn<>("Viaje");
        columna.setCellValueFactory(obj -> {
            String viaje = "Viaje #" + obj.getValue().getViajeTiquete().getIdViaje() + 
                           " - " + obj.getValue().getViajeTiquete().getRutaViaje().getNombreRuta();
            return new SimpleStringProperty(viaje);
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.20));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }

    private TableColumn<TiqueteDto, String> crearColumnaPasajero() {
        TableColumn<TiqueteDto, String> columna = new TableColumn<>("Pasajero");
        columna.setCellValueFactory(obj -> {
            String pasajero = obj.getValue().getPasajeroTiquete().getNombrePasajero() + " - " +
                              obj.getValue().getPasajeroTiquete().getDocumentoPasajero();
            return new SimpleStringProperty(pasajero);
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.18));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }

    private TableColumn<TiqueteDto, Integer> crearColumnaAsiento() {
        TableColumn<TiqueteDto, Integer> columna = new TableColumn<>("Asiento");
        columna.setCellValueFactory(new PropertyValueFactory<>("numeroAsientoTiquete"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.08));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<TiqueteDto, String> crearColumnaPrecio() {
        TableColumn<TiqueteDto, String> columna = new TableColumn<>("Precio");
        columna.setCellValueFactory(obj -> {
            String precio = String.format("$%.2f", obj.getValue().getPrecioTiquete());
            return new SimpleStringProperty(precio);
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.10));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<TiqueteDto, String> crearColumnaFecha() {
        TableColumn<TiqueteDto, String> columna = new TableColumn<>("Fecha Compra");
        columna.setCellValueFactory(obj -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            String fecha = obj.getValue().getFechaCompraTiquete().format(formatter);
            return new SimpleStringProperty(fecha);
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.14));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<TiqueteDto, String> crearColumnaImagen() {
        TableColumn<TiqueteDto, String> columna = new TableColumn<>("Imagen");
        columna.setCellValueFactory(new PropertyValueFactory<>("nombreImagenPublicoTiquete"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.20));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }

    private void configurarColumnas() {
        miTabla.getColumns().add(crearColumnaCodigo());
        miTabla.getColumns().add(crearColumnaViaje());
        miTabla.getColumns().add(crearColumnaPasajero());
        miTabla.getColumns().add(crearColumnaAsiento());
        miTabla.getColumns().add(crearColumnaPrecio());
        miTabla.getColumns().add(crearColumnaFecha());
        miTabla.getColumns().add(crearColumnaImagen());
    }

    private void crearTabla() {
        configurarColumnas();

        List<TiqueteDto> arrTiquetes = TiqueteControladorListar.obtenerTiquetes();
        ObservableList<TiqueteDto> datosTabla = FXCollections.observableArrayList(arrTiquetes);

        miTabla.setItems(datosTabla);
        miTabla.setPlaceholder(new Text("No hay tiquetes registrados"));
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
