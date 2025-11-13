package edu.poo.modelo;

public class Categoria {

    private int codCategoria;
    private String nombreCategoria;
    private boolean estadoCategoria;
    private int cantidadProductoCategoria;
    private String nombreImagenPublicoCategoria;
    private String nombreImagenPrivadoCategoria;

    public Categoria() {
    }

    public Categoria(int codCategoria, String nombreCategoria, boolean estadoCategoria, int cantidadProductoCategoria, String nombreImagenPublicoCategoria, String nombreImagenPrivadoCategoria) {
        this.codCategoria = codCategoria;
        this.nombreCategoria = nombreCategoria;
        this.estadoCategoria = estadoCategoria;
        this.cantidadProductoCategoria = cantidadProductoCategoria;
        this.nombreImagenPublicoCategoria = nombreImagenPublicoCategoria;
        this.nombreImagenPrivadoCategoria = nombreImagenPrivadoCategoria;
    }

    public int getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(int codCategoria) {
        this.codCategoria = codCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public boolean isEstadoCategoria() {
        return estadoCategoria;
    }

    public void setEstadoCategoria(boolean estadoCategoria) {
        this.estadoCategoria = estadoCategoria;
    }

    public int getCantidadProductoCategoria() {
        return cantidadProductoCategoria;
    }

    public void setCantidadProductoCategoria(int cantidadProductoCategoria) {
        this.cantidadProductoCategoria = cantidadProductoCategoria;
    }

    public String getNombreImagenPublicoCategoria() {
        return nombreImagenPublicoCategoria;
    }

    public void setNombreImagenPublicoCategoria(String nombreImagenPublicoCategoria) {
        this.nombreImagenPublicoCategoria = nombreImagenPublicoCategoria;
    }

    public String getNombreImagenPrivadoCategoria() {
        return nombreImagenPrivadoCategoria;
    }

    public void setNombreImagenPrivadoCategoria(String nombreImagenPrivadoCategoria) {
        this.nombreImagenPrivadoCategoria = nombreImagenPrivadoCategoria;
    }

    @Override
    public String toString() {
        return "Categoria{" + "codCategoria=" + codCategoria + ", nombreCategoria=" + nombreCategoria + ", estadoCategoria=" + estadoCategoria + ", cantidadProductoCategoria=" + cantidadProductoCategoria + ", nombreImagenPublicoCategoria=" + nombreImagenPublicoCategoria + ", nombreImagenPrivadoCategoria=" + nombreImagenPrivadoCategoria + '}';
    }

}
