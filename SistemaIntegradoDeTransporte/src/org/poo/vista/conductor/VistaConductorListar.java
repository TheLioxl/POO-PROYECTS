package org.poo.vista.conductor;

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
import org.poo.controlador.conductor.ConductorControladorListar;
import org.poo.dto.ConductorDto;
import org.poo.dto.EmpresaDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.Icono;
import org.poo.recurso.utilidad.Marco;

public class VistaConductorListar extends StackPane {

    private final Rectangle marco;
    private final Stage miEscenario;
    private final VBox cajaVertical;
    private final TableView<ConductorDto> miTabla;

    private static final String ESTILO_CENTRAR = "-fx-alignment: CENTER;";
    private static final String ESTILO_IZQUIERDA = "-fx-alignment: CENTER-LEFT;";
    private static final String ESTILO_ROJO = "-fx-text-fill: " + Configuracion.ROJO_ERROR + ";" + ESTILO_CENTRAR;
    private static final String ESTILO_VERDE = "-fx-text-fill: " + Configuracion.VERDE_EXITO + ";" + ESTILO_CENTRAR;

    public VistaConductorListar(Stage ventanaPadre, double ancho, double alto) {
        setAlignment(Pos.CENTER);
        miEscenario = ventanaPadre;
        marco = Marco.crear(
                miEscenario,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_CONDUCTOR, // análogo al de TERMINAL
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

        int cant = ConductorControladorListar.obtenerCantidadConductores();
        Text titulo = new Text("LISTA DE CONDUCTORES (" + cant + ")");
        titulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        titulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 28));

        cajaVertical.getChildren().addAll(bloqueSeparador, titulo);
    }

    private TableColumn<ConductorDto, Integer> crearColumnaCodigo() {
        TableColumn<ConductorDto, Integer> columna = new TableColumn<>("Código");
        columna.setCellValueFactory(new PropertyValueFactory<>("idConductor"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.08));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<ConductorDto, String> crearColumnaNombre() {
        TableColumn<ConductorDto, String> columna = new TableColumn<>("Nombre Conductor");
        columna.setCellValueFactory(new PropertyValueFactory<>("nombreConductor"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.22));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }

    private TableColumn<ConductorDto, String> crearColumnaCedula() {
        TableColumn<ConductorDto, String> columna = new TableColumn<>("Cédula");
        columna.setCellValueFactory(new PropertyValueFactory<>("cedulaConductor"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.14));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<ConductorDto, String> crearColumnaTelefono() {
        TableColumn<ConductorDto, String> columna = new TableColumn<>("Teléfono");
        columna.setCellValueFactory(new PropertyValueFactory<>("telefonoConductor"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.14));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<ConductorDto, String> crearColumnaEmpresa() {
        TableColumn<ConductorDto, String> columna = new TableColumn<>("Empresa");
        columna.setCellValueFactory(obj -> {
            EmpresaDto emp = obj.getValue().getEmpresaConductor();
            String nombreEmp = (emp != null) ? emp.getNombreEmpresa() : "Sin empresa";
            return new SimpleStringProperty(nombreEmp);
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.18));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }

    private TableColumn<ConductorDto, String> crearColumnaEstado() {
        TableColumn<ConductorDto, String> columna = new TableColumn<>("Estado");
        columna.setCellValueFactory(obj -> {
            String estado = obj.getValue().getEstadoConductor() ? "Activo" : "Inactivo";
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

        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.10));
        return columna;
    }

    private TableColumn<ConductorDto, String> crearColumnaImagen() {
        TableColumn<ConductorDto, String> columna = new TableColumn<>("Imagen");

        // 👉 nombre PÚBLICO (el que ve el usuario), no el privado
        columna.setCellValueFactory(new PropertyValueFactory<>("nombreImagenPublicoConductor"));

        // Solo texto, sin miniaturas ni iconos
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.14));
        columna.setStyle(ESTILO_CENTRAR);

        return columna;
    }

    private void configurarColumnas() {
        miTabla.getColumns().add(crearColumnaCodigo());
        miTabla.getColumns().add(crearColumnaNombre());
        miTabla.getColumns().add(crearColumnaCedula());
        miTabla.getColumns().add(crearColumnaTelefono());
        miTabla.getColumns().add(crearColumnaEmpresa());
        miTabla.getColumns().add(crearColumnaEstado());
        miTabla.getColumns().add(crearColumnaImagen());
    }

    private void crearTabla() {
        configurarColumnas();

        List<ConductorDto> arrConductores = ConductorControladorListar.obtenerConductores();
        ObservableList<ConductorDto> datosTabla = FXCollections.observableArrayList(arrConductores);

        miTabla.setItems(datosTabla);
        miTabla.setPlaceholder(new Text("No hay conductores registrados"));
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
