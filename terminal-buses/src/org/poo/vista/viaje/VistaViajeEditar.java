package org.poo.vista.viaje;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.poo.controlador.viaje.ViajeControladorEditar;
import org.poo.controlador.viaje.ViajeControladorVentana;
import org.poo.dto.ViajeDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.*;

public class VistaViajeEditar extends StackPane {

    private static final int H_GAP = 10;
    private static final int V_GAP = 20;
    private static final int ALTO_CAJA = 35;
    private static final int TAMANIO_FUENTE = 18;

    private final GridPane miGrilla;
    private final StackPane miFormulario;
    private final Rectangle miMarco;
    private final Stage miEscenario;

    private TextField txtFechaSalida;
    private TextField txtHoraSalida;
    private ComboBox<String> cmbRuta;
    private ComboBox<String> cmbConductor;
    private ComboBox<String> cmbBus;
    private Spinner<Integer> spinnerAsientosDisponibles;
    private ComboBox<String> cmbEstadoViaje;
    private TextField txtPrecio;

    private final int posicion;
    private final ViajeDto objViaje;
    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;
    private final boolean desdeCarrusel;

    public VistaViajeEditar(Stage ventanaPadre, BorderPane princ, Pane pane,
            double ancho, double alto, ViajeDto objViajeExterno, int posicionArchivo,
            boolean vieneDeCarrusel) {

        miEscenario = ventanaPadre;
        miFormulario = this;
        miFormulario.setAlignment(Pos.CENTER);

        posicion = posicionArchivo;
        objViaje = objViajeExterno;
        panelPrincipal = princ;
        panelCuerpo = pane;
        desdeCarrusel = vieneDeCarrusel;

        miGrilla = new GridPane();
        miMarco = Marco.crear(
                miEscenario,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_VIAJE,
                Configuracion.DEGRADE_BORDE
        );

        getChildren().add(miMarco);

        configurarGrilla(ancho, alto);
        crearTitulo();
        crearFormulario();
        getChildren().add(miGrilla);
    }

    public StackPane getMiFormulario() {
        return miFormulario;
    }

    private void configurarGrilla(double ancho, double alto) {
        double anchoGrilla = ancho * Configuracion.GRILLA_ANCHO_PORCENTAJE;

        miGrilla.setHgap(H_GAP);
        miGrilla.setVgap(V_GAP);
        miGrilla.setAlignment(Pos.TOP_CENTER);
        miGrilla.setPrefSize(anchoGrilla, alto);
        miGrilla.setMinSize(anchoGrilla, alto);
        miGrilla.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        ColumnConstraints col0 = new ColumnConstraints();
        col0.setPercentWidth(40);
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(60);
        col1.setHgrow(Priority.ALWAYS);
        
        miGrilla.getColumnConstraints().addAll(col0, col1);
    }

    private void crearTitulo() {
        int columna = 0, fila = 0, colSpan = 2, rowSpan = 1;

        Region espacioSuperior = new Region();
        espacioSuperior.prefHeightProperty().bind(miEscenario.heightProperty().multiply(0.05));
        miGrilla.add(espacioSuperior, columna, fila, colSpan, rowSpan);

        fila = 1;
        Text titulo = new Text("Formulario Actualización de Viaje");
        titulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        GridPane.setHalignment(titulo, HPos.CENTER);
        miGrilla.add(titulo, columna, fila, colSpan, rowSpan);
    }

    private void crearFormulario() {
        int fila = 2;
        int primeraColumna = 0;
        int segundaColumna = 1;

        // FECHA SALIDA
        fila++;
        Label lblFechaSalida = new Label("Fecha Salida:");
        lblFechaSalida.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblFechaSalida, primeraColumna, fila);

        txtFechaSalida = new TextField();
        txtFechaSalida.setText(objViaje.getFechaSalida() != null ? objViaje.getFechaSalida() : "");
        txtFechaSalida.setPrefHeight(ALTO_CAJA);
        txtFechaSalida.setPromptText("yyyy-MM-dd");
        GridPane.setHgrow(txtFechaSalida, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtFechaSalida, 10);
        miGrilla.add(txtFechaSalida, segundaColumna, fila);

        // HORA SALIDA
        fila++;
        Label lblHoraSalida = new Label("Hora Salida:");
        lblHoraSalida.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblHoraSalida, primeraColumna, fila);

        txtHoraSalida = new TextField();
        txtHoraSalida.setText(objViaje.getHoraSalidaViaje()!= null ? objViaje.getHoraSalidaViaje(): "");
        txtHoraSalida.setPrefHeight(ALTO_CAJA);
        txtHoraSalida.setPromptText("HH:mm");
        GridPane.setHgrow(txtHoraSalida, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtHoraSalida, 5);
        miGrilla.add(txtHoraSalida, segundaColumna, fila);

        // RUTA
        fila++;
        Label lblRuta = new Label("Ruta:");
        lblRuta.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblRuta, primeraColumna, fila);

        cmbRuta = new ComboBox<>();
        cmbRuta.setMaxWidth(Double.MAX_VALUE);
        cmbRuta.setPrefHeight(ALTO_CAJA);
        cmbRuta.getItems().addAll("Seleccione ruta", "Ruta 1", "Ruta 2", "Ruta 3");
        cmbRuta.getSelectionModel().select(0);
        miGrilla.add(cmbRuta, segundaColumna, fila);

        // CONDUCTOR
        fila++;
        Label lblConductor = new Label("Conductor:");
        lblConductor.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblConductor, primeraColumna, fila);

        cmbConductor = new ComboBox<>();
        cmbConductor.setMaxWidth(Double.MAX_VALUE);
        cmbConductor.setPrefHeight(ALTO_CAJA);
        cmbConductor.getItems().addAll("Seleccione conductor", "Conductor 1", "Conductor 2", "Conductor 3");
        cmbConductor.getSelectionModel().select(0);
        miGrilla.add(cmbConductor, segundaColumna, fila);

        // BUS
        fila++;
        Label lblBus = new Label("Bus:");
        lblBus.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblBus, primeraColumna, fila);

        cmbBus = new ComboBox<>();
        cmbBus.setMaxWidth(Double.MAX_VALUE);
        cmbBus.setPrefHeight(ALTO_CAJA);
        cmbBus.getItems().addAll("Seleccione bus", "Bus 1", "Bus 2", "Bus 3");
        cmbBus.getSelectionModel().select(0);
        miGrilla.add(cmbBus, segundaColumna, fila);

        // ASIENTOS DISPONIBLES
        fila++;
        Label lblAsientos = new Label("Asientos Disponibles:");
        lblAsientos.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblAsientos, primeraColumna, fila);

        spinnerAsientosDisponibles = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 
                objViaje.getAsientosDisponiblesViaje()!= null ? objViaje.getAsientosDisponiblesViaje(): 50);
        spinnerAsientosDisponibles.setValueFactory(valueFactory);
        spinnerAsientosDisponibles.setPrefHeight(ALTO_CAJA);
        spinnerAsientosDisponibles.setMaxWidth(Double.MAX_VALUE);
        spinnerAsientosDisponibles.setEditable(true);
        miGrilla.add(spinnerAsientosDisponibles, segundaColumna, fila);

        // PRECIO
        fila++;
        Label lblPrecio = new Label("Precio:");
        lblPrecio.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblPrecio, primeraColumna, fila);

        txtPrecio = new TextField();
        txtPrecio.setText(objViaje.getPrecioViaje()!= null ? objViaje.getPrecioViaje().toString() : "");
        txtPrecio.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtPrecio, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtPrecio, 10);
        Formulario.soloNumeros(txtPrecio);
        miGrilla.add(txtPrecio, segundaColumna, fila);

        // ESTADO
        fila++;
        Label lblEstado = new Label("Estado:");
        lblEstado.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEstado, primeraColumna, fila);

        cmbEstadoViaje = new ComboBox<>();
        cmbEstadoViaje.setMaxWidth(Double.MAX_VALUE);
        cmbEstadoViaje.setPrefHeight(ALTO_CAJA);
        cmbEstadoViaje.getItems().addAll("Seleccione estado", "Activo", "Cancelado", "Finalizado");
        
        if (objViaje.getEstadoViaje() != null) {
            cmbEstadoViaje.getSelectionModel().select(1);
        } else {
            cmbEstadoViaje.getSelectionModel().select(0);
        }
        miGrilla.add(cmbEstadoViaje, segundaColumna, fila);

        fila++;

        fila++;
        Button btnActualizar = new Button("Actualizar Viaje");
        btnActualizar.setPrefHeight(ALTO_CAJA);
        btnActualizar.setMaxWidth(Double.MAX_VALUE);
        btnActualizar.setTextFill(Color.web("#FFFFFF"));
        btnActualizar.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        btnActualizar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + ";");
        btnActualizar.setOnAction(e -> actualizarViaje());
        miGrilla.add(btnActualizar, segundaColumna, fila);

        fila++;
        Button btnRegresar = new Button("Regresar");
        btnRegresar.setPrefHeight(ALTO_CAJA);
        btnRegresar.setMaxWidth(Double.MAX_VALUE);
        btnRegresar.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        btnRegresar.setOnAction(e -> {
            if (desdeCarrusel) {
                panelCuerpo = ViajeControladorVentana.carrusel(
                        miEscenario, panelPrincipal, panelCuerpo,
                        Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO, posicion);
            } else {
                panelCuerpo = ViajeControladorVentana.administrar(
                        miEscenario, panelPrincipal, panelCuerpo,
                        Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO);
            }
            panelPrincipal.setCenter(null);
            panelPrincipal.setCenter(panelCuerpo);
        });
        miGrilla.add(btnRegresar, segundaColumna, fila);
    }

    private Boolean formularioCompleto() {
        if (txtFechaSalida.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar la fecha de salida");
            txtFechaSalida.requestFocus();
            return false;
        }

        if (txtHoraSalida.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar la hora de salida");
            txtHoraSalida.requestFocus();
            return false;
        }

        if (cmbRuta.getSelectionModel().getSelectedIndex() == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Ruta no seleccionada", "Debe seleccionar una ruta");
            return false;
        }

        if (cmbConductor.getSelectionModel().getSelectedIndex() == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Conductor no seleccionado", "Debe seleccionar un conductor");
            return false;
        }

        if (cmbBus.getSelectionModel().getSelectedIndex() == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Bus no seleccionado", "Debe seleccionar un bus");
            return false;
        }

        if (txtPrecio.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar el precio");
            txtPrecio.requestFocus();
            return false;
        }

        if (cmbEstadoViaje.getSelectionModel().getSelectedIndex() == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Estado no seleccionado", "Debe seleccionar un estado");
            return false;
        }

        return true;
    }

    private void actualizarViaje() {
        if (formularioCompleto()) {
            ViajeDto dtoActualizado = new ViajeDto();
            dtoActualizado.setIdViaje(objViaje.getIdViaje());
            dtoActualizado.setFechaViaje(txtFechaSalida.getText());
            dtoActualizado.setHoraSalidaViaje(txtHoraSalida.getText());
            dtoActualizado.setAsientosDisponiblesViaje(spinnerAsientosDisponibles.getValue());
            dtoActualizado.setPrecioViaje(Double.parseDouble(txtPrecio.getText()));
            dtoActualizado.setEstadoViaje(cmbEstadoViaje.getValue().equals("Activo"));

            if (ViajeControladorEditar.actualizar(posicion, dtoActualizado)) {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, this.getScene().getWindow(),
                        "Éxito", "Viaje actualizado correctamente");
            } else {
                Mensaje.mostrar(Alert.AlertType.ERROR, this.getScene().getWindow(),
                        "Error", "No se pudo actualizar el viaje");
            }
        }
    }
}