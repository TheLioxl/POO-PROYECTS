package org.poo.vista.gestor;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.poo.recurso.constante.Configuracion;
import org.poo.recurso.utilidad.Icono;

public class VistaAcerca {

    public final static String LBL_TEXTO = "#2E2E2E";

    public static void mostrar(Stage escenarioPadre, double anchoPanel, double altoPanel) {
        Stage escenarioModal = new Stage();

        VBox miPanel = new VBox(6);
        miPanel.setAlignment(Pos.CENTER);
        miPanel.setPadding(new Insets(10, 0, 0, 0));
        miPanel.setStyle(Configuracion.CABECERA_ESTILO_FONDO);

        // --- FOTOS ---
        ImageView foto1 = Icono.obtenerIcono("developer1.jpg", 250);
        foto1.setPreserveRatio(true);

        ImageView foto2 = Icono.obtenerIcono("developer2.jpg", 250);
        foto2.setPreserveRatio(true);

        ImageView foto3 = Icono.obtenerIcono("developer3.jpg", 250);
        foto3.setPreserveRatio(true);

// --- PERSONA 1 ---
        Label nombre1 = new Label("Maria Alexandra Osorio Jacquin");
        nombre1.setTextFill(Color.web(LBL_TEXTO));
        nombre1.setFont(Font.font("Rockwell", FontWeight.BOLD, 14));

        Label correo1 = new Label("maosorio@unimagdalena.edu.co");
        correo1.setTextFill(Color.web(LBL_TEXTO));
        correo1.setFont(Font.font("Rockwell", FontWeight.NORMAL, 12));

        Label codigo1 = new Label("2024214006");
        codigo1.setTextFill(Color.web(LBL_TEXTO));
        codigo1.setFont(Font.font("Rockwell", FontWeight.NORMAL, 12));

        VBox persona1 = new VBox(5);
        persona1.setAlignment(Pos.CENTER);
        persona1.getChildren().addAll(foto1, nombre1, correo1, codigo1);

// --- PERSONA 2 ---
        Label nombre2 = new Label("Leonardo Raul Campo Ballesteros");
        nombre2.setTextFill(Color.web(LBL_TEXTO));
        nombre2.setFont(Font.font("Rockwell", FontWeight.BOLD, 14));

        Label correo2 = new Label("lrcampo@unimagdalena.edu.co");
        correo2.setTextFill(Color.web(LBL_TEXTO));
        correo2.setFont(Font.font("Rockwell", FontWeight.NORMAL, 12));

        Label codigo2 = new Label("2024214050");
        codigo2.setTextFill(Color.web(LBL_TEXTO));
        codigo2.setFont(Font.font("Rockwell", FontWeight.NORMAL, 12));

        VBox persona2 = new VBox(5);
        persona2.setAlignment(Pos.CENTER);
        persona2.getChildren().addAll(foto2, nombre2, correo2, codigo2);

// --- PERSONA 3 ---
        Label nombre3 = new Label("Ricardo Rafael Sierra Acevedo");
        nombre3.setTextFill(Color.web(LBL_TEXTO));
        nombre3.setFont(Font.font("Rockwell", FontWeight.BOLD, 14));

        Label correo3 = new Label("rrsierra@unimagdalena.edu.co");
        correo3.setTextFill(Color.web(LBL_TEXTO));
        correo3.setFont(Font.font("Rockwell", FontWeight.NORMAL, 12));

        Label codigo3 = new Label("2025114049");
        codigo3.setTextFill(Color.web(LBL_TEXTO));
        codigo3.setFont(Font.font("Rockwell", FontWeight.NORMAL, 12));

        VBox persona3 = new VBox(5);
        persona3.setAlignment(Pos.CENTER);
        persona3.getChildren().addAll(foto3, nombre3, correo3, codigo3);

// --- CONTENEDOR HORIZONTAL PARA LOS 3 BLOQUES ---
        HBox contenedorFotos = new HBox(25);
        contenedorFotos.setAlignment(Pos.CENTER);
        contenedorFotos.getChildren().addAll(persona1, persona2, persona3);

        Button btnCerrar = new Button("CERRAR");
        btnCerrar.setPrefWidth(180);
        btnCerrar.setFont(Font.font("Rockwell", FontWeight.BOLD, 14));
        btnCerrar.setStyle("-fx-background-color: " + Configuracion.AZUL_SUPER_CLARO + ";"
                + "-fx-border-radius: 8; -fx-background-radius: 8;");
        btnCerrar.setCursor(Cursor.HAND);
        btnCerrar.setOnAction(event -> escenarioModal.close());

// --- AGREGAR TODO AL PANEL PRINCIPAL ---
        miPanel.getChildren().addAll(contenedorFotos, btnCerrar);

        Scene nuevaEscena = new Scene(miPanel, 850, 450);

        escenarioModal.setScene(nuevaEscena);
        escenarioModal.initModality(Modality.APPLICATION_MODAL);
        escenarioModal.initStyle(StageStyle.UTILITY);
        escenarioModal.setTitle("Acerca de ...");
        escenarioModal.show();

        escenarioPadre.getScene().getRoot().setOpacity(0.2);
        escenarioModal.setOnHidden(event -> escenarioPadre.getScene().getRoot().setOpacity(1.0));
    }
}
