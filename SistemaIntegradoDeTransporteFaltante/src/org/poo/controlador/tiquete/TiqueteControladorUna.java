package org.poo.controlador.tiquete;

import org.poo.dto.TiqueteDto;
import org.poo.servicio.TiqueteServicio;

public class TiqueteControladorUna {

    public static TiqueteDto obtenerTiquete(int indice) {
        TiqueteDto obj;

        TiqueteServicio miDAO = new TiqueteServicio();
        obj = miDAO.getOne(indice);

        return obj;
    }
}
