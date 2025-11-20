package org.poo.controlador.terminal;

import java.util.List;
import org.poo.dto.TerminalDto;
import org.poo.servicio.TerminalServicio;

public class TerminalControladorListar {

    public static List<TerminalDto> obtenerTerminales() {
        TerminalServicio miDao = new TerminalServicio();
        List<TerminalDto> arreglo = miDao.selectFrom();
        return arreglo;
    }

    public static List<TerminalDto> obtenerTerminalesActivos() {
        TerminalServicio miDao = new TerminalServicio();
        List<TerminalDto> arreglo = miDao.selectFromWhereActivos();
        return arreglo;
    }

    public static int obtenerCantidadTerminales() {
        TerminalServicio miDao = new TerminalServicio();
        int cantidad = miDao.numRows();
        return cantidad;
    }
}
