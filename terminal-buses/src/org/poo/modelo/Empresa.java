package org.poo.modelo;

public class Empresa {
    
    private Integer idEmpresa;
    private String nombreEmpresa;
    private String nitEmpresa;
    private Terminal terminalEmpresa;
    private Boolean estadoEmpresa;
    private Short cantidadBusesEmpresa;
    private String nombreImagenPublicoEmpresa;
    private String nombreImagenPrivadoEmpresa;

    public Empresa() {
    }

    public Empresa(Integer idEmpresa, String nombreEmpresa, String nitEmpresa, 
                  Terminal terminalEmpresa, Boolean estadoEmpresa, Short cantidadBusesEmpresa,
                  String nombreImagenPublicoEmpresa, String nombreImagenPrivadoEmpresa) {
        this.idEmpresa = idEmpresa;
        this.nombreEmpresa = nombreEmpresa;
        this.nitEmpresa = nitEmpresa;
        this.terminalEmpresa = terminalEmpresa;
        this.estadoEmpresa = estadoEmpresa;
        this.cantidadBusesEmpresa = cantidadBusesEmpresa;
        this.nombreImagenPublicoEmpresa = nombreImagenPublicoEmpresa;
        this.nombreImagenPrivadoEmpresa = nombreImagenPrivadoEmpresa;
    }

    // Getters y Setters
    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNitEmpresa() {
        return nitEmpresa;
    }

    public void setNitEmpresa(String nitEmpresa) {
        this.nitEmpresa = nitEmpresa;
    }

    public Terminal getTerminalEmpresa() {
        return terminalEmpresa;
    }

    public void setTerminalEmpresa(Terminal terminalEmpresa) {
        this.terminalEmpresa = terminalEmpresa;
    }

    public Boolean getEstadoEmpresa() {
        return estadoEmpresa;
    }

    public void setEstadoEmpresa(Boolean estadoEmpresa) {
        this.estadoEmpresa = estadoEmpresa;
    }

    public Short getCantidadBusesEmpresa() {
        return cantidadBusesEmpresa;
    }

    public void setCantidadBusesEmpresa(Short cantidadBusesEmpresa) {
        this.cantidadBusesEmpresa = cantidadBusesEmpresa;
    }

    public String getNombreImagenPublicoEmpresa() {
        return nombreImagenPublicoEmpresa;
    }

    public void setNombreImagenPublicoEmpresa(String nombreImagenPublicoEmpresa) {
        this.nombreImagenPublicoEmpresa = nombreImagenPublicoEmpresa;
    }

    public String getNombreImagenPrivadoEmpresa() {
        return nombreImagenPrivadoEmpresa;
    }

    public void setNombreImagenPrivadoEmpresa(String nombreImagenPrivadoEmpresa) {
        this.nombreImagenPrivadoEmpresa = nombreImagenPrivadoEmpresa;
    }

    @Override
    public String toString() {
        return nombreEmpresa;
    }
}