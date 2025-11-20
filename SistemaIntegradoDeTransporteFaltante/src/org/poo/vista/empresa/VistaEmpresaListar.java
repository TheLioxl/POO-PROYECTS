package org.poo.vista.empresa;

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
import org.poo.controlador.empresa.EmpresaControladorListar;
import org.poo.dto.EmpresaDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.Marco;

public class VistaEmpresaListar extends StackPane {

    private final Rectangle marco;
    private final Stage miEscenario;
    private final VBox cajaVertical;
    private final TableView<EmpresaDto> miTabla;

    private static final String ESTILO_CENTRAR = "-fx-alignment: CENTER;";
    private static final String ESTILO_IZQUIERDA = "-fx-alignment: CENTER-LEFT;";
    private static final String ESTILO_ROJO = "-fx-text-fill: " + Configuracion.ROJO_ERROR + ";" + ESTILO_CENTRAR;
    private static final String ESTILO_VERDE = "-fx-text-fill: " + Configuracion.VERDE_EXITO + ";" + ESTILO_CENTRAR;

    public VistaEmpresaListar(Stage ventanaPadre, double ancho, double alto) {
        setAlignment(Pos.CENTER);

        miEscenario = ventanaPadre;

        marco = Marco.crear(
                miEscenario,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_EMPRESA,
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

        int cant = EmpresaControladorListar.obtenerCantidadEmpresas();
        Text titulo = new Text("LISTA DE EMPRESAS (" + cant + ")");
        titulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        titulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 28));

        cajaVertical.getChildren().addAll(bloqueSeparador, titulo);
    }

    private void crearTabla() {
        TableColumn<EmpresaDto, Integer> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("idEmpresa"));
        colCodigo.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.08));
        colCodigo.setStyle(ESTILO_CENTRAR);

        TableColumn<EmpresaDto, String> colNombre = new TableColumn<>("Nombre Empresa");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreEmpresa"));
        colNombre.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.20));
        colNombre.setStyle(ESTILO_IZQUIERDA);

        TableColumn<EmpresaDto, String> colNit = new TableColumn<>("NIT");
        colNit.setCellValueFactory(new PropertyValueFactory<>("nitEmpresa"));
        colNit.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.12));
        colNit.setStyle(ESTILO_CENTRAR);

        TableColumn<EmpresaDto, String> colTerminal = new TableColumn<>("Terminal");
        colTerminal.setCellValueFactory(obj -> 
            new SimpleStringProperty(obj.getValue().getTerminalEmpresa().getNombreTerminal()));
        colTerminal.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        colTerminal.setStyle(ESTILO_IZQUIERDA);

        TableColumn<EmpresaDto, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(obj -> {
            String estado = obj.getValue().getEstadoEmpresa() ? "Activo" : "Inactivo";
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
        colEstado.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.10));

        TableColumn<EmpresaDto, Short> colBuses = new TableColumn<>("Buses");
        colBuses.setCellValueFactory(new PropertyValueFactory<>("cantidadBusesEmpresa"));
        colBuses.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.08));
        colBuses.setStyle(ESTILO_CENTRAR);

        TableColumn<EmpresaDto, String> colImagen = new TableColumn<>("Logo");
        colImagen.setCellValueFactory(new PropertyValueFactory<>("nombreImagenPublicoEmpresa"));
        colImagen.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.17));
        colImagen.setStyle(ESTILO_IZQUIERDA);

        miTabla.getColumns().addAll(List.of(
                colCodigo, colNombre, colNit, colTerminal, colEstado, colBuses, colImagen
        ));

        List<EmpresaDto> arrEmpresas = EmpresaControladorListar.obtenerEmpresas();
        ObservableList<EmpresaDto> datosTabla = FXCollections.observableArrayList(arrEmpresas);

        miTabla.setItems(datosTabla);
        miTabla.setPlaceholder(new Text("No hay empresas registradas"));
        miTabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        miTabla.maxWidthProperty().bind(miEscenario.widthProperty().multiply(0.75));
        miTabla.maxHeightProperty().bind(miEscenario.heightProperty().multiply(0.60));

        miEscenario.heightProperty().addListener((o, oldVal, newVal)
                -> miTabla.setPrefHeight(newVal.doubleValue()));

        VBox.setVgrow(miTabla, Priority.ALWAYS);

        cajaVertical.getChildren().add(miTabla);
        getChildren().add(cajaVertical);
    }
}
