package org.poo.dto;

import java.time.LocalDate;

public class PasajeroDto {
    
    private Integer idPasajero;
    private String nombrePasajero;
    private String documentoPasajero;
    private LocalDate fechaNacimientoPasajero;
    private Boolean esMayorPasajero;
    private String telefonoPasajero;
    private String tipoDocumentoPasajero; // "Cédula", "Pasaporte", "Tarjeta de identidad"
    private String nombreImagenPublicoPasajero;
    private String nombreImagenPrivadoPasajero;

    public PasajeroDto() {
    }

    public PasajeroDto(Integer idPasajero, String nombrePasajero, String documentoPasajero, LocalDate fechaNacimientoPasajero, Boolean esMayorPasajero, String telefonoPasajero, String tipoDocumentoPasajero, String nombreImagenPublicoPasajero, String nombreImagenPrivadoPasajero) {
        this.idPasajero = idPasajero;
        this.nombrePasajero = nombrePasajero;
        this.documentoPasajero = documentoPasajero;
        this.fechaNacimientoPasajero = fechaNacimientoPasajero;
        this.esMayorPasajero = esMayorPasajero;
        this.telefonoPasajero = telefonoPasajero;
        this.tipoDocumentoPasajero = tipoDocumentoPasajero;
        this.nombreImagenPublicoPasajero = nombreImagenPublicoPasajero;
        this.nombreImagenPrivadoPasajero = nombreImagenPrivadoPasajero;
    }

    public Integer getIdPasajero() {
        return idPasajero;
    }

    public void setIdPasajero(Integer idPasajero) {
        this.idPasajero = idPasajero;
    }

    public String getNombrePasajero() {
        return nombrePasajero;
    }

    public void setNombrePasajero(String nombrePasajero) {
        this.nombrePasajero = nombrePasajero;
    }

    public String getDocumentoPasajero() {
        return documentoPasajero;
    }

    public void setDocumentoPasajero(String documentoPasajero) {
        this.documentoPasajero = documentoPasajero;
    }

    public LocalDate getFechaNacimientoPasajero() {
        return fechaNacimientoPasajero;
    }

    public void setFechaNacimientoPasajero(LocalDate fechaNacimientoPasajero) {
        this.fechaNacimientoPasajero = fechaNacimientoPasajero;
    }

    public Boolean getEsMayorPasajero() {
        return esMayorPasajero;
    }

    public void setEsMayorPasajero(Boolean esMayorPasajero) {
        this.esMayorPasajero = esMayorPasajero;
    }

    public String getTelefonoPasajero() {
        return telefonoPasajero;
    }

    public void setTelefonoPasajero(String telefonoPasajero) {
        this.telefonoPasajero = telefonoPasajero;
    }

    public String getTipoDocumentoPasajero() {
        return tipoDocumentoPasajero;
    }

    public void setTipoDocumentoPasajero(String tipoDocumentoPasajero) {
        this.tipoDocumentoPasajero = tipoDocumentoPasajero;
    }

    public String getNombreImagenPublicoPasajero() {
        return nombreImagenPublicoPasajero;
    }

    public void setNombreImagenPublicoPasajero(String nombreImagenPublicoPasajero) {
        this.nombreImagenPublicoPasajero = nombreImagenPublicoPasajero;
    }

    public String getNombreImagenPrivadoPasajero() {
        return nombreImagenPrivadoPasajero;
    }

    public void setNombreImagenPrivadoPasajero(String nombreImagenPrivadoPasajero) {
        this.nombreImagenPrivadoPasajero = nombreImagenPrivadoPasajero;
    }

    @Override
    public String toString() {
        return nombrePasajero + " - " + documentoPasajero;
    }
}
