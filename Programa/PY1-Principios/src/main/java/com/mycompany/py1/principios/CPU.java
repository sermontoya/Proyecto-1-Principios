/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.py1.principios;

import java.util.Arrays;

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
    private int pc;
    private String InstructionRegister;
    private Memoria memoria;
    private boolean flag;

    public CPU(Memoria memoria) {
        this.memoria = memoria;
        this.ax = 0;
        this.bx = 0;
        this.cx = 0;
        this.dx = 0;
        this.accumulator = 0;
        this.pc = 0;
        this.InstructionRegister = "";

    }

    public void ejecutarPrograma( BCP bcp) {
        this.pc = bcp.getPc();
        int inicio = bcp.getInicio();
        int tamano = bcp.getTamano();
        while (pc < inicio + tamano) {
            String instruccion = memoria.leerMemoria(pc);
            ejecutarInstruccion(instruccion, bcp);
            pc++;
            bcp.setPc(pc);
            
        }

    }
    
    // revisa la instrucción y ejecuta lo que indica
    public void ejecutarInstruccion(String instruccion, BCP bcp) {
        this.InstructionRegister = instruccion;
        String normalizada = instruccion.trim().toUpperCase();
        String[] partes = normalizada.split("[\\s,]+"); //formato de instruccion
        String operacion = partes[0];
        String registro = "";
        String[] params;
        String valor = "";
        if (operacion.equals("PARAM")) {
                params = Arrays.copyOfRange(partes, 1, partes.length);
        }
        else if (partes.length > 1) {
            registro = partes[1];
            if (partes.length == 3) {
                valor = partes[2];
            }
        }
        
        
        switch (operacion) {
            case "LOAD":
                cargarRegistro(registro);
                break;
            case "STORE":
                almacenarRegistro(registro);
                break;
            case "MOV":
                switch(valor) {
                    case "AX":
                        moverValor(registro, String.valueOf(getAx()));
                        break;
                    case "BX":
                        moverValor(registro, String.valueOf(getBx()));
                        break;
                    case "CX":
                        moverValor(registro, String.valueOf(getCx()));
                        break;
                    case "DX":
                        moverValor(registro, String.valueOf(getDx()));
                        break;
                    default:
                        moverValor(registro, valor);
                        break;
                }
                break;
            case "ADD":
                sumarRegistro(registro);
                break;
            case "SUB":
                restarRegistro(registro);
                break;
            case "INC":
                if (!registro.equals("")){
                    incrementarRegistro(registro);
                } else{
                    //si no se indica registro, incrementa el acumulador
                    accumulator++;
                }
                break;
            case "DEC":
                if (!registro.equals("")){
                    decrementarRegistro(registro);
                } else{
                    //si no se indica registro, decrementa el acumulador
                    accumulator--;
                }
                break;
            case "SWAP":
                intercambiarRegistros(registro, valor);
                break;
            case "INT":
                // Manejar interrupciones según el valor de registro
                break;
            case "JMP":
                manejarSalto(valor);
                break;
            case "CMP":
                manejarComparacion(registro, valor);
                break;
            case "JE":
            case "JNE": 
                manejarSaltoCondicional(operacion, valor);
                break;
            case "PUSH":
                pushAlStack(registro, bcp);
                break;
            case "POP":
                popDelStack(registro, bcp);
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
    private void moverValor(String registro, String valor) {
        switch (registro) {
            case "AX":
                ax = Integer.parseInt(valor);
                break;
            case "BX":
                bx = Integer.parseInt(valor);
                break;
            case "CX":
                cx = Integer.parseInt(valor);
                break;
            case "DX":
                dx = Integer.parseInt(valor);
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
    // si es inc registro, incrementa el valor del registro indicado en 1
    private void incrementarRegistro(String registro) {
        switch (registro) {
            case "AX":
                ax++;
                break;
            case "BX":
                bx++;
                break;
            case "CX":
                cx++;
                break;
            case "DX":
                dx++;
                break;
            default:
                break;
        }
    }

    private void decrementarRegistro(String registro) {
        switch (registro) {
            case "AX":
                ax--;
                break;
            case "BX":
                bx--;
                break;
            case "CX":
                cx--;
                break;
            case "DX":
                dx--;
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

    private void intercambiarRegistros(String registro1, String registro2) {
        int temp = getRegistro(registro1);
        moverValor(registro1, String.valueOf(getRegistro(registro2)));
        moverValor(registro2, String.valueOf(temp));
        
    }
    private void manejarInterrupcion(String valor) {
        // Implementar la lógica para manejar interrupciones según el valor
    }

    private void manejarSalto(String valor) {
        pc += Integer.parseInt(valor);
    }

    private void manejarComparacion(String registro1, String registro2) {
        int r1, r2;
        r1 = getRegistro(registro1);
        r2 = getRegistro(registro2);
        flag = r1 == r2; 
    }

    private void manejarSaltoCondicional(String operacion, String valor) {
        if (operacion.equals("JE") && flag) {
            pc += Integer.parseInt(valor);
        } else if (operacion.equals("JNE") && !flag) {
            pc += Integer.parseInt(valor);
        }
    }

    private void pushAlStack(String registro, BCP bcp) {
        bcp.pushPila(String.valueOf(getRegistro(registro)));
    }

    private void popDelStack(String registro, BCP bcp) {
        String valor = bcp.popPila();
        if (valor != null) {
            moverValor(registro, valor);
        }
    }

    public int getAx() { return ax; }
    public int getBx() { return bx; }
    public int getCx() { return cx; }
    public int getDx() { return dx; }
    public int getAccumulator() { return accumulator; }
    public int getRegistro(String registro) {
        switch (registro) {
            case "AX":
                return ax;
            case "BX":
                return bx;
            case "CX":
                return cx;
            case "DX":
                return dx;
            default:
                return 0; 
        }
    }
 
    // Reinicia los registros y el acumulador a 0
    public void reiniciar() {
        ax = 0;
        bx = 0;
        cx = 0;
        dx = 0;
        accumulator = 0;
    }
}
