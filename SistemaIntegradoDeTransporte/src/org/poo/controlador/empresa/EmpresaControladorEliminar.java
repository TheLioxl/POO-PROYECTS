package org.poo.controlador.empresa;

import org.poo.servicio.EmpresaServicio;

public class EmpresaControladorEliminar {

    public static Boolean borrar(int codigo) {
        Boolean correcto;
        EmpresaServicio empresaServicio = new EmpresaServicio();
        correcto = empresaServicio.deleteFrom(codigo);
        return correcto;
    }
}
