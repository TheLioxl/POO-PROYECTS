package org.poo.controlador.tiquete;

import org.poo.dto.TiqueteDto;
import org.poo.servicio.TiqueteServicio;

public class TiqueteControladorGrabar {

    public static Boolean crearTiquete(TiqueteDto dto, String rutaDeLaImagen) {
        Boolean correcto = false;
        TiqueteServicio tiqueteServicio = new TiqueteServicio();
        TiqueteDto dtoRespuesta;
        dtoRespuesta = tiqueteServicio.insertInto(dto, rutaDeLaImagen);

        if (dtoRespuesta != null) {
            correcto = true;
        }

        return correcto;
    }
}