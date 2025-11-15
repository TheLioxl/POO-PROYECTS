package org.poo.controlador.viaje;

import java.util.List;
import org.poo.dto.ViajeDto;
import org.poo.servicio.ViajeServicio;

public class ViajeControladorListar {

    public static List<ViajeDto> obtenerViajes() {
        ViajeServicio miDao = new ViajeServicio();
        List<ViajeDto> arreglo = miDao.selectFrom();
        return arreglo;
    }

    public static List<ViajeDto> obtenerViajesActivos() {
        ViajeServicio miDao = new ViajeServicio();
        List<ViajeDto> arreglo = miDao.selectFromWhereActivos();
        return arreglo;
    }

    public static int obtenerCantidadViajes() {
        ViajeServicio miDao = new ViajeServicio();
        int cantidad = miDao.numRows();
        return cantidad;
    }
}