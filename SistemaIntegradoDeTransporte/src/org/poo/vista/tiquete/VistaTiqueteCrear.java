package org.poo.vista.tiquete;

import java.nio.file.Paths;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.poo.controlador.tiquete.TiqueteControladorGrabar;
import org.poo.controlador.viaje.ViajeControladorListar;
import org.poo.controlador.pasajero.PasajeroControladorListar;
import org.poo.dto.TiqueteDto;
import org.poo.dto.ViajeDto;
import org.poo.dto.PasajeroDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.Formulario;
import org.poo.recurso.utilidad.GestorImagen;
import org.poo.recurso.utilidad.Icono;
import org.poo.recurso.utilidad.Marco;
import org.poo.recurso.utilidad.Mensaje;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import javafx.stage.Window;

public class VistaTiqueteCrear extends StackPane {

    private static final int H_GAP = 10;
    private static final int V_GAP = 20;
    private static final int ALTO_FILA = 30;
    private static final int ALTO_CAJA = 30;
    private static final int TAMANIO_FUENTE = 20;
    private static final double AJUSTE_TITULO = 0.1;

    private final GridPane miGrilla;
    private final Rectangle miMarco;

    // =================  CONTROLES  =================
    // Tipo 1: ComboBox (Viaje, Pasajero, Método de pago, Categoría)
    private ComboBox<ViajeDto> cmbViaje;
    private ComboBox<PasajeroDto> cmbPasajero;
    private ComboBox<String> cmbMetodoPago;
    private ComboBox<String> cmbCategoria;

    // Tipo 2: Spinner (Asiento y Precio)
    private Spinner<Integer> spinnerAsiento;
    private Spinner<Double> spinnerPrecio;

    // Tipo 3: DatePicker (Fecha de compra)
    private DatePicker dateFechaCompra;

    // Tipo 4: CheckBox (Equipaje extra)
    private CheckBox chkEquipajeExtra;

    // Tipo 5: TextField (Nombre público imagen + ruta de imagen)
    private TextField txtReferencia;   // nombre público de la imagen
    private TextField cajaImagen;      // ruta / archivo real

    // Tipo 6: (ya tienes botones, imageview, etc., pero los 5 tipos de entrada ya están)
    private Button btnSeleccionarImagen;
    private Button btnGrabar;
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private String rutaImagenSeleccionada;

    public VistaTiqueteCrear(Stage escenario, double ancho, double alto) {
        rutaImagenSeleccionada = "";

        setAlignment(Pos.CENTER);
        miGrilla = new GridPane();
        miMarco = Marco.crear(escenario,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_TERMINAL,
                Configuracion.DEGRADE_BORDE);

        getChildren().add(miMarco);

        configurarGrilla(ancho, alto);
        crearTitulo();
        crearFormulario();
        colocarFrmElegante();
        getChildren().add(miGrilla);
    }

    private void configurarGrilla(double ancho, double alto) {
        double miAnchoGrilla = ancho * Configuracion.GRILLA_ANCHO_PORCENTAJE;

        miGrilla.setHgap(H_GAP);
        miGrilla.setVgap(V_GAP);
        miGrilla.maxWidthProperty().bind(widthProperty().multiply(0.70));
        miGrilla.maxHeightProperty().bind(heightProperty().multiply(0.80));
        miGrilla.setAlignment(Pos.CENTER);

        ColumnConstraints col0 = new ColumnConstraints();
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        
        col0.setPercentWidth(30);  // etiqueta
        col1.setPercentWidth(45);  // campo de texto
        col2.setPercentWidth(30);  // imagen
        col1.setHgrow(Priority.ALWAYS);
        miGrilla.getColumnConstraints().addAll(col0, col1, col2);

        for (int i = 0; i < 12; i++) {
            RowConstraints fila = new RowConstraints();
            fila.setMinHeight(ALTO_FILA);
            fila.setMaxHeight(ALTO_FILA);
            fila.setVgrow(Priority.ALWAYS);
            miGrilla.getRowConstraints().add(fila);
        }
    }


    private void crearTitulo() {
        Text miTitulo = new Text("FORMULARIO - CREAR TIQUETE");
        miTitulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        miTitulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 28));
        GridPane.setHalignment(miTitulo, HPos.CENTER);
        GridPane.setMargin(miTitulo, new Insets(30, 0, 0, 0));
        miGrilla.add(miTitulo, 0, 0, 3, 2);
    }

    private void crearFormulario() {
        // ===== VIAJE =====
        Label lblViaje = new Label("Viaje:");
        lblViaje.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblViaje, 0, 2);

        cmbViaje = new ComboBox<>();
        cmbViaje.setMaxWidth(Double.MAX_VALUE);
        cmbViaje.setPrefHeight(ALTO_CAJA);

        List<ViajeDto> viajes = ViajeControladorListar.obtenerViajes();
        ViajeDto opcionViaje = new ViajeDto();
        opcionViaje.setIdViaje(0);
        cmbViaje.getItems().add(opcionViaje);
        cmbViaje.getItems().addAll(viajes);
        cmbViaje.getSelectionModel().select(0);

        cmbViaje.setConverter(new StringConverter<ViajeDto>() {
            @Override
            public String toString(ViajeDto viaje) {
                if (viaje == null || viaje.getIdViaje() == 0) {
                    return "Seleccione viaje";
                }
                return "Viaje #" + viaje.getIdViaje() + " - "
                        + viaje.getRutaViaje().getNombreRuta();
            }

            @Override
            public ViajeDto fromString(String string) {
                return null;
            }
        });
        miGrilla.add(cmbViaje, 1, 2);

        // ===== PASAJERO =====
        Label lblPasajero = new Label("Pasajero:");
        lblPasajero.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblPasajero, 0, 3);

        cmbPasajero = new ComboBox<>();
        cmbPasajero.setMaxWidth(Double.MAX_VALUE);
        cmbPasajero.setPrefHeight(ALTO_CAJA);

        List<PasajeroDto> pasajeros = PasajeroControladorListar.obtenerPasajeros();
        PasajeroDto opcionPasajero = new PasajeroDto();
        opcionPasajero.setIdPasajero(0);
        cmbPasajero.getItems().add(opcionPasajero);
        cmbPasajero.getItems().addAll(pasajeros);
        cmbPasajero.getSelectionModel().select(0);

        cmbPasajero.setConverter(new StringConverter<PasajeroDto>() {
            @Override
            public String toString(PasajeroDto pasajero) {
                if (pasajero == null || pasajero.getIdPasajero() == 0) {
                    return "Seleccione pasajero";
                }
                return pasajero.getNombrePasajero() + " - " + pasajero.getDocumentoPasajero();
            }

            @Override
            public PasajeroDto fromString(String string) {
                return null;
            }
        });
        miGrilla.add(cmbPasajero, 1, 3);

        // ===== NÚMERO DE ASIENTO =====
        Label lblAsiento = new Label("Número de Asiento:");
        lblAsiento.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblAsiento, 0, 4);

        spinnerAsiento = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactoryAsiento
                = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 60, 1);
        spinnerAsiento.setValueFactory(valueFactoryAsiento);
        spinnerAsiento.setPrefHeight(ALTO_CAJA);
        spinnerAsiento.setMaxWidth(Double.MAX_VALUE);
        spinnerAsiento.setEditable(true);
        miGrilla.add(spinnerAsiento, 1, 4);

        // ===== PRECIO =====
        Label lblPrecio = new Label("Precio:");
        lblPrecio.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblPrecio, 0, 5);

        spinnerPrecio = new Spinner<>();
        SpinnerValueFactory<Double> valueFactoryPrecio
                = new SpinnerValueFactory.DoubleSpinnerValueFactory(10000.0, 500000.0, 50000.0, 5000.0);
        spinnerPrecio.setValueFactory(valueFactoryPrecio);
        spinnerPrecio.setPrefHeight(ALTO_CAJA);
        spinnerPrecio.setMaxWidth(Double.MAX_VALUE);
        spinnerPrecio.setEditable(true);
        miGrilla.add(spinnerPrecio, 1, 5);

        // ===== FECHA COMPRA =====
        Label lblFecha = new Label("Fecha de Compra:");
        lblFecha.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblFecha, 0, 6);

        dateFechaCompra = new DatePicker();
        dateFechaCompra.setMaxWidth(Double.MAX_VALUE);
        dateFechaCompra.setPrefHeight(ALTO_CAJA);
        dateFechaCompra.setValue(LocalDate.now());
        miGrilla.add(dateFechaCompra, 1, 6);

        // ===== MÉTODO DE PAGO =====
        Label lblMetodoPago = new Label("Método de Pago:");
        lblMetodoPago.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblMetodoPago, 0, 7);

        cmbMetodoPago = new ComboBox<>();
        cmbMetodoPago.setMaxWidth(Double.MAX_VALUE);
        cmbMetodoPago.setPrefHeight(ALTO_CAJA);
        cmbMetodoPago.getItems().addAll("Seleccione método", "Efectivo", "Tarjeta", "Transferencia");
        cmbMetodoPago.getSelectionModel().select(0);
        miGrilla.add(cmbMetodoPago, 1, 7);

        // ===== EQUIPAJE EXTRA =====
        Label lblEquipaje = new Label("Equipaje:");
        lblEquipaje.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEquipaje, 0, 8);

        chkEquipajeExtra = new CheckBox("Equipaje Extra (+10,000)");
        chkEquipajeExtra.setFont(Font.font("Times new roman", 14));
        miGrilla.add(chkEquipajeExtra, 1, 8);

        // ===== CATEGORÍA TIQUETE (nuevo atributo) =====
        Label lblCategoria = new Label("Categoría:");
        lblCategoria.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblCategoria, 0, 9);

        cmbCategoria = new ComboBox<>();
        cmbCategoria.setMaxWidth(Double.MAX_VALUE);
        cmbCategoria.setPrefHeight(ALTO_CAJA);
        cmbCategoria.getItems().addAll("Seleccione categoría", "Económico", "Ejecutivo", "VIP");
        cmbCategoria.getSelectionModel().select(0);
        miGrilla.add(cmbCategoria, 1, 9);

        // ===== IMAGEN (archivo real) =====
        Label lblImagen = new Label("Imagen de tiquete:");
        lblImagen.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblImagen, 0, 10);

        cajaImagen = new TextField();
        cajaImagen.setDisable(true);
        cajaImagen.setPrefHeight(ALTO_CAJA);

        String[] extensiones = {"*.png", "*.jpg", "*.jpeg"};
        FileChooser objSeleccionar = Formulario.selectorImagen(
                "Seleccionar imagen", "Imágenes", extensiones);

        btnSeleccionarImagen = new Button("+");
        btnSeleccionarImagen.setPrefHeight(ALTO_CAJA);

        btnSeleccionarImagen.setOnAction((e) -> {
            rutaImagenSeleccionada = GestorImagen.obtenerRutaImagen(cajaImagen, objSeleccionar);
            if (rutaImagenSeleccionada.isEmpty()) {
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                miGrilla.add(imgPorDefecto, 2, 5);
            } else {
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                imgPrevisualizar = Icono.previsualizar(rutaImagenSeleccionada, 150);
                GridPane.setHalignment(imgPrevisualizar, HPos.CENTER);
                miGrilla.add(imgPrevisualizar, 2, 5);
            }
        });

        HBox.setHgrow(cajaImagen, Priority.ALWAYS);
        HBox panelHorizontal = new HBox(2);
        panelHorizontal.setAlignment(Pos.BOTTOM_RIGHT);
        panelHorizontal.getChildren().addAll(cajaImagen, btnSeleccionarImagen);
        miGrilla.add(panelHorizontal, 1, 10);

        imgPorDefecto = Icono.obtenerIcono("imgNoDisponible.png", 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        GridPane.setValignment(imgPorDefecto, VPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 5);

        // ===== BOTÓN GRABAR =====
        btnGrabar = new Button("GRABAR TIQUETE");
        btnGrabar.setPrefHeight(ALTO_CAJA);
        btnGrabar.setMaxWidth(Double.MAX_VALUE);
        btnGrabar.setTextFill(Color.web("#FFFFFF"));
        btnGrabar.setFont(Font.font("Rockwell", FontWeight.BOLD, 14));
        btnGrabar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + ";"
                + "-fx-border-radius: 8; -fx-background-radius: 8;");
        btnGrabar.setCursor(Cursor.HAND);
        btnGrabar.setOnAction(e -> guardarTiquete());
        miGrilla.add(btnGrabar, 1, 11);
    }

    // =================  VALIDACIONES  =================
    private Boolean formularioCompleto() {
        Window ventana = this.getScene() != null ? this.getScene().getWindow() : null;

        if (cmbViaje.getSelectionModel().getSelectedIndex() == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, ventana,
                    "Viaje no seleccionado", "Debe seleccionar un viaje");
            cmbViaje.requestFocus();
            return false;
        }

        if (cmbPasajero.getSelectionModel().getSelectedIndex() == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, ventana,
                    "Pasajero no seleccionado", "Debe seleccionar un pasajero");
            cmbPasajero.requestFocus();
            return false;
        }

        if (dateFechaCompra.getValue() == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, ventana,
                    "Fecha no seleccionada", "Debe seleccionar la fecha de compra");
            dateFechaCompra.requestFocus();
            return false;
        }

        if (cmbMetodoPago.getSelectionModel().getSelectedIndex() == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, ventana,
                    "Método de pago no seleccionado", "Debe seleccionar un método de pago");
            cmbMetodoPago.requestFocus();
            return false;
        }

        if (cmbCategoria.getSelectionModel().getSelectedIndex() == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, ventana,
                    "Categoría no seleccionada", "Debe seleccionar una categoría");
            cmbCategoria.requestFocus();
            return false;
        }

        if (rutaImagenSeleccionada.isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, ventana,
                    "Imagen no seleccionada", "Debe seleccionar una imagen");
            return false;
        }
        if (cmbCategoria.getSelectionModel().getSelectedIndex() == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Categoría no seleccionada", "Debe seleccionar una categoría de tiquete");
            cmbCategoria.requestFocus();
            return false;
        }

        return true;
    }

    private void guardarTiquete() {
        if (formularioCompleto()) {

            TiqueteDto dto = new TiqueteDto();
            dto.setViajeTiquete(cmbViaje.getValue());
            dto.setPasajeroTiquete(cmbPasajero.getValue());
            dto.setNumeroAsientoTiquete(spinnerAsiento.getValue());
            dto.setPrecioTiquete(spinnerPrecio.getValue());
            dto.setFechaCompraTiquete(LocalDateTime.of(dateFechaCompra.getValue(), LocalTime.now()));
            dto.setMetodoPagoTiquete(cmbMetodoPago.getValue());
            dto.setEquipajeExtraTiquete(chkEquipajeExtra.isSelected());
            dto.setCategoriaTiquete(cmbCategoria.getValue());

            // nombre público = nombre del archivo
            dto.setNombreImagenPublicoTiquete(
                    Paths.get(rutaImagenSeleccionada).getFileName().toString()
            );

            if (TiqueteControladorGrabar.crearTiquete(dto, rutaImagenSeleccionada)) {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, this.getScene().getWindow(),
                        "Éxito", "Tiquete creado correctamente");
                limpiarFormulario();
            } else {
                Mensaje.mostrar(Alert.AlertType.ERROR, this.getScene().getWindow(),
                        "Error", "No se pudo crear el tiquete");
            }
        }
    }

    private void limpiarFormulario() {

        cmbViaje.getSelectionModel().select(0);
        cmbPasajero.getSelectionModel().select(0);
        spinnerAsiento.getValueFactory().setValue(1);
        spinnerPrecio.getValueFactory().setValue(50000.0);
        dateFechaCompra.setValue(LocalDate.now());
        cmbMetodoPago.getSelectionModel().select(0);
        cmbCategoria.getSelectionModel().select(0);
        chkEquipajeExtra.setSelected(false);

        cajaImagen.clear();
        rutaImagenSeleccionada = "";

        miGrilla.getChildren().remove(imgPrevisualizar);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        GridPane.setValignment(imgPorDefecto, VPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 5);

        cmbViaje.requestFocus();
    }

    private void colocarFrmElegante() {

        Runnable calcular = () -> {
            double alturaMarco = miMarco.getHeight();
            if (alturaMarco > 0) {
                double desplazamiento = alturaMarco * AJUSTE_TITULO;
                miGrilla.setTranslateY(-alturaMarco / 8 + desplazamiento);
            }
        };

        calcular.run();
        miMarco.heightProperty().addListener((obs, antes, despues) -> calcular.run());
    }
}
