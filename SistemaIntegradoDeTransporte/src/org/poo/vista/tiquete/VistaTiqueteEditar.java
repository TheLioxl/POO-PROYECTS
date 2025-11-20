package org.poo.vista.tiquete;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.poo.controlador.tiquete.TiqueteControladorEditar;
import org.poo.controlador.tiquete.TiqueteControladorVentana;
import org.poo.controlador.viaje.ViajeControladorListar;
import org.poo.controlador.pasajero.PasajeroControladorListar;
import org.poo.dto.TiqueteDto;
import org.poo.dto.ViajeDto;
import org.poo.dto.PasajeroDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Cursor;

public class VistaTiqueteEditar extends StackPane {

    private static final int H_GAP = 10;
    private static final int V_GAP = 20;
    private static final int ALTO_FILA = 31;
    private static final int ALTO_CAJA = 32;
    private static final int TAMANIO_FUENTE = 18;
    private static final double AJUSTE_TITULO = 0.1;

    private final GridPane miGrilla;
    private final StackPane miFormulario;
    private final Rectangle miMarco;
    private final Stage miEscenario;

    private ComboBox<ViajeDto> cmbViaje;
    private ComboBox<PasajeroDto> cmbPasajero;
    private Spinner<Integer> spinnerAsiento;
    private Spinner<Double> spinnerPrecio;
    private DatePicker dateFechaCompra;
    private ComboBox<String> cmbMetodoPago;
    private CheckBox chkEquipajeExtra;
    private TextField txtReferencia;
    private TextArea txtObservaciones;
    private TextField txtImagen;
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private String rutaImagenSeleccionada;

    private final int posicion;
    private final TiqueteDto objTiquete;
    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;
    private final boolean desdeCarrusel;

    public VistaTiqueteEditar(Stage ventanaPadre, BorderPane princ, Pane pane,
            double ancho, double alto, TiqueteDto objTiqueteExterno, int posicionArchivo,
            boolean vieneDeCarrusel) {

        miEscenario = ventanaPadre;
        miFormulario = this;
        miFormulario.setAlignment(Pos.CENTER);

        posicion = posicionArchivo;
        objTiquete = objTiqueteExterno;
        panelPrincipal = princ;
        panelCuerpo = pane;
        rutaImagenSeleccionada = "";
        desdeCarrusel = vieneDeCarrusel;

        miGrilla = new GridPane();
        miMarco = Marco.crear(
                miEscenario,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_RUTA,
                Configuracion.DEGRADE_BORDE
        );

        getChildren().add(miMarco);

        configurarGrilla(ancho, alto);
        crearTitulo();
        crearFormulario();
        getChildren().add(miGrilla);
    }

    public StackPane getMiFormulario() {
        return miFormulario;
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
        Text miTitulo = new Text("FORMULARIO - EDITAR TIQUETE");
        miTitulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        miTitulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 28));
        GridPane.setHalignment(miTitulo, HPos.CENTER);
        GridPane.setMargin(miTitulo, new Insets(30, 0, 0, 0));
        miGrilla.add(miTitulo, 0, 0, 3, 2);//nombre, columna, fila, union de filas 
    }

    private void crearFormulario() {

        Label lblViaje = new Label("Viaje:");
        lblViaje.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblViaje, 0, 2);

        cmbViaje = new ComboBox<>();
        cmbViaje.setMaxWidth(Double.MAX_VALUE);
        cmbViaje.setPrefHeight(ALTO_CAJA);
        
        List<ViajeDto> viajes = ViajeControladorListar.obtenerViajes();
        cmbViaje.getItems().addAll(viajes);
        
        // Seleccionar el viaje actual
        for (int i = 0; i < viajes.size(); i++) {
            if (viajes.get(i).getIdViaje().equals(objTiquete.getViajeTiquete().getIdViaje())) {
                cmbViaje.getSelectionModel().select(i);
                break;
            }
        }
        
        cmbViaje.setConverter(new StringConverter<ViajeDto>() {
            @Override
            public String toString(ViajeDto viaje) {
                if (viaje == null) return "";
                return "Viaje #" + viaje.getIdViaje() + " - " + 
                       viaje.getRutaViaje().getNombreRuta();
            }

            @Override
            public ViajeDto fromString(String string) {
                return null;
            }
        });
        miGrilla.add(cmbViaje, 1, 2);

        // PASAJERO
        Label lblPasajero = new Label("Pasajero:");
        lblPasajero.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblPasajero, 0, 3);

        cmbPasajero = new ComboBox<>();
        cmbPasajero.setMaxWidth(Double.MAX_VALUE);
        cmbPasajero.setPrefHeight(ALTO_CAJA);
        
        List<PasajeroDto> pasajeros = PasajeroControladorListar.obtenerPasajeros();
        cmbPasajero.getItems().addAll(pasajeros);
        
        // Seleccionar el pasajero actual
        for (int i = 0; i < pasajeros.size(); i++) {
            if (pasajeros.get(i).getIdPasajero().equals(objTiquete.getPasajeroTiquete().getIdPasajero())) {
                cmbPasajero.getSelectionModel().select(i);
                break;
            }
        }
        
        cmbPasajero.setConverter(new StringConverter<PasajeroDto>() {
            @Override
            public String toString(PasajeroDto pasajero) {
                if (pasajero == null) return "";
                return pasajero.getNombrePasajero() + " - " + pasajero.getDocumentoPasajero();
            }

            @Override
            public PasajeroDto fromString(String string) {
                return null;
            }
        });
        miGrilla.add(cmbPasajero, 1, 3);

        // NÚMERO DE ASIENTO
        Label lblAsiento = new Label("Número de Asiento:");
        lblAsiento.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblAsiento, 0, 4);

        spinnerAsiento = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactoryAsiento = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 60, 
                objTiquete.getNumeroAsientoTiquete() != null ? objTiquete.getNumeroAsientoTiquete() : 1);
        spinnerAsiento.setValueFactory(valueFactoryAsiento);
        spinnerAsiento.setPrefHeight(ALTO_CAJA);
        spinnerAsiento.setMaxWidth(Double.MAX_VALUE);
        spinnerAsiento.setEditable(true);
        miGrilla.add(spinnerAsiento, 1, 4);

        // PRECIO
        Label lblPrecio = new Label("Precio:");
        lblPrecio.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblPrecio, 0, 5);

        spinnerPrecio = new Spinner<>();
        SpinnerValueFactory<Double> valueFactoryPrecio = 
            new SpinnerValueFactory.DoubleSpinnerValueFactory(10000.0, 500000.0, 
                objTiquete.getPrecioTiquete() != null ? objTiquete.getPrecioTiquete() : 50000.0, 5000.0);
        spinnerPrecio.setValueFactory(valueFactoryPrecio);
        spinnerPrecio.setPrefHeight(ALTO_CAJA);
        spinnerPrecio.setMaxWidth(Double.MAX_VALUE);
        spinnerPrecio.setEditable(true);
        miGrilla.add(spinnerPrecio, 1, 5);

        // FECHA DE COMPRA
        Label lblFecha = new Label("Fecha de compra:");
        lblFecha.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblFecha, 0, 6);

        dateFechaCompra = new DatePicker();
        dateFechaCompra.setMaxWidth(Double.MAX_VALUE);
        dateFechaCompra.setPrefHeight(ALTO_CAJA);
        dateFechaCompra.setValue(objTiquete.getFechaCompraTiquete().toLocalDate());
        miGrilla.add(dateFechaCompra, 1, 6);

        // MÉTODO DE PAGO
        Label lblMetodoPago = new Label("Método de Pago:");
        lblMetodoPago.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblMetodoPago, 0, 7);

        cmbMetodoPago = new ComboBox<>();
        cmbMetodoPago.setMaxWidth(Double.MAX_VALUE);
        cmbMetodoPago.setPrefHeight(ALTO_CAJA);
        cmbMetodoPago.getItems().addAll("Efectivo", "Tarjeta", "Transferencia");
        
        String metodoPago = objTiquete.getMetodoPagoTiquete();
        if (metodoPago != null && !metodoPago.isEmpty()) {
            cmbMetodoPago.setValue(metodoPago);
        } else {
            cmbMetodoPago.getSelectionModel().select(0);
        }
        miGrilla.add(cmbMetodoPago, 1, 7);

        // EQUIPAJE EXTRA
        Label lblEquipaje = new Label("Equipaje:");
        lblEquipaje.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEquipaje, 0, 8);

        chkEquipajeExtra = new CheckBox("Equipaje Extra (+10,000)");
        chkEquipajeExtra.setSelected(objTiquete.getEquipajeExtraTiquete() != null ? 
                                      objTiquete.getEquipajeExtraTiquete() : false);
        chkEquipajeExtra.setFont(Font.font("Arial", 14));
        miGrilla.add(chkEquipajeExtra, 1, 8);

        // IMAGEN
        Label lblImagen = new Label("Imagen:");
        lblImagen.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblImagen, 0, 9);

        txtImagen = new TextField();
        txtImagen.setText(objTiquete.getNombreImagenPublicoTiquete());
        txtImagen.setDisable(true);
        txtImagen.setPrefHeight(ALTO_CAJA);

        String[] extensiones = {"*.png", "*.jpg", "*.jpeg"};
        FileChooser selector = Formulario.selectorImagen(
                "Seleccionar imagen", "Imágenes", extensiones);

        Button btnSeleccionarImagen = new Button("+");
        btnSeleccionarImagen.setPrefHeight(ALTO_CAJA);

        btnSeleccionarImagen.setOnAction(e -> {
            rutaImagenSeleccionada = GestorImagen.obtenerRutaImagen(txtImagen, selector);
            
            if (rutaImagenSeleccionada.isEmpty()) {
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                imgPorDefecto = Icono.obtenerFotosExternas(objTiquete.getNombreImagenPrivadoTiquete(), 150);
                GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
                miGrilla.add(imgPorDefecto, 2, 5);
            } else {
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                imgPrevisualizar = Icono.previsualizar(rutaImagenSeleccionada, 150);
                GridPane.setHalignment(imgPrevisualizar, HPos.CENTER);
                miGrilla.add(imgPrevisualizar, 2, 5);
            }
        });

        HBox.setHgrow(txtImagen, Priority.ALWAYS);
        HBox panelImagen = new HBox(2, txtImagen, btnSeleccionarImagen);
        panelImagen.setAlignment(Pos.BOTTOM_RIGHT);
        miGrilla.add(panelImagen, 1, 9);

        // Imagen en columna derecha
        imgPorDefecto = Icono.obtenerFotosExternas(
                objTiquete.getNombreImagenPrivadoTiquete(), 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        GridPane.setValignment(imgPorDefecto, javafx.geometry.VPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 5);

        Button btnActualizar = new Button("ACTUALIZAR");
        btnActualizar.setPrefHeight(ALTO_CAJA);
        btnActualizar.setMaxWidth(Double.MAX_VALUE);
        btnActualizar.setTextFill(Color.web("#FFFFFF"));
        btnActualizar.setFont(Font.font("Rockwell", FontWeight.BOLD, 14));
        btnActualizar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + ";");
        btnActualizar.setCursor(Cursor.HAND);
        btnActualizar.setOnAction(e -> actualizarTiquete());
        miGrilla.add(btnActualizar, 1, 10);

        // BOTÓN REGRESAR
        Button btnRegresar = new Button("VOLVER");
        btnRegresar.setPrefHeight(ALTO_CAJA);
        btnRegresar.setMaxWidth(Double.MAX_VALUE);
        btnRegresar.setFont(Font.font("Rockwell", FontWeight.BOLD, 14));
        btnRegresar.setCursor(Cursor.HAND);
        btnRegresar.setOnAction(e -> {
            if (desdeCarrusel) {
                panelCuerpo = TiqueteControladorVentana.carrusel(
                        miEscenario, panelPrincipal, panelCuerpo,
                        Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO, posicion);
            } else {
                panelCuerpo = TiqueteControladorVentana.administrar(
                        miEscenario, panelPrincipal, panelCuerpo,
                        Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO);
            }
            panelPrincipal.setCenter(null);
            panelPrincipal.setCenter(panelCuerpo);
        });
        miGrilla.add(btnRegresar, 1, 11);
    }

    private Boolean formularioCompleto() {
        if (cmbViaje.getValue() == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Viaje no seleccionado", "Debe seleccionar un viaje");
            cmbViaje.requestFocus();
            return false;
        }

        if (cmbPasajero.getValue() == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Pasajero no seleccionado", "Debe seleccionar un pasajero");
            cmbPasajero.requestFocus();
            return false;
        }

        if (dateFechaCompra.getValue() == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Fecha no seleccionada", "Debe seleccionar la fecha de compra");
            dateFechaCompra.requestFocus();
            return false;
        }

        if (cmbMetodoPago.getValue() == null || cmbMetodoPago.getValue().isEmpty()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Método de pago no seleccionado", "Debe seleccionar un método de pago");
            cmbMetodoPago.requestFocus();
            return false;
        }

        return true;
    }

    private void actualizarTiquete() {
        if (formularioCompleto()) {
            TiqueteDto dtoActualizado = new TiqueteDto();
            dtoActualizado.setIdTiquete(objTiquete.getIdTiquete());
            dtoActualizado.setViajeTiquete(cmbViaje.getValue());
            dtoActualizado.setPasajeroTiquete(cmbPasajero.getValue());
            dtoActualizado.setNumeroAsientoTiquete(spinnerAsiento.getValue());
            dtoActualizado.setPrecioTiquete(spinnerPrecio.getValue());
            dtoActualizado.setFechaCompraTiquete(LocalDateTime.of(dateFechaCompra.getValue(), LocalTime.now()));
            dtoActualizado.setMetodoPagoTiquete(cmbMetodoPago.getValue());
            dtoActualizado.setEquipajeExtraTiquete(chkEquipajeExtra.isSelected());
            dtoActualizado.setNombreImagenPublicoTiquete(txtImagen.getText());
            dtoActualizado.setNombreImagenPrivadoTiquete(objTiquete.getNombreImagenPrivadoTiquete());

            if (TiqueteControladorEditar.actualizar(posicion, dtoActualizado, rutaImagenSeleccionada)) {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, this.getScene().getWindow(),
                        "Éxito", "Tiquete actualizado correctamente");
            } else {
                Mensaje.mostrar(Alert.AlertType.ERROR, this.getScene().getWindow(),
                        "Error", "No se pudo actualizar el tiquete");
            }
        }
    }
}
