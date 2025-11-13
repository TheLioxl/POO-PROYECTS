package edu.poo.vista.categoria;

import edu.poo.recurso.utilidad.Icono;
import edu.poo.recurso.utilidad.Fondo;
import edu.poo.recurso.utilidad.Marco;
import edu.poo.recurso.utilidad.Imagen;
import edu.poo.recurso.utilidad.Mensaje;
import edu.poo.recurso.utilidad.Formulario;
import edu.poo.recurso.dominio.Configuracion;
import edu.poo.controlador.categoria.ControladorCategoGrabar;

import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.SubScene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.scene.layout.Region;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.Priority;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.ColumnConstraints;

public class VistaCategoCrear extends SubScene {

    private final GridPane miGrilla;
    private final StackPane miFormulario;
    private final Stage laVentanaPrincipal;

    private TextField cajaImagen;
    private TextField cajaNombre;
    private ComboBox<String> comboEstado;

    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private String rutaImagenSeleccionada;

    public VistaCategoCrear(Stage ventanaPadre, double ancho, double alto) {
        super(new StackPane(), ancho, alto);

        Background fondo = Fondo.asignarAleatorio(Configuracion.FONDOS);
        miFormulario = (StackPane) getRoot();
        miFormulario.setBackground(fondo);
        miFormulario.setAlignment(Pos.TOP_CENTER);

        laVentanaPrincipal = ventanaPadre;
        miGrilla = new GridPane();

        rutaImagenSeleccionada = "";

        configurarGrilla(ancho, alto);
        crearMarco();
        crearTitulo();
        crearFormulario();
    }

    public StackPane getMiFormulario() {
        return miFormulario;
    }

    private void configurarGrilla(double ancho, double alto) {
        double anchoMarco = ancho * 0.5;

        miGrilla.setHgap(10);
        miGrilla.setVgap(20);
        // miGrilla.setAlignment(Pos.TOP_CENTER);

        miGrilla.setPrefSize(anchoMarco, alto);
        miGrilla.setMinSize(anchoMarco, alto);
        miGrilla.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        miGrilla.prefWidthProperty().bind(laVentanaPrincipal.widthProperty().multiply(0.7));

        ColumnConstraints anchoColumna1 = new ColumnConstraints();
        anchoColumna1.setPercentWidth(40);

        ColumnConstraints anchoColumna2 = new ColumnConstraints();
        anchoColumna2.setPercentWidth(60);
        anchoColumna2.setHgrow(Priority.ALWAYS);

        miGrilla.getColumnConstraints().addAll(anchoColumna1, anchoColumna2);
    }

    private void crearMarco() {
        double ancho = Configuracion.PORCENTAJE_ANCHO_MARCO;
        double alto = Configuracion.PORCENTAJE_ALTO_MARCO;
        Rectangle miMarco = Marco.crear(laVentanaPrincipal, ancho, alto,
                Configuracion.DEGRADE_ARREGLO, Configuracion.DEGRADE_BORDE);
        miFormulario.getChildren().add(miMarco);
    }

    private void crearTitulo() {
        int columna, fila, colSpan, rowSpan;

        // Espacio en la parte superior 
//        Region bloqueSeparador = new Region();
//        bloqueSeparador.prefHeightProperty().bind(laVentanaPrincipal.heightProperty().multiply(0.05));
        columna = 0;
        fila = 0;
        colSpan = 2;
        rowSpan = 1;
//        miGrilla.add(bloqueSeparador, columna, fila, colSpan, rowSpan);
        // *********************************************************************

        // Primera de la grilla: Titulo
        Text titulo = new Text("Formulario creación de categorías");
        titulo.setFill(Color.web("#E82E68"));
        titulo.setFont(Font.font("verdana", FontWeight.BOLD, 25));
        GridPane.setHalignment(titulo, HPos.CENTER);

        fila = 1;
        miGrilla.add(titulo, columna, fila, colSpan, rowSpan);
    }

    private void crearFormulario() {
        int fila, altoCaja, tamanioFuente, primeraColumna, segundaColumna;

        altoCaja = 30;
        tamanioFuente = 16;
        primeraColumna = 0;
        segundaColumna = 1;

        // Fila nombre de la categoría (fila = 2 no se usa, dejar el espacio)
        fila = 3;
        // --
        Label lblNombreCatego = new Label("Nombre categoria: ");
        lblNombreCatego.setFont(Font.font("Verdana", tamanioFuente));
        miGrilla.add(lblNombreCatego, primeraColumna, fila);
        // --
        cajaNombre = new TextField();
        GridPane.setHgrow(cajaNombre, Priority.ALWAYS);
        cajaNombre.setPrefHeight(altoCaja);
        cajaNombre.prefWidthProperty().bind(laVentanaPrincipal.widthProperty().multiply(0.6));
        Formulario.cantidadCaracteres(cajaNombre, 25);
        miGrilla.add(cajaNombre, segundaColumna, fila);
        // *********************************************************************

        // File estado de la categoría
        fila = 4;
        // --
        Label lblComboEstadoCargo = new Label("Estado categoria: ");
        lblComboEstadoCargo.setFont(Font.font("Verdana", tamanioFuente));
        miGrilla.add(lblComboEstadoCargo, primeraColumna, fila);
        // --
        comboEstado = new ComboBox<>();
        comboEstado.setMaxWidth(Double.MAX_VALUE);
        comboEstado.setPrefHeight(altoCaja);
        comboEstado.prefWidthProperty().bind(laVentanaPrincipal.widthProperty().multiply(0.6));

        comboEstado.getItems().addAll("Seleccione el estado", "Activo", "Inactivo");
        comboEstado.setValue("Seleccione el estado");
        comboEstado.getSelectionModel().select(0);
        miGrilla.add(comboEstado, segundaColumna, fila);
        // *********************************************************************

        // Fila imagen y botón de selección
        fila = 5;
        // --
        Label lblImagen = new Label("Seleccione la imagen:");
        lblImagen.setFont(Font.font("Verdana", tamanioFuente));
        miGrilla.add(lblImagen, primeraColumna, fila);
        // --
        cajaImagen = new TextField();
        cajaImagen.setDisable(true);
        cajaImagen.setPrefHeight(altoCaja);

        String[] extensionesPermitidas = {"*.png", "*.jpg", "*.jpeg"};
        FileChooser objSeleccionar = Formulario.selectorImagen("Seleccione la imagen", "Imágenes", extensionesPermitidas);

        Button btnSeleccionar = new Button("+");
        btnSeleccionar.setPrefHeight(altoCaja);
        btnSeleccionar.setOnAction((t) -> {
            rutaImagenSeleccionada = Imagen.obtenerRutaImagen(cajaImagen, objSeleccionar);
            miGrilla.getChildren().remove(imgPorDefecto);
            miGrilla.getChildren().remove(imgPrevisualizar);
            imgPrevisualizar = Icono.previsualizar(rutaImagenSeleccionada, 150);

            int filaImagen = 6;
            GridPane.setHalignment(imgPrevisualizar, HPos.CENTER);
            miGrilla.add(imgPrevisualizar, segundaColumna, filaImagen);
        });

        HBox.setHgrow(cajaImagen, Priority.ALWAYS);
        HBox panelHorizontalImagen = new HBox(2);
        panelHorizontalImagen.getChildren().addAll(cajaImagen, btnSeleccionar);

        miGrilla.add(panelHorizontalImagen, segundaColumna, fila);
        // *********************************************************************

        // Fila previsualización de la imágen
        fila = 6;
        imgPorDefecto = Icono.obtenerIcono("imgNoDisponible.png", 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        miGrilla.add(imgPorDefecto, segundaColumna, fila);
        // *********************************************************************

        // Fila del botón grabar (nos volamos la 7)
        fila = 8;
        Button btnGrabar = new Button("Crear categoria");
        btnGrabar.setPrefHeight(altoCaja);
        btnGrabar.setMaxWidth(Double.MAX_VALUE);

        btnGrabar.setTextFill(Color.web("#6C3483"));
        btnGrabar.setFont(Font.font("Verdana", FontWeight.BOLD, tamanioFuente));
        btnGrabar.setOnAction((e) -> {
            if (formularioDiligenciado()) {
                String nomCat = cajaNombre.getText();
                String imaCat = cajaImagen.getText();
                boolean estCat = comboEstado.getValue().equalsIgnoreCase("Activo");

                if (ControladorCategoGrabar.grabar(nomCat, estCat, imaCat, rutaImagenSeleccionada)) {
                    limpiarFormulario();
                    Mensaje.modal(Alert.AlertType.INFORMATION, null, "EXITO", "Categoría creada correctamente");
                } else {
                    Mensaje.modal(Alert.AlertType.ERROR, null, "ERROR", "Fatal error creando la categoría");
                }
            }
        });
        miGrilla.add(btnGrabar, segundaColumna, fila);

        miFormulario.getChildren().add(miGrilla);
    }

    private boolean formularioDiligenciado() {
        boolean correcto = false;
        String nombre = cajaNombre.getText();
        int indiceSeleccionado = comboEstado.getSelectionModel().getSelectedIndex();

        if (nombre.isBlank()) {
            cajaNombre.requestFocus();
            Mensaje.modal(Alert.AlertType.WARNING, null, "Advertencia", "El nombre  es obligatorio.");
        } else {
            if (indiceSeleccionado == 0) {
                Mensaje.modal(Alert.AlertType.WARNING, null, "Advertencia", "Debe seleccionar un estado.");
            } else {
                if (rutaImagenSeleccionada.isBlank()) {
                    Mensaje.modal(Alert.AlertType.WARNING, null, "Advertencia", "Debe seleccionar una imagen.");
                } else {
                    correcto = true;
                }
            }
        }
        return correcto;
    }

    private void limpiarFormulario() {
        cajaNombre.setText("");
        cajaImagen.setText("");
        rutaImagenSeleccionada = "";
        comboEstado.getSelectionModel().select(0);

        cajaNombre.requestFocus();

        int filaImagen = 6;
        int segundaColumna = 1;
        miGrilla.getChildren().remove(imgPrevisualizar);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        miGrilla.add(imgPorDefecto, segundaColumna, filaImagen);
    }

}
