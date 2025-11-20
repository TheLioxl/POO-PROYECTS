package org.poo.controlador.ruta;

import org.poo.servicio.RutaServicio;

public class RutaControladorEliminar {

    public static Boolean borrar(int codigo) {
        Boolean correcto;
        RutaServicio rutaServicio = new RutaServicio();
        correcto = rutaServicio.deleteFrom(codigo);
        return correcto;
    }
}
