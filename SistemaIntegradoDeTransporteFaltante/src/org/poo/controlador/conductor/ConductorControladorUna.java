package org.poo.controlador.conductor;

import org.poo.dto.ConductorDto;
import org.poo.servicio.ConductorServicio;

public class ConductorControladorUna {
    
    public static ConductorDto obtenerConductor(int indice) {
        ConductorDto obj;

        ConductorServicio miDAO = new ConductorServicio();
        obj = miDAO.getOne(indice);

        return obj;
    }

}
