package org.poo.vista.empresa;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.poo.controlador.empresa.EmpresaControladorEditar;
import org.poo.controlador.empresa.EmpresaControladorVentana;
import org.poo.controlador.terminal.TerminalControladorListar;
import org.poo.dto.EmpresaDto;
import org.poo.dto.TerminalDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.*;

import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Cursor;

public class VistaEmpresaEditar extends StackPane {

    private static final int H_GAP = 10;
    private static final int V_GAP = 20;
    private static final int ALTO_FILA = 33;
    private static final int ALTO_CAJA = 33;
    private static final int TAMANIO_FUENTE = 20;
    private static final double AJUSTE_TITULO = 0.1;

    private final GridPane miGrilla;
    private final StackPane miFormulario;
    private final Rectangle miMarco;
    private final Stage miEscenario;

    private TextField txtNombreEmpresa;
    private TextField txtNitEmpresa;
    private ComboBox<TerminalDto> cmbTerminalEmpresa;
    private ComboBox<String> cmbEstadoEmpresa;
    private DatePicker dateFechaFundacion;
    private Spinner<Integer> spinnerEmpleados;
    private CheckBox chk24Horas;
    private CheckBox chkMantenimiento;
    private CheckBox chkServicioCliente;
    private TextArea txtDescripcion;
    
    private TextField txtImagen;
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private String rutaImagenSeleccionada;

    private final int posicion;
    private final EmpresaDto objEmpresa;
    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;
    private final boolean origenCarrusel;

    public VistaEmpresaEditar(Stage ventanaPadre, BorderPane princ, Pane pane,
            double ancho, double alto, EmpresaDto objEmpresaExterno, int posicionArchivo, boolean origenCarrusel) {

        miEscenario = ventanaPadre;
        miFormulario = this;
        miFormulario.setAlignment(Pos.CENTER);

        posicion = posicionArchivo;
        objEmpresa = objEmpresaExterno;
        panelPrincipal = princ;
        panelCuerpo = pane;
        this.origenCarrusel = origenCarrusel;
        rutaImagenSeleccionada = "";

        miGrilla = new GridPane();
        miMarco = Marco.crear(
                miEscenario,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_RUTA,
                Configuracion.DEGRADE_BORDE
        );

        getChildren().add(miMarco);

        configurarGrilla(ancho, alto);
        crearTitulo();
        crearFormulario();
        colocarFrmElegante();
        getChildren().add(miGrilla);
    }

    public StackPane getMiFormulario() {
        return miFormulario;
    }

    private void configurarGrilla(double ancho, double alto) {
        double miAnchoGrilla = ancho * Configuracion.GRILLA_ANCHO_PORCENTAJE;

        miGrilla.setHgap(H_GAP);
        miGrilla.setVgap(V_GAP);
        miGrilla.maxWidthProperty().bind(widthProperty().multiply(0.70));
        miGrilla.maxHeightProperty().bind(heightProperty().multiply(0.80));
        miGrilla.setAlignment(Pos.CENTER);


        ColumnConstraints col0 = new ColumnConstraints();
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        
        col0.setPercentWidth(30);  // etiqueta
        col1.setPercentWidth(45);  // campo de texto
        col2.setPercentWidth(30);  // imagen
        col1.setHgrow(Priority.ALWAYS);

        miGrilla.getColumnConstraints().addAll(col0, col1, col2);

        for (int i = 0; i < 12; i++) {
            RowConstraints fila = new RowConstraints();
            fila.setMinHeight(ALTO_FILA);
            fila.setMaxHeight(ALTO_FILA);
            fila.setVgrow(Priority.ALWAYS);
            miGrilla.getRowConstraints().add(fila);
        }
    }

    private void crearTitulo() {
        Text miTitulo = new Text("FORMULARIO - EDITAR EMPRESA");
        miTitulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        miTitulo.setFont(Font.font("Rockwell", FontWeight.BOLD, 26));
        GridPane.setHalignment(miTitulo, HPos.CENTER);
        GridPane.setMargin(miTitulo, new Insets(30, 0, 0, 0));
        miGrilla.add(miTitulo, 0, 0, 3, 2);//nombre, columna, fila, union de filas 
    }

    private void crearFormulario() {

        Label lblNombre = new Label("Nombre Empresa:");
        lblNombre.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblNombre, 0, 2);

        txtNombreEmpresa = new TextField();
        txtNombreEmpresa.setText(objEmpresa.getNombreEmpresa());
        txtNombreEmpresa.setPrefHeight(ALTO_CAJA);
        txtNombreEmpresa.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        txtNombreEmpresa.setMinHeight(Region.USE_PREF_SIZE);
        GridPane.setHgrow(txtNombreEmpresa, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtNombreEmpresa, 50);
        miGrilla.add(txtNombreEmpresa, 1, 2);

        Label lblNit = new Label("NIT:");
        lblNit.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblNit, 0, 3);

        txtNitEmpresa = new TextField();
        txtNitEmpresa.setText(objEmpresa.getNitEmpresa());
        txtNitEmpresa.setPrefHeight(ALTO_CAJA);
        txtNitEmpresa.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        txtNitEmpresa.setMinHeight(Region.USE_PREF_SIZE);
        GridPane.setHgrow(txtNitEmpresa, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtNitEmpresa, 20);
        miGrilla.add(txtNitEmpresa, 1, 3);

        Label lblTerminal = new Label("Terminal:");
        lblTerminal.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblTerminal, 0, 4);

        cmbTerminalEmpresa = new ComboBox<>();
        cmbTerminalEmpresa.setMaxWidth(Double.MAX_VALUE);
        cmbTerminalEmpresa.setPrefHeight(ALTO_CAJA);
        cmbTerminalEmpresa.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        cmbTerminalEmpresa.setMinHeight(Region.USE_PREF_SIZE);
        
        List<TerminalDto> terminales = TerminalControladorListar.obtenerTerminalesActivos();
        TerminalDto opcionDefault = new TerminalDto();
        opcionDefault.setIdTerminal(0);
        opcionDefault.setNombreTerminal("Seleccione terminal");
        
        cmbTerminalEmpresa.getItems().add(opcionDefault);
        cmbTerminalEmpresa.getItems().addAll(terminales);
        
        int indiceSeleccionado = 0;
        for (int i = 0; i < cmbTerminalEmpresa.getItems().size(); i++) {
            if (cmbTerminalEmpresa.getItems().get(i).getIdTerminal().equals(
                    objEmpresa.getTerminalEmpresa().getIdTerminal())) {
                indiceSeleccionado = i;
                break;
            }
        }
        cmbTerminalEmpresa.getSelectionModel().select(indiceSeleccionado);
        
        cmbTerminalEmpresa.setConverter(new StringConverter<TerminalDto>() {
            @Override
            public String toString(TerminalDto terminal) {
                return terminal != null ? terminal.getNombreTerminal() : "";
            }

            @Override
            public TerminalDto fromString(String string) {
                return null;
            }
        });
        
        miGrilla.add(cmbTerminalEmpresa, 1, 4);

        Label lblEstado = new Label("Estado:");
        lblEstado.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEstado, 0, 5);

        cmbEstadoEmpresa = new ComboBox<>();
        cmbEstadoEmpresa.setMaxWidth(Double.MAX_VALUE);
        cmbEstadoEmpresa.setPrefHeight(ALTO_CAJA);
        cmbEstadoEmpresa.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        cmbEstadoEmpresa.setMinHeight(Region.USE_PREF_SIZE);
        cmbEstadoEmpresa.getItems().addAll("Seleccione estado", "Activo", "Inactivo");
        
        if (objEmpresa.getEstadoEmpresa()) {
            cmbEstadoEmpresa.getSelectionModel().select(1);
        } else {
            cmbEstadoEmpresa.getSelectionModel().select(2);
        }
        miGrilla.add(cmbEstadoEmpresa, 1, 5);

        Label lblFechaFundacion = new Label("Fecha de Fundación:");
        lblFechaFundacion.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblFechaFundacion, 0, 6);

        dateFechaFundacion = new DatePicker();
        dateFechaFundacion.setMaxWidth(Double.MAX_VALUE);
        dateFechaFundacion.setPrefHeight(ALTO_CAJA);
        dateFechaFundacion.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        dateFechaFundacion.setMinHeight(Region.USE_PREF_SIZE);
        dateFechaFundacion.setValue(objEmpresa.getFechaFundacion());
        Formulario.deshabilitarFechasFuturas(dateFechaFundacion);
        miGrilla.add(dateFechaFundacion, 1, 6);

        Label lblEmpleados = new Label("Cantidad de Empleados:");
        lblEmpleados.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEmpleados, 0, 7);

        spinnerEmpleados = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 
                objEmpresa.getCantidadEmpleados() != null ? objEmpresa.getCantidadEmpleados() : 50);
        spinnerEmpleados.setValueFactory(valueFactory);
        spinnerEmpleados.setPrefHeight(ALTO_CAJA);
        spinnerEmpleados.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        spinnerEmpleados.setMinHeight(Region.USE_PREF_SIZE);
        spinnerEmpleados.setEditable(true);
        miGrilla.add(spinnerEmpleados, 1, 7);

        Label lblServicios = new Label("Servicios de la Empresa:");
        lblServicios.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblServicios, 0, 8);

        chk24Horas = new CheckBox("Servicio 24 Horas");
        chkMantenimiento = new CheckBox("Mantenimiento Propio");
        chkServicioCliente = new CheckBox("Servicio al Cliente");
        
        chk24Horas.setSelected(objEmpresa.getServicio24Horas() != null ? objEmpresa.getServicio24Horas() : false);
        chkMantenimiento.setSelected(objEmpresa.getTieneMantenimientoPropio() != null ? objEmpresa.getTieneMantenimientoPropio() : false);
        chkServicioCliente.setSelected(objEmpresa.getTieneServicioCliente() != null ? objEmpresa.getTieneServicioCliente() : false);
        
        chk24Horas.setFont(Font.font("Times new roman", 12));
        chkMantenimiento.setFont(Font.font("Times new roman", 12));
        chkServicioCliente.setFont(Font.font("Times new roman", 12));

        VBox vboxServicios = new VBox(5);
        vboxServicios.getChildren().addAll(chk24Horas, chkMantenimiento, chkServicioCliente);
        miGrilla.add(vboxServicios, 1, 8);

        Label lblDescripcion = new Label("Descripción:");
        lblDescripcion.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDescripcion, 0, 9);

        txtDescripcion = new TextArea();
        txtDescripcion.setText(objEmpresa.getDescripcionEmpresa());
        txtDescripcion.setPrefRowCount(3);
        txtDescripcion.setWrapText(true);
        txtDescripcion.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        txtDescripcion.setMaxHeight(Region.USE_PREF_SIZE);
        miGrilla.add(txtDescripcion, 1, 9);

        Label lblImagen = new Label("Imagen:");
        lblImagen.setFont(Font.font("Times new roman", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblImagen, 0, 10);

        txtImagen = new TextField();
        txtImagen.setText(objEmpresa.getNombreImagenPublicoEmpresa());
        txtImagen.setDisable(true);
        txtImagen.setPrefHeight(ALTO_CAJA);
        txtImagen.maxWidthProperty().bind(miGrilla.widthProperty().multiply(0.45));
        txtImagen.setMinHeight(Region.USE_PREF_SIZE);

        String[] extensiones = {"*.png", "*.jpg", "*.jpeg"};
        FileChooser selector = Formulario.selectorImagen(
                "Seleccionar imagen", "Imágenes", extensiones);

        Button btnSeleccionarImagen = new Button("+");
        btnSeleccionarImagen.setPrefHeight(ALTO_CAJA);
        
        btnSeleccionarImagen.setOnAction(e -> {
            rutaImagenSeleccionada = GestorImagen.obtenerRutaImagen(txtImagen, selector);
            
            if (!rutaImagenSeleccionada.isEmpty()) {
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                
                imgPrevisualizar = Icono.previsualizar(rutaImagenSeleccionada, 150);
                GridPane.setHalignment(imgPrevisualizar, HPos.CENTER);
                miGrilla.add(imgPrevisualizar, 2, 5);
            }
        });

        HBox.setHgrow(txtImagen, Priority.ALWAYS);
        HBox panelImagen = new HBox(5, txtImagen, btnSeleccionarImagen);
        miGrilla.add(panelImagen, 1, 10);

        imgPorDefecto = Icono.obtenerFotosExternas(
                objEmpresa.getNombreImagenPrivadoEmpresa(), 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 5);

        Button btnActualizar = new Button("ACTUALIZAR");
        btnActualizar.setPrefHeight(ALTO_CAJA);
        btnActualizar.setMaxWidth(Double.MAX_VALUE);
        btnActualizar.setTextFill(Color.web("#FFFFFF"));
        btnActualizar.setFont(Font.font("Rockwell", FontWeight.BOLD, 14));
        btnActualizar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + ";");
        btnActualizar.setCursor(Cursor.HAND);
        btnActualizar.setOnAction(e -> actualizarEmpresa());
        miGrilla.add(btnActualizar, 1, 11);

        Button btnRegresar = new Button("VOLVER");
        btnRegresar.setPrefHeight(ALTO_CAJA);
        btnRegresar.setMaxWidth(Double.MAX_VALUE);
        btnRegresar.setFont(Font.font("Rockwell", FontWeight.BOLD, 14));
        btnRegresar.setCursor(Cursor.HAND);
        btnRegresar.setOnAction(e -> {
            if (origenCarrusel) {
                panelCuerpo = EmpresaControladorVentana.carrusel(
                        miEscenario, panelPrincipal, panelCuerpo,
                        Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO, posicion);
            } else {
                panelCuerpo = EmpresaControladorVentana.administrar(
                        miEscenario, panelPrincipal, panelCuerpo,
                        Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO);
            }
            panelPrincipal.setCenter(null);
            panelPrincipal.setCenter(panelCuerpo);
        });
        miGrilla.add(btnRegresar, 1, 12);
    }

    private Boolean formularioCompleto() {
        if (txtNombreEmpresa.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar el nombre");
            txtNombreEmpresa.requestFocus();
            return false;
        }

        if (txtNitEmpresa.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar el NIT");
            txtNitEmpresa.requestFocus();
            return false;
        }

        if (cmbTerminalEmpresa.getSelectionModel().getSelectedIndex() == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Terminal no seleccionada", "Debe seleccionar una terminal");
            return false;
        }

        if (cmbEstadoEmpresa.getSelectionModel().getSelectedIndex() == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Estado no seleccionado", "Debe seleccionar un estado");
            return false;
        }

        if (dateFechaFundacion.getValue() == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Fecha no seleccionada", "Debe seleccionar la fecha de fundación");
            return false;
        }

        if (txtDescripcion.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar una descripción");
            txtDescripcion.requestFocus();
            return false;
        }

        return true;
    }

    private void actualizarEmpresa() {
        if (formularioCompleto()) {
            EmpresaDto dtoActualizado = new EmpresaDto();
            dtoActualizado.setIdEmpresa(objEmpresa.getIdEmpresa());
            dtoActualizado.setNombreEmpresa(txtNombreEmpresa.getText());
            dtoActualizado.setNitEmpresa(txtNitEmpresa.getText());
            dtoActualizado.setTerminalEmpresa(cmbTerminalEmpresa.getValue());
            dtoActualizado.setEstadoEmpresa(cmbEstadoEmpresa.getValue().equals("Activo"));
            dtoActualizado.setNombreImagenPublicoEmpresa(txtImagen.getText());
            dtoActualizado.setNombreImagenPrivadoEmpresa(objEmpresa.getNombreImagenPrivadoEmpresa());
            dtoActualizado.setCantidadBusesEmpresa(objEmpresa.getCantidadBusesEmpresa());
            dtoActualizado.setFechaFundacion(dateFechaFundacion.getValue());
            dtoActualizado.setCantidadEmpleados(spinnerEmpleados.getValue());
            dtoActualizado.setServicio24Horas(chk24Horas.isSelected());
            dtoActualizado.setTieneMantenimientoPropio(chkMantenimiento.isSelected());
            dtoActualizado.setTieneServicioCliente(chkServicioCliente.isSelected());
            dtoActualizado.setDescripcionEmpresa(txtDescripcion.getText());

            if (EmpresaControladorEditar.actualizar(posicion, dtoActualizado, rutaImagenSeleccionada)) {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, this.getScene().getWindow(),
                        "Éxito", "Empresa actualizada correctamente");
            } else {
                Mensaje.mostrar(Alert.AlertType.ERROR, this.getScene().getWindow(),
                        "Error", "No se pudo actualizar la empresa");
            }
        }
    }

    private void colocarFrmElegante() {
        Runnable calcular = () -> {
            double alturaMarco = miMarco.getHeight();
            if (alturaMarco > 0) {
                double desplazamiento = alturaMarco * AJUSTE_TITULO;
                miGrilla.setTranslateY(-alturaMarco / 8 + desplazamiento);
            }
        };
        calcular.run();
        miMarco.heightProperty().addListener((obs, antes, despues) -> calcular.run());
    }
}
