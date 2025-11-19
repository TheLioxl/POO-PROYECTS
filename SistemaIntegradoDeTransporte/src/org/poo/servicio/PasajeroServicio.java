package org.poo.servicio;

import com.poo.persistence.NioFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.poo.api.ApiOperacionBD;
import org.poo.dto.PasajeroDto;
import org.poo.modelo.Pasajero;
import org.poo.recurso.constante.Persistencia;
import org.poo.recurso.utilidad.GestorImagen;

public class PasajeroServicio implements ApiOperacionBD<PasajeroDto, Integer> {

    private NioFile miArchivo;
    private String nombrePersistencia;

    public PasajeroServicio() {
        nombrePersistencia = Persistencia.NOMBRE_PASAJERO;
        try {
            miArchivo = new NioFile(nombrePersistencia);
        } catch (IOException ex) {
            Logger.getLogger(PasajeroServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getSerial() {
        int id = 0;
        try {
            id = miArchivo.ultimoCodigo() + 1;
        } catch (IOException ex) {
            Logger.getLogger(PasajeroServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    @Override
    public PasajeroDto insertInto(PasajeroDto dto, String ruta) {
        Pasajero objPasajero = new Pasajero();

        objPasajero.setIdPasajero(getSerial());
        objPasajero.setNombrePasajero(dto.getNombrePasajero());
        objPasajero.setDocumentoPasajero(dto.getDocumentoPasajero());
        objPasajero.setTelefonoPasajero(dto.getTelefonoPasajero());
        objPasajero.setEmailPasajero(dto.getEmailPasajero());
        objPasajero.setNombreImagendocumentoPublicoPasajero(dto.getNombreImagenPublicoPasajero());
        objPasajero.setNombreImagendocumentoPrivadoPasajero(GestorImagen.grabarLaImagen(ruta));

        String filaGrabar = objPasajero.getIdPasajero() + Persistencia.SEPARADOR_COLUMNAS
                + objPasajero.getNombrePasajero() + Persistencia.SEPARADOR_COLUMNAS
                + objPasajero.getDocumentoPasajero() + Persistencia.SEPARADOR_COLUMNAS
                + objPasajero.getTelefonoPasajero() + Persistencia.SEPARADOR_COLUMNAS
                + objPasajero.getEmailPasajero() + Persistencia.SEPARADOR_COLUMNAS
                + objPasajero.getNombreImagendocumentoPublicoPasajero() + Persistencia.SEPARADOR_COLUMNAS
                + objPasajero.getNombreImagendocumentoPrivadoPasajero();

        if (miArchivo.agregarRegistro(filaGrabar)) {
            dto.setIdPasajero(objPasajero.getIdPasajero());
            return dto;
        }

        return null;
    }

    @Override
    public List<PasajeroDto> selectFrom() {
        List<PasajeroDto> arregloPasajero = new ArrayList<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

                // Validar que el array tenga al menos 7 elementos
                if (columnas.length < 7) {
                    continue;
                }

                int codPasajero = Integer.parseInt(columnas[0].trim());
                String nombre = columnas[1].trim();
                String cedula = columnas[2].trim();
                String telefono = columnas[3].trim();
                String email = columnas[4].trim();
                String npub = columnas[5].trim();
                String nocu = columnas[6].trim();

                PasajeroDto dto = new PasajeroDto();
                dto.setIdPasajero(codPasajero);
                dto.setNombrePasajero(nombre);
                dto.setDocumentoPasajero(cedula);
                dto.setTelefonoPasajero(telefono);
                dto.setEmailPasajero(email);
                dto.setNombreImagenPublicoPasajero(npub);
                dto.setNombreImagenPrivadoPasajero(nocu);

                arregloPasajero.add(dto);

            } catch (NumberFormatException error) {
                Logger.getLogger(PasajeroServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arregloPasajero;
    }

    @Override
    public List<PasajeroDto> selectFromWhereActivos() {
        return selectFrom();
    }

    @Override
    public int numRows() {
        int cantidad = 0;
        try {
            cantidad = miArchivo.cantidadFilas();
        } catch (IOException ex) {
            Logger.getLogger(PasajeroServicio.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(PasajeroServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }

    @Override
    public PasajeroDto getOne(Integer codigo) {
        List<String> arregloDatos = miArchivo.obtenerDatos();

        // Buscar por ID del pasajero (columna 0)
        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

                int codPasajero = Integer.parseInt(columnas[0].trim());

                // Si encontramos el ID que buscamos
                if (codPasajero == codigo) {
                    String nombre = columnas[1].trim();
                    String cedula = columnas[2].trim();
                    String telefono = columnas[3].trim();
                    String email = columnas[4].trim();
                    String npub = columnas[5].trim();
                    String nocu = columnas[6].trim();

                    PasajeroDto dto = new PasajeroDto();
                    dto.setIdPasajero(codPasajero);
                    dto.setNombrePasajero(nombre);
                    dto.setDocumentoPasajero(cedula);
                    dto.setTelefonoPasajero(telefono);
                    dto.setEmailPasajero(email);
                    dto.setNombreImagenPublicoPasajero(npub);
                    dto.setNombreImagenPrivadoPasajero(nocu);

                    return dto;
                }
            } catch (NumberFormatException error) {
                Logger.getLogger(PasajeroServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return null; // No encontrado
    }

    @Override
    public PasajeroDto updateSet(Integer codigo, PasajeroDto dto, String ruta) {
        List<String> arregloDatos = miArchivo.obtenerDatos();
        int posicionAEditar = -1;

        for (int i = 0; i < arregloDatos.size(); i++) {
            String cadena = arregloDatos.get(i);
            cadena = cadena.replace("@", "");
            String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

            // Validar que el array tenga al menos 7 elementos
            if (columnas.length < 7) {
                continue;
            }

            int codPasajero = Integer.parseInt(columnas[0].trim());

            if (codPasajero == codigo) {
                posicionAEditar = i;
                break;
            }
        }

        if (posicionAEditar != -1) {
            String imagenPrivada = dto.getNombreImagenPrivadoPasajero();

            if (!ruta.isEmpty()) {
                imagenPrivada = GestorImagen.grabarLaImagen(ruta);
            }

            String filaActualizada = dto.getIdPasajero() + Persistencia.SEPARADOR_COLUMNAS
                    + dto.getNombrePasajero() + Persistencia.SEPARADOR_COLUMNAS
                    + dto.getDocumentoPasajero() + Persistencia.SEPARADOR_COLUMNAS
                    + dto.getTelefonoPasajero() + Persistencia.SEPARADOR_COLUMNAS
                    + dto.getEmailPasajero() + Persistencia.SEPARADOR_COLUMNAS
                    + dto.getNombreImagenPublicoPasajero() + Persistencia.SEPARADOR_COLUMNAS
                    + imagenPrivada;

            try {
                if (miArchivo.actualizaFilaPosicion(posicionAEditar, filaActualizada)) {
                    dto.setNombreImagenPrivadoPasajero(imagenPrivada);
                    return dto;
                }
            } catch (IOException ex) {
                Logger.getLogger(PasajeroServicio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }
}
