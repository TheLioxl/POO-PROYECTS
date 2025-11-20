package org.poo.controlador.ruta;

import org.poo.dto.RutaDto;
import org.poo.servicio.RutaServicio;

public class RutaControladorEditar {

    public static Boolean actualizarRuta(int indice, RutaDto dto, String rutaDeLaImagen) {
        Boolean correcto = false;
        RutaServicio rutaServicio = new RutaServicio();
        RutaDto dtoRespuesta;
        dtoRespuesta = rutaServicio.updateSet(indice, dto, rutaDeLaImagen);

        if (dtoRespuesta != null) {
            correcto = true;
        }

        return correcto;
    }
}
