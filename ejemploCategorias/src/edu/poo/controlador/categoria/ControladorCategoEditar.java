package edu.poo.controlador.categoria;

import edu.poo.modelo.Categoria;
import edu.poo.persistencia.DAOCategorias;

public class ControladorCategoEditar {

    public static boolean actualizar(int indiceExterno, Categoria objExterno, String rutaImagen) {
        boolean correcto;

        DAOCategorias miDao = new DAOCategorias();

        correcto = miDao.updateSet(indiceExterno, objExterno, rutaImagen);
        return correcto;
    }
}
