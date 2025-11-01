package org.poo.controlador.pelicula;

import java.util.List;
import org.poo.dto.PeliculaDto;
import org.poo.servicio.PeliculaServicio;

public class PeliculaControladorListar {

    public static List<PeliculaDto> obtenerPeliculas() {
        PeliculaServicio miDao = new PeliculaServicio();
        List<PeliculaDto> arreglo = miDao.selectFrom();
        return arreglo;
    }

    public static int obtenerCantidadPeliculas() {
        PeliculaServicio miDao = new PeliculaServicio();
        int cantidad = miDao.numRows();
        return cantidad;
    }
}