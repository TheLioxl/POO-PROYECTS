package org.poo.controlador.empresa;

import org.poo.dto.EmpresaDto;
import org.poo.servicio.EmpresaServicio;

public class EmpresaControladorUna {

    public static EmpresaDto obtenerEmpresa(int indice) {
        EmpresaDto obj;

        EmpresaServicio miDAO = new EmpresaServicio();
        obj = miDAO.getOne(indice);

        return obj;
    }
}
