package org.poo.servicio;

import com.poo.persistence.NioFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.poo.api.ApiOperacionBD;
import org.poo.dto.RutaDto;
import org.poo.modelo.Ruta;
import org.poo.recurso.constante.Persistencia;
import org.poo.recurso.utilidad.GestorImagen;

public class RutaServicio implements ApiOperacionBD<RutaDto, Integer> {

    private NioFile miArchivo;
    private String nombrePersistencia;

    public RutaServicio() {
        nombrePersistencia = Persistencia.NOMBRE_RUTA;
        try {
            miArchivo = new NioFile(nombrePersistencia);
        } catch (IOException ex) {
            Logger.getLogger(RutaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getSerial() {
        int id = 0;
        try {
            id = miArchivo.ultimoCodigo() + 1;
        } catch (IOException ex) {
            Logger.getLogger(RutaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    @Override
    public RutaDto insertInto(RutaDto dto, String ruta) {
        Ruta objRuta = new Ruta();

        objRuta.setIdRuta(getSerial());
        objRuta.setNombreRuta(dto.getNombreRuta());
        objRuta.setCiudadOrigenRuta(dto.getCiudadOrigenRuta());
        objRuta.setCiudadDestinoRuta(dto.getCiudadDestinoRuta());
        objRuta.setDistanciaKmRuta(dto.getDistanciaKmRuta());
        objRuta.setDuracionHorasRuta(dto.getDuracionHorasRuta());
        objRuta.setEstadoRuta(dto.getEstadoRuta());
        objRuta.setNombreImagenPublicoRuta(dto.getNombreImagenPublicoRuta());
        objRuta.setNombreImagenPrivadoRuta(GestorImagen.grabarLaImagen(ruta));

        String filaGrabar = objRuta.getIdRuta() + Persistencia.SEPARADOR_COLUMNAS
                + objRuta.getNombreRuta() + Persistencia.SEPARADOR_COLUMNAS
                + objRuta.getCiudadOrigenRuta() + Persistencia.SEPARADOR_COLUMNAS
                + objRuta.getCiudadDestinoRuta() + Persistencia.SEPARADOR_COLUMNAS
                + objRuta.getDistanciaKmRuta() + Persistencia.SEPARADOR_COLUMNAS
                + objRuta.getDuracionHorasRuta() + Persistencia.SEPARADOR_COLUMNAS
                + objRuta.getEstadoRuta() + Persistencia.SEPARADOR_COLUMNAS
                + objRuta.getNombreImagenPublicoRuta() + Persistencia.SEPARADOR_COLUMNAS
                + objRuta.getNombreImagenPrivadoRuta();

        if (miArchivo.agregarRegistro(filaGrabar)) {
            dto.setIdRuta(objRuta.getIdRuta());
            return dto;
        }

        return null;
    }

    @Override
    public List<RutaDto> selectFrom() {
        List<RutaDto> arregloRuta = new ArrayList<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

                int codRuta = Integer.parseInt(columnas[0].trim());
                String nomRuta = columnas[1].trim();
                String ciudadOrigen = columnas[2].trim();
                String ciudadDestino = columnas[3].trim();
                Double distancia = Double.parseDouble(columnas[4].trim());
                Integer duracion = Integer.parseInt(columnas[5].trim());
                Boolean estado = Boolean.valueOf(columnas[6].trim());
                String npub = columnas[7].trim();
                String nocu = columnas[8].trim();

                RutaDto dto = new RutaDto(codRuta, nomRuta, ciudadOrigen, ciudadDestino,
                        distancia, duracion, estado, npub, nocu);

                arregloRuta.add(dto);

            } catch (NumberFormatException error) {
                Logger.getLogger(RutaServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arregloRuta;
    }

    @Override
    public List<RutaDto> selectFromWhereActivos() {
        List<RutaDto> arregloRuta = new ArrayList<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

                int codRuta = Integer.parseInt(columnas[0].trim());
                String nomRuta = columnas[1].trim();
                String ciudadOrigen = columnas[2].trim();
                String ciudadDestino = columnas[3].trim();
                Double distancia = Double.parseDouble(columnas[4].trim());
                Integer duracion = Integer.parseInt(columnas[5].trim());
                Boolean estado = Boolean.valueOf(columnas[6].trim());
                String npub = columnas[7].trim();
                String nocu = columnas[8].trim();

                if (Boolean.TRUE.equals(estado)) {
                    RutaDto dto = new RutaDto(codRuta, nomRuta, ciudadOrigen, ciudadDestino,
                            distancia, duracion, estado, npub, nocu);

                    arregloRuta.add(dto);
                }
            } catch (NumberFormatException error) {
                Logger.getLogger(RutaServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arregloRuta;
    }

    @Override
    public int numRows() {
        int cantidad = 0;
        try {
            cantidad = miArchivo.cantidadFilas();
        } catch (IOException ex) {
            Logger.getLogger(RutaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cantidad;
    }

    @Override
    public Boolean deleteFrom(Integer codigo) {
        Boolean correcto = false;
        try {
            List<String> arreglo = miArchivo.borrarFilaPosicion(codigo);
            if (!arreglo.isEmpty()) {
                correcto = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(RutaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }

    @Override
    public RutaDto getOne(Integer codigo) {
        RutaDto dto = null;
        List<String> arregloDatos = miArchivo.obtenerDatos();

        if (codigo < 0 || codigo >= arregloDatos.size()) {
            return null;
        }

        try {
            String cadena = arregloDatos.get(codigo);
            cadena = cadena.replace("@", "");
            String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

            int codRuta = Integer.parseInt(columnas[0].trim());
            String nomRuta = columnas[1].trim();
            String ciudadOrigen = columnas[2].trim();
            String ciudadDestino = columnas[3].trim();
            Double distancia = Double.parseDouble(columnas[4].trim());
            Integer duracion = Integer.parseInt(columnas[5].trim());
            Boolean estado = Boolean.valueOf(columnas[6].trim());
            String npub = columnas[7].trim();
            String nocu = columnas[8].trim();

            dto = new RutaDto(codRuta, nomRuta, ciudadOrigen, ciudadDestino,
                    distancia, duracion, estado, npub, nocu);

        } catch (NumberFormatException error) {
            Logger.getLogger(RutaServicio.class.getName()).log(Level.SEVERE, null, error);
        }

        return dto;
    }

    @Override
    public RutaDto updateSet(Integer codigo, RutaDto objeto, String ruta) {
        try {
            String cadena, nocu;
            List<String> arregloDatos;

            cadena = objeto.getIdRuta() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getNombreRuta() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getCiudadOrigenRuta() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getCiudadDestinoRuta() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getDistanciaKmRuta() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getDuracionHorasRuta() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getEstadoRuta() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getNombreImagenPublicoRuta() + Persistencia.SEPARADOR_COLUMNAS;

            if (ruta.isBlank()) {
                cadena = cadena + objeto.getNombreImagenPrivadoRuta();
            } else {
                nocu = GestorImagen.grabarLaImagen(ruta);
                cadena = cadena + nocu;
                
                arregloDatos = miArchivo.borrarFilaPosicion(codigo);
                if (!arregloDatos.isEmpty()) {
                    String nomOculto = arregloDatos.get(arregloDatos.size() - 1);
                    String nombreBorrar = Persistencia.RUTA_IMAGENES 
                            + Persistencia.SEPARADOR_CARPETAS + nomOculto;
                    java.nio.file.Path rutaBorrar = java.nio.file.Paths.get(nombreBorrar);
                    java.nio.file.Files.deleteIfExists(rutaBorrar);
                }
            }

            if (miArchivo.actualizaFilaPosicion(codigo, cadena)) {
                return objeto;
            }
        } catch (IOException ex) {
            Logger.getLogger(RutaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
