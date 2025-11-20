package org.poo.controlador.terminal;

import org.poo.servicio.TerminalServicio;

public class TerminalControladorEliminar {

    public static Boolean borrar(int codigo) {
        Boolean correcto;
        TerminalServicio terminalServicio = new TerminalServicio();
        correcto = terminalServicio.deleteFrom(codigo);
        return correcto;
    }
}
