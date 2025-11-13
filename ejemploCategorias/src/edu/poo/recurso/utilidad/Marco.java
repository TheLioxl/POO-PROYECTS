package edu.poo.recurso.utilidad;

import javafx.scene.paint.Stop;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.stage.Stage;

public class Marco {

    public static Rectangle crear(Stage ventana, double porcentajeAncho, double porcentajeAlto, 
            Stop[] arrColores, String colorBorde) {
        Rectangle marco = new Rectangle();
        // Ancho y alto responsive
        marco.widthProperty().bind(ventana.widthProperty().multiply(porcentajeAncho));
        marco.heightProperty().bind(ventana.heightProperty().multiply(porcentajeAlto));
        // *********************************************************************

        marco.setArcWidth(30);
        marco.setArcHeight(30);
        marco.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, arrColores));
        marco.setStroke(Color.web(colorBorde));
        return marco;
    }
}
