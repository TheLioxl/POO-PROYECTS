package org.poo.vista.terminal;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
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
    private static final int ALTO_FILA = 40;
    private static final int ALTO_CAJA = 35;
    private static final int TAMANIO_FUENTE = 20;
    private static final double AJUSTE_TITULO = 0.1;

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
    private final boolean desdeCarrusel; // NUEVO: Para saber de dónde viene

    public VistaTerminalEditar(Stage ventanaPadre, BorderPane princ, Pane pane,
            double ancho, double alto, TerminalDto objTerminalExterno, int posicionArchivo,
            boolean vieneDeCarrusel) { // NUEVO PARÁMETRO

        miEscenario = ventanaPadre;
        miFormulario = this;
        miFormulario.setAlignment(Pos.CENTER);

        posicion = posicionArchivo;
        objTerminal = objTerminalExterno;
        panelPrincipal = princ;
        panelCuerpo = pane;
        rutaImagenSeleccionada = "";
        desdeCarrusel = vieneDeCarrusel; // GUARDAR ORIGEN

        miGrilla = new GridPane();
        miMarco = Marco.crear(
                miEscenario,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_RUTA,
                Configuracion.DEGRADE_BORDE
        );

        getChildren().add(miMarco);

        configurarGrilla(ancho, alto);
        crearTitulo();
        crearFormulario();
        colocarFrmElegante();
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

        for (int i = 0; i < 12; i++) {
            RowConstraints fila = new RowConstraints();
            fila.setMinHeight(ALTO_FILA);
            fila.setMaxHeight(ALTO_FILA);
            fila.setVgrow(Priority.ALWAYS);
            miGrilla.getRowConstraints().add(fila);
        }
    }

    private void crearTitulo() {
        Text miTitulo = new Text("FORMULARIO - EDITAR TERMINAL");
        miTitulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        miTitulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 26));
        GridPane.setHalignment(miTitulo, HPos.CENTER);
        GridPane.setMargin(miTitulo, new Insets(30, 0, 0, 0));
        miGrilla.add(miTitulo, 0, 0, 3, 2);//nombre, columna, fila, union de filas 
    }

    private void crearFormulario() {

        Label lblNombre = new Label("Nombre Terminal:");
        lblNombre.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblNombre, 0, 2);

        txtNombreTerminal = new TextField();
        txtNombreTerminal.setText(objTerminal.getNombreTerminal());
        txtNombreTerminal.setPrefHeight(ALTO_CAJA);
        txtNombreTerminal.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        txtNombreTerminal.setMinHeight(Region.USE_PREF_SIZE);
        GridPane.setHgrow(txtNombreTerminal, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtNombreTerminal, 50);
        miGrilla.add(txtNombreTerminal, 1, 2);

        // CIUDAD
        Label lblCiudad = new Label("Ciudad:");
        lblCiudad.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblCiudad, 0, 3);

        txtCiudadTerminal = new TextField();
        txtCiudadTerminal.setText(objTerminal.getCiudadTerminal());
        txtCiudadTerminal.setPrefHeight(ALTO_CAJA);
        txtCiudadTerminal.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        txtCiudadTerminal.setMinHeight(Region.USE_PREF_SIZE);
        GridPane.setHgrow(txtCiudadTerminal, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtCiudadTerminal, 30);
        Formulario.soloLetras(txtCiudadTerminal);
        miGrilla.add(txtCiudadTerminal, 1, 3);

        // DIRECCIÓN
        Label lblDireccion = new Label("Dirección:");
        lblDireccion.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDireccion, 0, 4);

        txtDireccionTerminal = new TextField();
        txtDireccionTerminal.setText(objTerminal.getDireccionTerminal());
        txtDireccionTerminal.setPrefHeight(ALTO_CAJA);
        txtDireccionTerminal.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        txtDireccionTerminal.setMinHeight(Region.USE_PREF_SIZE);
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
        cmbEstadoTerminal.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        cmbEstadoTerminal.setMinHeight(Region.USE_PREF_SIZE);
        cmbEstadoTerminal.getItems().addAll("Seleccione estado", "Activo", "Inactivo");
        
        if (objTerminal.getEstadoTerminal()) {
            cmbEstadoTerminal.getSelectionModel().select(1);
        } else {
            cmbEstadoTerminal.getSelectionModel().select(2);
        }
        miGrilla.add(cmbEstadoTerminal, 1, 5);

        // PLATAFORMAS (Spinner)
        Label lblPlataformas = new Label("Plataformas/Muelles:");
        lblPlataformas.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblPlataformas, 0, 6);

        spinnerPlataformas = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 
                objTerminal.getNumeroPlataformas() != null ? objTerminal.getNumeroPlataformas() : 10);
        spinnerPlataformas.setValueFactory(valueFactory);
        spinnerPlataformas.setPrefHeight(ALTO_CAJA);
        spinnerPlataformas.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        spinnerPlataformas.setMinHeight(Region.USE_PREF_SIZE);
        spinnerPlataformas.setEditable(true);
        miGrilla.add(spinnerPlataformas, 1, 6);

        // SERVICIOS (CheckBox)
        Label lblServicios = new Label("Servicios disponibles:");
        lblServicios.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblServicios, 0, 7);

        chkWifi = new CheckBox("WiFi Gratis");
        chkCafeteria = new CheckBox("Cafetería");
        chkBanos = new CheckBox("Baños");
        
        // Cargar valores del objeto
        chkWifi.setSelected(objTerminal.getTieneWifi() != null ? objTerminal.getTieneWifi() : false);
        chkCafeteria.setSelected(objTerminal.getTieneCafeteria() != null ? objTerminal.getTieneCafeteria() : false);
        chkBanos.setSelected(objTerminal.getTieneBanos() != null ? objTerminal.getTieneBanos() : false);
        
        chkWifi.setFont(Font.font("Times new roman", 14));
        chkCafeteria.setFont(Font.font("Times new roman", 14));
        chkBanos.setFont(Font.font("Times new roman", 14));

        VBox vboxServicios = new VBox(5);
        vboxServicios.getChildren().addAll(chkWifi, chkCafeteria, chkBanos);
        miGrilla.add(vboxServicios, 1, 7);

        // IMAGEN
        Label lblImagen = new Label("Imagen:");
        lblImagen.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblImagen, 0, 8);

        txtImagen = new TextField();
        txtImagen.setText(objTerminal.getNombreImagenPublicoTerminal());
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
        miGrilla.add(panelImagen, 1, 8);

        // PREVISUALIZACIÓN
        imgPorDefecto = Icono.obtenerFotosExternas(
                objTerminal.getNombreImagenPrivadoTerminal(), 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 5);

        // BOTÓN ACTUALIZAR
        Button btnActualizar = new Button("ACTUALIZAR");
        btnActualizar.setPrefHeight(ALTO_CAJA);
        btnActualizar.setMaxWidth(Double.MAX_VALUE);
        btnActualizar.setTextFill(Color.web("#FFFFFF"));
        btnActualizar.setFont(Font.font("Rockwell", FontWeight.BOLD, 14));
        btnActualizar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + ";");
        btnActualizar.setCursor(Cursor.HAND);
        btnActualizar.setOnAction(e -> actualizarTerminal());
        miGrilla.add(btnActualizar, 1, 9);

        // BOTÓN REGRESAR - MODIFICADO PARA DETECTAR ORIGEN
        Button btnRegresar = new Button("VOLVER");
        btnRegresar.setPrefHeight(ALTO_CAJA);
        btnRegresar.setMaxWidth(Double.MAX_VALUE);
        btnRegresar.setFont(Font.font("Rockwell", FontWeight.BOLD, 14));
        btnRegresar.setCursor(Cursor.HAND);
        btnRegresar.setOnAction(e -> {
            // VERIFICAR SI VIENE DEL CARRUSEL
            if (desdeCarrusel) {
                // Regresar al carrusel en la misma posición
                panelCuerpo = TerminalControladorVentana.carrusel(
                        miEscenario, panelPrincipal, panelCuerpo,
                        Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO, posicion);
            } else {
                // Regresar a administrar
                panelCuerpo = TerminalControladorVentana.administrar(
                        miEscenario, panelPrincipal, panelCuerpo,
                        Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO);
            }
            panelPrincipal.setCenter(null);
            panelPrincipal.setCenter(panelCuerpo);
        });
        miGrilla.add(btnRegresar, 1, 10);
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
