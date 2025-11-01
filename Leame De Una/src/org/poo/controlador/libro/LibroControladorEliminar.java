package org.poo.controlador.libro;

import org.poo.servicio.LibroServicio;

public class LibroControladorEliminar {

    public static Boolean borrar(int codigo) {
        boolean correcto;
        LibroServicio libroServicio = new LibroServicio();
        correcto = libroServicio.deleteFrom(codigo);
        return correcto;
    }
}