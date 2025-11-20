package org.poo.controlador.bus;

import org.poo.dto.BusDto;
import org.poo.servicio.BusServicio;

public class BusControladorUna {

    public static BusDto obtenerBus(int indice) {
        BusDto obj;

        BusServicio miDAO = new BusServicio();
        obj = miDAO.getOne(indice);

        return obj;
    }
}
