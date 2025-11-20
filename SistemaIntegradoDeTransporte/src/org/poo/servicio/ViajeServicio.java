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
import org.poo.dto.TiqueteDto;
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
        objViaje.setHoraLlegadaViaje(dto.getHoraLlegadaViaje());
        objViaje.setPrecioViaje(dto.getPrecioViaje());
        objViaje.setAsientosDisponiblesViaje(dto.getAsientosDisponiblesViaje());
        objViaje.setEstadoViaje(dto.getEstadoViaje());
        objViaje.setViajeDirecto(dto.getViajeDirecto());
        objViaje.setIncluyeRefrigerio(dto.getIncluyeRefrigerio());
        objViaje.setTieneParadasIntermedias(dto.getTieneParadasIntermedias());
        objViaje.setDescripcionViaje(dto.getDescripcionViaje());
        objViaje.setNotasAdicionalesViaje(dto.getNotasAdicionalesViaje());
        objViaje.setNombreImagenPublicoViaje(dto.getNombreImagenPublicoViaje());
        objViaje.setNombreImagenPrivadoViaje(GestorImagen.grabarLaImagen(ruta));

        String filaGrabar = objViaje.getIdViaje() + Persistencia.SEPARADOR_COLUMNAS
                + dto.getBusViaje().getIdBus() + Persistencia.SEPARADOR_COLUMNAS
                + dto.getRutaViaje().getIdRuta() + Persistencia.SEPARADOR_COLUMNAS
                + dto.getConductorViaje().getIdConductor() + Persistencia.SEPARADOR_COLUMNAS
                + objViaje.getFechaViaje().toString() + Persistencia.SEPARADOR_COLUMNAS
                + objViaje.getHoraSalidaViaje().toString() + Persistencia.SEPARADOR_COLUMNAS
                + objViaje.getHoraLlegadaViaje().toString() + Persistencia.SEPARADOR_COLUMNAS
                + objViaje.getPrecioViaje() + Persistencia.SEPARADOR_COLUMNAS
                + objViaje.getAsientosDisponiblesViaje() + Persistencia.SEPARADOR_COLUMNAS
                + objViaje.getEstadoViaje() + Persistencia.SEPARADOR_COLUMNAS
                + objViaje.getViajeDirecto() + Persistencia.SEPARADOR_COLUMNAS
                + objViaje.getIncluyeRefrigerio() + Persistencia.SEPARADOR_COLUMNAS
                + objViaje.getTieneParadasIntermedias() + Persistencia.SEPARADOR_COLUMNAS
                + objViaje.getDescripcionViaje() + Persistencia.SEPARADOR_COLUMNAS
                + objViaje.getNotasAdicionalesViaje() + Persistencia.SEPARADOR_COLUMNAS
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

                ViajeDto dto = new ViajeDto();

                int codViaje = Integer.parseInt(columnas[0].trim());
                int codBus = Integer.parseInt(columnas[1].trim());
                int codRuta = Integer.parseInt(columnas[2].trim());
                int codConductor = Integer.parseInt(columnas[3].trim());
                LocalDate fecha = LocalDate.parse(columnas[4].trim());
                LocalTime horaSalida = LocalTime.parse(columnas[5].trim());
                // --- NUEVO: cargar tiquetes asociados al viaje ---
                TiqueteServicio tiqueteServicio = new TiqueteServicio();
                List<TiqueteDto> arrTiquetes = tiqueteServicio.selectFrom();

                // filtrar solo los tiquetes que pertenecen a este viaje
                List<TiqueteDto> tiquetesDelViaje = new ArrayList<>();
                for (TiqueteDto t : arrTiquetes) {
                    if (t.getViajeTiquete() != null
                            && Objects.equals(t.getViajeTiquete().getIdViaje(), dto.getIdViaje())) {
                        tiquetesDelViaje.add(t);
                    }
                }

                dto.setTiquetesViaje(tiquetesDelViaje);

                dto.setIdViaje(codViaje);
                dto.setFechaViaje(fecha);
                dto.setHoraSalidaViaje(horaSalida);

                if (columnas.length >= 17) {
                    LocalTime horaLlegada = LocalTime.parse(columnas[6].trim());
                    Double precio = Double.parseDouble(columnas[7].trim());
                    Integer asientos = Integer.parseInt(columnas[8].trim());
                    Boolean estado = Boolean.valueOf(columnas[9].trim());
                    Boolean viajeDirecto = Boolean.valueOf(columnas[10].trim());
                    Boolean incluyeRefrigerio = Boolean.valueOf(columnas[11].trim());
                    Boolean tieneParadas = Boolean.valueOf(columnas[12].trim());
                    String descripcion = columnas[13].trim();
                    String notas = columnas[14].trim();
                    String npub = columnas[15].trim();
                    String nocu = columnas[16].trim();

                    dto.setHoraLlegadaViaje(horaLlegada);
                    dto.setPrecioViaje(precio);
                    dto.setAsientosDisponiblesViaje(asientos);
                    dto.setEstadoViaje(estado);
                    dto.setViajeDirecto(viajeDirecto);
                    dto.setIncluyeRefrigerio(incluyeRefrigerio);
                    dto.setTieneParadasIntermedias(tieneParadas);
                    dto.setDescripcionViaje(descripcion);
                    dto.setNotasAdicionalesViaje(notas);
                    dto.setNombreImagenPublicoViaje(npub);
                    dto.setNombreImagenPrivadoViaje(nocu);
                } else {
                    Double precio = Double.parseDouble(columnas[6].trim());
                    Integer asientos = Integer.parseInt(columnas[7].trim());
                    Boolean estado = Boolean.valueOf(columnas[8].trim());
                    String npub = columnas[9].trim();
                    String nocu = columnas[10].trim();

                    dto.setHoraLlegadaViaje(horaSalida.plusHours(4));
                    dto.setPrecioViaje(precio);
                    dto.setAsientosDisponiblesViaje(asientos);
                    dto.setEstadoViaje(estado);
                    dto.setViajeDirecto(true);
                    dto.setIncluyeRefrigerio(false);
                    dto.setTieneParadasIntermedias(false);
                    dto.setDescripcionViaje("Viaje estándar");
                    dto.setNotasAdicionalesViaje("Sin notas adicionales");
                    dto.setNombreImagenPublicoViaje(npub);
                    dto.setNombreImagenPrivadoViaje(nocu);
                }

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
        List<ViajeDto> todosLosViajes = selectFrom();
        List<ViajeDto> viajesActivos = new ArrayList<>();

        for (ViajeDto viaje : todosLosViajes) {
            if (Boolean.TRUE.equals(viaje.getEstadoViaje())) {
                viajesActivos.add(viaje);
            }
        }

        return viajesActivos;
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
        int contador = 0;
        ViajeDto objListo = new ViajeDto();
        List<ViajeDto> arrViajes = selectFrom();

        for (ViajeDto objViaje : arrViajes) {
            if (contador == codigo) {
                objListo = objViaje;
                break;
            }
            contador++;
        }
        return objListo;
    }

    @Override
    public ViajeDto updateSet(Integer codigo, ViajeDto objeto, String ruta) {
        try {
            String cadena, nocu;
            List<String> arregloDatos;

            cadena = objeto.getIdViaje() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getBusViaje().getIdBus() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getRutaViaje().getIdRuta() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getConductorViaje().getIdConductor() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getFechaViaje().toString() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getHoraSalidaViaje().toString() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getHoraLlegadaViaje().toString() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getPrecioViaje() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getAsientosDisponiblesViaje() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getEstadoViaje() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getViajeDirecto() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getIncluyeRefrigerio() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getTieneParadasIntermedias() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getDescripcionViaje() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getNotasAdicionalesViaje() + Persistencia.SEPARADOR_COLUMNAS
                    + objeto.getNombreImagenPublicoViaje() + Persistencia.SEPARADOR_COLUMNAS;

            if (ruta.isBlank()) {
                cadena = cadena + objeto.getNombreImagenPrivadoViaje();
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
            Logger.getLogger(ViajeServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
