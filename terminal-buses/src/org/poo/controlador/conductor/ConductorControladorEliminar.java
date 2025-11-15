package org.poo.controlador.conductor;

import org.poo.servicio.ConductorServicio;

public class ConductorControladorEliminar {

    public static Boolean borrar(int codigo) {
        Boolean correcto;
        ConductorServicio conductorServicio = new ConductorServicio();
        correcto = conductorServicio.deleteFrom(codigo);
        return correcto;
    }
}
