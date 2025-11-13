package edu.poo.controlador.categoria;

import edu.poo.modelo.Categoria;
import edu.poo.persistencia.DAOCategorias;

public class ControladorCategoGrabar {

    public static boolean grabar(String nomCat, Boolean estCat, String nomIma, String rutaImagen) {
        boolean correcto = false;
        DAOCategorias miDAO = new DAOCategorias();

        int consecutivo = miDAO.getSerial();
        Categoria miObj = new Categoria(consecutivo, nomCat, estCat, 0, nomIma, "");

        if (miDAO.insertInto(miObj, rutaImagen)) {
            correcto = true;
        }
        return correcto;
    }
}
