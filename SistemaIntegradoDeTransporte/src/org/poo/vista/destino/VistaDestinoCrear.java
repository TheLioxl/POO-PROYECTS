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
    private static final int ALTO_FILA = 50;
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

    public VistaDestinoCrear(Stage escenario, double ancho, double alto) {
        miEscenario = escenario;
        rutaImagenSeleccionada = "";
        
        setAlignment(Pos.CENTER);
        miGrilla = new GridPane();
        miMarco = Marco.crear(escenario,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_DESTINO,
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
        miGrilla.setPrefSize(miAnchoGrilla, alto);
        miGrilla.setMinSize(miAnchoGrilla, alto);
        miGrilla.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        ColumnConstraints col0 = new ColumnConstraints();
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        
        col0.setPrefWidth(200);
        col1.setPrefWidth(200);
        col2.setPrefWidth(200);
        
        col1.setHgrow(Priority.ALWAYS);
        miGrilla.getColumnConstraints().addAll(col0, col1, col2);

        for (int i = 0; i < 12; i++) {  // CAMBIAR de 8 a 12
            RowConstraints fila = new RowConstraints();
            fila.setMinHeight(ALTO_FILA);
            fila.setMaxHeight(ALTO_FILA);
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
        int fila = 2;
        int primeraColumna = 0;
        int segundaColumna = 1;

        // NOMBRE
        Label lblNombre = new Label("Nombre del Destino:");
        lblNombre.setFont(Font.font("Rockwell", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblNombre, primeraColumna, fila);

        txtNombreDestino = new TextField();
        txtNombreDestino.setPromptText("Ej: Cartagena");
        txtNombreDestino.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtNombreDestino, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtNombreDestino, 50);
        miGrilla.add(txtNombreDestino, segundaColumna, fila);

        // DEPARTAMENTO
        fila++;
        Label lblDepartamento = new Label("Departamento:");
        lblDepartamento.setFont(Font.font("Rockwell", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDepartamento, primeraColumna, fila);

        txtDepartamentoDestino = new TextField();
        txtDepartamentoDestino.setPromptText("Ej: Bolívar");
        txtDepartamentoDestino.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtDepartamentoDestino, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtDepartamentoDestino, 50);
        miGrilla.add(txtDepartamentoDestino, segundaColumna, fila);

        // DESCRIPCIÓN
        fila++;
        Label lblDescripcion = new Label("Descripción:");
        lblDescripcion.setFont(Font.font("Rockwell", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDescripcion, primeraColumna, fila);

        txtDescripcionDestino = new TextArea();
        txtDescripcionDestino.setPromptText("Descripción del destino turístico...");
        txtDescripcionDestino.setPrefRowCount(3);
        txtDescripcionDestino.setWrapText(true);
        txtDescripcionDestino.setMaxWidth(Double.MAX_VALUE);
        miGrilla.add(txtDescripcionDestino, segundaColumna, fila);

        // ALTITUD (Spinner)
        fila++;
        Label lblAltitud = new Label("Altitud (msnm):");
        lblAltitud.setFont(Font.font("Rockwell", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblAltitud, primeraColumna, fila);

        spinnerAltitud = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactoryAltitud = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5000, 100);
        spinnerAltitud.setValueFactory(valueFactoryAltitud);
        spinnerAltitud.setPrefHeight(ALTO_CAJA);
        spinnerAltitud.setMaxWidth(Double.MAX_VALUE);
        spinnerAltitud.setEditable(true);
        miGrilla.add(spinnerAltitud, segundaColumna, fila);

        // TEMPERATURA (Slider)
        fila++;
        Label lblTemperatura = new Label("Temperatura Promedio °C:");
        lblTemperatura.setFont(Font.font("Rockwell", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblTemperatura, primeraColumna, fila);

        VBox vboxTemp = new VBox(5);
        sliderTemperatura = new Slider(0, 40, 25);
        sliderTemperatura.setShowTickLabels(true);
        sliderTemperatura.setShowTickMarks(true);
        sliderTemperatura.setMajorTickUnit(10);
        sliderTemperatura.setBlockIncrement(1);
        
        lblValorTemp = new Label("25.0°C");
        lblValorTemp.setFont(Font.font("Rockwell", FontWeight.BOLD, 14));
        
        sliderTemperatura.valueProperty().addListener((obs, oldVal, newVal) -> {
            lblValorTemp.setText(String.format("%.1f°C", newVal.doubleValue()));
        });
        
        vboxTemp.getChildren().addAll(sliderTemperatura, lblValorTemp);
        miGrilla.add(vboxTemp, segundaColumna, fila);

        // TIPO DE DESTINO (RadioButton)
        fila++;
        Label lblTipo = new Label("Tipo de Destino:");
        lblTipo.setFont(Font.font("Rockwell", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblTipo, primeraColumna, fila);

        ToggleGroup grupoTipo = new ToggleGroup();
        radioPlayero = new RadioButton("Playero");
        radioMontañoso = new RadioButton("Montañoso/Cultural");
        
        radioPlayero.setToggleGroup(grupoTipo);
        radioMontañoso.setToggleGroup(grupoTipo);
        radioPlayero.setSelected(true);
        
        radioPlayero.setFont(Font.font("Rockwell", 14));
        radioMontañoso.setFont(Font.font("Rockwell", 14));
        
        HBox hboxRadio = new HBox(15);
        hboxRadio.getChildren().addAll(radioPlayero, radioMontañoso);
        miGrilla.add(hboxRadio, segundaColumna, fila);

        // TEMPORADA ALTA (DatePicker)
        fila++;
        Label lblTemporada = new Label("Inicio Temporada Alta:");
        lblTemporada.setFont(Font.font("Rockwell", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblTemporada, primeraColumna, fila);

        datePickerTemporada = new DatePicker();
        datePickerTemporada.setValue(LocalDate.now());
        datePickerTemporada.setPrefHeight(ALTO_CAJA);
        datePickerTemporada.setMaxWidth(Double.MAX_VALUE);
        miGrilla.add(datePickerTemporada, segundaColumna, fila);

        // ESTADO
        fila++;
        Label lblEstado = new Label("Estado:");
        lblEstado.setFont(Font.font("Rockwell", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEstado, primeraColumna, fila);

        cmbEstadoDestino = new ComboBox<>();
        cmbEstadoDestino.setMaxWidth(Double.MAX_VALUE);
        cmbEstadoDestino.setPrefHeight(ALTO_CAJA);
        cmbEstadoDestino.getItems().addAll("Seleccione estado", "Activo", "Inactivo");
        cmbEstadoDestino.getSelectionModel().select(0);
        miGrilla.add(cmbEstadoDestino, segundaColumna, fila);

        // IMAGEN
        fila++;
        Label lblImagen = new Label("Imagen:");
        lblImagen.setFont(Font.font("Rockwell", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblImagen, primeraColumna, fila);

        cajaImagen = new TextField();
        cajaImagen.setDisable(true);
        cajaImagen.setPrefHeight(ALTO_CAJA);

        String[] extensiones = {"*.png", "*.jpg", "*.jpeg"};
        FileChooser selector = Formulario.selectorImagen(
                "Seleccionar imagen", "Imágenes", extensiones);

        Button btnSeleccionarImagen = new Button("+");
        btnSeleccionarImagen.setPrefHeight(ALTO_CAJA);
        btnSeleccionarImagen.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        btnSeleccionarImagen.setCursor(Cursor.HAND);

        btnSeleccionarImagen.setOnAction(e -> {
            rutaImagenSeleccionada = GestorImagen.obtenerRutaImagen(cajaImagen, selector);

            if (rutaImagenSeleccionada.isEmpty()) {
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                miGrilla.add(imgPorDefecto, 2, 1, 1, 7);
            } else {
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                imgPrevisualizar = Icono.previsualizar(rutaImagenSeleccionada, 150);
                GridPane.setHalignment(imgPrevisualizar, HPos.CENTER);
                miGrilla.add(imgPrevisualizar, 2, 1, 1, 7);
            }
        });

        HBox.setHgrow(cajaImagen, Priority.ALWAYS);
        HBox panelHorizontal = new HBox(2);
        panelHorizontal.setAlignment(Pos.BOTTOM_RIGHT);
        panelHorizontal.getChildren().addAll(cajaImagen, btnSeleccionarImagen);
        miGrilla.add(panelHorizontal, 1, fila);
        
        imgPorDefecto = Icono.obtenerIcono("imgNoDisponible.png", 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        GridPane.setValignment(imgPorDefecto, javafx.geometry.VPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 1, 1, 7);

        // BOTÓN GRABAR
        fila++;
        Button btnGrabar = new Button("GRABAR DESTINO");
        btnGrabar.setPrefHeight(ALTO_CAJA);
        btnGrabar.setMaxWidth(Double.MAX_VALUE);
        btnGrabar.setTextFill(Color.web("#FFFFFF"));
        btnGrabar.setFont(Font.font("Rockwell", FontWeight.BOLD, 14));
        btnGrabar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + ";" 
                + "-fx-border-radius: 8; -fx-background-radius: 8;");
        btnGrabar.setCursor(Cursor.HAND);
        btnGrabar.setOnAction(e -> guardarDestino());
        miGrilla.add(btnGrabar, 1, fila);
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

        if (txtDescripcionDestino.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar una descripción");
            txtDescripcionDestino.requestFocus();
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
        radioPlayero.setSelected(true);
        datePickerTemporada.setValue(LocalDate.now());
        cmbEstadoDestino.getSelectionModel().select(0);
        cajaImagen.clear();
        rutaImagenSeleccionada = "";

        miGrilla.getChildren().remove(imgPrevisualizar);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 1, 1, 7);
        txtNombreDestino.requestFocus();
    }

    private void colocarFrmElegante() {
        Runnable calcular = () -> {
            double alturaMarco = miMarco.getHeight();
            if (alturaMarco > 0) {
                double desplazamiento = alturaMarco * AJUSTE_TITULO;
                miGrilla.setTranslateY(-alturaMarco / 14 + desplazamiento);
            }
        };
        calcular.run();
        miMarco.heightProperty().addListener((obs, antes, despues) -> calcular.run());
    }
}
