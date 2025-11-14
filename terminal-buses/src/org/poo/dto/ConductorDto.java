package org.poo.dto;

import java.time.LocalDate;

public class ConductorDto {
    
    private Integer idConductor;
    private String nombreConductor;
    private String cedulaConductor;
    private LocalDate fechaNacimientoConductor;
    private String telefonoConductor;
    private String licenciaConductor;
    private LocalDate fechaVencimientoLicencia;
    private EmpresaDto empresaConductor;
    private Boolean estadoConductor;
    private String nombreImagenPublicoConductor;
    private String nombreImagenPrivadoConductor;

    public ConductorDto() {
    }

    public ConductorDto(Integer idConductor, String nombreConductor, String cedulaConductor,
                       LocalDate fechaNacimientoConductor, String telefonoConductor,
                       String licenciaConductor, LocalDate fechaVencimientoLicencia,
                       EmpresaDto empresaConductor, Boolean estadoConductor,
                       String nombreImagenPublicoConductor, String nombreImagenPrivadoConductor) {
        this.idConductor = idConductor;
        this.nombreConductor = nombreConductor;
        this.cedulaConductor = cedulaConductor;
        this.fechaNacimientoConductor = fechaNacimientoConductor;
        this.telefonoConductor = telefonoConductor;
        this.licenciaConductor = licenciaConductor;
        this.fechaVencimientoLicencia = fechaVencimientoLicencia;
        this.empresaConductor = empresaConductor;
        this.estadoConductor = estadoConductor;
        this.nombreImagenPublicoConductor = nombreImagenPublicoConductor;
        this.nombreImagenPrivadoConductor = nombreImagenPrivadoConductor;
    }

    // Getters y Setters
    public Integer getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(Integer idConductor) {
        this.idConductor = idConductor;
    }

    public String getNombreConductor() {
        return nombreConductor;
    }

    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
    }

    public String getCedulaConductor() {
        return cedulaConductor;
    }

    public void setCedulaConductor(String cedulaConductor) {
        this.cedulaConductor = cedulaConductor;
    }

    public LocalDate getFechaNacimientoConductor() {
        return fechaNacimientoConductor;
    }

    public void setFechaNacimientoConductor(LocalDate fechaNacimientoConductor) {
        this.fechaNacimientoConductor = fechaNacimientoConductor;
    }

    public String getTelefonoConductor() {
        return telefonoConductor;
    }

    public void setTelefonoConductor(String telefonoConductor) {
        this.telefonoConductor = telefonoConductor;
    }

    public String getLicenciaConductor() {
        return licenciaConductor;
    }

    public void setLicenciaConductor(String licenciaConductor) {
        this.licenciaConductor = licenciaConductor;
    }

    public LocalDate getFechaVencimientoLicencia() {
        return fechaVencimientoLicencia;
    }

    public void setFechaVencimientoLicencia(LocalDate fechaVencimientoLicencia) {
        this.fechaVencimientoLicencia = fechaVencimientoLicencia;
    }

    public EmpresaDto getEmpresaConductor() {
        return empresaConductor;
    }

    public void setEmpresaConductor(EmpresaDto empresaConductor) {
        this.empresaConductor = empresaConductor;
    }

    public Boolean getEstadoConductor() {
        return estadoConductor;
    }

    public void setEstadoConductor(Boolean estadoConductor) {
        this.estadoConductor = estadoConductor;
    }

    public String getNombreImagenPublicoConductor() {
        return nombreImagenPublicoConductor;
    }

    public void setNombreImagenPublicoConductor(String nombreImagenPublicoConductor) {
        this.nombreImagenPublicoConductor = nombreImagenPublicoConductor;
    }

    public String getNombreImagenPrivadoConductor() {
        return nombreImagenPrivadoConductor;
    }

    public void setNombreImagenPrivadoConductor(String nombreImagenPrivadoConductor) {
        this.nombreImagenPrivadoConductor = nombreImagenPrivadoConductor;
    }

    @Override
    public String toString() {
        return nombreConductor + " - " + cedulaConductor;
    }
}