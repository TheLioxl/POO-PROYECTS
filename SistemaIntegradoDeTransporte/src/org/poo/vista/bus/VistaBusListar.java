package org.poo.vista.bus;

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
import org.poo.controlador.bus.BusControladorListar;
import org.poo.dto.BusDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.Icono;
import org.poo.recurso.utilidad.Marco;

public class VistaBusListar extends StackPane {

    private final Rectangle marco;
    private final Stage miEscenario;
    private final VBox cajaVertical;
    private final TableView<BusDto> miTabla;

    private static final String ESTILO_CENTRAR = "-fx-alignment: CENTER;";
    private static final String ESTILO_IZQUIERDA = "-fx-alignment: CENTER-LEFT;";
    private static final String ESTILO_ROJO = "-fx-text-fill: " + Configuracion.ROJO_ERROR + ";" + ESTILO_CENTRAR;
    private static final String ESTILO_VERDE = "-fx-text-fill: " + Configuracion.VERDE_EXITO + ";" + ESTILO_CENTRAR;

    public VistaBusListar(Stage ventanaPadre, double ancho, double alto) {
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

        int cant = BusControladorListar.obtenerCantidadBuses();
        Text titulo = new Text("LISTA DE BUSES (" + cant + ")");
        titulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        titulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 28));

        cajaVertical.getChildren().addAll(bloqueSeparador, titulo);
    }

    private void crearTabla() {
        
        TableColumn<BusDto, Integer> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("idBus"));
        colCodigo.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.10));
        colCodigo.setStyle(ESTILO_CENTRAR);

        TableColumn<BusDto, String> colPlaca = new TableColumn<>("Placa");
        colPlaca.setCellValueFactory(new PropertyValueFactory<>("placaBus"));
        colPlaca.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.20));
        colPlaca.setStyle(ESTILO_CENTRAR);

        TableColumn<BusDto, String> colModelo = new TableColumn<>("Modelo");
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modeloBus"));
        colModelo.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.20));
        colModelo.setStyle(ESTILO_IZQUIERDA);

        TableColumn<BusDto, Integer> colCapacidad = new TableColumn<>("Capacidad");
        colCapacidad.setCellValueFactory(new PropertyValueFactory<>("capacidadBus"));
        colCapacidad.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.12));
        colCapacidad.setStyle(ESTILO_CENTRAR);

        TableColumn<BusDto, String> colEmpresa = new TableColumn<>("Empresa");
        colEmpresa.setCellValueFactory(obj -> 
            new SimpleStringProperty(obj.getValue().getEmpresaBus().getNombreEmpresa()));
        colEmpresa.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.20));
        colEmpresa.setStyle(ESTILO_IZQUIERDA);

        TableColumn<BusDto, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoBus"));
        colTipo.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.20));
        colTipo.setStyle(ESTILO_CENTRAR);

        TableColumn<BusDto, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(obj -> {
            String estado = obj.getValue().getEstadoBus() ? "Activo" : "Inactivo";
            return new SimpleStringProperty(estado);
        });
        colEstado.setCellFactory(col -> new TableCell<>() {
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
        colEstado.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.20));

        TableColumn<BusDto, String> colImagen = new TableColumn<>("Imagen");
        colImagen.setCellValueFactory(new PropertyValueFactory<>("nombreImagenPublicoBus"));
        colImagen.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.20));
        colImagen.setStyle(ESTILO_IZQUIERDA);

        miTabla.getColumns().add(colCodigo);
        miTabla.getColumns().add(colPlaca);
        miTabla.getColumns().add(colModelo);
        miTabla.getColumns().add(colCapacidad);
        miTabla.getColumns().add(colEmpresa);
        miTabla.getColumns().add(colTipo);
        miTabla.getColumns().add(colEstado);
        miTabla.getColumns().add(colImagen);
        
        List<BusDto> arrBuses = BusControladorListar.obtenerBuses();
        ObservableList<BusDto> datosTabla = FXCollections.observableArrayList(arrBuses);

        miTabla.setItems(datosTabla);
        miTabla.setPlaceholder(new Text("No hay buses registrados"));
        miTabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        miTabla.maxWidthProperty().bind(miEscenario.widthProperty().multiply(0.72));
        miTabla.maxHeightProperty().bind(miEscenario.heightProperty().multiply(0.60));

        miEscenario.heightProperty().addListener((o, oldVal, newVal)
                -> miTabla.setPrefHeight(newVal.doubleValue()));

        VBox.setVgrow(miTabla, Priority.ALWAYS);

        cajaVertical.getChildren().add(miTabla);
        getChildren().add(cajaVertical);
    }
}
