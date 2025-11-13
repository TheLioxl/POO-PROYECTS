package edu.poo.vista.categoria;

import edu.poo.modelo.Categoria;
import edu.poo.recurso.dominio.Ruta;
import edu.poo.recurso.utilidad.Icono;
import edu.poo.recurso.utilidad.Fondo;
import edu.poo.recurso.utilidad.Marco;
import edu.poo.recurso.dominio.IconoNombre;
import edu.poo.recurso.dominio.Configuracion;
import edu.poo.controlador.categoria.ControladorCategoUna;
import edu.poo.controlador.categoria.ControladorCategoCant;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.Cursor;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontWeight;
import javafx.beans.binding.Bindings;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Background;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VistaCategoCarrusel extends SubScene {

    // Borde para visualizar el tamaño de los paneles del BorderPane
    // String borderPanel = "-fx-border-color: red";
    private final BorderPane miBorderPane;
    private final Stage laVentanaPrincipal;
    private final VBox organizadorVertical;

    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;

    private int indiceActual;
    private int totalCategorias;
    private Categoria objCargado;

    private StringProperty categoTitulo;
    private StringProperty categoNombre;
    private ObjectProperty<Image> categoImagen;
    private BooleanProperty categoEstado;
    private IntegerProperty categoCantPro;

    public VistaCategoCarrusel(Stage ventanaPadre, BorderPane princ, Pane pane, double anchoPanel, double altoPanel, int indice) {
        super(new BorderPane(), anchoPanel, altoPanel);

        indiceActual = indice;
        objCargado = ControladorCategoUna.obtenerCategoria(indice);

        miBorderPane = (BorderPane) this.getRoot();

        laVentanaPrincipal = ventanaPadre;
        panelPrincipal = princ;
        panelCuerpo = pane;

        organizadorVertical = new VBox();
        configurarOrganizadorVertical();
        crearTitulo();

        construirPanelIzquierdo(0.14);
        construirPanelDerecho(0.14);
        construirPanelCentro();
    }

    public BorderPane getMiBorderPane() {
        return miBorderPane;
    }

    private void construirPanelIzquierdo(double porcentaje) {
        Button btnAnterior = new Button();
        btnAnterior.setGraphic(Icono.obtenerIcono("btnAtras.png", 80));
        btnAnterior.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        btnAnterior.setCursor(Cursor.HAND);
        btnAnterior.setOnAction(e -> {
            indiceActual = obtenerIndice("Anterior", indiceActual, totalCategorias);
            objCargado = ControladorCategoUna.obtenerCategoria(indiceActual);

            categoTitulo.set("Detalle de la categoría (" + (indiceActual + 1) + " / " + totalCategorias + ")");

            categoNombre.set(objCargado.getNombreCategoria());
            // Actualizando la imagen
            FileInputStream imgArchivo;
            try {
                String rutaNuevaImagen = Ruta.RUTA_FOTOS + Configuracion.SEPARADOR_CARPETA + objCargado.getNombreImagenPrivadoCategoria();
                imgArchivo = new FileInputStream(rutaNuevaImagen);
                Image imgnueva = new Image(imgArchivo);
                categoImagen.set(imgnueva);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(VistaCategoCarrusel.class.getName()).log(Level.SEVERE, null, ex);
            }
            // *****************************************************************
            categoEstado.set(objCargado.isEstadoCategoria());
            categoCantPro.set(objCargado.getCantidadProductoCategoria());
        });

        StackPane panelIzquierdo = new StackPane();
        // panelIzquierdo.setStyle(borderPanel);
        panelIzquierdo.prefWidthProperty().bind(laVentanaPrincipal.widthProperty().multiply(porcentaje));
        panelIzquierdo.getChildren().add(btnAnterior);
        miBorderPane.setLeft(panelIzquierdo);
    }

    private void construirPanelDerecho(double porcentaje) {
        Button btnSiguiente = new Button();
        btnSiguiente.setGraphic(Icono.obtenerIcono("btnSiguiente.png", 80));
        btnSiguiente.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        btnSiguiente.setCursor(Cursor.HAND);
        btnSiguiente.setOnAction((ActionEvent t) -> {
            indiceActual = obtenerIndice("Siguiente", indiceActual, totalCategorias);
            objCargado = ControladorCategoUna.obtenerCategoria(indiceActual);
            
            categoTitulo.set("Detalle de la categoría (" + (indiceActual + 1) + " / " + totalCategorias + ")");

            categoNombre.set(objCargado.getNombreCategoria());
            // Actualizando la imagen
            FileInputStream imgArchivo;
            try {
                String rutaNuevaImagen = Ruta.RUTA_FOTOS + Configuracion.SEPARADOR_CARPETA + objCargado.getNombreImagenPrivadoCategoria();
                imgArchivo = new FileInputStream(rutaNuevaImagen);
                Image imgnueva = new Image(imgArchivo);
                categoImagen.set(imgnueva);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(VistaCategoCarrusel.class.getName()).log(Level.SEVERE, null, ex);
            }
            // *****************************************************************
            categoEstado.set(objCargado.isEstadoCategoria());
            categoCantPro.set(objCargado.getCantidadProductoCategoria());
        });

        StackPane panelDerecho = new StackPane();
        // panelIzquierdo.setStyle(borderPanel);
        panelDerecho.prefWidthProperty().bind(laVentanaPrincipal.widthProperty().multiply(porcentaje));
        panelDerecho.getChildren().add(btnSiguiente);
        miBorderPane.setRight(panelDerecho);
    }

    private void configurarOrganizadorVertical() {
        organizadorVertical.setSpacing(10);
        organizadorVertical.setAlignment(Pos.TOP_CENTER);
        organizadorVertical.prefWidthProperty().bind(laVentanaPrincipal.widthProperty());
        organizadorVertical.prefHeightProperty().bind(laVentanaPrincipal.heightProperty());
    }

    private void crearTitulo() {
        Region bloqueSeparador = new Region();
        bloqueSeparador.prefHeightProperty().bind(laVentanaPrincipal.heightProperty().multiply(0.10));
        organizadorVertical.getChildren().add(0, bloqueSeparador);

        totalCategorias = ControladorCategoCant.obtener();
        categoTitulo = new SimpleStringProperty("Detalle de la categoría (" + (indiceActual + 1) + " / " + totalCategorias + ")");

        Label lblTitulo = new Label();
        lblTitulo.textProperty().bind(categoTitulo);
        lblTitulo.setTextFill(Color.web("#E82E68"));
        lblTitulo.setFont(Font.font("verdana", FontWeight.BOLD, 25));
        organizadorVertical.getChildren().add(lblTitulo);
    }

    private void panelOpciones() {
        int anchoBoton = 40;
        int tamanioIcono = 16;

        Button btnEliminar = new Button();
        btnEliminar.setPrefWidth(anchoBoton);
        btnEliminar.setCursor(Cursor.HAND);
        btnEliminar.setGraphic(Icono.obtenerIcono(IconoNombre.ICONO_BORRAR, tamanioIcono));

        btnEliminar.setOnAction((t) -> {
//            int cantidad = ControladorCarro.obtenerCantidadCarros();
//            if (cantidad > 0) {
//                Alert msg = new Alert(Alert.AlertType.CONFIRMATION);
//                msg.setTitle("Advertencia");
//                msg.setHeaderText(null);
//                msg.setContentText("¿Seguro que desea eliminar el carro?");
//                msg.initOwner(escenarioActual);
//                if (msg.showAndWait().get() == ButtonType.OK) {
//                    ControladorCarro.eliminar(indice);
//                    indice = ControladorCarroDetalle.rotar("anterior", indice,
//                            lblMarca, lblModelo, lblDescripcion, lblNombreImagen,
//                            lblContador, imagenMostrar, anchoPanel, altoPanel);
//                }
//            } else {
//                Mensaje.modal(Alert.AlertType.WARNING, escenarioActual, "Advertencia",
//                        "No hay carros");
//            }
        });

        Button btnActualizar = new Button();
        btnActualizar.setPrefWidth(anchoBoton);
        btnActualizar.setCursor(Cursor.HAND);
        btnActualizar.setGraphic(Icono.obtenerIcono(IconoNombre.ICONO_EDITAR, tamanioIcono));

        btnActualizar.setOnAction((ActionEvent t) -> {
//            panelCuerpo = ControladorCarroVentana.carroEditar(
//                    panelPrincipal, panelCuerpo,
//                    Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor(),
//                    indice);
//            panelPrincipal.setCenter(null);
//            panelPrincipal.setCenter(panelCuerpo);
        });

        HBox panelHorizontalBotones = new HBox(4);
        panelHorizontalBotones.setAlignment(Pos.CENTER);
        panelHorizontalBotones.getChildren().addAll(btnEliminar, btnActualizar);

        organizadorVertical.getChildren().add(panelHorizontalBotones);
        // *********************************************************************
    }

    private void construirPanelCentro() {
        StackPane centerPane = new StackPane();

        // Fondo
        Background fondo = Fondo.asignarAleatorio(Configuracion.FONDOS);
        centerPane.setBackground(fondo);
        // *********************************************************************

        // Marco
        Rectangle miMarco = Marco.crear(laVentanaPrincipal, 0.55, 0.75,
                Configuracion.DEGRADE_ARREGLO, Configuracion.DEGRADE_BORDE);
        centerPane.getChildren().addAll(miMarco, organizadorVertical);
        // *********************************************************************

        panelOpciones();

        // Nombre de la categoría
        categoNombre = new SimpleStringProperty(objCargado.getNombreCategoria());

        int tamanioFuente = 25;
        Label lblNombreCatego = new Label();
        lblNombreCatego.textProperty().bind(categoNombre);
        lblNombreCatego.setFont(Font.font("Verdana", tamanioFuente));
        lblNombreCatego.setTextFill(Color.web("#6C3483"));
        organizadorVertical.getChildren().add(lblNombreCatego);
        // *********************************************************************

        // Imagen de la categoría
        categoImagen = new SimpleObjectProperty<>();

        FileInputStream imgArchivo;
        try {
            String rutaNuevaImagen = Ruta.RUTA_FOTOS + Configuracion.SEPARADOR_CARPETA + objCargado.getNombreImagenPrivadoCategoria();
            imgArchivo = new FileInputStream(rutaNuevaImagen);
            Image imgNueva = new Image(imgArchivo);
            categoImagen.set(imgNueva);

            ImageView imgMostrar;
            imgMostrar = new ImageView(imgNueva);

            imgMostrar.setFitHeight(250);
            imgMostrar.setSmooth(true);
            imgMostrar.setPreserveRatio(true);
            ImageView imgCategoria = imgMostrar;

            imgCategoria.imageProperty().bind(categoImagen);
            organizadorVertical.getChildren().add(imgCategoria);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VistaCategoCarrusel.class.getName()).log(Level.SEVERE, null, ex);
        }
        // *********************************************************************

        // Estado de la categoría
        categoEstado = new SimpleBooleanProperty(objCargado.isEstadoCategoria());
        tamanioFuente = 20;
        Label lblEstado = new Label();
        lblEstado.textProperty().bind(Bindings.when(categoEstado).then("Activo").otherwise("Inactivo"));
        lblEstado.setFont(Font.font("Verdana", FontWeight.BOLD, tamanioFuente));
        lblEstado.styleProperty().bind(
                categoEstado
                        .map(dato -> dato.equals(true) ? "-fx-text-fill: #6C3483;" : "-fx-text-fill: red;")
        );
        organizadorVertical.getChildren().add(lblEstado);
        // *********************************************************************

        // Cantidad de productos x categoría
        categoCantPro = new SimpleIntegerProperty(objCargado.getCantidadProductoCategoria());

        Label lblCantProductos = new Label("Productos: " + objCargado.getCantidadProductoCategoria());
        lblCantProductos.textProperty().bind(Bindings.concat("Productos: ", categoCantPro.asString()));
        lblCantProductos.setFont(Font.font("Verdana", tamanioFuente));
        lblCantProductos.setTextFill(Color.web("#6C3483"));
        organizadorVertical.getChildren().add(lblCantProductos);
        // *********************************************************************

        miBorderPane.setCenter(centerPane);
    }

    private static Integer obtenerIndice(String opcion, int indice, int numCarros) {
        Integer nuevoIndice, limite;

        nuevoIndice = indice;
        limite = numCarros - 1;
        switch (opcion.toLowerCase()) {
            case "anterior" -> {
                if (indice == 0) {
                    nuevoIndice = limite;
                } else {
                    nuevoIndice = indice - 1;
                }
            }
            case "siguiente" -> {
                if (indice == limite) {
                    nuevoIndice = 0;
                } else {
                    nuevoIndice = indice + 1;
                }
            }
        }
        return nuevoIndice;
    }
}
