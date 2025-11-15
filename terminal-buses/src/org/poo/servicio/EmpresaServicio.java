package org.poo.servicio;

import com.poo.persistence.NioFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.poo.api.ApiOperacionBD;
import org.poo.dto.EmpresaDto;
import org.poo.dto.TerminalDto;
import org.poo.modelo.Empresa;
import org.poo.modelo.Terminal;
import org.poo.recurso.constante.Persistencia;
import org.poo.recurso.utilidad.GestorImagen;

public class EmpresaServicio implements ApiOperacionBD<EmpresaDto, Integer> {

    private NioFile miArchivo;
    private String nombrePersistencia;

    public EmpresaServicio() {
        nombrePersistencia = Persistencia.NOMBRE_EMPRESA;
        try {
            miArchivo = new NioFile(nombrePersistencia);
        } catch (IOException ex) {
            Logger.getLogger(EmpresaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getSerial() {
        int id = 0;
        try {
            id = miArchivo.ultimoCodigo() + 1;
        } catch (IOException ex) {
            Logger.getLogger(EmpresaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    @Override
    public EmpresaDto insertInto(EmpresaDto dto, String ruta) {
        Empresa objEmpresa = new Empresa();

        objEmpresa.setIdEmpresa(getSerial());
        objEmpresa.setNombreEmpresa(dto.getNombreEmpresa());
        objEmpresa.setNitEmpresa(dto.getNitEmpresa());
        objEmpresa.setEstadoEmpresa(dto.getEstadoEmpresa());
        objEmpresa.setNombreImagenPublicoEmpresa(dto.getNombreImagenPublicoEmpresa());
        objEmpresa.setNombreImagenPrivadoEmpresa(GestorImagen.grabarLaImagen(ruta));

        String filaGrabar = objEmpresa.getIdEmpresa() + Persistencia.SEPARADOR_COLUMNAS
                + objEmpresa.getNombreEmpresa() + Persistencia.SEPARADOR_COLUMNAS
                + objEmpresa.getNitEmpresa() + Persistencia.SEPARADOR_COLUMNAS
                + dto.getTerminalEmpresa().getIdTerminal() + Persistencia.SEPARADOR_COLUMNAS
                + objEmpresa.getEstadoEmpresa() + Persistencia.SEPARADOR_COLUMNAS
                + objEmpresa.getNombreImagenPublicoEmpresa() + Persistencia.SEPARADOR_COLUMNAS
                + objEmpresa.getNombreImagenPrivadoEmpresa();

        if (miArchivo.agregarRegistro(filaGrabar)) {
            dto.setIdEmpresa(objEmpresa.getIdEmpresa());
            return dto;
        }

        return null;
    }

    // Método para contar empresas por terminal
    public Map<Integer, Integer> empresasPorTerminal() {
        Map<Integer, Integer> arrCantidades = new HashMap<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);
                int idTerminal = Integer.parseInt(columnas[3].trim());
                arrCantidades.put(idTerminal, arrCantidades.getOrDefault(idTerminal, 0) + 1);
            } catch (NumberFormatException error) {
                Logger.getLogger(EmpresaServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arrCantidades;
    }

    @Override
    public List<EmpresaDto> selectFrom() {
        // Obtener terminales completos
        TerminalServicio terminalServicio = new TerminalServicio();
        List<TerminalDto> arrTerminales = terminalServicio.selectFrom();

        // Obtener cantidad de buses por empresa
        BusServicio busServicio = new BusServicio();
        Map<Integer, Integer> arrCantBuses = busServicio.busesPorEmpresa();

        List<EmpresaDto> arregloEmpresa = new ArrayList<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

                int codEmpresa = Integer.parseInt(columnas[0].trim());
                String nomEmpresa = columnas[1].trim();
                String nitEmpresa = columnas[2].trim();
                int codTerminal = Integer.parseInt(columnas[3].trim());
                Boolean estEmpresa = Boolean.valueOf(columnas[4].trim());
                String npub = columnas[5].trim();
                String nocu = columnas[6].trim();

                Short cantBuses = arrCantBuses.getOrDefault(codEmpresa, 0).shortValue();

                EmpresaDto dto = new EmpresaDto();
                dto.setIdEmpresa(codEmpresa);
                dto.setNombreEmpresa(nomEmpresa);
                dto.setNitEmpresa(nitEmpresa);
                dto.setEstadoEmpresa(estEmpresa);
                dto.setCantidadBusesEmpresa(cantBuses);
                dto.setNombreImagenPublicoEmpresa(npub);
                dto.setNombreImagenPrivadoEmpresa(nocu);

                // Asignar terminal completo
                dto.setTerminalEmpresa(obtenerTerminalCompleto(codTerminal, arrTerminales));

                arregloEmpresa.add(dto);

            } catch (NumberFormatException error) {
                Logger.getLogger(EmpresaServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arregloEmpresa;
    }

    private TerminalDto obtenerTerminalCompleto(Integer codigoTerminal, List<TerminalDto> arrTerminales) {
        for (TerminalDto terminalExterno : arrTerminales) {
            if (Objects.equals(codigoTerminal, terminalExterno.getIdTerminal())) {
                return terminalExterno;
            }
        }
        return null;
    }

    @Override
    public List<EmpresaDto> selectFromWhereActivos() {
        TerminalServicio terminalServicio = new TerminalServicio();
        List<TerminalDto> arrTerminales = terminalServicio.selectFrom();

        BusServicio busServicio = new BusServicio();
        Map<Integer, Integer> arrCantBuses = busServicio.busesPorEmpresa();

        List<EmpresaDto> arregloEmpresa = new ArrayList<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

                int codEmpresa = Integer.parseInt(columnas[0].trim());
                String nomEmpresa = columnas[1].trim();
                String nitEmpresa = columnas[2].trim();
                int codTerminal = Integer.parseInt(columnas[3].trim());
                Boolean estEmpresa = Boolean.valueOf(columnas[4].trim());
                String npub = columnas[5].trim();
                String nocu = columnas[6].trim();

                if (Boolean.TRUE.equals(estEmpresa)) {
                    Short cantBuses = arrCantBuses.getOrDefault(codEmpresa, 0).shortValue();

                    EmpresaDto dto = new EmpresaDto();
                    dto.setIdEmpresa(codEmpresa);
                    dto.setNombreEmpresa(nomEmpresa);
                    dto.setNitEmpresa(nitEmpresa);
                    dto.setEstadoEmpresa(estEmpresa);
                    dto.setCantidadBusesEmpresa(cantBuses);
                    dto.setNombreImagenPublicoEmpresa(npub);
                    dto.setNombreImagenPrivadoEmpresa(nocu);

                    dto.setTerminalEmpresa(obtenerTerminalCompleto(codTerminal, arrTerminales));

                    arregloEmpresa.add(dto);
                }
            } catch (NumberFormatException error) {
                Logger.getLogger(EmpresaServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arregloEmpresa;
    }

    @Override
    public int numRows() {
        int cantidad = 0;
        try {
            cantidad = miArchivo.cantidadFilas();
        } catch (IOException ex) {
            Logger.getLogger(EmpresaServicio.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(EmpresaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }

    @Override
    public EmpresaDto getOne(Integer codigo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EmpresaDto updateSet(Integer codigo, EmpresaDto objeto, String ruta) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
