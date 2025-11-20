package org.poo.modelo;

public class Destino {
    
    private Integer idDestino;
    private String nombreDestino;
    private String departamentoDestino;
    private String descripcionDestino;
    private Boolean estadoDestino;
    private String nombreImagenPublicoDestino;
    private String nombreImagenPrivadoDestino;
    
    // NUEVOS CAMPOS PARA CUMPLIR CON 6 TIPOS DIFERENTES
    private Integer altitudMetros;           // Spinner
    private Double temperaturaPromedio;       // Slider
    private Boolean esPlayero;               // RadioButton (ya tenías CheckBox)
    private String temporadaAlta;            // DatePicker (guardaremos como String)

    public Destino() {
    }

    public Destino(Integer idDestino, String nombreDestino, String departamentoDestino,
                  String descripcionDestino, Boolean estadoDestino,
                  String nombreImagenPublicoDestino, String nombreImagenPrivadoDestino,
                  Integer altitudMetros, Double temperaturaPromedio, Boolean esPlayero, String temporadaAlta) {
        this.idDestino = idDestino;
        this.nombreDestino = nombreDestino;
        this.departamentoDestino = departamentoDestino;
        this.descripcionDestino = descripcionDestino;
        this.estadoDestino = estadoDestino;
        this.nombreImagenPublicoDestino = nombreImagenPublicoDestino;
        this.nombreImagenPrivadoDestino = nombreImagenPrivadoDestino;
        this.altitudMetros = altitudMetros;
        this.temperaturaPromedio = temperaturaPromedio;
        this.esPlayero = esPlayero;
        this.temporadaAlta = temporadaAlta;
    }

    // Getters y Setters existentes...
    public Integer getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(Integer idDestino) {
        this.idDestino = idDestino;
    }

    public String getNombreDestino() {
        return nombreDestino;
    }

    public void setNombreDestino(String nombreDestino) {
        this.nombreDestino = nombreDestino;
    }

    public String getDepartamentoDestino() {
        return departamentoDestino;
    }

    public void setDepartamentoDestino(String departamentoDestino) {
        this.departamentoDestino = departamentoDestino;
    }

    public String getDescripcionDestino() {
        return descripcionDestino;
    }

    public void setDescripcionDestino(String descripcionDestino) {
        this.descripcionDestino = descripcionDestino;
    }

    public Boolean getEstadoDestino() {
        return estadoDestino;
    }

    public void setEstadoDestino(Boolean estadoDestino) {
        this.estadoDestino = estadoDestino;
    }

    public String getNombreImagenPublicoDestino() {
        return nombreImagenPublicoDestino;
    }

    public void setNombreImagenPublicoDestino(String nombreImagenPublicoDestino) {
        this.nombreImagenPublicoDestino = nombreImagenPublicoDestino;
    }

    public String getNombreImagenPrivadoDestino() {
        return nombreImagenPrivadoDestino;
    }

    public void setNombreImagenPrivadoDestino(String nombreImagenPrivadoDestino) {
        this.nombreImagenPrivadoDestino = nombreImagenPrivadoDestino;
    }

    // NUEVOS GETTERS Y SETTERS
    public Integer getAltitudMetros() {
        return altitudMetros;
    }

    public void setAltitudMetros(Integer altitudMetros) {
        this.altitudMetros = altitudMetros;
    }

    public Double getTemperaturaPromedio() {
        return temperaturaPromedio;
    }

    public void setTemperaturaPromedio(Double temperaturaPromedio) {
        this.temperaturaPromedio = temperaturaPromedio;
    }

    public Boolean getEsPlayero() {
        return esPlayero;
    }

    public void setEsPlayero(Boolean esPlayero) {
        this.esPlayero = esPlayero;
    }

    public String getTemporadaAlta() {
        return temporadaAlta;
    }

    public void setTemporadaAlta(String temporadaAlta) {
        this.temporadaAlta = temporadaAlta;
    }

    @Override
    public String toString() {
        return nombreDestino + ", " + departamentoDestino;
    }
}