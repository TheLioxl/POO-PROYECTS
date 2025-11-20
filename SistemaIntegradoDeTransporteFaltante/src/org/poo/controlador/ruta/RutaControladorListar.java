package org.poo.controlador.ruta;

import java.util.List;
import org.poo.dto.RutaDto;
import org.poo.servicio.RutaServicio;

public class RutaControladorListar {

    public static List<RutaDto> obtenerRutas() {
        RutaServicio miDao = new RutaServicio();
        List<RutaDto> arreglo = miDao.selectFrom();
        return arreglo;
    }

    public static List<RutaDto> obtenerRutasActivas() {
        RutaServicio miDao = new RutaServicio();
        List<RutaDto> arreglo = miDao.selectFromWhereActivos();
        return arreglo;
    }

    public static int obtenerCantidadRutas() {
        RutaServicio miDao = new RutaServicio();
        int cantidad = miDao.numRows();
        return cantidad;
    }
}
