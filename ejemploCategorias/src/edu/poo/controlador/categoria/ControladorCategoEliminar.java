package edu.poo.controlador.categoria;

import edu.poo.persistencia.DAOCategorias;

public class ControladorCategoEliminar {

    public static boolean borrarRegistro(int indiceExterno) {
        boolean correcto;

        DAOCategorias miDao = new DAOCategorias();
        correcto = miDao.deleteFrom(indiceExterno);

        return correcto;
    }
}
