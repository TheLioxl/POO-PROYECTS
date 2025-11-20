package org.poo.controlador.ruta;

import org.poo.dto.RutaDto;
import org.poo.servicio.RutaServicio;

public class RutaControladorUna {

    public static RutaDto obtenerUnaRuta(int indice) {
        RutaServicio miDao = new RutaServicio();
        RutaDto ruta = miDao.getOne(indice);
        return ruta;
    }
}
