package org.poo.vista.viaje;

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
import org.poo.controlador.viaje.ViajeControladorListar;
import org.poo.dto.ViajeDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.Marco;

public class VistaViajeListar extends StackPane {

    private final Rectangle marco;
    private final Stage miEscenario;
    private final VBox cajaVertical;
    private final TableView<ViajeDto> miTabla;

    private static final String ESTILO_CENTRAR = "-fx-alignment: CENTER;";
    private static final String ESTILO_IZQUIERDA = "-fx-alignment: CENTER-LEFT;";
    private static final String ESTILO_ROJO = "-fx-text-fill: " + Configuracion.ROJO_ERROR + ";" + ESTILO_CENTRAR;
    private static final String ESTILO_VERDE = "-fx-text-fill: " + Configuracion.VERDE_EXITO + ";" + ESTILO_CENTRAR;

    public VistaViajeListar(Stage ventanaPadre, double ancho, double alto) {
        setAlignment(Pos.CENTER);

        miEscenario = ventanaPadre;

        marco = Marco.crear(
                miEscenario,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_VIAJE,
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

        int cant = ViajeControladorListar.obtenerCantidadViajes();
        Text titulo = new Text("LISTA DE VIAJES (" + cant + ")");
        titulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));

        cajaVertical.getChildren().addAll(bloqueSeparador, titulo);
    }

    private TableColumn<ViajeDto, Integer> crearColumnaCodigo() {
        TableColumn<ViajeDto, Integer> columna = new TableColumn<>("Código");
        columna.setCellValueFactory(new PropertyValueFactory<>("idViaje"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.08));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<ViajeDto, String> crearColumnaFecha() {
        TableColumn<ViajeDto, String> columna = new TableColumn<>("Fecha Viaje");
        columna.setCellValueFactory(obj -> 
            new SimpleStringProperty(obj.getValue().getFechaViaje() != null ? 
                obj.getValue().getFechaViaje().toString() : ""));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.12));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<ViajeDto, String> crearColumnaHoraSalida() {
        TableColumn<ViajeDto, String> columna = new TableColumn<>("Hora Salida");
        columna.setCellValueFactory(obj -> 
            new SimpleStringProperty(obj.getValue().getHoraSalidaViaje() != null ? 
                obj.getValue().getHoraSalidaViaje().toString() : ""));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.10));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<ViajeDto, String> crearColumnaHoraLlegada() {
        TableColumn<ViajeDto, String> columna = new TableColumn<>("Hora Llegada");
        columna.setCellValueFactory(obj -> 
            new SimpleStringProperty(obj.getValue().getHoraLlegadaViaje() != null ? 
                obj.getValue().getHoraLlegadaViaje().toString() : ""));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.10));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<ViajeDto, String> crearColumnaRuta() {
        TableColumn<ViajeDto, String> columna = new TableColumn<>("Ruta");
        columna.setCellValueFactory(obj -> 
            new SimpleStringProperty(obj.getValue().getRutaViaje() != null ? 
                obj.getValue().getRutaViaje().toString() : ""));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.12));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }

    private TableColumn<ViajeDto, String> crearColumnaConductor() {
        TableColumn<ViajeDto, String> columna = new TableColumn<>("Conductor");
        columna.setCellValueFactory(obj -> 
            new SimpleStringProperty(obj.getValue().getConductorViaje() != null ? 
                obj.getValue().getConductorViaje().toString() : ""));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.12));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }

    private TableColumn<ViajeDto, String> crearColumnaBus() {
        TableColumn<ViajeDto, String> columna = new TableColumn<>("Bus");
        columna.setCellValueFactory(obj -> 
            new SimpleStringProperty(obj.getValue().getBusViaje() != null ? 
                obj.getValue().getBusViaje().toString() : ""));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.10));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<ViajeDto, Integer> crearColumnaAsientos() {
        TableColumn<ViajeDto, Integer> columna = new TableColumn<>("Asientos");
        columna.setCellValueFactory(new PropertyValueFactory<>("asientosDisponiblesViaje"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.08));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<ViajeDto, Double> crearColumnaPrecio() {
        TableColumn<ViajeDto, Double> columna = new TableColumn<>("Precio");
        columna.setCellValueFactory(new PropertyValueFactory<>("precioViaje"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.10));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<ViajeDto, String> crearColumnaEstado() {
        TableColumn<ViajeDto, String> columna = new TableColumn<>("Estado");
        columna.setCellValueFactory(obj -> {
            String estado = obj.getValue().getEstadoViaje() ? "Activo" : "Cancelado";
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

        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.08));
        return columna;
    }

    private void configurarColumnas() {
        miTabla.getColumns().addAll(List.of(
                crearColumnaCodigo(),
                crearColumnaFecha(),
                crearColumnaHoraSalida(),
                crearColumnaHoraLlegada(),
                crearColumnaRuta(),
                crearColumnaConductor(),
                crearColumnaBus(),
                crearColumnaAsientos(),
                crearColumnaPrecio(),
                crearColumnaEstado()
        ));
    }

    private void crearTabla() {
        configurarColumnas();

        List<ViajeDto> arrViajes = ViajeControladorListar.obtenerViajes();
        ObservableList<ViajeDto> datosTabla = FXCollections.observableArrayList(arrViajes);

        miTabla.setItems(datosTabla);
        miTabla.setPlaceholder(new Text("No hay viajes registrados"));
        miTabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        miTabla.maxWidthProperty().bind(miEscenario.widthProperty().multiply(0.95));
        miTabla.maxHeightProperty().bind(miEscenario.heightProperty().multiply(0.60));

        miEscenario.heightProperty().addListener((o, oldVal, newVal)
                -> miTabla.setPrefHeight(newVal.doubleValue()));

        VBox.setVgrow(miTabla, Priority.ALWAYS);

        cajaVertical.getChildren().add(miTabla);
        getChildren().add(cajaVertical);
    }
}