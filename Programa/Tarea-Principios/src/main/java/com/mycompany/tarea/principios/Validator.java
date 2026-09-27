package com.mycompany.tarea.principios;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 *
 * @author sermo
 */
public class Validator {

    private String error = null;
    private ArrayList<String> instrucciones = null;

    // Valida el archivo de instrucciones y carga las instrucciones en la lista
    private boolean validarLineas(String path) {
        instrucciones = new ArrayList<>();
        try (BufferedReader lector = new BufferedReader(new FileReader(path))) {
            String linea;
            int numeroLinea = 0;

            while ((linea = lector.readLine()) != null) {
                numeroLinea++;
                linea = linea.trim();

                if (linea.isEmpty()) {
                    continue;
                }

                if (!esLineaValida(linea)) {
                    error = "Error en la línea " + numeroLinea + ": " + linea;
                    return false;
                }
                instrucciones.add(linea);
            }

            return true;

        } catch (IOException e) {
            error = "Error al leer el archivo: " + e.getMessage();
            return false;
        }
    }

    private boolean esLineaValida(String linea) {
        // Validación de la instruccion MOV REGISTRO, VALOR
        Pattern patronMov = Pattern.compile(
                "^(MOV)\\s+(AX|BX|CX|DX)\\s*,\\s*(-?\\d+)$",
                Pattern.CASE_INSENSITIVE
        );
        // Validación de las instrucciones (LOAD, STORE, ADD y SUB) REGISTRO
        Pattern patronSimple = Pattern.compile(
                "^(LOAD|STORE|ADD|SUB)\\s+(AX|BX|CX|DX)$",
                Pattern.CASE_INSENSITIVE
        );

        return patronMov.matcher(linea).matches()
                || patronSimple.matcher(linea).matches();
    }
    // Valida el archivo de instrucciones y carga las instrucciones en la lista
    private boolean validarArchivo(String path) {
        Path archivo = Paths.get(path);

        if (!archivo.toString().toLowerCase().endsWith(".asm")) {
            error = "El archivo debe tener extensión .asm";
            return false;
        }

        if (!Files.isReadable(archivo)) {
            error = "El archivo no es legible";
            return false;
        }

        return validarLineas(path);
    }

    public boolean cargarArchivo(String path) {
        error = null;
        return validarArchivo(path);
    }

    public String getError() {
        return error;
    }

    public ArrayList<String> getInstrucciones() {
        return instrucciones;
    }
}