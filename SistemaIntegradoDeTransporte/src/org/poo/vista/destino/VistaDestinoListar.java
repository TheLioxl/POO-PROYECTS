package org.poo.vista.destino;

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

    private TableColumn<DestinoDto, Integer> crearColumnaCodigo() {
        TableColumn<DestinoDto, Integer> columna = new TableColumn<>("Código");
        columna.setCellValueFactory(new PropertyValueFactory<>("idDestino"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.08));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<DestinoDto, String> crearColumnaNombre() {
        TableColumn<DestinoDto, String> columna = new TableColumn<>("Nombre Destino");
        columna.setCellValueFactory(new PropertyValueFactory<>("nombreDestino"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }

    private TableColumn<DestinoDto, String> crearColumnaDepartamento() {
        TableColumn<DestinoDto, String> columna = new TableColumn<>("Departamento");
        columna.setCellValueFactory(new PropertyValueFactory<>("departamentoDestino"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.12));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<DestinoDto, String> crearColumnaDescripcion() {
        TableColumn<DestinoDto, String> columna = new TableColumn<>("Descripción");
        columna.setCellValueFactory(new PropertyValueFactory<>("descripcionDestino"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }

    private TableColumn<DestinoDto, Integer> crearColumnaAltitud() {
        TableColumn<DestinoDto, Integer> columna = new TableColumn<>("Altitud (msnm)");
        columna.setCellValueFactory(new PropertyValueFactory<>("altitudMetros"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.10));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<DestinoDto, Double> crearColumnaTemperatura() {
        TableColumn<DestinoDto, Double> columna = new TableColumn<>("Temp. °C");
        columna.setCellValueFactory(new PropertyValueFactory<>("temperaturaPromedio"));
        columna.setCellFactory(col -> new TableCell<DestinoDto, Double>() {
            @Override
            protected void updateItem(Double temp, boolean empty) {
                super.updateItem(temp, empty);
                if (empty || temp == null) {
                    setText(null);
                } else {
                    setText(String.format("%.1f°C", temp));
                }
            }
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.08));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<DestinoDto, String> crearColumnaTipo() {
        TableColumn<DestinoDto, String> columna = new TableColumn<>("Tipo");
        columna.setCellValueFactory(obj -> {
            Boolean esPlayero = obj.getValue().getEsPlayero();
            String tipo = (esPlayero != null && esPlayero) ? "Playero" : "Montañoso";
            return new SimpleStringProperty(tipo);
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.10));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<DestinoDto, String> crearColumnaImagen() {
        TableColumn<DestinoDto, String> columna = new TableColumn<>("Imagen");
        columna.setCellValueFactory(new PropertyValueFactory<>("nombreImagenPrivadoDestino"));
        columna.setCellFactory(column -> new TableCell<DestinoDto, String>() {
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
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.12));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private void configurarColumnas() {
        miTabla.getColumns().add(crearColumnaCodigo());
        miTabla.getColumns().add(crearColumnaNombre());
        miTabla.getColumns().add(crearColumnaDepartamento());
        miTabla.getColumns().add(crearColumnaDescripcion());
        miTabla.getColumns().add(crearColumnaAltitud());
        miTabla.getColumns().add(crearColumnaTemperatura());
        miTabla.getColumns().add(crearColumnaTipo());
        miTabla.getColumns().add(crearColumnaImagen());
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
