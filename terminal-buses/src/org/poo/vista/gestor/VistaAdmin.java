package org.poo.vista.gestor;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.poo.recurso.constante.Configuracion;

public class VistaAdmin {

    private final Stage miEscenario;
    private final Scene miEscena;
    private final Pane miPanelCuerpo;
    private final BorderPane miPanelPrincipal;
    private final VistaCabecera miCabecera;

    public VistaAdmin(Stage stage) {
        miEscenario = stage;
        miPanelCuerpo = new Pane();
        miPanelPrincipal = new BorderPane();

        // Crear la cabecera con los menús
        miCabecera = new VistaCabecera(
                miEscenario,
                miPanelPrincipal,
                miPanelCuerpo,
                Configuracion.ANCHO_APP,
                Configuracion.ALTO_CABECERA
        );

        // Configurar el layout principal
        miPanelPrincipal.setTop(miCabecera);
        miPanelPrincipal.setCenter(miPanelCuerpo);

        // Crear la escena
        miEscena = new Scene(
                miPanelPrincipal,
                Configuracion.ANCHO_APP,
                Configuracion.ALTO_APP
        );

        miEscenario.setScene(miEscena);
    }

    /**
     * Habilita el evento de cierre con la X de la ventana
     */
    public void habilitarXCerrar(Runnable accion) {
        miEscenario.setOnCloseRequest(event -> {
            event.consume();
            accion.run();
        });
    }

    public Stage getMiEscenario() {
        return miEscenario;
    }
}
