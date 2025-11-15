package org.poo.controlador.conductor;

import java.util.List;
import org.poo.dto.ConductorDto;
import org.poo.servicio.ConductorServicio;

public class ConductorControladorListar {

    public static List<ConductorDto> obtenerConductores() {
        ConductorServicio miDao = new ConductorServicio();
        List<ConductorDto> arreglo = miDao.selectFrom();
        return arreglo;
    }

    public static List<ConductorDto> obtenerConductoresActivos() {
        ConductorServicio miDao = new ConductorServicio();
        List<ConductorDto> arreglo = miDao.selectFromWhereActivos();
        return arreglo;
    }

    public static int obtenerCantidadConductores() {
        ConductorServicio miDao = new ConductorServicio();
        int cantidad = miDao.numRows();
        return cantidad;
    }
}
