package org.poo.vista.terminal;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
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
    private static final int ALTO_FILA = 40;
    private static final int ALTO_CAJA = 35;
    private static final int TAMANIO_FUENTE = 18;

    private final GridPane miGrilla;
    private final Rectangle miMarco;
    private final Stage miEscenario;

    // Campos del formulario
    private TextField txtNombreTerminal;
    private TextField txtCiudadTerminal;
    private TextField txtDireccionTerminal;
    private ComboBox<String> cmbEstadoTerminal;
    private TextField txtImagen;

    // Gestión de imágenes
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
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
        ajustarPosicion();
        
        getChildren().add(miGrilla);
    }

    private void configurarGrilla(double ancho, double alto) {
        double anchoGrilla = ancho * Configuracion.GRILLA_ANCHO_PORCENTAJE;

        miGrilla.setHgap(H_GAP);
        miGrilla.setVgap(V_GAP);
        miGrilla.setPrefSize(anchoGrilla, alto);
        miGrilla.setMinSize(anchoGrilla, alto);
        miGrilla.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        // Configurar columnas
        ColumnConstraints col0 = new ColumnConstraints(200);
        ColumnConstraints col1 = new ColumnConstraints(300);
        col1.setHgrow(Priority.ALWAYS);
        miGrilla.getColumnConstraints().addAll(col0, col1);

        // Configurar filas
        for (int i = 0; i < 8; i++) {
            RowConstraints fila = new RowConstraints(ALTO_FILA);
            miGrilla.getRowConstraints().add(fila);
        }
    }

    private void crearTitulo() {
        Text titulo = new Text("CREAR TERMINAL");
        titulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        GridPane.setHalignment(titulo, HPos.CENTER);
        GridPane.setMargin(titulo, new Insets(20, 0, 20, 0));
        miGrilla.add(titulo, 0, 0, 2, 1);
    }

    private void crearFormulario() {
        int fila = 1;

        // NOMBRE TERMINAL
        Label lblNombre = new Label("Nombre Terminal:");
        lblNombre.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblNombre, 0, fila);

        txtNombreTerminal = new TextField();
        txtNombreTerminal.setPromptText("Ej: Terminal del Norte");
        txtNombreTerminal.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtNombreTerminal, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtNombreTerminal, 50);
        miGrilla.add(txtNombreTerminal, 1, fila);

        // CIUDAD
        fila++;
        Label lblCiudad = new Label("Ciudad:");
        lblCiudad.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblCiudad, 0, fila);

        txtCiudadTerminal = new TextField();
        txtCiudadTerminal.setPromptText("Ej: Santa Marta");
        txtCiudadTerminal.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtCiudadTerminal, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtCiudadTerminal, 30);
        Formulario.soloLetras(txtCiudadTerminal);
        miGrilla.add(txtCiudadTerminal, 1, fila);

        // DIRECCIÓN
        fila++;
        Label lblDireccion = new Label("Dirección:");
        lblDireccion.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDireccion, 0, fila);

        txtDireccionTerminal = new TextField();
        txtDireccionTerminal.setPromptText("Ej: Calle 25 # 15-30");
        txtDireccionTerminal.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtDireccionTerminal, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtDireccionTerminal, 100);
        miGrilla.add(txtDireccionTerminal, 1, fila);

        // ESTADO
        fila++;
        Label lblEstado = new Label("Estado:");
        lblEstado.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEstado, 0, fila);

        cmbEstadoTerminal = new ComboBox<>();
        cmbEstadoTerminal.setMaxWidth(Double.MAX_VALUE);
        cmbEstadoTerminal.setPrefHeight(ALTO_CAJA);
        cmbEstadoTerminal.getItems().addAll("Seleccione estado", "Activo", "Inactivo");
        cmbEstadoTerminal.getSelectionModel().select(0);
        miGrilla.add(cmbEstadoTerminal, 1, fila);

        // IMAGEN
        fila++;
        Label lblImagen = new Label("Imagen:");
        lblImagen.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblImagen, 0, fila);

        txtImagen = new TextField();
        txtImagen.setDisable(true);
        txtImagen.setPrefHeight(ALTO_CAJA);

        String[] extensiones = {"*.png", "*.jpg", "*.jpeg"};
        FileChooser selector = Formulario.selectorImagen(
                "Seleccionar imagen", "Imágenes", extensiones);

        Button btnSeleccionar = new Button("+");
        btnSeleccionar.setPrefHeight(ALTO_CAJA);
        btnSeleccionar.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        btnSeleccionar.setOnAction(e -> seleccionarImagen(selector));

        HBox.setHgrow(txtImagen, Priority.ALWAYS);
        HBox panelImagen = new HBox(5, txtImagen, btnSeleccionar);
        miGrilla.add(panelImagen, 1, fila);

        // PREVISUALIZACIÓN
        fila++;
        imgPorDefecto = Icono.obtenerIcono(Configuracion.ICONO_NO_DISPONIBLE, 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        miGrilla.add(imgPorDefecto, 1, fila);

        // BOTÓN GRABAR
        fila++;
        Button btnGrabar = new Button("CREAR TERMINAL");
        btnGrabar.setPrefHeight(ALTO_CAJA);
        btnGrabar.setMaxWidth(Double.MAX_VALUE);
        btnGrabar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + 
                "; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
        btnGrabar.setOnAction(e -> guardarTerminal());
        miGrilla.add(btnGrabar, 1, fila);
    }

    private void seleccionarImagen(FileChooser selector) {
        rutaImagenSeleccionada = GestorImagen.obtenerRutaImagen(txtImagen, selector);
        
        if (!rutaImagenSeleccionada.isEmpty()) {
            miGrilla.getChildren().remove(imgPorDefecto);
            miGrilla.getChildren().remove(imgPrevisualizar);
            
            imgPrevisualizar = Icono.previsualizar(rutaImagenSeleccionada, 150);
            GridPane.setHalignment(imgPrevisualizar, HPos.CENTER);
            miGrilla.add(imgPrevisualizar, 1, 5);
        }
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
        rutaImagenSeleccionada = "";

        miGrilla.getChildren().remove(imgPrevisualizar);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        miGrilla.add(imgPorDefecto, 1, 5);

        txtNombreTerminal.requestFocus();
    }

    private void ajustarPosicion() {
        Runnable ajustar = () -> {
            double alturaMarco = miMarco.getHeight();
            if (alturaMarco > 0) {
                miGrilla.setTranslateY(-alturaMarco * 0.05);
            }
        };
        ajustar.run();
        miMarco.heightProperty().addListener((obs, old, nuevo) -> ajustar.run());
    }
}
