package org.poo.modelo;

public class Editorial {
    
    private Integer idEditorial;
    private String nombreEditorial;
    private String PaisEditorial;
    private Short formatoEditorial;
    private Short cantidadLibrosEditorial;

    public Editorial() {
    }

    public Integer getIdEditorial() {
        return idEditorial;
    }

    public void setIdEditorial(Integer idEditorial) {
        this.idEditorial = idEditorial;
    }

    public String getNombreEditorial() {
        return nombreEditorial;
    }

    public void setNombreEditorial(String nombreEditorial) {
        this.nombreEditorial = nombreEditorial;
    }

    public String getPaisEditorial() {
        return PaisEditorial;
    }

    public void setPaisEditorial(String paisEditorial) {
        this.PaisEditorial = paisEditorial;
    }

    public Short getFormatoEditorial() {
        return formatoEditorial;
    }

    public void setFormatoEditorial(Short formatoEditorial) {
        this.formatoEditorial = formatoEditorial;
    }

    public Short getCantidadLibrosEditorial() {
        return cantidadLibrosEditorial;
    }

    public void setCantidadLibrosEditorial(Short cantidadLibrosEditorial) {
        this.cantidadLibrosEditorial = cantidadLibrosEditorial;
    }

    @Override
    public String toString() {
        return nombreEditorial;
    }
}