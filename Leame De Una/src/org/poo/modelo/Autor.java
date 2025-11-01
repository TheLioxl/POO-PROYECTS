package org.poo.modelo;

public class Autor {
    
    private Integer idAutor;
    private String nombreAutor;
    private Boolean generoAutor;
    private Short cantidadLibrosAutor;

    public Autor() {
    }

    public Integer getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Integer idAutor) {
        this.idAutor = idAutor;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public Boolean getGeneroAutor() {
        return generoAutor;
    }

    public void setGeneroAutor(Boolean generoAutor) {
        this.generoAutor = generoAutor;
    }

    public Short getCantidadLibrosAutor() {
        return cantidadLibrosAutor;
    }

    public void setCantidadLibrosAutor(Short cantidadLibrosAutor) {
        this.cantidadLibrosAutor = cantidadLibrosAutor;
    }

    @Override
    public String toString() {
        return nombreAutor;
    }
}