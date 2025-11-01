package org.poo.controlador.editorial;

import org.poo.dto.EditorialDto;
import org.poo.servicio.EditorialServicio;

public class EditorialControladorGrabar {

    public static Boolean crearEditorial(EditorialDto dto) {
        Boolean correcto = false;

        EditorialServicio editorialServicio = new EditorialServicio();
        EditorialDto dtoRespuesta;
        dtoRespuesta = editorialServicio.insertInto(dto, "");
        if (dtoRespuesta != null) {
            correcto = true;
        }
        return correcto;
    }
}