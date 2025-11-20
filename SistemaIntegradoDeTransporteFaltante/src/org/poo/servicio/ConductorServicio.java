package org.poo.servicio;

import com.poo.persistence.NioFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.poo.api.ApiOperacionBD;
import org.poo.dto.ConductorDto;
import org.poo.dto.EmpresaDto;
import org.poo.modelo.Conductor;
import org.poo.recurso.constante.Persistencia;
import org.poo.recurso.utilidad.GestorImagen;

public class ConductorServicio implements ApiOperacionBD<ConductorDto, Integer> {

    private NioFile miArchivo;
    private String nombrePersistencia;

    public ConductorServicio() {
        nombrePersistencia = Persistencia.NOMBRE_CONDUCTOR;
        try {
            miArchivo = new NioFile(nombrePersistencia);
        } catch (IOException ex) {
            Logger.getLogger(ConductorServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getSerial() {
        int id = 0;
        try {
            id = miArchivo.ultimoCodigo() + 1;
        } catch (IOException ex) {
            Logger.getLogger(ConductorServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    @Override
    public ConductorDto insertInto(ConductorDto dto, String ruta) {
        Conductor objConductor = new Conductor();

        objConductor.setIdConductor(getSerial());
        objConductor.setNombreConductor(dto.getNombreConductor());
        objConductor.setCedulaConductor(dto.getCedulaConductor());
        objConductor.setLicenciaConductor(dto.getLicenciaConductor());
        objConductor.setEstadoConductor(dto.getEstadoConductor());
        objConductor.setNombreImagenPublicoConductor(dto.getNombreImagenPublicoConductor());
        objConductor.setNombreImagenPrivadoConductor(GestorImagen.grabarLaImagen(ruta));

        String fechaNac = dto.getFechaNacimientoConductor() != null
                ? dto.getFechaNacimientoConductor().toString()
                : "";
        String fechaVenc = dto.getFechaVencimientoLicencia() != null
                ? dto.getFechaVencimientoLicencia().toString()
                : "";

        String filaGrabar = objConductor.getIdConductor() + Persistencia.SEPARADOR_COLUMNAS
                + objConductor.getNombreConductor() + Persistencia.SEPARADOR_COLUMNAS
                + objConductor.getCedulaConductor() + Persistencia.SEPARADOR_COLUMNAS
                + fechaNac + Persistencia.SEPARADOR_COLUMNAS
                + dto.getTelefonoConductor() + Persistencia.SEPARADOR_COLUMNAS
                + objConductor.getLicenciaConductor() + Persistencia.SEPARADOR_COLUMNAS
                + fechaVenc + Persistencia.SEPARADOR_COLUMNAS
                + dto.getEmpresaConductor().getIdEmpresa() + Persistencia.SEPARADOR_COLUMNAS
                + objConductor.getEstadoConductor() + Persistencia.SEPARADOR_COLUMNAS
                + objConductor.getNombreImagenPublicoConductor() + Persistencia.SEPARADOR_COLUMNAS
                + objConductor.getNombreImagenPrivadoConductor();

        if (miArchivo.agregarRegistro(filaGrabar)) {
            dto.setIdConductor(objConductor.getIdConductor());
            return dto;
        }

        return null;
    }

    @Override
    public List<ConductorDto> selectFrom() {
        EmpresaServicio empresaServicio = new EmpresaServicio();
        List<EmpresaDto> arrEmpresas = empresaServicio.selectFrom();

        List<ConductorDto> arregloConductor = new ArrayList<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

                int codConductor = Integer.parseInt(columnas[0].trim());
                String nombre = columnas[1].trim();
                String cedula = columnas[2].trim();
                String fechaNacTxt = columnas[3].trim();
                String telefono = columnas[4].trim();
                String licencia = columnas[5].trim();
                String fechaVencTxt = columnas[6].trim();
                int codEmpresa = Integer.parseInt(columnas[7].trim());
                Boolean estado = Boolean.valueOf(columnas[8].trim());
                String npub = columnas[9].trim();
                String nocu = columnas[10].trim();

                ConductorDto dto = new ConductorDto();
                dto.setIdConductor(codConductor);
                dto.setNombreConductor(nombre);
                dto.setCedulaConductor(cedula);
                dto.setTelefonoConductor(telefono);
                dto.setLicenciaConductor(licencia);
                dto.setEstadoConductor(estado);
                dto.setNombreImagenPublicoConductor(npub);
                dto.setNombreImagenPrivadoConductor(nocu);

                if (!fechaNacTxt.isBlank()) {
                    dto.setFechaNacimientoConductor(LocalDate.parse(fechaNacTxt));
                }
                if (!fechaVencTxt.isBlank()) {
                    dto.setFechaVencimientoLicencia(LocalDate.parse(fechaVencTxt));
                }

                dto.setEmpresaConductor(obtenerEmpresaCompleta(codEmpresa, arrEmpresas));

                arregloConductor.add(dto);

            } catch (NumberFormatException error) {
                Logger.getLogger(ConductorServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arregloConductor;
    }

    private EmpresaDto obtenerEmpresaCompleta(Integer codigoEmpresa, List<EmpresaDto> arrEmpresas) {
        for (EmpresaDto empresaExterna : arrEmpresas) {
            if (Objects.equals(codigoEmpresa, empresaExterna.getIdEmpresa())) {
                return empresaExterna;
            }
        }
        return null;
    }

    @Override
    public List<ConductorDto> selectFromWhereActivos() {
        EmpresaServicio empresaServicio = new EmpresaServicio();
        List<EmpresaDto> arrEmpresas = empresaServicio.selectFrom();

        List<ConductorDto> arregloConductor = new ArrayList<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

                int codConductor = Integer.parseInt(columnas[0].trim());
                String nombre = columnas[1].trim();
                String cedula = columnas[2].trim();
                String fechaNacTxt = columnas[3].trim();
                String telefono = columnas[4].trim();
                String licencia = columnas[5].trim();
                String fechaVencTxt = columnas[6].trim();
                int codEmpresa = Integer.parseInt(columnas[7].trim());
                Boolean estado = Boolean.valueOf(columnas[8].trim());
                String npub = columnas[9].trim();
                String nocu = columnas[10].trim();

                if (Boolean.TRUE.equals(estado)) {
                    ConductorDto dto = new ConductorDto();
                    dto.setIdConductor(codConductor);
                    dto.setNombreConductor(nombre);
                    dto.setCedulaConductor(cedula);
                    dto.setTelefonoConductor(telefono);
                    dto.setLicenciaConductor(licencia);
                    dto.setEstadoConductor(estado);
                    dto.setNombreImagenPublicoConductor(npub);
                    dto.setNombreImagenPrivadoConductor(nocu);

                    if (!fechaNacTxt.isBlank()) {
                        dto.setFechaNacimientoConductor(LocalDate.parse(fechaNacTxt));
                    }
                    if (!fechaVencTxt.isBlank()) {
                        dto.setFechaVencimientoLicencia(LocalDate.parse(fechaVencTxt));
                    }

                    dto.setEmpresaConductor(obtenerEmpresaCompleta(codEmpresa, arrEmpresas));

                    arregloConductor.add(dto);
                }
            } catch (NumberFormatException error) {
                Logger.getLogger(ConductorServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arregloConductor;
    }

    @Override
    public int numRows() {
        int cantidad = 0;
        try {
            cantidad = miArchivo.cantidadFilas();
        } catch (IOException ex) {
            Logger.getLogger(ConductorServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cantidad;
    }

    @Override
    public Boolean deleteFrom(Integer codigo) {
        Boolean correcto = false;
        try {
            List<String> arreglo = miArchivo.borrarFilaPosicion(codigo);
            if (!arreglo.isEmpty()) {

                String nomOculto = arreglo.get(arreglo.size() - 1);
                String nombreBorrar = Persistencia.RUTA_IMAGENES
                        + Persistencia.SEPARADOR_CARPETAS + nomOculto;
                Path rutaBorrar = Paths.get(nombreBorrar);
                Files.deleteIfExists(rutaBorrar);
                correcto = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(ConductorServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }

    @Override
    public ConductorDto getOne(Integer codigo) {
        int contador = 0;
        ConductorDto objListo = new ConductorDto();
        List<ConductorDto> arrConductores = selectFrom();

        for (ConductorDto objConductor : arrConductores) {
            if (contador == codigo) {
                objListo = objConductor;
                break;
            }
            contador++;
        }
        return objListo;
    }

    @Override
    public ConductorDto updateSet(Integer codigo, ConductorDto objeto, String ruta) {
        try {
        String cadena, nocu;
        List<String> arregloDatos;

        String fechaNac = objeto.getFechaNacimientoConductor() != null
                ? objeto.getFechaNacimientoConductor().toString()
                : "";
        String fechaVenc = objeto.getFechaVencimientoLicencia() != null
                ? objeto.getFechaVencimientoLicencia().toString()
                : "";

        cadena = objeto.getIdConductor() + Persistencia.SEPARADOR_COLUMNAS
                + objeto.getNombreConductor() + Persistencia.SEPARADOR_COLUMNAS
                + objeto.getCedulaConductor() + Persistencia.SEPARADOR_COLUMNAS
                + fechaNac + Persistencia.SEPARADOR_COLUMNAS
                + objeto.getTelefonoConductor() + Persistencia.SEPARADOR_COLUMNAS
                + objeto.getLicenciaConductor() + Persistencia.SEPARADOR_COLUMNAS
                + fechaVenc + Persistencia.SEPARADOR_COLUMNAS
                + objeto.getEmpresaConductor().getIdEmpresa() + Persistencia.SEPARADOR_COLUMNAS
                + objeto.getEstadoConductor() + Persistencia.SEPARADOR_COLUMNAS
                + objeto.getNombreImagenPublicoConductor() + Persistencia.SEPARADOR_COLUMNAS;

        if (ruta.isBlank()) {
            cadena = cadena + objeto.getNombreImagenPrivadoConductor();
        } else {
            nocu = GestorImagen.grabarLaImagen(ruta);
            cadena = cadena + nocu;

            arregloDatos = miArchivo.borrarFilaPosicion(codigo);
            if (!arregloDatos.isEmpty()) {
                String nomOculto = arregloDatos.get(arregloDatos.size() - 1);
                String nombreBorrar = Persistencia.RUTA_IMAGENES
                        + Persistencia.SEPARADOR_CARPETAS + nomOculto;
                Path rutaBorrar = Paths.get(nombreBorrar);
                Files.deleteIfExists(rutaBorrar);
            }
        }

        if (miArchivo.actualizaFilaPosicion(codigo, cadena)) {
            return objeto;
        }
    } catch (IOException ex) {
        Logger.getLogger(ConductorServicio.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
}
    }
    
