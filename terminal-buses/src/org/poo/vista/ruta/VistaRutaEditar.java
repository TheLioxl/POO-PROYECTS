package org.poo.vista.ruta;

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
import org.poo.controlador.ruta.RutaControladorEditar;
import org.poo.controlador.ruta.RutaControladorVentana;
import org.poo.dto.RutaDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.*;

import java.io.File;
import java.time.LocalDate;

public class VistaRutaEditar extends StackPane {

    private final Rectangle marco;
    private final Stage miEscenario;
    private final VBox cajaVertical;

    private TextField txtNombreRuta;
    private TextField txtCiudadOrigen;
    private TextField txtCiudadDestino;
    private Spinner<Double> spinnerDistancia;
    private Spinner<Integer> spinnerDuracion;
    private ComboBox<String> cmbEstado;
    private DatePicker dateFechaCreacion;
    private CheckBox chkPeaje;
    private CheckBox chkCarretera;
    private TextArea txtDescripcion;
    private TextField txtNombreImagen;
    private ImageView imgPrevisualizar;

    private String rutaImagenSeleccionada = "";
    private final int posicion;
    private final RutaDto objRuta;
    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;
    private final boolean origenCarrusel;

    public VistaRutaEditar(Stage ventanaPadre, BorderPane princ, Pane pane,
            double ancho, double alto, RutaDto objRutaExterno, int posicionArchivo, boolean origenCarrusel) {

        miEscenario = ventanaPadre;
        posicion = posicionArchivo;
        objRuta = objRutaExterno;
        panelPrincipal = princ;
        panelCuerpo = pane;
        this.origenCarrusel = origenCarrusel;

        setAlignment(Pos.CENTER);
        
        marco = Marco.crear(
                miEscenario,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_RUTA,
                Configuracion.DEGRADE_BORDE
        );

        cajaVertical = new VBox(15);
        getChildren().add(marco);

        configurarCajaVertical();
        crearTitulo();
        crearFormulario();
        cargarDatos();
        crearBotones();
    }

    private void configurarCajaVertical() {
        cajaVertical.setAlignment(Pos.TOP_CENTER);
        cajaVertical.prefWidthProperty().bind(miEscenario.widthProperty());
        cajaVertical.prefHeightProperty().bind(miEscenario.heightProperty());
        cajaVertical.setPadding(new Insets(20));
    }

    private void crearTitulo() {
        Text titulo = new Text("Editar Ruta #" + objRuta.getIdRuta());
        titulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        cajaVertical.getChildren().add(titulo);
    }

    private void crearFormulario() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(15);
        grid.setVgap(12);
        grid.setPadding(new Insets(20));
        grid.setMaxWidth(700);

        // Nombre de la Ruta
        Label lblNombre = Formulario.crearEtiqueta("Nombre de la Ruta:");
        txtNombreRuta = new TextField();
        txtNombreRuta.setPrefWidth(300);
        grid.add(lblNombre, 0, 0);
        grid.add(txtNombreRuta, 1, 0);

        // Ciudad Origen
        Label lblOrigen = Formulario.crearEtiqueta("Ciudad de Origen:");
        txtCiudadOrigen = new TextField();
        txtCiudadOrigen.setPrefWidth(300);
        grid.add(lblOrigen, 0, 1);
        grid.add(txtCiudadOrigen, 1, 1);

        // Ciudad Destino
        Label lblDestino = Formulario.crearEtiqueta("Ciudad de Destino:");
        txtCiudadDestino = new TextField();
        txtCiudadDestino.setPrefWidth(300);
        grid.add(lblDestino, 0, 2);
        grid.add(txtCiudadDestino, 1, 2);

        // Distancia
        Label lblDistancia = Formulario.crearEtiqueta("Distancia (Km):");
        SpinnerValueFactory<Double> factoryDistancia = 
            new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 10000.0, 100.0, 10.0);
        spinnerDistancia = new Spinner<>(factoryDistancia);
        spinnerDistancia.setEditable(true);
        spinnerDistancia.setPrefWidth(150);
        grid.add(lblDistancia, 0, 3);
        grid.add(spinnerDistancia, 1, 3);

        // Duración
        Label lblDuracion = Formulario.crearEtiqueta("Duración (Horas):");
        SpinnerValueFactory<Integer> factoryDuracion = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 48, 4, 1);
        spinnerDuracion = new Spinner<>(factoryDuracion);
        spinnerDuracion.setEditable(true);
        spinnerDuracion.setPrefWidth(150);
        grid.add(lblDuracion, 0, 4);
        grid.add(spinnerDuracion, 1, 4);

        // Estado
        Label lblEstado = Formulario.crearEtiqueta("Estado:");
        cmbEstado = new ComboBox<>();
        cmbEstado.getItems().addAll("Activa", "Inactiva");
        cmbEstado.setValue("Activa");
        cmbEstado.setPrefWidth(150);
        grid.add(lblEstado, 0, 5);
        grid.add(cmbEstado, 1, 5);

        // Fecha
        Label lblFecha = Formulario.crearEtiqueta("Fecha de Creación:");
        dateFechaCreacion = new DatePicker(LocalDate.now());
        dateFechaCreacion.setPrefWidth(200);
        grid.add(lblFecha, 0, 6);
        grid.add(dateFechaCreacion, 1, 6);

        // CheckBoxes
        Label lblCaracteristicas = Formulario.crearEtiqueta("Características:");
        HBox cajaCheques = new HBox(15);
        chkPeaje = new CheckBox("Tiene Peajes");
        chkCarretera = new CheckBox("Carretera Principal");
        cajaCheques.getChildren().addAll(chkPeaje, chkCarretera);
        grid.add(lblCaracteristicas, 0, 7);
        grid.add(cajaCheques, 1, 7);

        // Descripción
        Label lblDescripcion = Formulario.crearEtiqueta("Descripción:");
        txtDescripcion = new TextArea();
        txtDescripcion.setPrefRowCount(3);
        txtDescripcion.setPrefWidth(300);
        txtDescripcion.setWrapText(true);
        grid.add(lblDescripcion, 0, 8);
        grid.add(txtDescripcion, 1, 8);

        // Imagen
        Label lblImagen = Formulario.crearEtiqueta("Imagen:");
        txtNombreImagen = new TextField();
        txtNombreImagen.setPrefWidth(180);
        
        Button btnBuscarImagen = new Button("Cambiar");
        btnBuscarImagen.setCursor(Cursor.HAND);
        btnBuscarImagen.setOnAction(e -> buscarImagen());
        
        HBox cajaImagen = new HBox(10);
        cajaImagen.getChildren().addAll(txtNombreImagen, btnBuscarImagen);
        grid.add(lblImagen, 0, 9);
        grid.add(cajaImagen, 1, 9);

        // Previsualización de imagen
        imgPrevisualizar = new ImageView();
        imgPrevisualizar.setFitHeight(100);
        imgPrevisualizar.setFitWidth(150);
        imgPrevisualizar.setPreserveRatio(true);
        grid.add(imgPrevisualizar, 1, 10);

        cajaVertical.getChildren().add(grid);
    }

    private void buscarImagen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen de la Ruta");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File archivoSeleccionado = fileChooser.showOpenDialog(miEscenario);
        
        if (archivoSeleccionado != null) {
            rutaImagenSeleccionada = archivoSeleccionado.getAbsolutePath();
            txtNombreImagen.setText(archivoSeleccionado.getName());
            try {
                imgPrevisualizar.setImage(new javafx.scene.image.Image("file:" + rutaImagenSeleccionada));
            } catch (Exception e) {
                System.out.println("Error al cargar imagen: " + e.getMessage());
            }
        }
    }

    private void cargarDatos() {
        txtNombreRuta.setText(objRuta.getNombreRuta());
        txtCiudadOrigen.setText(objRuta.getCiudadOrigenRuta());
        txtCiudadDestino.setText(objRuta.getCiudadDestinoRuta());
        spinnerDistancia.getValueFactory().setValue(objRuta.getDistanciaKmRuta());
        spinnerDuracion.getValueFactory().setValue(objRuta.getDuracionHorasRuta());
        cmbEstado.setValue(objRuta.getEstadoRuta() ? "Activa" : "Inactiva");
        txtNombreImagen.setText(objRuta.getNombreImagenPublicoRuta());

        try {
            imgPrevisualizar.setImage(Icono.obtenerFotosExternas(objRuta.getNombreImagenPrivadoRuta(), 100).getImage());
        } catch (Exception e) {
            imgPrevisualizar.setImage(Icono.obtenerIcono(Configuracion.ICONO_NO_DISPONIBLE, 100).getImage());
        }
    }

    private void crearBotones() {
        HBox cajaBotones = new HBox(15);
        cajaBotones.setAlignment(Pos.CENTER);
        cajaBotones.setPadding(new Insets(10));

        Button btnActualizar = new Button("Actualizar");
        btnActualizar.setCursor(Cursor.HAND);
        btnActualizar.setPrefWidth(150);
        btnActualizar.setStyle("-fx-background-color: " + Configuracion.VERDE_EXITO + "; -fx-text-fill: white;");
        btnActualizar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_GUARDAR, 16));
        btnActualizar.setOnAction(e -> actualizarRuta());

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setCursor(Cursor.HAND);
        btnCancelar.setPrefWidth(120);
        btnCancelar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_CANCELAR, 16));
        btnCancelar.setOnAction(e -> cancelar());

        cajaBotones.getChildren().addAll(btnActualizar, btnCancelar);
        cajaVertical.getChildren().add(cajaBotones);

        getChildren().add(cajaVertical);
    }

    private void actualizarRuta() {
        if (txtNombreRuta.getText().trim().isEmpty()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, miEscenario,
                "Advertencia", "Debe ingresar el nombre de la ruta");
            return;
        }

        objRuta.setNombreRuta(txtNombreRuta.getText().trim());
        objRuta.setCiudadOrigenRuta(txtCiudadOrigen.getText().trim());
        objRuta.setCiudadDestinoRuta(txtCiudadDestino.getText().trim());
        objRuta.setDistanciaKmRuta(spinnerDistancia.getValue());
        objRuta.setDuracionHorasRuta(spinnerDuracion.getValue());
        objRuta.setEstadoRuta(cmbEstado.getValue().equals("Activa"));
        objRuta.setNombreImagenPublicoRuta(txtNombreImagen.getText().trim());

        Boolean resultado = RutaControladorEditar.actualizarRuta(posicion, objRuta, rutaImagenSeleccionada);

        if (resultado) {
            Mensaje.mostrar(Alert.AlertType.INFORMATION, miEscenario,
                "Éxito", "Ruta actualizada correctamente");
            cancelar();
        } else {
            Mensaje.mostrar(Alert.AlertType.ERROR, miEscenario,
                "Error", "No se pudo actualizar la ruta");
        }
    }

    private void cancelar() {
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
    }
}
