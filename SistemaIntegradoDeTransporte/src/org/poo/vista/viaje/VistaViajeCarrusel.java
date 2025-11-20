package org.poo.vista.viaje;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.poo.controlador.viaje.ViajeControladorEliminar;
import org.poo.controlador.viaje.ViajeControladorListar;
import org.poo.controlador.viaje.ViajeControladorUna;
import org.poo.controlador.viaje.ViajeControladorVentana;
import org.poo.dto.ViajeDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.constante.Persistencia;
import org.poo.recurso.utilidad.Icono;
import org.poo.recurso.utilidad.Marco;
import org.poo.recurso.utilidad.Mensaje;

public class VistaViajeCarrusel extends BorderPane {

    private final Stage miEscenario;
    private final BorderPane panelPrincipal;
    private Pane panelCuerpo;
    private final VBox organizadorVertical;

    private static int indiceActualEstatico = 0;
    private int indiceActual;
    private int totalViajes;
    private ViajeDto objCargado;

    private StringProperty viajeTitulo;
    private StringProperty viajeFecha;
    private StringProperty viajeRuta;
    private StringProperty viajeBus;
    private StringProperty viajeConductor;
    private StringProperty viajeHorario;
    private StringProperty viajePrecio;
    private ObjectProperty<Image> viajeImagen;
    private BooleanProperty viajeEstado;
    private IntegerProperty viajeAsientos;

    public VistaViajeCarrusel(Stage ventanaPadre, BorderPane princ, Pane pane,
            double anchoPanel, double altoPanel, int indice) {

        miEscenario = ventanaPadre;
        panelPrincipal = princ;
        panelCuerpo = pane;
        
        if (indice == 0 && indiceActualEstatico > 0) {
            indiceActual = indiceActualEstatico;
        } else {
            indiceActual = indice;
            indiceActualEstatico = indice;
        }

        organizadorVertical = new VBox();

        totalViajes = ViajeControladorListar.obtenerCantidadViajes();
        
        if (totalViajes == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, 
                    miEscenario, "Sin viajes", 
                    "No hay viajes registrados para mostrar en el carrusel");
            return;
        }
        
        if (indiceActual >= totalViajes) {
            indiceActual = 0;
            indiceActualEstatico = 0;
        }
        
        objCargado = ViajeControladorUna.obtenerViaje(indiceActual);

        configurarOrganizadorVertical();
        crearTitulo();
        construirPanelIzquierdo(0.08);
        construirPanelDerecho(0.08);
        construirPanelCentro();
    }

    private void configurarOrganizadorVertical() {
        organizadorVertical.setSpacing(15);
        organizadorVertical.setAlignment(Pos.TOP_CENTER);
        organizadorVertical.prefWidthProperty().bind(miEscenario.widthProperty());
        organizadorVertical.prefHeightProperty().bind(miEscenario.heightProperty());
    }

    private void crearTitulo() {
        Region bloqueSeparador = new Region();
        bloqueSeparador.prefHeightProperty().bind(miEscenario.heightProperty().multiply(0.08));
        organizadorVertical.getChildren().add(0, bloqueSeparador);

        viajeTitulo = new SimpleStringProperty(
                "Detalle del Viaje (" + (indiceActual + 1) + " / " + totalViajes + ")");

        Label lblTitulo = new Label();
        lblTitulo.textProperty().bind(viajeTitulo);
        lblTitulo.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        organizadorVertical.getChildren().add(lblTitulo);
    }

    private void construirPanelIzquierdo(double porcentaje) {
        Button btnAnterior = new Button();
        btnAnterior.setGraphic(Icono.obtenerIcono("btnAtras.png", 60));
        btnAnterior.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        btnAnterior.setCursor(Cursor.HAND);

        btnAnterior.setOnAction(e -> {
            indiceActual = obtenerIndice("Anterior", indiceActual, totalViajes);
            indiceActualEstatico = indiceActual;
            actualizarContenido();
        });

        StackPane panelIzquierdo = new StackPane();
        panelIzquierdo.prefWidthProperty().bind(miEscenario.widthProperty().multiply(porcentaje));
        panelIzquierdo.getChildren().add(btnAnterior);
        setLeft(panelIzquierdo);
    }

    private void construirPanelDerecho(double porcentaje) {
        Button btnSiguiente = new Button();
        btnSiguiente.setGraphic(Icono.obtenerIcono("btnSiguiente.png", 60));
        btnSiguiente.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        btnSiguiente.setCursor(Cursor.HAND);

        btnSiguiente.setOnAction(e -> {
            indiceActual = obtenerIndice("Siguiente", indiceActual, totalViajes);
            indiceActualEstatico = indiceActual;
            actualizarContenido();
        });

        StackPane panelDerecho = new StackPane();
        panelDerecho.prefWidthProperty().bind(miEscenario.widthProperty().multiply(porcentaje));
        panelDerecho.getChildren().add(btnSiguiente);
        setRight(panelDerecho);
    }

    private void construirPanelCentro() {
        StackPane centerPane = new StackPane();

        Rectangle miMarco = Marco.crear(miEscenario, 0.70, 0.80,
                Configuracion.DEGRADE_ARREGLO_VIAJE,
                Configuracion.DEGRADE_BORDE);
        centerPane.getChildren().addAll(miMarco, organizadorVertical);

        panelOpciones();
        mostrarDatos();

        setCenter(centerPane);
    }

    private void panelOpciones() {
        int anchoBoton = 40;
        int tamanioIcono = 18;

        Button btnEliminar = new Button();
        btnEliminar.setPrefWidth(anchoBoton);
        btnEliminar.setCursor(Cursor.HAND);
        btnEliminar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_BORRAR, tamanioIcono));

        btnEliminar.setOnAction(e -> {
            String mensaje = "¿Seguro que desea eliminar este viaje?\n\n"
                    + "Código: " + objCargado.getIdViaje() + "\n"
                    + "Fecha: " + objCargado.getFechaViaje() + "\n"
                    + "Ruta: " + objCargado.getRutaViaje() + "\n\n"
                    + "Esta acción es irreversible.";

            Alert msg = new Alert(Alert.AlertType.CONFIRMATION);
            msg.setTitle("Confirmar Eliminación");
            msg.setHeaderText(null);
            msg.setContentText(mensaje);
            msg.initOwner(miEscenario);

            if (msg.showAndWait().get() == ButtonType.OK) {
                // Liberar la imagen antes de eliminar
                viajeImagen.set(null);
                System.gc(); // Sugerir recolección de basura
                
                if (ViajeControladorEliminar.borrar(indiceActual)) {
                    totalViajes = ViajeControladorListar.obtenerCantidadViajes();
                    
                    if (totalViajes > 0) {
                        if (indiceActual >= totalViajes) {
                            indiceActual = totalViajes - 1;
                            indiceActualEstatico = indiceActual;
                        }
                        actualizarContenido();
                        Mensaje.mostrar(Alert.AlertType.INFORMATION,
                                miEscenario, "Éxito", "Viaje eliminado correctamente");
                    } else {
                        Mensaje.mostrar(Alert.AlertType.INFORMATION,
                                miEscenario, "Sin viajes", "No quedan viajes registrados");
                        indiceActualEstatico = 0;
                        panelCuerpo = ViajeControladorVentana.administrar(
                                miEscenario, panelPrincipal, panelCuerpo,
                                Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO);
                        panelPrincipal.setCenter(panelCuerpo);
                    }
                } else {
                    Mensaje.mostrar(Alert.AlertType.ERROR,
                            miEscenario, "Error", "No se pudo eliminar el viaje");
                }
            }
        });

        Button btnActualizar = new Button();
        btnActualizar.setPrefWidth(anchoBoton);
        btnActualizar.setCursor(Cursor.HAND);
        btnActualizar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_EDITAR, tamanioIcono));

        btnActualizar.setOnAction(e -> {
            panelCuerpo = ViajeControladorVentana.editar(
                    miEscenario,
                    panelPrincipal,
                    panelCuerpo,
                    Configuracion.ANCHO_APP,
                    Configuracion.ALTO_CUERPO,
                    objCargado,
                    indiceActual,
                    true);
                    
            panelPrincipal.setCenter(null);
            panelPrincipal.setCenter(panelCuerpo);
        });

        HBox panelBotones = new HBox(5);
        panelBotones.setAlignment(Pos.CENTER);
        panelBotones.getChildren().addAll(btnEliminar, btnActualizar);
        
        organizadorVertical.getChildren().add(panelBotones);
    }
    
    private void mostrarDatos() {
        int tamanioFuente = 20;

        // Fecha y Ruta (título principal)
        viajeFecha = new SimpleStringProperty(objCargado.getFechaViaje().toString());
        viajeRuta = new SimpleStringProperty(objCargado.getRutaViaje().toString());
        Label lblTituloViaje = new Label();
        lblTituloViaje.textProperty().bind(Bindings.concat(viajeFecha, " - ", viajeRuta));
        lblTituloViaje.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        lblTituloViaje.setTextFill(Color.web(Configuracion.AZUL_OSCURO));
        organizadorVertical.getChildren().add(lblTituloViaje);

        // Imagen
        viajeImagen = new SimpleObjectProperty<>();
        try {
            String rutaImagen = Persistencia.RUTA_IMAGENES + Persistencia.SEPARADOR_CARPETAS
                    + objCargado.getNombreImagenPrivadoViaje();
            FileInputStream imgArchivo = new FileInputStream(rutaImagen);
            Image imgNueva = new Image(imgArchivo);
            viajeImagen.set(imgNueva);

            ImageView imgMostrar = new ImageView(imgNueva);
            imgMostrar.setFitHeight(200);
            imgMostrar.setSmooth(true);
            imgMostrar.setPreserveRatio(true);
            imgMostrar.imageProperty().bind(viajeImagen);

            organizadorVertical.getChildren().add(imgMostrar);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VistaViajeCarrusel.class.getName()).log(Level.SEVERE, null, ex);
            ImageView imgDefault = Icono.obtenerIcono(Configuracion.ICONO_NO_DISPONIBLE, 200);
            organizadorVertical.getChildren().add(imgDefault);
        }

        // Bus
        viajeBus = new SimpleStringProperty(objCargado.getBusViaje().toString());
        Label lblBus = new Label();
        lblBus.textProperty().bind(Bindings.concat("🚌 Bus: ", viajeBus));
        lblBus.setFont(Font.font("Arial", tamanioFuente));
        lblBus.setTextFill(Color.web(Configuracion.AZUL_MEDIO));
        organizadorVertical.getChildren().add(lblBus);

        // Conductor
        viajeConductor = new SimpleStringProperty(objCargado.getConductorViaje().getNombreConductor());
        Label lblConductor = new Label();
        lblConductor.textProperty().bind(Bindings.concat("👨‍✈️ Conductor: ", viajeConductor));
        lblConductor.setFont(Font.font("Arial", tamanioFuente));
        lblConductor.setTextFill(Color.web(Configuracion.AZUL_MEDIO));
        organizadorVertical.getChildren().add(lblConductor);

        // Horario (Salida - Llegada)
        viajeHorario = new SimpleStringProperty(
            objCargado.getHoraSalidaViaje() + " → " + objCargado.getHoraLlegadaViaje()
        );
        Label lblHorario = new Label();
        lblHorario.textProperty().bind(Bindings.concat("🕐 Horario: ", viajeHorario));
        lblHorario.setFont(Font.font("Arial", tamanioFuente));
        lblHorario.setTextFill(Color.web(Configuracion.AZUL_MEDIO));
        organizadorVertical.getChildren().add(lblHorario);

        // Precio
        viajePrecio = new SimpleStringProperty(String.format("$%.2f", objCargado.getPrecioViaje()));
        Label lblPrecio = new Label();
        lblPrecio.textProperty().bind(Bindings.concat("💰 Precio: ", viajePrecio));
        lblPrecio.setFont(Font.font("Arial", tamanioFuente));
        lblPrecio.setTextFill(Color.web(Configuracion.AZUL_MEDIO));
        organizadorVertical.getChildren().add(lblPrecio);

        // Asientos Disponibles
        viajeAsientos = new SimpleIntegerProperty(objCargado.getAsientosDisponiblesViaje());
        Label lblAsientos = new Label();
        lblAsientos.textProperty().bind(Bindings.concat("💺 Asientos disponibles: ", viajeAsientos.asString()));
        lblAsientos.setFont(Font.font("Arial", tamanioFuente));
        lblAsientos.setTextFill(Color.web(Configuracion.AZUL_MEDIO));
        organizadorVertical.getChildren().add(lblAsientos);

        // Estado
        viajeEstado = new SimpleBooleanProperty(objCargado.getEstadoViaje());
        Label lblEstado = new Label();
        lblEstado.textProperty().bind(Bindings.when(viajeEstado).then("✅ ACTIVO").otherwise("❌ INACTIVO"));
        lblEstado.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        lblEstado.textFillProperty().bind(
                viajeEstado.map(dato -> dato ? Color.web(Configuracion.VERDE_EXITO) 
                        : Color.web(Configuracion.ROJO_ERROR))
        );
        organizadorVertical.getChildren().add(lblEstado);
    }

    private void actualizarContenido() {
        objCargado = ViajeControladorUna.obtenerViaje(indiceActual);

        viajeTitulo.set("Carrusel de Viajes (" + (indiceActual + 1) + " / " + totalViajes + ")");
        viajeFecha.set(objCargado.getFechaViaje().toString());
        viajeRuta.set(objCargado.getRutaViaje().toString());
        viajeBus.set(objCargado.getBusViaje().toString());
        viajeConductor.set(objCargado.getConductorViaje().getNombreConductor());
        viajeHorario.set(objCargado.getHoraSalidaViaje() + " → " + objCargado.getHoraLlegadaViaje());
        viajePrecio.set(String.format("$%.2f", objCargado.getPrecioViaje()));
        viajeAsientos.set(objCargado.getAsientosDisponiblesViaje());
        viajeEstado.set(objCargado.getEstadoViaje());

        // Actualizar imagen
        try {
            String rutaImagen = Persistencia.RUTA_IMAGENES + Persistencia.SEPARADOR_CARPETAS
                    + objCargado.getNombreImagenPrivadoViaje();
            FileInputStream imgArchivo = new FileInputStream(rutaImagen);
            Image imgNueva = new Image(imgArchivo);
            viajeImagen.set(imgNueva);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VistaViajeCarrusel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static Integer obtenerIndice(String opcion, int indice, int total) {
        Integer nuevoIndice = indice;
        Integer limite = total - 1;

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
    
    public static void resetearPosicion() {
        indiceActualEstatico = 0;
    }
}