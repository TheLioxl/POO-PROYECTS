package org.poo.controlador.pasajero;

import org.poo.dto.PasajeroDto;
import org.poo.servicio.PasajeroServicio;

public class PasajeroControladorUna {

    public static PasajeroDto obtenerPasajero(int indice) {
        PasajeroDto obj;

        PasajeroServicio miDAO = new PasajeroServicio();
        obj = miDAO.getOne(indice);

        return obj;
    }
}
