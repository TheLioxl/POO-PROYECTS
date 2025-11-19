package org.poo.controlador.empresa;

import org.poo.dto.EmpresaDto;
import org.poo.servicio.EmpresaServicio;

public class EmpresaControladorGrabar {

    public static Boolean crearEmpresa(EmpresaDto dto, String rutaDeLaImagen) {
        Boolean correcto = false;
        EmpresaServicio empresaServicio = new EmpresaServicio();
        EmpresaDto dtoRespuesta;
        dtoRespuesta = empresaServicio.insertInto(dto, rutaDeLaImagen);

        if (dtoRespuesta != null) {
            correcto = true;
        }

        return correcto;
    }
}
