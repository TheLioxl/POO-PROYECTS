package org.poo.controlador.viaje;

import org.poo.dto.ViajeDto;
import org.poo.servicio.ViajeServicio;

public class ViajeControladorGrabar {

    public static Boolean crearViaje(ViajeDto dto, String rutaDeLaImagen) {
        Boolean correcto = false;
        ViajeServicio viajeServicio = new ViajeServicio();
        ViajeDto dtoRespuesta;
        dtoRespuesta = viajeServicio.insertInto(dto, rutaDeLaImagen);

        if (dtoRespuesta != null) {
            correcto = true;
        }

        return correcto;
    }
}