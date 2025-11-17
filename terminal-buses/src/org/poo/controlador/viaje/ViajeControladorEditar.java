package org.poo.controlador.viaje;

import org.poo.dto.ViajeDto;
import org.poo.servicio.ViajeServicio;

public class ViajeControladorEditar {

    public static boolean actualizar(int indiceExterno, ViajeDto objExterno, String rutaImagen) {
        boolean correcto;

        ViajeServicio miDao = new ViajeServicio();
        ViajeDto resultado = miDao.updateSet(indiceExterno, objExterno, rutaImagen);
        
        correcto = (resultado != null);
        
        return correcto;
    }
}