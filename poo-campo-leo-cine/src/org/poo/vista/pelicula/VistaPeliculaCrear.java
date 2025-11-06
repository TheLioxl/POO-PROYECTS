package org.poo.vista.pelicula;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
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
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.poo.controlador.genero.GeneroControladorListar;
import org.poo.controlador.pelicula.PeliculaControladorGrabar;
import org.poo.dto.GeneroDto;
import org.poo.dto.PeliculaDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.Formulario;
import org.poo.recurso.utilidad.GestorImagen;
import org.poo.recurso.utilidad.Icono;
import org.poo.recurso.utilidad.Marco;
import org.poo.recurso.utilidad.Mensaje;

public class VistaPeliculaCrear extends StackPane {

    private static final int H_GAP = 10;
    private static final int V_GAP = 20;
    private static final int ALTO_FILA = 40;
    private static final int TAMANIO_FUENTE = 20;
    private static final int ALTO_CAJA = 35;
    private static final double AJUSTE_TITULO = 0.1;

    private final GridPane miGrilla;
    private final Rectangle miMarco;

    private TextField txtNombrePelicula;
    private TextField txtProtaPelicula;
    private TextField txtPresupuestoPelicula;
    private ComboBox<GeneroDto> cbmGeneroPelicula;
    private ToggleGroup radioCodigoPelicula;
    
    // ✅ Propiedades para la imagen
    private TextField cajaImagen;
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private String rutaImagenSeleccionada;

    public VistaPeliculaCrear(Stage esce, double ancho, double alto) {
        rutaImagenSeleccionada = "";  // ✅ Inicializar
        setAlignment(Pos.CENTER);
        miGrilla = new GridPane();
        miMarco = Marco.crear(esce, Configuracion.MARCO_ALTO_PORCENTAJE, Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_PELICULA, Configuracion.DEGRADE_BORDE);
        getChildren().add(miMarco);
        configurarMiGrilla(ancho, alto);
        crearTitulo();
        crearFormulario();
        colocarFrmElegante();
        getChildren().add(miGrilla);
    }

    private void configurarMiGrilla(double ancho, double alto) {
        double miAnchoGrilla = ancho * Configuracion.GRILLA_ANCHO_PORCENTAJE;
        miGrilla.setHgap(H_GAP);
        miGrilla.setVgap(V_GAP);
        miGrilla.setPrefSize(miAnchoGrilla, alto);
        miGrilla.setMinSize(miAnchoGrilla, alto);
        miGrilla.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        // ✅ Ahora con 3 columnas para incluir la imagen
        ColumnConstraints col0 = new ColumnConstraints();
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();

        col0.setPrefWidth(250);
        col1.setPrefWidth(250);
        col2.setPrefWidth(200);
        col1.setHgrow(Priority.ALWAYS);

        miGrilla.getColumnConstraints().addAll(col0, col1, col2);

        for (int i = 0; i < 9; i++) {
            RowConstraints fila = new RowConstraints();
            fila.setMinHeight(ALTO_FILA);
            fila.setMaxHeight(ALTO_FILA);
            miGrilla.getRowConstraints().add(fila);
        }
    }

    private void crearTitulo() {
        Text miTitulo = new Text("FORMULARIO CREAR PELICULA");
        miTitulo.setFill(Color.web(Configuracion.MORADO_OSCURO));
        miTitulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 28));
        GridPane.setHalignment(miTitulo, HPos.CENTER);
        GridPane.setMargin(miTitulo, new Insets(30, 0, 0, 0));
        miGrilla.add(miTitulo, 0, 0, 3, 1);  // ✅ Ahora ocupa 3 columnas
    }

    private void crearFormulario() {
        Label lblNombre = new Label("Nombre pelicula:");
        lblNombre.setFont(Font.font("Times New Roman", FontPosture.ITALIC, TAMANIO_FUENTE));
        miGrilla.add(lblNombre, 0, 2);

        txtNombrePelicula = new TextField();
        txtNombrePelicula.setPromptText("Digita la pelicula");
        GridPane.setHgrow(txtNombrePelicula, Priority.ALWAYS);
        txtNombrePelicula.setPrefHeight(ALTO_CAJA);
        miGrilla.add(txtNombrePelicula, 1, 2);

        Label lblProtaPelicula = new Label("Protagonista:");
        lblProtaPelicula.setFont(Font.font("Times New Roman", FontPosture.ITALIC, TAMANIO_FUENTE));
        miGrilla.add(lblProtaPelicula, 0, 3);

        txtProtaPelicula = new TextField();
        txtProtaPelicula.setPromptText("Digita el protagonista");
        GridPane.setHgrow(txtProtaPelicula, Priority.ALWAYS);
        txtProtaPelicula.setPrefHeight(ALTO_CAJA);
        miGrilla.add(txtProtaPelicula, 1, 3);

        Label lblCodigoPelicula = new Label("Genero pelicula:");
        lblCodigoPelicula.setFont(Font.font("Times New Roman", FontPosture.ITALIC, TAMANIO_FUENTE));
        miGrilla.add(lblCodigoPelicula, 0, 4);

        List<GeneroDto> arrGeneros = GeneroControladorListar.obtenerGenerosActivos();
        GeneroDto opcionInicial = new GeneroDto(0, "Seleccione genero", true);
        arrGeneros.add(0, opcionInicial);

        cbmGeneroPelicula = new ComboBox<>();
        cbmGeneroPelicula.setMaxWidth(Double.MAX_VALUE);
        cbmGeneroPelicula.setPrefHeight(ALTO_CAJA);

        ObservableList<GeneroDto> items = FXCollections.observableArrayList(arrGeneros);
        cbmGeneroPelicula.setItems(items);
        cbmGeneroPelicula.getSelectionModel().select(0);
        miGrilla.add(cbmGeneroPelicula, 1, 4);

        Label lblPresupuestoPelicula = new Label("Presupuesto:");
        lblPresupuestoPelicula.setFont(Font.font("Times New Roman", FontPosture.ITALIC, TAMANIO_FUENTE));
        miGrilla.add(lblPresupuestoPelicula, 0, 5);

        txtPresupuestoPelicula = new TextField();
        txtPresupuestoPelicula.setPromptText("Digita el presupuesto");
        GridPane.setHgrow(txtPresupuestoPelicula, Priority.ALWAYS);
        txtPresupuestoPelicula.setPrefHeight(ALTO_CAJA);
        miGrilla.add(txtPresupuestoPelicula, 1, 5);

        Label lblEdadPelicula = new Label("Edad:");
        lblEdadPelicula.setFont(Font.font("Times New Roman", FontPosture.ITALIC, TAMANIO_FUENTE));
        miGrilla.add(lblEdadPelicula, 0, 6);

        radioCodigoPelicula = new ToggleGroup();

        RadioButton rbAdulto = new RadioButton("+18");
        rbAdulto.setToggleGroup(radioCodigoPelicula);

        RadioButton rbInfantil = new RadioButton("Infantil");
        rbInfantil.setToggleGroup(radioCodigoPelicula);

        VBox vboxRadios = new VBox(10);
        vboxRadios.getChildren().addAll(rbAdulto, rbInfantil);
        miGrilla.add(vboxRadios, 1, 6);

        // ✅ NUEVO: Selector de imagen
        Label lblImagen = new Label("Imagen Película:");
        lblImagen.setFont(Font.font("Times New Roman", FontPosture.ITALIC, TAMANIO_FUENTE));
        miGrilla.add(lblImagen, 0, 7);

        cajaImagen = new TextField();
        cajaImagen.setDisable(true);
        cajaImagen.setPrefHeight(ALTO_CAJA);
        String[] extensiones = {"*.png", "*.jpg", "*.jpeg"};
        FileChooser objSeleccionar = Formulario.selectorImagen("Selecciona la foto", "Imagenes", extensiones);

        Button btnSeleccionarImagen = new Button("+");
        btnSeleccionarImagen.setPrefHeight(ALTO_CAJA);
        btnSeleccionarImagen.setOnAction((e) -> {
            rutaImagenSeleccionada = GestorImagen.obtenerRutaImagen(cajaImagen, objSeleccionar);
            if (rutaImagenSeleccionada.isEmpty()) {
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                miGrilla.add(imgPorDefecto, 2, 1, 1, 7);
            } else {
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                imgPrevisualizar = Icono.previsualizar(rutaImagenSeleccionada, 150);
                GridPane.setHalignment(imgPrevisualizar, HPos.CENTER);
                miGrilla.add(imgPrevisualizar, 2, 1, 1, 7);
            }
        });

        HBox.setHgrow(cajaImagen, Priority.ALWAYS);
        HBox panelHorizontal = new HBox(2);
        panelHorizontal.setAlignment(Pos.BOTTOM_RIGHT);
        panelHorizontal.getChildren().addAll(cajaImagen, btnSeleccionarImagen);
        miGrilla.add(panelHorizontal, 1, 7);

        // ✅ Imagen por defecto
        imgPorDefecto = Icono.obtenerIcono("imgNoDisponible.png", 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        GridPane.setValignment(imgPorDefecto, VPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 1, 1, 7);

        Button btnGrabar = new Button("Grabar pelicula");
        btnGrabar.setTextFill(Color.PURPLE);
        btnGrabar.setMaxWidth(Double.MAX_VALUE);
        btnGrabar.setFont(Font.font("Times New Roman", TAMANIO_FUENTE));
        btnGrabar.setOnAction((e) -> {
            guardarPelicula();
        });
        miGrilla.add(btnGrabar, 1, 8);
    }

    private Boolean obtenerEstado() {
        Toggle radioSeleccionado = radioCodigoPelicula.getSelectedToggle();
        if (radioSeleccionado != null) {
            RadioButton seleccionado = (RadioButton) radioSeleccionado;
            String miTexto = seleccionado.getText();
            if ("Infantil".equals(miTexto)) {
                return false;
            }
            if ("+18".equals(miTexto)) {
                return true;
            }
        }
        return null;
    }

    private GeneroDto obtenerGenero() {
        GeneroDto seleccionado = cbmGeneroPelicula.getSelectionModel().getSelectedItem();
        if (seleccionado != null && seleccionado.getIdGenero() != 0) {
            return seleccionado;
        }
        return null;
    }

    private void limpiarFormulario() {
        txtNombrePelicula.setText("");
        txtProtaPelicula.setText("");
        txtPresupuestoPelicula.setText("");
        cbmGeneroPelicula.getSelectionModel().select(0);
        radioCodigoPelicula.selectToggle(null);
        cajaImagen.setText("");
        rutaImagenSeleccionada = "";
        
        // ✅ Restaurar imagen por defecto
        miGrilla.getChildren().remove(imgPorDefecto);
        miGrilla.getChildren().remove(imgPrevisualizar);
        miGrilla.add(imgPorDefecto, 2, 1, 1, 7);
    }

    private Boolean formularioCompleto() {
        if (txtNombrePelicula.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Atención", "Por favor coloca un nombre");
            txtNombrePelicula.requestFocus();
            return false;
        }
        if (txtProtaPelicula.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Atención", "Por favor ingresa el protagonista");
            txtProtaPelicula.requestFocus();
            return false;
        }
        if (txtPresupuestoPelicula.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Atención", "Por favor ingresa el presupuesto");
            txtPresupuestoPelicula.requestFocus();
            return false;
        }
        if (cbmGeneroPelicula.getValue() == null || cbmGeneroPelicula.getValue().getIdGenero() == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Atención", "Por favor selecciona un género");
            cbmGeneroPelicula.requestFocus();
            return false;
        }
        if (obtenerEstado() == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Atención", "Por favor selecciona la edad (+18 o Infantil)");
            return false;
        }
        if (rutaImagenSeleccionada.isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, null,
                    "Atención", "Ajá, y la imagen?");
            return false;
        }
        return true;
    }

    private void guardarPelicula() {
        if (formularioCompleto()) {
            PeliculaDto dto = new PeliculaDto();
            dto.setNombrePelicula(txtNombrePelicula.getText());
            dto.setProtagonistaPelicula(txtProtaPelicula.getText());
            dto.setGeneroPelicula(obtenerGenero());
            dto.setPresupuestoPelicula(Double.valueOf(txtPresupuestoPelicula.getText()));
            dto.setEstadoPelicula(obtenerEstado());
            dto.setNombreImagenPublicoPelicula(cajaImagen.getText());

            if (PeliculaControladorGrabar.crearPelicula(dto, rutaImagenSeleccionada)) {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, null,
                        "Exito", "La Pelicula se Guardó Exitosamente");
                limpiarFormulario();
                txtNombrePelicula.requestFocus();
            } else {
                Mensaje.mostrar(Alert.AlertType.ERROR, null,
                        "Error", "Esto no sirve");
                txtNombrePelicula.requestFocus();
            }
        }
    }

    private void colocarFrmElegante() {
        Runnable calcular = () -> {
            double alturaMarco = miMarco.getHeight();
            if (alturaMarco > 0) {
                double desplazamiento = alturaMarco * AJUSTE_TITULO;
                miGrilla.setTranslateY(alturaMarco / 8 + desplazamiento);
            }
        };
        calcular.run();
        miMarco.heightProperty().addListener((obs, antes, despues) -> {
            calcular.run();
        });
    }
}
                