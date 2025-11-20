package org.poo.modelo;

import java.time.LocalDateTime;

public class Tiquete {

    private Integer idTiquete;
    private Viaje viajeTiquete;
    private Pasajero pasajeroTiquete;
    private Integer numeroAsientoTiquete;
    private Double precioTiquete;
    private String categoriaTiquete;
    private LocalDateTime fechaCompraTiquete;
    private String nombreImagenPublicoTiquete;
    private String nombreImagenPrivadoTiquete;

    public Tiquete() {
    }

    public Tiquete(Integer idTiquete, Viaje viajeTiquete, Pasajero pasajeroTiquete, Integer numeroAsientoTiquete, Double precioTiquete, String categoriaTiquete, LocalDateTime fechaCompraTiquete, String nombreImagenPublicoTiquete, String nombreImagenPrivadoTiquete) {
        this.idTiquete = idTiquete;
        this.viajeTiquete = viajeTiquete;
        this.pasajeroTiquete = pasajeroTiquete;
        this.numeroAsientoTiquete = numeroAsientoTiquete;
        this.precioTiquete = precioTiquete;
        this.categoriaTiquete = categoriaTiquete;
        this.fechaCompraTiquete = fechaCompraTiquete;
        this.nombreImagenPublicoTiquete = nombreImagenPublicoTiquete;
        this.nombreImagenPrivadoTiquete = nombreImagenPrivadoTiquete;
    }

    public Integer getIdTiquete() {
        return idTiquete;
    }

    public void setIdTiquete(Integer idTiquete) {
        this.idTiquete = idTiquete;
    }

    public Viaje getViajeTiquete() {
        return viajeTiquete;
    }

    public void setViajeTiquete(Viaje viajeTiquete) {
        this.viajeTiquete = viajeTiquete;
    }

    public Pasajero getPasajeroTiquete() {
        return pasajeroTiquete;
    }

    public void setPasajeroTiquete(Pasajero pasajeroTiquete) {
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
        return "Tiquete #" + idTiquete + " - Asiento: " + numeroAsientoTiquete;
    }
}
