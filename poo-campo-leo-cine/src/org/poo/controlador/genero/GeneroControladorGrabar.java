package org.poo.controlador.genero;

import org.poo.dto.GeneroDto;
import org.poo.servicio.GeneroServicio;

public class GeneroControladorGrabar {

    public static Boolean crearGenero(GeneroDto dto) {
        Boolean correcto = false;

        GeneroServicio generoServicio = new GeneroServicio();
        GeneroDto dtoRespuesta;
        dtoRespuesta = generoServicio.insertInto(dto, "");
        if (dtoRespuesta != null) {
            correcto = true;
        }
        return correcto;
    }

}
