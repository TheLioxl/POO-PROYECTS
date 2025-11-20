package org.poo.servicio;

import com.poo.persistence.NioFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
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
        objPasajero.setTipoDocumentoPasajero(dto.getTipoDocumentoPasajero());
        objPasajero.setEsMayorPasajero(dto.getEsMayorPasajero());
        objPasajero.setFechaNacimientoPasajero(dto.getFechaNacimientoPasajero());
        objPasajero.setTelefonoPasajero(dto.getTelefonoPasajero());
        objPasajero.setEmailPasajero(dto.getEmailPasajero());
        objPasajero.setNombreImagendocumentoPublicoPasajero(dto.getNombreImagenPublicoPasajero());
        objPasajero.setNombreImagendocumentoPrivadoPasajero(GestorImagen.grabarLaImagen(ruta));

        String fechaNac = dto.getFechaNacimientoPasajero() != null
                ? dto.getFechaNacimientoPasajero().toString()
                : "";

        String filaGrabar = objPasajero.getIdPasajero() + Persistencia.SEPARADOR_COLUMNAS
                + objPasajero.getNombrePasajero() + Persistencia.SEPARADOR_COLUMNAS
                + objPasajero.getDocumentoPasajero() + Persistencia.SEPARADOR_COLUMNAS
                + objPasajero.getTipoDocumentoPasajero() + Persistencia.SEPARADOR_COLUMNAS
                + objPasajero.getEsMayorPasajero() + Persistencia.SEPARADOR_COLUMNAS
                + fechaNac + Persistencia.SEPARADOR_COLUMNAS
                + objPasajero.getTelefonoPasajero() + Persistencia.SEPARADOR_COLUMNAS
                + objPasajero.getEmailPasajero() + Persistencia.SEPARADOR_COLUMNAS
                + objPasajero.getNombreImagendocumentoPublicoPasajero() + Persistencia.SEPARADOR_COLUMNAS
                + objPasajero.getNombreImagendocumentoPrivadoPasajero();

        if (miArchivo.agregarRegistro(filaGrabar)) {
            dto.setIdPasajero(objPasajero.getIdPasajero());
            dto.setNombreImagenPrivadoPasajero(objPasajero.getNombreImagendocumentoPrivadoPasajero());
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

                int codPasajero = Integer.parseInt(columnas[0].trim());
                String nombre = columnas[1].trim();
                String documento = columnas[2].trim();
                String tipoDocumento = columnas[3].trim();
                Boolean esMayor = Boolean.valueOf(columnas[4].trim());
                String fechaNacTxt = columnas[5].trim();
                String telefono = columnas[6].trim();
                String email = columnas[7].trim();
                String npub = columnas.length > 7 ? columnas[7].trim() : "";
                String nocu = columnas.length > 8 ? columnas[8].trim() : "";

                PasajeroDto dto = new PasajeroDto();
                dto.setIdPasajero(codPasajero);
                dto.setNombrePasajero(nombre);
                dto.setDocumentoPasajero(documento);
                dto.setTipoDocumentoPasajero(tipoDocumento);
                dto.setEsMayorPasajero(esMayor);
                dto.setTelefonoPasajero(telefono);
                dto.setEmailPasajero(email);
                dto.setNombreImagenPublicoPasajero(npub);
                dto.setNombreImagenPrivadoPasajero(nocu);

                if (!fechaNacTxt.isBlank()) {
                    dto.setFechaNacimientoPasajero(LocalDate.parse(fechaNacTxt));
                }

                arregloPasajero.add(dto);

            } catch (NumberFormatException error) {
                Logger.getLogger(PasajeroServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arregloPasajero;
    }


    @Override
    public List<PasajeroDto> selectFromWhereActivos() {
        // No hay campo "estado" en Pasajero, así que devolvemos todos
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
                // El último dato es el nombre de la imagen privada
                String nomOculto = arreglo.get(arreglo.size() - 1);
                String nombreBorrar = Persistencia.RUTA_IMAGENES
                        + Persistencia.SEPARADOR_CARPETAS + nomOculto;
                Path rutaBorrar = Paths.get(nombreBorrar);
                Files.deleteIfExists(rutaBorrar);
                correcto = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(PasajeroServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }


    @Override
    public PasajeroDto getOne(Integer codigo) {
       int contador = 0;
        PasajeroDto objListo = new PasajeroDto();
        List<PasajeroDto> arrPasajeros = selectFrom();

        for (PasajeroDto objPasajero : arrPasajeros) {
            if (contador == codigo) {
                objListo = objPasajero;
                break;
            }
            contador++;
        }
        return objListo;
    }
    

    @Override
    public PasajeroDto updateSet(Integer codigo, PasajeroDto objeto, String ruta) {
        try {
            String cadena, nocu;
            List<String> arregloDatos;

            String fechaNac = objeto.getFechaNacimientoPasajero() != null
                    ? objeto.getFechaNacimientoPasajero().toString()
                    : "";

            cadena = objeto.getIdPasajero() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getNombrePasajero() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getDocumentoPasajero() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getTipoDocumentoPasajero() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getEsMayorPasajero() + Persistencia.SEPARADOR_COLUMNAS
                    + fechaNac + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getTelefonoPasajero() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getEmailPasajero() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getNombreImagenPublicoPasajero() + Persistencia.SEPARADOR_COLUMNAS;

            if (ruta.isBlank()) {
                // Mantener imagen privada actual
                cadena = cadena + objeto.getNombreImagenPrivadoPasajero();
            } else {
                // Grabar nueva imagen y borrar la anterior
                nocu = GestorImagen.grabarLaImagen(ruta);
                cadena = cadena + nocu;

                arregloDatos = miArchivo.borrarFilaPosicion(codigo);
                if (!arregloDatos.isEmpty()) {
                    String nomOculto = arregloDatos.get(arregloDatos.size() - 1);
                    String nombreBorrar = Persistencia.RUTA_IMAGENES
                            + Persistencia.SEPARADOR_CARPETAS + nomOculto;
                    Path rutaBorrar = Paths.get(nombreBorrar);
                    Files.deleteIfExists(rutaBorrar);
                }
            }

            if (miArchivo.actualizaFilaPosicion(codigo, cadena)) {
                return objeto;
            }
        } catch (IOException ex) {
            Logger.getLogger(PasajeroServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
