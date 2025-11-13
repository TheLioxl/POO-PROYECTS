package edu.poo.vista.producto;

import edu.poo.modelo.Categoria;
import edu.poo.modelo.Producto;
import edu.poo.recurso.utilidad.Fondo;
import edu.poo.recurso.utilidad.Icono;
import edu.poo.recurso.utilidad.Marco;
import edu.poo.recurso.utilidad.Mensaje;
import edu.poo.recurso.dominio.IconoNombre;
import edu.poo.recurso.dominio.Configuracion;
import edu.poo.controlador.producto.ControladorProductoListar;
import edu.poo.controlador.producto.ControladorProductoEliminar;
import edu.poo.controlador.producto.ControladorProductoVentana;
import edu.poo.recurso.dominio.Contenedor;

import java.text.DecimalFormat;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class VistaProductoAdmin extends SubScene {

    private final VBox miColumna;
    private final StackPane miFormulario;
    private HBox panelHorizontalAdmin;
    private TableView<Producto> miTabla;

    private final String centrar = "-fx-alignment: CENTER";
    // entrar el texto 
    private final String izquierda = "-fx-alignment: CENTER-LEFT";
    // alinear el texto a la izquierda
    private final String derecha = "-fx-alignment: CENTER-RIGHT";

    private final double anchoMarco, altoMarco;

    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;

    public VistaProductoAdmin(BorderPane princ, Pane pane, double anchoFrm, double altoFrm /*formulario*/) {
        super(new StackPane(), anchoFrm, altoFrm);
        //
        Background fondo = Fondo.asignarAleatorio(Configuracion.FONDOS);//
        miFormulario = (StackPane) getRoot();
        miFormulario.setBackground(fondo);
        miFormulario.setAlignment(Pos.CENTER);

        anchoMarco = anchoFrm - (anchoFrm * 0.15);
        altoMarco = altoFrm - (altoFrm * 0.10);

        panelPrincipal = princ;
        panelCuerpo = pane;

        miColumna = new VBox();

        crearMarco();
        armarTabla();

    }

    public StackPane getMiFormulario() {
        return miFormulario;
    }

    private void crearMarco() {
        Stage ventanaPrincipal = (Stage) miFormulario.getScene().getWindow();
        Rectangle marco = Marco.crear(ventanaPrincipal, anchoMarco, altoMarco,
                Configuracion.DEGRADE_ARREGLO,
                Configuracion.DEGRADE_BORDE);
        miFormulario.getChildren().add(marco);
    }

    private void armarTabla() {
        Text titulo = new Text("Listado De Productos");
        titulo.setFill(Color.web("#E6E6E6"));
        titulo.setFont(Font.font("verdana", FontWeight.BOLD, 25));

        miTabla = new TableView<>();

        // Celda Código del producto
        // *********************************************************************
        TableColumn<Producto, Integer> columnaCodigoProducto = new TableColumn<>("Código");
        columnaCodigoProducto.setCellValueFactory(new PropertyValueFactory<>("codProducto"));
        columnaCodigoProducto.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.08));
        columnaCodigoProducto.setStyle(centrar);

        TableColumn<Producto, String> columnaNombre = new TableColumn<>("Nombre Producto");
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nomProducto"));
        columnaNombre.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.25));
        columnaNombre.setStyle(izquierda);
        // *********************************************************************

        // Celda categoría
        // *********************************************************************
        TableColumn<Producto, String> codCategoriaColum = new TableColumn<>("Categoría");
        codCategoriaColum.setCellValueFactory(
                miCatego -> {
                    Categoria categoria = miCatego.getValue().getCatProducto();
                    int codCatego = categoria.getCodCategoria();
                    String cadena = categoria.getNombreCategoria() + " (" + codCatego + ")";
                    return new SimpleStringProperty(cadena);
                });
        codCategoriaColum.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.25));
        codCategoriaColum.setStyle(izquierda);
        // *********************************************************************

        // Celda Precio
        // *********************************************************************
        DecimalFormat precioTXT = new DecimalFormat("#,###.00");
        TableColumn<Producto, String> columnaPrecio = new TableColumn<>("Precio");
        columnaPrecio.setCellValueFactory(
                (dato) -> new SimpleStringProperty(precioTXT.format(dato.getValue().getPreProducto()))
        );
        columnaPrecio.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.12));
        columnaPrecio.setStyle(derecha);
        // *********************************************************************

        // Celda cantidad
        // *********************************************************************
        TableColumn<Producto, Integer> columnaCantidad = new TableColumn<>("Cantidad");
        columnaCantidad.setCellValueFactory(new PropertyValueFactory<>("canProducto"));
        columnaCantidad.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.10));
        columnaCantidad.setStyle(centrar);
        // *********************************************************************

        // Celda Imagen
        // *********************************************************************
        TableColumn<Producto, String> columnaImagen = new TableColumn<>("Imagen");
        columnaImagen.setCellValueFactory(new PropertyValueFactory<>("nomImgOcuProducto"));
        columnaImagen.setCellFactory(column -> new TableCell<Producto, String>() {
            @Override
            protected void updateItem(String nombreImagen, boolean bandera) {
                super.updateItem(nombreImagen, bandera);
                if (bandera || nombreImagen == null) {
                    setGraphic(null);
                } else {
                    setGraphic(Icono.obtenerFotosExternas(nombreImagen, 50));
                }
            }
        });

        columnaImagen.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.20));
        columnaImagen.setStyle(centrar);
        // *********************************************************************

        miTabla.getColumns().add(columnaCodigoProducto);
        miTabla.getColumns().add(columnaNombre);
        miTabla.getColumns().add(codCategoriaColum);
        miTabla.getColumns().add(columnaPrecio);
        miTabla.getColumns().add(columnaCantidad);
        miTabla.getColumns().add(columnaImagen);

        double nuevoAncho = anchoMarco * 0.95;
        double nuevoAlto = altoMarco * 0.75;

        miTabla.setMaxWidth(nuevoAncho);
        miTabla.setMaxHeight(nuevoAlto);

        // más pulido para que NO salga el scroll horizontal en la tabla
        miTabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        ObservableList<Producto> datosCompletos = ControladorProductoListar.cargarDatos();
        miTabla.setItems(datosCompletos);
        miTabla.refresh();

        armarIconosAdministrar();

        miColumna.setSpacing(15);
        miColumna.setAlignment(Pos.CENTER);
        miColumna.setPadding(new Insets(15, 0, 0, 0));
        miColumna.getChildren().addAll(titulo, miTabla);
        // Agregar los botones del panel Admin
        miColumna.getChildren().add(panelHorizontalAdmin);

        miFormulario.getChildren().add(miColumna);

    }

    private void armarIconosAdministrar() {
        int anchoBoton = 40;
        Button btnEliminar = new Button();
        btnEliminar.setPrefWidth(anchoBoton);
        btnEliminar.setCursor(Cursor.HAND);
        btnEliminar.setGraphic(Icono.obtenerIcono(IconoNombre.ICONO_BORRAR, 16));

        btnEliminar.setOnAction((e) -> {
            if (miTabla.getSelectionModel().getSelectedItem() == null) {
                Mensaje.modal(Alert.AlertType.WARNING, null, "Advertencia", "Debes seleccionar un producto!");
            } else {
                String texto1, texto2, texto3, texto4;
                Producto objProducto = miTabla.getSelectionModel().getSelectedItem();

                texto1 = "¿Seguro que quieres borrar el producto?\n";
                texto2 = "\nCodigo: " + objProducto.getCodProducto();
                texto3 = "\nNombre: " + objProducto.getNomProducto();
                texto4 = "\nEsto es irreversible!!!";

                Alert msg = new Alert(Alert.AlertType.CONFIRMATION);
                msg.setTitle("Advertencia Borrar");
                msg.setHeaderText(null);
                msg.setContentText(texto1 + texto2 + texto3 + texto4);
                msg.initOwner(null);
                if (msg.showAndWait().get() == ButtonType.OK) {
                    int posicion = miTabla.getSelectionModel().getSelectedIndex();
                    if (ControladorProductoEliminar.eliminar(posicion)) {
                        miTabla.setItems(ControladorProductoListar.cargarDatos());
                        miTabla.refresh();
                        Mensaje.modal(Alert.AlertType.INFORMATION, null, "Exito", "Producto borrado");

                    } else {
                        Mensaje.modal(Alert.AlertType.ERROR, null, "Error", "No se pudo borrar");
                    }
                } else {
                    miTabla.getSelectionModel().clearSelection();

                }
            }
        });

        //El de actulizar 
        Button btnEditar = new Button();
        btnEditar.setPrefWidth(anchoBoton);
        btnEditar.setCursor(Cursor.HAND);
        btnEditar.setGraphic(Icono.obtenerIcono(IconoNombre.ICONO_EDITAR, 16));

        btnEditar.setOnAction((e) -> {
            if (miTabla.getSelectionModel().getSelectedItem() == null) {
                Mensaje.modal(Alert.AlertType.WARNING, null, "Advertencia", "No ha seleccionado un producto para editar");
            } else {
                Producto objPro = miTabla.getSelectionModel().getSelectedItem();
                int posicion = miTabla.getSelectionModel().getSelectedIndex();
                panelCuerpo = ControladorProductoVentana.editar(
                        panelPrincipal, panelCuerpo,
                        Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor(),
                        objPro, posicion);
                panelPrincipal.setCenter(null);
                panelPrincipal.setCenter(panelCuerpo);
            }

        });

        //El de cancelar
        Button btnCancelar = new Button();
        btnCancelar.setPrefWidth(anchoBoton);
        btnCancelar.setCursor(Cursor.HAND);
        btnCancelar.setGraphic(Icono.obtenerIcono(IconoNombre.ICONO_CANCELAR, 16));

        btnCancelar.setOnAction((e) -> {
            miTabla.getSelectionModel().clearSelection();
        });
        panelHorizontalAdmin = new HBox(4);
        panelHorizontalAdmin.setAlignment(Pos.CENTER);
        panelHorizontalAdmin.getChildren().addAll(btnEliminar, btnEditar, btnCancelar);
    }

}
