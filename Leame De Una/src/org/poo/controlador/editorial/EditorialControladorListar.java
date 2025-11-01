package org.poo.controlador.editorial;

import java.util.List;
import org.poo.dto.EditorialDto;
import org.poo.servicio.EditorialServicio;

public class EditorialControladorListar {

    public static List<EditorialDto> obtenerEditoriales() {
        EditorialServicio miDao = new EditorialServicio();
        List<EditorialDto> arreglo = miDao.selectFrom();
        return arreglo;
    }

    public static int obtenerCantidadEditoriales() {
        EditorialServicio miDao = new EditorialServicio();
        int cantidad = miDao.numRows();
        return cantidad;
    }
}
