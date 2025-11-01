package org.poo.controlador.editorial;

import org.poo.servicio.EditorialServicio;

public class EditorialControladorEliminar {

    public static Boolean borrar(int codigo) {
        boolean correcto;
        EditorialServicio editorialServicio = new EditorialServicio();
        correcto = editorialServicio.deleteFrom(codigo);
        return correcto;
    }
}