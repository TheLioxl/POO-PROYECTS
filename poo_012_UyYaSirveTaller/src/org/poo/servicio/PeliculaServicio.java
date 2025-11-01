package org.poo.servicio;

import com.poo.persistence.NioFile;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.poo.api.ApiOperacionBD;
import org.poo.dto.GeneroDto;
import org.poo.dto.PeliculaDto;
import org.poo.modelo.Genero;
import org.poo.modelo.Pelicula;
import org.poo.recurso.constante.Persistencia;

public class PeliculaServicio implements ApiOperacionBD<PeliculaDto, Integer> {

    private NioFile miArchivo;
    private String nombrePersistencia;

    public PeliculaServicio() {
        nombrePersistencia = Persistencia.NOMBRE_PELICULA;
        try {
            miArchivo = new NioFile(nombrePersistencia);
        } catch (IOException ex) {
            Logger.getLogger(PeliculaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getSerial() {
        int id = 0;
        try {
            id = miArchivo.ultimoCodigo() + 1;
        } catch (IOException ex) {
            Logger.getLogger(PeliculaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    @Override
    public PeliculaDto insertInto(PeliculaDto dto, String ruta) {
        Genero objGenero = new Genero(
                dto.getGeneroPelicula().getIdGenero(),
                dto.getGeneroPelicula().getNombreGenero(),
                dto.getGeneroPelicula().getEstadoGenero(),
                dto.getGeneroPelicula().getCantidadPeliculasGenero()
        );

        Pelicula objPelicula = new Pelicula();
        objPelicula.setIdPelicula(getSerial());
        objPelicula.setNombrePelicula(dto.getNombrePelicula());
        objPelicula.setProtagonistaPelicula(dto.getProtagonistaPelicula());
        objPelicula.setGeneroPelicula(objGenero);
        objPelicula.setPresupuestoPelicula(dto.getPresupuestoPelicula());
        objPelicula.setEstadoPelicula(dto.getEstadoPelicula());

        String filaGrabar = objPelicula.getIdPelicula() + Persistencia.SEPARADOR_COLUMNAS
                + objPelicula.getNombrePelicula() + Persistencia.SEPARADOR_COLUMNAS
                + objPelicula.getProtagonistaPelicula() + Persistencia.SEPARADOR_COLUMNAS
                + objPelicula.getGeneroPelicula().getIdGenero() + Persistencia.SEPARADOR_COLUMNAS
                + BigDecimal.valueOf(objPelicula.getPresupuestoPelicula()).toPlainString() + Persistencia.SEPARADOR_COLUMNAS
                + objPelicula.getEstadoPelicula();

        if (miArchivo.agregarRegistro(filaGrabar)) {
            dto.setIdPelicula(objPelicula.getIdPelicula());
            return dto;
        }

        return null;
    }

    public Map<Integer, Integer> peliculasPorGenero() {
        Map<Integer, Integer> arrCantidades = new HashMap<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

                int idGenero = Integer.parseInt(columnas[3].trim());
                arrCantidades.put(idGenero, arrCantidades.getOrDefault(idGenero, 0) + 1);

            } catch (NumberFormatException error) {
                Logger.getLogger(PeliculaServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arrCantidades;
    }

    @Override
    public List<PeliculaDto> selectFrom() {
        GeneroServicio generoServicio = new GeneroServicio();
        List<GeneroDto> arrGeneros = generoServicio.selectFrom();

        List<PeliculaDto> arregloPeliculaDtos = new ArrayList<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

                if (columnas.length >= 6) {
                    Integer codigoPelicula = Integer.valueOf(columnas[0].trim());
                    String nombre = columnas[1].trim();
                    String protagonista = columnas[2].trim();
                    Integer codigoGenero = Integer.valueOf(columnas[3].trim());
                    Double presupuesto = Double.valueOf(columnas[4].trim());
                    Boolean estado = Boolean.valueOf(columnas[5].trim());

                    PeliculaDto objPeliculaDto = new PeliculaDto();
                    objPeliculaDto.setIdPelicula(codigoPelicula);
                    objPeliculaDto.setNombrePelicula(nombre);
                    objPeliculaDto.setProtagonistaPelicula(protagonista);
                    objPeliculaDto.setPresupuestoPelicula(presupuesto);
                    objPeliculaDto.setEstadoPelicula(estado);

                    objPeliculaDto.setGeneroPelicula(obtenerGeneroCompleto(codigoGenero, arrGeneros));

                    arregloPeliculaDtos.add(objPeliculaDto);
                }
            } catch (NumberFormatException error) {
                Logger.getLogger(PeliculaServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arregloPeliculaDtos;
    }

    private GeneroDto obtenerGeneroCompleto(Integer codigoGenero, List<GeneroDto> arrGeneros) {
        for (GeneroDto generoExterno : arrGeneros) {
            if (Objects.equals(codigoGenero, generoExterno.getIdGenero())) {
                return generoExterno;
            }
        }
        return null;
    }

    @Override
    public List<PeliculaDto> selectFromWhereActivos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int numRows() {
        int cantidad = 0;
        try {
            cantidad = miArchivo.cantidadFilas();
        } catch (IOException ex) {
            Logger.getLogger(PeliculaServicio.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(PeliculaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }

    @Override
    public PeliculaDto updateSet(Integer codigo, PeliculaDto objeto, String ruta) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PeliculaDto getOne(Integer codigo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
