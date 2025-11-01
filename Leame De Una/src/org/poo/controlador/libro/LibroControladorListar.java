package org.poo.controlador.libro;

import java.util.List;
import org.poo.dto.LibroDto;
import org.poo.servicio.LibroServicio;

public class LibroControladorListar {

    public static List<LibroDto> obtenerLibros() {
        LibroServicio miDao = new LibroServicio();
        List<LibroDto> arreglo = miDao.selectFrom();
        return arreglo;
    }

    public static int obtenerCantidadLibros() {
        LibroServicio miDao = new LibroServicio();
        int cantidad = miDao.numRows();
        return cantidad;
    }
}