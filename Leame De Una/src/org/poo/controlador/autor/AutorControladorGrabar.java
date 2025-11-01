package org.poo.controlador.autor;

import org.poo.dto.AutorDto;
import org.poo.servicio.AutorServicio;

public class AutorControladorGrabar {

    public static Boolean crearAutor(AutorDto dto) {
        Boolean correcto = false;

        AutorServicio autorServicio = new AutorServicio();
        AutorDto dtoRespuesta;
        dtoRespuesta = autorServicio.insertInto(dto, "");
        if (dtoRespuesta != null) {
            correcto = true;
        }
        return correcto;
    }
}