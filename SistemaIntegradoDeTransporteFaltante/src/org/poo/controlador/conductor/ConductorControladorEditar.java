package org.poo.controlador.conductor;

import org.poo.dto.ConductorDto;
import org.poo.servicio.ConductorServicio;

public class ConductorControladorEditar {

    public static boolean actualizar(int indiceExterno, ConductorDto objExterno, String rutaImagen) {
        boolean correcto;

        ConductorServicio miDao = new ConductorServicio();
        ConductorDto resultado = miDao.updateSet(indiceExterno, objExterno, rutaImagen);

        correcto = (resultado != null);

        return correcto;

    }
}
