package org.poo.controlador.pasajero;

import org.poo.dto.PasajeroDto;
import org.poo.servicio.PasajeroServicio;

public class PasajeroControladorEditar {
    
    public static boolean actualizar(int indiceExterno, PasajeroDto objExterno, String rutaImagen) {
        boolean correcto;

        PasajeroServicio miDao = new PasajeroServicio();
        PasajeroDto resultado = miDao.updateSet(indiceExterno, objExterno, rutaImagen);

        correcto = (resultado != null);

        return correcto;
    }

}
