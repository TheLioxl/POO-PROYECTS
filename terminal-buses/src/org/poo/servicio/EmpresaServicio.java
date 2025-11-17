package org.poo.servicio;

import com.poo.persistence.NioFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
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
        
        // NUEVOS CAMPOS
        objEmpresa.setFechaFundacion(dto.getFechaFundacion());
        objEmpresa.setCantidadEmpleados(dto.getCantidadEmpleados());
        objEmpresa.setServicio24Horas(dto.getServicio24Horas());
        objEmpresa.setTieneMantenimientoPropio(dto.getTieneMantenimientoPropio());
        objEmpresa.setTieneServicioCliente(dto.getTieneServicioCliente());
        objEmpresa.setDescripcionEmpresa(dto.getDescripcionEmpresa());

        String filaGrabar = objEmpresa.getIdEmpresa() + Persistencia.SEPARADOR_COLUMNAS
                + objEmpresa.getNombreEmpresa() + Persistencia.SEPARADOR_COLUMNAS
                + objEmpresa.getNitEmpresa() + Persistencia.SEPARADOR_COLUMNAS
                + dto.getTerminalEmpresa().getIdTerminal() + Persistencia.SEPARADOR_COLUMNAS
                + objEmpresa.getEstadoEmpresa() + Persistencia.SEPARADOR_COLUMNAS
                + objEmpresa.getFechaFundacion().toString() + Persistencia.SEPARADOR_COLUMNAS
                + objEmpresa.getCantidadEmpleados() + Persistencia.SEPARADOR_COLUMNAS
                + objEmpresa.getServicio24Horas() + Persistencia.SEPARADOR_COLUMNAS
                + objEmpresa.getTieneMantenimientoPropio() + Persistencia.SEPARADOR_COLUMNAS
                + objEmpresa.getTieneServicioCliente() + Persistencia.SEPARADOR_COLUMNAS
                + objEmpresa.getDescripcionEmpresa() + Persistencia.SEPARADOR_COLUMNAS
                + objEmpresa.getNombreImagenPublicoEmpresa() + Persistencia.SEPARADOR_COLUMNAS
                + objEmpresa.getNombreImagenPrivadoEmpresa();

        if (miArchivo.agregarRegistro(filaGrabar)) {
            dto.setIdEmpresa(objEmpresa.getIdEmpresa());
            return dto;
        }

        return null;
    }

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
                LocalDate fechaFund = LocalDate.parse(columnas[5].trim());
                Integer cantEmpleados = Integer.parseInt(columnas[6].trim());
                Boolean servicio24 = Boolean.valueOf(columnas[7].trim());
                Boolean tieneMantenimiento = Boolean.valueOf(columnas[8].trim());
                Boolean tieneServCliente = Boolean.valueOf(columnas[9].trim());
                String descripcion = columnas[10].trim();
                String npub = columnas[11].trim();
                String nocu = columnas[12].trim();

                Short cantBuses = arrCantBuses.getOrDefault(codEmpresa, 0).shortValue();

                EmpresaDto dto = new EmpresaDto();
                dto.setIdEmpresa(codEmpresa);
                dto.setNombreEmpresa(nomEmpresa);
                dto.setNitEmpresa(nitEmpresa);
                dto.setEstadoEmpresa(estEmpresa);
                dto.setCantidadBusesEmpresa(cantBuses);
                dto.setFechaFundacion(fechaFund);
                dto.setCantidadEmpleados(cantEmpleados);
                dto.setServicio24Horas(servicio24);
                dto.setTieneMantenimientoPropio(tieneMantenimiento);
                dto.setTieneServicioCliente(tieneServCliente);
                dto.setDescripcionEmpresa(descripcion);
                dto.setNombreImagenPublicoEmpresa(npub);
                dto.setNombreImagenPrivadoEmpresa(nocu);

                dto.setTerminalEmpresa(obtenerTerminalCompleto(codTerminal, arrTerminales));

                arregloEmpresa.add(dto);

            } catch (Exception error) {
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
                LocalDate fechaFund = LocalDate.parse(columnas[5].trim());
                Integer cantEmpleados = Integer.parseInt(columnas[6].trim());
                Boolean servicio24 = Boolean.valueOf(columnas[7].trim());
                Boolean tieneMantenimiento = Boolean.valueOf(columnas[8].trim());
                Boolean tieneServCliente = Boolean.valueOf(columnas[9].trim());
                String descripcion = columnas[10].trim();
                String npub = columnas[11].trim();
                String nocu = columnas[12].trim();

                if (Boolean.TRUE.equals(estEmpresa)) {
                    Short cantBuses = arrCantBuses.getOrDefault(codEmpresa, 0).shortValue();

                    EmpresaDto dto = new EmpresaDto();
                    dto.setIdEmpresa(codEmpresa);
                    dto.setNombreEmpresa(nomEmpresa);
                    dto.setNitEmpresa(nitEmpresa);
                    dto.setEstadoEmpresa(estEmpresa);
                    dto.setCantidadBusesEmpresa(cantBuses);
                    dto.setFechaFundacion(fechaFund);
                    dto.setCantidadEmpleados(cantEmpleados);
                    dto.setServicio24Horas(servicio24);
                    dto.setTieneMantenimientoPropio(tieneMantenimiento);
                    dto.setTieneServicioCliente(tieneServCliente);
                    dto.setDescripcionEmpresa(descripcion);
                    dto.setNombreImagenPublicoEmpresa(npub);
                    dto.setNombreImagenPrivadoEmpresa(nocu);

                    dto.setTerminalEmpresa(obtenerTerminalCompleto(codTerminal, arrTerminales));

                    arregloEmpresa.add(dto);
                }
            } catch (Exception error) {
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
                String nocu = arreglo.get(arreglo.size() - 1);
                String nombreBorrar = Persistencia.RUTA_IMAGENES 
                        + Persistencia.SEPARADOR_CARPETAS + nocu;
                Path rutaBorrar = Paths.get(nombreBorrar);
                Files.deleteIfExists(rutaBorrar);
                correcto = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(EmpresaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }

    @Override
    public EmpresaDto getOne(Integer codigo) {
        int contador = 0;
        EmpresaDto objListo = new EmpresaDto();
        List<EmpresaDto> arrEmpresas = selectFrom();

        for (EmpresaDto objEmpresa : arrEmpresas) {
            if (contador == codigo) {
                objListo = objEmpresa;
                break;
            }
            contador++;
        }
        return objListo;
    }

    @Override
    public EmpresaDto updateSet(Integer codigo, EmpresaDto objeto, String ruta) {
        try {
            String cadena, nocu;
            List<String> arregloDatos;

            cadena = objeto.getIdEmpresa() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getNombreEmpresa() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getNitEmpresa() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getTerminalEmpresa().getIdTerminal() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getEstadoEmpresa() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getFechaFundacion().toString() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getCantidadEmpleados() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getServicio24Horas() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getTieneMantenimientoPropio() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getTieneServicioCliente() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getDescripcionEmpresa() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getNombreImagenPublicoEmpresa() + Persistencia.SEPARADOR_COLUMNAS;

            if (ruta.isBlank()) {
                cadena = cadena + objeto.getNombreImagenPrivadoEmpresa();
            } else {
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
            Logger.getLogger(EmpresaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
