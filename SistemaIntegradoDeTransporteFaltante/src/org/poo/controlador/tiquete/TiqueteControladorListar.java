package org.poo.controlador.tiquete;

import java.util.List;
import org.poo.dto.TiqueteDto;
import org.poo.servicio.TiqueteServicio;

public class TiqueteControladorListar {

    public static List<TiqueteDto> obtenerTiquetes() {
        TiqueteServicio miDao = new TiqueteServicio();
        List<TiqueteDto> arreglo = miDao.selectFrom();
        return arreglo;
    }

    public static List<TiqueteDto> obtenerTiquetesActivos() {
        TiqueteServicio miDao = new TiqueteServicio();
        List<TiqueteDto> arreglo = miDao.selectFromWhereActivos();
        return arreglo;
    }

    public static int obtenerCantidadTiquetes() {
        TiqueteServicio miDao = new TiqueteServicio();
        int cantidad = miDao.numRows();
        return cantidad;
    }
}