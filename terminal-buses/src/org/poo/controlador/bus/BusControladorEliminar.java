package org.poo.controlador.bus;

import org.poo.servicio.BusServicio;

public class BusControladorEliminar {

    public static Boolean borrar(int codigo) {
        Boolean correcto;
        BusServicio busServicio = new BusServicio();
        correcto = busServicio.deleteFrom(codigo);
        return correcto;
    }
}
