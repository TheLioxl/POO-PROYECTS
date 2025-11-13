package edu.poo.persistencia;

import edu.poo.modelo.Categoria;
import edu.poo.modelo.Producto;
import edu.poo.recurso.dominio.Ruta;
import edu.poo.interfaz.OperacionBD;
import unimag.poo.persistencia.NioFile;
import edu.poo.recurso.dominio.Configuracion;
import edu.poo.recurso.utilidad.Imagen;
import edu.poo.controlador.categoria.ControladorCategoListar;

import java.io.IOException;
import java.math.BigDecimal;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOProducto implements OperacionBD<Producto> {

    private NioFile miArchivo;
    private String nombrePersistencia;

    public DAOProducto() {
        try {
            nombrePersistencia = Ruta.RUTA_PERSISTENCIA + Configuracion.SEPARADOR_CARPETA + "DatosProducto.txt";
            miArchivo = new NioFile(nombrePersistencia);
        } catch (IOException ex) {
            Logger.getLogger(DAOProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Producto> SelectFromContar() {
        String cadena;
        int i, codCategoria, desde, limite, cuente, codProducto;

        List<String> arregloDatos;
        List<Producto> arregloProductos = new ArrayList<>();

        arregloDatos = miArchivo.obtenerDatos();
        limite = arregloDatos.size();

        for (i = 0; i < limite; i++) {
            cadena = arregloDatos.get(i).trim();
            try {
                desde = 0;
                cuente = cadena.indexOf(Configuracion.SEPARADOR_COLUMNAS, desde);
                codProducto = Integer.parseInt(cadena.substring(desde, cuente).trim());

                desde = cuente + 1;
                cuente = cadena.indexOf(Configuracion.SEPARADOR_COLUMNAS, desde);

                desde = cuente + 1;
                cuente = cadena.indexOf(Configuracion.SEPARADOR_COLUMNAS, desde);

                desde = cuente + 1;
                cuente = cadena.indexOf(Configuracion.SEPARADOR_COLUMNAS, desde);

                desde = cuente + 1;
                cuente = cadena.indexOf(Configuracion.SEPARADOR_COLUMNAS, desde);
                codCategoria = Integer.parseInt(cadena.substring(desde, cuente).trim());

                // Asigna el objeto de tipo Categoría al producto
                Categoria objCate = new Categoria();
                objCate.setCodCategoria(codCategoria);

                Producto objProdu = new Producto();
                objProdu.setCodProducto(codProducto);
                objProdu.setCatProducto(objCate);

                arregloProductos.add(objProdu);
            } catch (NumberFormatException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        return arregloProductos;
    }

    @Override
    public List<Producto> SelectFrom() {
        double precio;
        String nombre, cadena, imagenPublica, imagenPrivada;
        int cantidad, codCategoria, i, desde, limite, cuente, codigo;

        List<String> arregloDatos;
        List<Producto> arregloProducto = new ArrayList<>();

        List<Categoria> arrCategorias = ControladorCategoListar.arregloCategoriaTodos();

        arregloDatos = miArchivo.obtenerDatos();
        limite = arregloDatos.size();

        for (i = 0; i < limite; i++) {
            cadena = arregloDatos.get(i).trim();
            try {
                desde = 0;
                cuente = cadena.indexOf(Configuracion.SEPARADOR_COLUMNAS, desde);
                codigo = Integer.parseInt(cadena.substring(desde, cuente).trim());

                desde = cuente + 1;
                cuente = cadena.indexOf(Configuracion.SEPARADOR_COLUMNAS, desde);
                nombre = cadena.substring(desde, cuente).trim();

                desde = cuente + 1;
                cuente = cadena.indexOf(Configuracion.SEPARADOR_COLUMNAS, desde);
                precio = Double.parseDouble(cadena.substring(desde, cuente).trim());

                desde = cuente + 1;
                cuente = cadena.indexOf(Configuracion.SEPARADOR_COLUMNAS, desde);
                cantidad = Integer.parseInt(cadena.substring(desde, cuente).trim());

                desde = cuente + 1;
                cuente = cadena.indexOf(Configuracion.SEPARADOR_COLUMNAS, desde);
                codCategoria = Integer.parseInt(cadena.substring(desde, cuente).trim());

                desde = cuente + 1;
                cuente = cadena.indexOf(Configuracion.SEPARADOR_COLUMNAS, desde);
                imagenPublica = cadena.substring(desde, cuente).trim();

                desde = cuente + 1;
                cuente = cadena.length() - 1;
                imagenPrivada = cadena.substring(desde, cuente).trim();

//codProducto=10, nomProducto=Kuromi White XS, preProducto=78200.5, canProducto=10, catProducto=null, nomImgPubProducto=camisaKuromi02.png, nomImgOcuProducto=f1cafce4-a61e-4214-9107-e23dd7dd93fa_camisaKuromi02.png}
//codProducto=20, nomProducto=Kuromi Black XL, preProducto=60800.0, canProducto=5, catProducto=null, nomImgPubProducto=camisaKuromi03.png, nomImgOcuProducto=dd980186-e190-416b-adbd-ffddc9056adb_camisaKuromi03.png}
                Categoria objCategoria = null;
                for (Categoria cate : arrCategorias) {
                    if (cate.getCodCategoria() == codCategoria) {
                        objCategoria = cate;
                        break;
                    }
                }

                // Asigna el objeto de tipo Categoría al producto
                Producto objTemporal = new Producto(codigo, nombre, precio, cantidad, objCategoria, imagenPublica, imagenPrivada);
                arregloProducto.add(objTemporal);
            } catch (NumberFormatException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        return arregloProducto;
    }

    @Override
    public boolean insertInto(Producto obj, String ruta) {
        boolean correcto = false;
        BigDecimal precioBonito = new BigDecimal(obj.getPreProducto());
        obj.setNomImgOcuProducto(Imagen.grabarLaImagen(ruta));
        String cadena
                = obj.getCodProducto() + Configuracion.SEPARADOR_COLUMNAS
                + obj.getNomProducto() + Configuracion.SEPARADOR_COLUMNAS
                + precioBonito + Configuracion.SEPARADOR_COLUMNAS
                + obj.getCanProducto() + Configuracion.SEPARADOR_COLUMNAS
                + obj.getCatProducto().getCodCategoria() + Configuracion.SEPARADOR_COLUMNAS
                + obj.getNomImgPubProducto() + Configuracion.SEPARADOR_COLUMNAS
                + obj.getNomImgOcuProducto();
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
            Logger.getLogger(DAOProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    @Override
    public boolean deleteFrom(int indice) {
        boolean correcto = false;
        List<String> arregloDatos;
        try {
            arregloDatos = miArchivo.borrarFilaPosicion(indice);
            if (!arregloDatos.isEmpty()) {
                String nocu = arregloDatos.get(arregloDatos.size() - 1);
                String nombreBorrar = Ruta.RUTA_FOTOS
                        + Configuracion.SEPARADOR_CARPETA + nocu;
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
    public Producto getOne(int indice) {
        DAOCategorias miDaoCategoria = new DAOCategorias();
        List<Categoria> arregloCategoria = miDaoCategoria.SelectFromTodos();

        Producto objCarro = new Producto();
        try {
            double prep;
            int codP, codC, cantp;
            String nomp, npub, nocu;
            List<String> arrDatos = miArchivo.obtenerFila(indice);

            codP = Integer.parseInt(arrDatos.get(0));
            nomp = arrDatos.get(1);
            prep = Double.parseDouble(arrDatos.get(2));
            cantp = Integer.parseInt(arrDatos.get(3));
            codC = Integer.parseInt(arrDatos.get(4));
            npub = arrDatos.get(5);
            nocu = arrDatos.get(6);

            // Armar el objeto de categoría
            Categoria objTemporal = new Categoria();
            for (Categoria cate : arregloCategoria) {
                if (codC == cate.getCodCategoria()) {
                    objTemporal = cate;
                }
            }
            // *****************************************************************

            objCarro = new Producto(codP, nomp, prep, cantp, objTemporal, npub, nocu);

        } catch (IOException ex) {
            Logger.getLogger(DAOProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return objCarro;
    }

    @Override
    public boolean updateSet(int indice, Producto elObjeto, String ruta) {
        boolean correcto = false;
        try {
            String cadena, nocu;
            List<String> arregloDatos;

            cadena = elObjeto.getCodProducto() + Configuracion.SEPARADOR_COLUMNAS
                    + elObjeto.getNomProducto() + Configuracion.SEPARADOR_COLUMNAS
                    + elObjeto.getPreProducto() + Configuracion.SEPARADOR_COLUMNAS
                    + elObjeto.getCanProducto() + Configuracion.SEPARADOR_COLUMNAS
                    + elObjeto.getCatProducto().getCodCategoria() + Configuracion.SEPARADOR_COLUMNAS
                    + elObjeto.getNomImgPubProducto() + Configuracion.SEPARADOR_COLUMNAS;

            if (ruta.isBlank()) {
                cadena = cadena + elObjeto.getNomImgOcuProducto();
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
            Logger.getLogger(DAOProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }

}
