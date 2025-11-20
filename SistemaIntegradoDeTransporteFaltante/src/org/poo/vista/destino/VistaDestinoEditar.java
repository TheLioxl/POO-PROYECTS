package org.poo.vista.destino;

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
import org.poo.controlador.destino.DestinoControladorEditar;
import org.poo.controlador.destino.DestinoControladorVentana;
import org.poo.dto.DestinoDto;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.*;

public class VistaDestinoEditar extends StackPane {

    private static final int H_GAP = 10;
    private static final int V_GAP = 20;
    private static final int ALTO_CAJA = 35;
    private static final int TAMANIO_FUENTE = 18;

    private final GridPane miGrilla;
    private final StackPane miFormulario;
    private final Rectangle miMarco;
    private final Stage miEscenario;

    private TextField txtNombreDestino;
    private TextField txtDepartamentoDestino;
    private TextArea txtDescripcionDestino;
    private ComboBox<String> cmbEstadoDestino;
    private TextField txtImagen;
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private String rutaImagenSeleccionada;

    private final int posicion;
    private final DestinoDto objDestino;
    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;
    private final boolean desdeCarrusel;

    public VistaDestinoEditar(Stage ventanaPadre, BorderPane princ, Pane pane,
            double ancho, double alto, DestinoDto objDestinoExterno, int posicionArchivo,
            boolean vieneDeCarrusel) {

        miEscenario = ventanaPadre;
        miFormulario = this;
        miFormulario.setAlignment(Pos.CENTER);

        posicion = posicionArchivo;
        objDestino = objDestinoExterno;
        panelPrincipal = princ;
        panelCuerpo = pane;
        rutaImagenSeleccionada = "";
        desdeCarrusel = vieneDeCarrusel;

        miGrilla = new GridPane();
        miMarco = Marco.crear(
                miEscenario,
                Configuracion.MARCO_ANCHO_PORCENTAJE,
                Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.DEGRADE_ARREGLO_DESTINO,
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
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        
        col0.setPrefWidth(200);
        col1.setPrefWidth(200);
        col2.setPrefWidth(200);
        
        col1.setHgrow(Priority.ALWAYS);
        miGrilla.getColumnConstraints().addAll(col0, col1, col2);
    }

    private void crearTitulo() {
        int columna = 0, fila = 0, colSpan = 3, rowSpan = 1;

        Region espacioSuperior = new Region();
        espacioSuperior.prefHeightProperty().bind(miEscenario.heightProperty().multiply(0.05));
        miGrilla.add(espacioSuperior, columna, fila, colSpan, rowSpan);

        fila = 1;
        Text titulo = new Text("Formulario Actualización de Destino");
        titulo.setFill(Color.web(Configuracion.AZUL_OSCURO));
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        GridPane.setHalignment(titulo, HPos.CENTER);
        miGrilla.add(titulo, columna, fila, colSpan, rowSpan);
    }

    private void crearFormulario() {
        int fila = 2;
        int primeraColumna = 0;
        int segundaColumna = 1;

        // NOMBRE
        fila++;
        Label lblNombre = new Label("Nombre del Destino:");
        lblNombre.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblNombre, primeraColumna, fila);

        txtNombreDestino = new TextField();
        txtNombreDestino.setText(objDestino.getNombreDestino());
        txtNombreDestino.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtNombreDestino, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtNombreDestino, 50);
        miGrilla.add(txtNombreDestino, segundaColumna, fila);

        // DEPARTAMENTO
        fila++;
        Label lblDepartamento = new Label("Departamento:");
        lblDepartamento.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDepartamento, primeraColumna, fila);

        txtDepartamentoDestino = new TextField();
        txtDepartamentoDestino.setText(objDestino.getDepartamentoDestino());
        txtDepartamentoDestino.setPrefHeight(ALTO_CAJA);
        GridPane.setHgrow(txtDepartamentoDestino, Priority.ALWAYS);
        Formulario.cantidadCaracteres(txtDepartamentoDestino, 50);
        miGrilla.add(txtDepartamentoDestino, segundaColumna, fila);

        // DESCRIPCIÓN
        fila++;
        Label lblDescripcion = new Label("Descripción:");
        lblDescripcion.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblDescripcion, primeraColumna, fila);

        txtDescripcionDestino = new TextArea();
        txtDescripcionDestino.setText(objDestino.getDescripcionDestino());
        txtDescripcionDestino.setPrefRowCount(3);
        txtDescripcionDestino.setWrapText(true);
        txtDescripcionDestino.setMaxWidth(Double.MAX_VALUE);
        miGrilla.add(txtDescripcionDestino, segundaColumna, fila);

        // ESTADO
        fila++;
        Label lblEstado = new Label("Estado:");
        lblEstado.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblEstado, primeraColumna, fila);

        cmbEstadoDestino = new ComboBox<>();
        cmbEstadoDestino.setMaxWidth(Double.MAX_VALUE);
        cmbEstadoDestino.setPrefHeight(ALTO_CAJA);
        cmbEstadoDestino.getItems().addAll("Seleccione estado", "Activo", "Inactivo");
        
        if (objDestino.getEstadoDestino()) {
            cmbEstadoDestino.getSelectionModel().select(1);
        } else {
            cmbEstadoDestino.getSelectionModel().select(2);
        }
        miGrilla.add(cmbEstadoDestino, segundaColumna, fila);

        // IMAGEN
        fila++;
        Label lblImagen = new Label("Imagen:");
        lblImagen.setFont(Font.font("Arial", FontWeight.NORMAL, TAMANIO_FUENTE));
        miGrilla.add(lblImagen, primeraColumna, fila);

        txtImagen = new TextField();
        txtImagen.setText(objDestino.getNombreImagenPublicoDestino());
        txtImagen.setDisable(true);
        txtImagen.setPrefHeight(ALTO_CAJA);

        String[] extensiones = {"*.png", "*.jpg", "*.jpeg"};
        FileChooser selector = Formulario.selectorImagen(
                "Seleccionar imagen", "Imágenes", extensiones);

        Button btnSeleccionarImagen = new Button("+");
        btnSeleccionarImagen.setPrefHeight(ALTO_CAJA);
        btnSeleccionarImagen.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        btnSeleccionarImagen.setOnAction(e -> {
            rutaImagenSeleccionada = GestorImagen.obtenerRutaImagen(txtImagen, selector);
            
            if (rutaImagenSeleccionada.isEmpty()) {
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                imgPorDefecto = Icono.obtenerFotosExternas(objDestino.getNombreImagenPrivadoDestino(), 150);
                GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
                miGrilla.add(imgPorDefecto, 2, 1, 1, 7);
            } else {
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                imgPrevisualizar = Icono.previsualizar(rutaImagenSeleccionada, 150);
                GridPane.setHalignment(imgPrevisualizar, HPos.CENTER);
                miGrilla.add(imgPrevisualizar, 2, 1, 1, 7);
            }
        });

        HBox.setHgrow(txtImagen, Priority.ALWAYS);
        HBox panelImagen = new HBox(2, txtImagen, btnSeleccionarImagen);
        panelImagen.setAlignment(Pos.BOTTOM_RIGHT);
        miGrilla.add(panelImagen, segundaColumna, fila);

        // Imagen en columna derecha
        imgPorDefecto = Icono.obtenerFotosExternas(
                objDestino.getNombreImagenPrivadoDestino(), 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        GridPane.setValignment(imgPorDefecto, javafx.geometry.VPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 1, 1, 7);

        // ESPACIO ADICIONAL
        fila++;

        // BOTÓN ACTUALIZAR
        fila++;
        Button btnActualizar = new Button("Actualizar Destino");
        btnActualizar.setPrefHeight(ALTO_CAJA);
        btnActualizar.setMaxWidth(Double.MAX_VALUE);
        btnActualizar.setTextFill(Color.web("#FFFFFF"));
        btnActualizar.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        btnActualizar.setStyle("-fx-background-color: " + Configuracion.AZUL_MEDIO + ";");
        btnActualizar.setOnAction(e -> actualizarDestino());
        miGrilla.add(btnActualizar, segundaColumna, fila);

        // BOTÓN REGRESAR
        fila++;
        Button btnRegresar = new Button("Regresar");
        btnRegresar.setPrefHeight(ALTO_CAJA);
        btnRegresar.setMaxWidth(Double.MAX_VALUE);
        btnRegresar.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        btnRegresar.setOnAction(e -> {
            if (desdeCarrusel) {
                panelCuerpo = DestinoControladorVentana.carrusel(
                        miEscenario, panelPrincipal, panelCuerpo,
                        Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO, posicion);
            } else {
                panelCuerpo = DestinoControladorVentana.administrar(
                        miEscenario, panelPrincipal, panelCuerpo,
                        Configuracion.ANCHO_APP, Configuracion.ALTO_CUERPO);
            }
            panelPrincipal.setCenter(null);
            panelPrincipal.setCenter(panelCuerpo);
        });
        miGrilla.add(btnRegresar, segundaColumna, fila);
    }

    private Boolean formularioCompleto() {
        if (txtNombreDestino.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar el nombre");
            txtNombreDestino.requestFocus();
            return false;
        }

        if (txtDepartamentoDestino.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar el departamento");
            txtDepartamentoDestino.requestFocus();
            return false;
        }

        if (txtDescripcionDestino.getText().isBlank()) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Campo vacío", "Debe ingresar una descripción");
            txtDescripcionDestino.requestFocus();
            return false;
        }

        if (cmbEstadoDestino.getSelectionModel().getSelectedIndex() == 0) {
            Mensaje.mostrar(Alert.AlertType.WARNING, this.getScene().getWindow(),
                    "Estado no seleccionado", "Debe seleccionar un estado");
            return false;
        }

        return true;
    }

    private void actualizarDestino() {
        if (formularioCompleto()) {
            DestinoDto dtoActualizado = new DestinoDto();
            dtoActualizado.setIdDestino(objDestino.getIdDestino());
            dtoActualizado.setNombreDestino(txtNombreDestino.getText());
            dtoActualizado.setDepartamentoDestino(txtDepartamentoDestino.getText());
            dtoActualizado.setDescripcionDestino(txtDescripcionDestino.getText());
            dtoActualizado.setEstadoDestino(cmbEstadoDestino.getValue().equals("Activo"));
            dtoActualizado.setNombreImagenPublicoDestino(txtImagen.getText());
            dtoActualizado.setNombreImagenPrivadoDestino(objDestino.getNombreImagenPrivadoDestino());

            if (DestinoControladorEditar.actualizar(posicion, dtoActualizado, rutaImagenSeleccionada)) {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, this.getScene().getWindow(),
                        "Éxito", "Destino actualizado correctamente");
            } else {
                Mensaje.mostrar(Alert.AlertType.ERROR, this.getScene().getWindow(),
                        "Error", "No se pudo actualizar el destino");
            }
        }
    }
}
