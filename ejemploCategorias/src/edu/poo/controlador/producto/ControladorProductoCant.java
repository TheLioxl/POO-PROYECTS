package edu.poo.controlador.producto;

import edu.poo.persistencia.DAOProducto;

public class ControladorProductoCant {

    public static int obtenerCantidadCarros() {
        DAOProducto miDaoProducto;

        miDaoProducto = new DAOProducto();
        return miDaoProducto.numRows();
    }

}
