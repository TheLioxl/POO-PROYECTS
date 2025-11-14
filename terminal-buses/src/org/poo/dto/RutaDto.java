package org.poo.dto;

public class RutaDto {
    
    private Integer idRuta;
    private String nombreRuta;
    private String ciudadOrigenRuta;
    private String ciudadDestinoRuta;
    private Double distanciaKmRuta;
    private Integer duracionHorasRuta;
    private Boolean estadoRuta;
    private String nombreImagenPublicoRuta;
    private String nombreImagenPrivadoRuta;

    public RutaDto() {
    }

    public RutaDto(Integer idRuta, String nombreRuta, String ciudadOrigenRuta,
                  String ciudadDestinoRuta, Double distanciaKmRuta, Integer duracionHorasRuta,
                  Boolean estadoRuta, String nombreImagenPublicoRuta, String nombreImagenPrivadoRuta) {
        this.idRuta = idRuta;
        this.nombreRuta = nombreRuta;
        this.ciudadOrigenRuta = ciudadOrigenRuta;
        this.ciudadDestinoRuta = ciudadDestinoRuta;
        this.distanciaKmRuta = distanciaKmRuta;
        this.duracionHorasRuta = duracionHorasRuta;
        this.estadoRuta = estadoRuta;
        this.nombreImagenPublicoRuta = nombreImagenPublicoRuta;
        this.nombreImagenPrivadoRuta = nombreImagenPrivadoRuta;
    }

    // Getters y Setters
    public Integer getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(Integer idRuta) {
        this.idRuta = idRuta;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    public String getCiudadOrigenRuta() {
        return ciudadOrigenRuta;
    }

    public void setCiudadOrigenRuta(String ciudadOrigenRuta) {
        this.ciudadOrigenRuta = ciudadOrigenRuta;
    }

    public String getCiudadDestinoRuta() {
        return ciudadDestinoRuta;
    }

    public void setCiudadDestinoRuta(String ciudadDestinoRuta) {
        this.ciudadDestinoRuta = ciudadDestinoRuta;
    }

    public Double getDistanciaKmRuta() {
        return distanciaKmRuta;
    }

    public void setDistanciaKmRuta(Double distanciaKmRuta) {
        this.distanciaKmRuta = distanciaKmRuta;
    }

    public Integer getDuracionHorasRuta() {
        return duracionHorasRuta;
    }

    public void setDuracionHorasRuta(Integer duracionHorasRuta) {
        this.duracionHorasRuta = duracionHorasRuta;
    }

    public Boolean getEstadoRuta() {
        return estadoRuta;
    }

    public void setEstadoRuta(Boolean estadoRuta) {
        this.estadoRuta = estadoRuta;
    }

    public String getNombreImagenPublicoRuta() {
        return nombreImagenPublicoRuta;
    }

    public void setNombreImagenPublicoRuta(String nombreImagenPublicoRuta) {
        this.nombreImagenPublicoRuta = nombreImagenPublicoRuta;
    }

    public String getNombreImagenPrivadoRuta() {
        return nombreImagenPrivadoRuta;
    }

    public void setNombreImagenPrivadoRuta(String nombreImagenPrivadoRuta) {
        this.nombreImagenPrivadoRuta = nombreImagenPrivadoRuta;
    }

    @Override
    public String toString() {
        return ciudadOrigenRuta + " → " + ciudadDestinoRuta;
    }
}