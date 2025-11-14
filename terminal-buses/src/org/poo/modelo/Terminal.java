package org.poo.modelo;

public class Terminal {
    
    private Integer idTerminal;
    private String nombreTerminal;
    private String ciudadTerminal;
    private String direccionTerminal;
    private Boolean estadoTerminal;
    private Short cantidadEmpresasTerminal;
    private String nombreImagenPublicoTerminal;
    private String nombreImagenPrivadoTerminal;

    public Terminal() {
    }

    public Terminal(Integer idTerminal, String nombreTerminal, String ciudadTerminal, 
                   String direccionTerminal, Boolean estadoTerminal, Short cantidadEmpresasTerminal,
                   String nombreImagenPublicoTerminal, String nombreImagenPrivadoTerminal) {
        this.idTerminal = idTerminal;
        this.nombreTerminal = nombreTerminal;
        this.ciudadTerminal = ciudadTerminal;
        this.direccionTerminal = direccionTerminal;
        this.estadoTerminal = estadoTerminal;
        this.cantidadEmpresasTerminal = cantidadEmpresasTerminal;
        this.nombreImagenPublicoTerminal = nombreImagenPublicoTerminal;
        this.nombreImagenPrivadoTerminal = nombreImagenPrivadoTerminal;
    }

    // Getters y Setters
    public Integer getIdTerminal() {
        return idTerminal;
    }

    public void setIdTerminal(Integer idTerminal) {
        this.idTerminal = idTerminal;
    }

    public String getNombreTerminal() {
        return nombreTerminal;
    }

    public void setNombreTerminal(String nombreTerminal) {
        this.nombreTerminal = nombreTerminal;
    }

    public String getCiudadTerminal() {
        return ciudadTerminal;
    }

    public void setCiudadTerminal(String ciudadTerminal) {
        this.ciudadTerminal = ciudadTerminal;
    }

    public String getDireccionTerminal() {
        return direccionTerminal;
    }

    public void setDireccionTerminal(String direccionTerminal) {
        this.direccionTerminal = direccionTerminal;
    }

    public Boolean getEstadoTerminal() {
        return estadoTerminal;
    }

    public void setEstadoTerminal(Boolean estadoTerminal) {
        this.estadoTerminal = estadoTerminal;
    }

    public Short getCantidadEmpresasTerminal() {
        return cantidadEmpresasTerminal;
    }

    public void setCantidadEmpresasTerminal(Short cantidadEmpresasTerminal) {
        this.cantidadEmpresasTerminal = cantidadEmpresasTerminal;
    }

    public String getNombreImagenPublicoTerminal() {
        return nombreImagenPublicoTerminal;
    }

    public void setNombreImagenPublicoTerminal(String nombreImagenPublicoTerminal) {
        this.nombreImagenPublicoTerminal = nombreImagenPublicoTerminal;
    }

    public String getNombreImagenPrivadoTerminal() {
        return nombreImagenPrivadoTerminal;
    }

    public void setNombreImagenPrivadoTerminal(String nombreImagenPrivadoTerminal) {
        this.nombreImagenPrivadoTerminal = nombreImagenPrivadoTerminal;
    }

    @Override
    public String toString() {
        return nombreTerminal;
    }
}