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
import org.poo.dto.BusDto;
import org.poo.dto.EmpresaDto;
import org.poo.modelo.Bus;
import org.poo.recurso.constante.Persistencia;
import org.poo.recurso.utilidad.GestorImagen;

public class BusServicio implements ApiOperacionBD<BusDto, Integer> {

    private NioFile miArchivo;
    private String nombrePersistencia;

    public BusServicio() {
        nombrePersistencia = Persistencia.NOMBRE_BUS;
        try {
            miArchivo = new NioFile(nombrePersistencia);
        } catch (IOException ex) {
            Logger.getLogger(BusServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getSerial() {
        int id = 0;
        try {
            id = miArchivo.ultimoCodigo() + 1;
        } catch (IOException ex) {
            Logger.getLogger(BusServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    @Override
    public BusDto insertInto(BusDto dto, String ruta) {
        Bus objBus = new Bus();

        objBus.setIdBus(getSerial());
        objBus.setPlacaBus(dto.getPlacaBus());
        objBus.setModeloBus(dto.getModeloBus());
        objBus.setCapacidadBus(dto.getCapacidadBus());
        objBus.setTipoBus(dto.getTipoBus());
        objBus.setEstadoBus(dto.getEstadoBus());
        objBus.setNombreImagenPublicoBus(dto.getNombreImagenPublicoBus());
        objBus.setNombreImagenPrivadoBus(GestorImagen.grabarLaImagen(ruta));

        String filaGrabar = objBus.getIdBus() + Persistencia.SEPARADOR_COLUMNAS
                + objBus.getPlacaBus() + Persistencia.SEPARADOR_COLUMNAS
                + objBus.getModeloBus() + Persistencia.SEPARADOR_COLUMNAS
                + objBus.getCapacidadBus() + Persistencia.SEPARADOR_COLUMNAS
                + dto.getEmpresaBus().getIdEmpresa() + Persistencia.SEPARADOR_COLUMNAS
                + objBus.getTipoBus() + Persistencia.SEPARADOR_COLUMNAS
                + objBus.getEstadoBus() + Persistencia.SEPARADOR_COLUMNAS
                + objBus.getNombreImagenPublicoBus() + Persistencia.SEPARADOR_COLUMNAS
                + objBus.getNombreImagenPrivadoBus();

        if (miArchivo.agregarRegistro(filaGrabar)) {
            dto.setIdBus(objBus.getIdBus());
            return dto;
        }

        return null;
    }

    // Método para contar buses por empresa
    public Map<Integer, Integer> busesPorEmpresa() {
        Map<Integer, Integer> arrCantidades = new HashMap<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);
                int idEmpresa = Integer.parseInt(columnas[4].trim());
                arrCantidades.put(idEmpresa, arrCantidades.getOrDefault(idEmpresa, 0) + 1);
            } catch (NumberFormatException error) {
                Logger.getLogger(BusServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arrCantidades;
    }

    @Override
    public List<BusDto> selectFrom() {
        // OBTENER LISTA DE EMPRESAS AL INICIO
        EmpresaServicio empresaServicio = new EmpresaServicio();
        List<EmpresaDto> arrEmpresas = empresaServicio.selectFrom();

        List<BusDto> arregloBus = new ArrayList<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

                int codBus = Integer.parseInt(columnas[0].trim());
                String placaBus = columnas[1].trim();
                String modeloBus = columnas[2].trim();
                int capacidadBus = Integer.parseInt(columnas[3].trim());
                int codEmpresa = Integer.parseInt(columnas[4].trim());
                String tipoBus = columnas[5].trim();
                Boolean estBus = Boolean.valueOf(columnas[6].trim());
                String npub = columnas[7].trim();
                String nocu = columnas[8].trim();

                BusDto dto = new BusDto();
                dto.setIdBus(codBus);
                dto.setPlacaBus(placaBus);
                dto.setModeloBus(modeloBus);
                dto.setCapacidadBus(capacidadBus);
                dto.setTipoBus(tipoBus);
                dto.setEstadoBus(estBus);
                dto.setNombreImagenPublicoBus(npub);
                dto.setNombreImagenPrivadoBus(nocu);

                // AQUÍ SE USA LA LISTA DE EMPRESAS
                dto.setEmpresaBus(obtenerEmpresaCompleta(codEmpresa, arrEmpresas));

                arregloBus.add(dto);

            } catch (NumberFormatException error) {
                Logger.getLogger(BusServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arregloBus;
    }

    // MÉTODO AUXILIAR PARA OBTENER LA EMPRESA COMPLETA
    private EmpresaDto obtenerEmpresaCompleta(Integer codigoEmpresa, List<EmpresaDto> arrEmpresas) {
        for (EmpresaDto empresaExterna : arrEmpresas) {
            if (Objects.equals(codigoEmpresa, empresaExterna.getIdEmpresa())) {
                return empresaExterna;
            }
        }
        return null;
    }

    @Override
    public List<BusDto> selectFromWhereActivos() {
        // OBTENER LISTA DE EMPRESAS AL INICIO
        EmpresaServicio empresaServicio = new EmpresaServicio();
        List<EmpresaDto> arrEmpresas = empresaServicio.selectFrom();

        List<BusDto> arregloBus = new ArrayList<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

                int codBus = Integer.parseInt(columnas[0].trim());
                String placaBus = columnas[1].trim();
                String modeloBus = columnas[2].trim();
                int capacidadBus = Integer.parseInt(columnas[3].trim());
                int codEmpresa = Integer.parseInt(columnas[4].trim());
                String tipoBus = columnas[5].trim();
                Boolean estBus = Boolean.valueOf(columnas[6].trim());
                String npub = columnas[7].trim();
                String nocu = columnas[8].trim();

                // FILTRAR SOLO LOS ACTIVOS
                if (Boolean.TRUE.equals(estBus)) {
                    BusDto dto = new BusDto();
                    dto.setIdBus(codBus);
                    dto.setPlacaBus(placaBus);
                    dto.setModeloBus(modeloBus);
                    dto.setCapacidadBus(capacidadBus);
                    dto.setTipoBus(tipoBus);
                    dto.setEstadoBus(estBus);
                    dto.setNombreImagenPublicoBus(npub);
                    dto.setNombreImagenPrivadoBus(nocu);

                    // AQUÍ SE USA LA LISTA DE EMPRESAS
                    dto.setEmpresaBus(obtenerEmpresaCompleta(codEmpresa, arrEmpresas));

                    arregloBus.add(dto);
                }
            } catch (NumberFormatException error) {
                Logger.getLogger(BusServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arregloBus;
    }

    @Override
    public int numRows() {
        int cantidad = 0;
        try {
            cantidad = miArchivo.cantidadFilas();
        } catch (IOException ex) {
            Logger.getLogger(BusServicio.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(BusServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }

    @Override
    public BusDto getOne(Integer codigo) {
        int contador = 0;
        BusDto objListo = new BusDto();
        List<BusDto> arrBuses = selectFrom();

        for (BusDto objBus : arrBuses) {
            if (contador == codigo) {
                objListo = objBus;
                break;
            }
            contador++;
        }
        return objListo;
    }

    @Override
    public BusDto updateSet(Integer codigo, BusDto objeto, String ruta) {
        try {
            String cadena, nocu;
            List<String> arregloDatos;

            cadena = objeto.getIdBus() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getPlacaBus() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getModeloBus() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getCapacidadBus() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getEmpresaBus().getIdEmpresa() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getTipoBus() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getEstadoBus() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getNombreImagenPublicoBus() + Persistencia.SEPARADOR_COLUMNAS;

            if (ruta.isBlank()) {
                cadena = cadena + objeto.getNombreImagenPrivadoBus();
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
            Logger.getLogger(BusServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
