package org.poo.controlador.terminal;

import org.poo.dto.TerminalDto;
import org.poo.servicio.TerminalServicio;

public class TerminalControladorEditar {

    public static boolean actualizar(int indiceExterno, TerminalDto objExterno, String rutaImagen) {
        boolean correcto;

        TerminalServicio miDao = new TerminalServicio();
        correcto = miDao.updateSet(indiceExterno, objExterno, rutaImagen);
        
        return correcto;
    }
}