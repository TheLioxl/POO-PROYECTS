package org.poo.dto;

import java.time.LocalDateTime;

public class TiqueteDto {

    private Integer idTiquete;
    private ViajeDto viajeTiquete;
    private PasajeroDto pasajeroTiquete;
    private Integer numeroAsientoTiquete;
    private Double precioTiquete;
    private String categoriaTiquete;
    private LocalDateTime fechaCompraTiquete;
    private String metodoPagoTiquete; // "Efectivo", "Tarjeta", "Transferencia"
    private Boolean equipajeExtraTiquete;
    private String nombreImagenPublicoTiquete;
    private String nombreImagenPrivadoTiquete;

    public TiqueteDto() {
    }

    public TiqueteDto(Integer idTiquete, ViajeDto viajeTiquete, PasajeroDto pasajeroTiquete, Integer numeroAsientoTiquete, Double precioTiquete, String categoriaTiquete, LocalDateTime fechaCompraTiquete, String metodoPagoTiquete, Boolean equipajeExtraTiquete, String nombreImagenPublicoTiquete, String nombreImagenPrivadoTiquete) {
        this.idTiquete = idTiquete;
        this.viajeTiquete = viajeTiquete;
        this.pasajeroTiquete = pasajeroTiquete;
        this.numeroAsientoTiquete = numeroAsientoTiquete;
        this.precioTiquete = precioTiquete;
        this.categoriaTiquete = categoriaTiquete;
        this.fechaCompraTiquete = fechaCompraTiquete;
        this.metodoPagoTiquete = metodoPagoTiquete;
        this.equipajeExtraTiquete = equipajeExtraTiquete;
        this.nombreImagenPublicoTiquete = nombreImagenPublicoTiquete;
        this.nombreImagenPrivadoTiquete = nombreImagenPrivadoTiquete;
    }

    public Integer getIdTiquete() {
        return idTiquete;
    }

    public void setIdTiquete(Integer idTiquete) {
        this.idTiquete = idTiquete;
    }

    public ViajeDto getViajeTiquete() {
        return viajeTiquete;
    }

    public void setViajeTiquete(ViajeDto viajeTiquete) {
        this.viajeTiquete = viajeTiquete;
    }

    public PasajeroDto getPasajeroTiquete() {
        return pasajeroTiquete;
    }

    public void setPasajeroTiquete(PasajeroDto pasajeroTiquete) {
        this.pasajeroTiquete = pasajeroTiquete;
    }

    public Integer getNumeroAsientoTiquete() {
        return numeroAsientoTiquete;
    }

    public void setNumeroAsientoTiquete(Integer numeroAsientoTiquete) {
        this.numeroAsientoTiquete = numeroAsientoTiquete;
    }

    public Double getPrecioTiquete() {
        return precioTiquete;
    }

    public void setPrecioTiquete(Double precioTiquete) {
        this.precioTiquete = precioTiquete;
    }

    public String getCategoriaTiquete() {
        return categoriaTiquete;
    }

    public void setCategoriaTiquete(String categoriaTiquete) {
        this.categoriaTiquete = categoriaTiquete;
    }

    public LocalDateTime getFechaCompraTiquete() {
        return fechaCompraTiquete;
    }

    public void setFechaCompraTiquete(LocalDateTime fechaCompraTiquete) {
        this.fechaCompraTiquete = fechaCompraTiquete;
    }

    public String getMetodoPagoTiquete() {
        return metodoPagoTiquete;
    }

    public void setMetodoPagoTiquete(String metodoPagoTiquete) {
        this.metodoPagoTiquete = metodoPagoTiquete;
    }

    public Boolean getEquipajeExtraTiquete() {
        return equipajeExtraTiquete;
    }

    public void setEquipajeExtraTiquete(Boolean equipajeExtraTiquete) {
        this.equipajeExtraTiquete = equipajeExtraTiquete;
    }

    public String getNombreImagenPublicoTiquete() {
        return nombreImagenPublicoTiquete;
    }

    public void setNombreImagenPublicoTiquete(String nombreImagenPublicoTiquete) {
        this.nombreImagenPublicoTiquete = nombreImagenPublicoTiquete;
    }

    public String getNombreImagenPrivadoTiquete() {
        return nombreImagenPrivadoTiquete;
    }

    public void setNombreImagenPrivadoTiquete(String nombreImagenPrivadoTiquete) {
        this.nombreImagenPrivadoTiquete = nombreImagenPrivadoTiquete;
    }

    @Override
    public String toString() {
        return "Tiquete #" + idTiquete + " - Asiento " + numeroAsientoTiquete;
    }
}
