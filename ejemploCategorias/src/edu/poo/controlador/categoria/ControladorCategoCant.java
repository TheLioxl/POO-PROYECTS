package edu.poo.controlador.categoria;

import edu.poo.persistencia.DAOCategorias;

public class ControladorCategoCant {

    public static int obtener() {
        int cantidad;

        DAOCategorias miDAO = new DAOCategorias();
        cantidad = miDAO.numRows();

        return cantidad;
    }

}
