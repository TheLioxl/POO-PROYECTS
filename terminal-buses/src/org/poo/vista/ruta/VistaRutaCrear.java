package org.poo.vista.ruta;

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
import org.poo.controlador.ruta.RutaControladorGrabar;
import org.poo.dto.RutaDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.*;

import java.time.LocalDate;

public class VistaRutaCrear extends StackPane {

    private static final int H_GAP = 10;
    private static final int V_GAP = 12;
    private static final int ALTO_CAJA = 35;
    private static final int TAMANIO_FUENTE = 16;

    private final GridPane miGrilla;
    private final Rectangle miMarco;
    private final Stage miEscenario;

    // Tipo 1: TextField - para nombre, origen, destino
    private TextField txtNombreRuta;
    private TextField txtCiudadOrigen;
    private TextField txtCiudadDestino;
    
    // Tipo 2: Spinner - para distancia y duración
    private Spinner<Double> spinnerDistancia;
    private Spinner<Integer> spinnerDuracion;
    
    // Tipo 3: ComboBox - para estado
    private ComboBox<String> cmbEstadoRuta;
    
    // Tipo 4: DatePicker - para fecha de creación
    private DatePicker dateFechaCreacion;
    
    // Tipo 5: CheckBox - para características de la ruta
    private CheckBox chkPeajes;
    private CheckBox chkCarreteraPrincipal;
    private CheckBox chkPavimentada;
    
    // Tipo 6: TextArea - para descripción
    private TextArea txtDescripcion;
    
    private TextField txtImagen;
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private String rutaImagenSeleccionada;

    public VistaRutaCrear(Stage escenario, double ancho, double alto) {
        miEscenario = escenario;
        rutaImagenSeleccionada = "";
        setAlignment(Pos.CENTER);

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
        col0.setPercentWidth(35);
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(65);
        col1.setHgrow(Priority.ALWAYS);
        
        miGrilla.getColumnConstraints().addAll(col0, col1);
    }

    private void crearTitulo() {
        int columna = 0, fila = 0, colSpan = 2, rowSpan = 1;

        Region espacioSuperior = new Region();
        espacioSuperior.prefHeightProperty().bind(
                miEscenario.heightProperty().multiply(0.02));
        miGrilla.add(espacioSuperior, columna, fila, colSpan, rowSpan);

        fila = 1;
        Text titulo = new Text("Formulario Creación de Ruta");
        titulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        GridPane.setHalignment(titulo, HPos.CENTER);
        miGrilla.add(titulo, columna, fila, colSpan, rowSpan);
    }

    private void crearFormulario() {
        int fila = 2;
        int primeraColumna = 0;
        int segundaColumna = 1;

        // Campo 1: Nombre de la Ruta (TextField)
        fila++;
        Label lblNombre = new Label("Nombre de la Ruta:");
        lblNombre.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblNombre, primeraColumna, fila);

        txtNombreRuta = new TextField();
        txtNombreRuta.setPromptText("Ej: Bogotá - Medellín Express");
        txtNombreRuta.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtNombreRuta, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtNombreRuta, 100);
        miGrilla.add(txtNombreRuta, segundaColumna, fila);

        // Campo 2: Ciudad de Origen (TextField)
        fila++;
        Label lblOrigen = new Label("Ciudad de Origen:");
        lblOrigen.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblOrigen, primeraColumna, fila);

        txtCiudadOrigen = new TextField();
        txtCiudadOrigen.setPromptText("Ej: Bogotá");
        txtCiudadOrigen.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtCiudadOrigen, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtCiudadOrigen, 50);
        miGrilla.add(txtCiudadOrigen, segundaColumna, fila);

        // Campo 3: Ciudad de Destino (TextField)
        fila++;
        Label lblDestino = new Label("Ciudad de Destino:");
        lblDestino.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDestino, primeraColumna, fila);

        txtCiudadDestino = new TextField();
        txtCiudadDestino.setPromptText("Ej: Medellín");
        txtCiudadDestino.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtCiudadDestino, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtCiudadDestino, 50);
        miGrilla.add(txtCiudadDestino, segundaColumna, fila);

        // Campo 4: Distancia en Km (Spinner Double)
        fila++;
        Label lblDistancia = new Label("Distancia (Km):");
        lblDistancia.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDistancia, primeraColumna, fila);

        spinnerDistancia = new Spinner<>();
        SpinnerValueFactory<Double> valueFactoryDistancia = 
            new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 5000.0, 100.0, 10.0);
        spinnerDistancia.setValueFactory(valueFactoryDistancia);
        spinnerDistancia.setPrefHeight(ALTO_CAJA);
        spinnerDistancia.setMaxWidth(Double.MAX_VALUE);
        spinnerDistancia.setEditable(true);
        miGrilla.add(spinnerDistancia, segundaColumna, fila);

        // Campo 5: Duración en Horas (Spinner Integer)
        fila++;
        Label lblDuracion = new Label("Duración (Horas):");
        lblDuracion.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDuracion, primeraColumna, fila);

        spinnerDuracion = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactoryDuracion = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 48, 4, 1);
        spinnerDuracion.setValueFactory(valueFactoryDuracion);
        spinnerDuracion.setPrefHeight(ALTO_CAJA);
        spinnerDuracion.setMaxWidth(Double.MAX_VALUE);
        spinnerDuracion.setEditable(true);
        miGrilla.add(spinnerDuracion, segundaColumna, fila);

        // Campo 6: Fecha de Creación (DatePicker)
        fila++;
        Label lblFechaCreacion = new Label("Fecha de Creación:");
        lblFechaCreacion.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblFechaCreacion, primeraColumna, fila);

        dateFechaCreacion = new DatePicker();
        dateFechaCreacion.setMaxWidth(Double.MAX_VALUE);
        dateFechaCreacion.setPrefHeight(ALTO_CAJA);
        dateFechaCreacion.setPromptText("Seleccione fecha");
        dateFechaCreacion.setValue(LocalDate.now());
        Formulario.deshabilitarFechasFuturas(dateFechaCreacion);
        miGrilla.add(dateFechaCreacion, segundaColumna, fila);

        // Campo 7: Características (CheckBox)
        fila++;
        Label lblCaracteristicas = new Label("Características:");
        lblCaracteristicas.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblCaracteristicas, primeraColumna, fila);

        chkPeajes = new CheckBox("Tiene Peajes");
        chkCarreteraPrincipal = new CheckBox("Carretera Principal");
        chkPavimentada = new CheckBox("Pavimentada");
        
        chkPeajes.setFont(Font.font("Arial", 13));
        chkCarreteraPrincipal.setFont(Font.font("Arial", 13));
        chkPavimentada.setFont(Font.font("Arial", 13));

        VBox vboxCaracteristicas = new VBox(3);
        vboxCaracteristicas.getChildren().addAll(chkPeajes, chkCarreteraPrincipal, chkPavimentada);
        miGrilla.add(vboxCaracteristicas, segundaColumna, fila);

        // Campo 8: Descripción (TextArea)
        fila++;
        Label lblDescripcion = new Label("Descripción:");
        lblDescripcion.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDescripcion, primeraColumna, fila);

        txtDescripcion = new TextArea();
        txtDescripcion.setPromptText("Breve descripción de la ruta...");
        txtDescripcion.setPrefRowCount(2);
        txtDescripcion.setWrapText(true);
        txtDescripcion.setMaxWidth(Double.MAX_VALUE);
        miGrilla.add(txtDescripcion, segundaColumna, fila);

        // Campo 9: Estado (ComboBox)
        fila++;
        Label lblEstado = new Label("Estado:");
        lblEstado.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEstado, primeraColumna, fila);

        cmbEstadoRuta = new ComboBox<>();
        cmbEstadoRuta.setMaxWidth(Double.MAX_VALUE);
        cmbEstadoRuta.setPrefHeight(ALTO_CAJA);
        cmbEstadoRuta.getItems().addAll("Seleccione estado", "Activa", "Inactiva");
        cmbEstadoRuta.getSelectionModel().select(0);
        miGrilla.add(cmbEstadoRuta, segundaColumna, fila);

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

        // Previsualización de imagen
        fila++;
        imgPorDefecto = Icono.obtenerIcono(Configuracion.ICONO_NO_DISPONIBLE, 120);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        miGrilla.add(imgPorDefecto, segundaColumna, fila);

        // Botón Crear
        fila++;
        Button btnGrabar = new Button("Crear Ruta");
        btnGrabar.setPrefHeight(ALTO_CAJA);
        btnGrabar.setMaxWidth(Double.MAX_VALUE);
        btnGrabar.setTextFill(Color.web("#FFFFFF"));
        btnGrabar.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        btnGrabar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + ";");
        btnGrabar.setOnAction(e -> guardarRuta());
        miGrilla.add(btnGrabar, segundaColumna, fila);
    }

    private Boolean formularioCompleto() {
        if (txtNombreRuta.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar el nombre de la ruta");
            txtNombreRuta.requestFocus();
            return false;
        }

        if (txtCiudadOrigen.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar la ciudad de origen");
            txtCiudadOrigen.requestFocus();
            return false;
        }

        if (txtCiudadDestino.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar la ciudad de destino");
            txtCiudadDestino.requestFocus();
            return false;
        }

        if (dateFechaCreacion.getValue() == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Fecha no seleccionada", "Debe seleccionar la fecha de creación");
            return false;
        }

        if (txtDescripcion.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar una descripción");
            txtDescripcion.requestFocus();
            return false;
        }

        if (cmbEstadoRuta.getSelectionModel().getSelectedIndex() == 0) {
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

    private void guardarRuta() {
        if (formularioCompleto()) {
            RutaDto dto = new RutaDto();
            dto.setNombreRuta(txtNombreRuta.getText());
            dto.setCiudadOrigenRuta(txtCiudadOrigen.getText());
            dto.setCiudadDestinoRuta(txtCiudadDestino.getText());
            dto.setDistanciaKmRuta(spinnerDistancia.getValue());
            dto.setDuracionHorasRuta(spinnerDuracion.getValue());
            dto.setEstadoRuta(cmbEstadoRuta.getValue().equals("Activa"));
            dto.setNombreImagenPublicoRuta(txtImagen.getText());

            if (RutaControladorGrabar.crearRuta(dto, rutaImagenSeleccionada)) {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, this.getScene().getWindow(),
                        "Éxito", "Ruta creada correctamente");
                limpiarFormulario();
            } else {
                Mensaje.mostrar(Alert.AlertType.ERROR, this.getScene().getWindow(),
                        "Error", "No se pudo crear la ruta");
            }
        }
    }

    private void limpiarFormulario() {
        txtNombreRuta.clear();
        txtCiudadOrigen.clear();
        txtCiudadDestino.clear();
        spinnerDistancia.getValueFactory().setValue(100.0);
        spinnerDuracion.getValueFactory().setValue(4);
        dateFechaCreacion.setValue(LocalDate.now());
        chkPeajes.setSelected(false);
        chkCarreteraPrincipal.setSelected(false);
        chkPavimentada.setSelected(false);
        txtDescripcion.clear();
        cmbEstadoRuta.getSelectionModel().select(0);
        txtImagen.clear();
        rutaImagenSeleccionada = "";

        miGrilla.getChildren().remove(imgPrevisualizar);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        miGrilla.add(imgPorDefecto, 1, 11);

        txtNombreRuta.requestFocus();
    }
}
