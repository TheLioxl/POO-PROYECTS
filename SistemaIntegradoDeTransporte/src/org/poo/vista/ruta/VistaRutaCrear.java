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
import javafx.geometry.Insets;
import javafx.scene.Cursor;

public class VistaRutaCrear extends StackPane {

    private static final int H_GAP = 10;
    private static final int V_GAP = 20;
    private static final int ALTO_FILA = 33;
    private static final int ALTO_CAJA = 30;
    private static final int TAMANIO_FUENTE = 20;
    private static final double AJUSTE_TITULO = 0.1;

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
        Text miTitulo = new Text("FORMULARIO - CREAR RUTA");
        miTitulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        miTitulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 28));
        GridPane.setHalignment(miTitulo, HPos.CENTER);
        GridPane.setMargin(miTitulo, new Insets(30, 0, 0, 0));
        miGrilla.add(miTitulo, 0, 0, 3, 2);//nombre, columna, fila, union de filas 
    }

    private void crearFormulario() {

        Label lblNombre = new Label("Nombre:");
        lblNombre.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblNombre, 0, 2);

        txtNombreRuta = new TextField();
        txtNombreRuta.setPromptText("Ej: Bogotá - Medellín Express");
        txtNombreRuta.setPrefHeight(ALTO_CAJA);
        txtNombreRuta.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        txtNombreRuta.setMinHeight(Region.USE_PREF_SIZE);
        GridPane.setHgrow(txtNombreRuta, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtNombreRuta, 100);
        miGrilla.add(txtNombreRuta, 1, 2);

        // Campo 2: Ciudad de Origen (TextField)
        Label lblOrigen = new Label("Ciudad de Origen:");
        lblOrigen.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblOrigen, 0, 3);

        txtCiudadOrigen = new TextField();
        txtCiudadOrigen.setPromptText("Ej: Bogotá");
        txtCiudadOrigen.setPrefHeight(ALTO_CAJA);
        txtCiudadOrigen.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        txtCiudadOrigen.setMinHeight(Region.USE_PREF_SIZE);
        GridPane.setHgrow(txtCiudadOrigen, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtCiudadOrigen, 50);
        miGrilla.add(txtCiudadOrigen, 1, 3);

        // Campo 3: Ciudad de Destino (TextField)
        Label lblDestino = new Label("Ciudad de Destino:");
        lblDestino.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDestino, 0, 4);

        txtCiudadDestino = new TextField();
        txtCiudadDestino.setPromptText("Ej: Medellín");
        txtCiudadDestino.setPrefHeight(ALTO_CAJA);
        txtCiudadDestino.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        txtCiudadDestino.setMinHeight(Region.USE_PREF_SIZE);
        GridPane.setHgrow(txtCiudadDestino, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtCiudadDestino, 50);
        miGrilla.add(txtCiudadDestino, 1, 4);

        // Campo 4: Distancia en Km (Spinner Double)
        Label lblDistancia = new Label("Distancia (Km):");
        lblDistancia.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDistancia, 0, 5);

        spinnerDistancia = new Spinner<>();
        SpinnerValueFactory<Double> valueFactoryDistancia = 
            new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 5000.0, 100.0, 10.0);
        spinnerDistancia.setValueFactory(valueFactoryDistancia);
        spinnerDistancia.setPrefHeight(ALTO_CAJA);
        spinnerDistancia.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        spinnerDistancia.setMinHeight(Region.USE_PREF_SIZE);
        spinnerDistancia.setEditable(true);
        miGrilla.add(spinnerDistancia, 1, 5);

        // Campo 5: Duración en Horas (Spinner Integer)
        Label lblDuracion = new Label("Duración (Horas):");
        lblDuracion.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDuracion, 0, 6);

        spinnerDuracion = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactoryDuracion = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 48, 4, 1);
        spinnerDuracion.setValueFactory(valueFactoryDuracion);
        spinnerDuracion.setPrefHeight(ALTO_CAJA);
        spinnerDuracion.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        spinnerDuracion.setMinHeight(Region.USE_PREF_SIZE);
        spinnerDuracion.setEditable(true);
        miGrilla.add(spinnerDuracion, 1, 6);

        // Campo 6: Fecha de Creación (DatePicker)
        Label lblFechaCreacion = new Label("Fecha de Creación:");
        lblFechaCreacion.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblFechaCreacion, 0, 7);

        dateFechaCreacion = new DatePicker();
        dateFechaCreacion.setMaxWidth(Double.MAX_VALUE);
        dateFechaCreacion.setPrefHeight(ALTO_CAJA);
        dateFechaCreacion.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        dateFechaCreacion.setMinHeight(Region.USE_PREF_SIZE);
        dateFechaCreacion.setPromptText("Seleccione fecha");
        dateFechaCreacion.setValue(LocalDate.now());
        Formulario.deshabilitarFechasFuturas(dateFechaCreacion);
        miGrilla.add(dateFechaCreacion, 1, 7);

        // Campo 7: Características (CheckBox)
        Label lblCaracteristicas = new Label("Características:");
        lblCaracteristicas.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblCaracteristicas, 0, 8);

        chkPeajes = new CheckBox("Tiene Peajes");
        chkCarreteraPrincipal = new CheckBox("Carretera Principal");
        chkPavimentada = new CheckBox("Pavimentada");
        
        chkPeajes.setFont(Font.font("Times new roman", 13));
        chkCarreteraPrincipal.setFont(Font.font("Times new roman", 13));
        chkPavimentada.setFont(Font.font("Times new roman", 13));

        VBox vboxCaracteristicas = new VBox(3);
        vboxCaracteristicas.getChildren().addAll(chkPeajes, chkCarreteraPrincipal, chkPavimentada);
        miGrilla.add(vboxCaracteristicas, 1, 8);

        // Campo 9: Estado (ComboBox)
        Label lblEstado = new Label("Estado:");
        lblEstado.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEstado, 0, 9);

        cmbEstadoRuta = new ComboBox<>();
        cmbEstadoRuta.setMaxWidth(Double.MAX_VALUE);
        cmbEstadoRuta.setPrefHeight(ALTO_CAJA);
        cmbEstadoRuta.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        cmbEstadoRuta.setMinHeight(Region.USE_PREF_SIZE);
        cmbEstadoRuta.getItems().addAll("Seleccione estado", "Activa", "Inactiva");
        cmbEstadoRuta.getSelectionModel().select(0);
        miGrilla.add(cmbEstadoRuta, 1, 9);

        // Campo 10: Imagen
        Label lblImagen = new Label("Imagen:");
        lblImagen.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblImagen, 0, 10);

        txtImagen = new TextField();
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
                
                imgPrevisualizar = Icono.previsualizar(rutaImagenSeleccionada, 150);
                GridPane.setHalignment(imgPrevisualizar, HPos.CENTER);
                miGrilla.add(imgPrevisualizar, 2, 5);
            }
        });

        HBox.setHgrow(txtImagen, Priority.ALWAYS);
        HBox panelImagen = new HBox(5, txtImagen, btnSeleccionarImagen);
        miGrilla.add(panelImagen, 1, 10);

        // Previsualización de imagen
        imgPorDefecto = Icono.obtenerIcono(Configuracion.ICONO_NO_DISPONIBLE, 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 5);

        // Botón Crear
        Button btnGrabar = new Button("GRABAR RUTA");
        btnGrabar.setPrefHeight(ALTO_CAJA);
        btnGrabar.setMaxWidth(Double.MAX_VALUE);
        btnGrabar.setTextFill(Color.web("#FFFFFF"));
        btnGrabar.setFont(Font.font("Rockwell", FontWeight.BOLD, 16));
        btnGrabar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + ";"
                + "-fx-border-radius: 8; -fx-background-radius: 8;");
        btnGrabar.setCursor(Cursor.HAND);
        btnGrabar.setOnAction(e -> guardarRuta());
        miGrilla.add(btnGrabar, 1, 11);
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
        cmbEstadoRuta.getSelectionModel().select(0);
        txtImagen.clear();
        rutaImagenSeleccionada = "";

        miGrilla.getChildren().remove(imgPrevisualizar);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 5);

        txtNombreRuta.requestFocus();
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
