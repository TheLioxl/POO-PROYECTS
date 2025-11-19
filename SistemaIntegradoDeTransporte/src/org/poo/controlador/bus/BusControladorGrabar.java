package org.poo.controlador.bus;

import org.poo.dto.BusDto;
import org.poo.servicio.BusServicio;

public class BusControladorGrabar {

    public static Boolean crearBus(BusDto dto, String rutaDeLaImagen) {
        Boolean correcto = false;
        BusServicio busServicio = new BusServicio();
        BusDto dtoRespuesta;
        dtoRespuesta = busServicio.insertInto(dto, rutaDeLaImagen);

        if (dtoRespuesta != null) {
            correcto = true;
        }

        return correcto;
    }
}
