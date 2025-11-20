package org.poo.controlador.conductor;

import org.poo.dto.ConductorDto;
import org.poo.servicio.ConductorServicio;

public class ConductorControladorGrabar {

    public static Boolean crearConductor(ConductorDto dto, String rutaDeLaImagen) {
        Boolean correcto = false;
        ConductorServicio conductorServicio = new ConductorServicio();
        ConductorDto dtoRespuesta;
        dtoRespuesta = conductorServicio.insertInto(dto, rutaDeLaImagen);

        if (dtoRespuesta != null) {
            correcto = true;
        }

        return correcto;
    }
}
