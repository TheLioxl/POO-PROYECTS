package org.poo.app;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.poo.controlador.SalidaControlador;
import org.poo.recurso.constante.Persistencia;
import org.poo.vista.gestor.VistaAdmin;

public class Principal extends Application {

    private VistaAdmin vistaAdmin;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        vistaAdmin = new VistaAdmin(stage);
        stage.setTitle("Sistema de Terminal de Buses - Universidad del Magdalena");
        try {
            String rutaIcono = Persistencia.NOMBRE_CARPETA_IMAGENES_INTERNAS + "iconoTerminal.png";
            Image icono = new Image(getClass().getResourceAsStream(rutaIcono));
            stage.getIcons().add(icono);
        } catch (Exception e) {
            System.out.println("No se pudo cargar el ícono de la aplicación");
        }

        vistaAdmin.habilitarXCerrar(() -> {
            SalidaControlador.verificar(stage);
        });
        stage.show();
    }
}
