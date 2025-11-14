package org.poo.modelo;

public class Pasajero {
    
    private Integer idPasajero;
    private String nombrePasajero;
    private String cedulaPasajero;
    private String telefonoPasajero;
    private String emailPasajero;
    private String nombreImagenPublicoPasajero;
    private String nombreImagenPrivadoPasajero;

    public Pasajero() {
    }

    public Pasajero(Integer idPasajero, String nombrePasajero, String cedulaPasajero,
                   String telefonoPasajero, String emailPasajero,
                   String nombreImagenPublicoPasajero, String nombreImagenPrivadoPasajero) {
        this.idPasajero = idPasajero;
        this.nombrePasajero = nombrePasajero;
        this.cedulaPasajero = cedulaPasajero;
        this.telefonoPasajero = telefonoPasajero;
        this.emailPasajero = emailPasajero;
        this.nombreImagenPublicoPasajero = nombreImagenPublicoPasajero;
        this.nombreImagenPrivadoPasajero = nombreImagenPrivadoPasajero;
    }

    // Getters y Setters
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

    public String getCedulaPasajero() {
        return cedulaPasajero;
    }

    public void setCedulaPasajero(String cedulaPasajero) {
        this.cedulaPasajero = cedulaPasajero;
    }

    public String getTelefonoPasajero() {
        return telefonoPasajero;
    }

    public void setTelefonoPasajero(String telefonoPasajero) {
        this.telefonoPasajero = telefonoPasajero;
    }

    public String getEmailPasajero() {
        return emailPasajero;
    }

    public void setEmailPasajero(String emailPasajero) {
        this.emailPasajero = emailPasajero;
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
        return nombrePasajero + " - " + cedulaPasajero;
    }
}