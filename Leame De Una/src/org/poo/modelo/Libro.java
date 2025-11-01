package org.poo.modelo;

public class Libro {
    
    private Integer idLibro;
    private String nombreLibro;
    private Double precioLibro;
    private Short anioLibro;
    private Editorial idEditorial;
    private Autor idAutor;

    public Libro() {
    }

    public Integer getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    public String getNombreLibro() {
        return nombreLibro;
    }

    public void setNombreLibro(String nombreLibro) {
        this.nombreLibro = nombreLibro;
    }

    public Double getPrecioLibro() {
        return precioLibro;
    }

    public void setPrecioLibro(Double precioLibro) {
        this.precioLibro = precioLibro;
    }

    public Short getAnioLibro() {
        return anioLibro;
    }

    public void setAnioLibro(Short anioLibro) {
        this.anioLibro = anioLibro;
    }

    public Editorial getIdEditorialLibro() {
        return idEditorial;
    }

    public void setIdEditorialLibro(Editorial idEditorialLibro) {
        this.idEditorial = idEditorialLibro;
    }

    public Autor getIdAutorLibro() {
        return idAutor;
    }

    public void setIdAutorLibro(Autor idAutorLibro) {
        this.idAutor = idAutorLibro;
    }

    @Override
    public String toString() {
        return "Libro{" + "idLibro=" + idLibro + ", nombreLibro=" + nombreLibro + ", precioLibro=" + precioLibro + ", anioLibro=" + anioLibro + ", idEditorialLibro=" + idEditorial + ", idAutorLibro=" + idAutor + '}';
    }
}