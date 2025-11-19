package org.poo.controlador.pasajero;

import java.util.List;
import org.poo.dto.PasajeroDto;
import org.poo.servicio.PasajeroServicio;

public class PasajeroControladorListar {

    public static List<PasajeroDto> obtenerPasajeros() {
        PasajeroServicio miDao = new PasajeroServicio();
        List<PasajeroDto> arreglo = miDao.selectFrom();
        return arreglo;
    }

    public static int obtenerCantidadPasajeros() {
        PasajeroServicio miDao = new PasajeroServicio();
        int cantidad = miDao.numRows();
        return cantidad;
    }
}
