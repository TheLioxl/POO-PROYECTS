package org.poo.modelo;

public class Genero {

    private Integer idGenero;
    private String nombreGenero;
    private Boolean estadoGenero;
    private Short cantidadPeliculasGenero;
    private String nombreImagenPublicoGenero;   // Nombre original del archivo
    private String nombreImagenPrivadoGenero;   // Nombre con UUID en disco

    public Genero() {
    }

    public Genero(Integer idGenero, String nombreGenero, Boolean estadoGenero) {
        this.idGenero = idGenero;
        this.nombreGenero = nombreGenero;
        this.estadoGenero = estadoGenero;
    }

    public Genero(Integer idGenero, String nombreGenero, Boolean estadoGenero, Short cantidadPeliculasGenero) {
        this.idGenero = idGenero;
        this.nombreGenero = nombreGenero;
        this.estadoGenero = estadoGenero;
        this.cantidadPeliculasGenero = cantidadPeliculasGenero;
    }

    // Getters y Setters
    public Integer getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Integer idGenero) {
        this.idGenero = idGenero;
    }

    public String getNombreGenero() {
        return nombreGenero;
    }

    public void setNombreGenero(String nombreGenero) {
        this.nombreGenero = nombreGenero;
    }

    public Boolean getEstadoGenero() {
        return estadoGenero;
    }

    public void setEstadoGenero(Boolean estadoGenero) {
        this.estadoGenero = estadoGenero;
    }

    public Short getCantidadPeliculasGenero() {
        return cantidadPeliculasGenero;
    }

    public void setCantidadPeliculasGenero(Short cantidadPeliculasGenero) {
        this.cantidadPeliculasGenero = cantidadPeliculasGenero;
    }

    public String getNombreImagenPublicoGenero() {
        return nombreImagenPublicoGenero;
    }

    public void setNombreImagenPublicoGenero(String nombreImagenPublicoGenero) {
        this.nombreImagenPublicoGenero = nombreImagenPublicoGenero;
    }

    public String getNombreImagenPrivadoGenero() {
        return nombreImagenPrivadoGenero;
    }

    public void setNombreImagenPrivadoGenero(String nombreImagenPrivadoGenero) {
        this.nombreImagenPrivadoGenero = nombreImagenPrivadoGenero;
    }

    @Override
    public String toString() {
        return nombreGenero;
    }
}