package org.poo.controlador.autor;

import java.util.List;
import org.poo.dto.AutorDto;
import org.poo.servicio.AutorServicio;

public class AutorControladorListar {

    public static List<AutorDto> obtenerAutores() {
        AutorServicio miDao = new AutorServicio();
        List<AutorDto> arreglo = miDao.selectFrom();
        return arreglo;
    }

    public static int obtenerCantidadAutores() {
        AutorServicio miDao = new AutorServicio();
        int cantidad = miDao.numRows();
        return cantidad;
    }
}