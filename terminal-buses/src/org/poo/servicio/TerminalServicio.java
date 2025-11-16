package org.poo.servicio;

import com.poo.persistence.NioFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.poo.api.ApiOperacionBD;
import org.poo.dto.TerminalDto;
import org.poo.modelo.Terminal;
import org.poo.recurso.constante.Persistencia;
import org.poo.recurso.utilidad.GestorImagen;

public class TerminalServicio implements ApiOperacionBD<TerminalDto, Integer> {

    private NioFile miArchivo;
    private String nombrePersistencia;

    public TerminalServicio() {
        nombrePersistencia = Persistencia.NOMBRE_TERMINAL;
        try {
            miArchivo = new NioFile(nombrePersistencia);
        } catch (IOException ex) {
            Logger.getLogger(TerminalServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getSerial() {
        int id = 0;
        try {
            id = miArchivo.ultimoCodigo() + 1;
        } catch (IOException ex) {
            Logger.getLogger(TerminalServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    @Override
    public TerminalDto insertInto(TerminalDto dto, String ruta) {
        Terminal objTerminal = new Terminal();

        objTerminal.setIdTerminal(getSerial());
        objTerminal.setNombreTerminal(dto.getNombreTerminal());
        objTerminal.setCiudadTerminal(dto.getCiudadTerminal());
        objTerminal.setDireccionTerminal(dto.getDireccionTerminal());
        objTerminal.setEstadoTerminal(dto.getEstadoTerminal());
        objTerminal.setNumeroPlataformas(dto.getNumeroPlataformas());
        objTerminal.setTieneWifi(dto.getTieneWifi());
        objTerminal.setTieneCafeteria(dto.getTieneCafeteria());
        objTerminal.setTieneBanos(dto.getTieneBanos());
        objTerminal.setNombreImagenPublicoTerminal(dto.getNombreImagenPublicoTerminal());
        objTerminal.setNombreImagenPrivadoTerminal(GestorImagen.grabarLaImagen(ruta));

        String filaGrabar = objTerminal.getIdTerminal() + Persistencia.SEPARADOR_COLUMNAS
                + objTerminal.getNombreTerminal() + Persistencia.SEPARADOR_COLUMNAS
                + objTerminal.getCiudadTerminal() + Persistencia.SEPARADOR_COLUMNAS
                + objTerminal.getDireccionTerminal() + Persistencia.SEPARADOR_COLUMNAS
                + objTerminal.getEstadoTerminal() + Persistencia.SEPARADOR_COLUMNAS
                + objTerminal.getNumeroPlataformas() + Persistencia.SEPARADOR_COLUMNAS
                + objTerminal.getTieneWifi() + Persistencia.SEPARADOR_COLUMNAS
                + objTerminal.getTieneCafeteria() + Persistencia.SEPARADOR_COLUMNAS
                + objTerminal.getTieneBanos() + Persistencia.SEPARADOR_COLUMNAS
                + objTerminal.getNombreImagenPublicoTerminal() + Persistencia.SEPARADOR_COLUMNAS
                + objTerminal.getNombreImagenPrivadoTerminal();

        if (miArchivo.agregarRegistro(filaGrabar)) {
            dto.setIdTerminal(objTerminal.getIdTerminal());
            return dto;
        }

        return null;
    }

    @Override
    public List<TerminalDto> selectFrom() {
        List<TerminalDto> arregloTerminal = new ArrayList<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        EmpresaServicio empresaServicio = new EmpresaServicio();
        Map<Integer, Integer> arrCantEmpresas = empresaServicio.empresasPorTerminal();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

                int codTerminal = Integer.parseInt(columnas[0].trim());
                String nomTerminal = columnas[1].trim();
                String ciudadTerminal = columnas[2].trim();
                String direccionTerminal = columnas[3].trim();
                Boolean estTerminal = Boolean.valueOf(columnas[4].trim());
                Integer numPlataformas = Integer.parseInt(columnas[5].trim());
                Boolean wifi = Boolean.valueOf(columnas[6].trim());
                Boolean cafeteria = Boolean.valueOf(columnas[7].trim());
                Boolean banos = Boolean.valueOf(columnas[8].trim());
                String npub = columnas[9].trim();
                String nocu = columnas[10].trim();

                Short cantEmpresas = arrCantEmpresas.getOrDefault(codTerminal, 0).shortValue();

                TerminalDto dto = new TerminalDto(codTerminal, nomTerminal, ciudadTerminal,
                        direccionTerminal, estTerminal, cantEmpresas, numPlataformas, wifi, cafeteria, banos);
                dto.setNombreImagenPublicoTerminal(npub);
                dto.setNombreImagenPrivadoTerminal(nocu);

                arregloTerminal.add(dto);

            } catch (NumberFormatException error) {
                Logger.getLogger(TerminalServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arregloTerminal;
    }

    @Override
    public List<TerminalDto> selectFromWhereActivos() {
        List<TerminalDto> arregloTerminal = new ArrayList<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        EmpresaServicio empresaServicio = new EmpresaServicio();
        Map<Integer, Integer> arrCantEmpresas = empresaServicio.empresasPorTerminal();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

                int codTerminal = Integer.parseInt(columnas[0].trim());
                String nomTerminal = columnas[1].trim();
                String ciudadTerminal = columnas[2].trim();
                String direccionTerminal = columnas[3].trim();
                Boolean estTerminal = Boolean.valueOf(columnas[4].trim());
                Integer numPlataformas = Integer.parseInt(columnas[5].trim());
                Boolean wifi = Boolean.valueOf(columnas[6].trim());
                Boolean cafeteria = Boolean.valueOf(columnas[7].trim());
                Boolean banos = Boolean.valueOf(columnas[8].trim());
                String npub = columnas[9].trim();
                String nocu = columnas[10].trim();

                if (Boolean.TRUE.equals(estTerminal)) {
                    Short cantEmpresas = arrCantEmpresas.getOrDefault(codTerminal, 0).shortValue();

                    TerminalDto dto = new TerminalDto(codTerminal, nomTerminal, ciudadTerminal,
                            direccionTerminal, estTerminal, cantEmpresas, numPlataformas, wifi, cafeteria, banos);
                    dto.setNombreImagenPublicoTerminal(npub);
                    dto.setNombreImagenPrivadoTerminal(nocu);

                    arregloTerminal.add(dto);
                }
            } catch (NumberFormatException error) {
                Logger.getLogger(TerminalServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arregloTerminal;
    }

    @Override
    public int numRows() {
        int cantidad = 0;
        try {
            cantidad = miArchivo.cantidadFilas();
        } catch (IOException ex) {
            Logger.getLogger(TerminalServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cantidad;
    }

    @Override
    public Boolean deleteFrom(Integer codigo) {
        Boolean correcto = false;
        try {
            List<String> arreglo = miArchivo.borrarFilaPosicion(codigo);
            if (!arreglo.isEmpty()) {
                // Eliminar la imagen asociada
                String nocu = arreglo.get(arreglo.size() - 1);
                String nombreBorrar = Persistencia.RUTA_IMAGENES 
                        + Persistencia.SEPARADOR_CARPETAS + nocu;
                Path rutaBorrar = Paths.get(nombreBorrar);
                Files.deleteIfExists(rutaBorrar);
                correcto = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(TerminalServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }

    @Override
    public TerminalDto getOne(Integer codigo) {
        int contador = 0;
        TerminalDto objListo = new TerminalDto();
        List<TerminalDto> arrTerminales = selectFrom();

        for (TerminalDto objTerminal : arrTerminales) {
            if (contador == codigo) {
                objListo = objTerminal;
                break;
            }
            contador++;
        }
        return objListo;
    }

    @Override
    public TerminalDto updateSet(Integer codigo, TerminalDto objeto, String ruta) {
        try {
            String cadena, nocu;
            List<String> arregloDatos;

            cadena = objeto.getIdTerminal() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getNombreTerminal() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getCiudadTerminal() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getDireccionTerminal() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getEstadoTerminal() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getNumeroPlataformas() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getTieneWifi() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getTieneCafeteria() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getTieneBanos() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getNombreImagenPublicoTerminal() + Persistencia.SEPARADOR_COLUMNAS;

            // Si no hay nueva imagen, mantener la actual
            if (ruta.isBlank()) {
                cadena = cadena + objeto.getNombreImagenPrivadoTerminal();
            } else {
                // Grabar nueva imagen
                nocu = GestorImagen.grabarLaImagen(ruta);
                cadena = cadena + nocu;
                
                // Eliminar imagen anterior
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
            Logger.getLogger(TerminalServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
