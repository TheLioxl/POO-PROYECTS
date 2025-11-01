package org.poo.dto;

/**
 * Data Transfer Object para Editorial
 * Propiedades consistentes con el modelo y JavaFX
 */
public class EditorialDto {
    
    private Integer idEditorial;
    private String nombreEditorial;
    private String paisEditorial;
    private Short formatoEditorial;
    private Short cantidadLibrosEditorial;

    // Constructor vacío
    public EditorialDto() {
    }

    // Constructor sin cantidad de libros (para crear editoriales)
    public EditorialDto(Integer idEditorial, String nombreEditorial, String paisEditorial, Short formatoEditorial) {
        this.idEditorial = idEditorial;
        this.nombreEditorial = nombreEditorial;
        this.paisEditorial = paisEditorial;
        this.formatoEditorial = formatoEditorial;
    }

    // Constructor completo con cantidad de libros (para listar)
    public EditorialDto(Integer idEditorial, String nombreEditorial, String paisEditorial, Short formatoEditorial, Short cantidadLibrosEditorial) {
        this.idEditorial = idEditorial;
        this.nombreEditorial = nombreEditorial;
        this.paisEditorial = paisEditorial;
        this.formatoEditorial = formatoEditorial;
        this.cantidadLibrosEditorial = cantidadLibrosEditorial;
    }

    // Getters y Setters
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
        return paisEditorial;
    }

    public void setPaisEditorial(String paisEditorial) {
        this.paisEditorial = paisEditorial;
    }

    public Short getFormatoEditorial() {
        return formatoEditorial;
    }

    public void setFormatoEditorial(Short formatoEditorial) {
        this.formatoEditorial = formatoEditorial;
    }

    // ⚠️ IMPORTANTE: El getter debe tener el nombre exacto para JavaFX PropertyValueFactory
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