package org.poo.recurso.utilidad;

import java.io.File;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.poo.recurso.constante.Persistencia;

public class Formulario {

    /**
     * Crea un selector de imágenes con filtros
     */
    public static FileChooser selectorImagen(String tituloVentana, String tituloFiltros, String[] extensiones) {
        File rutaInicial = new File(System.getProperty("user.home"));
        if (!rutaInicial.exists()) {
            rutaInicial = new File(Persistencia.RUTA_PROYECTO);
        }
        
        FileChooser objSeleccionar = new FileChooser();
        objSeleccionar.setTitle(tituloVentana);
        objSeleccionar.setInitialDirectory(rutaInicial);
        objSeleccionar.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter(tituloFiltros, extensiones)
        );
        
        return objSeleccionar;
    }

    /**
     * Limita la cantidad de caracteres en un TextField
     */
    public static void cantidadCaracteres(TextField caja, int limite) {
        caja.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.length() > limite) {
                caja.setText(newValue.substring(0, limite));
            }
        });
    }

    /**
     * Permite solo números enteros en un TextField
     */
    public static void soloNumeros(TextField caja) {
        caja.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("\\d*")) {
                caja.setText(oldValue);
            }
        });
    }

    /**
     * Permite solo números decimales con hasta 2 decimales
     */
    public static void soloDecimales(TextField caja) {
        caja.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("\\d*(\\.\\d{0,2})?")) {
                caja.setText(oldValue);
            }
        });
    }

    /**
     * Permite solo letras y espacios
     */
    public static void soloLetras(TextField caja) {
        caja.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) {
                caja.setText(oldValue);
            }
        });
    }

    /**
     * Valida formato de correo electrónico
     */
    public static boolean validarEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(regex);
    }

    /**
     * Valida formato de teléfono (10 dígitos)
     */
    public static void soloTelefono(TextField caja) {
        caja.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("\\d{0,10}")) {
                caja.setText(oldValue);
            }
        });
    }

    /**
     * Valida formato de cédula (números y guión)
     */
    public static void formatoCedula(TextField caja) {
        caja.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("\\d{0,10}[-]?\\d{0,1}")) {
                caja.setText(oldValue);
            }
        });
    }

    /**
     * Valida formato de placa de vehículo
     */
    public static void formatoPlaca(TextField caja) {
        caja.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String upperNew = newValue.toUpperCase();
                if (!upperNew.matches("[A-Z]{0,3}\\d{0,3}")) {
                    caja.setText(oldValue);
                } else if (!upperNew.equals(newValue)) {
                    caja.setText(upperNew);
                }
            }
        });
        cantidadCaracteres(caja, 6);
    }

    /**
     * Deshabilita el DatePicker para fechas pasadas
     */
    public static void deshabilitarFechasPasadas(DatePicker datePicker) {
        datePicker.setDayCellFactory(picker -> new javafx.scene.control.DateCell() {
            @Override
            public void updateItem(java.time.LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(java.time.LocalDate.now()));
            }
        });
    }

    /**
     * Deshabilita el DatePicker para fechas futuras
     */
    public static void deshabilitarFechasFuturas(DatePicker datePicker) {
        datePicker.setDayCellFactory(picker -> new javafx.scene.control.DateCell() {
            @Override
            public void updateItem(java.time.LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isAfter(java.time.LocalDate.now()));
            }
        });
    }
}
