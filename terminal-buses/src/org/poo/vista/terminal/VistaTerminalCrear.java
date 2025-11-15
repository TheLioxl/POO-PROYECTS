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
import javafx.scene.layout.RowConstraints;
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
    private static final int ALTO_FILA = 40;
    private static final int ALTO_CAJA = 35;
    private static final int TAMANIO_FUENTE = 18;

    private final GridPane miGrilla;
    private final Rectangle miMarco;
    private final Stage miEscenario;

    // ==================== 6 TIPOS DE COMPONENTES ====================
    // 1. TextField - Para textos
    private TextField txtNombreTerminal;
    private TextField txtCiudadTerminal;
    private TextField txtDireccionTerminal;
    
    // 2. ComboBox - Para selección de opciones
    private ComboBox<String> cmbEstadoTerminal;
    
    // 3. Spinner - Para números (capacidad de plataformas)
    private Spinner<Integer> spinnerPlataformas;
    
    // 4. CheckBox - Para servicios de la terminal
    private CheckBox chkWifi;
    private CheckBox chkCafeteria;
    private CheckBox chkBanos;
    
    // 5. Button - Botones de acción
    private Button btnSeleccionarImagen;
    private Button btnGrabar;
    
    // 6. ImageView - Visualización de imagen
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
        ColumnConstraints col0 = new ColumnConstraints(220);
        ColumnConstraints col1 = new ColumnConstraints(350);
        col1.setHgrow(Priority.ALWAYS);
        miGrilla.getColumnConstraints().addAll(col0, col1);

        // Configurar filas
        for (int i = 0; i < 11; i++) {
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

        // ============ TIPO 1: TextField ============
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

        // ============ TIPO 2: ComboBox ============
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

        // ============ TIPO 3: Spinner ============
        fila++;
        Label lblPlataformas = new Label("Plataformas/Muelles:");
        lblPlataformas.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblPlataformas, 0, fila);

        spinnerPlataformas = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 10);
        spinnerPlataformas.setValueFactory(valueFactory);
        spinnerPlataformas.setPrefHeight(ALTO_CAJA);
        spinnerPlataformas.setMaxWidth(Double.MAX_VALUE);
        spinnerPlataformas.setEditable(true);
        miGrilla.add(spinnerPlataformas, 1, fila);

        // ============ TIPO 4: CheckBox ============
        fila++;
        Label lblServicios = new Label("Servicios disponibles:");
        lblServicios.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblServicios, 0, fila);

        chkWifi = new CheckBox("WiFi Gratis");
        chkCafeteria = new CheckBox("Cafetería");
        chkBanos = new CheckBox("Baños");
        
        chkWifi.setFont(Font.font("Arial", 14));
        chkCafeteria.setFont(Font.font("Arial", 14));
        chkBanos.setFont(Font.font("Arial", 14));

        VBox vboxServicios = new VBox(5);
        vboxServicios.getChildren().addAll(chkWifi, chkCafeteria, chkBanos);
        miGrilla.add(vboxServicios, 1, fila);

        // ============ TIPO 5: Button (Imagen) ============
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

        btnSeleccionarImagen = new Button("+");
        btnSeleccionarImagen.setPrefHeight(ALTO_CAJA);
        btnSeleccionarImagen.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        btnSeleccionarImagen.setOnAction(e -> seleccionarImagen(selector));

        HBox.setHgrow(txtImagen, Priority.ALWAYS);
        HBox panelImagen = new HBox(5, txtImagen, btnSeleccionarImagen);
        miGrilla.add(panelImagen, 1, fila);

        // ============ TIPO 6: ImageView ============
        fila++;
        imgPorDefecto = Icono.obtenerIcono(Configuracion.ICONO_NO_DISPONIBLE, 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        miGrilla.add(imgPorDefecto, 1, fila);

        // ============ TIPO 5: Button (Grabar) ============
        fila++;
        btnGrabar = new Button("CREAR TERMINAL");
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
            miGrilla.add(imgPrevisualizar, 1, 7);
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

            // Nota: Los servicios (CheckBox) y plataformas (Spinner) podrían 
            // guardarse en campos adicionales del DTO si se requiere

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
        miGrilla.add(imgPorDefecto, 1, 7);

        txtNombreTerminal.requestFocus();
    }

    private void ajustarPosicion() {
        Runnable ajustar = () -> {
            double alturaMarco = miMarco.getHeight();
            if (alturaMarco > 0) {
                miGrilla.setTranslateY(alturaMarco * 0.05);
            }
        };
        ajustar.run();
        miMarco.heightProperty().addListener((obs, old, nuevo) -> ajustar.run());
    }
}
