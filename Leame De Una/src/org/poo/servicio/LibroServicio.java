package org.poo.servicio;

import com.poo.persistence.NioFile;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.poo.api.ApiOperacionBD;
import org.poo.dto.AutorDto;
import org.poo.dto.EditorialDto;
import org.poo.dto.LibroDto;
import org.poo.modelo.Autor;
import org.poo.modelo.Editorial;
import org.poo.modelo.Libro;
import org.poo.recurso.constante.Persistencia;

public class LibroServicio implements ApiOperacionBD<LibroDto, Integer> {

    private NioFile miArchivo;
    private final String nombrePersistencia;

    public LibroServicio() {
        nombrePersistencia = Persistencia.NOMBRE_LIBRO;
        try {
            miArchivo = new NioFile(nombrePersistencia);
        } catch (IOException ex) {
            Logger.getLogger(LibroServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getSerial() {
        int id = 0;
        try {
            id = miArchivo.ultimoCodigo() + 1;
        } catch (IOException ex) {
            Logger.getLogger(LibroServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    @Override
    public LibroDto insertInto(LibroDto dto, String ruta) {
        Editorial objEditorial = new Editorial();
        objEditorial.setIdEditorial(dto.getIdEditorialLibro().getIdEditorial());
        objEditorial.setNombreEditorial(dto.getIdEditorialLibro().getNombreEditorial());
        objEditorial.setPaisEditorial(dto.getIdEditorialLibro().getPaisEditorial());
        objEditorial.setFormatoEditorial(dto.getIdEditorialLibro().getFormatoEditorial());

        Autor objAutor = new Autor();
        objAutor.setIdAutor(dto.getIdAutorLibro().getIdAutor());
        objAutor.setNombreAutor(dto.getIdAutorLibro().getNombreAutor());
        objAutor.setGeneroAutor(dto.getIdAutorLibro().getGeneroAutor());

        Libro objLibro = new Libro();
        objLibro.setIdLibro(getSerial());
        objLibro.setNombreLibro(dto.getNombreLibro());
        objLibro.setPrecioLibro(dto.getPrecioLibro());
        objLibro.setAnioLibro(dto.getAnioLibro());
        objLibro.setIdEditorialLibro(objEditorial);
        objLibro.setIdAutorLibro(objAutor);

        String filaGrabar = objLibro.getIdLibro() + Persistencia.SEPARADOR_COLUMNAS
                + objLibro.getNombreLibro() + Persistencia.SEPARADOR_COLUMNAS
                + BigDecimal.valueOf(objLibro.getPrecioLibro()).toPlainString() + Persistencia.SEPARADOR_COLUMNAS
                + objLibro.getAnioLibro() + Persistencia.SEPARADOR_COLUMNAS
                + objLibro.getIdEditorialLibro().getIdEditorial() + Persistencia.SEPARADOR_COLUMNAS
                + objLibro.getIdAutorLibro().getIdAutor();

        if (miArchivo.agregarRegistro(filaGrabar)) {
            dto.setIdLibro(objLibro.getIdLibro());
            return dto;
        }

        return null;
    }

    public Map<Integer, Integer> librosPorEditorial() {
        Map<Integer, Integer> arrCantidades = new HashMap<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();
        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

                int idEditorial = Integer.parseInt(columnas[4].trim());
                arrCantidades.put(idEditorial, arrCantidades.getOrDefault(idEditorial, 0) + 1);

            } catch (NumberFormatException error) {
                Logger.getLogger(LibroServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arrCantidades;
    }

    public Map<Integer, Integer> librosPorAutor() {
        Map<Integer, Integer> arrCantidades = new HashMap<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

                int idAutor = Integer.parseInt(columnas[5].trim());
                arrCantidades.put(idAutor, arrCantidades.getOrDefault(idAutor, 0) + 1);

            } catch (NumberFormatException error) {
                Logger.getLogger(LibroServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arrCantidades;
    }

    @Override
    public List<LibroDto> selectFrom() {
        EditorialServicio editorialServicio = new EditorialServicio();
        List<EditorialDto> arrEditoriales = editorialServicio.selectFrom();

        AutorServicio autorServicio = new AutorServicio();
        List<AutorDto> arrAutores = autorServicio.selectFrom();

        List<LibroDto> arregloLibroDtos = new ArrayList<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

                if (columnas.length >= 6) {
                    Integer codigoLibro = Integer.valueOf(columnas[0].trim());
                    String nombre = columnas[1].trim();
                    Double precio = Double.valueOf(columnas[2].trim());
                    Short anio = Short.valueOf(columnas[3].trim());
                    Integer codigoEditorial = Integer.valueOf(columnas[4].trim());
                    Integer codigoAutor = Integer.valueOf(columnas[5].trim());

                    LibroDto objLibroDto = new LibroDto();
                    objLibroDto.setIdLibro(codigoLibro);
                    objLibroDto.setNombreLibro(nombre);
                    objLibroDto.setPrecioLibro(precio);
                    objLibroDto.setAnioLibro(anio);

                    objLibroDto.setIdEditorialLibro(obtenerEditorialCompleta(codigoEditorial, arrEditoriales));
                    objLibroDto.setIdAutorLibro(obtenerAutorCompleto(codigoAutor, arrAutores));

                    arregloLibroDtos.add(objLibroDto);
                }
            } catch (NumberFormatException error) {
                Logger.getLogger(LibroServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arregloLibroDtos;
    }

    private EditorialDto obtenerEditorialCompleta(Integer codigoEditorial, List<EditorialDto> arrEditoriales) {
        for (EditorialDto editorialExterna : arrEditoriales) {
            if (Objects.equals(codigoEditorial, editorialExterna.getIdEditorial())) {
                return editorialExterna;
            }
        }
        return null;
    }

    private AutorDto obtenerAutorCompleto(Integer codigoAutor, List<AutorDto> arrAutores) {
        for (AutorDto autorExterno : arrAutores) {
            if (Objects.equals(codigoAutor, autorExterno.getIdAutor())) {
                return autorExterno;
            }
        }
        return null;
    }

    @Override
    public List<LibroDto> selectFromWhereActivos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int numRows() {
        int cantidad = 0;
        try {
            cantidad = miArchivo.cantidadFilas();
        } catch (IOException ex) {
            Logger.getLogger(LibroServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cantidad;
    }

    @Override
    public Boolean deleteFrom(Integer codigo) {
        Boolean correcto = false;
        try {
            List<String> arreglo;
            arreglo = miArchivo.borrarFilaPosicion(codigo);
            if (!arreglo.isEmpty()) {
                correcto = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(LibroServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }

    @Override
    public LibroDto updateSet(Integer codigo, LibroDto objeto, String ruta) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LibroDto getOne(Integer codigo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
