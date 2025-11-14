package org.poo.modelo;

public class Conductor {
    
    private Integer idConductor;
    private String nombreConductor;
    private String cedulaConductor;
    private String licenciaConductor;
    private Empresa empresaConductor;
    private Boolean estadoConductor;
    private String nombreImagenPublicoConductor;
    private String nombreImagenPrivadoConductor;

    public Conductor() {
    }

    public Conductor(Integer idConductor, String nombreConductor, String cedulaConductor,
                    String licenciaConductor, Empresa empresaConductor, Boolean estadoConductor,
                    String nombreImagenPublicoConductor, String nombreImagenPrivadoConductor) {
        this.idConductor = idConductor;
        this.nombreConductor = nombreConductor;
        this.cedulaConductor = cedulaConductor;
        this.licenciaConductor = licenciaConductor;
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

    public String getLicenciaConductor() {
        return licenciaConductor;
    }

    public void setLicenciaConductor(String licenciaConductor) {
        this.licenciaConductor = licenciaConductor;
    }

    public Empresa getEmpresaConductor() {
        return empresaConductor;
    }

    public void setEmpresaConductor(Empresa empresaConductor) {
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