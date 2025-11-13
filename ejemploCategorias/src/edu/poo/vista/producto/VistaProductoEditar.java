package edu.poo.vista.producto;

import edu.poo.modelo.Producto;
import edu.poo.modelo.Categoria;
import edu.poo.recurso.utilidad.Fondo;
import edu.poo.recurso.utilidad.Marco;
import edu.poo.recurso.utilidad.Imagen;
import edu.poo.recurso.utilidad.Mensaje;
import edu.poo.recurso.dominio.Contenedor;
import edu.poo.recurso.utilidad.Formulario;
import edu.poo.recurso.dominio.Configuracion;
import edu.poo.controlador.categoria.ControladorCategoListar;
import edu.poo.controlador.producto.ControladorProductoEditar;
import edu.poo.controlador.producto.ControladorProductoVentana;

import java.util.List;
import javafx.event.ActionEvent;

import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import javafx.scene.control.Button;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.stage.Stage;

public class VistaProductoEditar extends SubScene {

    private final GridPane miGrilla;
    private final StackPane miFormualario;

    private final double anchoMarco;
    private final double altoMarco;

    private String rutaSeleccionada;

    private ComboBox<Categoria> comboCategoria;
    private TextField cajaImagen;

    private final int posicion;
    private final Producto objProducto;
    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;

    public VistaProductoEditar(BorderPane princ, Pane pane,
            double anchoFormulario, double altoFormulario,
            Producto objProductoExterno, int posicionArchivo) {
        super(new StackPane(), anchoFormulario, altoFormulario);

        Background fondo = Fondo.asignarAleatorio(Configuracion.FONDOS);

        miFormualario = (StackPane) getRoot();
        miFormualario.setBackground(fondo);
        miFormualario.setAlignment(Pos.TOP_CENTER);

        anchoMarco = anchoFormulario - (anchoFormulario * 0.3);
        altoMarco = altoFormulario - (altoFormulario * 0.2);
        miGrilla = new GridPane();

        posicion = posicionArchivo;
        objProducto = objProductoExterno;
        panelPrincipal = princ;
        panelCuerpo = pane;
        rutaSeleccionada = "";

        crearMarco();
        crearFormulario();
    }

    public StackPane getMiFormualario() {
        return miFormualario;
    }

    private void crearMarco() {
        Stage ventanaPrincipal = (Stage) miFormualario.getScene().getWindow();
        Rectangle marco = Marco.crear(ventanaPrincipal, anchoMarco, altoMarco,
                Configuracion.DEGRADE_ARREGLO, Configuracion.DEGRADE_BORDE);
        StackPane.setAlignment(marco, Pos.CENTER);
        miFormualario.getChildren().add(marco);
    }

    private void armarCombo() {
        int indice, indiceSeleccionado;

        indice = 0;
        indiceSeleccionado = 0;

        comboCategoria = new ComboBox<>();
        List<Categoria> arregloCategoria = ControladorCategoListar.arregloCategoriaActivos();
        Categoria primeraOpcion = new Categoria(0, "Selecciona categoria", true, 0, "", "");
        comboCategoria.getItems().add(primeraOpcion);
        for (Categoria objCategoria : arregloCategoria) {
            indice++;
            comboCategoria.getItems().add(objCategoria);
            if (objCategoria.getCodCategoria() == objProducto.getCatProducto().getCodCategoria()) {
                indiceSeleccionado = indice;
            }
        }
        comboCategoria.getSelectionModel().select(indiceSeleccionado);
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

        miGrilla.setAlignment(Pos.CENTER);
        miGrilla.setHgap(10);
        miGrilla.setVgap(10);

        int anchoGrilla = (int) (anchoMarco - (anchoMarco * 0.2));
        double columna1 = anchoGrilla * 0.40;
        double columna2 = anchoGrilla * 0.60;

        miGrilla.getColumnConstraints().add(new ColumnConstraints(columna1));
        miGrilla.getColumnConstraints().add(new ColumnConstraints(columna2));

        // Título
        Text titulo = new Text("Formulario Productos");
        titulo.setFill(Color.web("#E6E6E6"));
        titulo.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
        miGrilla.add(titulo, 0, 0, 2, 1);

        // Fila 1: Nombre Producto
        Label lblNombreProducto = new Label("Nombre Producto:");
        miGrilla.add(lblNombreProducto, 0, 1);
        TextField cajaNombreProducto = new TextField();
        cajaNombreProducto.setText(objProducto.getNomProducto());
        cajaNombreProducto.setMaxWidth(columna2);
        Formulario.cantidadCaracteres(cajaNombreProducto, 30);
        miGrilla.add(cajaNombreProducto, 1, 1);

        // Fila 2: Precio
        Label lblPrecioProducto = new Label("Precio:");
        miGrilla.add(lblPrecioProducto, 0, 2);
        TextField cajaPrecioProducto = new TextField();
        cajaPrecioProducto.setText("" + objProducto.getPreProducto());
        Formulario.cantidadCaracteres(cajaPrecioProducto, 12);
        Formulario.soloDecimales(cajaPrecioProducto);
        cajaPrecioProducto.setMaxWidth(columna2);
        miGrilla.add(cajaPrecioProducto, 1, 2);

        // Fila 3: Cantidad
        Label lblCantidadProducto = new Label("Cantidad:");
        miGrilla.add(lblCantidadProducto, 0, 3);
        TextField cajaCantidadProducto = new TextField();
        cajaCantidadProducto.setText("" + objProducto.getCanProducto());
        Formulario.cantidadCaracteres(cajaCantidadProducto, 7);
        Formulario.soloNumeros(cajaCantidadProducto);
        cajaCantidadProducto.setMaxWidth(columna2);
        miGrilla.add(cajaCantidadProducto, 1, 3);
        // Para el combo
        Label lblCategoria = new Label("Categoria: ");
        miGrilla.add(lblCategoria, 0, 4);

        armarCombo();
        comboCategoria.setMaxWidth(columna2);

        miGrilla.add(comboCategoria, 1, 4);

        //Fila 4: La imagen
        Label lblImagen = new Label("Imagen: ");
        miGrilla.add(lblImagen, 0, 5);

        cajaImagen = new TextField();
        cajaImagen.setText(objProducto.getNomImgPubProducto());
        cajaImagen.setDisable(true);
        cajaImagen.setMaxWidth(columna2);

        String[] extensionesPermitidas = {"*.png", "*.jpg", "*.jpeg"};
        FileChooser objSeleccionar = Formulario.selectorImagen("Seleccione la imagen", "Imágenes", extensionesPermitidas);

        Button btnAgregarImg = new Button("+");
        btnAgregarImg.setOnAction((e) -> {
            rutaSeleccionada = Imagen.obtenerRutaImagen(cajaImagen, objSeleccionar);
            if (rutaSeleccionada.isEmpty()) {
                Mensaje.modal(Alert.AlertType.WARNING, null, "ADVERTENCIA", "Imagen perdida mani");
            }
        });
        btnAgregarImg.setMaxWidth(columna2);

        HBox.setHgrow(cajaImagen, Priority.ALWAYS);
        HBox panelHorizontal = new HBox(2);
        panelHorizontal.setAlignment(Pos.BOTTOM_RIGHT);
        panelHorizontal.getChildren().addAll(cajaImagen, btnAgregarImg);
        miGrilla.add(panelHorizontal, 1, 5);

        // Fila 5: Botón Grabar
        Button btnEditar = new Button("Actualizar Producto");
        btnEditar.setMaxWidth(columna2);
        btnEditar.setTextFill(Color.web("#6C3483"));
        btnEditar.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        miGrilla.add(btnEditar, 1, 7);

        btnEditar.setOnAction(e -> {
            if (formularioDiligenciado(cajaNombreProducto, cajaPrecioProducto,
                    cajaCantidadProducto, comboCategoria)) {
                String nombre = cajaNombreProducto.getText();
                double precio = Double.parseDouble(cajaPrecioProducto.getText());
                int cantidad = Integer.parseInt(cajaCantidadProducto.getText());

                // Categoria categoriaSeleccionada = comboCategoria.getValue();
                Categoria cate = comboCategoria.getSelectionModel().getSelectedItem();
                String nomImg = cajaImagen.getText();

                int codi = objProducto.getCodProducto();
                String nocu = objProducto.getNomImgOcuProducto();
                Producto objProEditado = new Producto(codi, nombre, precio, cantidad, cate, nomImg, nocu);
                if (ControladorProductoEditar.actualizar(posicion, objProEditado, rutaSeleccionada)) {
                    Mensaje.modal(Alert.AlertType.INFORMATION, null, "ÉXITO", "Producto actualizado exitosamente");
                    cajaNombreProducto.requestFocus();
                } else {
                    Mensaje.modal(Alert.AlertType.ERROR, null, "ERROR", "No se pudo actualizar el producto");
                }
            }
        });

        Button btnRegresar = new Button("Regresar");
        btnRegresar.setMaxWidth(columna2);
        btnRegresar.setTextFill(Color.web("#6C3483"));
        btnRegresar.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));
        miGrilla.add(btnRegresar, 1, 9);
        btnRegresar.setOnAction((ActionEvent e) -> {
            panelCuerpo = ControladorProductoVentana.administrar(
                    panelPrincipal, panelCuerpo,
                    Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
            panelPrincipal.setCenter(null);
            panelPrincipal.setCenter(panelCuerpo);
        });

        miFormualario.getChildren().add(miGrilla);

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
