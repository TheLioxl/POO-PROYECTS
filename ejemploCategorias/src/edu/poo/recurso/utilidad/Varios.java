package edu.poo.recurso.utilidad;

import java.util.ArrayList;

import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.stage.Window;
import javafx.collections.ObservableList;

public class Varios {

    // Ver nodos
    // *************************************************************************
    public static void verNodosPanel(Parent objeto) {
        System.out.println("Tipo --> " + objeto);
        ArrayList<Node> arregloNodos = new ArrayList<>();
        descencientes(objeto, arregloNodos);
        for (Node nodito : arregloNodos) {
            System.out.println("Componente --> " + nodito);
        }
    }

    private static void descencientes(Parent objeto, ArrayList<Node> arregloNodos) {
        for (Node nodito : objeto.getChildrenUnmodifiable()) {
            arregloNodos.add(nodito);
            if (nodito instanceof Parent padre) {
                descencientes(padre, arregloNodos);
            }
        }
    }
    // *************************************************************************

    // Obtener el STAGE abierto
    // *************************************************************************
    public static Stage obtenerEscenarioAbierto() {
        Stage escenario = null;
        ObservableList<Window> arrVentanas = Stage.getWindows();
        for (Window ventana : arrVentanas) {
            if (ventana.isShowing()) {
//                System.out.println("Stage abierto -> " + ventana);
                escenario = (Stage) ventana;
            }
        }
        return escenario;
    }
    // *************************************************************************
}
