package org.poo.vista.terminal;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.poo.controlador.terminal.TerminalControladorGrabar;
import org.poo.dto.TerminalDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.Formulario;
import org.poo.recurso.utilidad.GestorImagen;
import org.poo.recurso.utilidad.Icono;
import org.poo.recurso.utilidad.Marco;
import org.poo.recurso.utilidad.Mensaje;

public class VistaTerminalCrear extends StackPane {

    private static final int H_GAP = 10;
    private static final int V_GAP = 20;
    private static final int ALTO_CAJA = 35;
    private static final int TAMANIO_FUENTE = 18;

    private final GridPane miGrilla;
    private final Rectangle miMarco;
    private final Stage miEscenario;

    // Componentes del formulario
    private TextField txtNombreTerminal;
    private TextField txtCiudadTerminal;
    private TextField txtDireccionTerminal;
    private ComboBox<String> cmbEstadoTerminal;
    private Spinner<Integer> spinnerPlataformas;
    private CheckBox chkWifi;
    private CheckBox chkCafeteria;
    private CheckBox chkBanos;
    private Button btnSeleccionarImagen;
    private Button btnGrabar;
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private TextField txtImagen;
    private String rutaImagenSeleccionada;

    public VistaTerminalCrear(Stage escenario, double ancho, double alto) {
        miEscenario = escenario;
        rutaImagenSeleccionada = "";
        setAlignment(Pos.CENTER);

        miGrilla = new GridPane();
        miMarco = Marco.crear(
                miEscenario,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_TERMINAL,
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
        miGrilla.setAlignment(Pos.CENTER); // ✅ CENTRADO
        miGrilla.setPrefSize(anchoGrilla, alto);
        miGrilla.setMinSize(anchoGrilla, alto);
        miGrilla.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        // Configurar columnas
        ColumnConstraints col0 = new ColumnConstraints();
        col0.setPercentWidth(40);
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(60);
        col1.setHgrow(Priority.ALWAYS);
        
        miGrilla.getColumnConstraints().addAll(col0, col1);
    }

    private void crearTitulo() {
        int columna = 0, fila = 0, colSpan = 2, rowSpan = 1;

        // Espacio superior
        Region espacioSuperior = new Region();
        espacioSuperior.prefHeightProperty().bind(
                miEscenario.heightProperty().multiply(0.05));
        miGrilla.add(espacioSuperior, columna, fila, colSpan, rowSpan);

        // Título centrado
        fila = 1;
        Text titulo = new Text("Formulario Creación de Terminal");
        titulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        GridPane.setHalignment(titulo, HPos.CENTER);
        miGrilla.add(titulo, columna, fila, colSpan, rowSpan);
    }

    private void crearFormulario() {
        int fila = 2; // Empezamos después del título
        int primeraColumna = 0;
        int segundaColumna = 1;

        // NOMBRE TERMINAL
        fila++;
        Label lblNombre = new Label("Nombre Terminal:");
        lblNombre.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblNombre, primeraColumna, fila);

        txtNombreTerminal = new TextField();
        txtNombreTerminal.setPromptText("Ej: Terminal del Norte");
        txtNombreTerminal.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtNombreTerminal, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtNombreTerminal, 50);
        miGrilla.add(txtNombreTerminal, segundaColumna, fila);

        // CIUDAD
        fila++;
        Label lblCiudad = new Label("Ciudad:");
        lblCiudad.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblCiudad, primeraColumna, fila);

        txtCiudadTerminal = new TextField();
        txtCiudadTerminal.setPromptText("Ej: Santa Marta");
        txtCiudadTerminal.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtCiudadTerminal, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtCiudadTerminal, 30);
        Formulario.soloLetras(txtCiudadTerminal);
        miGrilla.add(txtCiudadTerminal, segundaColumna, fila);

        // DIRECCIÓN
        fila++;
        Label lblDireccion = new Label("Dirección:");
        lblDireccion.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDireccion, primeraColumna, fila);

        txtDireccionTerminal = new TextField();
        txtDireccionTerminal.setPromptText("Ej: Calle 25 # 15-30");
        txtDireccionTerminal.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtDireccionTerminal, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtDireccionTerminal, 100);
        miGrilla.add(txtDireccionTerminal, segundaColumna, fila);

        // ESTADO
        fila++;
        Label lblEstado = new Label("Estado:");
        lblEstado.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEstado, primeraColumna, fila);

        cmbEstadoTerminal = new ComboBox<>();
        cmbEstadoTerminal.setMaxWidth(Double.MAX_VALUE);
        cmbEstadoTerminal.setPrefHeight(ALTO_CAJA);
        cmbEstadoTerminal.getItems().addAll("Seleccione estado", "Activo", "Inactivo");
        cmbEstadoTerminal.getSelectionModel().select(0);
        miGrilla.add(cmbEstadoTerminal, segundaColumna, fila);

        // PLATAFORMAS (Spinner)
        fila++;
        Label lblPlataformas = new Label("Plataformas/Muelles:");
        lblPlataformas.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblPlataformas, primeraColumna, fila);

        spinnerPlataformas = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 10);
        spinnerPlataformas.setValueFactory(valueFactory);
        spinnerPlataformas.setPrefHeight(ALTO_CAJA);
        spinnerPlataformas.setMaxWidth(Double.MAX_VALUE);
        spinnerPlataformas.setEditable(true);
        miGrilla.add(spinnerPlataformas, segundaColumna, fila);

        // SERVICIOS (CheckBox)
        fila++;
        Label lblServicios = new Label("Servicios disponibles:");
        lblServicios.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblServicios, primeraColumna, fila);

        chkWifi = new CheckBox("WiFi Gratis");
        chkCafeteria = new CheckBox("Cafetería");
        chkBanos = new CheckBox("Baños");
        
        chkWifi.setFont(Font.font("Arial", 14));
        chkCafeteria.setFont(Font.font("Arial", 14));
        chkBanos.setFont(Font.font("Arial", 14));

        VBox vboxServicios = new VBox(5);
        vboxServicios.getChildren().addAll(chkWifi, chkCafeteria, chkBanos);
        miGrilla.add(vboxServicios, segundaColumna, fila);

        // IMAGEN
        fila++;
        Label lblImagen = new Label("Imagen de la terminal:");
        lblImagen.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblImagen, primeraColumna, fila);

        txtImagen = new TextField();
        txtImagen.setDisable(true);
        txtImagen.setPrefHeight(ALTO_CAJA);

        String[] extensiones = {"*.png", "*.jpg", "*.jpeg"};
        FileChooser selector = Formulario.selectorImagen(
                "Seleccionar imagen", "Imágenes", extensiones);

        btnSeleccionarImagen = new Button("+");
        btnSeleccionarImagen.setPrefHeight(ALTO_CAJA);
        btnSeleccionarImagen.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        btnSeleccionarImagen.setOnAction(e -> {
            rutaImagenSeleccionada = GestorImagen.obtenerRutaImagen(txtImagen, selector);
            
            if (!rutaImagenSeleccionada.isEmpty()) {
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                
                imgPrevisualizar = Icono.previsualizar(rutaImagenSeleccionada, 150);
                GridPane.setHalignment(imgPrevisualizar, HPos.CENTER);
                miGrilla.add(imgPrevisualizar, segundaColumna, + 1);
            }
        });

        HBox.setHgrow(txtImagen, Priority.ALWAYS);
        HBox panelImagen = new HBox(5, txtImagen, btnSeleccionarImagen);
        miGrilla.add(panelImagen, segundaColumna, fila);

        // PREVISUALIZACIÓN DE IMAGEN
        fila++;
        final int filaImagen = fila; // Variable final para usar en lambda
        imgPorDefecto = Icono.obtenerIcono(Configuracion.ICONO_NO_DISPONIBLE, 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        miGrilla.add(imgPorDefecto, segundaColumna, fila);

        // ESPACIO ADICIONAL antes del botón
        fila++;
        Region espacioAntes = new Region();
        espacioAntes.setPrefHeight(15);
        miGrilla.add(espacioAntes, segundaColumna, fila);

        // BOTÓN GRABAR
        fila++;
        btnGrabar = new Button("Crear Terminal");
        btnGrabar.setPrefHeight(ALTO_CAJA);
        btnGrabar.setMaxWidth(Double.MAX_VALUE);
        btnGrabar.setTextFill(Color.web("#FFFFFF"));
        btnGrabar.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        btnGrabar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + ";");
        btnGrabar.setOnAction(e -> guardarTerminal());
        miGrilla.add(btnGrabar, segundaColumna, fila);
    }

    private Boolean formularioCompleto() {
        if (txtNombreTerminal.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar el nombre de la terminal");
            txtNombreTerminal.requestFocus();
            return false;
        }

        if (txtCiudadTerminal.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar la ciudad");
            txtCiudadTerminal.requestFocus();
            return false;
        }

        if (txtDireccionTerminal.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar la dirección");
            txtDireccionTerminal.requestFocus();
            return false;
        }

        if (cmbEstadoTerminal.getSelectionModel().getSelectedIndex() == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Estado no seleccionado", "Debe seleccionar un estado");
            cmbEstadoTerminal.requestFocus();
            return false;
        }

        if (rutaImagenSeleccionada.isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Imagen no seleccionada", "Debe seleccionar una imagen");
            return false;
        }

        return true;
    }

    private void guardarTerminal() {
        if (formularioCompleto()) {
            TerminalDto dto = new TerminalDto();
            dto.setNombreTerminal(txtNombreTerminal.getText());
            dto.setCiudadTerminal(txtCiudadTerminal.getText());
            dto.setDireccionTerminal(txtDireccionTerminal.getText());
            dto.setEstadoTerminal(cmbEstadoTerminal.getValue().equals("Activo"));
            dto.setNumeroPlataformas(spinnerPlataformas.getValue());
            dto.setTieneWifi(chkWifi.isSelected());
            dto.setTieneCafeteria(chkCafeteria.isSelected());
            dto.setTieneBanos(chkBanos.isSelected());
            dto.setCantidadEmpresasTerminal((short) 0);
            dto.setNombreImagenPublicoTerminal(txtImagen.getText());

            if (TerminalControladorGrabar.crearTerminal(dto, rutaImagenSeleccionada)) {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, this.getScene().getWindow(),
                        "Éxito", "Terminal creada correctamente");
                limpiarFormulario();
            } else {
                Mensaje.mostrar(Alert.AlertType.ERROR, this.getScene().getWindow(),
                        "Error", "No se pudo crear la terminal");
            }
        }
    }

    private void limpiarFormulario() {
        txtNombreTerminal.clear();
        txtCiudadTerminal.clear();
        txtDireccionTerminal.clear();
        txtImagen.clear();
        cmbEstadoTerminal.getSelectionModel().select(0);
        spinnerPlataformas.getValueFactory().setValue(10);
        chkWifi.setSelected(false);
        chkCafeteria.setSelected(false);
        chkBanos.setSelected(false);
        rutaImagenSeleccionada = "";

        miGrilla.getChildren().remove(imgPrevisualizar);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        int filaImagen = 9; // Ajustar según la fila donde está la imagen
        miGrilla.add(imgPorDefecto, 1, filaImagen);

        txtNombreTerminal.requestFocus();
    }
}
