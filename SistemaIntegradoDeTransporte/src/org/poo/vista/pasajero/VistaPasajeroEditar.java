package org.poo.vista.pasajero;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
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
    private static final int ALTO_CAJA = 35;
    private static final int TAMANIO_FUENTE = 18;

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
    private TextField txtEmailPasajero;
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
                Configuracion.DEGRADE_ARREGLO_CONDUCTOR, // o uno específico de pasajero si lo tienes
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
        Text titulo = new Text("Formulario Actualización de Pasajero");
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
        Label lblNombre = new Label("Nombre Pasajero:");
        lblNombre.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblNombre, primeraColumna, fila);

        txtNombrePasajero = new TextField();
        txtNombrePasajero.setText(objPasajero.getNombrePasajero());
        txtNombrePasajero.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtNombrePasajero, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtNombrePasajero, 50);
        miGrilla.add(txtNombrePasajero, segundaColumna, fila);

        // DOCUMENTO
        fila++;
        Label lblDocumento = new Label("Documento:");
        lblDocumento.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDocumento, primeraColumna, fila);

        txtDocumentoPasajero = new TextField();
        txtDocumentoPasajero.setText(objPasajero.getDocumentoPasajero());
        txtDocumentoPasajero.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtDocumentoPasajero, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtDocumentoPasajero, 20);
        // Formulario.soloNumeros(txtDocumentoPasajero); // si lo tienes
        miGrilla.add(txtDocumentoPasajero, segundaColumna, fila);

        // TIPO DOCUMENTO
        fila++;
        Label lblTipoDoc = new Label("Tipo documento:");
        lblTipoDoc.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblTipoDoc, primeraColumna, fila);

        cmbTipoDocumento = new ComboBox<>();
        cmbTipoDocumento.setPrefHeight(ALTO_CAJA);
        cmbTipoDocumento.setMaxWidth(Double.MAX_VALUE);
        cmbTipoDocumento.getItems().addAll("Cédula", "Pasaporte", "Tarjeta de identidad");
        if (objPasajero.getTipoDocumentoPasajero() != null) {
            cmbTipoDocumento.getSelectionModel().select(objPasajero.getTipoDocumentoPasajero());
        }
        miGrilla.add(cmbTipoDocumento, segundaColumna, fila);

        // FECHA NACIMIENTO
        fila++;
        Label lblFechaNac = new Label("Fecha Nacimiento:");
        lblFechaNac.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblFechaNac, primeraColumna, fila);

        dpFechaNacimiento = new DatePicker();
        dpFechaNacimiento.setPrefHeight(ALTO_CAJA);
        dpFechaNacimiento.setMaxWidth(Double.MAX_VALUE);
        dpFechaNacimiento.setValue(objPasajero.getFechaNacimientoPasajero());
        miGrilla.add(dpFechaNacimiento, segundaColumna, fila);

        // MAYOR / MENOR (RadioButton)
        fila++;
        Label lblMayor = new Label("Mayor de edad:");
        lblMayor.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblMayor, primeraColumna, fila);

        grupoMayor = new ToggleGroup();
        rbMayor = new RadioButton("Mayor");
        rbMenor = new RadioButton("Menor");

        rbMayor.setToggleGroup(grupoMayor);
        rbMenor.setToggleGroup(grupoMayor);

        rbMayor.setFont(Font.font("Arial", 14));
        rbMenor.setFont(Font.font("Arial", 14));

        Boolean esMayor = objPasajero.getEsMayorPasajero();
        if (Boolean.TRUE.equals(esMayor)) {
            rbMayor.setSelected(true);
        } else {
            rbMenor.setSelected(true);
        }

        HBox boxMayor = new HBox(15, rbMayor, rbMenor);
        boxMayor.setAlignment(Pos.CENTER_LEFT);
        miGrilla.add(boxMayor, segundaColumna, fila);

        // TELÉFONO
        fila++;
        Label lblTelefono = new Label("Teléfono:");
        lblTelefono.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblTelefono, primeraColumna, fila);

        txtTelefonoPasajero = new TextField();
        txtTelefonoPasajero.setText(objPasajero.getTelefonoPasajero());
        txtTelefonoPasajero.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtTelefonoPasajero, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtTelefonoPasajero, 15);
        // Formulario.soloNumeros(txtTelefonoPasajero); // si lo tienes
        miGrilla.add(txtTelefonoPasajero, segundaColumna, fila);

        // EMAIL
        fila++;
        Label lblEmail = new Label("Email:");
        lblEmail.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEmail, primeraColumna, fila);

        txtEmailPasajero = new TextField();
        txtEmailPasajero.setText(objPasajero.getEmailPasajero());
        txtEmailPasajero.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtEmailPasajero, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtEmailPasajero, 60);
        miGrilla.add(txtEmailPasajero, segundaColumna, fila);

        // IMAGEN (nombre público)
        fila++;
        Label lblImagen = new Label("Imagen documento:");
        lblImagen.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblImagen, primeraColumna, fila);

        txtImagen = new TextField();
        txtImagen.setText(objPasajero.getNombreImagenPublicoPasajero());
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

        // PREVISUALIZACIÓN INICIAL (usa nombre PRIVADO)
        fila++;
        imgPorDefecto = Icono.obtenerFotosExternas(
                objPasajero.getNombreImagenPrivadoPasajero(), 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        miGrilla.add(imgPorDefecto, segundaColumna, fila);

        // ESPACIO ADICIONAL
        fila++;

        // BOTÓN ACTUALIZAR
        fila++;
        Button btnActualizar = new Button("Actualizar Pasajero");
        btnActualizar.setPrefHeight(ALTO_CAJA);
        btnActualizar.setMaxWidth(Double.MAX_VALUE);
        btnActualizar.setTextFill(Color.web("#FFFFFF"));
        btnActualizar.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        btnActualizar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + ";");
        btnActualizar.setOnAction(e -> actualizarPasajero());
        miGrilla.add(btnActualizar, segundaColumna, fila);

        // BOTÓN REGRESAR
        fila++;
        Button btnRegresar = new Button("Regresar");
        btnRegresar.setPrefHeight(ALTO_CAJA);
        btnRegresar.setMaxWidth(Double.MAX_VALUE);
        btnRegresar.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
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
        miGrilla.add(btnRegresar, segundaColumna, fila);
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

        if (txtEmailPasajero.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, ventana,
                    "Campo vacío", "Debe ingresar el email");
            txtEmailPasajero.requestFocus();
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
            dtoActualizado.setEmailPasajero(txtEmailPasajero.getText());
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
