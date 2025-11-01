package org.poo.controlador.autor;

import org.poo.servicio.AutorServicio;

public class AutorControladorEliminar {

    public static Boolean borrar(int codigo) {
        boolean correcto;
        AutorServicio autorServicio = new AutorServicio();
        correcto = autorServicio.deleteFrom(codigo);
        return correcto;
    }
}