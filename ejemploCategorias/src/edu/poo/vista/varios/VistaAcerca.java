package edu.poo.vista.varios;

import edu.poo.recurso.dominio.Ruta;
import edu.poo.recurso.dominio.Configuracion;
import edu.poo.recurso.utilidad.Icono;

import javafx.geometry.Pos;
import javafx.geometry.Insets;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.text.Font;
import javafx.stage.StageStyle;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.text.FontWeight;
import javafx.scene.image.ImageView;

public class VistaAcerca {

    public final static String LBL_TEXTO = "#2E2E2E";
    public final static String ACERCA_NOMBRE = "Andrés Guerrero";
    public final static String ACERCA_FOTO = "developer.jpg";
    public final static String ACERCA_CODIGO = "66778899";
    public final static String ACERCA_CORREO = "elProfe@dePoo.com";

    public static void mostrar(Stage escenarioPadre, double anchoPanel, double altoPanel) {
        Stage escenarioModal = new Stage();

        VBox miPanel = new VBox(6);
        miPanel.setAlignment(Pos.CENTER);
        miPanel.setPadding(new Insets(10, 0, 0, 0));
        miPanel.setStyle(Configuracion.CABECERA_COLOR_FONDO);

        ImageView foto = Icono.obtenerIcono(ACERCA_FOTO, 300);
        foto.setPreserveRatio(true);

        Label lblNombre = new Label(ACERCA_NOMBRE);
        lblNombre.setAlignment(Pos.CENTER);
        lblNombre.setTextFill(Color.web(LBL_TEXTO));
        lblNombre.setFont(Font.font("Verdana", FontWeight.BOLD, 18));

        Label lblCorreo = new Label(ACERCA_CORREO);
        lblCorreo.setAlignment(Pos.CENTER);
        lblCorreo.setTextFill(Color.web(LBL_TEXTO));
        lblCorreo.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));

        Label lblCodigo = new Label(ACERCA_CODIGO);
        lblCodigo.setAlignment(Pos.CENTER);
        lblCodigo.setTextFill(Color.web(LBL_TEXTO));
        lblCodigo.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));

        Button btnCerrar = new Button("Cerrar");
        btnCerrar.setPrefWidth(160);
        btnCerrar.setTextFill(Color.web("#E82E68"));
        btnCerrar.setOnAction(event -> escenarioModal.close());

        miPanel.getChildren().addAll(foto, lblNombre, lblCorreo, lblCodigo, btnCerrar);

        Scene nuevaEscena = new Scene(miPanel, anchoPanel, altoPanel);
        escenarioModal.setScene(nuevaEscena);
        escenarioModal.initModality(Modality.APPLICATION_MODAL);
        escenarioModal.initStyle(StageStyle.UTILITY);
        escenarioModal.setTitle("Acerca de ...");
        escenarioModal.show();

        escenarioPadre.getScene().getRoot().setOpacity(0.2);
        escenarioModal.setOnHidden(event -> escenarioPadre.getScene().getRoot().setOpacity(1.0));
    }
}
