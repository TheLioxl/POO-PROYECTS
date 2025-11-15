package org.poo.servicio;

import com.poo.persistence.NioFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.poo.api.ApiOperacionBD;
import org.poo.dto.PasajeroDto;
import org.poo.dto.TiqueteDto;
import org.poo.dto.ViajeDto;
import org.poo.modelo.Tiquete;
import org.poo.recurso.constante.Persistencia;
import org.poo.recurso.utilidad.GestorImagen;

public class TiqueteServicio implements ApiOperacionBD<TiqueteDto, Integer> {

    private NioFile miArchivo;
    private String nombrePersistencia;

    public TiqueteServicio() {
        nombrePersistencia = Persistencia.NOMBRE_TIQUETE;
        try {
            miArchivo = new NioFile(nombrePersistencia);
        } catch (IOException ex) {
            Logger.getLogger(TiqueteServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getSerial() {
        int id = 0;
        try {
            id = miArchivo.ultimoCodigo() + 1;
        } catch (IOException ex) {
            Logger.getLogger(TiqueteServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    @Override
    public TiqueteDto insertInto(TiqueteDto dto, String ruta) {
        Tiquete objTiquete = new Tiquete();

        objTiquete.setIdTiquete(getSerial());
        objTiquete.setNumeroAsientoTiquete(dto.getNumeroAsientoTiquete());
        objTiquete.setPrecioTiquete(dto.getPrecioTiquete());
        objTiquete.setFechaCompraTiquete(dto.getFechaCompraTiquete());
        objTiquete.setNombreImagenPublicoTiquete(dto.getNombreImagenPublicoTiquete());
        objTiquete.setNombreImagenPrivadoTiquete(GestorImagen.grabarLaImagen(ruta));

        String filaGrabar = objTiquete.getIdTiquete() + Persistencia.SEPARADOR_COLUMNAS
                + dto.getViajeTiquete().getIdViaje() + Persistencia.SEPARADOR_COLUMNAS
                + dto.getPasajeroTiquete().getIdPasajero() + Persistencia.SEPARADOR_COLUMNAS
                + objTiquete.getNumeroAsientoTiquete() + Persistencia.SEPARADOR_COLUMNAS
                + objTiquete.getPrecioTiquete() + Persistencia.SEPARADOR_COLUMNAS
                + objTiquete.getFechaCompraTiquete().toString() + Persistencia.SEPARADOR_COLUMNAS
                + objTiquete.getNombreImagenPublicoTiquete() + Persistencia.SEPARADOR_COLUMNAS
                + objTiquete.getNombreImagenPrivadoTiquete();

        if (miArchivo.agregarRegistro(filaGrabar)) {
            dto.setIdTiquete(objTiquete.getIdTiquete());
            return dto;
        }

        return null;
    }

    @Override
    public List<TiqueteDto> selectFrom() {
        ViajeServicio viajeServicio = new ViajeServicio();
        List<ViajeDto> arrViajes = viajeServicio.selectFrom();

        PasajeroServicio pasajeroServicio = new PasajeroServicio();
        List<PasajeroDto> arrPasajeros = pasajeroServicio.selectFrom();

        List<TiqueteDto> arregloTiquete = new ArrayList<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

                int codTiquete = Integer.parseInt(columnas[0].trim());
                int codViaje = Integer.parseInt(columnas[1].trim());
                int codPasajero = Integer.parseInt(columnas[2].trim());
                Integer numAsiento = Integer.parseInt(columnas[3].trim());
                Double precio = Double.parseDouble(columnas[4].trim());
                LocalDateTime fechaCompra = LocalDateTime.parse(columnas[5].trim());
                String npub = columnas[6].trim();
                String nocu = columnas[7].trim();

                TiqueteDto dto = new TiqueteDto();
                dto.setIdTiquete(codTiquete);
                dto.setNumeroAsientoTiquete(numAsiento);
                dto.setPrecioTiquete(precio);
                dto.setFechaCompraTiquete(fechaCompra);
                dto.setNombreImagenPublicoTiquete(npub);
                dto.setNombreImagenPrivadoTiquete(nocu);

                dto.setViajeTiquete(obtenerViajeCompleto(codViaje, arrViajes));
                dto.setPasajeroTiquete(obtenerPasajeroCompleto(codPasajero, arrPasajeros));

                arregloTiquete.add(dto);

            } catch (Exception error) {
                Logger.getLogger(TiqueteServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arregloTiquete;
    }

    private ViajeDto obtenerViajeCompleto(Integer codigoViaje, List<ViajeDto> arrViajes) {
        for (ViajeDto viajeExterno : arrViajes) {
            if (Objects.equals(codigoViaje, viajeExterno.getIdViaje())) {
                return viajeExterno;
            }
        }
        return null;
    }

    private PasajeroDto obtenerPasajeroCompleto(Integer codigoPasajero, List<PasajeroDto> arrPasajeros) {
        for (PasajeroDto pasajeroExterno : arrPasajeros) {
            if (Objects.equals(codigoPasajero, pasajeroExterno.getIdPasajero())) {
                return pasajeroExterno;
            }
        }
        return null;
    }

    @Override
    public List<TiqueteDto> selectFromWhereActivos() {
        return selectFrom();
    }

    @Override
    public int numRows() {
        int cantidad = 0;
        try {
            cantidad = miArchivo.cantidadFilas();
        } catch (IOException ex) {
            Logger.getLogger(TiqueteServicio.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(TiqueteServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }

    @Override
    public TiqueteDto getOne(Integer codigo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TiqueteDto updateSet(Integer codigo, TiqueteDto objeto, String ruta) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
