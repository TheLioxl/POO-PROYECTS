package edu.poo.persistencia;

import edu.poo.modelo.Producto;
import edu.poo.modelo.Categoria;
import edu.poo.recurso.dominio.Ruta;
import edu.poo.interfaz.OperacionBD;
import edu.poo.recurso.utilidad.Imagen;
import edu.poo.recurso.dominio.Configuracion;
import edu.poo.controlador.producto.ControladorProductoListar;

import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;
import unimag.poo.persistencia.NioFile;

import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOCategorias implements OperacionBD<Categoria> {

    private NioFile miArchivo;
    private String nombrePersistencia;

    public DAOCategorias() {
        nombrePersistencia = Ruta.RUTA_PERSISTENCIA + Configuracion.SEPARADOR_CARPETA + "DatosCategoria.txt";
        try {
            miArchivo = new NioFile(nombrePersistencia);
        } catch (IOException ex) {
            Logger.getLogger(DAOCategorias.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Categoria> SelectFrom() {
        boolean estado;
        String nombre, cadena, npub, nocu;
        int i, desde, limiteCategoria, cuente, codCategoria, cantProductos;

        // Obtiene las categorías desde el archivo plano
        List<Categoria> arregloCategoria = new ArrayList<>();
        List<String> arregloDatosCategoria = miArchivo.obtenerDatos();
        limiteCategoria = arregloDatosCategoria.size();
        // *********************************************************************

        // Obtiene los productos desde el archivo plano
        List<Producto> arregloProductos = ControladorProductoListar.arregloProductos();
        // *********************************************************************

        for (i = 0; i < limiteCategoria; i++) {
            cadena = arregloDatosCategoria.get(i);

            desde = 0;
            cuente = cadena.indexOf(Configuracion.SEPARADOR_COLUMNAS, desde);
            codCategoria = Integer.parseInt(cadena.substring(desde, cuente).trim());

            desde = cuente + 1;
            cuente = cadena.indexOf(Configuracion.SEPARADOR_COLUMNAS, desde);
            nombre = cadena.substring(desde, cuente).trim();

            desde = cuente + 1;
            cuente = cadena.indexOf(Configuracion.SEPARADOR_COLUMNAS, desde);
            estado = Boolean.parseBoolean(cadena.substring(desde, cuente).trim());

            desde = cuente + 1;
            cuente = cadena.indexOf(Configuracion.SEPARADOR_COLUMNAS, desde);
            npub = cadena.substring(desde, cuente).trim();

            desde = cuente + 1;
            cuente = cadena.length() - 1;
            nocu = cadena.substring(desde, cuente).trim();

            // Cuenta los productos de cada categoría
            // *****************************************************************
            cantProductos = 0;
            for (Producto objPro : arregloProductos) {
                if (codCategoria == objPro.getCatProducto().getCodCategoria()) {
                    cantProductos++;
                }
            }

            Categoria objTemporal = new Categoria(codCategoria, nombre, estado, cantProductos, npub, nocu);
            arregloCategoria.add(objTemporal);
        }
        return arregloCategoria;
    }

    // Solo categorías activas
    public List<Categoria> SelectFromActivos() {
        boolean estado;
        String nombre, cadena, npub, nocu;
        int i, desde, limiteCategoria, cuente, codCategoria, cantProductos;

        // Obtiene las categorías desde el archivo plano
        List<Categoria> arregloCategoria = new ArrayList<>();
        List<String> arregloDatosCategoria = miArchivo.obtenerDatos();
        limiteCategoria = arregloDatosCategoria.size();
        // *********************************************************************

        // Obtiene los productos desde el archivo plano
        List<Producto> arregloProductos = ControladorProductoListar.arregloProductos();
        // *********************************************************************

        for (i = 0; i < limiteCategoria; i++) {
            cadena = arregloDatosCategoria.get(i);

            desde = 0;
            cuente = cadena.indexOf(Configuracion.SEPARADOR_COLUMNAS, desde);
            codCategoria = Integer.parseInt(cadena.substring(desde, cuente).trim());

            desde = cuente + 1;
            cuente = cadena.indexOf(Configuracion.SEPARADOR_COLUMNAS, desde);
            nombre = cadena.substring(desde, cuente).trim();

            desde = cuente + 1;
            cuente = cadena.indexOf(Configuracion.SEPARADOR_COLUMNAS, desde);
            estado = Boolean.parseBoolean(cadena.substring(desde, cuente).trim());

            desde = cuente + 1;
            cuente = cadena.indexOf(Configuracion.SEPARADOR_COLUMNAS, desde);
            npub = cadena.substring(desde, cuente).trim();

            desde = cuente + 1;
            cuente = cadena.length() - 1;
            nocu = cadena.substring(desde, cuente).trim();

            // Para asegurar que solo cargue las categorías con estado true
            if (estado == true) {
                cantProductos = 0;
                for (Producto objPro : arregloProductos) {
                    if (codCategoria == objPro.getCatProducto().getCodCategoria()) {
                        cantProductos++;
                    }
                }
                Categoria objTemporal = new Categoria(codCategoria, nombre, estado, cantProductos, npub, nocu);
                arregloCategoria.add(objTemporal);
            }
        }
        return arregloCategoria;
    }

// Solo categorías activas
    public List<Categoria> SelectFromTodos() {
        boolean estado;
        String nombre, cadena, npub, nocu;
        int i, desde, limiteCategoria, cuente, codCategoria;

        // Obtiene las categorías desde el archivo plano
        List<Categoria> arregloCategoria = new ArrayList<>();
        List<String> arregloDatosCategoria = miArchivo.obtenerDatos();
        limiteCategoria = arregloDatosCategoria.size();
        // *********************************************************************

        for (i = 0; i < limiteCategoria; i++) {
            cadena = arregloDatosCategoria.get(i);

            desde = 0;
            cuente = cadena.indexOf(Configuracion.SEPARADOR_COLUMNAS, desde);
            codCategoria = Integer.parseInt(cadena.substring(desde, cuente).trim());

            desde = cuente + 1;
            cuente = cadena.indexOf(Configuracion.SEPARADOR_COLUMNAS, desde);
            nombre = cadena.substring(desde, cuente).trim();

            desde = cuente + 1;
            cuente = cadena.indexOf(Configuracion.SEPARADOR_COLUMNAS, desde);
            estado = Boolean.parseBoolean(cadena.substring(desde, cuente).trim());

            desde = cuente + 1;
            cuente = cadena.indexOf(Configuracion.SEPARADOR_COLUMNAS, desde);
            npub = cadena.substring(desde, cuente).trim();

            desde = cuente + 1;
            cuente = cadena.length() - 1;
            nocu = cadena.substring(desde, cuente).trim();

            Categoria objTemporal = new Categoria(codCategoria, nombre, estado, 0, npub, nocu);
            arregloCategoria.add(objTemporal);

        }
        return arregloCategoria;
    }

    @Override
    public boolean insertInto(Categoria obj, String rutaCompletaImagen) {
        boolean correcto = false;
        obj.setNombreImagenPrivadoCategoria(Imagen.grabarLaImagen(rutaCompletaImagen));

        String cadena
                = obj.getCodCategoria() + Configuracion.SEPARADOR_COLUMNAS
                + obj.getNombreCategoria() + Configuracion.SEPARADOR_COLUMNAS
                + obj.isEstadoCategoria() + Configuracion.SEPARADOR_COLUMNAS
                + obj.getNombreImagenPublicoCategoria() + Configuracion.SEPARADOR_COLUMNAS
                + obj.getNombreImagenPrivadoCategoria();

        if (miArchivo.agregarRegistro(cadena)) {
            correcto = true;
        }
        return correcto;
    }

    @Override
    public int getSerial() {
        int id = 0;
        try {
            id = miArchivo.ultimoCodigo() + 1;
        } catch (IOException ex) {
            Logger.getLogger(DAOCategorias.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    @Override
    public int numRows() {
        int cantidad = 0;
        try {
            cantidad = miArchivo.cantidadFilas();
        } catch (IOException ex) {
            Logger.getLogger(DAOProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cantidad;
    }

    @Override
    public boolean deleteFrom(int indice) {
        boolean correcto = false;
        List<String> arregloDatos;
        try {
            arregloDatos = miArchivo.borrarFilaPosicion(indice);
            if (!arregloDatos.isEmpty()) {
                String nocu = arregloDatos.get(arregloDatos.size() - 1);
                String nombreBorrar = Ruta.RUTA_FOTOS + Configuracion.SEPARADOR_CARPETA + nocu;
                Path rutaBorrar = Paths.get(nombreBorrar);
                Files.deleteIfExists(rutaBorrar);
                correcto = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(DAOProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }

    @Override
    public boolean updateSet(int indice, Categoria obj, String ruta) {
        boolean correcto = false;
        try {
            String cadena, nocu;
            List<String> arregloDatos;

            cadena = obj.getCodCategoria() + Configuracion.SEPARADOR_COLUMNAS
                    + obj.getNombreCategoria() + Configuracion.SEPARADOR_COLUMNAS
                    + obj.isEstadoCategoria() + Configuracion.SEPARADOR_COLUMNAS
                    + obj.getNombreImagenPublicoCategoria() + Configuracion.SEPARADOR_COLUMNAS;

            if (ruta.isBlank()) {
                cadena = cadena + obj.getNombreImagenPrivadoCategoria();
            } else {
                nocu = Imagen.grabarLaImagen(ruta);
                cadena = cadena + nocu;
                arregloDatos = miArchivo.borrarFilaPosicion(indice);
                if (!arregloDatos.isEmpty()) {
                    String nomOculto = arregloDatos.get(arregloDatos.size() - 1);
                    String nombreBorrar = Ruta.RUTA_FOTOS + "\\" + nomOculto;
                    Path rutaBorrar = Paths.get(nombreBorrar);
                    Files.deleteIfExists(rutaBorrar);
                }
            }

            if (miArchivo.actualizaFilaPosicion(indice, cadena)) {
                correcto = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(DAOCategorias.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }

    @Override
    public Categoria getOne(int indice) {
        int contador;
        Categoria objListo;

        contador = 0;
        objListo = new Categoria();
        List<Categoria> arrCategorias = SelectFrom();

        for (Categoria objCate : arrCategorias) {
            if (contador == indice) {
                objListo = objCate;
            }
            contador++;
        }
        return objListo;
    }
}
