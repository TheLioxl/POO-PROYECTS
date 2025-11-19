package org.poo.modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Viaje {
    
    private Integer idViaje;
    private Bus busViaje;
    private Ruta rutaViaje;
    private Conductor conductorViaje;
    private LocalDate fechaViaje;
    private LocalTime horaSalidaViaje;
    private Double precioViaje;
    private Integer asientosDisponiblesViaje;
    private Boolean estadoViaje; 
    private String nombreImagenPublicoViaje;
    private String nombreImagenPrivadoViaje;
    private LocalTime horaLlegadaViaje;        
    private Boolean viajeDirecto;              
    private Boolean incluyeRefrigerio;         
    private Boolean tieneParadasIntermedias;   
    private String descripcionViaje;           
    private String notasAdicionalesViaje;      

    public Viaje() {
    }

    public Viaje(Integer idViaje, Bus busViaje, Ruta rutaViaje, Conductor conductorViaje,
                LocalDate fechaViaje, LocalTime horaSalidaViaje, LocalTime horaLlegadaViaje,
                Double precioViaje, Integer asientosDisponiblesViaje, Boolean estadoViaje,
                Boolean viajeDirecto, Boolean incluyeRefrigerio, Boolean tieneParadasIntermedias,
                String descripcionViaje, String notasAdicionalesViaje,
                String nombreImagenPublicoViaje, String nombreImagenPrivadoViaje) {
        this.idViaje = idViaje;
        this.busViaje = busViaje;
        this.rutaViaje = rutaViaje;
        this.conductorViaje = conductorViaje;
        this.fechaViaje = fechaViaje;
        this.horaSalidaViaje = horaSalidaViaje;
        this.horaLlegadaViaje = horaLlegadaViaje;
        this.precioViaje = precioViaje;
        this.asientosDisponiblesViaje = asientosDisponiblesViaje;
        this.estadoViaje = estadoViaje;
        this.viajeDirecto = viajeDirecto;
        this.incluyeRefrigerio = incluyeRefrigerio;
        this.tieneParadasIntermedias = tieneParadasIntermedias;
        this.descripcionViaje = descripcionViaje;
        this.notasAdicionalesViaje = notasAdicionalesViaje;
        this.nombreImagenPublicoViaje = nombreImagenPublicoViaje;
        this.nombreImagenPrivadoViaje = nombreImagenPrivadoViaje;
    }
    
    public Integer getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(Integer idViaje) {
        this.idViaje = idViaje;
    }

    public Bus getBusViaje() {
        return busViaje;
    }

    public void setBusViaje(Bus busViaje) {
        this.busViaje = busViaje;
    }

    public Ruta getRutaViaje() {
        return rutaViaje;
    }

    public void setRutaViaje(Ruta rutaViaje) {
        this.rutaViaje = rutaViaje;
    }

    public Conductor getConductorViaje() {
        return conductorViaje;
    }

    public void setConductorViaje(Conductor conductorViaje) {
        this.conductorViaje = conductorViaje;
    }

    public LocalDate getFechaViaje() {
        return fechaViaje;
    }

    public void setFechaViaje(LocalDate fechaViaje) {
        this.fechaViaje = fechaViaje;
    }

    public LocalTime getHoraSalidaViaje() {
        return horaSalidaViaje;
    }

    public void setHoraSalidaViaje(LocalTime horaSalidaViaje) {
        this.horaSalidaViaje = horaSalidaViaje;
    }

    public Double getPrecioViaje() {
        return precioViaje;
    }

    public void setPrecioViaje(Double precioViaje) {
        this.precioViaje = precioViaje;
    }

    public Integer getAsientosDisponiblesViaje() {
        return asientosDisponiblesViaje;
    }

    public void setAsientosDisponiblesViaje(Integer asientosDisponiblesViaje) {
        this.asientosDisponiblesViaje = asientosDisponiblesViaje;
    }

    public Boolean getEstadoViaje() {
        return estadoViaje;
    }

    public void setEstadoViaje(Boolean estadoViaje) {
        this.estadoViaje = estadoViaje;
    }

    public String getNombreImagenPublicoViaje() {
        return nombreImagenPublicoViaje;
    }

    public void setNombreImagenPublicoViaje(String nombreImagenPublicoViaje) {
        this.nombreImagenPublicoViaje = nombreImagenPublicoViaje;
    }

    public String getNombreImagenPrivadoViaje() {
        return nombreImagenPrivadoViaje;
    }

    public void setNombreImagenPrivadoViaje(String nombreImagenPrivadoViaje) {
        this.nombreImagenPrivadoViaje = nombreImagenPrivadoViaje;
    }

    public LocalTime getHoraLlegadaViaje() {
        return horaLlegadaViaje;
    }

    public void setHoraLlegadaViaje(LocalTime horaLlegadaViaje) {
        this.horaLlegadaViaje = horaLlegadaViaje;
    }

    public Boolean getViajeDirecto() {
        return viajeDirecto;
    }

    public void setViajeDirecto(Boolean viajeDirecto) {
        this.viajeDirecto = viajeDirecto;
    }

    public Boolean getIncluyeRefrigerio() {
        return incluyeRefrigerio;
    }

    public void setIncluyeRefrigerio(Boolean incluyeRefrigerio) {
        this.incluyeRefrigerio = incluyeRefrigerio;
    }

    public Boolean getTieneParadasIntermedias() {
        return tieneParadasIntermedias;
    }

    public void setTieneParadasIntermedias(Boolean tieneParadasIntermedias) {
        this.tieneParadasIntermedias = tieneParadasIntermedias;
    }

    public String getDescripcionViaje() {
        return descripcionViaje;
    }

    public void setDescripcionViaje(String descripcionViaje) {
        this.descripcionViaje = descripcionViaje;
    }

    public String getNotasAdicionalesViaje() {
        return notasAdicionalesViaje;
    }

    public void setNotasAdicionalesViaje(String notasAdicionalesViaje) {
        this.notasAdicionalesViaje = notasAdicionalesViaje;
    }

    @Override
    public String toString() {
        return "Viaje " + idViaje + " - " + fechaViaje;
    }
}
