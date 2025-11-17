package org.poo.vista.empresa;

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
import org.poo.controlador.empresa.EmpresaControladorGrabar;
import org.poo.controlador.terminal.TerminalControladorListar;
import org.poo.dto.EmpresaDto;
import org.poo.dto.TerminalDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.*;

import java.util.List;

public class VistaEmpresaCrear extends StackPane {

    private static final int H_GAP = 10;
    private static final int V_GAP = 20;
    private static final int ALTO_CAJA = 35;
    private static final int TAMANIO_FUENTE = 18;

    private final GridPane miGrilla;
    private final Rectangle miMarco;
    private final Stage miEscenario;

    // Componentes del formulario
    private TextField txtNombreEmpresa;
    private TextField txtNitEmpresa;
    private TextField txtTelefonoEmpresa;
    private TextField txtEmailEmpresa;
    private ComboBox<TerminalDto> cmbTerminalEmpresa;
    private ComboBox<String> cmbEstadoEmpresa;
    private Button btnSeleccionarImagen;
    private Button btnGrabar;
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private TextField txtImagen;
    private String rutaImagenSeleccionada;

    public VistaEmpresaCrear(Stage escenario, double ancho, double alto) {
        miEscenario = escenario;
        rutaImagenSeleccionada = "";
        setAlignment(Pos.CENTER);

        miGrilla = new GridPane();
        miMarco = Marco.crear(
                miEscenario,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_EMPRESA,
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
        col0.setPercentWidth(40);
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(60);
        col1.setHgrow(Priority.ALWAYS);
        
        miGrilla.getColumnConstraints().addAll(col0, col1);
    }

    private void crearTitulo() {
        int columna = 0, fila = 0, colSpan = 2, rowSpan = 1;

        Region espacioSuperior = new Region();
        espacioSuperior.prefHeightProperty().bind(
                miEscenario.heightProperty().multiply(0.05));
        miGrilla.add(espacioSuperior, columna, fila, colSpan, rowSpan);

        fila = 1;
        Text titulo = new Text("Formulario Creación de Empresa");
        titulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        GridPane.setHalignment(titulo, HPos.CENTER);
        miGrilla.add(titulo, columna, fila, colSpan, rowSpan);
    }

    private void crearFormulario() {
        int fila = 2;
        int primeraColumna = 0;
        int segundaColumna = 1;

        // NOMBRE EMPRESA
        fila++;
        Label lblNombre = new Label("Nombre Empresa:");
        lblNombre.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblNombre, primeraColumna, fila);

        txtNombreEmpresa = new TextField();
        txtNombreEmpresa.setPromptText("Ej: Expreso Brasilia");
        txtNombreEmpresa.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtNombreEmpresa, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtNombreEmpresa, 50);
        miGrilla.add(txtNombreEmpresa, segundaColumna, fila);

        // NIT
        fila++;
        Label lblNit = new Label("NIT:");
        lblNit.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblNit, primeraColumna, fila);

        txtNitEmpresa = new TextField();
        txtNitEmpresa.setPromptText("Ej: 900123456-7");
        txtNitEmpresa.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtNitEmpresa, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtNitEmpresa, 20);
        miGrilla.add(txtNitEmpresa, segundaColumna, fila);

        // TELÉFONO
        fila++;
        Label lblTelefono = new Label("Teléfono:");
        lblTelefono.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblTelefono, primeraColumna, fila);

        txtTelefonoEmpresa = new TextField();
        txtTelefonoEmpresa.setPromptText("Ej: 3001234567");
        txtTelefonoEmpresa.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtTelefonoEmpresa, Priority.ALWAYS);
        Formulario.soloTelefono(txtTelefonoEmpresa);
        miGrilla.add(txtTelefonoEmpresa, segundaColumna, fila);

        // EMAIL
        fila++;
        Label lblEmail = new Label("Email:");
        lblEmail.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEmail, primeraColumna, fila);

        txtEmailEmpresa = new TextField();
        txtEmailEmpresa.setPromptText("Ej: contacto@empresa.com");
        txtEmailEmpresa.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtEmailEmpresa, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtEmailEmpresa, 50);
        miGrilla.add(txtEmailEmpresa, segundaColumna, fila);

        // TERMINAL (ComboBox)
        fila++;
        Label lblTerminal = new Label("Terminal:");
        lblTerminal.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblTerminal, primeraColumna, fila);

        cmbTerminalEmpresa = new ComboBox<>();
        cmbTerminalEmpresa.setMaxWidth(Double.MAX_VALUE);
        cmbTerminalEmpresa.setPrefHeight(ALTO_CAJA);
        
        List<TerminalDto> terminales = TerminalControladorListar.obtenerTerminalesActivos();
        cmbTerminalEmpresa.getItems().add(new TerminalDto(0, "Seleccione terminal", "", "", false, (short)0, 0, false, false, false));
        cmbTerminalEmpresa.getItems().addAll(terminales);
        cmbTerminalEmpresa.getSelectionModel().select(0);
        
        cmbTerminalEmpresa.setConverter(new StringConverter<TerminalDto>() {
            @Override
            public String toString(TerminalDto terminal) {
                return terminal != null ? terminal.getNombreTerminal() : "";
            }

            @Override
            public TerminalDto fromString(String string) {
                return null;
            }
        });
        
        miGrilla.add(cmbTerminalEmpresa, segundaColumna, fila);

        // ESTADO
        fila++;
        Label lblEstado = new Label("Estado:");
        lblEstado.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEstado, primeraColumna, fila);

        cmbEstadoEmpresa = new ComboBox<>();
        cmbEstadoEmpresa.setMaxWidth(Double.MAX_VALUE);
        cmbEstadoEmpresa.setPrefHeight(ALTO_CAJA);
        cmbEstadoEmpresa.getItems().addAll("Seleccione estado", "Activo", "Inactivo");
        cmbEstadoEmpresa.getSelectionModel().select(0);
        miGrilla.add(cmbEstadoEmpresa, segundaColumna, fila);

        // IMAGEN
        fila++;
        Label lblImagen = new Label("Imagen (logo empresa):");
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
        
        final int filaImagenPreview = fila + 1;
        
        btnSeleccionarImagen.setOnAction(e -> {
            rutaImagenSeleccionada = GestorImagen.obtenerRutaImagen(txtImagen, selector);
            
            if (!rutaImagenSeleccionada.isEmpty()) {
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                
                imgPrevisualizar = Icono.previsualizar(rutaImagenSeleccionada, 150);
                GridPane.setHalignment(imgPrevisualizar, HPos.CENTER);
                miGrilla.add(imgPrevisualizar, segundaColumna, filaImagenPreview);
            }
        });

        HBox.setHgrow(txtImagen, Priority.ALWAYS);
        HBox panelImagen = new HBox(5, txtImagen, btnSeleccionarImagen);
        miGrilla.add(panelImagen, segundaColumna, fila);

        // PREVISUALIZACIÓN DE IMAGEN
        fila++;
        imgPorDefecto = Icono.obtenerIcono(Configuracion.ICONO_NO_DISPONIBLE, 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        miGrilla.add(imgPorDefecto, segundaColumna, fila);

        // ESPACIO ADICIONAL
        fila++;

        // BOTÓN GRABAR
        fila++;
        btnGrabar = new Button("Crear Empresa");
        btnGrabar.setPrefHeight(ALTO_CAJA);
        btnGrabar.setMaxWidth(Double.MAX_VALUE);
        btnGrabar.setTextFill(Color.web("#FFFFFF"));
        btnGrabar.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        btnGrabar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + ";");
        btnGrabar.setOnAction(e -> guardarEmpresa());
        miGrilla.add(btnGrabar, segundaColumna, fila);
    }

    private Boolean formularioCompleto() {
        if (txtNombreEmpresa.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar el nombre de la empresa");
            txtNombreEmpresa.requestFocus();
            return false;
        }

        if (txtNitEmpresa.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar el NIT");
            txtNitEmpresa.requestFocus();
            return false;
        }

        if (txtTelefonoEmpresa.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar el teléfono");
            txtTelefonoEmpresa.requestFocus();
            return false;
        }

        if (txtEmailEmpresa.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar el email");
            txtEmailEmpresa.requestFocus();
            return false;
        }

        if (!Formulario.validarEmail(txtEmailEmpresa.getText())) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Email inválido", "Debe ingresar un email válido");
            txtEmailEmpresa.requestFocus();
            return false;
        }

        if (cmbTerminalEmpresa.getSelectionModel().getSelectedIndex() == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Terminal no seleccionada", "Debe seleccionar una terminal");
            return false;
        }

        if (cmbEstadoEmpresa.getSelectionModel().getSelectedIndex() == 0) {
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

    private void guardarEmpresa() {
        if (formularioCompleto()) {
            EmpresaDto dto = new EmpresaDto();
            dto.setNombreEmpresa(txtNombreEmpresa.getText());
            dto.setNitEmpresa(txtNitEmpresa.getText());
            dto.setTerminalEmpresa(cmbTerminalEmpresa.getValue());
            dto.setEstadoEmpresa(cmbEstadoEmpresa.getValue().equals("Activo"));
            dto.setCantidadBusesEmpresa((short) 0);
            dto.setNombreImagenPublicoEmpresa(txtImagen.getText());

            if (EmpresaControladorGrabar.crearEmpresa(dto, rutaImagenSeleccionada)) {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, this.getScene().getWindow(),
                        "Éxito", "Empresa creada correctamente");
                limpiarFormulario();
            } else {
                Mensaje.mostrar(Alert.AlertType.ERROR, this.getScene().getWindow(),
                        "Error", "No se pudo crear la empresa");
            }
        }
    }

    private void limpiarFormulario() {
        txtNombreEmpresa.clear();
        txtNitEmpresa.clear();
        txtTelefonoEmpresa.clear();
        txtEmailEmpresa.clear();
        txtImagen.clear();
        cmbTerminalEmpresa.getSelectionModel().select(0);
        cmbEstadoEmpresa.getSelectionModel().select(0);
        rutaImagenSeleccionada = "";

        miGrilla.getChildren().remove(imgPrevisualizar);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        int filaImagen = 10;
        miGrilla.add(imgPorDefecto, 1, filaImagen);

        txtNombreEmpresa.requestFocus();
    }
}