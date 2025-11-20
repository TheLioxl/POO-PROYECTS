package org.poo.vista.pasajero;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.poo.controlador.pasajero.PasajeroControladorGrabar;
import org.poo.dto.PasajeroDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.Formulario;
import org.poo.recurso.utilidad.GestorImagen;
import org.poo.recurso.utilidad.Icono;
import org.poo.recurso.utilidad.Marco;
import org.poo.recurso.utilidad.Mensaje;

public class VistaPasajeroCrear extends StackPane {

    private static final int H_GAP = 10;
    private static final int V_GAP = 20;
    private static final int ALTO_FILA = 30;
    private static final int ALTO_CAJA = 30;
    private static final int TAMANIO_FUENTE = 20;
    private static final double AJUSTE_TITULO = 0.1;

    private final GridPane miGrilla;
    private final Rectangle miMarco;

    // Componentes del formulario
    private TextField txtNombrePasajero;
    private TextField txtDocumentoPasajero;
    private ComboBox<String> cmbTipoDocumento;
    private DatePicker dpFechaNacimiento;
    private RadioButton rbMayor;
    private RadioButton rbMenor;
    private ToggleGroup grupoMayor;
    private TextField txtTelefonoPasajero;
    private TextField txtEmailPasajero;

    private Button btnSeleccionarImagen;
    private Button btnGrabar;
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private TextField cajaImagen;
    private String rutaImagenSeleccionada;

    public VistaPasajeroCrear(Stage escenario, double ancho, double alto) {
        rutaImagenSeleccionada = "";

        setAlignment(Pos.CENTER);
        miGrilla = new GridPane();
        miMarco = Marco.crear(escenario,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                // Si creas un degrade propio para pasajero, cámbialo aquí:
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
        Text miTitulo = new Text("FORMULARIO - CREAR PASAJERO");
        miTitulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        miTitulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 28));
        GridPane.setHalignment(miTitulo, HPos.CENTER);
        GridPane.setMargin(miTitulo, new Insets(30, 0, 0, 0));
        miGrilla.add(miTitulo, 0, 0, 3, 2);
    }

    private void crearFormulario() {
        // NOMBRE
        Label lblNombre = new Label("Nombre Pasajero:");
        lblNombre.setFont(Font.font("Times New Roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblNombre, 0, 2);

        txtNombrePasajero = new TextField();
        txtNombrePasajero.setPromptText("Ej: Carlos Pérez");
        txtNombrePasajero.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtNombrePasajero, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtNombrePasajero, 50);
        miGrilla.add(txtNombrePasajero, 1, 2);

        // DOCUMENTO
        Label lblDocumento = new Label("Documento:");
        lblDocumento.setFont(Font.font("Times New Roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDocumento, 0, 3);

        txtDocumentoPasajero = new TextField();
        txtDocumentoPasajero.setPromptText("Ej: 1234567890");
        txtDocumentoPasajero.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtDocumentoPasajero, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtDocumentoPasajero, 15);
        // Si tienes soloNumeros:
        // Formulario.soloNumeros(txtDocumentoPasajero);
        miGrilla.add(txtDocumentoPasajero, 1, 3);

        // TIPO DOCUMENTO
        Label lblTipoDoc = new Label("Tipo de documento:");
        lblTipoDoc.setFont(Font.font("Times New Roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblTipoDoc, 0, 4);

        cmbTipoDocumento = new ComboBox<>();
        cmbTipoDocumento.setPrefHeight(ALTO_CAJA);
        cmbTipoDocumento.setMaxWidth(Double.MAX_VALUE);
        cmbTipoDocumento.getItems().addAll(
                "Seleccione tipo",
                "Cédula de ciudadanía",
                "Tarjeta de identidad",
                "Pasaporte"
        );
        cmbTipoDocumento.getSelectionModel().select(0);
        miGrilla.add(cmbTipoDocumento, 1, 4);

        // FECHA NACIMIENTO
        Label lblFechaNac = new Label("Fecha nacimiento:");
        lblFechaNac.setFont(Font.font("Times New Roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblFechaNac, 0, 5);

        dpFechaNacimiento = new DatePicker();
        dpFechaNacimiento.setPrefHeight(ALTO_CAJA);
        dpFechaNacimiento.setMaxWidth(Double.MAX_VALUE);
        miGrilla.add(dpFechaNacimiento, 1, 5);

        // ES MAYOR DE EDAD (RADIOBUTTON)
        Label lblMayor = new Label("¿Mayor de edad?:");
        lblMayor.setFont(Font.font("Times New Roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblMayor, 0, 6);

        grupoMayor = new ToggleGroup();
        rbMayor = new RadioButton("Mayor de edad");
        rbMenor = new RadioButton("Menor de edad");
        rbMayor.setToggleGroup(grupoMayor);
        rbMenor.setToggleGroup(grupoMayor);
        rbMayor.setFont(Font.font("Times new roman", 14));
        rbMenor.setFont(Font.font("Times new roman", 14));
        HBox boxMayor = new HBox(15, rbMayor, rbMenor);
        boxMayor.setAlignment(Pos.CENTER_LEFT);
        miGrilla.add(boxMayor, 1, 6);

        // TELÉFONO
        Label lblTelefono = new Label("Teléfono:");
        lblTelefono.setFont(Font.font("Times New Roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblTelefono, 0, 7);

        txtTelefonoPasajero = new TextField();
        txtTelefonoPasajero.setPromptText("Ej: 3001234567");
        txtTelefonoPasajero.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtTelefonoPasajero, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtTelefonoPasajero, 15);
        // Formulario.soloNumeros(txtTelefonoPasajero);
        miGrilla.add(txtTelefonoPasajero, 1, 7);

        // IMAGEN DOCUMENTO
        Label lblImagen = new Label("Imagen del documento:");
        lblImagen.setFont(Font.font("Times New Roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblImagen, 0, 8);

        cajaImagen = new TextField();
        cajaImagen.setDisable(true);
        cajaImagen.setPrefHeight(ALTO_CAJA);
        String[] extensiones = {"*.png", "*.jpg", "*.jpeg"};
        FileChooser objSeleccionar = Formulario.selectorImagen(
                "Seleccionar imagen", "Imágenes", extensiones);

        btnSeleccionarImagen = new Button("+");
        btnSeleccionarImagen.setPrefHeight(ALTO_CAJA);

        btnSeleccionarImagen.setOnAction(e -> {
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
        miGrilla.add(panelHorizontal, 1, 8);

        imgPorDefecto = Icono.obtenerIcono("imgNoDisponible.png", 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        GridPane.setValignment(imgPorDefecto, VPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 5);

        // BOTÓN GRABAR
        btnGrabar = new Button("GRABAR PASAJERO");
        btnGrabar.setPrefHeight(ALTO_CAJA);
        btnGrabar.setMaxWidth(Double.MAX_VALUE);
        btnGrabar.setTextFill(Color.web("#FFFFFF"));
        btnGrabar.setFont(Font.font("Rockwell", FontWeight.BOLD, 14));
        btnGrabar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + ";"
                + "-fx-border-radius: 8; -fx-background-radius: 8;");
        btnGrabar.setCursor(Cursor.HAND);
        btnGrabar.setOnAction(e -> guardarPasajero());
        miGrilla.add(btnGrabar, 1, 9);
    }

    // ================== VALIDACIÓN ==================
    private Boolean formularioCompleto() {
        Window ventana = this.getScene() != null ? this.getScene().getWindow() : null;

        if (txtNombrePasajero.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, ventana,
                    "Campo vacío", "Debe ingresar el nombre del pasajero");
            txtNombrePasajero.requestFocus();
            return false;
        }

        if (txtDocumentoPasajero.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, ventana,
                    "Campo vacío", "Debe ingresar el documento");
            txtDocumentoPasajero.requestFocus();
            return false;
        }

        if (cmbTipoDocumento.getSelectionModel().getSelectedIndex() == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, ventana,
                    "Tipo de documento no seleccionado", "Debe seleccionar un tipo de documento");
            cmbTipoDocumento.requestFocus();
            return false;
        }

        if (dpFechaNacimiento.getValue() == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, ventana,
                    "Campo vacío", "Debe seleccionar la fecha de nacimiento");
            dpFechaNacimiento.requestFocus();
            return false;
        }

        if (grupoMayor.getSelectedToggle() == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, ventana,
                    "Campo vacío", "Debe indicar si es mayor o menor de edad");
            return false;
        }

        if (txtTelefonoPasajero.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, ventana,
                    "Campo vacío", "Debe ingresar el teléfono");
            txtTelefonoPasajero.requestFocus();
            return false;
        }

        if (rutaImagenSeleccionada.isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, ventana,
                    "Imagen no seleccionada", "Debe seleccionar la imagen del documento");
            return false;
        }

        return true;
    }

    // ================== GUARDAR ==================
    private void guardarPasajero() {
        if (formularioCompleto()) {
            PasajeroDto dto = new PasajeroDto();
            dto.setNombrePasajero(txtNombrePasajero.getText());
            dto.setDocumentoPasajero(txtDocumentoPasajero.getText());
            dto.setTipoDocumentoPasajero(cmbTipoDocumento.getValue());
            dto.setFechaNacimientoPasajero(dpFechaNacimiento.getValue());
            dto.setEsMayorPasajero(rbMayor.isSelected()); // true = mayor, false = menor
            dto.setTelefonoPasajero(txtTelefonoPasajero.getText());
            dto.setNombreImagenPublicoPasajero(cajaImagen.getText());

            if (PasajeroControladorGrabar.crearPasajero(dto, rutaImagenSeleccionada)) {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, this.getScene().getWindow(),
                        "Éxito", "Pasajero creado correctamente");
                limpiarFormulario();
            } else {
                Mensaje.mostrar(Alert.AlertType.ERROR, this.getScene().getWindow(),
                        "Error", "No se pudo crear el pasajero");
            }
        }
    }

    // ================== LIMPIAR ==================
    private void limpiarFormulario() {
        txtNombrePasajero.clear();
        txtDocumentoPasajero.clear();
        cmbTipoDocumento.getSelectionModel().select(0);
        dpFechaNacimiento.setValue(null);

        if (grupoMayor != null) {
            grupoMayor.selectToggle(null);
        }
        if (rbMayor != null) {
            rbMayor.setSelected(false);
        }
        if (rbMenor != null) {
            rbMenor.setSelected(false);
        }

        txtTelefonoPasajero.clear();
        txtEmailPasajero.clear();

        cajaImagen.clear();
        rutaImagenSeleccionada = "";

        miGrilla.getChildren().remove(imgPrevisualizar);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 5);

        txtNombrePasajero.requestFocus();
    }

    // ================== POSICIÓN BONITA ==================
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