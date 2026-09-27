
package com.mycompany.py1.principios;

/**
 *
 * @author sermo
 */
public class Assembler {
    public String ensamblarInstruccion(String line) {

        line = line.trim().toUpperCase();
        //formato de instrucción
        String[] partes = line.split("[\\s,]+");

        String operacion = partes[0];
        String codigoOperacion = ensamblarOperacion(operacion);

        String codigoRegistro = ensamblarRegistro(partes[1]);

        // Instrucciones MOV
        if (operacion.equals("MOV")) {
            int numero = Integer.parseInt(partes[2]);
            String codigoNumero = enteroABinario(numero);

            return codigoOperacion + codigoRegistro + codigoNumero;
        }

        // LOAD, STORE, ADD y SUB
        return codigoOperacion + codigoRegistro + "00000000";
    }

    private String ensamblarOperacion(String operation) {
        String code = "";
        switch (operation) {
            case "LOAD":
                code = "0001 "; 
                break;
            case "STORE":
                code = "0010 "; 
                break;
            case "MOV":
                code = "0011 "; 
                break;
            case "SUB":
                code = "0100 "; 
                break;
            case "ADD":
                code = "0101 "; 
                break;
            default:
                code = "0000 "; 
        }
        return code;
    }

    private String ensamblarRegistro(String register) {
        String code = "";
        switch (register) {
            case "AX":
                code = "0001 "; 
                break;
            case "BX":
                code = "0010 "; 
                break;
            case "CX":
                code = "0011 "; 
                break;
            case "DX":
                code = "0100 "; 
                break;
            default:
                code = "0000 "; 
        }
        return code;
    }

    private String enteroABinario(int number) {
        int signo = 0;
        //primer digito es el signo
        if(number < 0) {
            signo = 1;
            number = Math.abs(number);
        }
        String binary = Integer.toBinaryString(number);
        while (binary.length() < 7) {
            binary = "0" + binary;
        }
        return signo + binary;
    }
}

