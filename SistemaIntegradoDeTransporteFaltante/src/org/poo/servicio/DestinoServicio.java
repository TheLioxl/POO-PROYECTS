package org.poo.servicio;

import com.poo.persistence.NioFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.poo.api.ApiOperacionBD;
import org.poo.dto.DestinoDto;
import org.poo.modelo.Destino;
import org.poo.recurso.constante.Persistencia;
import org.poo.recurso.utilidad.GestorImagen;

public class DestinoServicio implements ApiOperacionBD<DestinoDto, Integer> {

    private NioFile miArchivo;
    private String nombrePersistencia;

    public DestinoServicio() {
        nombrePersistencia = Persistencia.NOMBRE_DESTINO;
        try {
            miArchivo = new NioFile(nombrePersistencia);
        } catch (IOException ex) {
            Logger.getLogger(DestinoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getSerial() {
        int id = 0;
        try {
            id = miArchivo.ultimoCodigo() + 1;
        } catch (IOException ex) {
            Logger.getLogger(DestinoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    @Override
    public DestinoDto insertInto(DestinoDto dto, String ruta) {
        Destino objDestino = new Destino();

        objDestino.setIdDestino(getSerial());
        objDestino.setNombreDestino(dto.getNombreDestino());
        objDestino.setDepartamentoDestino(dto.getDepartamentoDestino());
        objDestino.setDescripcionDestino(dto.getDescripcionDestino());
        objDestino.setEstadoDestino(dto.getEstadoDestino());
        objDestino.setNombreImagenPublicoDestino(dto.getNombreImagenPublicoDestino());
        objDestino.setNombreImagenPrivadoDestino(GestorImagen.grabarLaImagen(ruta));

        String filaGrabar = objDestino.getIdDestino() + Persistencia.SEPARADOR_COLUMNAS
                + objDestino.getNombreDestino() + Persistencia.SEPARADOR_COLUMNAS
                + objDestino.getDepartamentoDestino() + Persistencia.SEPARADOR_COLUMNAS
                + objDestino.getDescripcionDestino() + Persistencia.SEPARADOR_COLUMNAS
                + objDestino.getEstadoDestino() + Persistencia.SEPARADOR_COLUMNAS
                + objDestino.getNombreImagenPublicoDestino() + Persistencia.SEPARADOR_COLUMNAS
                + objDestino.getNombreImagenPrivadoDestino();

        if (miArchivo.agregarRegistro(filaGrabar)) {
            dto.setIdDestino(objDestino.getIdDestino());
            return dto;
        }

        return null;
    }

    @Override
    public List<DestinoDto> selectFrom() {
        List<DestinoDto> arregloDestino = new ArrayList<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

                int codDestino = Integer.parseInt(columnas[0].trim());
                String nombre = columnas[1].trim();
                String departamento = columnas[2].trim();
                String descripcion = columnas[3].trim();
                Boolean estado = Boolean.valueOf(columnas[4].trim());
                String npub = columnas[5].trim();
                String nocu = columnas[6].trim();

                DestinoDto dto = new DestinoDto(codDestino, nombre, departamento,
                        descripcion, estado, npub, nocu);

                arregloDestino.add(dto);

            } catch (NumberFormatException error) {
                Logger.getLogger(DestinoServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arregloDestino;
    }

    @Override
    public List<DestinoDto> selectFromWhereActivos() {
        List<DestinoDto> arregloDestino = new ArrayList<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

                int codDestino = Integer.parseInt(columnas[0].trim());
                String nombre = columnas[1].trim();
                String departamento = columnas[2].trim();
                String descripcion = columnas[3].trim();
                Boolean estado = Boolean.valueOf(columnas[4].trim());
                String npub = columnas.length > 5 ? columnas[5].trim() : "";
                String nocu = columnas.length > 6 ? columnas[6].trim() : "";

                if (Boolean.TRUE.equals(estado)) {
                    DestinoDto dto = new DestinoDto(codDestino, nombre, departamento,
                            descripcion, estado, npub, nocu);

                    arregloDestino.add(dto);
                }
            } catch (NumberFormatException error) {
                Logger.getLogger(DestinoServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arregloDestino;
    }

    @Override
    public int numRows() {
        int cantidad = 0;
        try {
            cantidad = miArchivo.cantidadFilas();
        } catch (IOException ex) {
            Logger.getLogger(DestinoServicio.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(DestinoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }

    @Override
    public DestinoDto getOne(Integer codigo) {
        int contador = 0;
        DestinoDto objListo = new DestinoDto();
        List<DestinoDto> arrDestinos = selectFrom();

        for (DestinoDto objDestino : arrDestinos) {
            if (contador == codigo) {
                objListo = objDestino;
                break;
            }
            contador++;
        }
        return objListo;
    }

    @Override
    public DestinoDto updateSet(Integer codigo, DestinoDto dto, String ruta) {
        List<String> arregloDatos = miArchivo.obtenerDatos();
        int posicionAEditar = -1;
        
        for (int i = 0; i < arregloDatos.size(); i++) {
            String cadena = arregloDatos.get(i);
            cadena = cadena.replace("@", "");
            String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);
            
            int codDestino = Integer.parseInt(columnas[0].trim());
            
            if (codDestino == codigo) {
                posicionAEditar = i;
                break;
            }
        }
        
        if (posicionAEditar != -1) {
            String imagenPrivada = dto.getNombreImagenPrivadoDestino();
            
            if (!ruta.isEmpty()) {
                imagenPrivada = GestorImagen.grabarLaImagen(ruta);
            }
            
            String filaActualizada = dto.getIdDestino() + Persistencia.SEPARADOR_COLUMNAS
                    + dto.getNombreDestino() + Persistencia.SEPARADOR_COLUMNAS
                    + dto.getDepartamentoDestino() + Persistencia.SEPARADOR_COLUMNAS
                    + dto.getDescripcionDestino() + Persistencia.SEPARADOR_COLUMNAS
                    + dto.getEstadoDestino() + Persistencia.SEPARADOR_COLUMNAS
                    + dto.getNombreImagenPublicoDestino() + Persistencia.SEPARADOR_COLUMNAS
                    + imagenPrivada;
            
            try {
                if (miArchivo.actualizaFilaPosicion(posicionAEditar, filaActualizada)) {
                    dto.setNombreImagenPrivadoDestino(imagenPrivada);
                    return dto;
                }
            } catch (IOException ex) {
                Logger.getLogger(DestinoServicio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return null;
    }
}
