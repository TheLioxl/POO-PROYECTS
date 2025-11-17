package org.poo.modelo;

import java.time.LocalDate;

public class Empresa {
    
    private Integer idEmpresa;
    private String nombreEmpresa;
    private String nitEmpresa;
    private Terminal terminalEmpresa;
    private Boolean estadoEmpresa;
    private Short cantidadBusesEmpresa;
    private String nombreImagenPublicoEmpresa;
    private String nombreImagenPrivadoEmpresa;
    private LocalDate fechaFundacion;
    private Integer cantidadEmpleados;
    private Boolean servicio24Horas;
    private Boolean tieneMantenimientoPropio;
    private Boolean tieneServicioCliente;
    private String descripcionEmpresa;

    public Empresa() {
    }

    public Empresa(Integer idEmpresa, String nombreEmpresa, String nitEmpresa, 
                  Terminal terminalEmpresa, Boolean estadoEmpresa, Short cantidadBusesEmpresa,
                  String nombreImagenPublicoEmpresa, String nombreImagenPrivadoEmpresa,
                  LocalDate fechaFundacion, Integer cantidadEmpleados, Boolean servicio24Horas,
                  Boolean tieneMantenimientoPropio, Boolean tieneServicioCliente, String descripcionEmpresa) {
        this.idEmpresa = idEmpresa;
        this.nombreEmpresa = nombreEmpresa;
        this.nitEmpresa = nitEmpresa;
        this.terminalEmpresa = terminalEmpresa;
        this.estadoEmpresa = estadoEmpresa;
        this.cantidadBusesEmpresa = cantidadBusesEmpresa;
        this.nombreImagenPublicoEmpresa = nombreImagenPublicoEmpresa;
        this.nombreImagenPrivadoEmpresa = nombreImagenPrivadoEmpresa;
        this.fechaFundacion = fechaFundacion;
        this.cantidadEmpleados = cantidadEmpleados;
        this.servicio24Horas = servicio24Horas;
        this.tieneMantenimientoPropio = tieneMantenimientoPropio;
        this.tieneServicioCliente = tieneServicioCliente;
        this.descripcionEmpresa = descripcionEmpresa;
    }

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

    public LocalDate getFechaFundacion() {
        return fechaFundacion;
    }

    public void setFechaFundacion(LocalDate fechaFundacion) {
        this.fechaFundacion = fechaFundacion;
    }

    public Integer getCantidadEmpleados() {
        return cantidadEmpleados;
    }

    public void setCantidadEmpleados(Integer cantidadEmpleados) {
        this.cantidadEmpleados = cantidadEmpleados;
    }

    public Boolean getServicio24Horas() {
        return servicio24Horas;
    }

    public void setServicio24Horas(Boolean servicio24Horas) {
        this.servicio24Horas = servicio24Horas;
    }

    public Boolean getTieneMantenimientoPropio() {
        return tieneMantenimientoPropio;
    }

    public void setTieneMantenimientoPropio(Boolean tieneMantenimientoPropio) {
        this.tieneMantenimientoPropio = tieneMantenimientoPropio;
    }

    public Boolean getTieneServicioCliente() {
        return tieneServicioCliente;
    }

    public void setTieneServicioCliente(Boolean tieneServicioCliente) {
        this.tieneServicioCliente = tieneServicioCliente;
    }

    public String getDescripcionEmpresa() {
        return descripcionEmpresa;
    }

    public void setDescripcionEmpresa(String descripcionEmpresa) {
        this.descripcionEmpresa = descripcionEmpresa;
    }

    @Override
    public String toString() {
        return nombreEmpresa;
    }
}
