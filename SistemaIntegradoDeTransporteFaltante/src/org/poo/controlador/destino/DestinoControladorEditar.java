package org.poo.controlador.destino;

import org.poo.dto.DestinoDto;
import org.poo.servicio.DestinoServicio;

public class DestinoControladorEditar {
    
    public static boolean actualizar(int posicion, DestinoDto dto, String rutaImagen) {
        DestinoServicio servicio = new DestinoServicio();
        DestinoDto resultado = servicio.updateSet(posicion, dto, rutaImagen);
        return resultado != null;
    }
}
