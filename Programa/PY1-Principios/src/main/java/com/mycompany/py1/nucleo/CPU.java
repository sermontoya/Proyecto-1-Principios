/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.py1.nucleo;

import com.mycompany.py1.nucleo.BCP;
import com.mycompany.py1.memoria.Memoria;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author sermo
 */
public class CPU {
    private static int cantCPUs = 1; // Número de CPUs disponible
    private int id = cantCPUs++; // Identificador único de la CPU
    private int ax;
    private int bx;
    private int cx;
    private String dx;
    private String ah;
    private String al;
    private int accumulator;
    private int pc;
    private String InstructionRegister;
    private Memoria memoria;
    private boolean flag;
    private Interrupcion interrupcion;

    // MOV DX, "texto"  o  MOV AL, "texto": se evalúa antes de pasar a mayúsculas
    private static final Pattern PATRON_TEXTO =
        Pattern.compile("^MOV\\s+(DX|AL)\\s*,\\s*\"([^\"]*)\"$", Pattern.CASE_INSENSITIVE);

    public CPU(Memoria memoria, Interrupcion interrupcion) {
        this.memoria = memoria;
        this.interrupcion = interrupcion;
        this.ax = 0;
        this.bx = 0;
        this.cx = 0;
        this.dx = "0";
        this.ah = "0";
        this.al = "0";
        this.accumulator = 0;
        this.pc = 0;
        this.InstructionRegister = "";

    }

    
    
    // revisa la instrucción y ejecuta lo que indica
    public void ejecutarInstruccion(String instruccion, BCP bcp) {
        this.InstructionRegister = instruccion;
        Matcher mTexto = PATRON_TEXTO.matcher(instruccion.trim());
        if (mTexto.matches()) {
            if (mTexto.group(1).equalsIgnoreCase("DX")) {
                dx = mTexto.group(2);   // texto tal cual, sin comillas y sin mayúsculas
            } else {
                al = mTexto.group(2);
            }
            return;
        }
        String normalizada = instruccion.trim().toUpperCase();
        String[] partes = normalizada.split("[\\s,]+"); //formato de instruccion
        String operacion = partes[0];
        String registro = "";
        String[] params;
        String valor = "";
        if (operacion.equals("PARAM")) {
            params = Arrays.copyOfRange(partes, 1, partes.length);
            if (params.length > bcp.espacioPila()) {
                interrupcion.abortarProceso(bcp, "Desbordamiento de pila en el proceso PID " + bcp.getPid()
                        + ": PARAM agrega " + params.length + " valor(es) y solo caben "
                        + bcp.espacioPila() + " más (capacidad " + BCP.TAM_PILA + ").");
                return;
            }
            for (String param : params) {
                bcp.pushPila(param);
            }
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
                        moverValor(registro, getDx());
                        break;
                    case "AH":
                        moverValor(registro, ah);
                        break;
                    case "AL":
                        moverValor(registro, al);
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
                manejarInterrupcion(registro, bcp);
                break;
            case "JMP":
                manejarSalto(registro, bcp);
                break;
            case "CMP":
                manejarComparacion(registro, valor);
                break;
            case "JE":
            case "JNE": 
                manejarSaltoCondicional(operacion, registro, bcp);
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
                accumulator = Integer.parseInt(dx);
                break;
            case "AH":
                accumulator = Integer.parseInt(ah);
                break;
            case "AL":
                accumulator = Integer.parseInt(al);
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
                dx = String.valueOf(accumulator);
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
                dx = valor;
                break;
            case "AH":
                ah = valor;
                break;
            case "AL":
                al = valor;
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
                accumulator += Integer.parseInt(dx);
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
                dx = String.valueOf(Integer.parseInt(dx) + 1);
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
                dx = String.valueOf(Integer.parseInt(dx) - 1);
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
                accumulator -= Integer.parseInt(dx);
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
    private void manejarInterrupcion(String valor, BCP bcp) {
        if(valor.equals("20H")) {
            // Interrupción 20H: Terminar programa
            interrupcion.terminarPrograma(bcp);
        } else if(valor.equals("10H")) {
            // Interrupción 10H: Imprimir en pantalla
            imprimir();
        } else if(valor.equals("09H")) {
            // Interrupción 09H: Leer de teclado
            leer();
        } else if(valor.equals("21H")) {
            // Interrupción 21H: Manejo de archivos
            manejarArchivos(bcp);
        }
    }

    
    // INT 21H: DX = nombre del archivo, AH = función, AL = contenido (leer/escribir)
    private void manejarArchivos(BCP bcp) {
        String nombre = dx;
        boolean exito;

        switch (ah) {
            case "3CH":   // crear
                exito = interrupcion.crearArchivo(nombre);
                break;
            case "3DH":   // abrir
                exito = interrupcion.abrirArchivo(nombre);
                if (exito) {
                    bcp.abrirArchivo(nombre);
                }
                break;
            case "4DH": { // leer: el contenido queda en AL
                String contenido = interrupcion.leerArchivo(nombre);
                exito = contenido != null;
                if (exito) {
                    al = contenido;
                }
                break;
            }
            case "40H":   // escribir el contenido de AL
                exito = interrupcion.escribirArchivo(nombre, al);
                break;
            case "41H":   // eliminar
                exito = interrupcion.eliminarArchivo(nombre);
                if (exito) {
                    bcp.cerrarArchivo(nombre);
                }
                break;
            default:
                interrupcion.abortarProceso(bcp,
                        "INT 21H: la función AH = " + ah + " no existe.");
                return;
        }

        if (!exito) {
            interrupcion.abortarProceso(bcp, null);   // null = usar el error de Almacenamiento
        }
    }
    private void manejarComparacion(String registro1, String registro2) {
        int r1, r2;
        r1 = getRegistro(registro1);
        r2 = getRegistro(registro2);
        flag = r1 == r2; 
    }

    private void manejarSalto(String valor, BCP bcp) {
        saltar(leerDesplazamiento(valor), bcp);
    }

    private void manejarSaltoCondicional(String operacion, String valor, BCP bcp) {
        if ((operacion.equals("JE") && flag) || (operacion.equals("JNE") && !flag)) {
            saltar(leerDesplazamiento(valor), bcp);
        }
    }

    // Quita los corchetes: "[+5]" -> 5, "[-3]" -> -3
    private int leerDesplazamiento(String valor) {
        return Integer.parseInt(valor.replaceAll("[\\[\\]]", ""));
    }

    // Modifica el PC del BCP (el que usa Principal). Principal le suma 1 después de ejecutar.
    private void saltar(int desplazamiento, BCP bcp) {
        int destino = bcp.getPc() + 1 + desplazamiento;          // dónde terminará el PC
        int ultima = bcp.getInicio() + bcp.getTamano() - 1;      // última instrucción del proceso
        if (destino < bcp.getInicio() || destino > ultima) {
            interrupcion.abortarProceso(bcp, "Salto fuera del programa en el proceso PID " + bcp.getPid()
                    + ": el PC iría a la posición " + destino
                    + ", y el programa solo abarca de la " + bcp.getInicio() + " a la " + ultima + ".");
            return;
        }
        bcp.setPc(bcp.getPc() + desplazamiento);
    }

    private void pushAlStack(String registro, BCP bcp) {
        if (!bcp.pushPila(String.valueOf(getRegistro(registro)))) {
            interrupcion.abortarProceso(bcp, "Desbordamiento de pila en el proceso PID " + bcp.getPid()
                    + ": la pila ya tiene " + BCP.TAM_PILA + " elementos y PUSH intentó agregar otro.");
        }
    }

    private void popDelStack(String registro, BCP bcp) {
        String valor = bcp.popPila();
        if (valor == null) {
            interrupcion.abortarProceso(bcp, "Pila vacía en el proceso PID " + bcp.getPid() + ": POP sin elementos.");
            return;
        }
        moverValor(registro, valor);
    }

    private void imprimir() {
        String texto = dx;
        interrupcion.imprimir(texto);
    }

    private void leer() {
        boolean valido = false;
        String input = "";
        while (!valido) {
            input = interrupcion.leer();
            try {
                int valor = Integer.parseInt(input);
                if (valor < 0 || valor > 255) {
                    interrupcion.imprimir("Entrada inválida. Por favor, ingrese un número entero en el rango de 0 a 255.");
                    continue;
                }
                valido = true;
            } catch (NumberFormatException e) {
                interrupcion.imprimir("Entrada inválida. Por favor, ingrese un número entero.");
            }
        }
        moverValor("DX", input);
    }

    public int getAx() { return ax; }
    public int getBx() { return bx; }
    public int getCx() { return cx; }
    public String getDx() { return dx; }
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
                return Integer.parseInt(dx);
            default:
                return 0; 
        }
    }

    public String getAh() { return ah; }
    public String getAl() { return al; }
    public int getPc() { return pc; }
    public String getInstructionRegister() { return InstructionRegister; }
    public int getId() { return id; }
 
    // Reinicia los registros y el acumulador a 0
    public void reiniciar() {
        ax = 0;
        bx = 0;
        cx = 0;
        dx = "0";
        ah = "0";
        al = "0";
        InstructionRegister = "";
        pc = 0;
        accumulator = 0;
    }
}
