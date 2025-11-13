package edu.poo.vista.producto;

import edu.poo.modelo.Categoria;
import edu.poo.recurso.utilidad.Fondo;
import edu.poo.recurso.utilidad.Marco;
import edu.poo.recurso.utilidad.Imagen;
import edu.poo.recurso.utilidad.Mensaje;
import edu.poo.recurso.utilidad.Formulario;
import edu.poo.recurso.dominio.Configuracion;
import edu.poo.controlador.categoria.ControladorCategoListar;
import edu.poo.controlador.producto.ControladorProductoGrabar;

import java.util.List;
import javafx.geometry.HPos;

import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.util.StringConverter;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.ColumnConstraints;

public class VistaProductoCrear extends SubScene {

    private final GridPane miGrilla;
    private final StackPane miFormulario;
    private final Stage laVentanaPrincipal;

    private TextField cajaNombreProducto;
    private TextField cajaPrecioProducto;
    private TextField cajaCantidadProducto;
    private ComboBox<Categoria> comboCategoria;
    private TextField cajaImagen;

    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private String rutaImagenSeleccionada;

    public VistaProductoCrear(Stage ventanaPadre, double anchoFormulario, double altoFormulario) {
        super(new StackPane(), anchoFormulario, altoFormulario);

        Background fondo = Fondo.asignarAleatorio(Configuracion.FONDOS);

        miFormulario = (StackPane) getRoot();
        miFormulario.setBackground(fondo);
        miFormulario.setAlignment(Pos.TOP_CENTER);

        laVentanaPrincipal = ventanaPadre;
        miGrilla = new GridPane();

        rutaImagenSeleccionada = "";

        configurarGrilla(anchoFormulario, altoFormulario);
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
        miGrilla.setAlignment(Pos.TOP_CENTER);

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
        Region bloqueSeparador = new Region();
        bloqueSeparador.prefHeightProperty().bind(laVentanaPrincipal.heightProperty().multiply(0.05));
        columna = 0;
        fila = 0;
        colSpan = 2;
        rowSpan = 1;
        miGrilla.add(bloqueSeparador, columna, fila, colSpan, rowSpan);
        // *********************************************************************

        // Primera de la grilla: Titulo
        Text titulo = new Text("Formulario creación de categorías");
        titulo.setFill(Color.web("#E82E68"));
        titulo.setFont(Font.font("verdana", FontWeight.BOLD, 25));
        GridPane.setHalignment(titulo, HPos.CENTER);

        fila = 1;
        miGrilla.add(titulo, columna, fila, colSpan, rowSpan);
    }

    private void armarCombo() {
        comboCategoria = new ComboBox<>();
        List<Categoria> arregloCategoria = ControladorCategoListar.arregloCategoriaActivos();
        Categoria primeraOpcion = new Categoria(0, "Selecciona categoria", true, 0, "", "");
        comboCategoria.getItems().add(primeraOpcion);
        for (Categoria objCategoria : arregloCategoria) {
            comboCategoria.getItems().add(objCategoria);
        }
        comboCategoria.getSelectionModel().select(0);
        comboCategoria.setConverter(new StringConverter<Categoria>() {
            @Override
            public String toString(Categoria obj) {
                return obj.getNombreCategoria();
            }

            @Override
            public Categoria fromString(String string) {
                return null;
            }

        });
    }

    private void crearFormulario() {
        int fila, altoCaja, tamanioFuente, primeraColumna, segundaColumna;

        altoCaja = 30;
        tamanioFuente = 16;
        primeraColumna = 0;
        segundaColumna = 1;

        // Nombre del producto
        fila = 3;
        // --
        Label lblNombreProducto = new Label("Nombre producto: ");
        lblNombreProducto.setFont(Font.font("Verdana", tamanioFuente));
        miGrilla.add(lblNombreProducto, primeraColumna, fila);
        // --
        cajaNombreProducto = new TextField();
        cajaNombreProducto.setPrefHeight(altoCaja);
        GridPane.setHgrow(cajaNombreProducto, Priority.ALWAYS);
        cajaNombreProducto.prefWidthProperty().bind(laVentanaPrincipal.widthProperty().multiply(0.6));
        Formulario.cantidadCaracteres(cajaNombreProducto, 30);
        miGrilla.add(cajaNombreProducto, segundaColumna, fila);
        // *********************************************************************

        // Precio del producto
        fila = 4;
        Label lblPrecioProducto = new Label("Precio producto:");
        lblPrecioProducto.setFont(Font.font("Verdana", tamanioFuente));
        miGrilla.add(lblPrecioProducto, primeraColumna, fila);
        // --
        cajaPrecioProducto = new TextField();
        cajaPrecioProducto.setPrefHeight(altoCaja);
        GridPane.setHgrow(cajaPrecioProducto, Priority.ALWAYS);
        cajaPrecioProducto.prefWidthProperty().bind(laVentanaPrincipal.widthProperty().multiply(0.6));
        Formulario.cantidadCaracteres(cajaPrecioProducto, 12);
        Formulario.soloDecimales(cajaPrecioProducto);
        miGrilla.add(cajaPrecioProducto, segundaColumna, fila);
        // *********************************************************************

        // Cantidad en inventario
        fila = 5;
        Label lblCantidadProducto = new Label("Cantidad en inventario:");
        lblCantidadProducto.setFont(Font.font("Verdana", tamanioFuente));
        miGrilla.add(lblCantidadProducto, primeraColumna, fila);
        // --
        cajaCantidadProducto = new TextField();
        cajaCantidadProducto.setPrefHeight(altoCaja);
        GridPane.setHgrow(cajaCantidadProducto, Priority.ALWAYS);
        cajaCantidadProducto.prefWidthProperty().bind(laVentanaPrincipal.widthProperty().multiply(0.6));
        Formulario.cantidadCaracteres(cajaCantidadProducto, 6);
        Formulario.soloNumeros(cajaCantidadProducto);
        miGrilla.add(cajaCantidadProducto, segundaColumna, fila);
        // *********************************************************************

        // Combo de categorías
        fila = 6;
        Label lblCategoria = new Label("Seleccione categoría: ");
        lblCategoria.setFont(Font.font("Verdana", tamanioFuente));
        miGrilla.add(lblCategoria, primeraColumna, fila);
        // --
        armarCombo();
        comboCategoria.setPrefHeight(altoCaja);
        comboCategoria.setMaxWidth(Double.MAX_VALUE);
        comboCategoria.prefWidthProperty().bind(laVentanaPrincipal.widthProperty().multiply(0.6));
        miGrilla.add(comboCategoria, segundaColumna, fila);
        // *********************************************************************

        // Imagen del producto
        fila = 7;
        Label lblImagen = new Label("Imagen del producto: ");
        lblImagen.setFont(Font.font("Verdana", tamanioFuente));
        miGrilla.add(lblImagen, primeraColumna, fila);
        // --
        cajaImagen = new TextField();
        cajaImagen.setDisable(true);
        cajaImagen.setPrefHeight(altoCaja);
        // GridPane.setHgrow(cajaImagen, Priority.ALWAYS);
        cajaImagen.prefWidthProperty().bind(laVentanaPrincipal.widthProperty().multiply(0.6));

        String[] extensionesPermitidas = {"*.png", "*.jpg", "*.jpeg", "*.gif"};
        FileChooser objSeleccionar = Formulario.selectorImagen("Seleccione la imagen", "Imágenes", extensionesPermitidas);

        Button btnSeleccionarImagen = new Button("+");
        btnSeleccionarImagen.setPrefHeight(altoCaja);
        btnSeleccionarImagen.setOnAction((e) -> {
            rutaImagenSeleccionada = Imagen.obtenerRutaImagen(cajaImagen, objSeleccionar);
            if (rutaImagenSeleccionada.isEmpty()) {
                Mensaje.modal(Alert.AlertType.WARNING, null, "ADVERTENCIA", "Imagen perdida mani");
            }
        });
        // btnSeleccionarImagen.prefWidthProperty().bind(laVentanaPrincipal.widthProperty().multiply(0.6));

        HBox.setHgrow(cajaImagen, Priority.ALWAYS);
        HBox panelHorizontal = new HBox(2);
        panelHorizontal.setAlignment(Pos.BOTTOM_RIGHT);
        panelHorizontal.getChildren().addAll(cajaImagen, btnSeleccionarImagen);
        miGrilla.add(panelHorizontal, segundaColumna, fila);
        // *********************************************************************

        // Botón Grabar
        fila = 8;
        Button btnGrabar = new Button("Crear Producto");
        btnGrabar.setPrefHeight(altoCaja);
        btnGrabar.setMaxWidth(Double.MAX_VALUE);
        btnGrabar.setTextFill(Color.web("#6C3483"));
        btnGrabar.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        miGrilla.add(btnGrabar, segundaColumna, fila);

        btnGrabar.setOnAction(e -> {
            if (formularioDiligenciado(cajaNombreProducto, cajaPrecioProducto, cajaCantidadProducto, comboCategoria)) {
                String nombre = cajaNombreProducto.getText();
                double precio = Double.parseDouble(cajaPrecioProducto.getText());
                int cantidad = Integer.parseInt(cajaCantidadProducto.getText());
                Categoria categoriaSeleccionada = comboCategoria.getValue();
                int codCategoria = categoriaSeleccionada.getCodCategoria();
                Categoria objCategoria = new Categoria(codCategoria, "", true, 0, "", "");

                if (ControladorProductoGrabar.grabar(nombre, precio, cantidad,
                        objCategoria, cajaImagen.getText(), rutaImagenSeleccionada)) {
                    Mensaje.modal(Alert.AlertType.INFORMATION, null, "ÉXITO", "Producto grabado exitosamente");

                    // Limpiar campos
                    cajaNombreProducto.setText("");
                    cajaPrecioProducto.setText("");
                    cajaCantidadProducto.setText("");
                    comboCategoria.getSelectionModel().select(0);
                    cajaNombreProducto.requestFocus();
                } else {
                    Mensaje.modal(Alert.AlertType.ERROR, null, "ERROR", "No se pudo grabar el producto");
                }
            }
        });

        miFormulario.getChildren().add(miGrilla);

    }

    private boolean formularioDiligenciado(TextField cajaNombreProducto, TextField cajaPrecioProducto,
            TextField cajaCantidadProducto, ComboBox comboCategoria) {
        boolean correcto = false;

        String nombre = cajaNombreProducto.getText();
        String precioTexto = cajaPrecioProducto.getText();
        String cantidadTexto = cajaCantidadProducto.getText();
        int indiceSeleccionado = comboCategoria.getSelectionModel().getSelectedIndex();

        if (nombre.isBlank()) {
            cajaNombreProducto.requestFocus();
            Mensaje.modal(Alert.AlertType.WARNING, null, "Advertencia", "El nombre del producto es obligatorio.");
        } else if (precioTexto.isBlank()) {
            cajaPrecioProducto.requestFocus();
            Mensaje.modal(Alert.AlertType.WARNING, null, "Advertencia", "El precio es obligatorio.");
        } else if (cantidadTexto.isBlank()) {
            cajaCantidadProducto.requestFocus();
            Mensaje.modal(Alert.AlertType.WARNING, null, "Advertencia", "La cantidad es obligatoria.");
        } else if (indiceSeleccionado == 0) {
            comboCategoria.requestFocus();
            Mensaje.modal(Alert.AlertType.WARNING, null, "Advertencia", "Debe seleccionar una categoría.");
        } else {
            correcto = true;
        }

        return correcto;
    }

}
