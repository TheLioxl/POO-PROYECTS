package org.poo.servicio;

import com.poo.persistence.NioFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.poo.api.ApiOperacionBD;
import org.poo.dto.AutorDto;
import org.poo.modelo.Autor;
import org.poo.recurso.constante.Persistencia;

public class AutorServicio implements ApiOperacionBD<AutorDto, Integer> {

    private NioFile miArchivo;
    private final String nombrePersistencia;

    public AutorServicio() {
        nombrePersistencia = Persistencia.NOMBRE_AUTOR;
        try {
            miArchivo = new NioFile(nombrePersistencia);
        } catch (IOException ex) {
            Logger.getLogger(AutorServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getSerial() {
        int id = 0;
        try {
            id = miArchivo.ultimoCodigo() + 1;
        } catch (IOException ex) {
            Logger.getLogger(AutorServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    @Override
    public AutorDto insertInto(AutorDto dto, String ruta) {
        Autor objAutor = new Autor();
        objAutor.setIdAutor(getSerial());
        objAutor.setNombreAutor(dto.getNombreAutor());
        objAutor.setGeneroAutor(dto.getGeneroAutor());

        String filaGrabar = objAutor.getIdAutor() + Persistencia.SEPARADOR_COLUMNAS
                + objAutor.getNombreAutor() + Persistencia.SEPARADOR_COLUMNAS
                + objAutor.getGeneroAutor();

        if (miArchivo.agregarRegistro(filaGrabar)) {
            dto.setIdAutor(objAutor.getIdAutor());
            return dto;
        }

        return null;
    }

    @Override
    public List<AutorDto> selectFrom() {
        List<AutorDto> arregloAutor = new ArrayList<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        LibroServicio libroServicio = new LibroServicio();
        Map<Integer, Integer> arrCantLibros = libroServicio.librosPorAutor();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

                int codAutor = Integer.parseInt(columnas[0].trim());
                String nomAutor = columnas[1].trim();
                Boolean generoAutor = Boolean.valueOf(columnas[2].trim());

                Short cantLibros = arrCantLibros.getOrDefault(codAutor, 0).shortValue();

                arregloAutor.add(new AutorDto(codAutor, nomAutor, generoAutor, cantLibros));

            } catch (NumberFormatException error) {
                Logger.getLogger(AutorServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arregloAutor;
    }

    @Override
    public List<AutorDto> selectFromWhereActivos() {
        return selectFrom();
    }

    @Override
    public int numRows() {
        int cantidad = 0;
        try {
            cantidad = miArchivo.cantidadFilas();
        } catch (IOException ex) {
            Logger.getLogger(AutorServicio.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AutorServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }

    @Override
    public AutorDto updateSet(Integer codigo, AutorDto objeto, String ruta) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public AutorDto getOne(Integer codigo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}