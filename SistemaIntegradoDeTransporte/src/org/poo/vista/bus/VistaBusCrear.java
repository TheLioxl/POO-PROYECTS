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

public class VistaBusCrear extends StackPane {

    private static final int H_GAP = 10;
    private static final int V_GAP = 12;
    private static final int ALTO_CAJA = 35;
    private static final int TAMANIO_FUENTE = 16;

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
    
    private TextField txtImagen;
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
                Configuracion.DEGRADE_ARREGLO_BUS,
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
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        
        col0.setPrefWidth(200);
        col1.setPrefWidth(200);
        col2.setPrefWidth(200);
        
        col1.setHgrow(Priority.ALWAYS);
        miGrilla.getColumnConstraints().addAll(col0, col1, col2);
    }

    private void crearTitulo() {
        int columna = 0, fila = 0, colSpan = 3, rowSpan = 1;

        Region espacioSuperior = new Region();
        espacioSuperior.prefHeightProperty().bind(
                miEscenario.heightProperty().multiply(0.02));
        miGrilla.add(espacioSuperior, columna, fila, colSpan, rowSpan);

        fila = 1;
        Text titulo = new Text("Formulario Creación de Bus");
        titulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        GridPane.setHalignment(titulo, HPos.CENTER);
        miGrilla.add(titulo, columna, fila, colSpan, rowSpan);
    }

    private void crearFormulario() {
        int fila = 2;
        int primeraColumna = 0;
        int segundaColumna = 1;

        // Campo 1: Placa del Bus (TextField)
        fila++;
        Label lblPlaca = new Label("Placa del Bus:");
        lblPlaca.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblPlaca, primeraColumna, fila);

        txtPlacaBus = new TextField();
        txtPlacaBus.setPromptText("Ej: ABC-123");
        txtPlacaBus.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtPlacaBus, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtPlacaBus, 10);
        miGrilla.add(txtPlacaBus, segundaColumna, fila);

        // Campo 2: Modelo del Bus (TextField)
        fila++;
        Label lblModelo = new Label("Modelo:");
        lblModelo.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblModelo, primeraColumna, fila);

        txtModeloBus = new TextField();
        txtModeloBus.setPromptText("Ej: Mercedes-Benz O500");
        txtModeloBus.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtModeloBus, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtModeloBus, 50);
        miGrilla.add(txtModeloBus, segundaColumna, fila);

        // Campo 3: Capacidad (Spinner)
        fila++;
        Label lblCapacidad = new Label("Capacidad:");
        lblCapacidad.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblCapacidad, primeraColumna, fila);

        spinnerCapacidad = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 60, 40);
        spinnerCapacidad.setValueFactory(valueFactory);
        spinnerCapacidad.setPrefHeight(ALTO_CAJA);
        spinnerCapacidad.setMaxWidth(Double.MAX_VALUE);
        spinnerCapacidad.setEditable(true);
        miGrilla.add(spinnerCapacidad, segundaColumna, fila);

        // Campo 4: Empresa (ComboBox)
        fila++;
        Label lblEmpresa = new Label("Empresa:");
        lblEmpresa.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEmpresa, primeraColumna, fila);

        cmbEmpresaBus = new ComboBox<>();
        cmbEmpresaBus.setMaxWidth(Double.MAX_VALUE);
        cmbEmpresaBus.setPrefHeight(ALTO_CAJA);
        
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
        
        miGrilla.add(cmbEmpresaBus, segundaColumna, fila);

        // Campo 5: Tipo de Bus (ComboBox)
        fila++;
        Label lblTipo = new Label("Tipo de Bus:");
        lblTipo.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblTipo, primeraColumna, fila);

        cmbTipoBus = new ComboBox<>();
        cmbTipoBus.setMaxWidth(Double.MAX_VALUE);
        cmbTipoBus.setPrefHeight(ALTO_CAJA);
        cmbTipoBus.getItems().addAll("Seleccione tipo", "Normal", "Ejecutivo", "VIP");
        cmbTipoBus.getSelectionModel().select(0);
        miGrilla.add(cmbTipoBus, segundaColumna, fila);

        // Campo 6: Fecha de Adquisición (DatePicker)
        fila++;
        Label lblFechaAdquisicion = new Label("Fecha Adquisición:");
        lblFechaAdquisicion.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblFechaAdquisicion, primeraColumna, fila);

        dateFechaAdquisicion = new DatePicker();
        dateFechaAdquisicion.setMaxWidth(Double.MAX_VALUE);
        dateFechaAdquisicion.setPrefHeight(ALTO_CAJA);
        dateFechaAdquisicion.setPromptText("Seleccione fecha");
        dateFechaAdquisicion.setValue(LocalDate.now().minusYears(5));
        Formulario.deshabilitarFechasFuturas(dateFechaAdquisicion);
        miGrilla.add(dateFechaAdquisicion, segundaColumna, fila);

        // Campo 7: Servicios (CheckBox)
        fila++;
        Label lblServicios = new Label("Servicios:");
        lblServicios.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblServicios, primeraColumna, fila);

        chkAireAcondicionado = new CheckBox("Aire Acondicionado");
        chkWifi = new CheckBox("WiFi");
        chkBano = new CheckBox("Baño");
        
        chkAireAcondicionado.setFont(Font.font("Arial", 13));
        chkWifi.setFont(Font.font("Arial", 13));
        chkBano.setFont(Font.font("Arial", 13));

        VBox vboxServicios = new VBox(3);
        vboxServicios.getChildren().addAll(chkAireAcondicionado, chkWifi, chkBano);
        miGrilla.add(vboxServicios, segundaColumna, fila);

        // Campo 8: Descripción (TextArea)
        fila++;
        Label lblDescripcion = new Label("Descripción:");
        lblDescripcion.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDescripcion, primeraColumna, fila);

        txtDescripcion = new TextArea();
        txtDescripcion.setPromptText("Breve descripción del bus...");
        txtDescripcion.setPrefRowCount(2);
        txtDescripcion.setWrapText(true);
        txtDescripcion.setMaxWidth(Double.MAX_VALUE);
        miGrilla.add(txtDescripcion, segundaColumna, fila);

        // Campo 9: Estado (ComboBox)
        fila++;
        Label lblEstado = new Label("Estado:");
        lblEstado.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEstado, primeraColumna, fila);

        cmbEstadoBus = new ComboBox<>();
        cmbEstadoBus.setMaxWidth(Double.MAX_VALUE);
        cmbEstadoBus.setPrefHeight(ALTO_CAJA);
        cmbEstadoBus.getItems().addAll("Seleccione estado", "Activo", "Inactivo");
        cmbEstadoBus.getSelectionModel().select(0);
        miGrilla.add(cmbEstadoBus, segundaColumna, fila);

        // Campo 10: Imagen
        fila++;
        Label lblImagen = new Label("Imagen:");
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

        HBox.setHgrow(txtImagen, Priority.ALWAYS);
        HBox panelImagen = new HBox(2, txtImagen, btnSeleccionarImagen);
        panelImagen.setAlignment(Pos.BOTTOM_RIGHT);
        miGrilla.add(panelImagen, segundaColumna, fila);

        // Previsualización de imagen en columna derecha
        imgPorDefecto = Icono.obtenerIcono(Configuracion.ICONO_NO_DISPONIBLE, 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        GridPane.setValignment(imgPorDefecto, javafx.geometry.VPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 1, 1, 11);

        // Botón Crear
        fila++;
        Button btnGrabar = new Button("Crear Bus");
        btnGrabar.setPrefHeight(ALTO_CAJA);
        btnGrabar.setMaxWidth(Double.MAX_VALUE);
        btnGrabar.setTextFill(Color.web("#FFFFFF"));
        btnGrabar.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        btnGrabar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + ";");
        btnGrabar.setOnAction(e -> guardarBus());
        miGrilla.add(btnGrabar, segundaColumna, fila);
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
            dto.setNombreImagenPublicoBus(txtImagen.getText());

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
        txtImagen.clear();
        rutaImagenSeleccionada = "";

        miGrilla.getChildren().remove(imgPrevisualizar);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        miGrilla.add(imgPorDefecto, 1, 11);

        txtPlacaBus.requestFocus();
    }
}
