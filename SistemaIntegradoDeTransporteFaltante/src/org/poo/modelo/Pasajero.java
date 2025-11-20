package org.poo.modelo;

import java.time.LocalDate;

public class Pasajero {

    private Integer idPasajero;
    private String nombrePasajero;
    private String documentoPasajero;
    private String tipoDocumentoPasajero; // "Cédula", "Pasaporte", "Tarjeta de identidad"
    private Boolean esMayorPasajero;
    private LocalDate fechaNacimientoPasajero;
    private String telefonoPasajero;
    private String nombreImagenPublicoPasajero;
    private String nombreImagenPrivadoPasajero;

    public Pasajero() {
    }

    public Pasajero(Integer idPasajero, String nombrePasajero, String documentoPasajero, String tipoDocumentoPasajero, Boolean esMayorPasajero, LocalDate fechaNacimientoPasajero, String telefonoPasajero, String nombreImagenPublicoPasajero, String nombreImagenPrivadoPasajero) {
        this.idPasajero = idPasajero;
        this.nombrePasajero = nombrePasajero;
        this.documentoPasajero = documentoPasajero;
        this.tipoDocumentoPasajero = tipoDocumentoPasajero;
        this.esMayorPasajero = esMayorPasajero;
        this.fechaNacimientoPasajero = fechaNacimientoPasajero;
        this.telefonoPasajero = telefonoPasajero;
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

    public String getTipoDocumentoPasajero() {
        return tipoDocumentoPasajero;
    }

    public void setTipoDocumentoPasajero(String tipoDocumentoPasajero) {
        this.tipoDocumentoPasajero = tipoDocumentoPasajero;
    }

    public Boolean getEsMayorPasajero() {
        return esMayorPasajero;
    }

    public void setEsMayorPasajero(Boolean esMayorPasajero) {
        this.esMayorPasajero = esMayorPasajero;
    }

    public LocalDate getFechaNacimientoPasajero() {
        return fechaNacimientoPasajero;
    }

    public void setFechaNacimientoPasajero(LocalDate fechaNacimientoPasajero) {
        this.fechaNacimientoPasajero = fechaNacimientoPasajero;
    }

    public String getTelefonoPasajero() {
        return telefonoPasajero;
    }

    public void setTelefonoPasajero(String telefonoPasajero) {
        this.telefonoPasajero = telefonoPasajero;
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
