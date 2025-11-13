package edu.poo.vista.categoria;

import edu.poo.modelo.Categoria;
import edu.poo.recurso.utilidad.Marco;
import edu.poo.recurso.utilidad.Fondo;
import edu.poo.recurso.utilidad.Icono;
import edu.poo.recurso.utilidad.Mensaje;
import edu.poo.recurso.dominio.Contenedor;
import edu.poo.recurso.dominio.IconoNombre;
import edu.poo.recurso.dominio.Configuracion;
import edu.poo.controlador.categoria.ControladorCategoCant;
import edu.poo.controlador.categoria.ControladorCategoListar;
import edu.poo.controlador.categoria.ControladorCategoEliminar;
import edu.poo.controlador.categoria.ControladorCategoVentana;

import javafx.stage.Stage;
import javafx.scene.Cursor;
import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.text.Font;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Background;
import javafx.scene.control.TableView;
import javafx.scene.control.TableCell;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class VistaCategoAdmin extends SubScene {

    private HBox panelHorizontalAdmin;
    private final StackPane miFormulario;
    private final Stage laVentanaPrincipal;
    private final VBox organizadorVertical;
    private final TableView<Categoria> miTabla;

    private final String centrar = "-fx-alignment: CENTER;";
    private final String derecha = "-fx-alignment: CENTER-RIGHT;";
    private final String izquierda = "-fx-alignment: CENTER-LEFT;";

    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;

    private StringProperty titulo;

    public VistaCategoAdmin(Stage ventanaPadre, BorderPane princ, Pane pane, double ancho, double alto) {
        super(new StackPane(), ancho, alto);

        Background fondo = Fondo.asignarAleatorio(Configuracion.FONDOS);
        miFormulario = (StackPane) getRoot();
        miFormulario.setBackground(fondo);
        miFormulario.setAlignment(Pos.CENTER);

        laVentanaPrincipal = ventanaPadre;

        panelPrincipal = princ;
        panelCuerpo = pane;

        miTabla = new TableView<>();
        organizadorVertical = new VBox();

        configurarOrganizadorVertical();
        crearMarco();
        crearTitulo();
        crearTabla();
        armarIconosAdministrar();
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

        titulo = new SimpleStringProperty("Administrar Categorías (" + cantidadCategorias + ")");
        Label lblTitulo = new Label();
        lblTitulo.textProperty().bind(titulo);
        lblTitulo.setTextFill(Color.web("#E82E68"));
        lblTitulo.setFont(Font.font("verdana", FontWeight.BOLD, 25));
        organizadorVertical.getChildren().add(lblTitulo);
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

        // Miniatura de la imagen
        // *********************************************************************
        TableColumn<Categoria, String> columnaImagen = new TableColumn<>("Imagen");
        columnaImagen.setCellValueFactory(new PropertyValueFactory<>("nombreImagenPrivadoCategoria"));
        columnaImagen.setCellFactory(column -> new TableCell<Categoria, String>() {
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

        columnaImagen.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.38));
        columnaImagen.setStyle(centrar);
        miTabla.getColumns().add(columnaImagen);
        // *********************************************************************
    }

    private void armarIconosAdministrar() {
        int anchoBoton = 40;
        int tamanioIcono = 16;

        Button btnEliminar = new Button();
        btnEliminar.setPrefWidth(anchoBoton);
        btnEliminar.setCursor(Cursor.HAND);
        btnEliminar.setGraphic(Icono.obtenerIcono(IconoNombre.ICONO_BORRAR, tamanioIcono));

        btnEliminar.setOnAction((e) -> {
            if (miTabla.getSelectionModel().getSelectedItem() == null) {
                Mensaje.modal(Alert.AlertType.WARNING, null, "Advertencia", "Debes seleccionar un producto!");
            } else {
                Categoria objcate = miTabla.getSelectionModel().getSelectedItem();

                if (objcate.getCantidadProductoCategoria() == 0) {
                    String texto1, texto2, texto3, texto4;

                    texto1 = "¿Seguro que quieres borrar la categoría?\n";
                    texto2 = "\nCodigo: " + objcate.getCodCategoria();
                    texto3 = "\nNombre: " + objcate.getNombreCategoria();
                    texto4 = "\nEsto es irreversible!!!";

                    Alert msg = new Alert(Alert.AlertType.CONFIRMATION);
                    msg.setTitle("Advertencia Borrar");
                    msg.setHeaderText(null);
                    msg.setContentText(texto1 + texto2 + texto3 + texto4);
                    msg.initOwner(null);
                    if (msg.showAndWait().get() == ButtonType.OK) {
                        int posicion = miTabla.getSelectionModel().getSelectedIndex();
                        if (ControladorCategoEliminar.borrarRegistro(posicion)) {
                            // Actualiza la cantidad de categorías
                            int cantidadCategorias = ControladorCategoCant.obtener();
                            titulo.set("Administrar Categorías - (" + cantidadCategorias + ")");

                            // Actualiza la tabla
                            miTabla.setItems(ControladorCategoListar.cargarDatos());
                            miTabla.refresh();

                            Mensaje.modal(Alert.AlertType.INFORMATION, null, "Exito", "Categoría eliminada");
                        } else {
                            Mensaje.modal(Alert.AlertType.ERROR, null, "Error", "No se pudo borrar la categoría");
                        }
                    } else {
                        miTabla.getSelectionModel().clearSelection();
                    }
                } else {
                    Mensaje.modal(Alert.AlertType.WARNING, null, "Advertencia", "Existen productos en esta categoría");
                }
            }
        });
        // *********************************************************************

        // Botón actualizar
        Button btnEditar = new Button();
        btnEditar.setPrefWidth(anchoBoton);
        btnEditar.setCursor(Cursor.HAND);
        btnEditar.setGraphic(Icono.obtenerIcono(IconoNombre.ICONO_EDITAR, tamanioIcono));

        btnEditar.setOnAction((ActionEvent t) -> {
            if (miTabla.getSelectionModel().getSelectedItem() == null) {
                Mensaje.modal(Alert.AlertType.WARNING, null, "Advertencia", "No ha seleccionado una categoría para editar");
            } else {
                Categoria objCate = miTabla.getSelectionModel().getSelectedItem();
                int posicion = miTabla.getSelectionModel().getSelectedIndex();

                panelCuerpo = ControladorCategoVentana.editar(
                        laVentanaPrincipal, panelPrincipal, panelCuerpo,
                        Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor(),
                        objCate, posicion);
                panelPrincipal.setCenter(null);
                panelPrincipal.setCenter(panelCuerpo);
            }
        });
        // *********************************************************************

        // Desmarcar la categoría seleccionada
        Button btnCancelar = new Button();
        btnCancelar.setPrefWidth(anchoBoton);
        btnCancelar.setCursor(Cursor.HAND);
        btnCancelar.setGraphic(Icono.obtenerIcono(IconoNombre.ICONO_CANCELAR, tamanioIcono));

        btnCancelar.setOnAction((e) -> {
            miTabla.getSelectionModel().clearSelection();
        });
        panelHorizontalAdmin = new HBox(4);
        panelHorizontalAdmin.setAlignment(Pos.CENTER);
        panelHorizontalAdmin.getChildren().addAll(btnEliminar, btnEditar, btnCancelar);

        organizadorVertical.getChildren().add(panelHorizontalAdmin);
    }

}
