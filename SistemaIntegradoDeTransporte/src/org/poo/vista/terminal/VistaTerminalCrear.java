package org.poo.vista.terminal;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
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
    private static final int TAMANIO_FUENTE = 20;
    private static final double AJUSTE_TITULO = 0.1;

    private final GridPane miGrilla;
    private final Rectangle miMarco;

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
    private TextField cajaImagen;
    private String rutaImagenSeleccionada;

    public VistaTerminalCrear(Stage escenario, double ancho, double alto) {
        rutaImagenSeleccionada = "";
        
        setAlignment(Pos.CENTER);
        miGrilla = new GridPane();
        miMarco = Marco.crear(escenario,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_TERMINAL,
                Configuracion.DEGRADE_BORDE);

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
        miGrilla.setPrefSize(miAnchoGrilla, alto);
        miGrilla.setMinSize(miAnchoGrilla, alto);
        miGrilla.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        ColumnConstraints col0 = new ColumnConstraints();
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        
        col0.setPrefWidth(200);
        col1.setPrefWidth(200);
        col2.setPrefWidth(200);
        
        col1.setHgrow(Priority.ALWAYS);
        miGrilla.getColumnConstraints().addAll(col0, col1, col2);

        for (int i = 0; i < 10; i++) {
            RowConstraints fila = new RowConstraints();
            fila.setMinHeight(ALTO_FILA);
            fila.setMaxHeight(ALTO_FILA);
            miGrilla.getRowConstraints().add(fila);
        }
    }

    private void crearTitulo() {
        Text miTitulo = new Text("FORMULARIO - CREAR TERMINAL");
        miTitulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        miTitulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 28));
        GridPane.setHalignment(miTitulo, HPos.CENTER);
        GridPane.setMargin(miTitulo, new Insets(30, 0, 0, 0));
        miGrilla.add(miTitulo, 0, 0, 3, 2);//nombre, columna, fila, union de filas 
    }

    private void crearFormulario() {
        //NOMBRE
        Label lblNombre = new Label("Nombre Terminal:");
        lblNombre.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblNombre, 0, 2);

        txtNombreTerminal = new TextField();
        txtNombreTerminal.setPromptText("Ej: Terminal del Norte");
        txtNombreTerminal.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtNombreTerminal, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtNombreTerminal, 50);
        miGrilla.add(txtNombreTerminal, 1, 2);

        // CIUDAD
        Label lblCiudad = new Label("Ciudad:");
        lblCiudad.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblCiudad, 0, 3);

        txtCiudadTerminal = new TextField();
        txtCiudadTerminal.setPromptText("Ej: Santa Marta");
        txtCiudadTerminal.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtCiudadTerminal, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtCiudadTerminal, 30);
        Formulario.soloLetras(txtCiudadTerminal);
        miGrilla.add(txtCiudadTerminal, 1, 3);

        // DIRECCIÓN
        Label lblDireccion = new Label("Dirección:");
        lblDireccion.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDireccion, 0, 4);

        txtDireccionTerminal = new TextField();
        txtDireccionTerminal.setPromptText("Ej: Calle 25 # 15-30");
        txtDireccionTerminal.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtDireccionTerminal, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtDireccionTerminal, 100);
        miGrilla.add(txtDireccionTerminal, 1, 4);

        // ESTADO
        Label lblEstado = new Label("Estado:");
        lblEstado.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEstado, 0, 5);

        cmbEstadoTerminal = new ComboBox<>();
        cmbEstadoTerminal.setMaxWidth(Double.MAX_VALUE);
        cmbEstadoTerminal.setPrefHeight(ALTO_CAJA);
        cmbEstadoTerminal.getItems().addAll("Seleccione estado", "Activo", "Inactivo");
        cmbEstadoTerminal.getSelectionModel().select(0);
        miGrilla.add(cmbEstadoTerminal, 1, 5);

        // PLATAFORMAS (Spinner)
        Label lblPlataformas = new Label("Plataformas/Muelles:");
        lblPlataformas.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblPlataformas, 0, 6);

        spinnerPlataformas = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50);
        spinnerPlataformas.setValueFactory(valueFactory);
        spinnerPlataformas.setPrefHeight(ALTO_CAJA);
        spinnerPlataformas.setMaxWidth(Double.MAX_VALUE);
        spinnerPlataformas.setEditable(true);
        miGrilla.add(spinnerPlataformas, 1, 6);

        // SERVICIOS (CheckBox)
        Label lblServicios = new Label("Servicios disponibles:");
        lblServicios.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblServicios, 0, 7);

        chkWifi = new CheckBox("WiFi Gratis");
        chkCafeteria = new CheckBox("Cafetería");
        chkBanos = new CheckBox("Baños");
        
        chkWifi.setFont(Font.font("Times new roman", 14));
        chkCafeteria.setFont(Font.font("Times new roman", 14));
        chkBanos.setFont(Font.font("Times new roman", 14));

        VBox vboxServicios = new VBox(5);
        vboxServicios.getChildren().addAll(chkWifi, chkCafeteria, chkBanos);
        miGrilla.add(vboxServicios, 1, 7);

        // IMAGEN
        Label lblImagen = new Label("Imagen de la terminal:");
        lblImagen.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblImagen, 0, 8);

        cajaImagen = new TextField();
        cajaImagen.setDisable(true);
        cajaImagen.setPrefHeight(ALTO_CAJA);
        String[] extensiones = {"*.png", "*.jpg", "*.jpeg"};
        FileChooser objSeleccionar = Formulario.selectorImagen(
                "Seleccionar imagen", "Imágenes", extensiones);

        Button btnSeleccionarImagen = new Button("+");
        btnSeleccionarImagen.setPrefHeight(ALTO_CAJA);
        
        btnSeleccionarImagen.setOnAction((e)->{
            rutaImagenSeleccionada = GestorImagen.obtenerRutaImagen(cajaImagen, objSeleccionar);
            if (rutaImagenSeleccionada.isEmpty()) {
                //Solo es para crear NO para actualizar
                //OJOOOOOOOOOOOOO
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                miGrilla.add(imgPorDefecto, 2, 1, 1, 9);
            }else{
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                imgPrevisualizar = Icono.previsualizar(rutaImagenSeleccionada, 150);
                GridPane.setHalignment(imgPrevisualizar, HPos.CENTER);
                miGrilla.add(imgPrevisualizar, 2, 1, 1, 9);
            }
        });

        HBox.setHgrow(cajaImagen, Priority.ALWAYS);
        HBox panelHorizontal = new HBox(2);
        panelHorizontal.setAlignment(Pos.BOTTOM_RIGHT);
        panelHorizontal.getChildren().addAll(cajaImagen, btnSeleccionarImagen);
        miGrilla.add(panelHorizontal, 1, 8);
        
        imgPorDefecto = Icono.obtenerIcono("imgNoDisponible.png", 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        GridPane.setValignment(imgPorDefecto, VPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 1, 1, 9);

        // BOTÓN GRABAR
        Button btnGrabar = new Button("GRABAR TERMINAL");
        btnGrabar.setPrefHeight(ALTO_CAJA);
        btnGrabar.setMaxWidth(Double.MAX_VALUE);
        btnGrabar.setTextFill(Color.web("#FFFFFF"));
        btnGrabar.setFont(Font.font("Rockwell", FontWeight.BOLD, 14));
        btnGrabar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + ";" 
                + "-fx-border-radius: 8; -fx-background-radius: 8;");
        btnGrabar.setCursor(Cursor.HAND);
        btnGrabar.setOnAction(e -> guardarTerminal());
        miGrilla.add(btnGrabar, 1, 9);
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
            dto.setNombreImagenPublicoTerminal(cajaImagen.getText());

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
        cajaImagen.clear();
        cmbEstadoTerminal.getSelectionModel().select(0);
        spinnerPlataformas.getValueFactory().setValue(10);
        chkWifi.setSelected(false);
        chkCafeteria.setSelected(false);
        chkBanos.setSelected(false);
        rutaImagenSeleccionada = "";

        miGrilla.getChildren().remove(imgPrevisualizar);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        GridPane.setValignment(imgPorDefecto, VPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 1, 1, 9);

        txtNombreTerminal.requestFocus();
    }
    
    private void colocarFrmElegante() {
        Runnable calcular = () -> {
            double alturaMarco = miMarco.getHeight();
            if (alturaMarco > 0) {
                double desplazamiento = alturaMarco * AJUSTE_TITULO;
                miGrilla.setTranslateY(-alturaMarco / 14 + desplazamiento);
            }
        };
        calcular.run();
        miMarco.heightProperty().addListener((obs, antes, despues) -> calcular.run());
    }
}
