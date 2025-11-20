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
        int contador = 0;
        RutaDto objListo = new RutaDto();
        List<RutaDto> arrEmpresas = selectFrom();

        for (RutaDto objEmpresa : arrEmpresas) {
            if (contador == codigo) {
                objListo = objEmpresa;
                break;
            }
            contador++;
        }
        return objListo;
    }

    @Override
    public RutaDto updateSet(Integer codigo, RutaDto dto, String ruta) {
        List<String> arregloDatos = miArchivo.obtenerDatos();
        int posicionAEditar = -1;
        
        for (int i = 0; i < arregloDatos.size(); i++) {
            String cadena = arregloDatos.get(i);
            cadena = cadena.replace("@", "");
            String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);
            
            int codRuta = Integer.parseInt(columnas[0].trim());
            
            if (codRuta == codigo) {
                posicionAEditar = i;
                break;
            }
        }
        
        if (posicionAEditar != -1) {
            String imagenPrivada = dto.getNombreImagenPrivadoRuta();
            
            if (!ruta.isEmpty()) {
                imagenPrivada = GestorImagen.grabarLaImagen(ruta);
            }
            
            String filaActualizada = dto.getIdRuta() + Persistencia.SEPARADOR_COLUMNAS
                    + dto.getNombreRuta() + Persistencia.SEPARADOR_COLUMNAS
                    + dto.getCiudadOrigenRuta() + Persistencia.SEPARADOR_COLUMNAS
                    + dto.getCiudadDestinoRuta() + Persistencia.SEPARADOR_COLUMNAS
                    + dto.getDistanciaKmRuta() + Persistencia.SEPARADOR_COLUMNAS
                    + dto.getDuracionHorasRuta() + Persistencia.SEPARADOR_COLUMNAS
                    + dto.getEstadoRuta() + Persistencia.SEPARADOR_COLUMNAS
                    + dto.getNombreImagenPublicoRuta() + Persistencia.SEPARADOR_COLUMNAS
                    + imagenPrivada;
            
            try {
                if (miArchivo.actualizaFilaPosicion(posicionAEditar, filaActualizada)) {
                    dto.setNombreImagenPrivadoRuta(imagenPrivada);
                    return dto;
                }
            } catch (IOException ex) {
                Logger.getLogger(RutaServicio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return null;
    }
}
