package org.poo.vista.conductor;

import java.util.List;
import java.util.Objects;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.poo.controlador.conductor.ConductorControladorEditar;
import org.poo.controlador.conductor.ConductorControladorVentana;
import org.poo.controlador.empresa.EmpresaControladorListar;
import org.poo.dto.ConductorDto;
import org.poo.dto.EmpresaDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.Formulario;
import org.poo.recurso.utilidad.GestorImagen;
import org.poo.recurso.utilidad.Icono;
import org.poo.recurso.utilidad.Marco;
import org.poo.recurso.utilidad.Mensaje;

public class VistaConductorEditar extends StackPane {

    private static final int H_GAP = 10;
    private static final int V_GAP = 20;
    private static final int ALTO_CAJA = 35;
    private static final int TAMANIO_FUENTE = 18;

    private final GridPane miGrilla;
    private final StackPane miFormulario;
    private final Rectangle miMarco;
    private final Stage miEscenario;

    // Controles
    private TextField txtNombreConductor;
    private TextField txtCedulaConductor;
    private DatePicker dpFechaNacimiento;
    private TextField txtTelefonoConductor;
    private TextField txtLicenciaConductor;
    private DatePicker dpFechaVencLicencia;
    private ComboBox<EmpresaDto> cmbEmpresaConductor;
    private RadioButton rbActivo;
    private RadioButton rbInactivo;
    private ToggleGroup grupoEstado;
    private TextField txtImagen;
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private String rutaImagenSeleccionada;

    private final int posicion;
    private final ConductorDto objConductor;
    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;
    private final boolean desdeCarrusel;

    public VistaConductorEditar(Stage ventanaPadre, BorderPane princ, Pane pane,
            double ancho, double alto, ConductorDto objConductorExterno,
            int posicionArchivo, boolean vieneDeCarrusel) {

        miEscenario = ventanaPadre;
        miFormulario = this;
        miFormulario.setAlignment(Pos.CENTER);

        posicion = posicionArchivo;
        objConductor = objConductorExterno;
        panelPrincipal = princ;
        panelCuerpo = pane;
        rutaImagenSeleccionada = "";
        desdeCarrusel = vieneDeCarrusel;

        miGrilla = new GridPane();
        miMarco = Marco.crear(
                miEscenario,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_CONDUCTOR,
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
        double anchoGrilla = ancho * Configuracion.GRILLA_ANCHO_PORCENTAJE;

        miGrilla.setHgap(H_GAP);
        miGrilla.setVgap(V_GAP);
        miGrilla.setAlignment(Pos.TOP_CENTER);
        miGrilla.setPrefSize(anchoGrilla, alto);
        miGrilla.setMinSize(anchoGrilla, alto);
        miGrilla.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        ColumnConstraints col0 = new ColumnConstraints();
        col0.setPercentWidth(40);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(60);
        col1.setHgrow(Priority.ALWAYS);

        miGrilla.getColumnConstraints().addAll(col0, col1);
    }

    private void crearTitulo() {
        int columna = 0, fila = 0, colSpan = 3, rowSpan = 1;

        Region espacioSuperior = new Region();
        espacioSuperior.prefHeightProperty().bind(miEscenario.heightProperty().multiply(0.05));
        miGrilla.add(espacioSuperior, columna, fila, colSpan, rowSpan);

        fila = 1;
        Text titulo = new Text("Formulario Actualización de Conductor");
        titulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        GridPane.setHalignment(titulo, HPos.CENTER);
        miGrilla.add(titulo, columna, fila, colSpan, rowSpan);
    }

    private void crearFormulario() {
        int fila = 2;
        int primeraColumna = 0;
        int segundaColumna = 1;

        // NOMBRE
        fila++;
        Label lblNombre = new Label("Nombre Conductor:");
        lblNombre.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblNombre, primeraColumna, fila);

        txtNombreConductor = new TextField();
        txtNombreConductor.setText(objConductor.getNombreConductor());
        txtNombreConductor.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtNombreConductor, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtNombreConductor, 50);
        miGrilla.add(txtNombreConductor, segundaColumna, fila);

        // CÉDULA
        fila++;
        Label lblCedula = new Label("Cédula:");
        lblCedula.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblCedula, primeraColumna, fila);

        txtCedulaConductor = new TextField();
        txtCedulaConductor.setText(objConductor.getCedulaConductor());
        txtCedulaConductor.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtCedulaConductor, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtCedulaConductor, 15);
        // Formulario.soloNumeros(txtCedulaConductor); // si lo tienes
        miGrilla.add(txtCedulaConductor, segundaColumna, fila);

        // FECHA NACIMIENTO
        fila++;
        Label lblFechaNac = new Label("Fecha Nacimiento:");
        lblFechaNac.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblFechaNac, primeraColumna, fila);

        dpFechaNacimiento = new DatePicker();
        dpFechaNacimiento.setPrefHeight(ALTO_CAJA);
        dpFechaNacimiento.setMaxWidth(Double.MAX_VALUE);
        dpFechaNacimiento.setValue(objConductor.getFechaNacimientoConductor());
        miGrilla.add(dpFechaNacimiento, segundaColumna, fila);

        // TELÉFONO
        fila++;
        Label lblTelefono = new Label("Teléfono:");
        lblTelefono.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblTelefono, primeraColumna, fila);

        txtTelefonoConductor = new TextField();
        txtTelefonoConductor.setText(objConductor.getTelefonoConductor());
        txtTelefonoConductor.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtTelefonoConductor, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtTelefonoConductor, 15);
        // Formulario.soloNumeros(txtTelefonoConductor); // si lo tienes
        miGrilla.add(txtTelefonoConductor, segundaColumna, fila);

        // LICENCIA
        fila++;
        Label lblLicencia = new Label("Licencia:");
        lblLicencia.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblLicencia, primeraColumna, fila);

        txtLicenciaConductor = new TextField();
        txtLicenciaConductor.setText(objConductor.getLicenciaConductor());
        txtLicenciaConductor.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtLicenciaConductor, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtLicenciaConductor, 20);
        miGrilla.add(txtLicenciaConductor, segundaColumna, fila);

        // FECHA VENCIMIENTO LICENCIA
        fila++;
        Label lblFechaVenc = new Label("Fecha Venc. Licencia:");
        lblFechaVenc.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblFechaVenc, primeraColumna, fila);

        dpFechaVencLicencia = new DatePicker();
        dpFechaVencLicencia.setPrefHeight(ALTO_CAJA);
        dpFechaVencLicencia.setMaxWidth(Double.MAX_VALUE);
        dpFechaVencLicencia.setValue(objConductor.getFechaVencimientoLicencia());
        miGrilla.add(dpFechaVencLicencia, segundaColumna, fila);

        // EMPRESA
        fila++;
        Label lblEmpresa = new Label("Empresa:");
        lblEmpresa.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEmpresa, primeraColumna, fila);

        cmbEmpresaConductor = new ComboBox<>();
        cmbEmpresaConductor.setPrefHeight(ALTO_CAJA);
        cmbEmpresaConductor.setMaxWidth(Double.MAX_VALUE);

        List<EmpresaDto> arrEmpresas = EmpresaControladorListar.obtenerEmpresas();
        cmbEmpresaConductor.getItems().addAll(arrEmpresas);

        // Seleccionar empresa actual
        EmpresaDto empActual = objConductor.getEmpresaConductor();
        if (empActual != null) {
            for (EmpresaDto emp : arrEmpresas) {
                if (Objects.equals(emp.getIdEmpresa(), empActual.getIdEmpresa())) {
                    cmbEmpresaConductor.getSelectionModel().select(emp);
                    break;
                }
            }
        }

        miGrilla.add(cmbEmpresaConductor, segundaColumna, fila);

        // ESTADO
        fila++;
        Label lblEstado = new Label("Estado:");
        lblEstado.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEstado, primeraColumna, fila);

        grupoEstado = new ToggleGroup();

        rbActivo = new RadioButton("Activo");
        rbInactivo = new RadioButton("Inactivo");

        rbActivo.setToggleGroup(grupoEstado);
        rbInactivo.setToggleGroup(grupoEstado);

        rbActivo.setFont(Font.font("Arial", 14));
        rbInactivo.setFont(Font.font("Arial", 14));

        if (Boolean.TRUE.equals(objConductor.getEstadoConductor())) {
            grupoEstado.selectToggle(rbActivo);
        } else {
            grupoEstado.selectToggle(rbInactivo);
        }

        HBox boxEstado = new HBox(15, rbActivo, rbInactivo);
        boxEstado.setAlignment(Pos.CENTER_LEFT);

        miGrilla.add(boxEstado, segundaColumna, fila);

        // IMAGEN
        fila++;
        Label lblImagen = new Label("Imagen:");
        lblImagen.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblImagen, primeraColumna, fila);

        txtImagen = new TextField();
        txtImagen.setText(objConductor.getNombreImagenPublicoConductor());
        txtImagen.setDisable(true);
        txtImagen.setPrefHeight(ALTO_CAJA);

        String[] extensiones = {"*.png", "*.jpg", "*.jpeg"};
        FileChooser selector = Formulario.selectorImagen(
                "Seleccionar imagen", "Imágenes", extensiones);

        Button btnSeleccionarImagen = new Button("+");
        btnSeleccionarImagen.setPrefHeight(ALTO_CAJA);
        btnSeleccionarImagen.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        final int filaImagenPreview = fila + 1;

        btnSeleccionarImagen.setOnAction(e -> {
            rutaImagenSeleccionada = GestorImagen.obtenerRutaImagen(txtImagen, selector);

            if (rutaImagenSeleccionada.isEmpty()) {
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                imgPorDefecto = Icono.obtenerFotosExternas(objConductor.getNombreImagenPrivadoConductor(), 150);
                GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
                miGrilla.add(imgPorDefecto, 2, 1, 1, 12);
            } else {
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                imgPrevisualizar = Icono.previsualizar(rutaImagenSeleccionada, 150);
                GridPane.setHalignment(imgPrevisualizar, HPos.CENTER);
                miGrilla.add(imgPrevisualizar, 2, 1, 1, 12);
            }
        });

        HBox.setHgrow(txtImagen, Priority.ALWAYS);
        HBox panelImagen = new HBox(2, txtImagen, btnSeleccionarImagen);
        panelImagen.setAlignment(Pos.BOTTOM_RIGHT);
        miGrilla.add(panelImagen, segundaColumna, fila);

        // Imagen en columna derecha
        imgPorDefecto = Icono.obtenerFotosExternas(
                objConductor.getNombreImagenPrivadoConductor(), 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        GridPane.setValignment(imgPorDefecto, javafx.geometry.VPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 1, 1, 12);

        // ESPACIO ADICIONAL
        fila++;

        // BOTÓN ACTUALIZAR
        fila++;
        Button btnActualizar = new Button("Actualizar Conductor");
        btnActualizar.setPrefHeight(ALTO_CAJA);
        btnActualizar.setMaxWidth(Double.MAX_VALUE);
        btnActualizar.setTextFill(Color.web("#FFFFFF"));
        btnActualizar.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        btnActualizar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + ";");
        btnActualizar.setOnAction(e -> actualizarConductor());
        miGrilla.add(btnActualizar, segundaColumna, fila);

        // BOTÓN REGRESAR (detecta si viene de carrusel o administrar)
        fila++;
        Button btnRegresar = new Button("Regresar");
        btnRegresar.setPrefHeight(ALTO_CAJA);
        btnRegresar.setMaxWidth(Double.MAX_VALUE);
        btnRegresar.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        btnRegresar.setOnAction(e -> {
            if (desdeCarrusel) {
                panelCuerpo = ConductorControladorVentana.carrusel(
                        miEscenario, panelPrincipal, panelCuerpo,
                        Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO, posicion);
            } else {
                panelCuerpo = ConductorControladorVentana.administrar(
                        miEscenario, panelPrincipal, panelCuerpo,
                        Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO);
            }
            panelPrincipal.setCenter(null);
            panelPrincipal.setCenter(panelCuerpo);
        });
        miGrilla.add(btnRegresar, segundaColumna, fila);
    }

    private Boolean formularioCompleto() {
        Window ventana = this.getScene() != null ? this.getScene().getWindow() : null;

        if (txtNombreConductor.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, ventana,
                    "Campo vacío", "Debe ingresar el nombre");
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

        if (cmbEmpresaConductor.getValue() == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, ventana,
                    "Empresa no seleccionada", "Debe seleccionar una empresa");
            cmbEmpresaConductor.requestFocus();
            return false;
        }

        if (grupoEstado.getSelectedToggle() == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, ventana,
                    "Estado no seleccionado", "Debe seleccionar si está Activo o Inactivo");
            return false;
        }

        return true;
    }

    private void actualizarConductor() {
        if (formularioCompleto()) {
            ConductorDto dtoActualizado = new ConductorDto();
            dtoActualizado.setIdConductor(objConductor.getIdConductor());
            dtoActualizado.setNombreConductor(txtNombreConductor.getText());
            dtoActualizado.setCedulaConductor(txtCedulaConductor.getText());
            dtoActualizado.setFechaNacimientoConductor(dpFechaNacimiento.getValue());
            dtoActualizado.setTelefonoConductor(txtTelefonoConductor.getText());
            dtoActualizado.setLicenciaConductor(txtLicenciaConductor.getText());
            dtoActualizado.setFechaVencimientoLicencia(dpFechaVencLicencia.getValue());
            dtoActualizado.setEmpresaConductor(cmbEmpresaConductor.getValue());
            boolean estado = rbActivo.isSelected();
            dtoActualizado.setEstadoConductor(estado);
            dtoActualizado.setNombreImagenPublicoConductor(txtImagen.getText());
            dtoActualizado.setNombreImagenPrivadoConductor(objConductor.getNombreImagenPrivadoConductor());

            if (ConductorControladorEditar.actualizar(posicion, dtoActualizado, rutaImagenSeleccionada)) {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, this.getScene().getWindow(),
                        "Éxito", "Conductor actualizado correctamente");
            } else {
                Mensaje.mostrar(Alert.AlertType.ERROR, this.getScene().getWindow(),
                        "Error", "No se pudo actualizar el conductor");
            }
        }
    }

}
