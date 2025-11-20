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
import org.poo.controlador.bus.BusControladorEditar;
import org.poo.controlador.bus.BusControladorVentana;
import org.poo.controlador.empresa.EmpresaControladorListar;
import org.poo.dto.BusDto;
import org.poo.dto.EmpresaDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.*;

import java.time.LocalDate;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Cursor;

public class VistaBusEditar extends StackPane {

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

    private TextField txtPlacaBus;
    private TextField txtModeloBus;
    private Spinner<Integer> spinnerCapacidad;
    private ComboBox<EmpresaDto> cmbEmpresaBus;
    private ComboBox<String> cmbTipoBus;
    private ComboBox<String> cmbEstadoBus;
    private DatePicker dateFechaAdquisicion;
    private CheckBox chkAireAcondicionado;
    private CheckBox chkWifi;
    private CheckBox chkBano;
    private TextArea txtDescripcion;
    
    private TextField txtImagen;
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private String rutaImagenSeleccionada;

    private final int posicion;
    private final BusDto objBus;
    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;
    private final boolean origenCarrusel;

    public VistaBusEditar(Stage ventanaPadre, BorderPane princ, Pane pane,
            double ancho, double alto, BusDto objBusExterno, int posicionArchivo, boolean origenCarrusel) {

        miEscenario = ventanaPadre;
        miFormulario = this;
        miFormulario.setAlignment(Pos.CENTER);

        posicion = posicionArchivo;
        objBus = objBusExterno;
        panelPrincipal = princ;
        panelCuerpo = pane;
        this.origenCarrusel = origenCarrusel;
        rutaImagenSeleccionada = "";

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
        colocarFrmElegante();
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
        Text miTitulo = new Text("FORMULARIO - EDITAR BUS");
        miTitulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        miTitulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 28));
        GridPane.setHalignment(miTitulo, HPos.CENTER);
        GridPane.setMargin(miTitulo, new Insets(30, 0, 0, 0));
        miGrilla.add(miTitulo, 0, 0, 3, 2);//nombre, columna, fila, union de filas 
    }

    private void crearFormulario() {

        Label lblPlaca = new Label("Placa del Bus:");
        lblPlaca.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblPlaca, 0, 2);

        txtPlacaBus = new TextField();
        txtPlacaBus.setText(objBus.getPlacaBus());
        txtPlacaBus.setPrefHeight(ALTO_CAJA);
        txtPlacaBus.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        txtPlacaBus.setMinHeight(Region.USE_PREF_SIZE);
        GridPane.setHgrow(txtPlacaBus, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtPlacaBus, 10);
        miGrilla.add(txtPlacaBus, 1, 2);

        Label lblModelo = new Label("Modelo:");
        lblModelo.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblModelo, 0, 3);

        txtModeloBus = new TextField();
        txtModeloBus.setText(objBus.getModeloBus());
        txtModeloBus.setPrefHeight(ALTO_CAJA);
        txtModeloBus.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        txtModeloBus.setMinHeight(Region.USE_PREF_SIZE);
        GridPane.setHgrow(txtModeloBus, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtModeloBus, 50);
        miGrilla.add(txtModeloBus, 1, 3);

        Label lblCapacidad = new Label("Capacidad:");
        lblCapacidad.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblCapacidad, 0, 4);

        spinnerCapacidad = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 60, 
                objBus.getCapacidadBus() != null ? objBus.getCapacidadBus() : 40);
        spinnerCapacidad.setValueFactory(valueFactory);
        spinnerCapacidad.setPrefHeight(ALTO_CAJA);
        spinnerCapacidad.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        spinnerCapacidad.setMinHeight(Region.USE_PREF_SIZE);
        spinnerCapacidad.setEditable(true);
        miGrilla.add(spinnerCapacidad, 1, 4);

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
        
        int indiceSeleccionado = 0;
        for (int i = 0; i < cmbEmpresaBus.getItems().size(); i++) {
            if (cmbEmpresaBus.getItems().get(i).getIdEmpresa().equals(
                    objBus.getEmpresaBus().getIdEmpresa())) {
                indiceSeleccionado = i;
                break;
            }
        }
        cmbEmpresaBus.getSelectionModel().select(indiceSeleccionado);
        
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

        Label lblTipo = new Label("Tipo de Bus:");
        lblTipo.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblTipo, 0, 6);

        cmbTipoBus = new ComboBox<>();
        cmbTipoBus.setMaxWidth(Double.MAX_VALUE);
        cmbTipoBus.setPrefHeight(ALTO_CAJA);
        cmbTipoBus.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        cmbTipoBus.setMinHeight(Region.USE_PREF_SIZE);
        cmbTipoBus.getItems().addAll("Seleccione tipo", "Normal", "Ejecutivo", "VIP");
        
        int indiceTipo = switch (objBus.getTipoBus()) {
            case "Normal" -> 1;
            case "Ejecutivo" -> 2;
            case "VIP" -> 3;
            default -> 0;
        };
        cmbTipoBus.getSelectionModel().select(indiceTipo);
        miGrilla.add(cmbTipoBus, 1, 6);

        Label lblFechaAdquisicion = new Label("Fecha Adquisición:");
        lblFechaAdquisicion.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblFechaAdquisicion, 0, 7);

        dateFechaAdquisicion = new DatePicker();
        dateFechaAdquisicion.setMaxWidth(Double.MAX_VALUE);
        dateFechaAdquisicion.setPrefHeight(ALTO_CAJA);
        dateFechaAdquisicion.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        dateFechaAdquisicion.setMinHeight(Region.USE_PREF_SIZE);
        dateFechaAdquisicion.setValue(LocalDate.now().minusYears(5));
        Formulario.deshabilitarFechasFuturas(dateFechaAdquisicion);
        miGrilla.add(dateFechaAdquisicion, 1, 7);

        Label lblServicios = new Label("Servicios del Bus:");
        lblServicios.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblServicios, 0, 8);

        chkAireAcondicionado = new CheckBox("Aire Acondicionado");
        chkWifi = new CheckBox("WiFi");
        chkBano = new CheckBox("Baño");
        
        chkAireAcondicionado.setFont(Font.font("Times new roman", 12));
        chkWifi.setFont(Font.font("Times new roman", 12));
        chkBano.setFont(Font.font("Times new roman", 12));

        VBox vboxServicios = new VBox(5);
        vboxServicios.getChildren().addAll(chkAireAcondicionado, chkWifi, chkBano);
        miGrilla.add(vboxServicios, 1, 8);

        Label lblDescripcion = new Label("Descripción:");
        lblDescripcion.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDescripcion, 0, 9);

        txtDescripcion = new TextArea();
        txtDescripcion.setPrefRowCount(3);
        txtDescripcion.setWrapText(true);
        txtDescripcion.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        txtDescripcion.setMaxHeight(Double.MAX_VALUE);
        miGrilla.add(txtDescripcion, 1, 9);

        Label lblEstado = new Label("Estado:");
        lblEstado.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEstado, 0, 10);

        cmbEstadoBus = new ComboBox<>();
        cmbEstadoBus.setMaxWidth(Double.MAX_VALUE);
        cmbEstadoBus.setPrefHeight(ALTO_CAJA);
        cmbEstadoBus.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        cmbEstadoBus.setMinHeight(Region.USE_PREF_SIZE);
        cmbEstadoBus.getItems().addAll("Seleccione estado", "Activo", "Inactivo");
        
        if (objBus.getEstadoBus()) {
            cmbEstadoBus.getSelectionModel().select(1);
        } else {
            cmbEstadoBus.getSelectionModel().select(2);
        }
        miGrilla.add(cmbEstadoBus, 1, 10);

        Label lblImagen = new Label("Imagen:");
        lblImagen.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblImagen, 0, 11);

        txtImagen = new TextField();
        txtImagen.setText(objBus.getNombreImagenPublicoBus());
        txtImagen.setDisable(true);
        txtImagen.setPrefHeight(ALTO_CAJA);
        txtImagen.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        txtImagen.setMinHeight(Region.USE_PREF_SIZE);

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
                
                imgPrevisualizar = Icono.previsualizar(rutaImagenSeleccionada, 100);
                GridPane.setHalignment(imgPrevisualizar, HPos.CENTER);
                miGrilla.add(imgPrevisualizar, 2, 5);
            }
        });

        HBox.setHgrow(txtImagen, Priority.ALWAYS);
        HBox panelImagen = new HBox(5, txtImagen, btnSeleccionarImagen);
        miGrilla.add(panelImagen, 1, 11);

        imgPorDefecto = Icono.obtenerFotosExternas(
                objBus.getNombreImagenPrivadoBus(), 100);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 5);

        Button btnActualizar = new Button("ACTUALIZAR");
        btnActualizar.setPrefHeight(ALTO_CAJA);
        btnActualizar.setMaxWidth(Double.MAX_VALUE);
        btnActualizar.setTextFill(Color.web("#FFFFFF"));
        btnActualizar.setFont(Font.font("Rockwell", FontWeight.BOLD, 14));
        btnActualizar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + ";");
        btnActualizar.setCursor(Cursor.HAND);
        btnActualizar.setOnAction(e -> actualizarBus());
        miGrilla.add(btnActualizar, 1, 12);

        Button btnRegresar = new Button("VOLVER");
        btnRegresar.setPrefHeight(ALTO_CAJA);
        btnRegresar.setMaxWidth(Double.MAX_VALUE);
        btnRegresar.setFont(Font.font("Rockwell", FontWeight.BOLD, 14));
        btnRegresar.setCursor(Cursor.HAND);
        btnRegresar.setOnAction(e -> {
            if (origenCarrusel) {
                panelCuerpo = BusControladorVentana.carrusel(
                        miEscenario, panelPrincipal, panelCuerpo,
                        Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO, posicion);
            } else {
                panelCuerpo = BusControladorVentana.administrar(
                        miEscenario, panelPrincipal, panelCuerpo,
                        Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO);
            }
            panelPrincipal.setCenter(null);
            panelPrincipal.setCenter(panelCuerpo);
        });
        miGrilla.add(btnRegresar, 1, 13);
    }

    private Boolean formularioCompleto() {
        if (txtPlacaBus.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar la placa");
            txtPlacaBus.requestFocus();
            return false;
        }

        if (txtModeloBus.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar el modelo");
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

        if (cmbEstadoBus.getSelectionModel().getSelectedIndex() == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Estado no seleccionado", "Debe seleccionar un estado");
            return false;
        }

        return true;
    }

    private void actualizarBus() {
        if (formularioCompleto()) {
            BusDto dtoActualizado = new BusDto();
            dtoActualizado.setIdBus(objBus.getIdBus());
            dtoActualizado.setPlacaBus(txtPlacaBus.getText());
            dtoActualizado.setModeloBus(txtModeloBus.getText());
            dtoActualizado.setCapacidadBus(spinnerCapacidad.getValue());
            dtoActualizado.setEmpresaBus(cmbEmpresaBus.getValue());
            dtoActualizado.setTipoBus(cmbTipoBus.getValue());
            dtoActualizado.setEstadoBus(cmbEstadoBus.getValue().equals("Activo"));
            dtoActualizado.setNombreImagenPublicoBus(txtImagen.getText());
            dtoActualizado.setNombreImagenPrivadoBus(objBus.getNombreImagenPrivadoBus());

            if (BusControladorEditar.actualizar(posicion, dtoActualizado, rutaImagenSeleccionada)) {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, this.getScene().getWindow(),
                        "Éxito", "Bus actualizado correctamente");
            } else {
                Mensaje.mostrar(Alert.AlertType.ERROR, this.getScene().getWindow(),
                        "Error", "No se pudo actualizar el bus");
            }
        }
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
