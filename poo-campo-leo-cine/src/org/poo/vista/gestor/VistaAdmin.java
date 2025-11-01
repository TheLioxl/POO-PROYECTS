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

    public VistaAdmin(Stage esce) {
        miEscenario = esce;
        miPanelCuerpo = new Pane();
        miPanelPrincipal = new BorderPane();

        VistaCabecera cabecera = new VistaCabecera(esce, miPanelPrincipal, Configuracion.ALTO_CABECERA);

        miPanelPrincipal.setTop(cabecera);
        miPanelPrincipal.setCenter(miPanelCuerpo);

        miEscena = new Scene(miPanelPrincipal, Configuracion.ANCHO_APP, Configuracion.ALTO_APP);
        miEscenario.setScene(miEscena);
    }

    public void habilitarXcerrar(Runnable accion) {
        miEscenario.setOnCloseRequest((e) -> {
            e.consume();
            accion.run();
        });
    }
}
