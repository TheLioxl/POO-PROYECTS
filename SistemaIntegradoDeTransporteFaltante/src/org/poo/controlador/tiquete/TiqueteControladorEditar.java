package org.poo.controlador.tiquete;

import org.poo.dto.TiqueteDto;
import org.poo.servicio.TiqueteServicio;

public class TiqueteControladorEditar {

    public static boolean actualizar(int indiceExterno, TiqueteDto objExterno, String rutaImagen) {
        boolean correcto;

        TiqueteServicio miDao = new TiqueteServicio();
        TiqueteDto resultado = miDao.updateSet(indiceExterno, objExterno, rutaImagen);

        correcto = (resultado != null);
        
        return correcto;
    }
}
