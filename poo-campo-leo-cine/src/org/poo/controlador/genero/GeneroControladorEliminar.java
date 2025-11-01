package org.poo.controlador.genero;

import org.poo.servicio.GeneroServicio;

public class GeneroControladorEliminar {
    public static Boolean borrar(int codigo){
        boolean correcto;
        GeneroServicio generoServicio = new GeneroServicio();
        correcto = generoServicio.deleteFrom(codigo);
        return correcto;
    }
}
