package edu.poo.vista.categoria;

import edu.poo.controlador.categoria.ControladorCategoCant;
import edu.poo.modelo.Categoria;
import edu.poo.recurso.utilidad.Marco;
import edu.poo.recurso.utilidad.Fondo;
import edu.poo.recurso.dominio.Configuracion;
import edu.poo.controlador.categoria.ControladorCategoListar;

import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Background;
import javafx.scene.control.TableView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;

public class VistaCategoListar extends SubScene {

    private final StackPane miFormulario;
    private final Stage laVentanaPrincipal;
    private final VBox organizadorVertical;
    private final TableView<Categoria> miTabla;

    private final String centrar = "-fx-alignment: CENTER;";
    private final String derecha = "-fx-alignment: CENTER-RIGHT;";
    private final String izquierda = "-fx-alignment: CENTER-LEFT;";

    public VistaCategoListar(Stage ventanaPadre, double ancho, double alto) {
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

        int cantidadCategorias = ControladorCategoCant.obtener();

        Text titulo = new Text("Listar Categorías (" + cantidadCategorias + ")");
        titulo.setFill(Color.web("#E82E68"));
        titulo.setFont(Font.font("verdana", FontWeight.BOLD, 25));
        organizadorVertical.getChildren().add(titulo);
    }

    private void crearTabla() {
        crearColumnasTabla();

        // Origen de los datos del tableView
        ObservableList<Categoria> datosCompletos = ControladorCategoListar.cargarDatos();
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
        // Codigo de la categoría
        TableColumn<Categoria, Integer> columnaCod = new TableColumn<>("Cod");
        columnaCod.setCellValueFactory(new PropertyValueFactory<>("CodCategoria"));
        columnaCod.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.07));
        columnaCod.setStyle(derecha);
        miTabla.getColumns().add(columnaCod);
        // *********************************************************************

        // Nombre de la categoría
        TableColumn<Categoria, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCategoria"));
        columnaNombre.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.25));
        columnaNombre.setStyle(izquierda);
        miTabla.getColumns().add(columnaNombre);
        // *********************************************************************

        // Columna estado activo - inactivo
        // *********************************************************************
        TableColumn<Categoria, String> columnaEstado = new TableColumn<>("Estado");
        columnaEstado.setCellValueFactory(
                objCategoria -> {
                    String estado = "Inactivo";
                    if (objCategoria.getValue().isEstadoCategoria()) {
                        estado = "Activo";
                    }
                    return new SimpleStringProperty(estado);
                }
        );
        columnaEstado.setCellFactory(column -> new TableCell<Categoria, String>() {
            @Override
            protected void updateItem(String estadoTXT, boolean bandera) {
                super.updateItem(estadoTXT, bandera);
                String estiloVerde = "-fx-text-fill: green;-fx-alignment: CENTER;";
                String estiloRojo = "-fx-text-fill: red;-fx-alignment: CENTER;";
                if (bandera || estadoTXT == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(estadoTXT);
                    setStyle(estadoTXT.equals("Activo") ? estiloVerde : estiloRojo);
                }
            }
        });
        columnaEstado.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        miTabla.getColumns().add(columnaEstado);
        // *********************************************************************

        // Cantidad de productos de cada categoria
        TableColumn<Categoria, Integer> columnaCantPro = new TableColumn<>("Productos");
        columnaCantPro.setCellValueFactory(new PropertyValueFactory<>("cantidadProductoCategoria"));
        columnaCantPro.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        columnaCantPro.setStyle(centrar);
        miTabla.getColumns().add(columnaCantPro);
        // *********************************************************************

        // Nombre de la imagen de la categoría
        TableColumn<Categoria, String> columnaImagen = new TableColumn<>("Imagen");
        columnaImagen.setCellValueFactory(new PropertyValueFactory<>("nombreImagenPublicoCategoria"));
        columnaImagen.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.38));
        columnaImagen.setStyle(izquierda);
        miTabla.getColumns().add(columnaImagen);
        // *********************************************************************
    }

}
