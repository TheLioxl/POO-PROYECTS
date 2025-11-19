package org.poo.dto;

public class DestinoDto {
    
    private Integer idDestino;
    private String nombreDestino;
    private String departamentoDestino;
    private String descripcionDestino;
    private Boolean estadoDestino;
    private String nombreImagenPublicoDestino;
    private String nombreImagenPrivadoDestino;

    public DestinoDto() {
    }

    public DestinoDto(Integer idDestino, String nombreDestino, String departamentoDestino,
                     String descripcionDestino, Boolean estadoDestino,
                     String nombreImagenPublicoDestino, String nombreImagenPrivadoDestino) {
        this.idDestino = idDestino;
        this.nombreDestino = nombreDestino;
        this.departamentoDestino = departamentoDestino;
        this.descripcionDestino = descripcionDestino;
        this.estadoDestino = estadoDestino;
        this.nombreImagenPublicoDestino = nombreImagenPublicoDestino;
        this.nombreImagenPrivadoDestino = nombreImagenPrivadoDestino;
    }

    // Getters y Setters
    public Integer getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(Integer idDestino) {
        this.idDestino = idDestino;
    }

    public String getNombreDestino() {
        return nombreDestino;
    }

    public void setNombreDestino(String nombreDestino) {
        this.nombreDestino = nombreDestino;
    }

    public String getDepartamentoDestino() {
        return departamentoDestino;
    }

    public void setDepartamentoDestino(String departamentoDestino) {
        this.departamentoDestino = departamentoDestino;
    }

    public String getDescripcionDestino() {
        return descripcionDestino;
    }

    public void setDescripcionDestino(String descripcionDestino) {
        this.descripcionDestino = descripcionDestino;
    }

    public Boolean getEstadoDestino() {
        return estadoDestino;
    }

    public void setEstadoDestino(Boolean estadoDestino) {
        this.estadoDestino = estadoDestino;
    }

    public String getNombreImagenPublicoDestino() {
        return nombreImagenPublicoDestino;
    }

    public void setNombreImagenPublicoDestino(String nombreImagenPublicoDestino) {
        this.nombreImagenPublicoDestino = nombreImagenPublicoDestino;
    }

    public String getNombreImagenPrivadoDestino() {
        return nombreImagenPrivadoDestino;
    }

    public void setNombreImagenPrivadoDestino(String nombreImagenPrivadoDestino) {
        this.nombreImagenPrivadoDestino = nombreImagenPrivadoDestino;
    }

    @Override
    public String toString() {
        return nombreDestino + ", " + departamentoDestino;
    }
}