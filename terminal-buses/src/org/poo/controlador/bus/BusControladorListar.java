package org.poo.controlador.bus;

import java.util.List;
import org.poo.dto.BusDto;
import org.poo.servicio.BusServicio;

public class BusControladorListar {

    public static List<BusDto> obtenerBuses() {
        BusServicio miDao = new BusServicio();
        List<BusDto> arreglo = miDao.selectFrom();
        return arreglo;
    }

    public static List<BusDto> obtenerBusesActivos() {
        BusServicio miDao = new BusServicio();
        List<BusDto> arreglo = miDao.selectFromWhereActivos();
        return arreglo;
    }

    public static int obtenerCantidadBuses() {
        BusServicio miDao = new BusServicio();
        int cantidad = miDao.numRows();
        return cantidad;
    }
}
