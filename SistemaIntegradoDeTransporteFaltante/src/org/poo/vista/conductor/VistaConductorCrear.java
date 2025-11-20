package org.poo.vista.conductor;

import java.util.List;
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
import org.poo.controlador.conductor.ConductorControladorGrabar;
import org.poo.controlador.empresa.EmpresaControladorListar;
import org.poo.dto.ConductorDto;
import org.poo.dto.EmpresaDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.Formulario;
import org.poo.recurso.utilidad.GestorImagen;
import org.poo.recurso.utilidad.Icono;
import org.poo.recurso.utilidad.Marco;
import org.poo.recurso.utilidad.Mensaje;

public class VistaConductorCrear extends StackPane {

    private static final int H_GAP = 10;
    private static final int V_GAP = 20;
    private static final int ALTO_FILA = 40;
    private static final int ALTO_CAJA = 35;
    private static final int TAMANIO_FUENTE = 20;
    private static final double AJUSTE_TITULO = 0.1;

    private final GridPane miGrilla;
    private final Rectangle miMarco;

    // Componentes del formulario
    private TextField txtNombreConductor;
    private TextField txtCedulaConductor;
    private DatePicker dpFechaNacimiento;
    private TextField txtTelefonoConductor;
    private TextField txtLicenciaConductor;
    private DatePicker dpFechaVencLicencia;
    private ComboBox<EmpresaDto> cmbEmpresa;
    private Button btnSeleccionarImagen;
    private Button btnGrabar;
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private TextField cajaImagen;
    private String rutaImagenSeleccionada;
    private RadioButton rbActivo;
    private RadioButton rbInactivo;
    private ToggleGroup grupoEstado;

    public VistaConductorCrear(Stage escenario, double ancho, double alto) {
        rutaImagenSeleccionada = "";

        setAlignment(Pos.CENTER);
        miGrilla = new GridPane();
        miMarco = Marco.crear(escenario,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_CONDUCTOR, // similar a TERMINAL
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

        // Un poco más de filas que en terminal para acomodar todos los campos
        for (int i = 0; i < 12; i++) {
            RowConstraints fila = new RowConstraints();
            fila.setMinHeight(ALTO_FILA);
            fila.setMaxHeight(ALTO_FILA);
            miGrilla.getRowConstraints().add(fila);
        }
    }

    private void crearTitulo() {
        Text miTitulo = new Text("FORMULARIO - CREAR CONDUCTOR");
        miTitulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        miTitulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 28));
        GridPane.setHalignment(miTitulo, HPos.CENTER);
        GridPane.setMargin(miTitulo, new Insets(30, 0, 0, 0));
        miGrilla.add(miTitulo, 0, 0, 3, 2); // nombre, columna, fila, colspan, rowspan
    }

    private void crearFormulario() {
        // NOMBRE
        Label lblNombre = new Label("Nombre Conductor:");
        lblNombre.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblNombre, 0, 2);

        txtNombreConductor = new TextField();
        txtNombreConductor.setPromptText("Ej: Juan Pérez");
        txtNombreConductor.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtNombreConductor, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtNombreConductor, 50);
        miGrilla.add(txtNombreConductor, 1, 2);

        // CÉDULA
        Label lblCedula = new Label("Cédula:");
        lblCedula.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblCedula, 0, 3);

        txtCedulaConductor = new TextField();
        txtCedulaConductor.setPromptText("Ej: 1234567890");
        txtCedulaConductor.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtCedulaConductor, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtCedulaConductor, 15);
        // Si tienes un método soloNúmeros:
        // Formulario.soloNumeros(txtCedulaConductor);
        miGrilla.add(txtCedulaConductor, 1, 3);

        // FECHA NACIMIENTO
        Label lblFechaNac = new Label("Fecha Nacimiento:");
        lblFechaNac.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblFechaNac, 0, 4);

        dpFechaNacimiento = new DatePicker();
        dpFechaNacimiento.setPrefHeight(ALTO_CAJA);
        dpFechaNacimiento.setMaxWidth(Double.MAX_VALUE);
        miGrilla.add(dpFechaNacimiento, 1, 4);

        // TELÉFONO
        Label lblTelefono = new Label("Teléfono:");
        lblTelefono.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblTelefono, 0, 5);

        txtTelefonoConductor = new TextField();
        txtTelefonoConductor.setPromptText("Ej: 3001234567");
        txtTelefonoConductor.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtTelefonoConductor, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtTelefonoConductor, 15);
        // Formulario.soloNumeros(txtTelefonoConductor); // si existe
        miGrilla.add(txtTelefonoConductor, 1, 5);

        // LICENCIA
        Label lblLicencia = new Label("Licencia:");
        lblLicencia.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblLicencia, 0, 6);

        txtLicenciaConductor = new TextField();
        txtLicenciaConductor.setPromptText("Ej: C2-123456");
        txtLicenciaConductor.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtLicenciaConductor, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtLicenciaConductor, 20);
        miGrilla.add(txtLicenciaConductor, 1, 6);

        // FECHA VENCIMIENTO LICENCIA
        Label lblFechaVenc = new Label("Vence Licencia:");
        lblFechaVenc.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblFechaVenc, 0, 7);

        dpFechaVencLicencia = new DatePicker();
        dpFechaVencLicencia.setPrefHeight(ALTO_CAJA);
        dpFechaVencLicencia.setMaxWidth(Double.MAX_VALUE);
        miGrilla.add(dpFechaVencLicencia, 1, 7);

        // EMPRESA
        Label lblEmpresa = new Label("Empresa:");
        lblEmpresa.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEmpresa, 0, 8);

        cmbEmpresa = new ComboBox<>();
        cmbEmpresa.setPrefHeight(ALTO_CAJA);
        cmbEmpresa.setMaxWidth(Double.MAX_VALUE);

        // Cargar empresas (ajusta el controlador según tengas)
        List<EmpresaDto> arrEmpresas = EmpresaControladorListar.obtenerEmpresas();
        cmbEmpresa.getItems().addAll(arrEmpresas);
        cmbEmpresa.setPromptText("Seleccione empresa");
        miGrilla.add(cmbEmpresa, 1, 8);

        // ESTADO
        Label lblEstado = new Label("Estado:");
        lblEstado.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEstado, 0, 9);

        grupoEstado = new ToggleGroup();

        rbActivo = new RadioButton("Activo");
        rbInactivo = new RadioButton("Inactivo");

        rbActivo.setToggleGroup(grupoEstado);
        rbInactivo.setToggleGroup(grupoEstado);

// estilo opcional
        rbActivo.setFont(Font.font("Arial", 14));
        rbInactivo.setFont(Font.font("Arial", 14));

        HBox boxEstado = new HBox(15, rbActivo, rbInactivo);
        boxEstado.setAlignment(Pos.CENTER_LEFT);

        miGrilla.add(boxEstado, 1, 9);

        // IMAGEN
        Label lblImagen = new Label("Imagen del conductor:");
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
                miGrilla.add(imgPorDefecto, 2, 1, 1, 10);
            } else {
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                imgPrevisualizar = Icono.previsualizar(rutaImagenSeleccionada, 150);
                GridPane.setHalignment(imgPrevisualizar, HPos.CENTER);
                miGrilla.add(imgPrevisualizar, 2, 1, 1, 10);
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
        miGrilla.add(imgPorDefecto, 2, 1, 1, 10);

        // BOTÓN GRABAR
        btnGrabar = new Button("GRABAR CONDUCTOR");
        btnGrabar.setPrefHeight(ALTO_CAJA);
        btnGrabar.setMaxWidth(Double.MAX_VALUE);
        btnGrabar.setTextFill(Color.web("#FFFFFF"));
        btnGrabar.setFont(Font.font("Rockwell", FontWeight.BOLD, 14));
        btnGrabar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + ";"
                + "-fx-border-radius: 8; -fx-background-radius: 8;");
        btnGrabar.setCursor(Cursor.HAND);
        btnGrabar.setOnAction(e -> guardarConductor());
        miGrilla.add(btnGrabar, 1, 11);
    }

    private Boolean formularioCompleto() {
        Window ventana = this.getScene() != null ? this.getScene().getWindow() : null;

        if (txtNombreConductor.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, ventana,
                    "Campo vacío", "Debe ingresar el nombre del conductor");
            txtNombreConductor.requestFocus();
            return false;
        }

        if (txtCedulaConductor.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, ventana,
                    "Campo vacío", "Debe ingresar la cédula");
            txtCedulaConductor.requestFocus();
            return false;
        }

        if (dpFechaNacimiento.getValue() == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, ventana,
                    "Campo vacío", "Debe seleccionar la fecha de nacimiento");
            dpFechaNacimiento.requestFocus();
            return false;
        }

        if (txtTelefonoConductor.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, ventana,
                    "Campo vacío", "Debe ingresar el teléfono");
            txtTelefonoConductor.requestFocus();
            return false;
        }

        if (txtLicenciaConductor.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, ventana,
                    "Campo vacío", "Debe ingresar la licencia");
            txtLicenciaConductor.requestFocus();
            return false;
        }

        if (dpFechaVencLicencia.getValue() == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, ventana,
                    "Campo vacío", "Debe seleccionar la fecha de vencimiento de la licencia");
            dpFechaVencLicencia.requestFocus();
            return false;
        }

        if (cmbEmpresa.getValue() == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, ventana,
                    "Empresa no seleccionada", "Debe seleccionar una empresa");
            cmbEmpresa.requestFocus();
            return false;
        }

        if (grupoEstado.getSelectedToggle() == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Estado no seleccionado", "Debe seleccionar si está Activo o Inactivo");
            return false;
        }

        if (rutaImagenSeleccionada.isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, ventana,
                    "Imagen no seleccionada", "Debe seleccionar una imagen");
            return false;
        }

        return true;
    }

    private void guardarConductor() {
        if (formularioCompleto()) {
            ConductorDto dto = new ConductorDto();
            dto.setNombreConductor(txtNombreConductor.getText());
            dto.setCedulaConductor(txtCedulaConductor.getText());
            dto.setFechaNacimientoConductor(dpFechaNacimiento.getValue());
            dto.setTelefonoConductor(txtTelefonoConductor.getText());
            dto.setLicenciaConductor(txtLicenciaConductor.getText());
            dto.setFechaVencimientoLicencia(dpFechaVencLicencia.getValue());
            dto.setEmpresaConductor(cmbEmpresa.getValue());
            Boolean estado = rbActivo.isSelected();
            dto.setEstadoConductor(estado);
            dto.setNombreImagenPublicoConductor(cajaImagen.getText());

            if (ConductorControladorGrabar.crearConductor(dto, rutaImagenSeleccionada)) {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, this.getScene().getWindow(),
                        "Éxito", "Conductor creado correctamente");
                limpiarFormulario();
            } else {
                Mensaje.mostrar(Alert.AlertType.ERROR, this.getScene().getWindow(),
                        "Error", "No se pudo crear el conductor");
            }
        }
    }

    private void limpiarFormulario() {
        txtNombreConductor.clear();
        txtCedulaConductor.clear();
        dpFechaNacimiento.setValue(null);
        txtTelefonoConductor.clear();
        txtLicenciaConductor.clear();
        dpFechaVencLicencia.setValue(null);

        cmbEmpresa.getSelectionModel().clearSelection();

        if (grupoEstado != null) {
            grupoEstado.selectToggle(null); // desmarca ambos
        }
        if (rbActivo != null) {
            rbActivo.setSelected(false);
        }
        if (rbInactivo != null) {
            rbInactivo.setSelected(false);
        }

        cajaImagen.clear();
        rutaImagenSeleccionada = "";

        miGrilla.getChildren().remove(imgPrevisualizar);
        miGrilla.getChildren().remove(imgPorDefecto);

        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        GridPane.setValignment(imgPorDefecto, VPos.CENTER);

        miGrilla.add(imgPorDefecto, 2, 1, 1, 10);

        txtNombreConductor.requestFocus();

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
