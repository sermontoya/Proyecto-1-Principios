package com.mycompany.py1.nucleo;

public class Duracion {

    /** Peso de una instrucción, según la tabla del enunciado. */
    public static int de(String instruccion) {
        if (instruccion == null) {
            return 1;
        }
        String[] p = instruccion.trim().toUpperCase().split("[\\s,]+");
        switch (p[0]) {
            case "LOAD": case "STORE": case "JMP": case "CMP": case "JE": case "JNE":
                return 2;
            case "MOV": case "INC": case "DEC": case "SWAP":
            case "PUSH": case "POP":
                return 1;
            case "ADD": case "SUB": case "PARAM":
                return 3;
            case "INT":
                return p.length > 1 ? deInterrupcion(p[1]) : 1;
            default:
                return 1;
        }
    }

    private static int deInterrupcion(String codigo) {
        switch (codigo) {
            case "20H": return 2;
            case "10H": return 2;
            case "09H": return 1;   // duración lo que dure el usuario en escribir
            case "21H": return 5;   
            default:    return 1;
        }
    }
}