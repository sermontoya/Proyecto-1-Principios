/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea.principios;

import java.util.ArrayList;

/**
 *
 * @author sermo
 */
public class CPU {
    private int ax;
    private int bx;
    private int cx;
    private int dx;
    private int accumulator;
    private String InstructionRegister;

    public CPU() {
        this.ax = 0;
        this.bx = 0;
        this.cx = 0;
        this.dx = 0;
        this.accumulator = 0;
        this.InstructionRegister = "";

    }

    public void ejecutarPrograma(ArrayList<String> instrucciones) {
        for (String instruccion : instrucciones) {
            InstructionRegister = instruccion;
            ejecutarInstruccion(instruccion);
            
        }
    }
    
    // revisa la instrucción y ejecuta lo que indica
    public void ejecutarInstruccion(String instruccion) {
        this.InstructionRegister = instruccion;
        String normalizada = instruccion.trim().toUpperCase();
        String[] partes = normalizada.split("[\\s,]+"); //formato de instruccion
        String operacion = partes[0];
        String registro = partes[1];
        int valor = 0;
        if(partes.length > 2) {
            valor = Integer.parseInt(partes[2]);
        }
        switch (operacion) {
            case "LOAD":
                cargarRegistro(registro);
                break;
            case "STORE":
                almacenarRegistro(registro);
                break;
            case "MOV":
                moverValor(registro, valor);
                break;
            case "ADD":
                sumarRegistro(registro);
                break;
            case "SUB":
                restarRegistro(registro);
                break;
            default:
                break;
        }
    }
    // si es load, carga a ac lo que está en el registro indicado
    private void cargarRegistro(String registro) {
        switch (registro) {
            case "AX":
                accumulator = ax;
                break;
            case "BX":
                accumulator = bx;
                break;
            case "CX":
                accumulator = cx;
                break;
            case "DX":
                accumulator = dx;
                break;
            default:
                break;
        }
    }
    
    // si es store, almacena el valor del acumulador en el registro indicado
    private void almacenarRegistro(String registro) {
        switch (registro) {
            case "AX":
                ax = accumulator;
                break;
            case "BX":
                bx = accumulator;
                break;
            case "CX":
                cx = accumulator;
                break;
            case "DX":
                dx = accumulator;
                break;
            default:
                break;
        }
    }
    // si es mov, mueve el valor indicado al registro indicado
    private void moverValor(String registro, int valor) {
        switch (registro) {
            case "AX":
                ax = valor;
                break;
            case "BX":
                bx = valor;
                break;
            case "CX":
                cx = valor;
                break;
            case "DX":
                dx = valor;
                break;
            default:
                break;
        }
    }
    // si es add, suma el valor del registro indicado al acumulador
    private void sumarRegistro(String registro) {
        switch (registro) {
            case "AX":
                accumulator += ax;
                break;
            case "BX":
                accumulator += bx;
                break;
            case "CX":
                accumulator += cx;
                break;
            case "DX":
                accumulator += dx;
                break;
            default:
                break;
        }
    }

    // si es sub, resta el valor del registro indicado al acumulador
    private void restarRegistro(String registro) {
        switch (registro) {
            case "AX":
                accumulator -= ax;
                break;
            case "BX":
                accumulator -= bx;
                break;
            case "CX":
                accumulator -= cx;
                break;
            case "DX":
                accumulator -= dx;
                break;
            default:
                break;
        }
    }

    public int getAx() { return ax; }
    public int getBx() { return bx; }
    public int getCx() { return cx; }
    public int getDx() { return dx; }
    public int getAccumulator() { return accumulator; }
 
    // Reinicia los registros y el acumulador a 0
    public void reiniciar() {
        ax = 0;
        bx = 0;
        cx = 0;
        dx = 0;
        accumulator = 0;
    }
}
