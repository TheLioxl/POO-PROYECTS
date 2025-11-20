package org.poo.controlador.bus;

import org.poo.dto.BusDto;
import org.poo.servicio.BusServicio;

public class BusControladorEditar {

    public static boolean actualizar(int indiceExterno, BusDto objExterno, String rutaImagen) {
        boolean correcto;

        BusServicio miDao = new BusServicio();
        BusDto resultado = miDao.updateSet(indiceExterno, objExterno, rutaImagen);
        
        correcto = (resultado != null);
        return correcto;
    }
}
