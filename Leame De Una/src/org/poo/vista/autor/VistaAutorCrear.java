package org.poo.vista.autor;

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
import org.poo.controlador.autor.AutorControladorGrabar;
import org.poo.dto.AutorDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.Marco;
import org.poo.recurso.utilidad.Mensaje;

public class VistaAutorCrear extends StackPane {

    private static final int H_GAP = 10;
    private static final int V_GAP = 20;
    private static final int ALTO_FILA = 40;
    private static final int ALTO_CAJA = 35;
    private static final int TAMANIO_FUENTE = 20;
    private static final double AJUSTE_TITULO = 0.1;

    private final GridPane miGrilla;
    private final Rectangle miMarco;
    private final Stage miEscenario;

    private TextField txtNombreAutor;
    private ComboBox<String> cbmGeneroAutor;

    public VistaAutorCrear(Stage esce, double ancho, double alto) {
        setAlignment(Pos.CENTER);
        miEscenario = esce;
        miGrilla = new GridPane();
        miMarco = Marco.crear(esce,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_AUTOR,
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

        for (int i = 0; i < 7; i++) {
            RowConstraints fila = new RowConstraints();
            fila.setMinHeight(ALTO_FILA);
            fila.setMaxHeight(ALTO_FILA);
            miGrilla.getRowConstraints().add(fila);
        }
    }

    private void crearTitulo() {
        Text miTitulo = new Text("FORMULARIO - CREAR AUTOR");
        miTitulo.setFill(Color.web(Configuracion.MORADO_OSCURO));
        miTitulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 28));
        GridPane.setHalignment(miTitulo, HPos.CENTER);
        GridPane.setMargin(miTitulo, new Insets(30, 0, 0, 0));
        miGrilla.add(miTitulo, 0, 0, 2, 1);
    }

    private void crearFormulario() {
        // Label y TextField para Nombre del Autor
        Label lblNombre = new Label("Nombre del Autor:");
        lblNombre.setFont(Font.font("Times New Roman", FontPosture.ITALIC, TAMANIO_FUENTE));
        miGrilla.add(lblNombre, 0, 2);

        txtNombreAutor = new TextField();
        txtNombreAutor.setPromptText("Digita el nombre del autor");
        GridPane.setHgrow(txtNombreAutor, Priority.ALWAYS);
        txtNombreAutor.setPrefHeight(ALTO_CAJA);
        txtNombreAutor.setMaxWidth(Double.MAX_VALUE);
        miGrilla.add(txtNombreAutor, 1, 2);

        // Label y ComboBox para Género del Autor
        Label lblGenero = new Label("Género del Autor:");
        lblGenero.setFont(Font.font("Times New Roman", FontPosture.ITALIC, TAMANIO_FUENTE));
        miGrilla.add(lblGenero, 0, 3);

        cbmGeneroAutor = new ComboBox<>();
        cbmGeneroAutor.setMaxWidth(Double.MAX_VALUE);
        cbmGeneroAutor.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(cbmGeneroAutor, Priority.ALWAYS);
        cbmGeneroAutor.getItems().addAll("Seleccione género", "Masculino", "Femenino");
        cbmGeneroAutor.getSelectionModel().select(0);
        miGrilla.add(cbmGeneroAutor, 1, 3);

        // Botón Grabar
        Button btnGrabar = new Button("Grabar Autor");
        btnGrabar.setTextFill(Color.web(Configuracion.MORADO_OSCURO));
        btnGrabar.setMaxWidth(Double.MAX_VALUE);
        btnGrabar.setFont(Font.font("Times New Roman", TAMANIO_FUENTE));
        btnGrabar.setCursor(Cursor.HAND);
        btnGrabar.setOnAction((e) -> {
            guardarAutor();
        });
        miGrilla.add(btnGrabar, 1, 5);
    }

    private Boolean obtenerGenero() {
        String miTexto = cbmGeneroAutor.getValue();
        if ("Masculino".equals(miTexto)) {
            return true;
        }
        if ("Femenino".equals(miTexto)) {
            return false;
        }
        return null;
    }

    private void limpiarFormulario() {
        txtNombreAutor.setText("");
        cbmGeneroAutor.getSelectionModel().select(0);
        txtNombreAutor.requestFocus();
    }

    private Boolean formularioCompleto() {
        if (txtNombreAutor.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, miEscenario,
                    "Atención", "Por favor coloca un nombre");
            txtNombreAutor.requestFocus();
            return false;
        }
        if (cbmGeneroAutor.getSelectionModel().getSelectedIndex() == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, miEscenario,
                    "Atención", "Escoge un género");
            cbmGeneroAutor.requestFocus();
            return false;
        }
        return true;
    }

    private void guardarAutor() {
        if (formularioCompleto()) {
            AutorDto dto = new AutorDto();
            dto.setNombreAutor(txtNombreAutor.getText());
            dto.setGeneroAutor(obtenerGenero());

            if (AutorControladorGrabar.crearAutor(dto)) {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, miEscenario,
                        "Éxito", "El Autor ha sido guardado exitosamente");
                limpiarFormulario();
            } else {
                Mensaje.mostrar(Alert.AlertType.ERROR, miEscenario,
                        "Error", "No se pudo guardar el autor");
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