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
        // Crear la vista principal
        vistaAdmin = new VistaAdmin(stage);

        // Configurar título
        stage.setTitle("Sistema de Terminal de Buses - Universidad del Magdalena");

        // Configurar ícono de la aplicación
        try {
            String rutaIcono = Persistencia.NOMBRE_CARPETA_IMAGENES_INTERNAS + "iconoTerminal.png";
            Image icono = new Image(getClass().getResourceAsStream(rutaIcono));
            stage.getIcons().add(icono);
        } catch (Exception e) {
            System.out.println("No se pudo cargar el ícono de la aplicación");
        }

        // Habilitar la X de cerrar
        vistaAdmin.habilitarXCerrar(() -> {
            SalidaControlador.verificar(stage);
        });

        // Mostrar ventana
        stage.show();
    }
}
