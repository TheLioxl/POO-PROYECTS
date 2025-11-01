package org.poo.vista.libro;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.poo.controlador.autor.AutorControladorListar;
import org.poo.controlador.editorial.EditorialControladorListar;
import org.poo.controlador.libro.LibroControladorGrabar;
import org.poo.dto.AutorDto;
import org.poo.dto.EditorialDto;
import org.poo.dto.LibroDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.Marco;
import org.poo.recurso.utilidad.Mensaje;

public class VistaLibroCrear extends StackPane {

    private static final int H_GAP = 10;
    private static final int V_GAP = 20;
    private static final int ALTO_FILA = 40;
    private static final int TAMANIO_FUENTE = 20;
    private static final int ALTO_CAJA = 35;
    private static final double AJUSTE_TITULO = 0.1;

    private final GridPane miGrilla;
    private final Rectangle miMarco;
    private final Stage miEscenario;

    private TextField txtNombreLibro;
    private TextField txtPrecioLibro;
    private TextField txtAnioLibro;
    private ComboBox<EditorialDto> cbmEditorial;
    private ComboBox<AutorDto> cbmAutor;

    public VistaLibroCrear(Stage esce, double ancho, double alto) {
        setAlignment(Pos.CENTER);
        miEscenario = esce;
        miGrilla = new GridPane();
        miMarco = Marco.crear(esce,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_LIBRO,
                Configuracion.DEGRADE_BORDE);

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

        ColumnConstraints col0 = new ColumnConstraints();
        ColumnConstraints col1 = new ColumnConstraints();

        col0.setPrefWidth(200);
        col1.setPrefWidth(400);
        col1.setHgrow(Priority.ALWAYS);

        miGrilla.getColumnConstraints().addAll(col0, col1);

        for (int i = 0; i < 9; i++) {
            RowConstraints fila = new RowConstraints();
            fila.setMinHeight(ALTO_FILA);
            fila.setMaxHeight(ALTO_FILA);
            miGrilla.getRowConstraints().add(fila);
        }
    }

    private void crearTitulo() {
        Text miTitulo = new Text("FORMULARIO - CREAR LIBRO");
        miTitulo.setFill(Color.web(Configuracion.MORADO_OSCURO));
        miTitulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 28));
        GridPane.setHalignment(miTitulo, HPos.CENTER);
        GridPane.setMargin(miTitulo, new Insets(30, 0, 0, 0));
        miGrilla.add(miTitulo, 0, 0, 2, 1);
    }

    private void crearFormulario() {
        Label lblNombre = new Label("Nombre libro:");
        lblNombre.setFont(Font.font("Times New Roman", FontPosture.ITALIC, TAMANIO_FUENTE));
        miGrilla.add(lblNombre, 0, 2);

        txtNombreLibro = new TextField();
        txtNombreLibro.setPromptText("Digita el nombre del libro");
        GridPane.setHgrow(txtNombreLibro, Priority.ALWAYS);
        txtNombreLibro.setPrefHeight(ALTO_CAJA);
        txtNombreLibro.setMaxWidth(Double.MAX_VALUE);
        miGrilla.add(txtNombreLibro, 1, 2);

        Label lblPrecio = new Label("Precio:");
        lblPrecio.setFont(Font.font("Times New Roman", FontPosture.ITALIC, TAMANIO_FUENTE));
        miGrilla.add(lblPrecio, 0, 3);

        txtPrecioLibro = new TextField();
        txtPrecioLibro.setPromptText("Digita el precio");
        GridPane.setHgrow(txtPrecioLibro, Priority.ALWAYS);
        txtPrecioLibro.setPrefHeight(ALTO_CAJA);
        txtPrecioLibro.setMaxWidth(Double.MAX_VALUE);
        miGrilla.add(txtPrecioLibro, 1, 3);

        Label lblAnio = new Label("Año:");
        lblAnio.setFont(Font.font("Times New Roman", FontPosture.ITALIC, TAMANIO_FUENTE));
        miGrilla.add(lblAnio, 0, 4);

        txtAnioLibro = new TextField();
        txtAnioLibro.setPromptText("Digita el año");
        GridPane.setHgrow(txtAnioLibro, Priority.ALWAYS);
        txtAnioLibro.setPrefHeight(ALTO_CAJA);
        txtAnioLibro.setMaxWidth(Double.MAX_VALUE);
        miGrilla.add(txtAnioLibro, 1, 4);

        Label lblEditorial = new Label("Editorial:");
        lblEditorial.setFont(Font.font("Times New Roman", FontPosture.ITALIC, TAMANIO_FUENTE));
        miGrilla.add(lblEditorial, 0, 5);

        List<EditorialDto> arrEditoriales = EditorialControladorListar.obtenerEditoriales();
        EditorialDto opcionInicialEditorial = new EditorialDto(0, "Seleccione editorial", "", (short) 0);
        arrEditoriales.add(0, opcionInicialEditorial);

        cbmEditorial = new ComboBox<>();
        cbmEditorial.setMaxWidth(Double.MAX_VALUE);
        cbmEditorial.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(cbmEditorial, Priority.ALWAYS);

        ObservableList<EditorialDto> itemsEditorial = FXCollections.observableArrayList(arrEditoriales);
        cbmEditorial.setItems(itemsEditorial);
        cbmEditorial.getSelectionModel().select(0);
        miGrilla.add(cbmEditorial, 1, 5);

        Label lblAutor = new Label("Autor:");
        lblAutor.setFont(Font.font("Times New Roman", FontPosture.ITALIC, TAMANIO_FUENTE));
        miGrilla.add(lblAutor, 0, 6);

        List<AutorDto> arrAutores = AutorControladorListar.obtenerAutores();
        AutorDto opcionInicialAutor = new AutorDto(0, "Seleccione autor", true);
        arrAutores.add(0, opcionInicialAutor);

        cbmAutor = new ComboBox<>();
        cbmAutor.setMaxWidth(Double.MAX_VALUE);
        cbmAutor.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(cbmAutor, Priority.ALWAYS);

        ObservableList<AutorDto> itemsAutor = FXCollections.observableArrayList(arrAutores);
        cbmAutor.setItems(itemsAutor);
        cbmAutor.getSelectionModel().select(0);
        miGrilla.add(cbmAutor, 1, 6);

        Button btnGrabar = new Button("Grabar libro");
        btnGrabar.setTextFill(Color.web(Configuracion.MORADO_OSCURO));
        btnGrabar.setMaxWidth(Double.MAX_VALUE);
        btnGrabar.setFont(Font.font("Times New Roman", TAMANIO_FUENTE));
        btnGrabar.setCursor(Cursor.HAND);
        btnGrabar.setOnAction((e) -> {
            guardarLibro();
        });
        miGrilla.add(btnGrabar, 1, 7);
    }

    private EditorialDto obtenerEditorial() {
        EditorialDto seleccionado = cbmEditorial.getSelectionModel().getSelectedItem();
        if (seleccionado != null && seleccionado.getIdEditorial() != 0) {
            return seleccionado;
        }
        return null;
    }

    private AutorDto obtenerAutor() {
        AutorDto seleccionado = cbmAutor.getSelectionModel().getSelectedItem();
        if (seleccionado != null && seleccionado.getIdAutor() != 0) {
            return seleccionado;
        }
        return null;
    }

    private void limpiarFormulario() {
        txtNombreLibro.setText("");
        txtPrecioLibro.setText("");
        txtAnioLibro.setText("");
        cbmEditorial.getSelectionModel().select(0);
        cbmAutor.getSelectionModel().select(0);
        txtNombreLibro.requestFocus();
    }

    private Boolean formularioCompleto() {
        if (txtNombreLibro.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, miEscenario,
                    "Atención", "Por favor coloca un nombre");
            txtNombreLibro.requestFocus();
            return false;
        }
        if (txtPrecioLibro.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, miEscenario,
                    "Atención", "Por favor ingresa el precio");
            txtPrecioLibro.requestFocus();
            return false;
        }
        try {
            Double.parseDouble(txtPrecioLibro.getText());
        } catch (NumberFormatException e) {
            Mensaje.mostrar(Alert.AlertType.WARNING, miEscenario,
                    "Atención", "El precio debe ser un número válido");
            txtPrecioLibro.requestFocus();
            return false;
        }
        if (txtAnioLibro.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, miEscenario,
                    "Atención", "Por favor ingresa el año");
            txtAnioLibro.requestFocus();
            return false;
        }
        try {
            Short.parseShort(txtAnioLibro.getText());
        } catch (NumberFormatException e) {
            Mensaje.mostrar(Alert.AlertType.WARNING, miEscenario,
                    "Atención", "El año debe ser un número válido");
            txtAnioLibro.requestFocus();
            return false;
        }
        if (obtenerEditorial() == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, miEscenario,
                    "Atención", "Por favor selecciona una editorial");
            cbmEditorial.requestFocus();
            return false;
        }
        if (obtenerAutor() == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, miEscenario,
                    "Atención", "Por favor selecciona un autor");
            cbmAutor.requestFocus();
            return false;
        }
        return true;
    }

    private void guardarLibro() {
        if (formularioCompleto()) {
            LibroDto dto = new LibroDto();
            dto.setNombreLibro(txtNombreLibro.getText());
            dto.setPrecioLibro(Double.valueOf(txtPrecioLibro.getText()));
            dto.setAnioLibro(Short.valueOf(txtAnioLibro.getText()));
            dto.setIdEditorialLibro(obtenerEditorial());
            dto.setIdAutorLibro(obtenerAutor());

            if (LibroControladorGrabar.crearLibro(dto)) {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, miEscenario,
                        "Éxito", "El Libro ha sido guardado exitosamente");
                limpiarFormulario();
            } else {
                Mensaje.mostrar(Alert.AlertType.ERROR, miEscenario,
                        "Error", "No se pudo guardar el libro");
            }
        }
    }

    private void colocarFrmElegante() {
        Runnable calcular = () -> {
            double alturaMarco = miMarco.getHeight();
            if (alturaMarco > 0) {
                double desplazamiento = alturaMarco * AJUSTE_TITULO;
                miGrilla.setTranslateY(alturaMarco / 5 + desplazamiento);
            }
        };

        calcular.run();
        miMarco.heightProperty().addListener((obs, antes, despues) -> {
            calcular.run();
        });
    }
}