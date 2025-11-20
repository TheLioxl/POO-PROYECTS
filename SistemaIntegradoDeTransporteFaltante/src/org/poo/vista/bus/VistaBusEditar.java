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

public class VistaBusEditar extends StackPane {

    private static final int H_GAP = 10;
    private static final int V_GAP = 15;
    private static final int ALTO_CAJA = 35;
    private static final int TAMANIO_FUENTE = 18;

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
                Configuracion.DEGRADE_ARREGLO_BUS,
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
        espacioSuperior.prefHeightProperty().bind(miEscenario.heightProperty().multiply(0.03));
        miGrilla.add(espacioSuperior, columna, fila, colSpan, rowSpan);

        fila = 1;
        Text titulo = new Text("Formulario Actualización de Bus");
        titulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        GridPane.setHalignment(titulo, HPos.CENTER);
        miGrilla.add(titulo, columna, fila, colSpan, rowSpan);
    }

    private void crearFormulario() {
        int fila = 2;
        int primeraColumna = 0;
        int segundaColumna = 1;

        fila++;
        Label lblPlaca = new Label("Placa del Bus:");
        lblPlaca.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblPlaca, primeraColumna, fila);

        txtPlacaBus = new TextField();
        txtPlacaBus.setText(objBus.getPlacaBus());
        txtPlacaBus.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtPlacaBus, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtPlacaBus, 10);
        miGrilla.add(txtPlacaBus, segundaColumna, fila);

        fila++;
        Label lblModelo = new Label("Modelo:");
        lblModelo.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblModelo, primeraColumna, fila);

        txtModeloBus = new TextField();
        txtModeloBus.setText(objBus.getModeloBus());
        txtModeloBus.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtModeloBus, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtModeloBus, 50);
        miGrilla.add(txtModeloBus, segundaColumna, fila);

        fila++;
        Label lblCapacidad = new Label("Capacidad:");
        lblCapacidad.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblCapacidad, primeraColumna, fila);

        spinnerCapacidad = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 60, 
                objBus.getCapacidadBus() != null ? objBus.getCapacidadBus() : 40);
        spinnerCapacidad.setValueFactory(valueFactory);
        spinnerCapacidad.setPrefHeight(ALTO_CAJA);
        spinnerCapacidad.setMaxWidth(Double.MAX_VALUE);
        spinnerCapacidad.setEditable(true);
        miGrilla.add(spinnerCapacidad, segundaColumna, fila);

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
        
        miGrilla.add(cmbEmpresaBus, segundaColumna, fila);

        fila++;
        Label lblTipo = new Label("Tipo de Bus:");
        lblTipo.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblTipo, primeraColumna, fila);

        cmbTipoBus = new ComboBox<>();
        cmbTipoBus.setMaxWidth(Double.MAX_VALUE);
        cmbTipoBus.setPrefHeight(ALTO_CAJA);
        cmbTipoBus.getItems().addAll("Seleccione tipo", "Normal", "Ejecutivo", "VIP");
        
        int indiceTipo = switch (objBus.getTipoBus()) {
            case "Normal" -> 1;
            case "Ejecutivo" -> 2;
            case "VIP" -> 3;
            default -> 0;
        };
        cmbTipoBus.getSelectionModel().select(indiceTipo);
        miGrilla.add(cmbTipoBus, segundaColumna, fila);

        fila++;
        Label lblFechaAdquisicion = new Label("Fecha Adquisición:");
        lblFechaAdquisicion.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblFechaAdquisicion, primeraColumna, fila);

        dateFechaAdquisicion = new DatePicker();
        dateFechaAdquisicion.setMaxWidth(Double.MAX_VALUE);
        dateFechaAdquisicion.setPrefHeight(ALTO_CAJA);
        dateFechaAdquisicion.setValue(LocalDate.now().minusYears(5));
        Formulario.deshabilitarFechasFuturas(dateFechaAdquisicion);
        miGrilla.add(dateFechaAdquisicion, segundaColumna, fila);

        fila++;
        Label lblServicios = new Label("Servicios del Bus:");
        lblServicios.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblServicios, primeraColumna, fila);

        chkAireAcondicionado = new CheckBox("Aire Acondicionado");
        chkWifi = new CheckBox("WiFi");
        chkBano = new CheckBox("Baño");
        
        chkAireAcondicionado.setFont(Font.font("Arial", 14));
        chkWifi.setFont(Font.font("Arial", 14));
        chkBano.setFont(Font.font("Arial", 14));

        VBox vboxServicios = new VBox(5);
        vboxServicios.getChildren().addAll(chkAireAcondicionado, chkWifi, chkBano);
        miGrilla.add(vboxServicios, segundaColumna, fila);

        fila++;
        Label lblDescripcion = new Label("Descripción:");
        lblDescripcion.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDescripcion, primeraColumna, fila);

        txtDescripcion = new TextArea();
        txtDescripcion.setPrefRowCount(3);
        txtDescripcion.setWrapText(true);
        txtDescripcion.setMaxWidth(Double.MAX_VALUE);
        miGrilla.add(txtDescripcion, segundaColumna, fila);

        fila++;
        Label lblEstado = new Label("Estado:");
        lblEstado.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEstado, primeraColumna, fila);

        cmbEstadoBus = new ComboBox<>();
        cmbEstadoBus.setMaxWidth(Double.MAX_VALUE);
        cmbEstadoBus.setPrefHeight(ALTO_CAJA);
        cmbEstadoBus.getItems().addAll("Seleccione estado", "Activo", "Inactivo");
        
        if (objBus.getEstadoBus()) {
            cmbEstadoBus.getSelectionModel().select(1);
        } else {
            cmbEstadoBus.getSelectionModel().select(2);
        }
        miGrilla.add(cmbEstadoBus, segundaColumna, fila);

        fila++;
        Label lblImagen = new Label("Imagen:");
        lblImagen.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblImagen, primeraColumna, fila);

        txtImagen = new TextField();
        txtImagen.setText(objBus.getNombreImagenPublicoBus());
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
                imgPorDefecto = Icono.obtenerFotosExternas(objBus.getNombreImagenPrivadoBus(), 150);
                GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
                miGrilla.add(imgPorDefecto, 2, 1, 1, 11);
            } else {
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                imgPrevisualizar = Icono.previsualizar(rutaImagenSeleccionada, 150);
                GridPane.setHalignment(imgPrevisualizar, HPos.CENTER);
                miGrilla.add(imgPrevisualizar, 2, 1, 1, 11);
            }
        });

        HBox.setHgrow(txtImagen, Priority.ALWAYS);
        HBox panelImagen = new HBox(2, txtImagen, btnSeleccionarImagen);
        panelImagen.setAlignment(Pos.BOTTOM_RIGHT);
        miGrilla.add(panelImagen, segundaColumna, fila);

        // Imagen en columna derecha
        imgPorDefecto = Icono.obtenerFotosExternas(
                objBus.getNombreImagenPrivadoBus(), 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        GridPane.setValignment(imgPorDefecto, javafx.geometry.VPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 1, 1, 11);

        fila++;
        Button btnActualizar = new Button("Actualizar Bus");
        btnActualizar.setPrefHeight(ALTO_CAJA);
        btnActualizar.setMaxWidth(Double.MAX_VALUE);
        btnActualizar.setTextFill(Color.web("#FFFFFF"));
        btnActualizar.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        btnActualizar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + ";");
        btnActualizar.setOnAction(e -> actualizarBus());
        miGrilla.add(btnActualizar, segundaColumna, fila);

        fila++;
        Button btnRegresar = new Button("Regresar");
        btnRegresar.setPrefHeight(ALTO_CAJA);
        btnRegresar.setMaxWidth(Double.MAX_VALUE);
        btnRegresar.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
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
        miGrilla.add(btnRegresar, segundaColumna, fila);
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
}
