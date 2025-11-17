package org.poo.vista.empresa;

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
import org.poo.controlador.empresa.EmpresaControladorGrabar;
import org.poo.controlador.terminal.TerminalControladorListar;
import org.poo.dto.EmpresaDto;
import org.poo.dto.TerminalDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.*;

import java.time.LocalDate;
import java.util.List;

public class VistaEmpresaCrear extends StackPane {

    private static final int H_GAP = 10;
    private static final int V_GAP = 12; // Reducido para que quepa todo
    private static final int ALTO_CAJA = 35;
    private static final int TAMANIO_FUENTE = 16; // Reducido

    private final GridPane miGrilla;
    private final Rectangle miMarco;
    private final Stage miEscenario;

    // 6 TIPOS DE OBJETOS DIFERENTES
    private TextField txtNombreEmpresa;           // 1. TextField
    private TextField txtNitEmpresa;              // 1. TextField
    private ComboBox<TerminalDto> cmbTerminalEmpresa;  // 2. ComboBox
    private ComboBox<String> cmbEstadoEmpresa;         // 2. ComboBox
    private DatePicker dateFechaFundacion;        // 3. DatePicker ✨
    private Spinner<Integer> spinnerEmpleados;    // 4. Spinner ✨
    private CheckBox chk24Horas;                  // 5. CheckBox ✨
    private CheckBox chkMantenimiento;            // 5. CheckBox
    private CheckBox chkServicioCliente;          // 5. CheckBox
    private TextArea txtDescripcion;              // 6. TextArea ✨
    
    private TextField txtImagen;
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private String rutaImagenSeleccionada;

    public VistaEmpresaCrear(Stage escenario, double ancho, double alto) {
        miEscenario = escenario;
        rutaImagenSeleccionada = "";
        setAlignment(Pos.CENTER);

        miGrilla = new GridPane();
        miMarco = Marco.crear(
                miEscenario,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_EMPRESA,
                Configuracion.DEGRADE_BORDE
        );

        getChildren().add(miMarco);

        configurarGrilla(ancho, alto);
        crearTitulo();
        crearFormulario();
        
        getChildren().add(miGrilla);
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
        col0.setPercentWidth(35); // Reducido para dar más espacio a los controles
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(65);
        col1.setHgrow(Priority.ALWAYS);
        
        miGrilla.getColumnConstraints().addAll(col0, col1);
    }

    private void crearTitulo() {
        int columna = 0, fila = 0, colSpan = 2, rowSpan = 1;

        Region espacioSuperior = new Region();
        espacioSuperior.prefHeightProperty().bind(
                miEscenario.heightProperty().multiply(0.02)); // Reducido
        miGrilla.add(espacioSuperior, columna, fila, colSpan, rowSpan);

        fila = 1;
        Text titulo = new Text("Formulario Creación de Empresa");
        titulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24)); // Reducido
        GridPane.setHalignment(titulo, HPos.CENTER);
        miGrilla.add(titulo, columna, fila, colSpan, rowSpan);
    }

    private void crearFormulario() {
        int fila = 2;
        int primeraColumna = 0;
        int segundaColumna = 1;

        // 1. NOMBRE EMPRESA - TextField
        fila++;
        Label lblNombre = new Label("Nombre Empresa:");
        lblNombre.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblNombre, primeraColumna, fila);

        txtNombreEmpresa = new TextField();
        txtNombreEmpresa.setPromptText("Ej: Expreso Brasilia");
        txtNombreEmpresa.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtNombreEmpresa, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtNombreEmpresa, 50);
        miGrilla.add(txtNombreEmpresa, segundaColumna, fila);

        // 1. NIT - TextField
        fila++;
        Label lblNit = new Label("NIT:");
        lblNit.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblNit, primeraColumna, fila);

        txtNitEmpresa = new TextField();
        txtNitEmpresa.setPromptText("Ej: 900123456-7");
        txtNitEmpresa.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtNitEmpresa, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtNitEmpresa, 20);
        miGrilla.add(txtNitEmpresa, segundaColumna, fila);

        // 2. TERMINAL - ComboBox
        fila++;
        Label lblTerminal = new Label("Terminal:");
        lblTerminal.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblTerminal, primeraColumna, fila);

        cmbTerminalEmpresa = new ComboBox<>();
        cmbTerminalEmpresa.setMaxWidth(Double.MAX_VALUE);
        cmbTerminalEmpresa.setPrefHeight(ALTO_CAJA);
        
        List<TerminalDto> terminales = TerminalControladorListar.obtenerTerminalesActivos();
        TerminalDto opcionDefault = new TerminalDto();
        opcionDefault.setIdTerminal(0);
        opcionDefault.setNombreTerminal("Seleccione terminal");
        
        cmbTerminalEmpresa.getItems().add(opcionDefault);
        cmbTerminalEmpresa.getItems().addAll(terminales);
        cmbTerminalEmpresa.getSelectionModel().select(0);
        
        cmbTerminalEmpresa.setConverter(new StringConverter<TerminalDto>() {
            @Override
            public String toString(TerminalDto terminal) {
                return terminal != null ? terminal.getNombreTerminal() : "";
            }

            @Override
            public TerminalDto fromString(String string) {
                return null;
            }
        });
        
        miGrilla.add(cmbTerminalEmpresa, segundaColumna, fila);

        // 2. ESTADO - ComboBox
        fila++;
        Label lblEstado = new Label("Estado:");
        lblEstado.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEstado, primeraColumna, fila);

        cmbEstadoEmpresa = new ComboBox<>();
        cmbEstadoEmpresa.setMaxWidth(Double.MAX_VALUE);
        cmbEstadoEmpresa.setPrefHeight(ALTO_CAJA);
        cmbEstadoEmpresa.getItems().addAll("Seleccione estado", "Activo", "Inactivo");
        cmbEstadoEmpresa.getSelectionModel().select(0);
        miGrilla.add(cmbEstadoEmpresa, segundaColumna, fila);

        // 3. FECHA FUNDACIÓN - DatePicker ✨
        fila++;
        Label lblFechaFundacion = new Label("Fecha Fundación:");
        lblFechaFundacion.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblFechaFundacion, primeraColumna, fila);

        dateFechaFundacion = new DatePicker();
        dateFechaFundacion.setMaxWidth(Double.MAX_VALUE);
        dateFechaFundacion.setPrefHeight(ALTO_CAJA);
        dateFechaFundacion.setPromptText("Seleccione fecha");
        dateFechaFundacion.setValue(LocalDate.now().minusYears(10)); // Valor por defecto
        Formulario.deshabilitarFechasFuturas(dateFechaFundacion);
        miGrilla.add(dateFechaFundacion, segundaColumna, fila);

        // 4. CANTIDAD EMPLEADOS - Spinner ✨
        fila++;
        Label lblEmpleados = new Label("Cant. Empleados:");
        lblEmpleados.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEmpleados, primeraColumna, fila);

        spinnerEmpleados = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 50);
        spinnerEmpleados.setValueFactory(valueFactory);
        spinnerEmpleados.setPrefHeight(ALTO_CAJA);
        spinnerEmpleados.setMaxWidth(Double.MAX_VALUE);
        spinnerEmpleados.setEditable(true);
        miGrilla.add(spinnerEmpleados, segundaColumna, fila);

        // 5. SERVICIOS - CheckBox ✨
        fila++;
        Label lblServicios = new Label("Servicios:");
        lblServicios.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblServicios, primeraColumna, fila);

        chk24Horas = new CheckBox("Servicio 24 Horas");
        chkMantenimiento = new CheckBox("Mantenimiento Propio");
        chkServicioCliente = new CheckBox("Atención al Cliente");
        
        chk24Horas.setFont(Font.font("Arial", 13));
        chkMantenimiento.setFont(Font.font("Arial", 13));
        chkServicioCliente.setFont(Font.font("Arial", 13));

        VBox vboxServicios = new VBox(3);
        vboxServicios.getChildren().addAll(chk24Horas, chkMantenimiento, chkServicioCliente);
        miGrilla.add(vboxServicios, segundaColumna, fila);

        // 6. DESCRIPCIÓN - TextArea ✨
        fila++;
        Label lblDescripcion = new Label("Descripción:");
        lblDescripcion.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDescripcion, primeraColumna, fila);

        txtDescripcion = new TextArea();
        txtDescripcion.setPromptText("Breve descripción de la empresa...");
        txtDescripcion.setPrefRowCount(2); // Reducido
        txtDescripcion.setWrapText(true);
        txtDescripcion.setMaxWidth(Double.MAX_VALUE);
        miGrilla.add(txtDescripcion, segundaColumna, fila);

        // IMAGEN
        fila++;
        Label lblImagen = new Label("Logo:");
        lblImagen.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblImagen, primeraColumna, fila);

        txtImagen = new TextField();
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
            
            if (!rutaImagenSeleccionada.isEmpty()) {
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                
                imgPrevisualizar = Icono.previsualizar(rutaImagenSeleccionada, 120);
                GridPane.setHalignment(imgPrevisualizar, HPos.CENTER);
                miGrilla.add(imgPrevisualizar, segundaColumna, filaImagenPreview);
            }
        });

        HBox.setHgrow(txtImagen, Priority.ALWAYS);
        HBox panelImagen = new HBox(5, txtImagen, btnSeleccionarImagen);
        miGrilla.add(panelImagen, segundaColumna, fila);

        // PREVISUALIZACIÓN
        fila++;
        imgPorDefecto = Icono.obtenerIcono(Configuracion.ICONO_NO_DISPONIBLE, 120);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        miGrilla.add(imgPorDefecto, segundaColumna, fila);

        // BOTÓN GRABAR
        fila++;
        Button btnGrabar = new Button("Crear Empresa");
        btnGrabar.setPrefHeight(ALTO_CAJA);
        btnGrabar.setMaxWidth(Double.MAX_VALUE);
        btnGrabar.setTextFill(Color.web("#FFFFFF"));
        btnGrabar.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        btnGrabar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + ";");
        btnGrabar.setOnAction(e -> guardarEmpresa());
        miGrilla.add(btnGrabar, segundaColumna, fila);
    }

    private Boolean formularioCompleto() {
        if (txtNombreEmpresa.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar el nombre de la empresa");
            txtNombreEmpresa.requestFocus();
            return false;
        }

        if (txtNitEmpresa.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar el NIT");
            txtNitEmpresa.requestFocus();
            return false;
        }

        if (cmbTerminalEmpresa.getSelectionModel().getSelectedIndex() == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Terminal no seleccionada", "Debe seleccionar una terminal");
            return false;
        }

        if (cmbEstadoEmpresa.getSelectionModel().getSelectedIndex() == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Estado no seleccionado", "Debe seleccionar un estado");
            return false;
        }

        if (dateFechaFundacion.getValue() == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Fecha no seleccionada", "Debe seleccionar la fecha de fundación");
            return false;
        }

        if (txtDescripcion.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar una descripción");
            txtDescripcion.requestFocus();
            return false;
        }

        if (rutaImagenSeleccionada.isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Imagen no seleccionada", "Debe seleccionar una imagen");
            return false;
        }

        return true;
    }

    private void guardarEmpresa() {
        if (formularioCompleto()) {
            EmpresaDto dto = new EmpresaDto();
            dto.setNombreEmpresa(txtNombreEmpresa.getText());
            dto.setNitEmpresa(txtNitEmpresa.getText());
            dto.setTerminalEmpresa(cmbTerminalEmpresa.getValue());
            dto.setEstadoEmpresa(cmbEstadoEmpresa.getValue().equals("Activo"));
            dto.setCantidadBusesEmpresa((short) 0);
            dto.setNombreImagenPublicoEmpresa(txtImagen.getText());
            
            // NUEVOS CAMPOS
            dto.setFechaFundacion(dateFechaFundacion.getValue());
            dto.setCantidadEmpleados(spinnerEmpleados.getValue());
            dto.setServicio24Horas(chk24Horas.isSelected());
            dto.setTieneMantenimientoPropio(chkMantenimiento.isSelected());
            dto.setTieneServicioCliente(chkServicioCliente.isSelected());
            dto.setDescripcionEmpresa(txtDescripcion.getText());

            if (EmpresaControladorGrabar.crearEmpresa(dto, rutaImagenSeleccionada)) {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, this.getScene().getWindow(),
                        "Éxito", "Empresa creada correctamente");
                limpiarFormulario();
            } else {
                Mensaje.mostrar(Alert.AlertType.ERROR, this.getScene().getWindow(),
                        "Error", "No se pudo crear la empresa");
            }
        }
    }

    private void limpiarFormulario() {
        txtNombreEmpresa.clear();
        txtNitEmpresa.clear();
        txtImagen.clear();
        txtDescripcion.clear();
        cmbTerminalEmpresa.getSelectionModel().select(0);
        cmbEstadoEmpresa.getSelectionModel().select(0);
        dateFechaFundacion.setValue(LocalDate.now().minusYears(10));
        spinnerEmpleados.getValueFactory().setValue(50);
        chk24Horas.setSelected(false);
        chkMantenimiento.setSelected(false);
        chkServicioCliente.setSelected(false);
        rutaImagenSeleccionada = "";

        miGrilla.getChildren().remove(imgPrevisualizar);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        // La fila de previsualización es la 12
        miGrilla.add(imgPorDefecto, 1, 12);

        txtNombreEmpresa.requestFocus();
    }
}
