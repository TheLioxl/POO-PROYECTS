package org.poo.modelo;

/**
 * Modelo de entidad Género
 * CORREGIDO: Nombres de propiedades consistentes
 */
public class Genero {

    private Integer idGenero;
    private String nombreGenero;
    private Boolean estadoGenero;
    private Short cantidadPeliculasGenero; // ⚠️ CON "S"

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

    // ⚠️ IMPORTANTE: El getter debe llamarse getCantidadPeliculasGenero (CON "S")
    public Short getCantidadPeliculasGenero() {
        return cantidadPeliculasGenero;
    }

    public void setCantidadPeliculasGenero(Short cantidadPeliculasGenero) {
        this.cantidadPeliculasGenero = cantidadPeliculasGenero;
    }

    @Override
    public String toString() {
        return nombreGenero;
    }
}