package org.poo.controlador.empresa;

import java.util.List;
import org.poo.dto.EmpresaDto;
import org.poo.servicio.EmpresaServicio;

public class EmpresaControladorListar {

    public static List<EmpresaDto> obtenerEmpresas() {
        EmpresaServicio miDao = new EmpresaServicio();
        List<EmpresaDto> arreglo = miDao.selectFrom();
        return arreglo;
    }

    public static List<EmpresaDto> obtenerEmpresasActivas() {
        EmpresaServicio miDao = new EmpresaServicio();
        List<EmpresaDto> arreglo = miDao.selectFromWhereActivos();
        return arreglo;
    }

    public static int obtenerCantidadEmpresas() {
        EmpresaServicio miDao = new EmpresaServicio();
        int cantidad = miDao.numRows();
        return cantidad;
    }
}
