package org.poo.dto;

public class BusDto {
    
    private Integer idBus;
    private String placaBus;
    private String modeloBus;
    private Integer capacidadBus;
    private EmpresaDto empresaBus;
    private String tipoBus; // Ejecutivo, Normal, VIP
    private Boolean estadoBus;
    private String nombreImagenPublicoBus;
    private String nombreImagenPrivadoBus;

    public BusDto() {
    }

    public BusDto(Integer idBus, String placaBus, String modeloBus, Integer capacidadBus,
                  EmpresaDto empresaBus, String tipoBus, Boolean estadoBus,
                  String nombreImagenPublicoBus, String nombreImagenPrivadoBus) {
        this.idBus = idBus;
        this.placaBus = placaBus;
        this.modeloBus = modeloBus;
        this.capacidadBus = capacidadBus;
        this.empresaBus = empresaBus;
        this.tipoBus = tipoBus;
        this.estadoBus = estadoBus;
        this.nombreImagenPublicoBus = nombreImagenPublicoBus;
        this.nombreImagenPrivadoBus = nombreImagenPrivadoBus;
    }

    // Getters y Setters
    public Integer getIdBus() {
        return idBus;
    }

    public void setIdBus(Integer idBus) {
        this.idBus = idBus;
    }

    public String getPlacaBus() {
        return placaBus;
    }

    public void setPlacaBus(String placaBus) {
        this.placaBus = placaBus;
    }

    public String getModeloBus() {
        return modeloBus;
    }

    public void setModeloBus(String modeloBus) {
        this.modeloBus = modeloBus;
    }

    public Integer getCapacidadBus() {
        return capacidadBus;
    }

    public void setCapacidadBus(Integer capacidadBus) {
        this.capacidadBus = capacidadBus;
    }

    public EmpresaDto getEmpresaBus() {
        return empresaBus;
    }

    public void setEmpresaBus(EmpresaDto empresaBus) {
        this.empresaBus = empresaBus;
    }

    public String getTipoBus() {
        return tipoBus;
    }

    public void setTipoBus(String tipoBus) {
        this.tipoBus = tipoBus;
    }

    public Boolean getEstadoBus() {
        return estadoBus;
    }

    public void setEstadoBus(Boolean estadoBus) {
        this.estadoBus = estadoBus;
    }

    public String getNombreImagenPublicoBus() {
        return nombreImagenPublicoBus;
    }

    public void setNombreImagenPublicoBus(String nombreImagenPublicoBus) {
        this.nombreImagenPublicoBus = nombreImagenPublicoBus;
    }

    public String getNombreImagenPrivadoBus() {
        return nombreImagenPrivadoBus;
    }

    public void setNombreImagenPrivadoBus(String nombreImagenPrivadoBus) {
        this.nombreImagenPrivadoBus = nombreImagenPrivadoBus;
    }

    @Override
    public String toString() {
        return placaBus + " - " + modeloBus;
    }
}