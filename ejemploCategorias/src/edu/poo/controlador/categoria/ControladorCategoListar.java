package edu.poo.controlador.categoria;

import java.util.List;

import edu.poo.modelo.Categoria;
import edu.poo.persistencia.DAOCategorias;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ControladorCategoListar {

    public static ObservableList<Categoria> cargarDatos() {
        DAOCategorias miDAO = new DAOCategorias();
        List<Categoria> arreglo = miDAO.SelectFrom();

        ObservableList<Categoria> datosTabla = FXCollections.observableArrayList(arreglo);
        return datosTabla;
    }

    public static List<Categoria> arregloCategoriaActivos() {
        DAOCategorias miDAO = new DAOCategorias();
        List<Categoria> arreglo = miDAO.SelectFromActivos();

        return arreglo;
    }

    public static List<Categoria> arregloCategoriaTodos() {
        DAOCategorias miDAO = new DAOCategorias();
        List<Categoria> arreglo = miDAO.SelectFromTodos();

        return arreglo;
    }
}
