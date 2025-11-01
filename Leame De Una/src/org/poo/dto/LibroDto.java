package org.poo.dto;

/**
 * Data Transfer Object para Libro
 * Propiedades consistentes con el modelo y JavaFX
 */
public class LibroDto {
    
    private Integer idLibro;
    private String nombreLibro;
    private Double precioLibro;
    private Short anioLibro;
    private EditorialDto idEditorialLibro;
    private AutorDto idAutorLibro;

    // Constructor vacío
    public LibroDto() {
    }

    // Constructor completo
    public LibroDto(Integer idLibro, String nombreLibro, Double precioLibro, Short anioLibro, 
                    EditorialDto idEditorialLibro, AutorDto idAutorLibro) {
        this.idLibro = idLibro;
        this.nombreLibro = nombreLibro;
        this.precioLibro = precioLibro;
        this.anioLibro = anioLibro;
        this.idEditorialLibro = idEditorialLibro;
        this.idAutorLibro = idAutorLibro;
    }

    // Getters y Setters
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

    // ⚠️ IMPORTANTE: Los getters deben tener los nombres exactos del modelo
    public EditorialDto getIdEditorialLibro() {
        return idEditorialLibro;
    }

    public void setIdEditorialLibro(EditorialDto idEditorialLibro) {
        this.idEditorialLibro = idEditorialLibro;
    }

    public AutorDto getIdAutorLibro() {
        return idAutorLibro;
    }

    public void setIdAutorLibro(AutorDto idAutorLibro) {
        this.idAutorLibro = idAutorLibro;
    }

    @Override
    public String toString() {
        return "LibroDto{" + 
               "idLibro=" + idLibro + 
               ", nombreLibro=" + nombreLibro + 
               ", precioLibro=" + precioLibro + 
               ", anioLibro=" + anioLibro + 
               ", idEditorialLibro=" + idEditorialLibro + 
               ", idAutorLibro=" + idAutorLibro + 
               '}';
    }
}