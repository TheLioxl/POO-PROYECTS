package org.poo.controlador.destino;

import org.poo.servicio.DestinoServicio;

public class DestinoControladorEliminar {

    public static Boolean borrar(int codigo) {
        Boolean correcto;
        DestinoServicio destinoServicio = new DestinoServicio();
        correcto = destinoServicio.deleteFrom(codigo);
        return correcto;
    }
}
