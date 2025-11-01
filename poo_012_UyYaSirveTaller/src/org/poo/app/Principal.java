
package org.poo.app;

import javafx.application.Application;
import javafx.stage.Stage;
import org.poo.controlador.SalidaControlador;
import org.poo.vista.gestor.VistaAdmin;

public class Principal extends Application{
    private VistaAdmin vistaAdmin;
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        vistaAdmin = new VistaAdmin(stage);
        stage.setTitle("Cine");
        
        vistaAdmin.habilitarXcerrar(()->{SalidaControlador.verificar(stage);});
        
        stage.show();
    }
}
