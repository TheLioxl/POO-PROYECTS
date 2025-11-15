package org.poo.servicio;

import com.poo.persistence.NioFile;
import java.io.IOException;
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
        objTerminal.setNombreImagenPublicoTerminal(dto.getNombreImagenPublicoTerminal());
        objTerminal.setNombreImagenPrivadoTerminal(GestorImagen.grabarLaImagen(ruta));

        String filaGrabar = objTerminal.getIdTerminal() + Persistencia.SEPARADOR_COLUMNAS
                + objTerminal.getNombreTerminal() + Persistencia.SEPARADOR_COLUMNAS
                + objTerminal.getCiudadTerminal() + Persistencia.SEPARADOR_COLUMNAS
                + objTerminal.getDireccionTerminal() + Persistencia.SEPARADOR_COLUMNAS
                + objTerminal.getEstadoTerminal() + Persistencia.SEPARADOR_COLUMNAS
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

        // Obtener cantidad de empresas por terminal
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
                String npub = columnas[5].trim();
                String nocu = columnas[6].trim();

                Short cantEmpresas = arrCantEmpresas.getOrDefault(codTerminal, 0).shortValue();

                TerminalDto dto = new TerminalDto(codTerminal, nomTerminal, ciudadTerminal,
                        direccionTerminal, estTerminal, cantEmpresas);
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
                String npub = columnas[5].trim();
                String nocu = columnas[6].trim();

                if (Boolean.TRUE.equals(estTerminal)) {
                    Short cantEmpresas = arrCantEmpresas.getOrDefault(codTerminal, 0).shortValue();

                    TerminalDto dto = new TerminalDto(codTerminal, nomTerminal, ciudadTerminal,
                            direccionTerminal, estTerminal, cantEmpresas);
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
                correcto = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(TerminalServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }

    @Override
    public TerminalDto getOne(Integer codigo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TerminalDto updateSet(Integer codigo, TerminalDto objeto, String ruta) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}