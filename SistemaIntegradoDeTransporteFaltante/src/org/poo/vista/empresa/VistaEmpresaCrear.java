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
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Cursor;

public class VistaEmpresaCrear extends StackPane {

    private static final int H_GAP = 10;
    private static final int V_GAP = 20;
    private static final int ALTO_FILA = 38;
    private static final int ALTO_CAJA = 32;
    private static final int TAMANIO_FUENTE = 20;
    private static final double AJUSTE_TITULO = 0.1;

    private final GridPane miGrilla;
    private final Rectangle miMarco;
    private final Stage miEscenario;

    private TextField txtNombreEmpresa;
    private TextField txtNitEmpresa;
    private ComboBox<TerminalDto> cmbTerminalEmpresa;
    private ComboBox<String> cmbEstadoEmpresa;
    private DatePicker dateFechaFundacion;
    private Spinner<Integer> spinnerEmpleados;
    private CheckBox chk24Horas;
    private CheckBox chkMantenimiento;
    private CheckBox chkServicioCliente;
    private TextArea txtDescripcion;
    
    private TextField cajaImagen;
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
                Configuracion.DEGRADE_ARREGLO_TERMINAL,
                Configuracion.DEGRADE_BORDE
        );

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
        Text miTitulo = new Text("FORMULARIO - CREAR EMPRESA");
        miTitulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        miTitulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 28));
        GridPane.setHalignment(miTitulo, HPos.CENTER);
        GridPane.setMargin(miTitulo, new Insets(30, 0, 0, 0));
        miGrilla.add(miTitulo, 0, 0, 3, 2);//nombre, columna, fila, union de filas 
    }

    private void crearFormulario() {
        
        //NOMBRE
        Label lblNombre = new Label("Nombre Empresa:");
        lblNombre.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblNombre, 0, 2);

        txtNombreEmpresa = new TextField();
        txtNombreEmpresa.setPromptText("Ej: Expreso Brasilia");
        txtNombreEmpresa.setPrefHeight(ALTO_CAJA);
        txtNombreEmpresa.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        txtNombreEmpresa.setMinHeight(Region.USE_PREF_SIZE);
        GridPane.setHgrow(txtNombreEmpresa, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtNombreEmpresa, 50);
        miGrilla.add(txtNombreEmpresa, 1, 2);

        Label lblNit = new Label("NIT:");
        lblNit.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblNit, 0, 3);

        txtNitEmpresa = new TextField();
        txtNitEmpresa.setPromptText("Ej: 900123456-7");
        txtNitEmpresa.setPrefHeight(ALTO_CAJA);
        txtNitEmpresa.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        txtNitEmpresa.setMinHeight(Region.USE_PREF_SIZE);
        GridPane.setHgrow(txtNitEmpresa, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtNitEmpresa, 20);
        miGrilla.add(txtNitEmpresa, 1, 3);

        Label lblTerminal = new Label("Terminal:");
        lblTerminal.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblTerminal, 0, 4);
        
        cmbTerminalEmpresa = new ComboBox<>();
        cmbTerminalEmpresa.setMaxWidth(Double.MAX_VALUE);
        cmbTerminalEmpresa.setPrefHeight(ALTO_CAJA);
        cmbTerminalEmpresa.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        cmbTerminalEmpresa.setMinHeight(Region.USE_PREF_SIZE);

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
        
        miGrilla.add(cmbTerminalEmpresa, 1, 4);

        Label lblEstado = new Label("Estado:");
        lblEstado.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEstado, 0, 5);

        cmbEstadoEmpresa = new ComboBox<>();
        cmbEstadoEmpresa.setMaxWidth(Double.MAX_VALUE);
        cmbEstadoEmpresa.setPrefHeight(ALTO_CAJA);
        cmbEstadoEmpresa.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        cmbEstadoEmpresa.setMinHeight(Region.USE_PREF_SIZE);
        cmbEstadoEmpresa.getItems().addAll("Seleccione estado", "Activo", "Inactivo");
        cmbEstadoEmpresa.getSelectionModel().select(0);
        miGrilla.add(cmbEstadoEmpresa, 1, 5);


        Label lblFechaFundacion = new Label("Fecha Fundación:");
        lblFechaFundacion.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblFechaFundacion, 0, 6);

        dateFechaFundacion = new DatePicker();
        dateFechaFundacion.setMaxWidth(Double.MAX_VALUE);
        dateFechaFundacion.setPrefHeight(ALTO_CAJA);
        dateFechaFundacion.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        dateFechaFundacion.setMinHeight(Region.USE_PREF_SIZE);
        dateFechaFundacion.setPromptText("Seleccione fecha");
        dateFechaFundacion.setValue(LocalDate.now().minusYears(10));
        Formulario.deshabilitarFechasFuturas(dateFechaFundacion);
        miGrilla.add(dateFechaFundacion, 1, 6);


        Label lblEmpleados = new Label("Cant. Empleados:");
        lblEmpleados.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEmpleados, 0, 7);

        spinnerEmpleados = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 50);
        spinnerEmpleados.setValueFactory(valueFactory);
        spinnerEmpleados.setPrefHeight(ALTO_CAJA);
        spinnerEmpleados.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        spinnerEmpleados.setMinHeight(Region.USE_PREF_SIZE);
        spinnerEmpleados.setEditable(true);
        miGrilla.add(spinnerEmpleados, 1, 7);

        
        Label lblServicios = new Label("Servicios:");
        lblServicios.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblServicios, 0, 8);

        chk24Horas = new CheckBox("Servicio 24 Horas");
        chkMantenimiento = new CheckBox("Mantenimiento Propio");
        chkServicioCliente = new CheckBox("Atención al Cliente");
        
        chk24Horas.setFont(Font.font("Times new roman", 13));
        chkMantenimiento.setFont(Font.font("Times new roman", 13));
        chkServicioCliente.setFont(Font.font("Times new roman", 13));

        VBox vboxServicios = new VBox(3);
        vboxServicios.getChildren().addAll(chk24Horas, chkMantenimiento, chkServicioCliente);
        miGrilla.add(vboxServicios, 1, 8);
        

        Label lblDescripcion = new Label("Descripción:");
        lblDescripcion.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDescripcion, 0, 9);

        txtDescripcion = new TextArea();
        txtDescripcion.setPromptText("Breve descripción de la empresa...");
        txtDescripcion.setPrefRowCount(2);
        txtDescripcion.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        txtDescripcion.setMinHeight(Region.USE_PREF_SIZE);
        txtDescripcion.setWrapText(true);
        miGrilla.add(txtDescripcion, 1, 9);


        Label lblImagen = new Label("Logo/Imagen:");
        lblImagen.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblImagen, 0, 10);

        cajaImagen = new TextField();
        cajaImagen.setDisable(true);
        cajaImagen.setPrefHeight(ALTO_CAJA);
        cajaImagen.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        cajaImagen.setMinHeight(Region.USE_PREF_SIZE);

        String[] extensiones = {"*.png", "*.jpg", "*.jpeg"};
        FileChooser objSeleccionar = Formulario.selectorImagen(
                "Seleccionar imagen", "Imágenes", extensiones);

        Button btnSeleccionarImagen = new Button("+");
        btnSeleccionarImagen.setPrefHeight(ALTO_CAJA);
        
        btnSeleccionarImagen.setOnAction((e)->{
            rutaImagenSeleccionada = GestorImagen.obtenerRutaImagen(cajaImagen, objSeleccionar);
            if (rutaImagenSeleccionada.isEmpty()) {
                //Solo es para crear NO para actualizar
                //OJOOOOOOOOOOOOO
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                miGrilla.add(imgPorDefecto, 2, 1, 1, 10);
            }else{
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
        miGrilla.add(imgPorDefecto, 2, 5);

        
        Button btnGrabar = new Button("GRABAR EMPRESA");
        btnGrabar.setPrefHeight(ALTO_CAJA);
        btnGrabar.setMaxWidth(Double.MAX_VALUE);
        btnGrabar.setTextFill(Color.web("#FFFFFF"));
        btnGrabar.setFont(Font.font("Rockwell", FontWeight.BOLD, 14));
        btnGrabar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + ";" 
                + "-fx-border-radius: 8; -fx-background-radius: 8;");
        btnGrabar.setCursor(Cursor.HAND);
        btnGrabar.setOnAction(e -> guardarEmpresa());
        miGrilla.add(btnGrabar, 1, 11);
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
            dto.setNombreImagenPublicoEmpresa(cajaImagen.getText());
            
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
        cajaImagen.clear();
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
        miGrilla.add(imgPorDefecto, 2, 5);

        txtNombreEmpresa.requestFocus();
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
