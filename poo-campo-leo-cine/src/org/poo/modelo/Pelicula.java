package org.poo.modelo;

public class Pelicula {

    private Integer idPelicula;
    private String nombrePelicula;
    private String protagonistaPelicula;
    private Genero GeneroPelicula;
    private Double presupuestoPelicula;
    private Boolean estadoPelicula;

    public Pelicula() {
    }

    public Pelicula(Integer idPelicula, String nombrePelicula, String protagonistaPelicula, Genero idGeneroPelicula, Double presupuestoPelicula, Boolean estadoPelicula) {
        this.idPelicula = idPelicula;
        this.nombrePelicula = nombrePelicula;
        this.protagonistaPelicula = protagonistaPelicula;
        this.GeneroPelicula = idGeneroPelicula;
        this.presupuestoPelicula = presupuestoPelicula;
        this.estadoPelicula = estadoPelicula;
    }

    public Integer getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Integer idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getNombrePelicula() {
        return nombrePelicula;
    }

    public void setNombrePelicula(String nombrePelicula) {
        this.nombrePelicula = nombrePelicula;
    }

    public String getProtagonistaPelicula() {
        return protagonistaPelicula;
    }

    public void setProtagonistaPelicula(String protagonistaPelicula) {
        this.protagonistaPelicula = protagonistaPelicula;
    }

    public Genero getGeneroPelicula() {
        return GeneroPelicula;
    }

    public void setGeneroPelicula(Genero GeneroPelicula) {
        this.GeneroPelicula = GeneroPelicula;
    }

    public Double getPresupuestoPelicula() {
        return presupuestoPelicula;
    }

    public void setPresupuestoPelicula(Double presupuestoPelicula) {
        this.presupuestoPelicula = presupuestoPelicula;
    }

    public Boolean getEstadoPelicula() {
        return estadoPelicula;
    }

    public void setEstadoPelicula(Boolean estadoPelicula) {
        this.estadoPelicula = estadoPelicula;
    }

    @Override
    public String toString() {
        return "Pelicula{" + "idPelicula=" + idPelicula + ", nombrePelicula=" + nombrePelicula + ", protagonistaPelicula=" + protagonistaPelicula + ", idGeneroPelicula=" + GeneroPelicula + ", presupuestoPelicula=" + presupuestoPelicula + ", estadoPelicula=" + estadoPelicula + '}';
    }

}