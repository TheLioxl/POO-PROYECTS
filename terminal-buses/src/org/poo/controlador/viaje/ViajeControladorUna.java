package org.poo.controlador.viaje;

import org.poo.dto.ViajeDto;
import org.poo.servicio.ViajeServicio;

public class ViajeControladorUna {

    public static ViajeDto obtenerViaje(int indice) {
        ViajeDto obj;

        ViajeServicio miDAO = new ViajeServicio();
        obj = miDAO.getOne(indice);

        return obj;
    }
}