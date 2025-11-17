package org.poo.vista.terminal;

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
import org.poo.controlador.terminal.TerminalControladorEditar;
import org.poo.controlador.terminal.TerminalControladorVentana;
import org.poo.dto.TerminalDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.*;

public class VistaTerminalEditar extends StackPane {

    private static final int H_GAP = 10;
    private static final int V_GAP = 20;
    private static final int ALTO_CAJA = 35;
    private static final int TAMANIO_FUENTE = 18;

    private final GridPane miGrilla;
    private final StackPane miFormulario;
    private final Rectangle miMarco;
    private final Stage miEscenario;

    private TextField txtNombreTerminal;
    private TextField txtCiudadTerminal;
    private TextField txtDireccionTerminal;
    private ComboBox<String> cmbEstadoTerminal;
    private Spinner<Integer> spinnerPlataformas;
    private CheckBox chkWifi;
    private CheckBox chkCafeteria;
    private CheckBox chkBanos;
    private TextField txtImagen;
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private String rutaImagenSeleccionada;

    private final int posicion;
    private final TerminalDto objTerminal;
    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;

    public VistaTerminalEditar(Stage ventanaPadre, BorderPane princ, Pane pane,
            double ancho, double alto, TerminalDto objTerminalExterno, int posicionArchivo) {

        miEscenario = ventanaPadre;
        miFormulario = this;
        miFormulario.setBackground(Fondo.asignarAleatorio(Configuracion.FONDOS));
        miFormulario.setAlignment(Pos.CENTER);

        posicion = posicionArchivo;
        objTerminal = objTerminalExterno;
        panelPrincipal = princ;
        panelCuerpo = pane;
        rutaImagenSeleccionada = "";

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
        Text titulo = new Text("Formulario Actualización de Terminal");
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
        Label lblNombre = new Label("Nombre Terminal:");
        lblNombre.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblNombre, primeraColumna, fila);

        txtNombreTerminal = new TextField();
        txtNombreTerminal.setText(objTerminal.getNombreTerminal());
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
        txtCiudadTerminal.setText(objTerminal.getCiudadTerminal());
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
        txtDireccionTerminal.setText(objTerminal.getDireccionTerminal());
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
        
        if (objTerminal.getEstadoTerminal()) {
            cmbEstadoTerminal.getSelectionModel().select(1);
        } else {
            cmbEstadoTerminal.getSelectionModel().select(2);
        }
        miGrilla.add(cmbEstadoTerminal, segundaColumna, fila);

        // PLATAFORMAS (Spinner)
        fila++;
        Label lblPlataformas = new Label("Plataformas/Muelles:");
        lblPlataformas.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblPlataformas, primeraColumna, fila);

        spinnerPlataformas = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 
                objTerminal.getNumeroPlataformas() != null ? objTerminal.getNumeroPlataformas() : 10);
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
        
        // Cargar valores del objeto
        chkWifi.setSelected(objTerminal.getTieneWifi() != null ? objTerminal.getTieneWifi() : false);
        chkCafeteria.setSelected(objTerminal.getTieneCafeteria() != null ? objTerminal.getTieneCafeteria() : false);
        chkBanos.setSelected(objTerminal.getTieneBanos() != null ? objTerminal.getTieneBanos() : false);
        
        chkWifi.setFont(Font.font("Arial", 14));
        chkCafeteria.setFont(Font.font("Arial", 14));
        chkBanos.setFont(Font.font("Arial", 14));

        VBox vboxServicios = new VBox(5);
        vboxServicios.getChildren().addAll(chkWifi, chkCafeteria, chkBanos);
        miGrilla.add(vboxServicios, segundaColumna, fila);

        // IMAGEN
        fila++;
        Label lblImagen = new Label("Imagen:");
        lblImagen.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblImagen, primeraColumna, fila);

        txtImagen = new TextField();
        txtImagen.setText(objTerminal.getNombreImagenPublicoTerminal());
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
                objTerminal.getNombreImagenPrivadoTerminal(), 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        miGrilla.add(imgPorDefecto, segundaColumna, fila);

        // ESPACIO ADICIONAL
        fila++;

        // BOTÓN ACTUALIZAR
        fila++;
        Button btnActualizar = new Button("Actualizar Terminal");
        btnActualizar.setPrefHeight(ALTO_CAJA);
        btnActualizar.setMaxWidth(Double.MAX_VALUE);
        btnActualizar.setTextFill(Color.web("#FFFFFF"));
        btnActualizar.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        btnActualizar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + ";");
        btnActualizar.setOnAction(e -> actualizarTerminal());
        miGrilla.add(btnActualizar, segundaColumna, fila);

        // BOTÓN REGRESAR
        fila++;
        Button btnRegresar = new Button("Regresar");
        btnRegresar.setPrefHeight(ALTO_CAJA);
        btnRegresar.setMaxWidth(Double.MAX_VALUE);
        btnRegresar.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        btnRegresar.setOnAction(e -> {
            panelCuerpo = TerminalControladorVentana.administrar(
                    miEscenario, panelPrincipal, panelCuerpo,
                    Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO);
            panelPrincipal.setCenter(null);
            panelPrincipal.setCenter(panelCuerpo);
        });
        miGrilla.add(btnRegresar, segundaColumna, fila);
    }

    private Boolean formularioCompleto() {
        if (txtNombreTerminal.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar el nombre");
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
            return false;
        }

        return true;
    }

    private void actualizarTerminal() {
        if (formularioCompleto()) {
            TerminalDto dtoActualizado = new TerminalDto();
            dtoActualizado.setIdTerminal(objTerminal.getIdTerminal());
            dtoActualizado.setNombreTerminal(txtNombreTerminal.getText());
            dtoActualizado.setCiudadTerminal(txtCiudadTerminal.getText());
            dtoActualizado.setDireccionTerminal(txtDireccionTerminal.getText());
            dtoActualizado.setEstadoTerminal(cmbEstadoTerminal.getValue().equals("Activo"));
            dtoActualizado.setNumeroPlataformas(spinnerPlataformas.getValue());
            dtoActualizado.setTieneWifi(chkWifi.isSelected());
            dtoActualizado.setTieneCafeteria(chkCafeteria.isSelected());
            dtoActualizado.setTieneBanos(chkBanos.isSelected());
            dtoActualizado.setNombreImagenPublicoTerminal(txtImagen.getText());
            dtoActualizado.setNombreImagenPrivadoTerminal(objTerminal.getNombreImagenPrivadoTerminal());
            dtoActualizado.setCantidadEmpresasTerminal(objTerminal.getCantidadEmpresasTerminal());

            if (TerminalControladorEditar.actualizar(posicion, dtoActualizado, rutaImagenSeleccionada)) {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, this.getScene().getWindow(),
                        "Éxito", "Terminal actualizada correctamente");
            } else {
                Mensaje.mostrar(Alert.AlertType.ERROR, this.getScene().getWindow(),
                        "Error", "No se pudo actualizar la terminal");
            }
        }
    }
}
