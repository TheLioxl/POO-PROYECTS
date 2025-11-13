package edu.poo.vista.producto;

import edu.poo.modelo.Producto;
import edu.poo.modelo.Categoria;
import edu.poo.recurso.utilidad.Fondo;
import edu.poo.recurso.utilidad.Marco;
import edu.poo.recurso.dominio.Configuracion;
import edu.poo.controlador.producto.ControladorProductoCant;
import edu.poo.controlador.producto.ControladorProductoListar;

import java.text.DecimalFormat;
import javafx.beans.property.SimpleObjectProperty;

import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.Region;
import javafx.scene.text.FontWeight;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Background;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;

public class VistaProductoListar extends SubScene {

    private final StackPane miFormulario;
    private final Stage laVentanaPrincipal;
    private final VBox organizadorVertical;
    private final TableView<Producto> miTabla;

    private final String centrar = "-fx-alignment: CENTER;";
    private final String derecha = "-fx-alignment: CENTER-RIGHT;";
    private final String izquierda = "-fx-alignment: CENTER-LEFT;";

    public VistaProductoListar(Stage ventanaPadre, double ancho, double alto) {
        super(new StackPane(), ancho, alto);

        Background fondo = Fondo.asignarAleatorio(Configuracion.FONDOS);
        miFormulario = (StackPane) getRoot();
        miFormulario.setBackground(fondo);
        miFormulario.setAlignment(Pos.CENTER);

        laVentanaPrincipal = ventanaPadre;

        miTabla = new TableView<>();
        organizadorVertical = new VBox();

        configurarOrganizadorVertical();
        crearMarco();
        crearTitulo();
        crearTabla();
    }

    public StackPane getMiFormulario() {
        return miFormulario;
    }

    private void configurarOrganizadorVertical() {
        organizadorVertical.setSpacing(20);
        organizadorVertical.setAlignment(Pos.TOP_CENTER);
        organizadorVertical.prefWidthProperty().bind(laVentanaPrincipal.widthProperty());
        organizadorVertical.prefHeightProperty().bind(laVentanaPrincipal.heightProperty());

    }

    private void crearMarco() {
        double ancho = Configuracion.PORCENTAJE_ANCHO_MARCO;
        double alto = Configuracion.PORCENTAJE_ALTO_MARCO;
        Rectangle miMarco = Marco.crear(laVentanaPrincipal, ancho, alto,
                Configuracion.DEGRADE_ARREGLO, Configuracion.DEGRADE_BORDE);
        miFormulario.getChildren().add(miMarco);
    }

    private void crearTitulo() {
        Region bloqueSeparador = new Region();
        bloqueSeparador.prefHeightProperty().bind(laVentanaPrincipal.heightProperty().multiply(0.05));
        organizadorVertical.getChildren().add(0, bloqueSeparador);

        int cantidadCategorias = ControladorProductoCant.obtenerCantidadCarros();

        Text titulo = new Text("Listar productos (" + cantidadCategorias + ")");
        titulo.setFill(Color.web("#E82E68"));
        titulo.setFont(Font.font("verdana", FontWeight.BOLD, 25));
        organizadorVertical.getChildren().add(titulo);
    }

    private void crearTabla() {
        crearColumnasTabla();

        // Origen de los datos del tableView
        ObservableList<Producto> datosCompletos = ControladorProductoListar.cargarDatos();
        miTabla.setItems(datosCompletos);
        miTabla.refresh();
        // *********************************************************************

        // Evita que salga el scroll horizontal en el tableView
        miTabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        // *********************************************************************

        // Ancho y alto responsive del tableView
        miTabla.maxWidthProperty().bind(laVentanaPrincipal.widthProperty().multiply(0.70));
        miTabla.maxHeightProperty().bind(laVentanaPrincipal.heightProperty().multiply(0.55));

        laVentanaPrincipal.heightProperty().addListener((o, valorAnterior, valorNuevo) -> {
            miTabla.setPrefHeight(valorNuevo.doubleValue());
        });
        VBox.setVgrow(miTabla, Priority.ALWAYS);
        // *********************************************************************

        organizadorVertical.getChildren().add(miTabla);

        miFormulario.getChildren().add(organizadorVertical);
    }

    private void crearColumnasTabla() {
        // Código del producto
        TableColumn<Producto, Integer> columnaCodigoProducto = new TableColumn<>("Código");
        columnaCodigoProducto.setCellValueFactory(new PropertyValueFactory<>("codProducto"));
        columnaCodigoProducto.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.08));
        columnaCodigoProducto.setStyle(centrar);
        miTabla.getColumns().add(columnaCodigoProducto);

        TableColumn<Producto, String> columnaNombre = new TableColumn<>("Nombre Producto");
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nomProducto"));
        columnaNombre.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.25));
        columnaNombre.setStyle(izquierda);
        miTabla.getColumns().add(columnaNombre);
        // *********************************************************************

        // Categoría del producto
        // *********************************************************************
        TableColumn<Producto, Producto> codCategoriaColum = new TableColumn<>("Categoría");
        codCategoriaColum.setCellValueFactory(miProducto -> new SimpleObjectProperty<>(miProducto.getValue()));
        codCategoriaColum.setCellFactory(column -> new TableCell<Producto, Producto>() {
            @Override
            protected void updateItem(Producto objPro, boolean bandera) {
                super.updateItem(objPro, bandera);
                String estiloVerde = "-fx-text-fill: green;";
                String estiloRojo = "-fx-text-fill: red;";
                if (bandera || objPro == null) {
                    setText(null);
                    setStyle("");
                } else {
                    int codCatego = objPro.getCatProducto().getCodCategoria();
                    String nombreCatego = objPro.getCatProducto().getNombreCategoria();
                    String cadena = nombreCatego + " (" + codCatego + ")";
                    setText(cadena);
                    setStyle(objPro.getCatProducto().isEstadoCategoria() ? estiloVerde : estiloRojo);
                }
            }
        });
        codCategoriaColum.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.20));
        codCategoriaColum.setStyle(izquierda);
        miTabla.getColumns().add(codCategoriaColum);
        // *********************************************************************

        // Precio del producto
        DecimalFormat precioTXT = new DecimalFormat("#,###.00");
        TableColumn<Producto, String> columnaPrecio = new TableColumn<>("Precio");
        columnaPrecio.setCellValueFactory(
                (dato) -> new SimpleStringProperty(precioTXT.format(dato.getValue().getPreProducto()))
        );
        columnaPrecio.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.12));
        columnaPrecio.setStyle(derecha);
        miTabla.getColumns().add(columnaPrecio);
        // *********************************************************************

        // Cantidad en inventario
        TableColumn<Producto, Integer> columnaCantidad = new TableColumn<>("Cantidad");
        columnaCantidad.setCellValueFactory(new PropertyValueFactory<>("canProducto"));
        columnaCantidad.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.10));
        columnaCantidad.setStyle(centrar);
        miTabla.getColumns().add(columnaCantidad);
        // *********************************************************************

        // Nombre de la imagen
        TableColumn<Producto, String> columnaImagen = new TableColumn<>("Imagen");
        columnaImagen.setCellValueFactory(new PropertyValueFactory<>("nomImgPubProducto"));
        columnaImagen.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.25));
        columnaImagen.setStyle(izquierda);
        miTabla.getColumns().add(columnaImagen);
        // *********************************************************************
    }

}
