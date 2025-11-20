package org.poo.vista.ruta;

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
import org.poo.controlador.ruta.RutaControladorEditar;
import org.poo.controlador.ruta.RutaControladorVentana;
import org.poo.dto.RutaDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.*;

import java.time.LocalDate;
import javafx.geometry.Insets;
import javafx.scene.Cursor;

public class VistaRutaEditar extends StackPane {

    private static final int H_GAP = 10;
    private static final int V_GAP = 20;
    private static final int ALTO_FILA = 31;
    private static final int ALTO_CAJA = 32;
    private static final int TAMANIO_FUENTE = 18;
    private static final double AJUSTE_TITULO = 0.1;

    private final GridPane miGrilla;
    private final StackPane miFormulario;
    private final Rectangle miMarco;
    private final Stage miEscenario;

    private TextField txtNombreRuta;
    private TextField txtCiudadOrigen;
    private TextField txtCiudadDestino;
    private Spinner<Double> spinnerDistancia;
    private Spinner<Integer> spinnerDuracion;
    private ComboBox<String> cmbEstadoRuta;
    private DatePicker dateFechaCreacion;
    private CheckBox chkPeajes;
    private CheckBox chkCarreteraPrincipal;
    private CheckBox chkPavimentada;
    private TextArea txtDescripcion;
    
    private TextField txtImagen;
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private String rutaImagenSeleccionada;

    private final int posicion;
    private final RutaDto objRuta;
    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;
    private final boolean origenCarrusel;

    public VistaRutaEditar(Stage ventanaPadre, BorderPane princ, Pane pane,
            double ancho, double alto, RutaDto objRutaExterno, int posicionArchivo, boolean origenCarrusel) {

        miEscenario = ventanaPadre;
        miFormulario = this;
        miFormulario.setAlignment(Pos.CENTER);

        posicion = posicionArchivo;
        objRuta = objRutaExterno;
        panelPrincipal = princ;
        panelCuerpo = pane;
        this.origenCarrusel = origenCarrusel;
        rutaImagenSeleccionada = "";

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
        colocarFrmElegante();
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

        for (int i = 0; i < 12; i++) {
            RowConstraints fila = new RowConstraints();
            fila.setMinHeight(ALTO_FILA);
            fila.setMaxHeight(ALTO_FILA);
            fila.setVgrow(Priority.ALWAYS);
            miGrilla.getRowConstraints().add(fila);
        }
    }

    private void crearTitulo() {
        Text miTitulo = new Text("FORMULARIO - EDITAR RUTA");
        miTitulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        miTitulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 28));
        GridPane.setHalignment(miTitulo, HPos.CENTER);
        GridPane.setMargin(miTitulo, new Insets(30, 0, 0, 0));
        miGrilla.add(miTitulo, 0, 0, 3, 2);//nombre, columna, fila, union de filas 
    }

    private void crearFormulario() {

        Label lblNombre = new Label("Nombre de la Ruta:");
        lblNombre.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblNombre, 0, 2);

        txtNombreRuta = new TextField();
        txtNombreRuta.setText(objRuta.getNombreRuta());
        txtNombreRuta.setPrefHeight(ALTO_CAJA);
        txtNombreRuta.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        txtNombreRuta.setMinHeight(Region.USE_PREF_SIZE);
        GridPane.setHgrow(txtNombreRuta, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtNombreRuta, 100);
        miGrilla.add(txtNombreRuta, 1, 2);

        Label lblOrigen = new Label("Ciudad de Origen:");
        lblOrigen.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblOrigen, 0, 3);

        txtCiudadOrigen = new TextField();
        txtCiudadOrigen.setText(objRuta.getCiudadOrigenRuta());
        txtCiudadOrigen.setPrefHeight(ALTO_CAJA);
        txtCiudadOrigen.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        txtCiudadOrigen.setMinHeight(Region.USE_PREF_SIZE);
        GridPane.setHgrow(txtCiudadOrigen, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtCiudadOrigen, 50);
        miGrilla.add(txtCiudadOrigen, 1, 3);

        Label lblDestino = new Label("Ciudad de Destino:");
        lblDestino.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDestino, 0, 4);

        txtCiudadDestino = new TextField();
        txtCiudadDestino.setText(objRuta.getCiudadDestinoRuta());
        txtCiudadDestino.setPrefHeight(ALTO_CAJA);
        txtCiudadDestino.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        txtCiudadDestino.setMinHeight(Region.USE_PREF_SIZE);
        GridPane.setHgrow(txtCiudadDestino, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtCiudadDestino, 50);
        miGrilla.add(txtCiudadDestino, 1, 4);

        Label lblDistancia = new Label("Distancia (Km):");
        lblDistancia.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDistancia, 0, 5);

        spinnerDistancia = new Spinner<>();
        SpinnerValueFactory<Double> valueFactoryDistancia = 
            new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 5000.0, 
                objRuta.getDistanciaKmRuta() != null ? objRuta.getDistanciaKmRuta() : 100.0, 10.0);
        spinnerDistancia.setValueFactory(valueFactoryDistancia);
        spinnerDistancia.setPrefHeight(ALTO_CAJA);
        spinnerDistancia.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        spinnerDistancia.setMinHeight(Region.USE_PREF_SIZE);
        spinnerDistancia.setEditable(true);
        miGrilla.add(spinnerDistancia, 1, 5);

        Label lblDuracion = new Label("Duración (Horas):");
        lblDuracion.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDuracion, 0, 6);

        spinnerDuracion = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactoryDuracion = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 48, 
                objRuta.getDuracionHorasRuta() != null ? objRuta.getDuracionHorasRuta() : 4, 1);
        spinnerDuracion.setValueFactory(valueFactoryDuracion);
        spinnerDuracion.setPrefHeight(ALTO_CAJA);
        spinnerDuracion.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        spinnerDuracion.setMinHeight(Region.USE_PREF_SIZE);
        spinnerDuracion.setEditable(true);
        miGrilla.add(spinnerDuracion, 1, 6);

        Label lblFechaCreacion = new Label("Fecha de Creación:");
        lblFechaCreacion.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblFechaCreacion, 0, 7);

        dateFechaCreacion = new DatePicker();
        dateFechaCreacion.setMaxWidth(Double.MAX_VALUE);
        dateFechaCreacion.setPrefHeight(ALTO_CAJA);
        dateFechaCreacion.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        dateFechaCreacion.setMinHeight(Region.USE_PREF_SIZE);
        dateFechaCreacion.setValue(LocalDate.now().minusMonths(6));
        Formulario.deshabilitarFechasFuturas(dateFechaCreacion);
        miGrilla.add(dateFechaCreacion, 1, 7);

        Label lblCaracteristicas = new Label("Características:");
        lblCaracteristicas.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblCaracteristicas, 0, 8);

        chkPeajes = new CheckBox("Tiene Peajes");
        chkCarreteraPrincipal = new CheckBox("Carretera Principal");
        chkPavimentada = new CheckBox("Pavimentada");
        
        chkPeajes.setFont(Font.font("Times new roman", 14));
        chkCarreteraPrincipal.setFont(Font.font("Times new roman", 14));
        chkPavimentada.setFont(Font.font("Times new roman", 14));

        VBox vboxCaracteristicas = new VBox(5);
        vboxCaracteristicas.getChildren().addAll(chkPeajes, chkCarreteraPrincipal, chkPavimentada);
        miGrilla.add(vboxCaracteristicas, 1, 8);

        Label lblEstado = new Label("Estado:");
        lblEstado.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEstado, 0, 9);

        cmbEstadoRuta = new ComboBox<>();
        cmbEstadoRuta.setMaxWidth(Double.MAX_VALUE);
        cmbEstadoRuta.setPrefHeight(ALTO_CAJA);
        cmbEstadoRuta.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        cmbEstadoRuta.setMinHeight(Region.USE_PREF_SIZE);
        cmbEstadoRuta.getItems().addAll("Seleccione estado", "Activa", "Inactiva");
        
        if (objRuta.getEstadoRuta()) {
            cmbEstadoRuta.getSelectionModel().select(1);
        } else {
            cmbEstadoRuta.getSelectionModel().select(2);
        }
        miGrilla.add(cmbEstadoRuta, 1, 9);

        Label lblImagen = new Label("Imagen:");
        lblImagen.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblImagen, 0, 10);

        txtImagen = new TextField();
        txtImagen.setText(objRuta.getNombreImagenPublicoRuta());
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
            
            if (rutaImagenSeleccionada.isEmpty()) {
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                imgPorDefecto = Icono.obtenerFotosExternas(objRuta.getNombreImagenPrivadoRuta(), 150);
                GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
                miGrilla.add(imgPorDefecto, 2, 5);
            } else {
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                imgPrevisualizar = Icono.previsualizar(rutaImagenSeleccionada, 150);
                GridPane.setHalignment(imgPrevisualizar, HPos.CENTER);
                miGrilla.add(imgPrevisualizar, 2, 5);
            }
        });

        HBox.setHgrow(txtImagen, Priority.ALWAYS);
        HBox panelImagen = new HBox(2, txtImagen, btnSeleccionarImagen);
        panelImagen.setAlignment(Pos.BOTTOM_RIGHT);
        miGrilla.add(panelImagen, 1, 10);

        // Imagen en columna derecha
        imgPorDefecto = Icono.obtenerFotosExternas(
                objRuta.getNombreImagenPrivadoRuta(), 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        GridPane.setValignment(imgPorDefecto, javafx.geometry.VPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 5);

        Button btnActualizar = new Button("ACTUALIZAR");
        btnActualizar.setPrefHeight(ALTO_CAJA);
        btnActualizar.setMaxWidth(Double.MAX_VALUE);
        btnActualizar.setTextFill(Color.web("#FFFFFF"));
        btnActualizar.setFont(Font.font("Rockwell", FontWeight.BOLD, 14));
        btnActualizar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + ";");
        btnActualizar.setCursor(Cursor.HAND);
        btnActualizar.setOnAction(e -> actualizarRuta());
        miGrilla.add(btnActualizar, 1, 11);

        Button btnRegresar = new Button("VOLVER");
        btnRegresar.setPrefHeight(ALTO_CAJA);
        btnRegresar.setMaxWidth(Double.MAX_VALUE);
        btnRegresar.setFont(Font.font("Rockwell", FontWeight.BOLD, 14));
        btnRegresar.setCursor(Cursor.HAND);
        btnRegresar.setOnAction(e -> {
            if (origenCarrusel) {
                panelCuerpo = RutaControladorVentana.carrusel(
                        miEscenario, panelPrincipal, panelCuerpo,
                        Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO, posicion);
            } else {
                panelCuerpo = RutaControladorVentana.administrar(
                        miEscenario, panelPrincipal, panelCuerpo,
                        Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO);
            }
            panelPrincipal.setCenter(null);
            panelPrincipal.setCenter(panelCuerpo);
        });
        miGrilla.add(btnRegresar, 1, 12);
    }

    private Boolean formularioCompleto() {
        if (txtNombreRuta.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar el nombre de la ruta");
            txtNombreRuta.requestFocus();
            return false;
        }

        if (txtCiudadOrigen.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar la ciudad de origen");
            txtCiudadOrigen.requestFocus();
            return false;
        }

        if (txtCiudadDestino.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar la ciudad de destino");
            txtCiudadDestino.requestFocus();
            return false;
        }

        if (cmbEstadoRuta.getSelectionModel().getSelectedIndex() == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Estado no seleccionado", "Debe seleccionar un estado");
            return false;
        }

        return true;
    }

    private void actualizarRuta() {
        if (formularioCompleto()) {
            RutaDto dtoActualizado = new RutaDto();
            dtoActualizado.setIdRuta(objRuta.getIdRuta());
            dtoActualizado.setNombreRuta(txtNombreRuta.getText());
            dtoActualizado.setCiudadOrigenRuta(txtCiudadOrigen.getText());
            dtoActualizado.setCiudadDestinoRuta(txtCiudadDestino.getText());
            dtoActualizado.setDistanciaKmRuta(spinnerDistancia.getValue());
            dtoActualizado.setDuracionHorasRuta(spinnerDuracion.getValue());
            dtoActualizado.setEstadoRuta(cmbEstadoRuta.getValue().equals("Activa"));
            dtoActualizado.setNombreImagenPublicoRuta(txtImagen.getText());
            dtoActualizado.setNombreImagenPrivadoRuta(objRuta.getNombreImagenPrivadoRuta());

            if (RutaControladorEditar.actualizarRuta(posicion, dtoActualizado, rutaImagenSeleccionada)) {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, this.getScene().getWindow(),
                        "Éxito", "Ruta actualizada correctamente");
            } else {
                Mensaje.mostrar(Alert.AlertType.ERROR, this.getScene().getWindow(),
                        "Error", "No se pudo actualizar la ruta");
            }
        }
    }

    private void colocarFrmElegante() {
        Runnable calcular = () -> {
            double alturaMarco = miMarco.getHeight();
            if (alturaMarco > 0) {
                double desplazamiento = alturaMarco * AJUSTE_TITULO;
                miGrilla.setTranslateY(-alturaMarco / 8 + desplazamiento);
            }
        };
        calcular.run();
        miMarco.heightProperty().addListener((obs, antes, despues) -> calcular.run());
    }
}
