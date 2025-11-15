package org.poo.controlador.destino;

import org.poo.dto.DestinoDto;
import org.poo.servicio.DestinoServicio;

public class DestinoControladorGrabar {

    public static Boolean crearDestino(DestinoDto dto, String rutaDeLaImagen) {
        Boolean correcto = false;
        DestinoServicio destinoServicio = new DestinoServicio();
        DestinoDto dtoRespuesta;
        dtoRespuesta = destinoServicio.insertInto(dto, rutaDeLaImagen);

        if (dtoRespuesta != null) {
            correcto = true;
        }

        return correcto;
    }
}
