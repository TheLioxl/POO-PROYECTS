package org.poo.controlador.pelicula;

import org.poo.dto.PeliculaDto;
import org.poo.servicio.PeliculaServicio;

public class PeliculaControladorGrabar {

    public static Boolean crearPelicula(PeliculaDto dto, String rutaDeLaImagen) {
        Boolean correcto = false;

        PeliculaServicio peliculaServicio = new PeliculaServicio();
        PeliculaDto dtoRespuesta;
        dtoRespuesta = peliculaServicio.insertInto(dto, rutaDeLaImagen);

        if (dtoRespuesta != null) {
            correcto = true;
        }

        return correcto;
    }
}