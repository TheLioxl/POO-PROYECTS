package org.poo.controlador.pelicula;

import org.poo.servicio.PeliculaServicio;

public class PeliculaControladorEliminar {

    public static Boolean borrar(int codigo) {
        boolean correcto;
        PeliculaServicio peliculaServicio = new PeliculaServicio();
        correcto = peliculaServicio.deleteFrom(codigo);
        return correcto;
    }
}
