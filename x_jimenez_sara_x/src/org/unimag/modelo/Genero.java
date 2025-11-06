package org.unimag.modelo;

public class Genero {

    private Integer idGenero;
    private String nombreGenero;
    private Boolean estadoGenero;
    private Short cantidadPeliculaGenero;
    private String nombreImagenPublicoGenero;
    private String nombreImagenPrivadoGenero;

    public Genero() {
    }

    public Genero(Integer idGenero, String nombreGenero, Boolean estadoGenero, Short cantidadPeliculaGenero, String nombreImagenPublicoGenero, String nombreImagenPrivadoGenero) {
        this.idGenero = idGenero;
        this.nombreGenero = nombreGenero;
        this.estadoGenero = estadoGenero;
        this.cantidadPeliculaGenero = cantidadPeliculaGenero;
        this.nombreImagenPublicoGenero = nombreImagenPublicoGenero;
        this.nombreImagenPrivadoGenero = nombreImagenPrivadoGenero;
    }

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

    public Short getCantidadPeliculaGenero() {
        return cantidadPeliculaGenero;
    }

    public void setCantidadPeliculaGenero(Short cantidadPeliculaGenero) {
        this.cantidadPeliculaGenero = cantidadPeliculaGenero;
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
        return "idGenero=" + idGenero
                + ", nombreGenero=" + nombreGenero
                + ", estadoGenero=" + estadoGenero
                + ", cantidadPeliculaGenero=" + cantidadPeliculaGenero
                + ", nombreImagenPublicoGenero=" + nombreImagenPublicoGenero
                + ", nombreImagenPrivadoGenero=" + nombreImagenPrivadoGenero + '}';
    }

}
