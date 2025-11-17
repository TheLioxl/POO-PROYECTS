package org.poo.controlador.empresa;

import org.poo.dto.EmpresaDto;
import org.poo.servicio.EmpresaServicio;

public class EmpresaControladorEditar {

    public static boolean actualizar(int indiceExterno, EmpresaDto objExterno, String rutaImagen) {
        boolean correcto;

        EmpresaServicio miDao = new EmpresaServicio();
        EmpresaDto resultado = miDao.updateSet(indiceExterno, objExterno, rutaImagen);
        
        correcto = (resultado != null);
        return correcto;
    }
}
