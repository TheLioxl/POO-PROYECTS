package org.poo.vista.ruta;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.poo.controlador.ruta.RutaControladorGrabar;
import org.poo.dto.RutaDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.Formulario;
import org.poo.recurso.utilidad.Icono;
import org.poo.recurso.utilidad.Marco;
import org.poo.recurso.utilidad.Mensaje;

import java.io.File;
import java.time.LocalDate;

public class VistaRutaCrear extends StackPane {

    private final Rectangle marco;
    private final Stage miEscenario;
    private final VBox cajaVertical;

    // Componentes del formulario - 6 tipos diferentes
    private TextField txtNombreRuta;           // 1. TextField
    private TextField txtCiudadOrigen;         // 1. TextField
    private TextField txtCiudadDestino;        // 1. TextField
    private Spinner<Double> spinnerDistancia;  // 2. Spinner (Double)
    private Spinner<Integer> spinnerDuracion;  // 2. Spinner (Integer)
    private ComboBox<String> cmbEstado;        // 3. ComboBox
    private DatePicker dateFechaCreacion;      // 4. DatePicker
    private CheckBox chkPeaje;                 // 5. CheckBox
    private CheckBox chkCarretera;             // 5. CheckBox
    private TextArea txtDescripcion;           // 6. TextArea
    private TextField txtNombreImagen;

    private String rutaImagenSeleccionada = "";

    public VistaRutaCrear(Stage ventanaPadre, double ancho, double alto) {
        setAlignment(Pos.CENTER);
        miEscenario = ventanaPadre;
        
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
        crearBotones();
    }

    private void configurarCajaVertical() {
        cajaVertical.setAlignment(Pos.TOP_CENTER);
        cajaVertical.prefWidthProperty().bind(miEscenario.widthProperty());
        cajaVertical.prefHeightProperty().bind(miEscenario.heightProperty());
        cajaVertical.setPadding(new Insets(20));
    }

    private void crearTitulo() {
        Text titulo = new Text("Crear Nueva Ruta");
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

        // Fila 0: Nombre de la Ruta (TextField)
        Label lblNombre = Formulario.crearEtiqueta("Nombre de la Ruta:");
        txtNombreRuta = new TextField();
        txtNombreRuta.setPromptText("Ej: Bogotá - Medellín Express");
        txtNombreRuta.setPrefWidth(300);
        grid.add(lblNombre, 0, 0);
        grid.add(txtNombreRuta, 1, 0);

        // Fila 1: Ciudad Origen (TextField)
        Label lblOrigen = Formulario.crearEtiqueta("Ciudad de Origen:");
        txtCiudadOrigen = new TextField();
        txtCiudadOrigen.setPromptText("Ej: Bogotá");
        txtCiudadOrigen.setPrefWidth(300);
        grid.add(lblOrigen, 0, 1);
        grid.add(txtCiudadOrigen, 1, 1);

        // Fila 2: Ciudad Destino (TextField)
        Label lblDestino = Formulario.crearEtiqueta("Ciudad de Destino:");
        txtCiudadDestino = new TextField();
        txtCiudadDestino.setPromptText("Ej: Medellín");
        txtCiudadDestino.setPrefWidth(300);
        grid.add(lblDestino, 0, 2);
        grid.add(txtCiudadDestino, 1, 2);

        // Fila 3: Distancia en Km (Spinner Double)
        Label lblDistancia = Formulario.crearEtiqueta("Distancia (Km):");
        SpinnerValueFactory<Double> factoryDistancia = 
            new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 10000.0, 100.0, 10.0);
        spinnerDistancia = new Spinner<>(factoryDistancia);
        spinnerDistancia.setEditable(true);
        spinnerDistancia.setPrefWidth(150);
        grid.add(lblDistancia, 0, 3);
        grid.add(spinnerDistancia, 1, 3);

        // Fila 4: Duración en Horas (Spinner Integer)
        Label lblDuracion = Formulario.crearEtiqueta("Duración (Horas):");
        SpinnerValueFactory<Integer> factoryDuracion = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 48, 4, 1);
        spinnerDuracion = new Spinner<>(factoryDuracion);
        spinnerDuracion.setEditable(true);
        spinnerDuracion.setPrefWidth(150);
        grid.add(lblDuracion, 0, 4);
        grid.add(spinnerDuracion, 1, 4);

        // Fila 5: Estado (ComboBox)
        Label lblEstado = Formulario.crearEtiqueta("Estado:");
        cmbEstado = new ComboBox<>();
        cmbEstado.getItems().addAll("Activa", "Inactiva");
        cmbEstado.setValue("Activa");
        cmbEstado.setPrefWidth(150);
        grid.add(lblEstado, 0, 5);
        grid.add(cmbEstado, 1, 5);

        // Fila 6: Fecha de Creación (DatePicker)
        Label lblFecha = Formulario.crearEtiqueta("Fecha de Creación:");
        dateFechaCreacion = new DatePicker(LocalDate.now());
        dateFechaCreacion.setPrefWidth(200);
        grid.add(lblFecha, 0, 6);
        grid.add(dateFechaCreacion, 1, 6);

        // Fila 7: CheckBoxes (Peaje y Carretera)
        Label lblCaracteristicas = Formulario.crearEtiqueta("Características:");
        HBox cajaCheques = new HBox(15);
        chkPeaje = new CheckBox("Tiene Peajes");
        chkCarretera = new CheckBox("Carretera Principal");
        chkPeaje.setSelected(false);
        chkCarretera.setSelected(true);
        cajaCheques.getChildren().addAll(chkPeaje, chkCarretera);
        grid.add(lblCaracteristicas, 0, 7);
        grid.add(cajaCheques, 1, 7);

        // Fila 8: Descripción (TextArea)
        Label lblDescripcion = Formulario.crearEtiqueta("Descripción:");
        txtDescripcion = new TextArea();
        txtDescripcion.setPromptText("Descripción detallada de la ruta...");
        txtDescripcion.setPrefRowCount(3);
        txtDescripcion.setPrefWidth(300);
        txtDescripcion.setWrapText(true);
        grid.add(lblDescripcion, 0, 8);
        grid.add(txtDescripcion, 1, 8);

        // Fila 9: Imagen
        Label lblImagen = Formulario.crearEtiqueta("Imagen:");
        txtNombreImagen = new TextField();
        txtNombreImagen.setPromptText("Nombre público de la imagen");
        txtNombreImagen.setPrefWidth(180);
        
        Button btnBuscarImagen = new Button("Buscar");
        btnBuscarImagen.setCursor(Cursor.HAND);
        btnBuscarImagen.setOnAction(e -> buscarImagen());
        
        HBox cajaImagen = new HBox(10);
        cajaImagen.getChildren().addAll(txtNombreImagen, btnBuscarImagen);
        grid.add(lblImagen, 0, 9);
        grid.add(cajaImagen, 1, 9);

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
        }
    }

    private void crearBotones() {
        HBox cajaBotones = new HBox(15);
        cajaBotones.setAlignment(Pos.CENTER);
        cajaBotones.setPadding(new Insets(10));

        Button btnGuardar = new Button("Guardar Ruta");
        btnGuardar.setCursor(Cursor.HAND);
        btnGuardar.setPrefWidth(150);
        btnGuardar.setStyle("-fx-background-color: " + Configuracion.VERDE_EXITO + "; -fx-text-fill: white;");
        btnGuardar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_GUARDAR, 16));
        btnGuardar.setOnAction(e -> guardarRuta());

        Button btnLimpiar = new Button("Limpiar");
        btnLimpiar.setCursor(Cursor.HAND);
        btnLimpiar.setPrefWidth(120);
        btnLimpiar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_LIMPIAR, 16));
        btnLimpiar.setOnAction(e -> limpiarFormulario());

        cajaBotones.getChildren().addAll(btnGuardar, btnLimpiar);
        cajaVertical.getChildren().add(cajaBotones);

        getChildren().add(cajaVertical);
    }

    private void guardarRuta() {
        // Validaciones
        if (txtNombreRuta.getText().trim().isEmpty()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, miEscenario, 
                "Advertencia", "Debe ingresar el nombre de la ruta");
            txtNombreRuta.requestFocus();
            return;
        }

        if (txtCiudadOrigen.getText().trim().isEmpty()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, miEscenario,
                "Advertencia", "Debe ingresar la ciudad de origen");
            txtCiudadOrigen.requestFocus();
            return;
        }

        if (txtCiudadDestino.getText().trim().isEmpty()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, miEscenario,
                "Advertencia", "Debe ingresar la ciudad de destino");
            txtCiudadDestino.requestFocus();
            return;
        }

        if (rutaImagenSeleccionada.isEmpty()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, miEscenario,
                "Advertencia", "Debe seleccionar una imagen para la ruta");
            return;
        }

        // Crear DTO
        RutaDto nuevaRuta = new RutaDto();
        nuevaRuta.setNombreRuta(txtNombreRuta.getText().trim());
        nuevaRuta.setCiudadOrigenRuta(txtCiudadOrigen.getText().trim());
        nuevaRuta.setCiudadDestinoRuta(txtCiudadDestino.getText().trim());
        nuevaRuta.setDistanciaKmRuta(spinnerDistancia.getValue());
        nuevaRuta.setDuracionHorasRuta(spinnerDuracion.getValue());
        nuevaRuta.setEstadoRuta(cmbEstado.getValue().equals("Activa"));
        nuevaRuta.setNombreImagenPublicoRuta(txtNombreImagen.getText().trim());

        // Guardar
        Boolean resultado = RutaControladorGrabar.crearRuta(nuevaRuta, rutaImagenSeleccionada);

        if (resultado) {
            Mensaje.mostrar(Alert.AlertType.INFORMATION, miEscenario,
                "Éxito", "Ruta creada correctamente");
            limpiarFormulario();
        } else {
            Mensaje.mostrar(Alert.AlertType.ERROR, miEscenario,
                "Error", "No se pudo crear la ruta");
        }
    }

    private void limpiarFormulario() {
        txtNombreRuta.clear();
        txtCiudadOrigen.clear();
        txtCiudadDestino.clear();
        spinnerDistancia.getValueFactory().setValue(100.0);
        spinnerDuracion.getValueFactory().setValue(4);
        cmbEstado.setValue("Activa");
        dateFechaCreacion.setValue(LocalDate.now());
        chkPeaje.setSelected(false);
        chkCarretera.setSelected(true);
        txtDescripcion.clear();
        txtNombreImagen.clear();
        rutaImagenSeleccionada = "";
        txtNombreRuta.requestFocus();
    }
}
