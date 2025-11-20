package org.poo.vista.destino;

import java.time.LocalDate;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
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
import org.poo.controlador.destino.DestinoControladorGrabar;
import org.poo.dto.DestinoDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.*;

public class VistaDestinoCrear extends StackPane {

    private static final int H_GAP = 10;
    private static final int V_GAP = 15;
    private static final int ALTO_CAJA = 35;
    private static final int ALTO_FILA = 40;
    private static final int TAMANIO_FUENTE = 18;
    private static final double AJUSTE_TITULO = 0.1;

    private final GridPane miGrilla;
    private final Rectangle miMarco;
    private final Stage miEscenario;

    private TextField txtNombreDestino;
    private TextField txtDepartamentoDestino;
    private TextArea txtDescripcionDestino;
    private ComboBox<String> cmbEstadoDestino;
    private TextField cajaImagen;
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private String rutaImagenSeleccionada;
    private Spinner<Integer> spinnerAltitud;
    private Slider sliderTemperatura;
    private Label lblValorTemp;
    private RadioButton radioPlayero;
    private RadioButton radioMontañoso;
    private DatePicker datePickerTemporada;
    private ToggleGroup grupoTipo;


    public VistaDestinoCrear(Stage escenario, double ancho, double alto) {
        miEscenario = escenario;
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

        for (int i = 0; i < 11; i++) {
            RowConstraints fila = new RowConstraints();
            fila.setMinHeight(ALTO_FILA);
            fila.setMaxHeight(ALTO_FILA);
            fila.setVgrow(Priority.ALWAYS);
            miGrilla.getRowConstraints().add(fila);
        }
    }

    private void crearTitulo() {
        Text miTitulo = new Text("FORMULARIO - CREAR DESTINO");
        miTitulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        miTitulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 28));
        GridPane.setHalignment(miTitulo, HPos.CENTER);
        GridPane.setMargin(miTitulo, new Insets(30, 0, 0, 0));
        miGrilla.add(miTitulo, 0, 0, 3, 2);
    }

    private void crearFormulario() {

        Label lblNombre = new Label("Nombre:");
        lblNombre.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblNombre, 0, 2);

        txtNombreDestino = new TextField();
        txtNombreDestino.setPromptText("Ej: Cartagena");
        txtNombreDestino.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtNombreDestino, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtNombreDestino, 50);
        miGrilla.add(txtNombreDestino, 1, 2);

        // DEPARTAMENTO
        Label lblDepartamento = new Label("Departamento:");
        lblDepartamento.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDepartamento, 0, 3);

        txtDepartamentoDestino = new TextField();
        txtDepartamentoDestino.setPromptText("Ej: Bolívar");
        txtDepartamentoDestino.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtDepartamentoDestino, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtDepartamentoDestino, 50);
        miGrilla.add(txtDepartamentoDestino, 1, 3);
        
        // ALTITUD (Spinner)
        Label lblAltitud = new Label("Altitud (msnm):");
        lblAltitud.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblAltitud, 0, 4);

        spinnerAltitud = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactoryAltitud = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5000, 100);
        spinnerAltitud.setValueFactory(valueFactoryAltitud);
        spinnerAltitud.setPrefHeight(ALTO_CAJA);
        spinnerAltitud.setMaxWidth(Double.MAX_VALUE);
        spinnerAltitud.setEditable(true);
        miGrilla.add(spinnerAltitud, 1, 4);

        // TEMPERATURA (Slider)
        Label lblTemperatura = new Label("Temperatura promedio °C:");
        lblTemperatura.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblTemperatura, 0, 5);

        VBox vboxTemp = new VBox(5);
        sliderTemperatura = new Slider(0, 40, 25);
        sliderTemperatura.setShowTickLabels(true);
        sliderTemperatura.setShowTickMarks(true);
        sliderTemperatura.setMajorTickUnit(10);
        sliderTemperatura.setBlockIncrement(1);
        
        lblValorTemp = new Label("25.0°C");
        lblValorTemp.setFont(Font.font("Times new roman", FontWeight.BOLD, 14));
        
        sliderTemperatura.valueProperty().addListener((obs, oldVal, newVal) -> {
            lblValorTemp.setText(String.format("%.1f°C", newVal.doubleValue()));
        });
        
        vboxTemp.getChildren().addAll(sliderTemperatura, lblValorTemp);
        miGrilla.add(vboxTemp, 1, 5);

        // TIPO DE DESTINO (RadioButton)
        Label lblTipo = new Label("Tipo de destino:");
        lblTipo.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblTipo, 0, 6);

        grupoTipo = new ToggleGroup();
        radioPlayero = new RadioButton("Playero");
        radioMontañoso = new RadioButton("Montañoso");

        radioPlayero.setToggleGroup(grupoTipo);
        radioMontañoso.setToggleGroup(grupoTipo);

// Ninguno seleccionado
        grupoTipo.selectToggle(null);

        
        radioPlayero.setFont(Font.font("Times new roman", 14));
        radioMontañoso.setFont(Font.font("Times new roman", 14));
        
        VBox vboxRadio = new VBox(8); // separación vertical opcional
        vboxRadio.getChildren().addAll(radioPlayero, radioMontañoso);

        miGrilla.add(vboxRadio, 1, 6);


        // TEMPORADA ALTA (DatePicker)
        Label lblTemporada = new Label("Inicio Temporada Alta:");
        lblTemporada.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblTemporada, 0, 7);

        datePickerTemporada = new DatePicker();
        datePickerTemporada.setValue(LocalDate.now());
        datePickerTemporada.setPrefHeight(ALTO_CAJA);
        datePickerTemporada.setMaxWidth(Double.MAX_VALUE);
        miGrilla.add(datePickerTemporada, 1, 7);

        // ESTADO
        Label lblEstado = new Label("Estado:");
        lblEstado.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEstado, 0, 8);

        cmbEstadoDestino = new ComboBox<>();
        cmbEstadoDestino.setMaxWidth(Double.MAX_VALUE);
        cmbEstadoDestino.setPrefHeight(ALTO_CAJA);
        cmbEstadoDestino.getItems().addAll("Seleccione estado", "Activo", "Inactivo");
        cmbEstadoDestino.getSelectionModel().select(0);
        miGrilla.add(cmbEstadoDestino, 1, 8);

        // IMAGEN
        Label lblImagen = new Label("Imagen:");
        lblImagen.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblImagen, 0, 9);

        cajaImagen = new TextField();
        cajaImagen.setDisable(true);
        cajaImagen.setPrefHeight(ALTO_CAJA);

        String[] extensiones = {"*.png", "*.jpg", "*.jpeg"};
        FileChooser selector = Formulario.selectorImagen(
                "Seleccionar imagen", "Imágenes", extensiones);

        Button btnSeleccionarImagen = new Button("+");
        btnSeleccionarImagen.setPrefHeight(ALTO_CAJA);
        btnSeleccionarImagen.setCursor(Cursor.HAND);

        btnSeleccionarImagen.setOnAction(e -> {
            rutaImagenSeleccionada = GestorImagen.obtenerRutaImagen(cajaImagen, selector);

            if (rutaImagenSeleccionada.isEmpty()) {
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                miGrilla.add(imgPorDefecto, 2, 6);
            } else {
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                imgPrevisualizar = Icono.previsualizar(rutaImagenSeleccionada, 150);
                GridPane.setHalignment(imgPrevisualizar, HPos.CENTER);
                miGrilla.add(imgPrevisualizar, 2, 6);
            }
        });

        HBox.setHgrow(cajaImagen, Priority.ALWAYS);
        HBox panelHorizontal = new HBox(2);
        panelHorizontal.setAlignment(Pos.BOTTOM_RIGHT);
        panelHorizontal.getChildren().addAll(cajaImagen, btnSeleccionarImagen);
        miGrilla.add(panelHorizontal, 1, 9);
        
        imgPorDefecto = Icono.obtenerIcono("imgNoDisponible.png", 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        GridPane.setValignment(imgPorDefecto, javafx.geometry.VPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 6);

        // BOTÓN GRABAR
        Button btnGrabar = new Button("GRABAR DESTINO");
        btnGrabar.setPrefHeight(ALTO_CAJA);
        btnGrabar.setMaxWidth(Double.MAX_VALUE);
        btnGrabar.setTextFill(Color.web("#FFFFFF"));
        btnGrabar.setFont(Font.font("Rockwell", FontWeight.BOLD, 14));
        btnGrabar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + ";" 
                + "-fx-border-radius: 8; -fx-background-radius: 8;");
        btnGrabar.setCursor(Cursor.HAND);
        btnGrabar.setOnAction(e -> guardarDestino());
        miGrilla.add(btnGrabar, 1, 10);
    }

    private Boolean formularioCompleto() {
        if (txtNombreDestino.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar el nombre del destino");
            txtNombreDestino.requestFocus();
            return false;
        }

        if (txtDepartamentoDestino.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar el departamento");
            txtDepartamentoDestino.requestFocus();
            return false;
        }

        if (sliderTemperatura.getValue() == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar una temperatura");
            sliderTemperatura.requestFocus();
            return false;
        }
        
        if (radioPlayero.getToggleGroup().getSelectedToggle() == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Tipo de destino no seleccionado", "Debe elegir si el destino es playero o montañoso");
            return false;
        }

        if (cmbEstadoDestino.getSelectionModel().getSelectedIndex() == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Estado no seleccionado", "Debe seleccionar un estado");
            return false;
        }

        if (rutaImagenSeleccionada.isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Imagen no seleccionada", "Debe seleccionar una imagen");
            return false;
        }

        return true;
    }

    private void guardarDestino() {
        if (formularioCompleto()) {
            DestinoDto dto = new DestinoDto();
            dto.setNombreDestino(txtNombreDestino.getText());
            dto.setDepartamentoDestino(txtDepartamentoDestino.getText());
            dto.setDescripcionDestino(txtDescripcionDestino.getText());
            dto.setAltitudMetros(spinnerAltitud.getValue());
            dto.setTemperaturaPromedio(sliderTemperatura.getValue());
            dto.setEsPlayero(radioPlayero.isSelected());
            dto.setTemporadaAlta(datePickerTemporada.getValue().toString());
            dto.setEstadoDestino(cmbEstadoDestino.getValue().equals("Activo"));
            dto.setNombreImagenPublicoDestino(cajaImagen.getText());

            if (DestinoControladorGrabar.crearDestino(dto, rutaImagenSeleccionada)) {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, this.getScene().getWindow(),
                        "Éxito", "Destino creado correctamente");
                limpiarFormulario();
            } else {
                Mensaje.mostrar(Alert.AlertType.ERROR, this.getScene().getWindow(),
                        "Error", "No se pudo crear el destino");
            }
        }
    }

    private void limpiarFormulario() {
        txtNombreDestino.clear();
        txtDepartamentoDestino.clear();
        txtDescripcionDestino.clear();
        spinnerAltitud.getValueFactory().setValue(100);
        sliderTemperatura.setValue(25);
        grupoTipo.selectToggle(null);
        datePickerTemporada.setValue(LocalDate.now());
        cmbEstadoDestino.getSelectionModel().select(0);
        cajaImagen.clear();
        rutaImagenSeleccionada = "";

        miGrilla.getChildren().remove(imgPrevisualizar);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 6);
        txtNombreDestino.requestFocus();
    }

    private void colocarFrmElegante() {
        Runnable calcular = () -> {
            double alturaMarco = miMarco.getHeight();
            if (alturaMarco > 0) {
                double desplazamiento = alturaMarco * AJUSTE_TITULO;
                miGrilla.setTranslateY(-alturaMarco / 9 + desplazamiento);
            }
        };
        calcular.run();
        miMarco.heightProperty().addListener((obs, antes, despues) -> calcular.run());
    }
}
