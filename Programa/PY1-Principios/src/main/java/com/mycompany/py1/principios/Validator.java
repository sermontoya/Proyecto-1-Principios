package com.mycompany.py1.principios;

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
    // --- Registro genérico, reutilizado en todos los patrones ---
    private static final String REG = "(AX|BX|CX|DX|AH|AL|BH|BL|CH|CL|DH|DL)";
 
    // LOAD reg | STORE reg | ADD reg | SUB reg
    private static final Pattern PATRON_LOAD_STORE_ADD_SUB =
            Pattern.compile("^(LOAD|STORE|ADD|SUB)\\s+" + REG + "$", Pattern.CASE_INSENSITIVE);
 
    // MOV reg_destino, valor      (ej. MOV BX, 5)
    private static final Pattern PATRON_MOV_VALOR =
            Pattern.compile("^(MOV)\\s+" + REG + "\\s*,\\s*(-?\\d+)$", Pattern.CASE_INSENSITIVE);
    
    // MOV DX|AL, "texto"      (ej. MOV DX, "Hola mundo")
    private static final Pattern PATRON_MOV_TEXTO =
        Pattern.compile("^(MOV)\\s+(DX|AL)\\s*,\\s*\"([^\"]*)\"$", Pattern.CASE_INSENSITIVE);


    // MOV AH, código   (ej. MOV AH, 3CH)
    private static final Pattern PATRON_MOV_INT =
            Pattern.compile("^(MOV)\\s+(AH)\\s*,\\s*(3C|3D|4D|40|41)H$", Pattern.CASE_INSENSITIVE);
 
    // MOV reg_destino, reg_origen (ej. MOV BX, AX)
    private static final Pattern PATRON_MOV_REGISTRO =
            Pattern.compile("^(MOV)\\s+" + REG + "\\s*,\\s*" + REG + "$", Pattern.CASE_INSENSITIVE);
 
    // INC | DEC          (afecta al AC)
    // INC reg | DEC reg  (afecta al registro)
    private static final Pattern PATRON_INC_DEC =
            Pattern.compile("^(INC|DEC)(?:\\s+" + REG + ")?$", Pattern.CASE_INSENSITIVE);
 
    // SWAP reg1, reg2
    private static final Pattern PATRON_SWAP =
            Pattern.compile("^(SWAP)\\s+" + REG + "\\s*,\\s*" + REG + "$", Pattern.CASE_INSENSITIVE);
 
    // INT 20H | INT 10H | INT 09H | INT 21H  (dos dígitos hex + 'H')
    private static final Pattern PATRON_INT =
            Pattern.compile("^(INT)\\s+(20H|10H|09H|21H)$", Pattern.CASE_INSENSITIVE);
 
    // JMP [+/-]Desplazamiento   (ej. JMP +3, JMP -2)
    private static final Pattern PATRON_JMP =
            Pattern.compile("^(JMP)\\s+\\[([+-]?\\d+)\\]$", Pattern.CASE_INSENSITIVE);
 
    // CMP reg1, reg2
    private static final Pattern PATRON_CMP =
            Pattern.compile("^(CMP)\\s+" + REG + "\\s*,\\s*" + REG + "$", Pattern.CASE_INSENSITIVE);
 
    // JE / JNE [+/-]Desplazamiento
    private static final Pattern PATRON_JE_JNE =
            Pattern.compile("^(JE|JNE)\\s+\\[([+-]?\\d+)\\]$", Pattern.CASE_INSENSITIVE);
 
    // PARAM v1, v2, v3   (1 a 3 valores numéricos, separados por coma)
    private static final Pattern PATRON_PARAM =
            Pattern.compile("^(PARAM)\\s+(-?\\d+)(\\s*,\\s*-?\\d+){0,2}$", Pattern.CASE_INSENSITIVE);
 
    // PUSH reg | POP reg
    private static final Pattern PATRON_PUSH_POP =
            Pattern.compile("^(PUSH|POP)\\s+" + REG + "$", Pattern.CASE_INSENSITIVE);

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
        return PATRON_LOAD_STORE_ADD_SUB.matcher(linea).matches()
                || PATRON_MOV_VALOR.matcher(linea).matches()
                || PATRON_MOV_INT.matcher(linea).matches()
                || PATRON_MOV_TEXTO.matcher(linea).matches()
                || PATRON_MOV_REGISTRO.matcher(linea).matches()
                || PATRON_INC_DEC.matcher(linea).matches()
                || PATRON_SWAP.matcher(linea).matches()
                || PATRON_INT.matcher(linea).matches()
                || PATRON_JMP.matcher(linea).matches()
                || PATRON_CMP.matcher(linea).matches()
                || PATRON_JE_JNE.matcher(linea).matches()
                || PATRON_PARAM.matcher(linea).matches()
                || PATRON_PUSH_POP.matcher(linea).matches();
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