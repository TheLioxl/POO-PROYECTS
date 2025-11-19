package org.poo.vista.viaje;

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
import org.poo.controlador.bus.BusControladorListar;
import org.poo.controlador.conductor.ConductorControladorListar;
import org.poo.controlador.ruta.RutaControladorListar;
import org.poo.controlador.viaje.ViajeControladorGrabar;
import org.poo.dto.BusDto;
import org.poo.dto.ConductorDto;
import org.poo.dto.RutaDto;
import org.poo.dto.ViajeDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class VistaViajeCrear extends StackPane {

    private static final int H_GAP = 10;
    private static final int V_GAP = 12;
    private static final int ALTO_CAJA = 35;
    private static final int TAMANIO_FUENTE = 16;

    private final GridPane miGrilla;
    private final Rectangle miMarco;
    private final Stage miEscenario;
    private ComboBox<BusDto> cmbBusViaje;
    private ComboBox<RutaDto> cmbRutaViaje;
    private ComboBox<ConductorDto> cmbConductorViaje;
    private ComboBox<String> cmbEstadoViaje;
    private DatePicker dateFechaViaje;
    private Spinner<Integer> spinnerHoraSalida;
    private Spinner<Integer> spinnerMinutoSalida;
    private Spinner<Integer> spinnerHoraLlegada;
    private Spinner<Integer> spinnerMinutoLlegada;
    private TextField txtPrecio;
    private TextField txtDescripcion;
    private Spinner<Integer> spinnerAsientos;
    private CheckBox chkViajeDirecto;
    private CheckBox chkIncluyeRefrigerio;
    private CheckBox chkParadasIntermedias;
    private TextArea txtNotasAdicionales;
    
    private TextField txtImagen;
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private String rutaImagenSeleccionada;

    public VistaViajeCrear(Stage escenario, double ancho, double alto) {
        miEscenario = escenario;
        rutaImagenSeleccionada = "";
        setAlignment(Pos.CENTER);

        miGrilla = new GridPane();
        miMarco = Marco.crear(
                miEscenario,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_VIAJE,
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
        Text titulo = new Text("Formulario Creación de Viaje");
        titulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        GridPane.setHalignment(titulo, HPos.CENTER);
        miGrilla.add(titulo, columna, fila, colSpan, rowSpan);
    }

    private void crearFormulario() {
        int fila = 2;
        int primeraColumna = 0;
        int segundaColumna = 1;

        fila++;
        Label lblBus = new Label("Bus:");
        lblBus.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblBus, primeraColumna, fila);

        cmbBusViaje = new ComboBox<>();
        cmbBusViaje.setMaxWidth(Double.MAX_VALUE);
        cmbBusViaje.setPrefHeight(ALTO_CAJA);
        
        List<BusDto> buses = BusControladorListar.obtenerBusesActivos();
        BusDto busDefault = new BusDto();
        busDefault.setIdBus(0);
        busDefault.setPlacaBus("Seleccione un bus");
        
        cmbBusViaje.getItems().add(busDefault);
        cmbBusViaje.getItems().addAll(buses);
        cmbBusViaje.getSelectionModel().select(0);
        
        cmbBusViaje.setConverter(new StringConverter<BusDto>() {
            @Override
            public String toString(BusDto bus) {
                return bus != null ? bus.toString() : "";
            }
            @Override
            public BusDto fromString(String string) {
                return null;
            }
        });
        
        miGrilla.add(cmbBusViaje, segundaColumna, fila);

        fila++;
        Label lblRuta = new Label("Ruta:");
        lblRuta.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblRuta, primeraColumna, fila);

        cmbRutaViaje = new ComboBox<>();
        cmbRutaViaje.setMaxWidth(Double.MAX_VALUE);
        cmbRutaViaje.setPrefHeight(ALTO_CAJA);
        
        List<RutaDto> rutas = RutaControladorListar.obtenerRutasActivas();
        RutaDto rutaDefault = new RutaDto();
        rutaDefault.setIdRuta(0);
        rutaDefault.setNombreRuta("Seleccione una ruta");
        
        cmbRutaViaje.getItems().add(rutaDefault);
        cmbRutaViaje.getItems().addAll(rutas);
        cmbRutaViaje.getSelectionModel().select(0);
        
        cmbRutaViaje.setConverter(new StringConverter<RutaDto>() {
            @Override
            public String toString(RutaDto ruta) {
                return ruta != null ? ruta.toString() : "";
            }
            @Override
            public RutaDto fromString(String string) {
                return null;
            }
        });
        
        miGrilla.add(cmbRutaViaje, segundaColumna, fila);

        fila++;
        Label lblConductor = new Label("Conductor:");
        lblConductor.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblConductor, primeraColumna, fila);

        cmbConductorViaje = new ComboBox<>();
        cmbConductorViaje.setMaxWidth(Double.MAX_VALUE);
        cmbConductorViaje.setPrefHeight(ALTO_CAJA);
        
        List<ConductorDto> conductores = ConductorControladorListar.obtenerConductoresActivos();
        ConductorDto conductorDefault = new ConductorDto();
        conductorDefault.setIdConductor(0);
        conductorDefault.setNombreConductor("Seleccione un conductor");
        
        cmbConductorViaje.getItems().add(conductorDefault);
        cmbConductorViaje.getItems().addAll(conductores);
        cmbConductorViaje.getSelectionModel().select(0);
        
        cmbConductorViaje.setConverter(new StringConverter<ConductorDto>() {
            @Override
            public String toString(ConductorDto conductor) {
                if (conductor == null) return "";
                if (conductor.getIdConductor() == 0) return conductor.getNombreConductor();
                return conductor.getNombreConductor() != null ? conductor.getNombreConductor() : "Conductor sin nombre";
            }
            @Override
            public ConductorDto fromString(String string) {
                return null;
            }
        });
        
        miGrilla.add(cmbConductorViaje, segundaColumna, fila);

        fila++;
        Label lblFecha = new Label("Fecha del Viaje:");
        lblFecha.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblFecha, primeraColumna, fila);

        dateFechaViaje = new DatePicker();
        dateFechaViaje.setMaxWidth(Double.MAX_VALUE);
        dateFechaViaje.setPrefHeight(ALTO_CAJA);
        dateFechaViaje.setPromptText("Seleccione fecha");
        dateFechaViaje.setValue(LocalDate.now());
        Formulario.deshabilitarFechasPasadas(dateFechaViaje);
        miGrilla.add(dateFechaViaje, segundaColumna, fila);

        fila++;
        Label lblHoraSalida = new Label("Hora de Salida:");
        lblHoraSalida.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblHoraSalida, primeraColumna, fila);

        spinnerHoraSalida = new Spinner<>();
        SpinnerValueFactory<Integer> horaFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 8);
        spinnerHoraSalida.setValueFactory(horaFactory);
        spinnerHoraSalida.setPrefHeight(ALTO_CAJA);
        spinnerHoraSalida.setEditable(true);

        spinnerMinutoSalida = new Spinner<>();
        SpinnerValueFactory<Integer> minutoFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        spinnerMinutoSalida.setValueFactory(minutoFactory);
        spinnerMinutoSalida.setPrefHeight(ALTO_CAJA);
        spinnerMinutoSalida.setEditable(true);

        HBox horaSalidaBox = new HBox(5);
        HBox.setHgrow(spinnerHoraSalida, Priority.ALWAYS);
        HBox.setHgrow(spinnerMinutoSalida, Priority.ALWAYS);
        spinnerHoraSalida.setMaxWidth(Double.MAX_VALUE);
        spinnerMinutoSalida.setMaxWidth(Double.MAX_VALUE);
        horaSalidaBox.getChildren().addAll(
            spinnerHoraSalida, new Label(":"), spinnerMinutoSalida
        );
        miGrilla.add(horaSalidaBox, segundaColumna, fila);

        fila++;
        Label lblHoraLlegada = new Label("Hora de Llegada:");
        lblHoraLlegada.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblHoraLlegada, primeraColumna, fila);

        spinnerHoraLlegada = new Spinner<>();
        SpinnerValueFactory<Integer> horaLlegadaFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 12);
        spinnerHoraLlegada.setValueFactory(horaLlegadaFactory);
        spinnerHoraLlegada.setPrefHeight(ALTO_CAJA);
        spinnerHoraLlegada.setEditable(true);

        spinnerMinutoLlegada = new Spinner<>();
        SpinnerValueFactory<Integer> minutoLlegadaFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        spinnerMinutoLlegada.setValueFactory(minutoLlegadaFactory);
        spinnerMinutoLlegada.setPrefHeight(ALTO_CAJA);
        spinnerMinutoLlegada.setEditable(true);

        HBox horaLlegadaBox = new HBox(5);
        HBox.setHgrow(spinnerHoraLlegada, Priority.ALWAYS);
        HBox.setHgrow(spinnerMinutoLlegada, Priority.ALWAYS);
        spinnerHoraLlegada.setMaxWidth(Double.MAX_VALUE);
        spinnerMinutoLlegada.setMaxWidth(Double.MAX_VALUE);
        horaLlegadaBox.getChildren().addAll(
            spinnerHoraLlegada, new Label(":"), spinnerMinutoLlegada
        );
        miGrilla.add(horaLlegadaBox, segundaColumna, fila);

        fila++;
        Label lblPrecio = new Label("Precio:");
        lblPrecio.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblPrecio, primeraColumna, fila);

        txtPrecio = new TextField();
        txtPrecio.setPromptText("Ej: 45000.00");
        txtPrecio.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtPrecio, Priority.ALWAYS);
        Formulario.soloDecimales(txtPrecio);
        miGrilla.add(txtPrecio, segundaColumna, fila);

        fila++;
        Label lblAsientos = new Label("Asientos Disponibles:");
        lblAsientos.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblAsientos, primeraColumna, fila);

        spinnerAsientos = new Spinner<>();
        SpinnerValueFactory<Integer> asientosFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 60, 40);
        spinnerAsientos.setValueFactory(asientosFactory);
        spinnerAsientos.setPrefHeight(ALTO_CAJA);
        spinnerAsientos.setMaxWidth(Double.MAX_VALUE);
        spinnerAsientos.setEditable(true);
        miGrilla.add(spinnerAsientos, segundaColumna, fila);

        fila++;
        Label lblCaracteristicas = new Label("Características:");
        lblCaracteristicas.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblCaracteristicas, primeraColumna, fila);

        chkViajeDirecto = new CheckBox("Viaje Directo");
        chkIncluyeRefrigerio = new CheckBox("Incluye Refrigerio");
        chkParadasIntermedias = new CheckBox("Paradas Intermedias");
        
        chkViajeDirecto.setFont(Font.font("Arial", 13));
        chkIncluyeRefrigerio.setFont(Font.font("Arial", 13));
        chkParadasIntermedias.setFont(Font.font("Arial", 13));

        VBox vboxCaracteristicas = new VBox(3);
        vboxCaracteristicas.getChildren().addAll(
            chkViajeDirecto, chkIncluyeRefrigerio, chkParadasIntermedias
        );
        miGrilla.add(vboxCaracteristicas, segundaColumna, fila);

        fila++;
        Label lblDescripcion = new Label("Descripción:");
        lblDescripcion.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDescripcion, primeraColumna, fila);

        txtDescripcion = new TextField();
        txtDescripcion.setPromptText("Descripción breve del viaje");
        txtDescripcion.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtDescripcion, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtDescripcion, 100);
        miGrilla.add(txtDescripcion, segundaColumna, fila);

        fila++;
        Label lblNotas = new Label("Notas Adicionales:");
        lblNotas.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblNotas, primeraColumna, fila);

        txtNotasAdicionales = new TextArea();
        txtNotasAdicionales.setPromptText("Notas u observaciones del viaje...");
        txtNotasAdicionales.setPrefRowCount(2);
        txtNotasAdicionales.setWrapText(true);
        txtNotasAdicionales.setMaxWidth(Double.MAX_VALUE);
        miGrilla.add(txtNotasAdicionales, segundaColumna, fila);

        fila++;
        Label lblEstado = new Label("Estado:");
        lblEstado.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEstado, primeraColumna, fila);

        cmbEstadoViaje = new ComboBox<>();
        cmbEstadoViaje.setMaxWidth(Double.MAX_VALUE);
        cmbEstadoViaje.setPrefHeight(ALTO_CAJA);
        cmbEstadoViaje.getItems().addAll("Seleccione estado", "Activo", "Inactivo");
        cmbEstadoViaje.getSelectionModel().select(0);
        miGrilla.add(cmbEstadoViaje, segundaColumna, fila);

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
                miGrilla.add(imgPorDefecto, 2, 1, 1, 16);
            } else {
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                imgPrevisualizar = Icono.previsualizar(rutaImagenSeleccionada, 150);
                GridPane.setHalignment(imgPrevisualizar, HPos.CENTER);
                miGrilla.add(imgPrevisualizar, 2, 1, 1, 16);
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
        miGrilla.add(imgPorDefecto, 2, 1, 1, 17);

        fila++;
        Button btnGrabar = new Button("Crear Viaje");
        btnGrabar.setPrefHeight(ALTO_CAJA);
        btnGrabar.setMaxWidth(Double.MAX_VALUE);
        btnGrabar.setTextFill(Color.web("#FFFFFF"));
        btnGrabar.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        btnGrabar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + ";");
        btnGrabar.setOnAction(e -> guardarViaje());
        miGrilla.add(btnGrabar, segundaColumna, fila);
    }

    private Boolean formularioCompleto() {
        if (cmbBusViaje.getSelectionModel().getSelectedIndex() == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Bus no seleccionado", "Debe seleccionar un bus");
            return false;
        }

        if (cmbRutaViaje.getSelectionModel().getSelectedIndex() == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Ruta no seleccionada", "Debe seleccionar una ruta");
            return false;
        }

        if (cmbConductorViaje.getSelectionModel().getSelectedIndex() == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Conductor no seleccionado", "Debe seleccionar un conductor");
            return false;
        }

        if (dateFechaViaje.getValue() == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Fecha no seleccionada", "Debe seleccionar la fecha del viaje");
            return false;
        }

        if (txtPrecio.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar el precio del viaje");
            txtPrecio.requestFocus();
            return false;
        }

        if (txtDescripcion.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar una descripción");
            txtDescripcion.requestFocus();
            return false;
        }

        if (cmbEstadoViaje.getSelectionModel().getSelectedIndex() == 0) {
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

    private void guardarViaje() {
        if (formularioCompleto()) {
            ViajeDto dto = new ViajeDto();
            dto.setBusViaje(cmbBusViaje.getValue());
            dto.setRutaViaje(cmbRutaViaje.getValue());
            dto.setConductorViaje(cmbConductorViaje.getValue());
            dto.setFechaViaje(dateFechaViaje.getValue());

            LocalTime horaSalida = LocalTime.of(
                spinnerHoraSalida.getValue(), 
                spinnerMinutoSalida.getValue()
            );
            dto.setHoraSalidaViaje(horaSalida);

            LocalTime horaLlegada = LocalTime.of(
                spinnerHoraLlegada.getValue(), 
                spinnerMinutoLlegada.getValue()
            );
            dto.setHoraLlegadaViaje(horaLlegada);
            
            dto.setPrecioViaje(Double.parseDouble(txtPrecio.getText()));
            dto.setAsientosDisponiblesViaje(spinnerAsientos.getValue());
            dto.setEstadoViaje(cmbEstadoViaje.getValue().equals("Activo"));
            dto.setViajeDirecto(chkViajeDirecto.isSelected());
            dto.setIncluyeRefrigerio(chkIncluyeRefrigerio.isSelected());
            dto.setTieneParadasIntermedias(chkParadasIntermedias.isSelected());
            dto.setDescripcionViaje(txtDescripcion.getText());
            dto.setNotasAdicionalesViaje(txtNotasAdicionales.getText());
            dto.setNombreImagenPublicoViaje(txtImagen.getText());

            if (ViajeControladorGrabar.crearViaje(dto, rutaImagenSeleccionada)) {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, this.getScene().getWindow(),
                        "Éxito", "Viaje creado correctamente");
                limpiarFormulario();
            } else {
                Mensaje.mostrar(Alert.AlertType.ERROR, this.getScene().getWindow(),
                        "Error", "No se pudo crear el viaje");
            }
        }
    }

    private void limpiarFormulario() {
        cmbBusViaje.getSelectionModel().select(0);
        cmbRutaViaje.getSelectionModel().select(0);
        cmbConductorViaje.getSelectionModel().select(0);
        dateFechaViaje.setValue(LocalDate.now());
        spinnerHoraSalida.getValueFactory().setValue(8);
        spinnerMinutoSalida.getValueFactory().setValue(0);
        spinnerHoraLlegada.getValueFactory().setValue(12);
        spinnerMinutoLlegada.getValueFactory().setValue(0);
        txtPrecio.clear();
        spinnerAsientos.getValueFactory().setValue(40);
        chkViajeDirecto.setSelected(false);
        chkIncluyeRefrigerio.setSelected(false);
        chkParadasIntermedias.setSelected(false);
        txtDescripcion.clear();
        txtNotasAdicionales.clear();
        cmbEstadoViaje.getSelectionModel().select(0);
        txtImagen.clear();
        rutaImagenSeleccionada = "";

        miGrilla.getChildren().remove(imgPrevisualizar);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        miGrilla.add(imgPorDefecto, 1, 16);
        cmbBusViaje.requestFocus();
    }
}