package org.poo.vista.destino;

import java.time.LocalDate;
import javafx.geometry.HPos;
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
import org.poo.controlador.destino.DestinoControladorEditar;
import org.poo.controlador.destino.DestinoControladorVentana;
import org.poo.dto.DestinoDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.*;

public class VistaDestinoEditar extends StackPane {

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

    private TextField txtNombreDestino;
    private TextField txtDepartamentoDestino;
    private TextArea txtDescripcionDestino;
    private ComboBox<String> cmbEstadoDestino;
    private TextField txtImagen;
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private String rutaImagenSeleccionada;
    private Spinner<Integer> spinnerAltitud;
    private Slider sliderTemperatura;
    private Label lblValorTemp;
    private RadioButton radioPlayero;
    private RadioButton radioMontañoso;
    private DatePicker datePickerTemporada;

    private final int posicion;
    private final DestinoDto objDestino;
    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;
    private final boolean desdeCarrusel;

    public VistaDestinoEditar(Stage ventanaPadre, BorderPane princ, Pane pane,
            double ancho, double alto, DestinoDto objDestinoExterno, int posicionArchivo,
            boolean vieneDeCarrusel) {

        miEscenario = ventanaPadre;
        miFormulario = this;
        miFormulario.setAlignment(Pos.CENTER);

        posicion = posicionArchivo;
        objDestino = objDestinoExterno;
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
        int columna = 0, fila = 0, colSpan = 3, rowSpan = 1;

        Region espacioSuperior = new Region();
        espacioSuperior.prefHeightProperty().bind(miEscenario.heightProperty().multiply(0.05));
        miGrilla.add(espacioSuperior, columna, fila, colSpan, rowSpan);

        fila = 1;
        Text titulo = new Text("FORMULARIO - EDITAR DESTINO");
        titulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        titulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 26));
        GridPane.setHalignment(titulo, HPos.CENTER);
        miGrilla.add(titulo, columna, fila, colSpan, rowSpan);
    }

    private void crearFormulario() {

        Label lblNombre = new Label("Nombre del Destino:");
        lblNombre.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblNombre, 0, 2);

        txtNombreDestino = new TextField();
        txtNombreDestino.setText(objDestino.getNombreDestino());
        txtNombreDestino.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtNombreDestino, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtNombreDestino, 50);
        miGrilla.add(txtNombreDestino, 1, 2);

        // DEPARTAMENTO
        Label lblDepartamento = new Label("Departamento:");
        lblDepartamento.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDepartamento, 0, 3);

        txtDepartamentoDestino = new TextField();
        txtDepartamentoDestino.setText(objDestino.getDepartamentoDestino());
        txtDepartamentoDestino.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtDepartamentoDestino, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtDepartamentoDestino, 50);
        miGrilla.add(txtDepartamentoDestino, 1, 3);

        // DESCRIPCIÓN
        Label lblDescripcion = new Label("Descripción:");
        lblDescripcion.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDescripcion, 0, 4);

        txtDescripcionDestino = new TextArea();
        txtDescripcionDestino.setText(objDestino.getDescripcionDestino());
        txtDescripcionDestino.setPrefRowCount(3);
        txtDescripcionDestino.setWrapText(true);
        txtDescripcionDestino.setMaxWidth(Double.MAX_VALUE);
        miGrilla.add(txtDescripcionDestino, 1, 4);

        // ALTITUD (Spinner)
        Label lblAltitud = new Label("Altitud (msnm):");
        lblAltitud.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblAltitud, 0, 5);

        spinnerAltitud = new Spinner<>();
        int altitudInicial = objDestino.getAltitudMetros() != null ? objDestino.getAltitudMetros() : 100;
        SpinnerValueFactory<Integer> valueFactoryAltitud = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5000, altitudInicial);
        spinnerAltitud.setValueFactory(valueFactoryAltitud);
        spinnerAltitud.setPrefHeight(ALTO_CAJA);
        spinnerAltitud.setMaxWidth(Double.MAX_VALUE);
        spinnerAltitud.setEditable(true);
        miGrilla.add(spinnerAltitud, 1, 5);

        // TEMPERATURA (Slider)
        Label lblTemperatura = new Label("Temperatura Promedio °C:");
        lblTemperatura.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblTemperatura, 0, 6);

        double tempInicial = objDestino.getTemperaturaPromedio() != null ? objDestino.getTemperaturaPromedio() : 25.0;
        VBox vboxTemp = new VBox(5);
        sliderTemperatura = new Slider(0, 40, tempInicial);
        sliderTemperatura.setShowTickLabels(true);
        sliderTemperatura.setShowTickMarks(true);
        sliderTemperatura.setMajorTickUnit(10);
        sliderTemperatura.setBlockIncrement(1);
        
        lblValorTemp = new Label(String.format("%.1f°C", tempInicial));
        lblValorTemp.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        sliderTemperatura.valueProperty().addListener((obs, oldVal, newVal) -> {
            lblValorTemp.setText(String.format("%.1f°C", newVal.doubleValue()));
        });
        
        vboxTemp.getChildren().addAll(sliderTemperatura, lblValorTemp);
        miGrilla.add(vboxTemp, 1, 6);

        // TIPO DE DESTINO (RadioButton)
        Label lblTipo = new Label("Tipo de destino:");
        lblTipo.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblTipo, 0, 7);

        ToggleGroup grupoTipo = new ToggleGroup();
        radioPlayero = new RadioButton("Playero");
        radioMontañoso = new RadioButton("Montañoso");
        
        radioPlayero.setToggleGroup(grupoTipo);
        radioMontañoso.setToggleGroup(grupoTipo);
        
        // Cargar valor del objeto
        if (objDestino.getEsPlayero() != null && objDestino.getEsPlayero()) {
            radioPlayero.setSelected(true);
        } else {
            radioMontañoso.setSelected(true);
        }
        
        radioPlayero.setFont(Font.font("Times new roman", 14));
        radioMontañoso.setFont(Font.font("Times new roman", 14));
        
        HBox hboxRadio = new HBox(15);
        hboxRadio.getChildren().addAll(radioPlayero, radioMontañoso);
        miGrilla.add(hboxRadio, 1, 7);

        // TEMPORADA ALTA (DatePicker)
        Label lblTemporada = new Label("Inicio Temporada Alta:");
        lblTemporada.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblTemporada, 0, 8);

        datePickerTemporada = new DatePicker();
        try {
            if (objDestino.getTemporadaAlta() != null && !objDestino.getTemporadaAlta().isEmpty()) {
                datePickerTemporada.setValue(LocalDate.parse(objDestino.getTemporadaAlta()));
            } else {
                datePickerTemporada.setValue(LocalDate.now());
            }
        } catch (Exception e) {
            datePickerTemporada.setValue(LocalDate.now());
        }
        datePickerTemporada.setPrefHeight(ALTO_CAJA);
        datePickerTemporada.setMaxWidth(Double.MAX_VALUE);
        miGrilla.add(datePickerTemporada, 1, 8);

        // ESTADO
        Label lblEstado = new Label("Estado:");
        lblEstado.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEstado, 0, 9);

        cmbEstadoDestino = new ComboBox<>();
        cmbEstadoDestino.setMaxWidth(Double.MAX_VALUE);
        cmbEstadoDestino.setPrefHeight(ALTO_CAJA);
        cmbEstadoDestino.getItems().addAll("Seleccione estado", "Activo", "Inactivo");
        
        if (objDestino.getEstadoDestino()) {
            cmbEstadoDestino.getSelectionModel().select(1);
        } else {
            cmbEstadoDestino.getSelectionModel().select(2);
        }
        miGrilla.add(cmbEstadoDestino, 1, 9);

        // IMAGEN
        Label lblImagen = new Label("Imagen:");
        lblImagen.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblImagen, 0, 10);

        txtImagen = new TextField();
        txtImagen.setText(objDestino.getNombreImagenPublicoDestino());
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
                imgPorDefecto = Icono.obtenerFotosExternas(objDestino.getNombreImagenPrivadoDestino(), 150);
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
        miGrilla.add(panelImagen, 1, 10);

        // Imagen en columna derecha
        imgPorDefecto = Icono.obtenerFotosExternas(
                objDestino.getNombreImagenPrivadoDestino(), 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        GridPane.setValignment(imgPorDefecto, javafx.geometry.VPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 5);

        Button btnActualizar = new Button("ACTUALIZAR");
        btnActualizar.setPrefHeight(ALTO_CAJA);
        btnActualizar.setMaxWidth(Double.MAX_VALUE);
        btnActualizar.setTextFill(Color.web("#FFFFFF"));
        btnActualizar.setFont(Font.font("Rockwell", FontWeight.BOLD, 16));
        btnActualizar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + ";");
        btnActualizar.setCursor(Cursor.HAND);
        btnActualizar.setOnAction(e -> actualizarDestino());
        miGrilla.add(btnActualizar, 1, 11);

        // BOTÓN REGRESAR
        Button btnRegresar = new Button("VOLVER");
        btnRegresar.setPrefHeight(ALTO_CAJA);
        btnRegresar.setMaxWidth(Double.MAX_VALUE);
        btnRegresar.setFont(Font.font("Rockwell", FontWeight.BOLD, 14));
        btnRegresar.setCursor(Cursor.HAND);
        btnRegresar.setOnAction(e -> {
            if (desdeCarrusel) {
                panelCuerpo = DestinoControladorVentana.carrusel(
                        miEscenario, panelPrincipal, panelCuerpo,
                        Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO, posicion);
            } else {
                panelCuerpo = DestinoControladorVentana.administrar(
                        miEscenario, panelPrincipal, panelCuerpo,
                        Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO);
            }
            panelPrincipal.setCenter(null);
            panelPrincipal.setCenter(panelCuerpo);
        });
        miGrilla.add(btnRegresar, 1, 12);
    }

    private Boolean formularioCompleto() {
        if (txtNombreDestino.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar el nombre");
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

        return true;
    }

    private void actualizarDestino() {
        if (formularioCompleto()) {
            DestinoDto dtoActualizado = new DestinoDto();
            dtoActualizado.setIdDestino(objDestino.getIdDestino());
            dtoActualizado.setNombreDestino(txtNombreDestino.getText());
            dtoActualizado.setDepartamentoDestino(txtDepartamentoDestino.getText());
            dtoActualizado.setDescripcionDestino(txtDescripcionDestino.getText());
            dtoActualizado.setAltitudMetros(spinnerAltitud.getValue());
            dtoActualizado.setTemperaturaPromedio(sliderTemperatura.getValue());
            dtoActualizado.setEsPlayero(radioPlayero.isSelected());
            dtoActualizado.setTemporadaAlta(datePickerTemporada.getValue().toString());
            dtoActualizado.setEstadoDestino(cmbEstadoDestino.getValue().equals("Activo"));
            dtoActualizado.setNombreImagenPublicoDestino(txtImagen.getText());
            dtoActualizado.setNombreImagenPrivadoDestino(objDestino.getNombreImagenPrivadoDestino());

            if (DestinoControladorEditar.actualizar(posicion, dtoActualizado, rutaImagenSeleccionada)) {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, this.getScene().getWindow(),
                        "Éxito", "Destino actualizado correctamente");
            } else {
                Mensaje.mostrar(Alert.AlertType.ERROR, this.getScene().getWindow(),
                        "Error", "No se pudo actualizar el destino");
            }
        }
    }
}
