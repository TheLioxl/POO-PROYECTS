package org.poo.servicio;

import com.poo.persistence.NioFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.poo.api.ApiOperacionBD;
import org.poo.dto.GeneroDto;
import org.poo.modelo.Genero;
import org.poo.recurso.constante.Persistencia;
import org.poo.recurso.utilidad.GestorImagen;

public class GeneroServicio implements ApiOperacionBD<GeneroDto, Integer> {

    private NioFile miArchivo;
    private String nombrePersistencia;

    public GeneroServicio() {
        nombrePersistencia = Persistencia.NOMBRE_GENERO;
        try {
            miArchivo = new NioFile(nombrePersistencia);
        } catch (IOException ex) {
            Logger.getLogger(GeneroServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getSerial() {
        int id = 0;
        try {
            id = miArchivo.ultimoCodigo() + 1;
        } catch (IOException ex) {
            Logger.getLogger(GeneroServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    @Override
    public GeneroDto insertInto(GeneroDto dto, String ruta) {
        Genero objGenero = new Genero();
        objGenero.setIdGenero(getSerial());
        objGenero.setNombreGenero(dto.getNombreGenero());
        objGenero.setEstadoGenero(dto.getEstadoGenero());
        
        // Guardar nombres de imagen
        objGenero.setNombreImagenPublicoGenero(dto.getNombreImagenPublicoGenero());
        objGenero.setNombreImagenPrivadoGenero(GestorImagen.grabarLaImagen(ruta));

        String filaGrabar = objGenero.getIdGenero() + Persistencia.SEPARADOR_COLUMNAS
                + objGenero.getNombreGenero() + Persistencia.SEPARADOR_COLUMNAS
                + objGenero.getEstadoGenero() + Persistencia.SEPARADOR_COLUMNAS
                + objGenero.getNombreImagenPublicoGenero() + Persistencia.SEPARADOR_COLUMNAS
                + objGenero.getNombreImagenPrivadoGenero();

        if (miArchivo.agregarRegistro(filaGrabar)) {
            dto.setIdGenero(objGenero.getIdGenero());
            return dto;
        }

        return null;
    }

    @Override
    public List<GeneroDto> selectFrom() {
        List<GeneroDto> arregloGenero = new ArrayList<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        PeliculaServicio peliculaServicio = new PeliculaServicio();
        Map<Integer, Integer> arrCantPelis = peliculaServicio.peliculasPorGenero();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

                int codGenero = Integer.parseInt(columnas[0].trim());
                String nomGenero = columnas[1].trim();
                Boolean estGenero = Boolean.valueOf(columnas[2].trim());
                
                // Leer nombres de imagen si existen
                String imagenPublico = columnas.length > 3 ? columnas[3].trim() : "";
                String imagenPrivado = columnas.length > 4 ? columnas[4].trim() : "";

                Short cantPelis = arrCantPelis.getOrDefault(codGenero, 0).shortValue();

                GeneroDto generoDto = new GeneroDto(codGenero, nomGenero, estGenero, cantPelis);
                generoDto.setNombreImagenPublicoGenero(imagenPublico);
                generoDto.setNombreImagenPrivadoGenero(imagenPrivado);
                
                arregloGenero.add(generoDto);

            } catch (NumberFormatException error) {
                Logger.getLogger(GeneroServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arregloGenero;
    }

    @Override
    public List<GeneroDto> selectFromWhereActivos() {
        List<GeneroDto> arregloGenero = new ArrayList<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        PeliculaServicio peliculaServicio = new PeliculaServicio();
        Map<Integer, Integer> arrCantPelis = peliculaServicio.peliculasPorGenero();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

                int codGenero = Integer.parseInt(columnas[0].trim());
                String nomGenero = columnas[1].trim();
                Boolean estGenero = Boolean.valueOf(columnas[2].trim());
                
                String imagenPublico = columnas.length > 3 ? columnas[3].trim() : "";
                String imagenPrivado = columnas.length > 4 ? columnas[4].trim() : "";

                Short cantPelis = arrCantPelis.getOrDefault(codGenero, 0).shortValue();

                if (Boolean.TRUE.equals(estGenero)) {
                    GeneroDto generoDto = new GeneroDto(codGenero, nomGenero, estGenero, cantPelis);
                    generoDto.setNombreImagenPublicoGenero(imagenPublico);
                    generoDto.setNombreImagenPrivadoGenero(imagenPrivado);
                    arregloGenero.add(generoDto);
                }

            } catch (NumberFormatException error) {
                Logger.getLogger(GeneroServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arregloGenero;
    }

    @Override
    public int numRows() {
        int cantidad = 0;
        try {
            cantidad = miArchivo.cantidadFilas();
        } catch (IOException ex) {
            Logger.getLogger(GeneroServicio.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(GeneroServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }

    @Override
    public GeneroDto updateSet(Integer codigo, GeneroDto objeto, String ruta) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GeneroDto getOne(Integer codigo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}