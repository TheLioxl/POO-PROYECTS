package org.poo.dto;

public class TerminalDto {

    private Integer idTerminal;
    private String nombreTerminal;
    private String ciudadTerminal;
    private String direccionTerminal;
    private Boolean estadoTerminal;
    private Short cantidadEmpresasTerminal;
    private Integer numeroPlataformas;
    private Boolean tieneWifi;
    private Boolean tieneCafeteria;
    private Boolean tieneBanos;
    private String nombreImagenPublicoTerminal;
    private String nombreImagenPrivadoTerminal;

    public TerminalDto() {
    }

    public TerminalDto(Integer idTerminal, String nombreTerminal, String ciudadTerminal,
                      String direccionTerminal, Boolean estadoTerminal, Short cantidadEmpresasTerminal,
                      Integer numeroPlataformas, Boolean tieneWifi, Boolean tieneCafeteria, Boolean tieneBanos) {
        this.idTerminal = idTerminal;
        this.nombreTerminal = nombreTerminal;
        this.ciudadTerminal = ciudadTerminal;
        this.direccionTerminal = direccionTerminal;
        this.estadoTerminal = estadoTerminal;
        this.cantidadEmpresasTerminal = cantidadEmpresasTerminal;
        this.numeroPlataformas = numeroPlataformas;
        this.tieneWifi = tieneWifi;
        this.tieneCafeteria = tieneCafeteria;
        this.tieneBanos = tieneBanos;
    }

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

    public Integer getNumeroPlataformas() {
        return numeroPlataformas;
    }

    public void setNumeroPlataformas(Integer numeroPlataformas) {
        this.numeroPlataformas = numeroPlataformas;
    }

    public Boolean getTieneWifi() {
        return tieneWifi;
    }

    public void setTieneWifi(Boolean tieneWifi) {
        this.tieneWifi = tieneWifi;
    }

    public Boolean getTieneCafeteria() {
        return tieneCafeteria;
    }

    public void setTieneCafeteria(Boolean tieneCafeteria) {
        this.tieneCafeteria = tieneCafeteria;
    }

    public Boolean getTieneBanos() {
        return tieneBanos;
    }

    public void setTieneBanos(Boolean tieneBanos) {
        this.tieneBanos = tieneBanos;
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