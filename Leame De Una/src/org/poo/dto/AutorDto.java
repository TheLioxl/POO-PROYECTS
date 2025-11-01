package org.poo.dto;

/**
 * Data Transfer Object para Autor
 * Propiedades consistentes con el modelo y JavaFX
 */
public class AutorDto {
    
    private Integer idAutor;
    private String nombreAutor;
    private Boolean generoAutor;
    private Short cantidadLibrosAutor;

    // Constructor vacío
    public AutorDto() {
    }

    // Constructor sin cantidad de libros (para crear autores)
    public AutorDto(Integer idAutor, String nombreAutor, Boolean generoAutor) {
        this.idAutor = idAutor;
        this.nombreAutor = nombreAutor;
        this.generoAutor = generoAutor;
    }

    // Constructor completo con cantidad de libros (para listar)
    public AutorDto(Integer idAutor, String nombreAutor, Boolean generoAutor, Short cantidadLibrosAutor) {
        this.idAutor = idAutor;
        this.nombreAutor = nombreAutor;
        this.generoAutor = generoAutor;
        this.cantidadLibrosAutor = cantidadLibrosAutor;
    }

    // Getters y Setters
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

    // ⚠️ IMPORTANTE: El getter debe tener el nombre exacto para JavaFX PropertyValueFactory
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