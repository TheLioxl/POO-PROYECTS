package org.poo.vista.bus;

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
import org.poo.controlador.bus.BusControladorGrabar;
import org.poo.controlador.empresa.EmpresaControladorListar;
import org.poo.dto.BusDto;
import org.poo.dto.EmpresaDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.*;

import java.time.LocalDate;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Cursor;

public class VistaBusCrear extends StackPane {

    private static final int H_GAP = 10;
    private static final int V_GAP = 20;
    private static final int ALTO_FILA = 33;
    private static final int ALTO_CAJA = 30;
    private static final int TAMANIO_FUENTE = 20;
    private static final double AJUSTE_TITULO = 0.1;

    private final GridPane miGrilla;
    private final Rectangle miMarco;
    private final Stage miEscenario;

    // Tipo 1: TextField - para placa y modelo
    private TextField txtPlacaBus;
    private TextField txtModeloBus;
    
    // Tipo 2: Spinner - para capacidad
    private Spinner<Integer> spinnerCapacidad;
    
    // Tipo 3: ComboBox - para empresa, tipo de bus y estado
    private ComboBox<EmpresaDto> cmbEmpresaBus;
    private ComboBox<String> cmbTipoBus;
    private ComboBox<String> cmbEstadoBus;
    
    // Tipo 4: DatePicker - para fecha de adquisición
    private DatePicker dateFechaAdquisicion;
    
    // Tipo 5: CheckBox - para servicios del bus
    private CheckBox chkAireAcondicionado;
    private CheckBox chkWifi;
    private CheckBox chkBano;
    
    // Tipo 6: TextArea - para descripción
    private TextArea txtDescripcion;
    
    private TextField cajaImagen;
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private String rutaImagenSeleccionada;

    public VistaBusCrear(Stage escenario, double ancho, double alto) {
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
        Text miTitulo = new Text("FORMULARIO - CREAR BUS");
        miTitulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        miTitulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 28));
        GridPane.setHalignment(miTitulo, HPos.CENTER);
        GridPane.setMargin(miTitulo, new Insets(30, 0, 0, 0));
        miGrilla.add(miTitulo, 0, 0, 3, 2);//nombre, columna, fila, union de filas 
    }

    private void crearFormulario() {

        // Campo 1: Placa del Bus (TextField)
        Label lblPlaca = new Label("Placa del Bus:");
        lblPlaca.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblPlaca, 0, 2);

        txtPlacaBus = new TextField();
        txtPlacaBus.setPromptText("Ej: ABC-123");
        txtPlacaBus.setPrefHeight(ALTO_CAJA);
        txtPlacaBus.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        txtPlacaBus.setMinHeight(Region.USE_PREF_SIZE);
        GridPane.setHgrow(txtPlacaBus, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtPlacaBus, 10);
        miGrilla.add(txtPlacaBus, 1, 2);

        // Campo 2: Modelo del Bus (TextField)
        Label lblModelo = new Label("Modelo:");
        lblModelo.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblModelo, 0, 3);

        txtModeloBus = new TextField();
        txtModeloBus.setPromptText("Ej: Mercedes-Benz O500");
        txtModeloBus.setPrefHeight(ALTO_CAJA);
        txtModeloBus.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        txtModeloBus.setMinHeight(Region.USE_PREF_SIZE);
        GridPane.setHgrow(txtModeloBus, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtModeloBus, 50);
        miGrilla.add(txtModeloBus, 1, 3);

        // Campo 3: Capacidad (Spinner)
        Label lblCapacidad = new Label("Capacidad:");
        lblCapacidad.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblCapacidad, 0, 4);

        spinnerCapacidad = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 60, 40);
        spinnerCapacidad.setValueFactory(valueFactory);
        spinnerCapacidad.setPrefHeight(ALTO_CAJA);
        spinnerCapacidad.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        spinnerCapacidad.setMinHeight(Region.USE_PREF_SIZE);
        spinnerCapacidad.setEditable(true);
        miGrilla.add(spinnerCapacidad, 1, 4);

        // Campo 4: Empresa (ComboBox)
        Label lblEmpresa = new Label("Empresa:");
        lblEmpresa.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEmpresa, 0, 5);

        cmbEmpresaBus = new ComboBox<>();
        cmbEmpresaBus.setMaxWidth(Double.MAX_VALUE);
        cmbEmpresaBus.setPrefHeight(ALTO_CAJA);
        cmbEmpresaBus.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        cmbEmpresaBus.setMinHeight(Region.USE_PREF_SIZE);
        
        List<EmpresaDto> empresas = EmpresaControladorListar.obtenerEmpresasActivas();
        EmpresaDto opcionDefault = new EmpresaDto();
        opcionDefault.setIdEmpresa(0);
        opcionDefault.setNombreEmpresa("Seleccione empresa");
        
        cmbEmpresaBus.getItems().add(opcionDefault);
        cmbEmpresaBus.getItems().addAll(empresas);
        cmbEmpresaBus.getSelectionModel().select(0);
        
        cmbEmpresaBus.setConverter(new StringConverter<EmpresaDto>() {
            @Override
            public String toString(EmpresaDto empresa) {
                return empresa != null ? empresa.getNombreEmpresa() : "";
            }

            @Override
            public EmpresaDto fromString(String string) {
                return null;
            }
        });
        
        miGrilla.add(cmbEmpresaBus, 1, 5);

        // Campo 5: Tipo de Bus (ComboBox)
        Label lblTipo = new Label("Tipo de Bus:");
        lblTipo.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblTipo, 0, 6);

        cmbTipoBus = new ComboBox<>();
        cmbTipoBus.setMaxWidth(Double.MAX_VALUE);
        cmbTipoBus.setPrefHeight(ALTO_CAJA);
        cmbTipoBus.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        cmbTipoBus.setMinHeight(Region.USE_PREF_SIZE);
        cmbTipoBus.getItems().addAll("Seleccione tipo", "Normal", "Ejecutivo", "VIP");
        cmbTipoBus.getSelectionModel().select(0);
        miGrilla.add(cmbTipoBus, 1, 6);

        // Campo 6: Fecha de Adquisición (DatePicker)
        Label lblFechaAdquisicion = new Label("Fecha Adquisición:");
        lblFechaAdquisicion.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblFechaAdquisicion, 0, 7);

        dateFechaAdquisicion = new DatePicker();
        dateFechaAdquisicion.setMaxWidth(Double.MAX_VALUE);
        dateFechaAdquisicion.setPrefHeight(ALTO_CAJA);
        dateFechaAdquisicion.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        dateFechaAdquisicion.setMinHeight(Region.USE_PREF_SIZE);
        dateFechaAdquisicion.setPromptText("Seleccione fecha");
        dateFechaAdquisicion.setValue(LocalDate.now().minusYears(5));
        Formulario.deshabilitarFechasFuturas(dateFechaAdquisicion);
        miGrilla.add(dateFechaAdquisicion, 1, 7);

        // Campo 7: Servicios (CheckBox)
        Label lblServicios = new Label("Servicios:");
        lblServicios.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblServicios, 0, 8);

        chkAireAcondicionado = new CheckBox("Aire Acondicionado");
        chkWifi = new CheckBox("WiFi");
        chkBano = new CheckBox("Baño");
        
        chkAireAcondicionado.setFont(Font.font("Times new roman", 13));
        chkWifi.setFont(Font.font("Times new roman", 13));
        chkBano.setFont(Font.font("Times new roman", 13));

        VBox vboxServicios = new VBox(3);
        vboxServicios.getChildren().addAll(chkAireAcondicionado, chkWifi, chkBano);
        miGrilla.add(vboxServicios, 1, 8);

        // Campo 8: Descripción (TextArea)
        Label lblDescripcion = new Label("Descripción:");
        lblDescripcion.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDescripcion, 0, 9);

        txtDescripcion = new TextArea();
        txtDescripcion.setPromptText("Breve descripción del bus...");
        txtDescripcion.setPrefRowCount(2);
        txtDescripcion.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        txtDescripcion.setMaxHeight(Double.MAX_VALUE);
        txtDescripcion.setWrapText(true);
        miGrilla.add(txtDescripcion, 1, 9);

        // Campo 9: Estado (ComboBox)
        Label lblEstado = new Label("Estado:");
        lblEstado.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEstado, 0, 10);

        cmbEstadoBus = new ComboBox<>();
        cmbEstadoBus.setMaxWidth(Double.MAX_VALUE);
        cmbEstadoBus.setPrefHeight(ALTO_CAJA);
        cmbEstadoBus.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        cmbEstadoBus.setMinHeight(Region.USE_PREF_SIZE);
        cmbEstadoBus.getItems().addAll("Seleccione estado", "Activo", "Inactivo");
        cmbEstadoBus.getSelectionModel().select(0);
        miGrilla.add(cmbEstadoBus, 1, 10);

        // Campo 10: Imagen
        Label lblImagen = new Label("Imagen:");
        lblImagen.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblImagen, 0, 11);

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
                miGrilla.add(imgPorDefecto, 2, 5);
            }else{
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                imgPrevisualizar = Icono.previsualizar(rutaImagenSeleccionada, 150);
                GridPane.setHalignment(imgPrevisualizar, HPos.CENTER);
                miGrilla.add(imgPrevisualizar, 2, 5);
            }
        });

        HBox.setHgrow(cajaImagen, Priority.ALWAYS);
        HBox panelImagen = new HBox(5, cajaImagen, btnSeleccionarImagen);
        miGrilla.add(panelImagen, 1, 11);

        // Previsualización de imagen
        imgPorDefecto = Icono.obtenerIcono(Configuracion.ICONO_NO_DISPONIBLE, 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 5);

        // Botón Crear
        Button btnGrabar = new Button("GRABAR BUS");
        btnGrabar.setPrefHeight(ALTO_CAJA);
        btnGrabar.setMaxWidth(Double.MAX_VALUE);
        btnGrabar.setTextFill(Color.web("#FFFFFF"));
        btnGrabar.setFont(Font.font("Rockwell", FontWeight.BOLD, 14));
        btnGrabar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + ";" 
                + "-fx-border-radius: 8; -fx-background-radius: 8;");
        btnGrabar.setCursor(Cursor.HAND);
        btnGrabar.setOnAction(e -> guardarBus());
        miGrilla.add(btnGrabar, 1, 12);
    }

    private Boolean formularioCompleto() {
        if (txtPlacaBus.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar la placa del bus");
            txtPlacaBus.requestFocus();
            return false;
        }

        if (txtModeloBus.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar el modelo del bus");
            txtModeloBus.requestFocus();
            return false;
        }

        if (cmbEmpresaBus.getSelectionModel().getSelectedIndex() == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Empresa no seleccionada", "Debe seleccionar una empresa");
            return false;
        }

        if (cmbTipoBus.getSelectionModel().getSelectedIndex() == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Tipo no seleccionado", "Debe seleccionar un tipo de bus");
            return false;
        }

        if (dateFechaAdquisicion.getValue() == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Fecha no seleccionada", "Debe seleccionar la fecha de adquisición");
            return false;
        }

        if (txtDescripcion.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar una descripción");
            txtDescripcion.requestFocus();
            return false;
        }

        if (cmbEstadoBus.getSelectionModel().getSelectedIndex() == 0) {
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

    private void guardarBus() {
        if (formularioCompleto()) {
            BusDto dto = new BusDto();
            dto.setPlacaBus(txtPlacaBus.getText());
            dto.setModeloBus(txtModeloBus.getText());
            dto.setCapacidadBus(spinnerCapacidad.getValue());
            dto.setEmpresaBus(cmbEmpresaBus.getValue());
            dto.setTipoBus(cmbTipoBus.getValue());
            dto.setEstadoBus(cmbEstadoBus.getValue().equals("Activo"));
            dto.setNombreImagenPublicoBus(cajaImagen.getText());

            if (BusControladorGrabar.crearBus(dto, rutaImagenSeleccionada)) {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, this.getScene().getWindow(),
                        "Éxito", "Bus creado correctamente");
                limpiarFormulario();
            } else {
                Mensaje.mostrar(Alert.AlertType.ERROR, this.getScene().getWindow(),
                        "Error", "No se pudo crear el bus");
            }
        }
    }

    private void limpiarFormulario() {
        txtPlacaBus.clear();
        txtModeloBus.clear();
        spinnerCapacidad.getValueFactory().setValue(40);
        cmbEmpresaBus.getSelectionModel().select(0);
        cmbTipoBus.getSelectionModel().select(0);
        dateFechaAdquisicion.setValue(LocalDate.now().minusYears(5));
        chkAireAcondicionado.setSelected(false);
        chkWifi.setSelected(false);
        chkBano.setSelected(false);
        txtDescripcion.clear();
        cmbEstadoBus.getSelectionModel().select(0);
        cajaImagen.clear();
        rutaImagenSeleccionada = "";

        miGrilla.getChildren().remove(imgPrevisualizar);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 5);

        txtPlacaBus.requestFocus();
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
