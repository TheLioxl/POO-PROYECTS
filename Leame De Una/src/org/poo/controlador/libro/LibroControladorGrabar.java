package org.poo.controlador.libro;

import org.poo.dto.LibroDto;
import org.poo.servicio.LibroServicio;

public class LibroControladorGrabar {

    public static Boolean crearLibro(LibroDto dto) {
        Boolean correcto = false;

        LibroServicio libroServicio = new LibroServicio();
        LibroDto dtoRespuesta;
        dtoRespuesta = libroServicio.insertInto(dto, "");

        if (dtoRespuesta != null) {
            correcto = true;
        }

        return correcto;
    }
}