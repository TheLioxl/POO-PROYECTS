package org.poo.vista.pasajero;

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
import org.poo.controlador.pasajero.PasajeroControladorListar;
import org.poo.dto.PasajeroDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.Marco;

public class VistaPasajeroListar extends StackPane {

    private final Rectangle marco;
    private final Stage miEscenario;
    private final VBox cajaVertical;
    private final TableView<PasajeroDto> miTabla;

    private static final String ESTILO_CENTRAR = "-fx-alignment: CENTER;";
    private static final String ESTILO_IZQUIERDA = "-fx-alignment: CENTER-LEFT;";
    private static final String ESTILO_ROJO = "-fx-text-fill: " + Configuracion.ROJO_ERROR + ";" + ESTILO_CENTRAR;
    private static final String ESTILO_VERDE = "-fx-text-fill: " + Configuracion.VERDE_EXITO + ";" + ESTILO_CENTRAR;

    public VistaPasajeroListar(Stage ventanaPadre, double ancho, double alto) {
        setAlignment(Pos.CENTER);
        miEscenario = ventanaPadre;
        marco = Marco.crear(
                miEscenario,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_CONDUCTOR,
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

        int cant = PasajeroControladorListar.obtenerCantidadPasajeros();
        Text titulo = new Text("LISTA DE PASAJEROS (" + cant + ")");
        titulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        titulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 28));

        cajaVertical.getChildren().addAll(bloqueSeparador, titulo);
    }

    private TableColumn<PasajeroDto, Integer> crearColumnaCodigo() {
        TableColumn<PasajeroDto, Integer> columna = new TableColumn<>("Código");
        columna.setCellValueFactory(new PropertyValueFactory<>("idPasajero"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.07));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<PasajeroDto, String> crearColumnaNombre() {
        TableColumn<PasajeroDto, String> columna = new TableColumn<>("Nombre");
        columna.setCellValueFactory(new PropertyValueFactory<>("nombrePasajero"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.18));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }

    private TableColumn<PasajeroDto, String> crearColumnaDocumento() {
        TableColumn<PasajeroDto, String> columna = new TableColumn<>("Documento");
        columna.setCellValueFactory(new PropertyValueFactory<>("documentoPasajero"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.14));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

   private TableColumn<PasajeroDto, String> crearColumnaTelefono() {
        TableColumn<PasajeroDto, String> columna = new TableColumn<>("Telefono");
        columna.setCellValueFactory(new PropertyValueFactory<>("telefonoPasajero"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.14));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<PasajeroDto, String> crearColumnaMayor() {
        TableColumn<PasajeroDto, String> columna = new TableColumn<>("Mayor de edad");
        columna.setCellValueFactory(obj -> {
            Boolean esMayor = obj.getValue().getEsMayorPasajero();
            String texto = Boolean.TRUE.equals(esMayor) ? "Mayor" : "Menor";
            return new SimpleStringProperty(texto);
        });

        columna.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String texto, boolean empty) {
                super.updateItem(texto, empty);
                if (empty || texto == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(texto);
                    // opcional: colores tipo activo/inactivo
                    if ("Mayor".equals(texto)) {
                        setStyle(ESTILO_VERDE);
                    } else {
                        setStyle(ESTILO_ROJO);
                    }
                }
            }
        });

        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.10));
        return columna;
    }

    private TableColumn<PasajeroDto, String> crearColumnaImagen() {
        TableColumn<PasajeroDto, String> columna = new TableColumn<>("Imagen documento");
        columna.setCellValueFactory(new PropertyValueFactory<>("nombreImagenPublicoPasajero"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        columna.setStyle(ESTILO_CENTRAR);

        return columna;
    }

    private void configurarColumnas() {
        miTabla.getColumns().add(crearColumnaCodigo());
        miTabla.getColumns().add(crearColumnaNombre());
        miTabla.getColumns().add(crearColumnaDocumento());
        miTabla.getColumns().add(crearColumnaMayor());
        miTabla.getColumns().add(crearColumnaTelefono());
        miTabla.getColumns().add(crearColumnaImagen()); // ✅ Agregada
    }

    private void crearTabla() {
        configurarColumnas();

        List<PasajeroDto> arrPasajeros = PasajeroControladorListar.obtenerPasajeros();
        ObservableList<PasajeroDto> datosTabla = FXCollections.observableArrayList(arrPasajeros);

        miTabla.setItems(datosTabla);
        miTabla.setPlaceholder(new Text("No hay pasajeros registrados"));
        miTabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        miTabla.maxWidthProperty().bind(miEscenario.widthProperty().multiply(0.85));
        miTabla.maxHeightProperty().bind(miEscenario.heightProperty().multiply(0.60));

        miEscenario.heightProperty().addListener((o, oldVal, newVal)
                -> miTabla.setPrefHeight(newVal.doubleValue()));

        VBox.setVgrow(miTabla, Priority.ALWAYS);

        cajaVertical.getChildren().add(miTabla);
        getChildren().add(cajaVertical);
    }
}

