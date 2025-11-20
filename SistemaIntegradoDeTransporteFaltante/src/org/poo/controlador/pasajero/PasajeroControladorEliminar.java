package org.poo.controlador.pasajero;

import org.poo.servicio.PasajeroServicio;

public class PasajeroControladorEliminar {

    public static Boolean borrar(int codigo) {
        Boolean correcto;
        PasajeroServicio pasajeroServicio = new PasajeroServicio();
        correcto = pasajeroServicio.deleteFrom(codigo);
        return correcto;
    }
}
