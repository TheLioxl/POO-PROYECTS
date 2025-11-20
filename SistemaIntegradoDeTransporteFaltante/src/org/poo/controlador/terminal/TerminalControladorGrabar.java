package org.poo.controlador.terminal;

import org.poo.dto.TerminalDto;
import org.poo.servicio.TerminalServicio;

public class TerminalControladorGrabar {

    public static Boolean crearTerminal(TerminalDto dto, String rutaDeLaImagen) {
        Boolean correcto = false;
        TerminalServicio terminalServicio = new TerminalServicio();
        TerminalDto dtoRespuesta;
        dtoRespuesta = terminalServicio.insertInto(dto, rutaDeLaImagen);

        if (dtoRespuesta != null) {
            correcto = true;
        }

        return correcto;
    }
}
