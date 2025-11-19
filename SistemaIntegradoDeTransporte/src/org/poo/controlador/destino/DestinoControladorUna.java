package org.poo.controlador.destino;

import org.poo.dto.DestinoDto;
import org.poo.servicio.DestinoServicio;

public class DestinoControladorUna {

    public static DestinoDto obtenerDestino(int indice) {
        DestinoDto obj;

        DestinoServicio miDAO = new DestinoServicio();
        obj = miDAO.getOne(indice);

        return obj;
    }
}
