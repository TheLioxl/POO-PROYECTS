package org.poo.controlador;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.poo.recurso.utilidad.Icono;

public class SalidaControlador {

    public static void verificar(Stage stage) {
        Alert msg = new Alert(Alert.AlertType.CONFIRMATION);
        msg.setTitle("Salir del Sistema");
        msg.initOwner(stage);
        msg.setHeaderText(null);
        
        // Añadir ícono personalizado si está disponible
        try {
            msg.getDialogPane().setGraphic(Icono.obtenerIcono("iconoTerminal.png", 40));
        } catch (Exception e) {
            // Si no se encuentra el ícono, continuar sin él
        }
        
        msg.setContentText("¿Está seguro de que desea salir del sistema?");

        if (msg.showAndWait().get() == ButtonType.OK) {
            stage.close();
            System.exit(0);
        }
    }
}
