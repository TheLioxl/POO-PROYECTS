package org.poo.controlador.viaje;

import org.poo.servicio.ViajeServicio;

public class ViajeControladorEliminar {

    public static Boolean borrar(int codigo) {
        Boolean correcto;
        ViajeServicio viajeServicio = new ViajeServicio();
        correcto = viajeServicio.deleteFrom(codigo);
        return correcto;
    }
}