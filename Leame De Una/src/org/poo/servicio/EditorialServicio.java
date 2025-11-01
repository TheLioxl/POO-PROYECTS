package org.poo.servicio;

import com.poo.persistence.NioFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.poo.api.ApiOperacionBD;
import org.poo.dto.EditorialDto;
import org.poo.modelo.Editorial;
import org.poo.recurso.constante.Persistencia;

public class EditorialServicio implements ApiOperacionBD<EditorialDto, Integer> {

    private NioFile miArchivo;
    private final String nombrePersistencia;

    public EditorialServicio() {
        nombrePersistencia = Persistencia.NOMBRE_EDITORIAL;
        try {
            miArchivo = new NioFile(nombrePersistencia);
        } catch (IOException ex) {
            Logger.getLogger(EditorialServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getSerial() {
        int id = 0;
        try {
            id = miArchivo.ultimoCodigo() + 1;
        } catch (IOException ex) {
            Logger.getLogger(EditorialServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    @Override
    public EditorialDto insertInto(EditorialDto dto, String ruta) {
        Editorial objEditorial = new Editorial();
        objEditorial.setIdEditorial(getSerial());
        objEditorial.setNombreEditorial(dto.getNombreEditorial());
        objEditorial.setPaisEditorial(dto.getPaisEditorial());
        objEditorial.setFormatoEditorial(dto.getFormatoEditorial());

        String filaGrabar = objEditorial.getIdEditorial() + Persistencia.SEPARADOR_COLUMNAS
                + objEditorial.getNombreEditorial() + Persistencia.SEPARADOR_COLUMNAS
                + objEditorial.getPaisEditorial() + Persistencia.SEPARADOR_COLUMNAS
                + objEditorial.getFormatoEditorial();

        if (miArchivo.agregarRegistro(filaGrabar)) {
            dto.setIdEditorial(objEditorial.getIdEditorial());
            return dto;
        }

        return null;
    }

    @Override
    public List<EditorialDto> selectFrom() {
        List<EditorialDto> arregloEditorial = new ArrayList<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        LibroServicio libroServicio = new LibroServicio();
        Map<Integer, Integer> arrCantLibros = libroServicio.librosPorEditorial();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

                int codEditorial = Integer.parseInt(columnas[0].trim());
                String nomEditorial = columnas[1].trim();
                String paisEditorial = columnas[2].trim();
                Short formatoEditorial = Short.valueOf(columnas[3].trim());

                Short cantLibros = arrCantLibros.getOrDefault(codEditorial, 0).shortValue();

                arregloEditorial.add(new EditorialDto(codEditorial, nomEditorial, paisEditorial, formatoEditorial, cantLibros));

            } catch (NumberFormatException error) {
                Logger.getLogger(EditorialServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arregloEditorial;
    }

    @Override
    public List<EditorialDto> selectFromWhereActivos() {
        return selectFrom();
    }

    @Override
    public int numRows() {
        int cantidad = 0;
        try {
            cantidad = miArchivo.cantidadFilas();
        } catch (IOException ex) {
            Logger.getLogger(EditorialServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cantidad;
    }

    @Override
    public Boolean deleteFrom(Integer codigo) {
        Boolean correcto = false;
        try {
            List<String> arreglo;
            arreglo = miArchivo.borrarFilaPosicion(codigo);
            if (!arreglo.isEmpty()) {
                correcto = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(EditorialServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }

    @Override
    public EditorialDto updateSet(Integer codigo, EditorialDto objeto, String ruta) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EditorialDto getOne(Integer codigo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}