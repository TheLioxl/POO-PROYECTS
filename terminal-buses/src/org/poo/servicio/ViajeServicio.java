package org.poo.servicio;

import com.poo.persistence.NioFile;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.poo.api.ApiOperacionBD;
import org.poo.dto.BusDto;
import org.poo.dto.ConductorDto;
import org.poo.dto.RutaDto;
import org.poo.dto.ViajeDto;
import org.poo.modelo.Viaje;
import org.poo.recurso.constante.Persistencia;
import org.poo.recurso.utilidad.GestorImagen;

public class ViajeServicio implements ApiOperacionBD<ViajeDto, Integer> {

    private NioFile miArchivo;
    private String nombrePersistencia;

    public ViajeServicio() {
        nombrePersistencia = Persistencia.NOMBRE_VIAJE;
        try {
            miArchivo = new NioFile(nombrePersistencia);
        } catch (IOException ex) {
            Logger.getLogger(ViajeServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getSerial() {
        int id = 0;
        try {
            id = miArchivo.ultimoCodigo() + 1;
        } catch (IOException ex) {
            Logger.getLogger(ViajeServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    @Override
    public ViajeDto insertInto(ViajeDto dto, String ruta) {
        Viaje objViaje = new Viaje();

        objViaje.setIdViaje(getSerial());
        objViaje.setFechaViaje(dto.getFechaViaje());
        objViaje.setHoraSalidaViaje(dto.getHoraSalidaViaje());
        objViaje.setPrecioViaje(dto.getPrecioViaje());
        objViaje.setAsientosDisponiblesViaje(dto.getAsientosDisponiblesViaje());
        objViaje.setEstadoViaje(dto.getEstadoViaje());
        objViaje.setNombreImagenPublicoViaje(dto.getNombreImagenPublicoViaje());
        objViaje.setNombreImagenPrivadoViaje(GestorImagen.grabarLaImagen(ruta));

        String filaGrabar = objViaje.getIdViaje() + Persistencia.SEPARADOR_COLUMNAS
                + dto.getBusViaje().getIdBus() + Persistencia.SEPARADOR_COLUMNAS
                + dto.getRutaViaje().getIdRuta() + Persistencia.SEPARADOR_COLUMNAS
                + dto.getConductorViaje().getIdConductor() + Persistencia.SEPARADOR_COLUMNAS
                + objViaje.getFechaViaje().toString() + Persistencia.SEPARADOR_COLUMNAS
                + objViaje.getHoraSalidaViaje().toString() + Persistencia.SEPARADOR_COLUMNAS
                + objViaje.getPrecioViaje() + Persistencia.SEPARADOR_COLUMNAS
                + objViaje.getAsientosDisponiblesViaje() + Persistencia.SEPARADOR_COLUMNAS
                + objViaje.getEstadoViaje() + Persistencia.SEPARADOR_COLUMNAS
                + objViaje.getNombreImagenPublicoViaje() + Persistencia.SEPARADOR_COLUMNAS
                + objViaje.getNombreImagenPrivadoViaje();

        if (miArchivo.agregarRegistro(filaGrabar)) {
            dto.setIdViaje(objViaje.getIdViaje());
            return dto;
        }

        return null;
    }

    @Override
    public List<ViajeDto> selectFrom() {
        BusServicio busServicio = new BusServicio();
        List<BusDto> arrBuses = busServicio.selectFrom();

        RutaServicio rutaServicio = new RutaServicio();
        List<RutaDto> arrRutas = rutaServicio.selectFrom();

        ConductorServicio conductorServicio = new ConductorServicio();
        List<ConductorDto> arrConductores = conductorServicio.selectFrom();

        List<ViajeDto> arregloViaje = new ArrayList<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

                int codViaje = Integer.parseInt(columnas[0].trim());
                int codBus = Integer.parseInt(columnas[1].trim());
                int codRuta = Integer.parseInt(columnas[2].trim());
                int codConductor = Integer.parseInt(columnas[3].trim());
                LocalDate fecha = LocalDate.parse(columnas[4].trim());
                LocalTime hora = LocalTime.parse(columnas[5].trim());
                Double precio = Double.parseDouble(columnas[6].trim());
                Integer asientos = Integer.parseInt(columnas[7].trim());
                Boolean estado = Boolean.valueOf(columnas[8].trim());
                String npub = columnas[9].trim();
                String nocu = columnas[10].trim();

                ViajeDto dto = new ViajeDto();
                dto.setIdViaje(codViaje);
                dto.setFechaViaje(fecha);
                dto.setHoraSalidaViaje(hora);
                dto.setPrecioViaje(precio);
                dto.setAsientosDisponiblesViaje(asientos);
                dto.setEstadoViaje(estado);
                dto.setNombreImagenPublicoViaje(npub);
                dto.setNombreImagenPrivadoViaje(nocu);

                dto.setBusViaje(obtenerBusCompleto(codBus, arrBuses));
                dto.setRutaViaje(obtenerRutaCompleta(codRuta, arrRutas));
                dto.setConductorViaje(obtenerConductorCompleto(codConductor, arrConductores));

                arregloViaje.add(dto);

            } catch (Exception error) {
                Logger.getLogger(ViajeServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arregloViaje;
    }

    private BusDto obtenerBusCompleto(Integer codigoBus, List<BusDto> arrBuses) {
        for (BusDto busExterno : arrBuses) {
            if (Objects.equals(codigoBus, busExterno.getIdBus())) {
                return busExterno;
            }
        }
        return null;
    }

    private RutaDto obtenerRutaCompleta(Integer codigoRuta, List<RutaDto> arrRutas) {
        for (RutaDto rutaExterna : arrRutas) {
            if (Objects.equals(codigoRuta, rutaExterna.getIdRuta())) {
                return rutaExterna;
            }
        }
        return null;
    }

    private ConductorDto obtenerConductorCompleto(Integer codigoConductor, List<ConductorDto> arrConductores) {
        for (ConductorDto conductorExterno : arrConductores) {
            if (Objects.equals(codigoConductor, conductorExterno.getIdConductor())) {
                return conductorExterno;
            }
        }
        return null;
    }

    @Override
    public List<ViajeDto> selectFromWhereActivos() {
        BusServicio busServicio = new BusServicio();
        List<BusDto> arrBuses = busServicio.selectFrom();

        RutaServicio rutaServicio = new RutaServicio();
        List<RutaDto> arrRutas = rutaServicio.selectFrom();

        ConductorServicio conductorServicio = new ConductorServicio();
        List<ConductorDto> arrConductores = conductorServicio.selectFrom();

        List<ViajeDto> arregloViaje = new ArrayList<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

                int codViaje = Integer.parseInt(columnas[0].trim());
                int codBus = Integer.parseInt(columnas[1].trim());
                int codRuta = Integer.parseInt(columnas[2].trim());
                int codConductor = Integer.parseInt(columnas[3].trim());
                LocalDate fecha = LocalDate.parse(columnas[4].trim());
                LocalTime hora = LocalTime.parse(columnas[5].trim());
                Double precio = Double.parseDouble(columnas[6].trim());
                Integer asientos = Integer.parseInt(columnas[7].trim());
                Boolean estado = Boolean.valueOf(columnas[8].trim());
                String npub = columnas[9].trim();
                String nocu = columnas[10].trim();

                if (Boolean.TRUE.equals(estado)) {
                    ViajeDto dto = new ViajeDto();
                    dto.setIdViaje(codViaje);
                    dto.setFechaViaje(fecha);
                    dto.setHoraSalidaViaje(hora);
                    dto.setPrecioViaje(precio);
                    dto.setAsientosDisponiblesViaje(asientos);
                    dto.setEstadoViaje(estado);
                    dto.setNombreImagenPublicoViaje(npub);
                    dto.setNombreImagenPrivadoViaje(nocu);

                    dto.setBusViaje(obtenerBusCompleto(codBus, arrBuses));
                    dto.setRutaViaje(obtenerRutaCompleta(codRuta, arrRutas));
                    dto.setConductorViaje(obtenerConductorCompleto(codConductor, arrConductores));

                    arregloViaje.add(dto);
                }
            } catch (Exception error) {
                Logger.getLogger(ViajeServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arregloViaje;
    }

    @Override
    public int numRows() {
        int cantidad = 0;
        try {
            cantidad = miArchivo.cantidadFilas();
        } catch (IOException ex) {
            Logger.getLogger(ViajeServicio.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ViajeServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }

    @Override
    public ViajeDto getOne(Integer codigo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ViajeDto updateSet(Integer codigo, ViajeDto objeto, String ruta) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
