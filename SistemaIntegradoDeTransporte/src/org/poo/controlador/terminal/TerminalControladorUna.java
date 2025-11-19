package org.poo.controlador.terminal;

import org.poo.dto.TerminalDto;
import org.poo.servicio.TerminalServicio;

public class TerminalControladorUna {

    public static TerminalDto obtenerTerminal(int indice) {
        TerminalDto obj;

        TerminalServicio miDAO = new TerminalServicio();
        obj = miDAO.getOne(indice);

        return obj;
    }
}
