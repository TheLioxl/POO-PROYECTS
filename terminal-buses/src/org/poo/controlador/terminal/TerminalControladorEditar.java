package org.poo.controlador.terminal;

import org.poo.dto.TerminalDto;
import org.poo.servicio.TerminalServicio;

public class TerminalControladorEditar {

    public static boolean actualizar(int indiceExterno, TerminalDto objExterno, String rutaImagen) {
        boolean correcto;

        TerminalServicio miDao = new TerminalServicio();
        TerminalDto resultado = miDao.updateSet(indiceExterno, objExterno, rutaImagen);
        
        // Si el resultado no es null, la actualización fue exitosa
        correcto = (resultado != null);
        
        return correcto;
    }
}
