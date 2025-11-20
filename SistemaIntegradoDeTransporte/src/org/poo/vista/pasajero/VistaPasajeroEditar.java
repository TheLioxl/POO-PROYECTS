package org.poo.vista.pasajero;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
import javafx.stage.Window;
import org.poo.controlador.pasajero.PasajeroControladorEditar;
import org.poo.controlador.pasajero.PasajeroControladorVentana;
import org.poo.dto.PasajeroDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.Formulario;
import org.poo.recurso.utilidad.GestorImagen;
import org.poo.recurso.utilidad.Icono;
import org.poo.recurso.utilidad.Marco;
import org.poo.recurso.utilidad.Mensaje;

public class VistaPasajeroEditar extends StackPane {

    private static final int H_GAP = 10;
    private static final int V_GAP = 20;
    private static final int ALTO_FILA = 32;
    private static final int ALTO_CAJA = 35;
    private static final int TAMANIO_FUENTE = 18;
    private static final double AJUSTE_TITULO = 0.1;

    private final GridPane miGrilla;
    private final StackPane miFormulario;
    private final Rectangle miMarco;
    private final Stage miEscenario;

    // Controles
    private TextField txtNombrePasajero;
    private TextField txtDocumentoPasajero;
    private ComboBox<String> cmbTipoDocumento;
    private DatePicker dpFechaNacimiento;
    private RadioButton rbMayor;
    private RadioButton rbMenor;
    private ToggleGroup grupoMayor;
    private TextField txtTelefonoPasajero;

    private TextField txtImagen;
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private String rutaImagenSeleccionada;

    private final int posicion;
    private final PasajeroDto objPasajero;
    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;
    private final boolean desdeCarrusel;

    public VistaPasajeroEditar(Stage ventanaPadre, BorderPane princ, Pane pane,
            double ancho, double alto,
            PasajeroDto objPasajeroExterno,
            int posicionArchivo,
            boolean vieneDeCarrusel) {

        miEscenario = ventanaPadre;
        miFormulario = this;
        miFormulario.setAlignment(Pos.CENTER);

        posicion = posicionArchivo;
        objPasajero = objPasajeroExterno;
        panelPrincipal = princ;
        panelCuerpo = pane;
        rutaImagenSeleccionada = "";
        desdeCarrusel = vieneDeCarrusel;

        miGrilla = new GridPane();
        miMarco = Marco.crear(
                miEscenario,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_RUTA, // o uno específico de pasajero si lo tienes
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

        for (int i = 0; i < 7; i++) {
            RowConstraints fila = new RowConstraints();
            fila.setMinHeight(ALTO_FILA);
            fila.setMaxHeight(ALTO_FILA);
            fila.setVgrow(Priority.ALWAYS);
            miGrilla.getRowConstraints().add(fila);
        }
    }

    private void crearTitulo() {
        Text miTitulo = new Text("FORMULARIO - EDITAR PASAJERO");
        miTitulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        miTitulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 28));
        GridPane.setHalignment(miTitulo, HPos.CENTER);
        GridPane.setMargin(miTitulo, new Insets(30, 0, 0, 0));
        miGrilla.add(miTitulo, 0, 0, 3, 2);//nombre, columna, fila, union de filas 
    }

    private void crearFormulario() {

        Label lblNombre = new Label("Nombre Pasajero:");
        lblNombre.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblNombre, 0, 2);

        txtNombrePasajero = new TextField();
        txtNombrePasajero.setText(objPasajero.getNombrePasajero());
        txtNombrePasajero.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtNombrePasajero, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtNombrePasajero, 50);
        miGrilla.add(txtNombrePasajero, 1, 2);

        // DOCUMENTO
        Label lblDocumento = new Label("Documento:");
        lblDocumento.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDocumento, 0, 3);

        txtDocumentoPasajero = new TextField();
        txtDocumentoPasajero.setText(objPasajero.getDocumentoPasajero());
        txtDocumentoPasajero.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtDocumentoPasajero, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtDocumentoPasajero, 20);
        // Formulario.soloNumeros(txtDocumentoPasajero); // si lo tienes
        miGrilla.add(txtDocumentoPasajero, 1, 3);

        // TIPO DOCUMENTO
        Label lblTipoDoc = new Label("Tipo documento:");
        lblTipoDoc.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblTipoDoc, 0, 4);

        cmbTipoDocumento = new ComboBox<>();
        cmbTipoDocumento.setPrefHeight(ALTO_CAJA);
        cmbTipoDocumento.setMaxWidth(Double.MAX_VALUE);
        cmbTipoDocumento.getItems().addAll("Cédula", "Pasaporte", "Tarjeta de identidad");
        if (objPasajero.getTipoDocumentoPasajero() != null) {
            cmbTipoDocumento.getSelectionModel().select(objPasajero.getTipoDocumentoPasajero());
        }
        miGrilla.add(cmbTipoDocumento, 1, 4);

        // FECHA NACIMIENTO
        Label lblFechaNac = new Label("Fecha Nacimiento:");
        lblFechaNac.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblFechaNac, 0, 5);

        dpFechaNacimiento = new DatePicker();
        dpFechaNacimiento.setPrefHeight(ALTO_CAJA);
        dpFechaNacimiento.setMaxWidth(Double.MAX_VALUE);
        dpFechaNacimiento.setValue(objPasajero.getFechaNacimientoPasajero());
        miGrilla.add(dpFechaNacimiento, 1, 5);

        // MAYOR / MENOR (RadioButton)
        Label lblMayor = new Label("Mayor de edad:");
        lblMayor.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblMayor, 0, 6);

        grupoMayor = new ToggleGroup();
        rbMayor = new RadioButton("Mayor");
        rbMenor = new RadioButton("Menor");

        rbMayor.setToggleGroup(grupoMayor);
        rbMenor.setToggleGroup(grupoMayor);

        rbMayor.setFont(Font.font("Times new roman", 14));
        rbMenor.setFont(Font.font("Times new roman", 14));

        Boolean esMayor = objPasajero.getEsMayorPasajero();
        if (Boolean.TRUE.equals(esMayor)) {
            rbMayor.setSelected(true);
        } else {
            rbMenor.setSelected(true);
        }

        HBox boxMayor = new HBox(15, rbMayor, rbMenor);
        boxMayor.setAlignment(Pos.CENTER_LEFT);
        miGrilla.add(boxMayor, 1, 6);

        // TELÉFONO
        Label lblTelefono = new Label("Teléfono:");
        lblTelefono.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblTelefono, 0, 7);

        txtTelefonoPasajero = new TextField();
        txtTelefonoPasajero.setText(objPasajero.getTelefonoPasajero());
        txtTelefonoPasajero.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtTelefonoPasajero, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtTelefonoPasajero, 15);
        // Formulario.soloNumeros(txtTelefonoPasajero); // si lo tienes
        miGrilla.add(txtTelefonoPasajero, 1, 7);

        // IMAGEN (nombre público)
        Label lblImagen = new Label("Imagen documento:");
        lblImagen.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblImagen, 0, 8);

        txtImagen = new TextField();
        txtImagen.setText(objPasajero.getNombreImagenPublicoPasajero());
        txtImagen.setDisable(true);
        txtImagen.setPrefHeight(ALTO_CAJA);

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
        miGrilla.add(panelImagen, 1, 8);

        // PREVISUALIZACIÓN INICIAL (usa nombre PRIVADO)
        imgPorDefecto = Icono.obtenerIcono("imgNoDisponible.png", 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        GridPane.setValignment(imgPorDefecto, VPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 5);

        Button btnActualizar = new Button("ACTUALIZAR");
        btnActualizar.setPrefHeight(ALTO_CAJA);
        btnActualizar.setMaxWidth(Double.MAX_VALUE);
        btnActualizar.setTextFill(Color.web("#FFFFFF"));
        btnActualizar.setFont(Font.font("Rockwell", FontWeight.BOLD, 16));
        btnActualizar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + ";");
        btnActualizar.setOnAction(e -> actualizarPasajero());
        miGrilla.add(btnActualizar, 1, 9);

        // BOTÓN REGRESAR
        Button btnRegresar = new Button("VOLVER");
        btnRegresar.setPrefHeight(ALTO_CAJA);
        btnRegresar.setMaxWidth(Double.MAX_VALUE);
        btnRegresar.setFont(Font.font("Rockwell", FontWeight.BOLD, 16));
        btnRegresar.setOnAction(e -> {
            if (desdeCarrusel) {
                panelCuerpo = PasajeroControladorVentana.carrusel(
                        miEscenario, panelPrincipal, panelCuerpo,
                        Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO, posicion);
            } else {
                panelCuerpo = PasajeroControladorVentana.administrar(
                        miEscenario, panelPrincipal, panelCuerpo,
                        Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO);
            }
            panelPrincipal.setCenter(null);
            panelPrincipal.setCenter(panelCuerpo);
        });
        miGrilla.add(btnRegresar, 1, 10);
    }

    private Boolean formularioCompleto() {
        Window ventana = this.getScene() != null ? this.getScene().getWindow() : null;

        if (txtNombrePasajero.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, ventana,
                    "Campo vacío", "Debe ingresar el nombre");
            txtNombrePasajero.requestFocus();
            return false;
        }

        if (txtDocumentoPasajero.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, ventana,
                    "Campo vacío", "Debe ingresar el documento");
            txtDocumentoPasajero.requestFocus();
            return false;
        }

        if (cmbTipoDocumento.getValue() == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, ventana,
                    "Tipo documento no seleccionado", "Debe seleccionar un tipo de documento");
            cmbTipoDocumento.requestFocus();
            return false;
        }

        if (dpFechaNacimiento.getValue() == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, ventana,
                    "Campo vacío", "Debe seleccionar la fecha de nacimiento");
            dpFechaNacimiento.requestFocus();
            return false;
        }

        if (grupoMayor.getSelectedToggle() == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, ventana,
                    "Campo vacío", "Debe seleccionar si es Mayor o Menor");
            return false;
        }

        if (txtTelefonoPasajero.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, ventana,
                    "Campo vacío", "Debe ingresar el teléfono");
            txtTelefonoPasajero.requestFocus();
            return false;
        }

        return true;
    }

    private void actualizarPasajero() {
        if (formularioCompleto()) {
            PasajeroDto dtoActualizado = new PasajeroDto();
            dtoActualizado.setIdPasajero(objPasajero.getIdPasajero());
            dtoActualizado.setNombrePasajero(txtNombrePasajero.getText());
            dtoActualizado.setDocumentoPasajero(txtDocumentoPasajero.getText());
            dtoActualizado.setTipoDocumentoPasajero(cmbTipoDocumento.getValue());
            dtoActualizado.setFechaNacimientoPasajero(dpFechaNacimiento.getValue());
            dtoActualizado.setEsMayorPasajero(rbMayor.isSelected());
            dtoActualizado.setTelefonoPasajero(txtTelefonoPasajero.getText());
            dtoActualizado.setNombreImagenPublicoPasajero(txtImagen.getText());
            dtoActualizado.setNombreImagenPrivadoPasajero(
                    objPasajero.getNombreImagenPrivadoPasajero()
            );

            if (PasajeroControladorEditar.actualizar(posicion, dtoActualizado, rutaImagenSeleccionada)) {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, this.getScene().getWindow(),
                        "Éxito", "Pasajero actualizado correctamente");
            } else {
                Mensaje.mostrar(Alert.AlertType.ERROR, this.getScene().getWindow(),
                        "Error", "No se pudo actualizar el pasajero");
            }
        }
    }
}
