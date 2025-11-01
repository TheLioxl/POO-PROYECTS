package org.poo.recurso.utilidad;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Mensaje {

    private static Alert crearAlerta(
            Alert.AlertType tipo,
            Window ventanaPadre,
            String titulo,
            String contenido
    ) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.initOwner(ventanaPadre);
        return alerta;
    }

    public static void salir(Stage miEscenario) {
        Alert msg = crearAlerta(Alert.AlertType.CONFIRMATION,
                miEscenario, "AMF", "¿ey, ya te vas?");
        if (msg.showAndWait().get() == ButtonType.OK) {
            miEscenario.close();
        }
    }

    public static void mostrar(
            Alert.AlertType tipo,
            Window ventanaPadre,
            String titulo,
            String contenido) {
        Alert msg = crearAlerta(tipo, ventanaPadre, titulo, contenido);
        msg.show();
    }
}
