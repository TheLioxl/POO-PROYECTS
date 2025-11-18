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

public class VistaRutaEditar extends StackPane {

    private static final int H_GAP = 10;
    private static final int V_GAP = 15;
    private static final int ALTO_CAJA = 35;
    private static final int TAMANIO_FUENTE = 18;

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
        espacioSuperior.prefHeightProperty().bind(miEscenario.heightProperty().multiply(0.03));
        miGrilla.add(espacioSuperior, columna, fila, colSpan, rowSpan);

        fila = 1;
        Text titulo = new Text("Formulario Actualización de Ruta");
        titulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        GridPane.setHalignment(titulo, HPos.CENTER);
        miGrilla.add(titulo, columna, fila, colSpan, rowSpan);
    }

    private void crearFormulario() {
        int fila = 2;
        int primeraColumna = 0;
        int segundaColumna = 1;

        fila++;
        Label lblNombre = new Label("Nombre de la Ruta:");
        lblNombre.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblNombre, primeraColumna, fila);

        txtNombreRuta = new TextField();
        txtNombreRuta.setText(objRuta.getNombreRuta());
        txtNombreRuta.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtNombreRuta, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtNombreRuta, 100);
        miGrilla.add(txtNombreRuta, segundaColumna, fila);

        fila++;
        Label lblOrigen = new Label("Ciudad de Origen:");
        lblOrigen.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblOrigen, primeraColumna, fila);

        txtCiudadOrigen = new TextField();
        txtCiudadOrigen.setText(objRuta.getCiudadOrigenRuta());
        txtCiudadOrigen.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtCiudadOrigen, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtCiudadOrigen, 50);
        miGrilla.add(txtCiudadOrigen, segundaColumna, fila);

        fila++;
        Label lblDestino = new Label("Ciudad de Destino:");
        lblDestino.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDestino, primeraColumna, fila);

        txtCiudadDestino = new TextField();
        txtCiudadDestino.setText(objRuta.getCiudadDestinoRuta());
        txtCiudadDestino.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtCiudadDestino, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtCiudadDestino, 50);
        miGrilla.add(txtCiudadDestino, segundaColumna, fila);

        fila++;
        Label lblDistancia = new Label("Distancia (Km):");
        lblDistancia.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDistancia, primeraColumna, fila);

        spinnerDistancia = new Spinner<>();
        SpinnerValueFactory<Double> valueFactoryDistancia = 
            new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 5000.0, 
                objRuta.getDistanciaKmRuta() != null ? objRuta.getDistanciaKmRuta() : 100.0, 10.0);
        spinnerDistancia.setValueFactory(valueFactoryDistancia);
        spinnerDistancia.setPrefHeight(ALTO_CAJA);
        spinnerDistancia.setMaxWidth(Double.MAX_VALUE);
        spinnerDistancia.setEditable(true);
        miGrilla.add(spinnerDistancia, segundaColumna, fila);

        fila++;
        Label lblDuracion = new Label("Duración (Horas):");
        lblDuracion.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDuracion, primeraColumna, fila);

        spinnerDuracion = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactoryDuracion = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 48, 
                objRuta.getDuracionHorasRuta() != null ? objRuta.getDuracionHorasRuta() : 4, 1);
        spinnerDuracion.setValueFactory(valueFactoryDuracion);
        spinnerDuracion.setPrefHeight(ALTO_CAJA);
        spinnerDuracion.setMaxWidth(Double.MAX_VALUE);
        spinnerDuracion.setEditable(true);
        miGrilla.add(spinnerDuracion, segundaColumna, fila);

        fila++;
        Label lblFechaCreacion = new Label("Fecha de Creación:");
        lblFechaCreacion.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblFechaCreacion, primeraColumna, fila);

        dateFechaCreacion = new DatePicker();
        dateFechaCreacion.setMaxWidth(Double.MAX_VALUE);
        dateFechaCreacion.setPrefHeight(ALTO_CAJA);
        dateFechaCreacion.setValue(LocalDate.now().minusMonths(6));
        Formulario.deshabilitarFechasFuturas(dateFechaCreacion);
        miGrilla.add(dateFechaCreacion, segundaColumna, fila);

        fila++;
        Label lblCaracteristicas = new Label("Características:");
        lblCaracteristicas.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblCaracteristicas, primeraColumna, fila);

        chkPeajes = new CheckBox("Tiene Peajes");
        chkCarreteraPrincipal = new CheckBox("Carretera Principal");
        chkPavimentada = new CheckBox("Pavimentada");
        
        chkPeajes.setFont(Font.font("Arial", 14));
        chkCarreteraPrincipal.setFont(Font.font("Arial", 14));
        chkPavimentada.setFont(Font.font("Arial", 14));

        VBox vboxCaracteristicas = new VBox(5);
        vboxCaracteristicas.getChildren().addAll(chkPeajes, chkCarreteraPrincipal, chkPavimentada);
        miGrilla.add(vboxCaracteristicas, segundaColumna, fila);

        fila++;
        Label lblDescripcion = new Label("Descripción:");
        lblDescripcion.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDescripcion, primeraColumna, fila);

        txtDescripcion = new TextArea();
        txtDescripcion.setPrefRowCount(3);
        txtDescripcion.setWrapText(true);
        txtDescripcion.setMaxWidth(Double.MAX_VALUE);
        miGrilla.add(txtDescripcion, segundaColumna, fila);

        fila++;
        Label lblEstado = new Label("Estado:");
        lblEstado.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEstado, primeraColumna, fila);

        cmbEstadoRuta = new ComboBox<>();
        cmbEstadoRuta.setMaxWidth(Double.MAX_VALUE);
        cmbEstadoRuta.setPrefHeight(ALTO_CAJA);
        cmbEstadoRuta.getItems().addAll("Seleccione estado", "Activa", "Inactiva");
        
        if (objRuta.getEstadoRuta()) {
            cmbEstadoRuta.getSelectionModel().select(1);
        } else {
            cmbEstadoRuta.getSelectionModel().select(2);
        }
        miGrilla.add(cmbEstadoRuta, segundaColumna, fila);

        fila++;
        Label lblImagen = new Label("Imagen:");
        lblImagen.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblImagen, primeraColumna, fila);

        txtImagen = new TextField();
        txtImagen.setText(objRuta.getNombreImagenPublicoRuta());
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

        fila++;
        imgPorDefecto = Icono.obtenerFotosExternas(
                objRuta.getNombreImagenPrivadoRuta(), 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        miGrilla.add(imgPorDefecto, segundaColumna, fila);

        fila++;
        Button btnActualizar = new Button("Actualizar Ruta");
        btnActualizar.setPrefHeight(ALTO_CAJA);
        btnActualizar.setMaxWidth(Double.MAX_VALUE);
        btnActualizar.setTextFill(Color.web("#FFFFFF"));
        btnActualizar.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        btnActualizar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + ";");
        btnActualizar.setOnAction(e -> actualizarRuta());
        miGrilla.add(btnActualizar, segundaColumna, fila);

        fila++;
        Button btnRegresar = new Button("Regresar");
        btnRegresar.setPrefHeight(ALTO_CAJA);
        btnRegresar.setMaxWidth(Double.MAX_VALUE);
        btnRegresar.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
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
        miGrilla.add(btnRegresar, segundaColumna, fila);
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
}
