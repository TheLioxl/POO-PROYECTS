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
import org.poo.controlador.empresa.EmpresaControladorEditar;
import org.poo.controlador.empresa.EmpresaControladorVentana;
import org.poo.controlador.terminal.TerminalControladorListar;
import org.poo.dto.EmpresaDto;
import org.poo.dto.TerminalDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.*;

import java.util.List;

public class VistaEmpresaEditar extends StackPane {

    private static final int H_GAP = 10;
    private static final int V_GAP = 20;
    private static final int ALTO_CAJA = 35;
    private static final int TAMANIO_FUENTE = 18;

    private final GridPane miGrilla;
    private final StackPane miFormulario;
    private final Rectangle miMarco;
    private final Stage miEscenario;

    private TextField txtNombreEmpresa;
    private TextField txtNitEmpresa;
    private TextField txtTelefonoEmpresa;
    private TextField txtEmailEmpresa;
    private ComboBox<TerminalDto> cmbTerminalEmpresa;
    private ComboBox<String> cmbEstadoEmpresa;
    private TextField txtImagen;
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private String rutaImagenSeleccionada;

    private final int posicion;
    private final EmpresaDto objEmpresa;
    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;

    public VistaEmpresaEditar(Stage ventanaPadre, BorderPane princ, Pane pane,
            double ancho, double alto, EmpresaDto objEmpresaExterno, int posicionArchivo) {

        miEscenario = ventanaPadre;
        miFormulario = this;
        miFormulario.setAlignment(Pos.CENTER);

        posicion = posicionArchivo;
        objEmpresa = objEmpresaExterno;
        panelPrincipal = princ;
        panelCuerpo = pane;
        rutaImagenSeleccionada = "";

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
        col0.setPercentWidth(40);
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(60);
        col1.setHgrow(Priority.ALWAYS);
        
        miGrilla.getColumnConstraints().addAll(col0, col1);
    }

    private void crearTitulo() {
        int columna = 0, fila = 0, colSpan = 2, rowSpan = 1;

        Region espacioSuperior = new Region();
        espacioSuperior.prefHeightProperty().bind(miEscenario.heightProperty().multiply(0.05));
        miGrilla.add(espacioSuperior, columna, fila, colSpan, rowSpan);

        fila = 1;
        Text titulo = new Text("Formulario Actualización de Empresa");
        titulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        GridPane.setHalignment(titulo, HPos.CENTER);
        miGrilla.add(titulo, columna, fila, colSpan, rowSpan);
    }

    private void crearFormulario() {
        int fila = 2;
        int primeraColumna = 0;
        int segundaColumna = 1;

        // NOMBRE
        fila++;
        Label lblNombre = new Label("Nombre Empresa:");
        lblNombre.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblNombre, primeraColumna, fila);

        txtNombreEmpresa = new TextField();
        txtNombreEmpresa.setText(objEmpresa.getNombreEmpresa());
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
        txtNitEmpresa.setText(objEmpresa.getNitEmpresa());
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
        txtEmailEmpresa.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtEmailEmpresa, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtEmailEmpresa, 50);
        miGrilla.add(txtEmailEmpresa, segundaColumna, fila);

        // TERMINAL
        fila++;
        Label lblTerminal = new Label("Terminal:");
        lblTerminal.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblTerminal, primeraColumna, fila);

        cmbTerminalEmpresa = new ComboBox<>();
        cmbTerminalEmpresa.setMaxWidth(Double.MAX_VALUE);
        cmbTerminalEmpresa.setPrefHeight(ALTO_CAJA);
        
        List<TerminalDto> terminales = TerminalControladorListar.obtenerTerminalesActivos();
        TerminalDto opcionDefault = new TerminalDto();
        opcionDefault.setIdTerminal(0);
        opcionDefault.setNombreTerminal("Seleccione terminal");
        
        cmbTerminalEmpresa.getItems().add(opcionDefault);
        cmbTerminalEmpresa.getItems().addAll(terminales);
        
        // Seleccionar el terminal actual
        int indiceSeleccionado = 0;
        for (int i = 0; i < cmbTerminalEmpresa.getItems().size(); i++) {
            if (cmbTerminalEmpresa.getItems().get(i).getIdTerminal().equals(
                    objEmpresa.getTerminalEmpresa().getIdTerminal())) {
                indiceSeleccionado = i;
                break;
            }
        }
        cmbTerminalEmpresa.getSelectionModel().select(indiceSeleccionado);
        
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
        
        if (objEmpresa.getEstadoEmpresa()) {
            cmbEstadoEmpresa.getSelectionModel().select(1);
        } else {
            cmbEstadoEmpresa.getSelectionModel().select(2);
        }
        miGrilla.add(cmbEstadoEmpresa, segundaColumna, fila);

        // IMAGEN
        fila++;
        Label lblImagen = new Label("Imagen:");
        lblImagen.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblImagen, primeraColumna, fila);

        txtImagen = new TextField();
        txtImagen.setText(objEmpresa.getNombreImagenPublicoEmpresa());
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
                
                imgPrevisualizar = Icono.previsualizar(rutaImagenSeleccionada, 150);
                GridPane.setHalignment(imgPrevisualizar, HPos.CENTER);
                miGrilla.add(imgPrevisualizar, segundaColumna, filaImagenPreview);
            }
        });

        HBox.setHgrow(txtImagen, Priority.ALWAYS);
        HBox panelImagen = new HBox(5, txtImagen, btnSeleccionarImagen);
        miGrilla.add(panelImagen, segundaColumna, fila);

        // PREVISUALIZACIÓN
        fila++;
        imgPorDefecto = Icono.obtenerFotosExternas(
                objEmpresa.getNombreImagenPrivadoEmpresa(), 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        miGrilla.add(imgPorDefecto, segundaColumna, fila);

        fila++;

        // BOTÓN ACTUALIZAR
        fila++;
        Button btnActualizar = new Button("Actualizar Empresa");
        btnActualizar.setPrefHeight(ALTO_CAJA);
        btnActualizar.setMaxWidth(Double.MAX_VALUE);
        btnActualizar.setTextFill(Color.web("#FFFFFF"));
        btnActualizar.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        btnActualizar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + ";");
        btnActualizar.setOnAction(e -> actualizarEmpresa());
        miGrilla.add(btnActualizar, segundaColumna, fila);

        // BOTÓN REGRESAR
        fila++;
        Button btnRegresar = new Button("Regresar");
        btnRegresar.setPrefHeight(ALTO_CAJA);
        btnRegresar.setMaxWidth(Double.MAX_VALUE);
        btnRegresar.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        btnRegresar.setOnAction(e -> {
            panelCuerpo = EmpresaControladorVentana.administrar(
                    miEscenario, panelPrincipal, panelCuerpo,
                    Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO);
            panelPrincipal.setCenter(null);
            panelPrincipal.setCenter(panelCuerpo);
        });
        miGrilla.add(btnRegresar, segundaColumna, fila);
    }

    private Boolean formularioCompleto() {
        if (txtNombreEmpresa.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar el nombre");
            txtNombreEmpresa.requestFocus();
            return false;
        }

        if (txtNitEmpresa.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar el NIT");
            txtNitEmpresa.requestFocus();
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

        return true;
    }

    private void actualizarEmpresa() {
        if (formularioCompleto()) {
            EmpresaDto dtoActualizado = new EmpresaDto();
            dtoActualizado.setIdEmpresa(objEmpresa.getIdEmpresa());
            dtoActualizado.setNombreEmpresa(txtNombreEmpresa.getText());
            dtoActualizado.setNitEmpresa(txtNitEmpresa.getText());
            dtoActualizado.setTerminalEmpresa(cmbTerminalEmpresa.getValue());
            dtoActualizado.setEstadoEmpresa(cmbEstadoEmpresa.getValue().equals("Activo"));
            dtoActualizado.setNombreImagenPublicoEmpresa(txtImagen.getText());
            dtoActualizado.setNombreImagenPrivadoEmpresa(objEmpresa.getNombreImagenPrivadoEmpresa());
            dtoActualizado.setCantidadBusesEmpresa(objEmpresa.getCantidadBusesEmpresa());

            if (EmpresaControladorEditar.actualizar(posicion, dtoActualizado, rutaImagenSeleccionada)) {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, this.getScene().getWindow(),
                        "Éxito", "Empresa actualizada correctamente");
            } else {
                Mensaje.mostrar(Alert.AlertType.ERROR, this.getScene().getWindow(),
                        "Error", "No se pudo actualizar la empresa");
            }
        }
    }
}
