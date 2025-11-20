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
import org.poo.controlador.viaje.ViajeControladorEditar;
import org.poo.controlador.viaje.ViajeControladorVentana;
import org.poo.dto.BusDto;
import org.poo.dto.ConductorDto;
import org.poo.dto.RutaDto;
import org.poo.dto.ViajeDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Cursor;

public class VistaViajeEditar extends StackPane {

    private static final int H_GAP = 10;
    private static final int V_GAP = 18;
    private static final int ALTO_FILA = 28;
    private static final int ALTO_CAJA = 27;
    private static final int TAMANIO_FUENTE = 17;
    private static final double AJUSTE_TITULO = 0.2;

    private final GridPane miGrilla;
    private final StackPane miFormulario;
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

    private final int posicion;
    private final ViajeDto objViaje;
    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;
    private final boolean desdeCarrusel;

    public VistaViajeEditar(Stage ventanaPadre, BorderPane princ, Pane pane,
            double ancho, double alto, ViajeDto objViajeExterno, int posicionArchivo,
            boolean vieneDeCarrusel) {

        miEscenario = ventanaPadre;
        miFormulario = this;
        miFormulario.setAlignment(Pos.CENTER);

        posicion = posicionArchivo;
        objViaje = objViajeExterno;
        panelPrincipal = princ;
        panelCuerpo = pane;
        desdeCarrusel = vieneDeCarrusel;
        rutaImagenSeleccionada = "";

        miGrilla = new GridPane();
        miMarco = Marco.crear(
                miEscenario,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.MARCO_ALTO_PORCENTAJE + 0.1,
                Configuracion.DEGRADE_ARREGLO_RUTA,
                Configuracion.DEGRADE_BORDE
        );

        getChildren().add(miMarco);

        configurarGrilla(ancho, alto);
        crearTitulo();
        colocarFrmElegante();
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
        miGrilla.maxHeightProperty().bind(heightProperty().multiply(0.60));
        miGrilla.setAlignment(Pos.CENTER);


        ColumnConstraints col0 = new ColumnConstraints();
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        
        col0.setPercentWidth(30);  // etiqueta
        col1.setPercentWidth(45);  // campo de texto
        col2.setPercentWidth(30);  // imagen
        col1.setHgrow(Priority.ALWAYS);

        miGrilla.getColumnConstraints().addAll(col0, col1, col2);

        for (int i = 0; i < 17; i++) {
            RowConstraints fila = new RowConstraints();
            fila.setMinHeight(ALTO_FILA);
            fila.setMaxHeight(ALTO_FILA);
            fila.setVgrow(Priority.ALWAYS);
            miGrilla.getRowConstraints().add(fila);
        }
    }

    private void crearTitulo() {
        Text miTitulo = new Text("FORMULARIO - EDITAR VIAJE");
        miTitulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        miTitulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 26));
        GridPane.setHalignment(miTitulo, HPos.CENTER);
        GridPane.setMargin(miTitulo, new Insets(30, 0, 0, 0));
        miGrilla.add(miTitulo, 0, 0, 3, 2);//nombre, columna, fila, union de filas 
    }

    private void crearFormulario() {

        Label lblBus = new Label("Bus:");
        lblBus.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblBus, 0, 2);

        cmbBusViaje = new ComboBox<>();
        cmbBusViaje.setMaxWidth(Double.MAX_VALUE);
        cmbBusViaje.setPrefHeight(ALTO_CAJA);
        
        List<BusDto> buses = BusControladorListar.obtenerBusesActivos();
        BusDto busDefault = new BusDto();
        busDefault.setIdBus(0);
        busDefault.setPlacaBus("Seleccione un bus");
        
        cmbBusViaje.getItems().add(busDefault);
        cmbBusViaje.getItems().addAll(buses);
        
        // Seleccionar el bus actual
        int indiceBus = 0;
        for (int i = 0; i < cmbBusViaje.getItems().size(); i++) {
            if (cmbBusViaje.getItems().get(i).getIdBus().equals(objViaje.getBusViaje().getIdBus())) {
                indiceBus = i;
                break;
            }
        }
        cmbBusViaje.getSelectionModel().select(indiceBus);
        
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
        
        miGrilla.add(cmbBusViaje, 1, 2);

        Label lblRuta = new Label("Ruta:");
        lblRuta.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblRuta, 0, 3);

        cmbRutaViaje = new ComboBox<>();
        cmbRutaViaje.setMaxWidth(Double.MAX_VALUE);
        cmbRutaViaje.setPrefHeight(ALTO_CAJA);
        
        List<RutaDto> rutas = RutaControladorListar.obtenerRutasActivas();
        RutaDto rutaDefault = new RutaDto();
        rutaDefault.setIdRuta(0);
        rutaDefault.setNombreRuta("Seleccione una ruta");
        
        cmbRutaViaje.getItems().add(rutaDefault);
        cmbRutaViaje.getItems().addAll(rutas);
        
        // Seleccionar la ruta actual
        int indiceRuta = 0;
        for (int i = 0; i < cmbRutaViaje.getItems().size(); i++) {
            if (cmbRutaViaje.getItems().get(i).getIdRuta().equals(objViaje.getRutaViaje().getIdRuta())) {
                indiceRuta = i;
                break;
            }
        }
        cmbRutaViaje.getSelectionModel().select(indiceRuta);
        
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
        
        miGrilla.add(cmbRutaViaje, 1, 3);

        Label lblConductor = new Label("Conductor:");
        lblConductor.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblConductor, 0, 4);

        cmbConductorViaje = new ComboBox<>();
        cmbConductorViaje.setMaxWidth(Double.MAX_VALUE);
        cmbConductorViaje.setPrefHeight(ALTO_CAJA);
        
        List<ConductorDto> conductores = ConductorControladorListar.obtenerConductoresActivos();
        ConductorDto conductorDefault = new ConductorDto();
        conductorDefault.setIdConductor(0);
        conductorDefault.setNombreConductor("Seleccione un conductor");
        
        cmbConductorViaje.getItems().add(conductorDefault);
        cmbConductorViaje.getItems().addAll(conductores);
        
        // Seleccionar el conductor actual
        int indiceConductor = 0;
        for (int i = 0; i < cmbConductorViaje.getItems().size(); i++) {
            if (cmbConductorViaje.getItems().get(i).getIdConductor().equals(
                    objViaje.getConductorViaje().getIdConductor())) {
                indiceConductor = i;
                break;
            }
        }
        cmbConductorViaje.getSelectionModel().select(indiceConductor);
        
        cmbConductorViaje.setConverter(new StringConverter<ConductorDto>() {
            @Override
            public String toString(ConductorDto conductor) {
                return conductor != null ? conductor.toString() : "";
            }
            @Override
            public ConductorDto fromString(String string) {
                return null;
            }
        });
        
        miGrilla.add(cmbConductorViaje, 1, 4);

        // FECHA
        Label lblFecha = new Label("Fecha del Viaje:");
        lblFecha.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblFecha, 0, 5);

        dateFechaViaje = new DatePicker();
        dateFechaViaje.setMaxWidth(Double.MAX_VALUE);
        dateFechaViaje.setPrefHeight(ALTO_CAJA);
        dateFechaViaje.setValue(objViaje.getFechaViaje());
        Formulario.deshabilitarFechasPasadas(dateFechaViaje);
        miGrilla.add(dateFechaViaje, 1, 5);

        // HORA SALIDA
        Label lblHoraSalida = new Label("Hora de Salida:");
        lblHoraSalida.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblHoraSalida, 0, 6);

        spinnerHoraSalida = new Spinner<>();
        SpinnerValueFactory<Integer> horaFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 
                objViaje.getHoraSalidaViaje().getHour());
        spinnerHoraSalida.setValueFactory(horaFactory);
        spinnerHoraSalida.setPrefHeight(ALTO_CAJA);
        spinnerHoraSalida.setEditable(true);

        spinnerMinutoSalida = new Spinner<>();
        SpinnerValueFactory<Integer> minutoFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 
                objViaje.getHoraSalidaViaje().getMinute());
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
        miGrilla.add(horaSalidaBox, 1, 6);

        // HORA LLEGADA
        Label lblHoraLlegada = new Label("Hora de Llegada:");
        lblHoraLlegada.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblHoraLlegada, 0, 7);

        spinnerHoraLlegada = new Spinner<>();
        SpinnerValueFactory<Integer> horaLlegadaFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 
                objViaje.getHoraLlegadaViaje().getHour());
        spinnerHoraLlegada.setValueFactory(horaLlegadaFactory);
        spinnerHoraLlegada.setPrefHeight(ALTO_CAJA);
        spinnerHoraLlegada.setEditable(true);

        spinnerMinutoLlegada = new Spinner<>();
        SpinnerValueFactory<Integer> minutoLlegadaFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 
                objViaje.getHoraLlegadaViaje().getMinute());
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
        miGrilla.add(horaLlegadaBox, 1, 7);

        // PRECIO
        Label lblPrecio = new Label("Precio:");
        lblPrecio.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblPrecio, 0, 8);

        txtPrecio = new TextField();
        txtPrecio.setText(String.valueOf(objViaje.getPrecioViaje()));
        txtPrecio.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtPrecio, Priority.ALWAYS);
        Formulario.soloDecimales(txtPrecio);
        miGrilla.add(txtPrecio, 1, 8);

        // ASIENTOS
        Label lblAsientos = new Label("Asientos Disponibles:");
        lblAsientos.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblAsientos, 0, 9);

        spinnerAsientos = new Spinner<>();
        SpinnerValueFactory<Integer> asientosFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 60, 
                objViaje.getAsientosDisponiblesViaje());
        spinnerAsientos.setValueFactory(asientosFactory);
        spinnerAsientos.setPrefHeight(ALTO_CAJA);
        spinnerAsientos.setMaxWidth(Double.MAX_VALUE);
        spinnerAsientos.setEditable(true);
        miGrilla.add(spinnerAsientos, 1, 9);

        // CARACTERÍSTICAS
        Label lblCaracteristicas = new Label("Características:");
        lblCaracteristicas.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblCaracteristicas, 0, 10);

        chkViajeDirecto = new CheckBox("Viaje Directo");
        chkIncluyeRefrigerio = new CheckBox("Incluye Refrigerio");
        chkParadasIntermedias = new CheckBox("Paradas Intermedias");
        
        chkViajeDirecto.setSelected(objViaje.getViajeDirecto() != null ? objViaje.getViajeDirecto() : false);
        chkIncluyeRefrigerio.setSelected(objViaje.getIncluyeRefrigerio() != null ? objViaje.getIncluyeRefrigerio() : false);
        chkParadasIntermedias.setSelected(objViaje.getTieneParadasIntermedias() != null ? objViaje.getTieneParadasIntermedias() : false);
        
        chkViajeDirecto.setFont(Font.font("Times new roman", 12));
        chkIncluyeRefrigerio.setFont(Font.font("Times new roman", 12));
        chkParadasIntermedias.setFont(Font.font("Times new roman", 12));

        VBox vboxCaracteristicas = new VBox(3);
        vboxCaracteristicas.getChildren().addAll(
            chkViajeDirecto, chkIncluyeRefrigerio, chkParadasIntermedias
        );
        miGrilla.add(vboxCaracteristicas, 1, 10);

        // DESCRIPCIÓN
        Label lblDescripcion = new Label("Descripción:");
        lblDescripcion.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDescripcion, 0, 11);

        txtDescripcion = new TextField();
        txtDescripcion.setText(objViaje.getDescripcionViaje());
        txtDescripcion.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtDescripcion, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtDescripcion, 100);
        miGrilla.add(txtDescripcion, 1, 11);

        // NOTAS ADICIONALES
        Label lblNotas = new Label("Notas Adicionales:");
        lblNotas.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblNotas, 0, 12);

        txtNotasAdicionales = new TextArea();
        txtNotasAdicionales.setText(objViaje.getNotasAdicionalesViaje());
        txtNotasAdicionales.setPrefRowCount(2);
        txtNotasAdicionales.setWrapText(true);
        txtNotasAdicionales.setMaxWidth(Double.MAX_VALUE);
        miGrilla.add(txtNotasAdicionales, 1, 12);

        // ESTADO
        Label lblEstado = new Label("Estado:");
        lblEstado.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEstado, 0, 13);

        cmbEstadoViaje = new ComboBox<>();
        cmbEstadoViaje.setMaxWidth(Double.MAX_VALUE);
        cmbEstadoViaje.setPrefHeight(ALTO_CAJA);
        cmbEstadoViaje.getItems().addAll("Seleccione estado", "Activo", "Inactivo");
        
        if (objViaje.getEstadoViaje()) {
            cmbEstadoViaje.getSelectionModel().select(1);
        } else {
            cmbEstadoViaje.getSelectionModel().select(2);
        }
        miGrilla.add(cmbEstadoViaje, 1, 13);

        // IMAGEN
        Label lblImagen = new Label("Imagen:");
        lblImagen.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblImagen, 0, 14);

        txtImagen = new TextField();
        txtImagen.setText(objViaje.getNombreImagenPublicoViaje());
        txtImagen.setDisable(true);
        txtImagen.setPrefHeight(ALTO_CAJA);

        String[] extensiones = {"*.png", "*.jpg", "*.jpeg"};
        FileChooser selector = Formulario.selectorImagen(
                "Seleccionar imagen", "Imágenes", extensiones);

        Button btnSeleccionarImagen = new Button("+");
        btnSeleccionarImagen.setPrefHeight(ALTO_CAJA);
        
        btnSeleccionarImagen.setOnAction(e -> {
            rutaImagenSeleccionada = GestorImagen.obtenerRutaImagen(txtImagen, selector);
            
            if (!rutaImagenSeleccionada.isEmpty()) {
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                
                imgPrevisualizar = Icono.previsualizar(rutaImagenSeleccionada, 150);
                GridPane.setHalignment(imgPrevisualizar, HPos.CENTER);
                miGrilla.add(imgPrevisualizar, 2, 6);
            }
        });

        HBox.setHgrow(txtImagen, Priority.ALWAYS);
        HBox panelImagen = new HBox(5, txtImagen, btnSeleccionarImagen);
        miGrilla.add(panelImagen, 1, 14);

        // PREVISUALIZACIÓN
        imgPorDefecto = Icono.obtenerFotosExternas(
                objViaje.getNombreImagenPrivadoViaje(), 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 6);

        // BOTÓN ACTUALIZAR
        Button btnActualizar = new Button("ACTUALIZAR");
        btnActualizar.setPrefHeight(ALTO_CAJA);
        btnActualizar.setMaxWidth(Double.MAX_VALUE);
        btnActualizar.setTextFill(Color.web("#FFFFFF"));
        btnActualizar.setFont(Font.font("Rockwell", FontWeight.BOLD, 14));
        btnActualizar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + ";");
        btnActualizar.setCursor(Cursor.HAND);
        btnActualizar.setOnAction(e -> actualizarViaje());

        // BOTÓN REGRESAR
        Button btnRegresar = new Button("VOLVER");
        btnRegresar.setPrefHeight(ALTO_CAJA);
        btnRegresar.setMaxWidth(Double.MAX_VALUE);
        btnRegresar.setFont(Font.font("Rockwell", FontWeight.BOLD, 14));
        btnRegresar.setCursor(Cursor.HAND);
        btnRegresar.setOnAction(e -> {
            if (desdeCarrusel) {
                panelCuerpo = ViajeControladorVentana.carrusel(
                        miEscenario, panelPrincipal, panelCuerpo,
                        Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO, posicion);
            } else {
                panelCuerpo = ViajeControladorVentana.administrar(
                        miEscenario, panelPrincipal, panelCuerpo,
                        Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO);
            }
            panelPrincipal.setCenter(null);
            panelPrincipal.setCenter(panelCuerpo);
        });
        
        HBox botones = new HBox(10);
        HBox.setHgrow(btnActualizar, Priority.ALWAYS);
        HBox.setHgrow(btnRegresar, Priority.ALWAYS);
        botones.setAlignment(Pos.CENTER);
        botones.setFillHeight(true);
        botones.setMaxWidth(Double.MAX_VALUE);

        botones.getChildren().addAll(btnActualizar, btnRegresar);
        miGrilla.add(botones, 1, 15);
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

        return true;
    }

    private void actualizarViaje() {
        if (formularioCompleto()) {
            ViajeDto dtoActualizado = new ViajeDto();
            dtoActualizado.setIdViaje(objViaje.getIdViaje());
            dtoActualizado.setBusViaje(cmbBusViaje.getValue());
            dtoActualizado.setRutaViaje(cmbRutaViaje.getValue());
            dtoActualizado.setConductorViaje(cmbConductorViaje.getValue());
            dtoActualizado.setFechaViaje(dateFechaViaje.getValue());

            LocalTime horaSalida = LocalTime.of(
                spinnerHoraSalida.getValue(), 
                spinnerMinutoSalida.getValue()
            );
            dtoActualizado.setHoraSalidaViaje(horaSalida);

            LocalTime horaLlegada = LocalTime.of(
                spinnerHoraLlegada.getValue(), 
                spinnerMinutoLlegada.getValue()
            );
            dtoActualizado.setHoraLlegadaViaje(horaLlegada);
            
            dtoActualizado.setPrecioViaje(Double.parseDouble(txtPrecio.getText()));
            dtoActualizado.setAsientosDisponiblesViaje(spinnerAsientos.getValue());
            dtoActualizado.setEstadoViaje(cmbEstadoViaje.getValue().equals("Activo"));
            dtoActualizado.setViajeDirecto(chkViajeDirecto.isSelected());
            dtoActualizado.setIncluyeRefrigerio(chkIncluyeRefrigerio.isSelected());
            dtoActualizado.setTieneParadasIntermedias(chkParadasIntermedias.isSelected());
            dtoActualizado.setDescripcionViaje(txtDescripcion.getText());
            dtoActualizado.setNotasAdicionalesViaje(txtNotasAdicionales.getText());
            dtoActualizado.setNombreImagenPublicoViaje(txtImagen.getText());
            dtoActualizado.setNombreImagenPrivadoViaje(objViaje.getNombreImagenPrivadoViaje());

            if (ViajeControladorEditar.actualizar(posicion, dtoActualizado, rutaImagenSeleccionada)) {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, this.getScene().getWindow(),
                        "Éxito", "Viaje actualizado correctamente");
            } else {
                Mensaje.mostrar(Alert.AlertType.ERROR, this.getScene().getWindow(),
                        "Error", "No se pudo actualizar el viaje");
            }
        }
    }

    private void colocarFrmElegante() {
        Runnable calcular = () -> {
            double alturaMarco = miMarco.getHeight();
            if (alturaMarco > 0) {
                double desplazamiento = alturaMarco * AJUSTE_TITULO;
                miGrilla.setTranslateY(-alturaMarco / 4 + desplazamiento);
            }
        };
        calcular.run();
        miMarco.heightProperty().addListener((obs, antes, despues) -> calcular.run());
    }
}