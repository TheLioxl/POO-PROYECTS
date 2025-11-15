package org.poo.recurso.utilidad;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase de utilidad para generar valores aleatorios
 */
public class Aleatorio {

    /**
     * Genera un número entero aleatorio entre inicio y fin (inclusivo)
     */
    public static Integer entero(int inicio, int fin) {
        Random aleatorio = new Random();
        return aleatorio.nextInt(fin - inicio + 1) + inicio;
    }

    /**
     * Genera un número decimal aleatorio entre inicio y fin
     */
    public static Double doble(Double inicio, Double fin) {
        Random aleatorio = new Random();
        Double valor = inicio + (fin - inicio) * aleatorio.nextDouble();
        return Math.round(valor * 100.0) / 100.0;
    }

    /**
     * Genera una cadena de texto aleatoria con la cantidad de caracteres especificada
     */
    public static String texto(int cantCaracteres) {
        String cadenaTexto = "";
        String diccionario = "abcdefghijklmnopqrstuvwxyz";
        int limiteDiccionario = diccionario.length() - 1;
        
        for (int i = 0; i < cantCaracteres; i++) {
            int posicion = entero(0, limiteDiccionario);
            char caracter = diccionario.charAt(posicion);
            cadenaTexto = cadenaTexto + caracter;
        }
        return cadenaTexto;
    }

    /**
     * Genera una fecha aleatoria entre dos fechas
     */
    public static String fecha(Date fechaIni, Date fechaFin) {
        String patronFecha = "yyyy-MM-dd";
        int unDiaMili = 1000 * 60 * 60 * 24;
        SimpleDateFormat miFormato = new SimpleDateFormat(patronFecha);
        long inicioMili = fechaIni.getTime();
        long finMili = fechaFin.getTime() + unDiaMili;
        long aleatorioMill = ThreadLocalRandom.current().nextLong(inicioMili, finMili);
        Date fechaAleatoria = new Date(aleatorioMill);
        return miFormato.format(fechaAleatoria);
    }
}