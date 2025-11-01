
package org.poo.controlador.genero;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.poo.vista.genero.VistaGeneroAdministrar;
import org.poo.vista.genero.VistaGeneroCrear;
import org.poo.vista.genero.VistaGeneroListar;

public class GeneroVistasControlador {
    public static StackPane crearGen(Stage esce, double anchito, double altito){
        return new VistaGeneroCrear(esce, anchito,altito);
    }
    
    public static StackPane listarGen (Stage esce, double ancho, double alto){
        return new VistaGeneroListar(esce, ancho, alto);
   }
    
    public static StackPane administrarGen (Stage esce, double ancho, double alto){
        return new VistaGeneroAdministrar(esce, ancho, alto);
   }    
    
}
