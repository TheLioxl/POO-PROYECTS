package org.unimag.controlador.genero;

import org.unimag.dto.GeneroDto;
import org.unimag.servicio.GeneroServicio;

public class GeneroControladorGrabar {

    public static Boolean crearGenero(GeneroDto dto, String rutaDeLaImagen) {

        Boolean correcto = false;
        GeneroServicio generoServicio = new GeneroServicio();
        GeneroDto dtoRespuesta;
        dtoRespuesta = generoServicio.inserInto(dto, rutaDeLaImagen);

        if (dtoRespuesta != null) {
            correcto = true;
        }

        return correcto;
    }
}
