package org.poo.controlador.pasajero;

import org.poo.dto.PasajeroDto;
import org.poo.servicio.PasajeroServicio;

public class PasajeroControladorGrabar {

    public static Boolean crearPasajero(PasajeroDto dto, String rutaDeLaImagen) {
        Boolean correcto = false;
        PasajeroServicio pasajeroServicio = new PasajeroServicio();
        PasajeroDto dtoRespuesta;
        dtoRespuesta = pasajeroServicio.insertInto(dto, rutaDeLaImagen);

        if (dtoRespuesta != null) {
            correcto = true;
        }

        return correcto;
    }
}
