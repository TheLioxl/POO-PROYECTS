package edu.poo.controlador.categoria;

import edu.poo.modelo.Categoria;
import edu.poo.persistencia.DAOCategorias;

public class ControladorCategoUna {

    public static Categoria obtenerCategoria(int indice) {
        Categoria obj;

        DAOCategorias miDAO = new DAOCategorias();
        obj = miDAO.getOne(indice);

        return obj;
    }

}
