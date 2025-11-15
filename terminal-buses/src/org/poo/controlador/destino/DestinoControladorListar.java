package org.poo.controlador.destino;

import java.util.List;
import org.poo.dto.DestinoDto;
import org.poo.servicio.DestinoServicio;

public class DestinoControladorListar {

    public static List<DestinoDto> obtenerDestinos() {
        DestinoServicio miDao = new DestinoServicio();
        List<DestinoDto> arreglo = miDao.selectFrom();
        return arreglo;
    }

    public static List<DestinoDto> obtenerDestinosActivos() {
        DestinoServicio miDao = new DestinoServicio();
        List<DestinoDto> arreglo = miDao.selectFromWhereActivos();
        return arreglo;
    }

    public static int obtenerCantidadDestinos() {
        DestinoServicio miDao = new DestinoServicio();
        int cantidad = miDao.numRows();
        return cantidad;
    }
}
